package com.gmail.hzz041120.utils;

import java.io.File;
import java.util.Map;

/**
 * 遍历文件时做一些小动作
 * 
 * @author zhongzhou.hanzz 2013-1-7 下午3:34:25
 */
public interface TraversalHandler {
    public boolean invoke(Map<String, String> context, File file);
}
