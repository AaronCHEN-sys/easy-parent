package com.java.controller;

import com.yuqing.common.FastDFSClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Description:	   <br/>
 * Date:     2020/12/22 15:47 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
@RequestMapping("uploadFile")
public class UploadFileController {

    /**
     * 文件上传
     *
     * @param uploadFile
     * @throws IOException
     */
    @RequestMapping("/uploadFile.do")
    public void uploadFile(MultipartFile uploadFile) {
        try {
            //获取上传文件的原始名称
            String originalFilename = uploadFile.getOriginalFilename();
            //根据originalFilename获取文件类型名称
            String fileTypeName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //动态随机生成文件名
            String uuid = UUID.randomUUID().toString();
            //动态生成文件目录结构
            String fileDirectoryStructure = new SimpleDateFormat("yyyyMMdd\\HH\\mm\\ss").format(new Date());
            //拼接文件上传的最终结构
            String filePath = "C:\\Users\\Aaron\\Desktop\\uploads\\" + fileDirectoryStructure;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            uploadFile.transferTo(new File(filePath + "\\" + uuid + fileTypeName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过fastDFS文件上传
     *
     * @param uploadFile
     * @return
     */
    @RequestMapping("/uploadByFastDFS.do")
    @ResponseBody
    public Map<String, Object> uploadByFastDFS(MultipartFile uploadFile) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:/resources/fdfs_client.conf");
            //获取文件的原始名称
            String originalFilename = uploadFile.getOriginalFilename();
            //获取文件类型
            String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //将文件保存到fastDFS服务器,并且返回相对地址
            String basePath = fastDFSClient.uploadFile(uploadFile.getBytes(), fileType);
            basePath = "http://192.168.25.133/" + basePath;
            resultMap.put("error", 0);//0代表上传成功
            resultMap.put("url", basePath);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("error", 1);//1代表上传失败
            return resultMap;
        }
    }
}
