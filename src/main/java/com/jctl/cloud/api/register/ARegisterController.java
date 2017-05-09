package com.jctl.cloud.api.register;

import com.google.common.collect.Maps;
import com.jctl.cloud.modules.sys.entity.Area;
import com.jctl.cloud.modules.sys.entity.Office;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.OfficeService;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.sms.HttpsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LewKAY on 2017/3/23.
 */

@Controller
@RequestMapping("register")
public class ARegisterController {


    @Autowired
    private OfficeService officeService;


    @RequestMapping("")
    @ResponseBody
    public Map<String, String> register(User user) {
        Map result = new HashMap();
        try {
            if (user.getVerCode() == null || !user.getVerCode().equals("123456")) {
                result.put("flag", "0");
                result.put("msg", "验证码不正确！");
                return result;
            }
            String password = user.getPassword();
            //创建赋予角色
            String res = officeService.doRegister(user);
            if (res.equals("0")) {
                result.put("flag", "0");
                result.put("msg", "该手机已被注册！");
                return result;
            }

            result.put("flag", 1);
            result.put("loginName", user.getLoginName());
            result.put("password", password);
            result.put("msg", "注册成功！");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 发送验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/sendSmsCode")
    @ResponseBody
    public Map sendSmsCode(String mobile, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            HttpsRequest req = new HttpsRequest();
            req.sendSms("POST", request.getSession(), request, mobile);

            result.put("flag", 1);
            result.put("code", 123456);

            request.getSession().setAttribute(mobile, "123456");
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }

        return result;
    }


}
