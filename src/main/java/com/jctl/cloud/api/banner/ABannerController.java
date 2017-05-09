package com.jctl.cloud.api.banner;

import com.jctl.cloud.app.banner.entity.AppBanner;
import com.jctl.cloud.app.banner.service.AppBannerService;
import com.jctl.cloud.common.utils.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KAY on 2017/4/21.
 */

@Controller
@RequestMapping("banner")
public class ABannerController {


    @Autowired
    private AppBannerService appBannerService;

    @RequestMapping("")
    @ResponseBody
    public Map list() {
        Map result = new HashMap();
        List data = new ArrayList();
        try {
            List<AppBanner> list = appBannerService.findList(new AppBanner());
            String[] propertys = new String[]{"path", "url"};

            for (AppBanner banner : list) {
                Map map = new HashMap();
                for (String property : propertys) {
                    map.put(property, Reflections.invokeGetter(banner, property));
                }
                data.add(map);
            }
            result.put("flag", 1);
            result.put("info", data);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
        }
        return result;
    }
}
