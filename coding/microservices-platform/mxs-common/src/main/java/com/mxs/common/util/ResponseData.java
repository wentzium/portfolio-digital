package com.mxs.common.util;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData<T> implements Serializable {
    /**
     * 返回代码
     */
    private int code;
    /**
     * 返回msg
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;
    /**
     * 分页信息
     */
    private Paging paging;
    /**
     * 额外信息
     */
    private Map<Object, Object> attrMaps;

    public ResponseData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.attrMaps = getDefaultAttrMap();
    }

    private Map<Object, Object> getDefaultAttrMap() {
        Map<Object, Object> map = Maps.newHashMap();
        map.put("serverTime", System.currentTimeMillis());
        return map;
    }

    public ResponseData(int code, String msg, T data, Paging paging) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.paging = paging;
        this.attrMaps = getDefaultAttrMap();
    }

    public ResponseData(int code, String msg, T data, Map<Object, Object> attrMaps) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (MapUtils.isNotEmpty(attrMaps)) {
            attrMaps.put("serverTime", System.currentTimeMillis());
        } else {
            attrMaps = getDefaultAttrMap();
        }
        this.attrMaps = attrMaps;

    }

    public static <T> ResponseData<T> createSuccessResult() {
        return new ResponseData<T>(ResponseCode.SUCCESS.code, "", null);
    }

    public static <T> ResponseData<T> createSuccessResult(String msg) {
        return new ResponseData<T>(ResponseCode.SUCCESS.code, msg, null);
    }

    public static <T> ResponseData<T> createSuccessResult(T data) {
        return new ResponseData<T>(ResponseCode.SUCCESS.code, StringUtils.EMPTY, data);
    }

    public static <T> ResponseData<T> createSuccessResult(T data, Map<Object, Object> attrMaps) {
        return new ResponseData<T>(ResponseCode.SUCCESS.code, StringUtils.EMPTY, data, attrMaps);
    }

    public static <T> ResponseData<T> createSuccessResult(T data, Paging paging) {
        return new ResponseData<T>(ResponseCode.SUCCESS.code, StringUtils.EMPTY, data, paging);
    }

    public static <T> ResponseData<T> createFailedResult(String msg) {
        return new ResponseData<T>(ResponseCode.FAILED.code, msg, null);
    }

    public static <T> ResponseData<T> createFailedResult(T data, int code, String msg) {
        return new ResponseData<T>(code, msg, data);
    }

    public static <T> ResponseData<T> createFailedResult(int code, String msg) {
        return new ResponseData<T>(code, msg, null);
    }

    public static <T> ResponseData<T> createFailedResult(int code, String msg, Map<Object, Object> attrMaps) {
        return new ResponseData<T>(code, msg, null, attrMaps);
    }
}
