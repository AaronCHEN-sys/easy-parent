package com.java.tasks;

import com.java.service.FrontWebMenuService;
import freemarker.template.Configuration;
import freemarker.template.SimpleDate;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:	   每隔5分钟扫描商品详情表, 找到最近5分钟之内被修改过的商品, 并且更新对应的静态html文件<br/>
 * Date:     2021/03/17 22:01 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Component
public class Task1 {

    @Autowired
    private FrontWebMenuService frontWebMenuService;

    @Autowired
    private Configuration configuration;

    @Scheduled(cron = "0 0/1 * * * *")
    public void test1() throws IOException, TemplateException {
        List<Map<String, Object>> goodsDetailList = frontWebMenuService.findAllProductDetailUpdated();
        for (Map<String, Object> goodsDetailMap : goodsDetailList) {
            //FreeMaker取出数据后, 生成静态的HTML页面(productId.html)
            // 1.获取指定的FreeMaker模板对象
            Template template = configuration.getTemplate("Product.ftl");
            File file = new File("D:\\ProgramFiles\\freemaker\\easy_buy\\details\\" + goodsDetailMap.get("goodsId") + ".html");
            FileWriter fileWriter = new FileWriter(file);
            template.process(goodsDetailMap, fileWriter);
            fileWriter.close();
        }
    }
}
