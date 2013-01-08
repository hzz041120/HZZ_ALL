package com.gmail.hzz041120.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.gmail.hzz041120.utils.FileEncodeTransferUtil.EncodeTransferHandler;

public class FileEncodeCheckUtil {

    private static String FOLD_PATH      = "foldPath";
    private static String CURRENT_ENCODE = "currentEncode";
    private static String TARGET_ENCODE  = "targetEncode";

//    /**
//     * 判断文件的编码格式
//     */
//    public static String codeString(String fileName) {
//        BufferedInputStream bin;
//        try {
//            bin = new BufferedInputStream(new FileInputStream(fileName));
//            int p = (bin.read() << 8) + bin.read();
//            bin.close();
//            String code = null;
//            switch (p) {
//                case 0xefbb:
//                    code = "UTF-8";
//                    break;
//                case 0xfffe:
//                    code = "Unicode";
//                    break;
//                case 0xfeff:
//                    code = "UTF-16BE";
//                    break;
//                default:
//                    code = "GBK";
//            }
//            return code;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        
//    }

    public static void main(String[] args) {
        String foldPath = "/Users/hzz041120/work/cpwork/feedback/intl-gangesweb/deploy/templates";
        String curEncode = "GBK";
        String targetEncode = "UTF-8";
        Map<String, String> context = new HashMap<String, String>();
        // 文件路径
        context.put(FOLD_PATH, foldPath);
        // 源编码
        context.put(CURRENT_ENCODE, curEncode);
        // 目的编码
        context.put(TARGET_ENCODE, targetEncode);
        doTransfer(context);
    }

    /**
     * 转换指定目录下所有文件的编码
     * 
     * @param context
     */
    public static void doTransfer(Map<String, String> context) {
        EncodeTransferHandler handler = new EncodeTransferHandler();
        String foldPath = context.get(FOLD_PATH);
        traversalFile(foldPath, context, handler);
    }

    // 遍历文件, 并调用指定处理器
    private static void traversalFile(String foldPath, Map<String, String> context, EncodeTransferHandler handler) {
        File root = new File(foldPath);
        if (!root.exists()) return;
        if (root.isDirectory()) {
            String[] subFileList = root.list();
            if (subFileList == null || subFileList.length == 0) return;
            for (String subPath : subFileList) {
                traversalFile(foldPath + File.separatorChar + subPath, context, handler);
            }
        } else {
            handler.invoke(context, root);
        }

    }

    /**
     * 获取文件后缀
     * 
     * @param file
     * @return
     */
    public static String getFilenameExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        if (index == -1) return "";
        return fileName.substring(index + 1);
    }

    /**
     * 校验文件字符是否在指定的编码范围之内
     * 
     * @author zhongzhou.hanzz 2013-1-8 下午1:32:58
     */
    static class FileEncodeCheckHandler implements TraversalHandler {

        public boolean invoke(Map<String, String> context, File file) {
            String currentEncode = context.get(CURRENT_ENCODE);

            String extension = getFilenameExtension(file);
            if (extension != "" && "vm".equals(extension)) {
                System.out.println(file.getAbsolutePath());
                File targetFile = new File(file.getAbsolutePath() + ".tmp");
                try {

                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
                                                                                 currentEncode));
                    int i = 0;
                    while (true) {
                        i++;
                        String line = br.readLine();
                        if (line == null) break;
                    }
                    br.close();
                    targetFile.renameTo(file);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

    }
}
