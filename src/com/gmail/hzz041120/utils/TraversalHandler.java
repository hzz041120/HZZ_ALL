package com.gmail.hzz041120.utils;

import java.io.File;
import java.util.Map;

/**
 * �����ļ�ʱ��һЩС����
 * 
 * @author zhongzhou.hanzz 2013-1-7 ����3:34:25
 */
public interface TraversalHandler {
    public boolean invoke(Map<String, String> context, File file);
}
