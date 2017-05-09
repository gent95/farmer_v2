package com.jctl.cloud.api.admin;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jctl.cloud.common.utils.http.FtpUploadUtil;

import sun.misc.BASE64Decoder;

/**O
 * 用户管理管理
 * Created by kay on 2016/12/8 0008.
 */

@Controller
@RequestMapping("admin")
public class AdminController extends HttpServlet {

    private List<String> imgs = Arrays.asList("png", "jpg", "jpeg");

    /**
     * 安卓图片上传(base64格式)
     * @param  file reg（"图片类型"）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Map<String, Object> upload(String file,String reg) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (reg == null || !imgs.contains(reg)) {
                resultMap.put("flag", 0);//成功
                resultMap.put("msg", "图片格式不正确！");//url
                return resultMap;
            }
            if (file == null || file.equals("")) {
                resultMap.put("flag", 0);//成功
                resultMap.put("msg", "未获取到上传文件！");//url
                return resultMap;
            }

            Map ups = FtpUploadUtil.upload(getStream(file), "png");
            if (ups.get("flag") != null && ups.get("flag").equals("1")) {
                resultMap.put("flag", 1);//成功
                resultMap.put("url", ups.get("url"));//url
                System.out.println(ups.get("url"));
            }
        } catch (Exception e) {
            resultMap.put("flag", 0);
            resultMap.put("code", 0);
            resultMap.put("msg", "操作失败");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * spring mvc 文件上传
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map ups = FtpUploadUtil.upload(file.getInputStream(), file);
            if (ups.get("flag") != null && ups.get("flag").equals("1")) {
                resultMap.put("flag", 1);//成功
                resultMap.put("url", ups.get("url"));//url

            }
        } catch (Exception e) {
            resultMap.put("flag", 0);
            resultMap.put("code", 0);
            resultMap.put("msg", "操作失败");
            e.printStackTrace();
        }
        return resultMap;
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

    public static void main(String[] args) throws Exception {


        File file11 = new File("D:/123.png");
        FileInputStream inputFile = new FileInputStream(file11);
        byte[] buffer = new byte[(int) file11.length()];
        inputFile.read(buffer);
        inputFile.close();
//        String base = new BASE64Encoder().encode(buffer);
//
//
//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] bytes1 = decoder.decodeBuffer(base);
//        InputStream stream = new ByteArrayInputStream(bytes1);

//        FtpUploadUtil.upload(stream, "png");
    }

}
