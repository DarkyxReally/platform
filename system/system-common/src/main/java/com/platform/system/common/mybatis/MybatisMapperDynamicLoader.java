package com.platform.system.common.mybatis;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 动态加载修改后的mapper
 * 注意仅在开发环境时运行,否则可能影响性能
 * @version: 1.0
 */
@Slf4j
public class MybatisMapperDynamicLoader implements InitializingBean, ApplicationContextAware {

    private final HashMap<String, String> mappers = new HashMap<String, String>();
    private volatile ConfigurableApplicationContext context = null;
    private volatile Scanner scanner = null;
    
    private String mapperLocations;
    
    public String getMapperLocations(){
        return mapperLocations;
    }

    
    public void setMapperLocations(String mapperLocations){
        this.mapperLocations = mapperLocations;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.context = (ConfigurableApplicationContext)applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception{
        try{
            scanner = new Scanner(mapperLocations);
            new Timer(true).schedule(new TimerTask(){
                @Override
                public void run(){
                    try{
                        if(scanner.isChanged()){
                            System.out.println("Mapper.xml有改动,重新加载");
                            scanner.reloadXML();
                        }
                    }catch(Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
            }, 10 * 1000, 5 * 1000);
        }catch(Exception e1){
            e1.printStackTrace();
        }
    }

    class Scanner {
        
        private String locations;
        
        public String getLocations(){
            return locations;
        }
        
        public void setLocations(String locations){
            this.locations = locations;
        }
        
        private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        public Scanner(String locations) throws IOException {
            this.locations = locations;
            Resource[] resources = findResource();
            if(resources != null){
                for(Resource resource : resources){
                    String key = resource.getURI().toString();
                    String value = getMd(resource);
                    mappers.put(key, value);
                }
            }
        }

        public void reloadXML() throws Exception{
            SqlSessionFactory factory = context.getBean(SqlSessionFactory.class);
            Configuration configuration = factory.getConfiguration();
            //不清除所有, 只修改改变的文件
//            removeConfig(configuration);
            for(Resource resource : findResource()){
                String key = resource.getURI().toString();
                String value = getMd(resource);
                if(!value.equals(mappers.get(key))){
                    try{
                        System.out.println("mybatis Mapper重新加载" + resource.getURL().toString());
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(), configuration,
                                resource.toString(), configuration.getSqlFragments());
                        xmlMapperBuilder.parse();
                    }finally{
                        mappers.put(key, value);
                        ErrorContext.instance().reset();
                    }
                }
            }
        }

        /**
         * 清除所有
         * @param configuration
         * @throws Exception
         */
        protected void removeConfig(Configuration configuration) throws Exception{
            Class<?> classConfig = configuration.getClass();
            clearMap(classConfig, configuration, "mappedStatements");
            clearMap(classConfig, configuration, "caches");
            clearMap(classConfig, configuration, "resultMaps");
            clearMap(classConfig, configuration, "parameterMaps");
            clearMap(classConfig, configuration, "keyGenerators");
            clearMap(classConfig, configuration, "sqlFragments");
            clearSet(classConfig, configuration, "loadedResources");
        }

        @SuppressWarnings("rawtypes")
        private void clearMap(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception{
            Field field = classConfig.getDeclaredField(fieldName);
            field.setAccessible(true);
            ((Map)field.get(configuration)).clear();
        }

        @SuppressWarnings("rawtypes")
        private void clearSet(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception{
            Field field = classConfig.getDeclaredField(fieldName);
            field.setAccessible(true);
            ((Set)field.get(configuration)).clear();
        }

        public boolean isChanged() throws IOException{
            boolean isChanged = false;
            for(Resource resource : findResource()){
                String key = resource.getURI().toString();
                String value = getMd(resource);
                if(!value.equals(mappers.get(key))){
                    isChanged = true;
                }
            }
            return isChanged;
        }

        private Resource[] findResource() throws IOException{
            return resourcePatternResolver.getResources(locations);
        }

        private String getMd(Resource resource) throws IOException{
            return new StringBuilder().append(resource.contentLength()).append("-").append(resource.lastModified())
                    .toString();
        }
    }
}