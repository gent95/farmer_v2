package com.jctl.cloud.api.user;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.common.utils.http.FtpUploadUtil;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by 刘凯 on 2017/3/24.
 */
@Controller
@RequestMapping("user")
public class AUserServiceController {


    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;


    private List<String> imgs = Arrays.asList("png", "jpg", "jpeg");

    /**
     * 更新个人信息
     *
     * @param user
     * @return
     */
    @RequestMapping("update")
    public Map update(User user) {
        Map result = Maps.newHashMap();
        try {
            userService.save(user);
            result.put("flag", 1);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取农场下的农户列表
     *
     * @return
     */
    @RequestMapping("farmers")
    @ResponseBody
    public Map getFarmers(HttpServletRequest request, HttpServletResponse response) {
        Map result = Maps.newHashMap();
        List<Map<String, String>> data = new ArrayList<>();
        try {
            User user = FrontUserUtils.getUser();
//            List<User> users  = systemService.findUser(new Page<User>(request, response), user).getList();
            List<User> users = userService.getByCompany(user);
            users.remove(user);

            String[] propertys = new String[]{"id", "name", "phone"};

            for (User user1 : users) {
                Map map = new HashMap();
                for (String property : propertys) {
                    map.put(property, Reflections.invokeGetter(user1, property));
                }
                data.add(map);
            }

            result.put("flag", 1);
            result.put("data", data);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("updateIcon")
    @ResponseBody
    public Map<String, Object> updateIcon(User user, String file, String reg) {
        Map result = Maps.newHashMap();
        try {

            if (reg == null || !imgs.contains(reg)) {
                result.put("flag", 0);//成功
                result.put("msg", "图片格式不正确！");//url
                return result;
            }
            if (file == null || file.equals("")) {
                result.put("flag", 0);//成功
                result.put("msg", "未获取到上传文件！");//url
                return result;
            }

            Map ups = FtpUploadUtil.upload(getStream(file), reg);
            if (ups.get("flag") != null && ups.get("flag").equals("1")) {
                user.setPhoto((String) ups.get("url"));
                userService.update(user);
                result.put("flag", 1);//成功
                result.put("url", ups.get("url"));//url
            }
            result.put("flag", 1);
            result.put("msg", "操作成功");
        }catch (Exception e){
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
            e.printStackTrace();
        }
        return result;
    }



    private InputStream getStream(String base) throws IOException {
        if (base == null)
            return null;
        byte[] bytes = new BASE64Decoder().decodeBuffer(base);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        return new ByteArrayInputStream(bytes);
    }


}
