package com.platform.user.dao.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ResourceUtils;

/**
 * mybatis反向工具，可以使用此生成所需代码。
 * @version: 1.0
 */
public class MyBatisGeneratorUtil {

    public final void testGenerator(String confFilePath) throws Exception{

        List<String> warnings = new ArrayList<String>();

        File configFile = new File(confFilePath);

        Configuration config = new ConfigurationParser(warnings).parseConfiguration(configFile);

        DefaultShellCallback callback = new DefaultShellCallback(true);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) throws FileNotFoundException{
        File file = ResourceUtils.getFile("classpath:builder/generatorConfig.xml");
        String parentPath = file.getParentFile().getParentFile().getParentFile().getParent();
        String filePath = parentPath + "\\src\\main\\resources\\builder\\generatorConfig.xml";
        try{
            new MyBatisGeneratorUtil().testGenerator(filePath);
            System.out.println("生成成功！");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}