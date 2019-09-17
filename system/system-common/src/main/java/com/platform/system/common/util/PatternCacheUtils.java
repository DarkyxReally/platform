package com.platform.system.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
* <p>Pattern缓存</p>
* @version 1.0
**/
public class PatternCacheUtils {
    private static final Cache<String, Pattern> PATTERN_CACHE = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).maximumSize(1_000).build();

    public static Pattern getPattern(String pattern, int flag) {
        try {
            return PATTERN_CACHE.get(pattern, () -> Pattern.compile(pattern, flag));
        } catch (ExecutionException e) {
            String msg = "Pattern:" + pattern + ";Flag:" + flag;
            throw new IllegalArgumentException(msg, e);
        }
    }

    public static Pattern getPattern(String pattern) {
        return getPattern(pattern, 0);
    }
}
