package com.jctl.cloud.manager.node.thread;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.mina.entity.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public class NodeSaveThread extends Thread {

    private ResultSet resultSet;
    private String relayMac;

    private NodeService nodeService = SpringContextHolder.getBean(NodeService.class);

    private RelayService relayService = SpringContextHolder.getBean(RelayService.class);


    public NodeSaveThread() {

    }

    public NodeSaveThread(ResultSet resultSet, String relayMac) {
        this.resultSet = resultSet;
        this.relayMac = relayMac;
    }

    public void run() {

        try {
            Thread.sleep(3000L);
            Relay relay = relayService.getByMac(relayMac);
            List<String> clintMacs = resultSet.getGatewayResultSet().getClientMacList();
            for (String cli : clintMacs) {
                Node tem = nodeService.findByNodeNum(cli);
                if (tem != null && !tem.getRelayId().toString().equals(relay.getId())) {
                    nodeService.deleteByNodeNum(cli);
                }
            }
            Node node = new Node();
            node.setRelayId(relay.getId());
            List<Node> nodes = nodeService.findList(node);
            List<String> save = new ArrayList<>();
            for (Node old : nodes) {
                save.add(old.getNodeNum());
            }
            for (String cli : save) {
                if (!clintMacs.contains(cli)) {
                    nodeService.deleteByNodeNum(cli);
                }
            }


            nodeService.saveOrUpdate(resultSet, relay.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
