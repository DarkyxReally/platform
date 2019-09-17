package com.platform.system.common.web.response.entity;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.platform.system.common.json.JsonUtil;

/**
 * 集合响应
 * @version: 1.0
 * @param <T>
 */
public class CollectionResponse<T> extends BaseResponse<List<T>> {

    private static final long serialVersionUID = 1862906172390850647L;

    public CollectionResponse(){
        super();
    }
    
    public CollectionResponse(Collection<T> dataSet) {
        super(ImmutableList.copyOf(dataSet));
    }
    
    @Override
    public String toString(){
        return JsonUtil.toJSONString(this);
    }
}
