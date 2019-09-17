package com.platform.system.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.exception.IllegalValidateException;
import com.platform.system.common.rest.RestStatus;
import com.platform.system.common.web.ServletContextHolder;
import com.platform.system.common.web.response.entity.ErrorResponse;

/**
 * hibernate校验工具
 * @version: 1.0
 */
public final class HibernateValidators {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateValidators.class);

    private static final Validator VALIDATOR;

    private HibernateValidators() {}

    static{
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    /**
     * 普通校验
     * @param object
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object){
        return VALIDATOR.validate(object);
    }

    /**
     * 使用groups校验
     * @param object
     * @param groups
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups){
        return VALIDATOR.validate(object, groups);
    }

    /**
     * 当验证不通过时,返回错误信息map, 通过时,返回null
     * @param object
     * @return
     */
    public static <T> Map<String, String> toErrorMapIfInvalid(T object){
        final Set<ConstraintViolation<T>> constraintViolations = validate(object);
        if(CollectionUtils.isNotEmpty(constraintViolations)){
            // 验证不通过
            return toErrorMap(constraintViolations);
        }
        return null;
    }
    
    /**
     * 当验证不通过时,返回错误信息map, 通过时,返回null
     * @param object
     * @param groups
     * @return
     */
    public static <T> Map<String, String> toErrorMapIfInvalid(T object, Class<?>... groups){
        final Set<ConstraintViolation<T>> constraintViolations = validate(object, groups);
        if(CollectionUtils.isNotEmpty(constraintViolations)){
            // 验证不通过
            return toErrorMap(constraintViolations);
        }
        return null;
    }

    /**
     * @throws IllegalValidateException 当校验有错误的时候抛出异常
     * @param object
     */
    public static <T> void throwsIfInvalid(T object){
        final Set<ConstraintViolation<T>> constraintViolations = validate(object);
        if(CollectionUtils.isNotEmpty(constraintViolations)){
            Map<String, String> errorMap = toErrorMap(constraintViolations);
            final ErrorResponse<?> entity = new ErrorResponse<>(StatusCode.INVALID_MODEL_FIELDS, errorMap);
            // 以entity中的code为key存入Request中
            final String errorCode = String.valueOf(StatusCode.INVALID_MODEL_FIELDS.code());
            bindStatusCodesInRequestScope(errorCode, entity);
            throw new IllegalValidateException(errorCode);
        }
    }

    /**
     * 验证结果的错误信息MAP
     * @param constraintViolations
     * @return
     */
    public static <T> Map<String, String> toErrorMap(Set<ConstraintViolation<T>> constraintViolations){
        final Map<String, String> errorMap = Maps.newHashMap();
        for(ConstraintViolation<T> violation : constraintViolations){
//            errorBuilder.put(violation.getPropertyPath().toString(), violation.getMessage());
            errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
//        return errorBuilder.build();
        return errorMap;
    }

    /**
     * @throws IllegalValidateException 当校验有错误的时候抛出异常
     * @param object
     * @param groups
     */
    public static <T> void throwsIfInvalid(T object, Class<?>... groups){
        final Set<ConstraintViolation<T>> constraintViolations = validate(object, groups);
        if(CollectionUtils.isNotEmpty(constraintViolations)){
            Map<String, String> errorMap = toErrorMap(constraintViolations);
            final ErrorResponse<?> entity = new ErrorResponse<>(StatusCode.INVALID_MODEL_FIELDS, errorMap);
            // 以entity中的code为key存入Request中
            final String errorCode = String.valueOf(StatusCode.INVALID_MODEL_FIELDS.code());
            bindStatusCodesInRequestScope(errorCode, entity);
            throw new IllegalValidateException(errorCode);
        }
    }

    /**
     * 校验实体合法性, 自动向Map封装错误信息.
     *
     * @param result Spring MVC中与@Valid成对出现的BindingResult, 用于绑定错误信息
     * @throws IllegalValidateException 实体校验失败异常
     * @see org.springframework.web.bind.annotation.ControllerAdvice
     */
    public static void throwIfInvalidModel(BindingResult result, RestStatus errorStatus){
        Preconditions.checkNotNull(result);
        // 默认为true, 检测到错误时赋值为false
        boolean isValid = true;
        final HashMap<Object, Object> errorMap = Maps.newHashMap();
        if(result.getErrorCount() > 0){
            isValid = false;
            String errorFieldName;
            for(FieldError fieldError : result.getFieldErrors()){
                errorFieldName = acquireFieldName(result, fieldError);
                final String errorMessage = fieldError.getDefaultMessage();
                LOGGER.debug("request id: {}, error field: {}, error msg: {}", ServletContextHolder.fetchRequestId(), errorFieldName, errorMessage);
                errorMap.put(errorFieldName, errorMessage);
            }
        }
        if(!isValid){
            final ErrorResponse<?> entity = new ErrorResponse<>(errorStatus, errorMap);
            // 以entity中的code为key存入Request中
            final String errorCode = String.valueOf(errorStatus.code());
            HibernateValidators.bindStatusCodesInRequestScope(errorCode, entity);
            throw new IllegalValidateException(errorCode);
        }
    }

    /**
     * 获取错误的字段名, 如果被{@link JSONField}或{@link JsonProperty} 修饰, 则优先选择修饰的字段名  JSONField优先于JsonProperty
     *
     * @see JSONField
     * @see JsonProperty
     */
    private static String acquireFieldName(BindingResult result, FieldError fieldError){
        Preconditions.checkNotNull(result);
        Preconditions.checkNotNull(fieldError);
        // 获取错误字段名
        String errorFieldName = fieldError.getField();
        // 获取校验非法的类
        Class<?> clazz = result.getTarget().getClass();
        Field field;
        for(Class<?> cz = clazz; cz != Object.class; cz = cz.getSuperclass()){
            try{
                // 获取其字段名
                field = cz.getDeclaredField(fieldError.getField());
                final JSONField jsonField = field.getAnnotation(JSONField.class);
                // 若JsonProperty里value()不为null则覆盖该值
                if(jsonField != null && StringUtils.isNotBlank(jsonField.name())){
                    errorFieldName = jsonField.name();
                    break;
                }
            }catch(NoSuchFieldException e){
                // 忽略
                continue;
            }
        }
        return errorFieldName;
    }

    /**
     * 绑定状态码 
     * @param key
     * @param entity
     */
    private static void bindStatusCodesInRequestScope(String key, ErrorResponse<?> entity){
        Preconditions.checkNotNull(entity);
        Preconditions.checkNotNull(key);
        HttpServletRequest request = ServletContextHolder.getRequest();
        if(request != null){
            request.setAttribute(key, entity);
        }
    }
}
