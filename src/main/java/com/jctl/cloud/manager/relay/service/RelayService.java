/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.relay.service;

import java.util.Date;
import java.util.List;

import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.manager.message.service.WaringMessageService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.node.thread.NodeSaveThread;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.service.NodeDataDetailsService;
import com.jctl.cloud.manager.relay.dao.RelayDao;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;
import com.jctl.cloud.mina.entity.DataResultSet;
import com.jctl.cloud.mina.entity.GatewayResultSet;
import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.entity.OpenCloseVO;
import com.jctl.cloud.mina.entity.ResultSet;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.utils.NodeControlUtil;

/**
 * 中继管理Service
 *
 * @author ll
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class RelayService extends CrudService<RelayDao, Relay> {

    @Autowired
    private RelayDao relayDao;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private WaringMessageService waringMessageService;

    @Autowired
    private WaringCycleService waringCycleService;

    @Autowired
    private NodeDataDetailsService nodeDataDetailsService;
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private NodeControlUtil nodeControlUtil;

    private static String WARMING_MESG_TITLE = "预警通知";

    private final String message_code = "尊敬的用户您好，当前检测到设备";

    public Relay get(String id) {
        return super.get(id);
    }
    public Relay getByFamerId(String farmerId){
        return relayDao.getByFamerId(farmerId);
    }
    public List<Relay> findList(Relay relay) {
        return super.findList(relay);
    }

    public Page<Relay> findPage(Page<Relay> page, Relay relay) {
        return super.findPage(page, relay);
    }

    @Transactional(readOnly = false)
    public void save(Relay relay) {
        super.save(relay);
    }

    @Transactional(readOnly = false)
    public void delete(Relay relay) {
        super.delete(relay);
    }


    //增加自定义aop
    @Transactional(readOnly = false)
    public void                                                                                                                                                                                         saveOrUpdate(ResultSet resultSet) {
        //节点详情

        saveOrChangeRelayAndNode(resultSet);

        changeNodeSwitch(resultSet);

        saveNewNodeDetail(resultSet);

    }


    @Transactional(readOnly = false)
    public void saveOrChangeRelayAndNode(ResultSet resultSet) {
        //中继挂载详情
        GatewayResultSet gatewayResultSet = resultSet.getGatewayResultSet();
        //新曾或修改当前中继和节点信息
        if (gatewayResultSet != null) {
            String serverMac = gatewayResultSet.getServerMac();
            String powerSupply = gatewayResultSet.getPowerSupply();
            Relay temp = new Relay();
            Relay relay = getByMac(serverMac);
            if (relay == null) {
//                relayId = relay.getId();
                temp.setPowerSupply(powerSupply);
                temp.setRelayNum(serverMac);
                temp.setAddTime(new Date());
                temp.setDelFlag("1");
                save(temp);
            } else {
                relay.setPowerSupply(powerSupply);
                relay.setUpdateTime(new Date());
                save(relay);
            }
            //启动一个线程去保存
            new NodeSaveThread(resultSet, serverMac).start();
        }
    }


    @Transactional(readOnly = false)
    public void changeNodeSwitch(ResultSet resultSet) {
        OpenCloseVO openCloseFlag = resultSet.getOpenCloseVo();
        if (openCloseFlag != null) {
            Node node = nodeService.getByNodeMac(openCloseFlag.getNodeMac());
            String status = openCloseFlag.getOpenOrClose();

            if (status.equals("0000")) {
                node.setOpenFlag("0");
            }
            if (status.equals("0001")) {
                node.setOpenFlag("1");
            }
            node.setUpdateTime(new Date());
            nodeService.save(node);
        }
    }

    @Transactional(readOnly = false)
    public void saveNewNodeDetail(ResultSet resultSet) {
        DataResultSet dataResultSet = resultSet.getDataResultSet();
        //新增当前节点详情
        if (dataResultSet != null) {
            NodeDataDetails nodeData = new NodeDataDetails(new Date(), dataResultSet.getClientMac());

            //大气温度
            if (dataResultSet.getAirTemperature() != null) {
                nodeData.setAirTemperature(dataResultSet.getAirTemperature());
                if (dataResultSet.getAirTemperature() == -3.55) {
                    nodeData.setAirTemperature(0.00);
                }
            }
            // 大气湿度
            if (dataResultSet.getAirHumidity() != null) {
                nodeData.setAirHumidity(dataResultSet.getAirHumidity());
                if (dataResultSet.getAirHumidity() == -3.55) {
                    nodeData.setAirHumidity(0.00);
                }
            }

            // 1号采集点温度
            if (dataResultSet.getSoilTemperature1() != null) {
                nodeData.setSoilTemperature1(dataResultSet.getSoilTemperature1());
                if (dataResultSet.getAirHumidity() == -3.55) {
                    nodeData.setSoilTemperature1(0.00);
                }
            }
            // 1号采集点湿度
            if (dataResultSet.getSoilHumidity1() != null) {
                nodeData.setSoilHumidity1(dataResultSet.getSoilHumidity1());
                if (dataResultSet.getSoilHumidity1() == -3.55) {
                    nodeData.setSoilHumidity1(0.00);
                }
            }
            // 2号采集点温度
            if (dataResultSet.getSoilTemperature2() != null) {
                nodeData.setSoilTemperature2(dataResultSet.getSoilTemperature2());
                if (dataResultSet.getSoilTemperature2() == -3.55) {
                    nodeData.setSoilTemperature2(0.00);
                }
            }
            // 2号采集点湿度
            if (dataResultSet.getSoilHumidity2() != null) {
                nodeData.setSoilHumidity2(dataResultSet.getSoilHumidity2());
                if (dataResultSet.getSoilHumidity2() == -3.55) {
                    nodeData.setSoilHumidity2(0.00);
                }
            }
            // 3号采集点温度
            if (dataResultSet.getSoilTemperature3() != null) {
                nodeData.setSoilTemperature3(dataResultSet.getSoilTemperature3());
                if (dataResultSet.getSoilTemperature3() == -3.55) {
                    nodeData.setSoilTemperature3(0.00);
                }
            }
            // 3号采集点湿度
            if (dataResultSet.getSoilHumidity3() != null) {
                nodeData.setSoilHumidity3(dataResultSet.getSoilHumidity3());
                if (dataResultSet.getSoilHumidity3() == -3.55) {
                    nodeData.setSoilHumidity3(0.00);
                }
            }
            // 二氧化碳
            if (dataResultSet.getCo2() != null) {
                nodeData.setCo2(dataResultSet.getCo2());
                if (dataResultSet.getCo2() == 65535.00) {
                    nodeData.setCo2(0.00);
                }
            }
            //开关状态
            if (dataResultSet.getIsOpen() != null) {
                nodeData.setOpenFlag(dataResultSet.getIsOpen().toString());
            }
            //电池电量
            if (dataResultSet.getPowerSupply() != null) {
                nodeData.setPower(dataResultSet.getPowerSupply());
            }
            //切换频点
            if (dataResultSet.getFrequencyPoint() != null) {
                nodeData.setFrequencyPoint(dataResultSet.getFrequencyPoint());
            }
            //电电量池
            if (dataResultSet.getPower() != null) {
                nodeData.setPowerSupply(dataResultSet.getPower());
            }
//            sendWaringMessage(nodeData);
            nodeDataDetailsService.save(nodeData);
        }

    }


    public Relay getByMac(String serverMac) {

        return relayDao.getByMac(serverMac);
    }

    public List<Relay> findRelayByNum(Relay relay) {
        return relayDao.findRelayByNum(relay);
    }

    public List<Relay> findRelayNumByFarmerId(String farmerId) {
        return relayDao.findRelayNumByFarmerId(farmerId);
    }


    /**
     * aop调用的方法 不要改
     * 有新的预警就插入数据库
     * @param nodeData
     */
    public void sendWaringMessage(NodeDataDetails nodeData) {
        WaringCycle search = new WaringCycle();
        search.setNodeNum(nodeData.getNodeMac());
        List<WaringCycle> list = waringCycleService.findList(search);
        Relay relay = get(nodeService.findByNodeNum(nodeData.getNodeMac()).getRelayId());
        Node node = nodeService.findByNodeNum(nodeData.getNodeMac());
        GoEasy goEasy = new GoEasy("BC-d14d4984a76247e99820ceb5f3ac219c");
        if (list != null && list.size() > 0) {
            for (WaringCycle waringCycle : list) {
                try {
                	 //大气温度
                    if (waringCycle.getProperty().equals("airTemperature")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气温度", 1, "airTemperature"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"大气温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气温度", 0, "airTemperature"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"大气温度超出正常范围,设备已自动开启!");
                        }
                    }
                    //大气湿度
                    if (waringCycle.getProperty().equals("airHumidity")) {
                        if (nodeData.getAirHumidity() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 1, "airHumidity"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"大气温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getAirHumidity() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 0, "airHumidity"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"大气温度超出正常范围,设备已自动关闭!");
                        }
                    }

                    //1号采集点温度
                    if (waringCycle.getProperty().equals("soilHumidity1")) {
                        if (nodeData.getSoilHumidity1() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "1号采集点温度", 1, "soilHumidity1"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getSoilHumidity1()  < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "1号采集点温度", 0, "soilHumidity1"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                    }

                    //1号采集点湿度
                    if (waringCycle.getProperty().equals("soilTemperature1")) {
                        if (nodeData.getSoilTemperature1() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "1号采集点湿度", 1, "soilTemperature1"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getSoilTemperature1() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "1号采集点湿度", 0, "soilTemperature1"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                    }
                    //2号采集点温度
                    if (waringCycle.getProperty().equals("soilHumidity2")) {
                        if (nodeData.getSoilHumidity2() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号采集点温度", 1, "soilHumidity2"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getSoilHumidity2() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号采集点温度", 0, "soilHumidity2"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                    }
                    //2号采集点湿度
                    if (waringCycle.getProperty().equals("soilTemperature2")) {
                        if (nodeData.getSoilTemperature2() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号采集点湿度", 1, "soilTemperature2"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getSoilTemperature2() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号采集点湿度", 0, "soilTemperature2"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                    }
                    //2号采集点温度
                    if (waringCycle.getProperty().equals("soilHumidity3")) {
                        if (nodeData.getSoilHumidity3() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 1, "soilHumidity3"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getSoilHumidity3() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 0, "soilHumidity3"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                    }
                    //2号采集点湿度
                    if (waringCycle.getProperty().equals("soilTemperature3")) {
                        if (nodeData.getSoilTemperature3() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号采集点湿度", 1, "soilTemperature3"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getSoilTemperature3() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号采集点湿度", 0, "soilTemperature3"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                    }
                    //二氧化碳浓度
                    if (waringCycle.getProperty().equals("co2")) {
                        if (nodeData.getCo2() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "二氧化碳浓度", 1, "co2"));
                            nodeControlUtil.closeNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                        if (nodeData.getCo2() < waringCycle.getMin()) {
                            waringMessageService.save(getWaringMessage(nodeData, "二氧化碳浓度", 0, "co2"));
                            nodeControlUtil.openNode(node);
                            goEasy.publish(WARMING_MESG_TITLE,"1号采集点温度超出正常范围,设备已自动关闭!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建预警消息，推送
     *
     * @param nodeDataDetails
     * @param str
     * @param status
     * @param property
     * @return
     */
    public WaringMessage getWaringMessage(NodeDataDetails nodeDataDetails, String str, Integer status, String property) {
        WaringMessage message = new WaringMessage();
        message.setProperty(property);
        message.setStatus("0");
        if (status == 1) {
            message.setNodeNum(nodeDataDetails.getNodeMac());
            message.setMessage(message_code + str + "高于预警值!请及时查看处理。");
        } else if(status == 0){
            message.setNodeNum(nodeDataDetails.getNodeMac());
            message.setMessage(message_code + str + "低于预警值!请及时查看处理。");
        }
        return message;
    }


    public Relay findFarmerByRelayNum(String relayNum) {
        return relayDao.findFarmerByRelayNum(relayNum);
    }

    public List<Node> selectAllNodeByUserId(String id) {
        return relayDao.selectAllNodeByUserId();
    }

    public List<Relay> findListByUser(Relay relay) {

        return  relayDao.findListByUser(relay);
    }

    public List<Relay> getTest() {
        return relayDao.getTest();
    }

    public Integer getRelayNum() {
        return relayDao.getRelayNum();
    }

    public Relay getRelayAndNodeNum(Relay relay) {
      return relayDao.getRelayAndNodeNum(relay);
    }

    public List<Relay> findListByUserAll(Relay relay) {
        return relayDao.findListByUserAll(relay);
    }
}