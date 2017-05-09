package com.jctl.cloud.api.farmer;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */
@Controller
@RequestMapping("farmer")
public class AFarmerController {
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private RelayService relayService;

    @RequestMapping("list")
    @ResponseBody
    public Map listFarmer(String userId, Farmer farmer) {
        Map result = Maps.newHashMap();
        List list = new ArrayList();
        try {
            User user = systemService.getUser(userId);
            if (user != null) {
                boolean AdminUser = User.isAdmin(userId);
                if (!AdminUser) {
                    List<Role> rolse = UserUtils.getRoleList();
                    for (Role ro : rolse) {
                        if (ro.getEnname().equals("farmerBoss")) {
                            farmer.setUser(user);
                        }
                        if (ro.getEnname().equals("farmerWorker")) {
                            farmer.setUser(user);
                        }
                    }
                }
                List<Farmer> lists = farmerService.findList(farmer);
                List<Farmer> listFarmer = new ArrayList<Farmer>();
                Integer relayNum;
                Integer farmerlandNum;
                Relay relay;
                for (Farmer fa : lists) {
                    relayNum = relayService.findRelayNumByFarmerId(fa.getId());
                     relay= relayService.getByFamerId(fa.getId());
                     if(relay!=null||!"".equals(relay)) {
                         fa.setLng(relay.getLog());
                         fa.setLat(relay.getLat());
                     }
                    farmerlandNum = farmlandService.findFarmerlandNumByFarmerId(fa.getId());
                    fa.setRelayNumber(relayNum);
                    fa.setFarmlandNumber(farmerlandNum);
                    listFarmer.add(fa);
                }
                String[] propertys = new String[]{"id", "name", "addr", "farmlandNumber", "plantVariety","lng","lat"};
                if (listFarmer != null && listFarmer.size() > 0) {
                    for (Farmer fa : listFarmer) {
                        Map map = new HashMap();
                        for (String property : propertys) {
                            map.put(property, Reflections.invokeGetter(fa, property));
                        }
                        list.add(map);
                    }

                    result.put("flag", 1);
                    result.put("info", list);
                } else {
                    result.put("flag", 0);
                    result.put("msg", "没有查询到农场信息!");
                }
            }

        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Map saveOrUpdate(String userId, Farmer farmer) {
        Map result = Maps.newHashMap();
        try {
            User user = systemService.getUser(userId);
            if (user == null) {
                result.put("flag", "0");
                result.put("msg", "账号未登陆！");
                return result;
            }

            farmer.setUser(user);
            farmerService.save(farmer);
            result.put("msg", "操作成功");
            result.put("flag", "1");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "添加失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map deleteFarmer(Farmer farmer) {
        Map result = Maps.newHashMap();
        try {
            farmerService.delete(farmer);
            result.put("flag", "1");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "删除失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public Map getFarmer(String id) {
        Map result = Maps.newHashMap();
        List list = new ArrayList();
        try {
            Farmer farmer = farmerService.get(id);
            String[] propert = new String[]{"id", "name", "farmerNum", "addr", "area", "plantVariety", "user.name","user.id", "farmlandNumber", "relayNumber","remarks"};
            Map maps = Maps.newHashMap();
            for (String pro : propert) {
                maps.put(pro, Reflections.invokeGetter(farmer, pro));
            }
            list.add(maps);
         /*   Farmland farmland = new Farmland();
            List data = new ArrayList();
            farmland.setFarmer(farmer);
            List<Farmland> farmlandList = farmlandService.findList(farmland);
          String[] propert2 = new String[]{"id", "alias", "plantVaritety","usedId","usedName","assigneTime","area","addr","nodeNumber","farmlandNum","landType","user","relay","remarks"};
            for (Farmland fa : farmlandList) {
                Map message = Maps.newHashMap();
                for (String po : propert2) {
                    message.put(po, Reflections.invokeGetter(fa, po));
                }
                data.add(message);
            }
            result.put("data", data);*/
            result.put("info", list);
            result.put("flag", "1");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
        }
        return result;
    }
}
