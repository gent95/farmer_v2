package com.jctl.cloud.api.node;

import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.QutarzUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gent on 2017/5/10.
 */
@Controller
@RequestMapping("timing")
public class ANodeTimingConsole {
    @Autowired
    private NodeCollectionCycleService nodeCollectionCycleService;
    @Autowired
    private NodeService nodeService;

    /**
     * 为指定的节点配置定时策略
     * @param node
     * @param time
     * @param on
     * @param off
     * @return
     */
    @RequestMapping("save")
    public Map save(Node node,String time, String[] on, String[] off) {
        Map<String,Object> result = new HashMap<String,Object>();
        NodeCollectionCycle nodeCollectionCycle = new NodeCollectionCycle();
        nodeCollectionCycle.setNodeId(node.getId());
        nodeCollectionCycle.setNodeId(node.getNodeNum());
        nodeCollectionCycle.setCycleTime(time);
        try{
            nodeCollectionCycleService.deleteByNodeId(nodeCollectionCycle);
            nodeCollectionCycle.setAddUserId(Long.parseLong(UserUtils.getUser().getId()));
            nodeCollectionCycle.setCycleTime("0 "+"*/"+nodeCollectionCycle.getCycleTime()+" * * * ?");
            if (nodeCollectionCycle.getCycleOn() == null || nodeCollectionCycle.getCycleOn().equals("")) {
                nodeCollectionCycle.setCycleOn(null);
            }else{
                nodeCollectionCycle.setCycleOn(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOn(),on));
            }
            if (nodeCollectionCycle.getCycleOff() == null || nodeCollectionCycle.getCycleOff().equals("")) {
                nodeCollectionCycle.setCycleOff(null);
            }else{
                nodeCollectionCycle.setCycleOff(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOff(),off));
            }
            nodeCollectionCycleService.save(nodeCollectionCycle);
            result.put("flag","1");
        }catch(Exception e){
            result.put("flag","0");
            result.put("msg","操作失败!0");
            e.printStackTrace();
        }finally {
            nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nodeCollectionCycle);
            result.put("cyclTimeGet",QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleTime()));
            if (nodeCollectionCycle.getCycleOn().length() > 0 && !nodeCollectionCycle.getCycleOn().equals("")){
                result.put("cyclOnTime",QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOn()));
                result.put("cyclOnWeek",QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOn()).split(","));
            }else if (nodeCollectionCycle.getCycleOff().length() > 0 && !nodeCollectionCycle.getCycleOff().equals("")){
                result.put("cyclOffTime",QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOff()));
                result.put("cyclOffWeek",QutarzUtil.qutarzStrConvertWeek(nodeCollectionCycle.getCycleOff()).split(","));
            }
        }
        //启动定时器
        QutarzUtil.start();
        return result;
    }
}
