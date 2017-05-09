package com.jctl.cloud.api.login;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.entity.LoginResult;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.FrontUserUtils;
import com.jctl.cloud.utils.baidupush.PushMsgToSingleDevice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Lew on 2017/3/23.
 */

@Controller
@RequestMapping("login")
public class ALoginController {

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "doLogin")
    @ResponseBody
    public Map doLogin(User user, HttpServletRequest request) {

        Map result = Maps.newHashMap();
        try {
            LoginResult flag = FrontUserUtils.doLogin(user,request);
         if(flag == LoginResult.登录成功){
             result.put("flag",1);
             String [] propertys = new String[]{"name","id","password","loginName","photo"};
             Map<String,Object> map = new HashMap<>();
             for (String property:propertys) {
                 if(property.equals("password")){
                     map.put(property,user.getPassword());continue;
                 }
                map.put(property, Reflections.invokeGetter(FrontUserUtils.getUser(),property));
             }
             result.put("user",map);
             return result;
         }else if(flag == LoginResult.用户被锁定){
             result.put("flag",0);
             result.put("msg","用户被锁定，请联系管理员！");
             return result;
         }else if(flag == LoginResult.登录失败){
             result.put("flag",0);
             result.put("msg","账号或密码错误！");
             return result;
         }
        } catch (Exception e) {
            result.put("flag",0);
            result.put("msg","操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用户登出
     * @return
     */
    @RequestMapping(value = "loginOut")
    @ResponseBody
    public Map loginOut(HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            result.put("flag",1);
            result.put("msg","退出成功！");
            request.getSession().setAttribute(request.getSession().getId(),null);
        } catch (Exception e) {
            result.put("flag",0);
            result.put("msg","操作失败！");
            e.printStackTrace();
        }
        return result;
    }



}
