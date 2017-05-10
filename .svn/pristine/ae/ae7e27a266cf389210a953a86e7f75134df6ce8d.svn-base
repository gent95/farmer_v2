package com.jctl.cloud.api.farmland;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.Office;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.OfficeService;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */
@Controller
@RequestMapping("farmland")
public class AFarmlandController {
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private NodeService nodeService;


    @RequestMapping("list")
    @ResponseBody
    public Map listFarmland(String farmerId, Farmland farmland) {
        Map result = Maps.newHashMap();
        try {
               farmland.setFarmer(farmerService.get(farmerId));
                List<Farmland> farmlandList = farmlandService.findList(farmland);
                  List<Farmland> lists = new ArrayList<Farmland>();
            Integer nodeNumber;
            for (Farmland fa : farmlandList) {
                nodeNumber = nodeService.getNodeNumberByFarmlandId(fa.getId());
                fa.setNodeNumber(nodeNumber);
                lists.add(fa);
            }
                String[] propert = new String[]{"id", "alias", "area", "plantVaritety", "nodeNumber"};
                if (lists != null||lists.size()>0) {
                    List messages = new ArrayList();
                    for (Farmland fa : lists) {
                        Map message = Maps.newHashMap();
                        for (String property : propert) {
                            message.put(property, Reflections.invokeGetter(fa, property));
                        }
                        messages.add(message);
                    }
                    result.put("flag", "1");
                    result.put("info", messages);
                    result.put("farmerId",farmerId);
                } else {
                    result.put("flag", "0");
                    result.put("msg", "抱歉，没有到农田信息!");
                    return result;
                }

        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Map saveFarmland(String userId,String farmerId,Farmland farmland) {
        Map result = Maps.newHashMap();
        try {
            User user = systemService.getUser(userId);
            if (user == null) {
                result.put("flag", "0");
                result.put("msg", "账号未登陆！");
                return result;
            }
            farmland.setFarmer(farmerService.get(farmerId));
            farmland.setUser(user);
            farmlandService.save(farmland);
            result.put("flag", "1");
            result.put("msg", "操作成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map delteFarmland(Farmland farmland) {
        Map result = Maps.newHashMap();
        try {
            farmlandService.delete(farmland);
            result.put("flag", "1");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "删除失败");
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public Map getFarmland(String id,String userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map result = Maps.newHashMap();
        try {
            Farmland farmland = farmlandService.get(id);
            String[] propert = new String[]{"id", "alias", "plantVaritety", "nodeNumber", "usedId", "usedName", "assigneTime", "area", "addr", "nodeNumber", "farmlandNum", "landType", "remarks","",""};

            if(farmland.getUser()!=null&&farmland.getUser().getId()!=null&&farmland.getUser().getId()!=""){
                propert[13]="user";
            }
            if(farmland.getRelay()!=null&&farmland.getRelay().getId()!=null&&farmland.getRelay().getId()!=""){
                propert[14]="relay";
            }
            List messages = new ArrayList();
            Map message = Maps.newHashMap();
            List lisq=new ArrayList();
            for (String pro : propert) {
                if (pro == "assigneTime") {
                    if (farmland.getAssigneTime() == null || farmland.getAssigneTime().equals("")) {
                        message.put("assigneTime", null);
                    } else {
                        message.put("assigneTime", sdf.format(farmland.getAssigneTime()));
                    }
                } else  if(pro=="area"){
                    if(farmland.getArea()==null){
                        message.put("area","0.00");
                    }else{
                        message.put("area",farmland.getArea());
                    }
                }else{
                message.put(pro, Reflections.invokeGetter(farmland, pro));
                 }
    }
            Farmer farmer=new Farmer();
            User user=systemService.getUser(userId);
            farmer.setUser(user);
            List<Farmer> farmers=farmerService.findList(farmer);
            List data=new ArrayList();
            String[] property1=new String[]{"id","name"};
            for(Farmer farmer1:farmers){
                Map dataMap=Maps.newHashMap();
                for(String pp:property1){
                    dataMap.put(pp,Reflections.invokeGetter(farmer1,pp));
                }
                data.add(dataMap);
            }
            List userList=new ArrayList();
            List<User> listUser=systemService.findUserByComplayIdAndUserId(user);
            String[] property2=new String[]{"id","name"};
            if(listUser!=null&&listUser.size()>0) {
                for (User of : listUser) {
                    Map offMaps = Maps.newHashMap();
                    for (String pr2 : property2) {
                        offMaps.put(pr2, Reflections.invokeGetter(of, pr2));
                    }
                    userList.add(offMaps);
                }
            }
            messages.add(message);
            result.put("flag", "1");
            result.put("farmerList",data);
            result.put("userList",userList);
            result.put("info", messages);
        }catch (Exception e){
            result.put("flag", "0");
            result.put("msg","操作失败");
        }
        return result;
    }
    @RequestMapping("getUser")
    @ResponseBody
    public Map getUser(String userId) {
        Map result=Maps.newHashMap();
        try {
            List userList = new ArrayList();
            List<User> listUser = systemService.findUserByComplayIdAndUserId(UserUtils.get(userId));
            if (listUser.size() > 0) {
                String[] property2 = new String[]{"id", "name"};
                for (User of : listUser) {
                    Map offMaps = Maps.newHashMap();
                    for (String pr2 : property2) {
                        offMaps.put(pr2, Reflections.invokeGetter(of, pr2));
                    }
                    userList.add(offMaps);
                }
                result.put("flag", "1");
                result.put("info", userList);
            } else {
                result.put("flag", "0");
                result.put("msg", "公司下无任何人员");
            }
        }catch (Exception e){
            result.put("flag","0");
            result.put("msg","操作失败");
        }
        return result;
    }
}
