package com.jctl.cloud.api.realy;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LewKay on 2017/4/5.
 */

@Controller
@RequestMapping("relay")
public class ARelayController {


    @Autowired
    private RelayService relayService;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private SystemService systemService;
    @Autowired
    private FarmerService farmerService;


    @RequestMapping("list")
    @ResponseBody
    public Map list(String userId,Relay relay) {
        Map result = Maps.newHashMap();
        List list = new ArrayList();
        try {
            User user = systemService.getUser(userId);
            if (user != null) {
                boolean AdminUser = User.isAdmin(userId);
                if (!AdminUser) {
                    List<Role> rolse = user.getRoleList();
                    for (Role ro : rolse) {
                        if (ro.getEnname().equals("farmerBoss")) {
                            relay.setUser(user);
                        }
                    }
                }
                relay.setUser(user);
                List<Relay> lists = relayService.findListByUser(relay);
                List<Relay> relays = new ArrayList<Relay>();
                if(lists!=null){
                    for (Relay relayTemp :lists) {
                        Integer num = nodeService.getNodeNum(relayTemp.getId());
                        if (num != null)
                            relayTemp.setNodeNum(num);
                        relays.add(relayTemp);
                    }

                }
                String[] propertys = new String[]{"id", "name","relayNum", "farmerName", "nodeNum", "powerSupply"};
                if (relays != null && relays.size() > 0) {
                    for (Relay relay1 : relays) {
                        System.out.print(relay1);
                        Map map = new HashMap();
                        for (String property : propertys) {
                            map.put(property, Reflections.invokeGetter(relay1, property));
                        }
                        list.add(map);
                    }
                    result.put("flag", 1);
                    result.put("info", list);
                } else {
                    result.put("flag",0);
                    result.put("msg", "没有查询到中继信息!");
                }
            }
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("info")
    @ResponseBody
    public Map info(Relay relay) {
        Map result = new HashMap();
        List infoList=new ArrayList();
        try {
            Relay relay1 = relayService.get(relay);
            relay1.setNodeNum(nodeService.getNodeNum(relay1.getId()));
            if(relay1.getUser()!=null){
                Farmer farmer=new Farmer();
                System.out.println(relay1.getUser().getId()+";"+relay1.getUser().getName());
                farmer.setUser(relay1.getUser());
                farmer.setDelFlag("1");
                List<Farmer> farmers=farmerService.findList(farmer);
               String[] poper=new String[]{"id","name"};
               for(Farmer fa:farmers){
                   Map message=Maps.newHashMap();
                   for(String pr:poper){
                       message.put(pr,Reflections.invokeGetter(fa,pr));
                   }
                   infoList.add(message);
               }
            }
            result.put("info",infoList);
            result.put("flag", 1);
            result.put("data", relay1);

        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("save")
    @ResponseBody
    public Map save(Relay relay){
        Map result = new HashedMap();
        try{
            if (relay != null){
                if(relay.getId()!=null&&relay.getId()!=""){
                    Farmer farmer=farmerService.get(relay.getFarmerId().toString());
                    if(farmer!=null&&farmer.getUsedId()!=null&&farmer.getUsedId()!="") {
                        User user = systemService.getUser(farmer.getUsedId());
                        relay.setUser(user);
                        relay.setFarmer(farmer);
                    }
                }
                relayService.save(relay);
                result.put("flag",1);
                result.put("msg","保存成功!");
            }else{
                result.put("flag",0);
                result.put("msg","信息不能为空!");
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","操作异常!");
        }finally {
            return  result;
        }

    }

}
