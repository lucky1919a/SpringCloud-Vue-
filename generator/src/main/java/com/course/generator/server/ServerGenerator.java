package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    public static void main(String[] args)  {
        try{
            String Domain ="Section";
            String domain ="section";
            Map<String, Object> map = new HashMap<>();
            map.put("Domain", Domain);
            map.put("domain", domain);

           /* map.put("tableNameCn", tableNameCn);
            map.put("module", module);
            map.put("fieldList", fieldList);
            map.put("typeSet", typeSet);*/
            // 生成service
            FreemarkerUtil.initConfig("service.ftl");
            FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);
            System.out.println("生成成功");
        }catch (Exception e){
            System.out.println("生成错误");
        }

    }
}
