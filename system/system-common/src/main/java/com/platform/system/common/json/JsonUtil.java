package com.platform.system.common.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 输出JSON格式
 * @version: 1.0
 */
public class JsonUtil {

    /**
     * JSON序列化特性
     */
    private static final SerializerFeature[] JSON_FEATURES = {SerializerFeature.WriteMapNullValue, SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteDateUseDateFormat};

    /**
     * 输出JSON字符串
     * @param obj
     * @return
     */
    public static final String toJSONString(Object obj){
        return JSON.toJSONString(obj, JSON_FEATURES);
    }
    
    /**
     * json转对象
     * @param text
     * @param clazz
     * @return
     */
    public static final <T> T parseObject(String text,Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }
    
    /**  
     * 从json数组中解析出java字符串数组  
     * @param jsonString  
     * @return  
     */  
    public static String[] getStringArray4Json(String jsonString){   
        JSONArray jsonArray = JSONArray.parseArray(jsonString);   
        String[] stringArray = new String[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            stringArray[i] = jsonArray.getString(i);   
        }   
        return stringArray;   
    } 


    /*//**  
     * 从json数组中得到相应java数组  
     * @param jsonString  
     * @return  
     */  
    public static Object[] getObjectArray4Json(String jsonString){   
        JSONArray jsonArray = JSONArray.parseArray(jsonString);   
        return jsonArray.toArray();   
    }   

    /**   
     * 从json数组中解析出javaLong型对象数组  
     * @param jsonString  
     * @return  
     */  
    public static Long[] getLongArray4Json(String jsonString){   
        JSONArray jsonArray = JSONArray.parseArray(jsonString);   
        Long[] longArray = new Long[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            longArray[i] = jsonArray.getLong(i);   
        }   
        return longArray;   
    }   

    /**   
     * 从json数组中解析出java Integer型对象数组  
     * @param jsonString  
     * @return  
     */  
    public static Integer[] getIntegerArray4Json(String jsonString){   
        JSONArray jsonArray = JSONArray.parseArray(jsonString);   
        Integer[] integerArray = new Integer[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            integerArray[i] = jsonArray.getInteger(i);   
        }   
        return integerArray;   
    }   


    /**  
     * 从json数组中解析出java Integer型对象数组  
     * @param jsonString  
     * @return  
     */  
    public static Double[] getDoubleArray4Json(String jsonString){   
        JSONArray jsonArray = JSONArray.parseArray(jsonString);   
        Double[] doubleArray = new Double[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            doubleArray[i] = jsonArray.getDouble(i);   
        }   
        return doubleArray;   
    }   

    /**
     * String转map
     * @param jsonString
     * @return
     */
    public static Map<String, Object> StringToMap(String jsonString){  
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonString);     
            return jsonObject.getInnerMap();   
        } catch (Exception e) {
            return null;
        }   
           
    }
    
    public static Map<String, Object> objectToMap(Object obj) throws Exception {      
        if(obj == null)    
            return null;        
        Map<String, Object> map = new HashMap<String, Object>();     
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());      
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();      
        for (PropertyDescriptor property : propertyDescriptors) {      
            String key = property.getName();      
            if (key.compareToIgnoreCase("class") == 0) {     
                continue;    
            }    
            Method getter = property.getReadMethod();    
            Object value = getter!=null ? getter.invoke(obj) : null;    
            map.put(key, value);    
        }      
        return map;    
    }
    
    public static void main(String[] args) {
    	String a = "{\"message\":\"获取成功\",\"data\":{\"runningTask\":[{\"id\":1837636138024960,\"taskInfo\":null,\"businessKey\":\"com.mossle.process.bean.BzAskForLeave:1837615404990464\",\"code\":\"usertask9\",\"name\":\"重新发起申请\",\"description\":null,\"priority\":0,\"category\":null,\"form\":null,\"tenantId\":\"1\",\"status\":\"active\",\"suspendStatus\":\"none\",\"delegateStatus\":\"none\",\"completeStatus\":null,\"skipStatus\":null,\"escalateStatus\":null,\"copyStatus\":null,\"copyTaskId\":null,\"presentationName\":null,\"presentationSubject\":\"请假申请-系统管理员-2019-09-04 14:06\",\"createTime\":1567579709000,\"activationTime\":null,\"claimTime\":null,\"completeTime\":null,\"expirationTime\":1567594109000,\"lastModifiedTime\":null,\"duration\":\"null\",\"creator\":null,\"initiator\":null,\"assignee\":\"1\",\"owner\":null,\"lastModifier\":null,\"swimlane\":null,\"taskId\":\"10931271\",\"executionId\":\"10931131\",\"processInstanceId\":\"10931131\",\"processDefinitionId\":\"askForLeaveProcess:55:10922709\",\"attr1\":null,\"attr2\":null,\"attr3\":null,\"attr4\":null,\"attr5\":null,\"processBusinessKey\":null,\"processStarter\":\"1\",\"catalog\":\"normal\",\"action\":null,\"comment\":\"#FFC107\",\"message\":null,\"taskDeadlines\":[],\"taskInfos\":[],\"taskParticipants\":[],\"taskLogs\":[]},{\"id\":1602464290045952,\"taskInfo\":null,\"businessKey\":\"com.mossle.process.bean.BzInvoice:1602464287408128\",\"code\":\"usertask1\",\"name\":\"提交开票申请【项目中心】\",\"description\":null,\"priority\":0,\"category\":null,\"form\":null,\"tenantId\":\"1\",\"status\":\"active\",\"suspendStatus\":\"none\",\"delegateStatus\":\"none\",\"completeStatus\":null,\"skipStatus\":null,\"escalateStatus\":null,\"copyStatus\":null,\"copyTaskId\":null,\"presentationName\":null,\"presentationSubject\":\"客户开票申请-系统管理员-2019-03-22 11:37\",\"createTime\":1553225959000,\"activationTime\":null,\"claimTime\":null,\"completeTime\":null,\"expirationTime\":1559574991000,\"lastModifiedTime\":null,\"duration\":\"null\",\"creator\":null,\"initiator\":null,\"assignee\":\"1\",\"owner\":null,\"lastModifier\":null,\"swimlane\":null,\"taskId\":\"10595028\",\"executionId\":\"10595001\",\"processInstanceId\":\"10595001\",\"processDefinitionId\":\"client_invoice:17:9562504\",\"attr1\":null,\"attr2\":null,\"attr3\":null,\"attr4\":null,\"attr5\":null,\"processBusinessKey\":null,\"processStarter\":\"1\",\"catalog\":\"start\",\"action\":null,\"comment\":\"#FFC107\",\"message\":null,\"taskDeadlines\":[],\"taskInfos\":[],\"taskParticipants\":[],\"taskLogs\":[]},{\"id\":1580122705756160,\"taskInfo\":null,\"businessKey\":\"com.mossle.process.bean.BzInvoice:1580122702790656\",\"code\":\"usertask1\",\"name\":\"提交开票申请【项目中心】\",\"description\":null,\"priority\":0,\"category\":null,\"form\":null,\"tenantId\":\"1\",\"status\":\"active\",\"suspendStatus\":\"none\",\"delegateStatus\":\"none\",\"completeStatus\":null,\"skipStatus\":null,\"escalateStatus\":null,\"copyStatus\":null,\"copyTaskId\":null,\"presentationName\":null,\"presentationSubject\":\"客户开票申请-系统管理员-2019-03-06 16:51\",\"createTime\":1551862336000,\"activationTime\":null,\"claimTime\":null,\"completeTime\":null,\"expirationTime\":1559574990000,\"lastModifiedTime\":null,\"duration\":\"null\",\"creator\":null,\"initiator\":null,\"assignee\":\"1\",\"owner\":null,\"lastModifier\":null,\"swimlane\":null,\"taskId\":\"10550028\",\"executionId\":\"10550001\",\"processInstanceId\":\"10550001\",\"processDefinitionId\":\"client_invoice:17:9562504\",\"attr1\":null,\"attr2\":null,\"attr3\":null,\"attr4\":null,\"attr5\":null,\"processBusinessKey\":null,\"processStarter\":\"1\",\"catalog\":\"start\",\"action\":null,\"comment\":\"#FFC107\",\"message\":null,\"taskDeadlines\":[],\"taskInfos\":[],\"taskParticipants\":[],\"taskLogs\":[]},{\"id\":1513629850370048,\"taskInfo\":null,\"businessKey\":\"com.mossle.process.bean.BzBusinessPurchase:1513629352624128\",\"code\":\"usertask10\",\"name\":\"重新申请\",\"description\":null,\"priority\":0,\"category\":null,\"form\":null,\"tenantId\":\"1\",\"status\":\"active\",\"suspendStatus\":\"none\",\"delegateStatus\":\"none\",\"completeStatus\":null,\"skipStatus\":null,\"escalateStatus\":null,\"copyStatus\":null,\"copyTaskId\":null,\"presentationName\":null,\"presentationSubject\":\"资产采购申请(业务类)-系统管理员-2019-01-18 17:31\",\"createTime\":1547803935000,\"activationTime\":null,\"claimTime\":null,\"completeTime\":null,\"expirationTime\":1559574986000,\"lastModifiedTime\":null,\"duration\":\"null\",\"creator\":null,\"initiator\":null,\"assignee\":\"1\",\"owner\":null,\"lastModifier\":null,\"swimlane\":null,\"taskId\":\"10450060\",\"executionId\":\"10450021\",\"processInstanceId\":\"10450021\",\"processDefinitionId\":\"businessPurchase:1:10437508\",\"attr1\":null,\"attr2\":null,\"attr3\":null,\"attr4\":null,\"attr5\":null,\"processBusinessKey\":null,\"processStarter\":\"1\",\"catalog\":\"normal\",\"action\":null,\"comment\":\"#FFC107\",\"message\":null,\"taskDeadlines\":[],\"taskInfos\":[],\"taskParticipants\":[],\"taskLogs\":[]},{\"id\":1513626325532672,\"taskInfo\":null,\"businessKey\":\"com.mossle.process.bean.BzBusinessPurchase:1513563925921792\",\"code\":\"usertask10\",\"name\":\"重新申请\",\"description\":null,\"priority\":0,\"category\":null,\"form\":null,\"tenantId\":\"1\",\"status\":\"active\",\"suspendStatus\":\"none\",\"delegateStatus\":\"none\",\"completeStatus\":null,\"skipStatus\":null,\"escalateStatus\":null,\"copyStatus\":null,\"copyTaskId\":null,\"presentationName\":null,\"presentationSubject\":\"资产采购申请(业务类)-系统管理员-2019-01-18 16:24\",\"createTime\":1547803720000,\"activationTime\":null,\"claimTime\":null,\"completeTime\":null,\"expirationTime\":1559574986000,\"lastModifiedTime\":null,\"duration\":\"null\",\"creator\":null,\"initiator\":null,\"assignee\":\"1\",\"owner\":null,\"lastModifier\":null,\"swimlane\":null,\"taskId\":\"10450020\",\"executionId\":\"10447501\",\"processInstanceId\":\"10447501\",\"processDefinitionId\":\"businessPurchase:1:10437508\",\"attr1\":null,\"attr2\":null,\"attr3\":null,\"attr4\":null,\"attr5\":null,\"processBusinessKey\":null,\"processStarter\":\"1\",\"catalog\":\"normal\",\"action\":null,\"comment\":\"#FFC107\",\"message\":null,\"taskDeadlines\":[],\"taskInfos\":[],\"taskParticipants\":[],\"taskLogs\":[]}]},\"code\":200}";
    	Map<String, Object> t = StringToMap(a);
    	for(String key:t.keySet()){
			
    		System.out.println(key);
    		System.out.println(t.get(key));
		}
	}

}