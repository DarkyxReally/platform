package com.platform.system.common.util;

/**
 * 运营商工具类
 * @version: 1.0
 */
public class OperatorUtils {

    /**
     * 运营商位置值0
     */
    public static final String SUPPORT_INDEX_VAL_0 = "0";
    /**
     * 运营商位置值1
     */
    public static final String SUPPORT_INDEX_VAL_1 = "1";
    /**
     * 运营商类型 zgyd：中国移动
     */
    public static final String OPERATOR_ZGYD = "zgyd";
    /**
     * 运营商类型 zglt：中国联通
     */
    public static final String OPERATOR_ZGLT = "zglt";
    /**
     * 运营商类型 zgdx：中国电信
     */
    public static final String OPERATOR_ZGDX = "zgdx";
    /**
     * 活动支持运营商校验
     * @param activitySup 活动支持的运营商，确保3位
     * @param operatorType 参与者手机运营商
     * @return
     */
    public static boolean isSupportOperator(String activitySup,String operatorType){
        String index0 = activitySup.substring(0,1);
        String index1 = activitySup.substring(1,2);
        String index2 = activitySup.substring(2,3);
        //中国移动
        if(OPERATOR_ZGYD.equals(operatorType)){
            if(SUPPORT_INDEX_VAL_0.equals(index0)){
                return false;
            }else if(SUPPORT_INDEX_VAL_1.equals(index0)){
                return true;
            }
        }
        //中国联通
        if(OPERATOR_ZGLT.equals(operatorType)){
            if(SUPPORT_INDEX_VAL_0.equals(index1)){
                return false;
            }else if(SUPPORT_INDEX_VAL_1.equals(index1)){
                return true;
            }
        }
        //中国电信
        if(OPERATOR_ZGDX.equals(operatorType)){
            if(SUPPORT_INDEX_VAL_0.equals(index2)){
                return false;
            }else if(SUPPORT_INDEX_VAL_1.equals(index2)){
                return true;
            }
        }
        return false;
    }
}
