package com.platform.system.common.web;


import java.io.Serializable;
import java.util.regex.Pattern;

import com.platform.system.common.util.common.TransferCamelUtil;

/**
* <p>排序公共类</p>
* @version 1.0
**/
public class LayPageOrderBy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序类型 desc asc null
     */
    private String order;

    public String getField() {
        return field;
    }

    /**
     * @param field 需要驼峰转下划线的属性
     * @Description <p>设置需要转换的值（用于反射自动封装）</p>
     * @Version V1.0
     */
    public void setField(String field) {
        fieldNameValidate(field);
        this.setField(field, true);
    }

    /**
     * @param field 需要驼峰转下划线的属性
     * @param flag  是否需要转换的标志，true:转 false:不转
     * @Description <p>设置需要转换的值（用于手动封装）</p>
     * @Version V1.0
     */
    public void setField(String field, boolean flag) {
        fieldNameValidate(field);
		this.field = flag ? TransferCamelUtil.camel2Underline4Database(field) : field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        fieldNameValidate(order);
        this.order = order;
    }

    private static final Pattern FIELD_VALIDATE = Pattern.compile("insert|select|delete|\\*|%|truncate|;|-|\\+|\t|\r|\n|\\s");

    /**
     * sql注入校验
     * @param fieldName
     * @return
     */
    private static boolean fieldNameValidate(String fieldName) {
        fieldName = fieldName.toLowerCase();
        if (FIELD_VALIDATE.matcher(fieldName).find()) {
            throw new IllegalArgumentException("字段名有误，请检查后再尝试.字段名为：" + fieldName);
        }
        if (fieldName.length() > 30) {
            throw new IllegalArgumentException("字段长度过长，请检查后再尝试.字段名为：" + fieldName);
        }
        return true;
    }

}
