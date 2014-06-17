package com.gmail.hzz041120.utils;

import java.io.File;

public class FileUtils {

    public static boolean deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    for (File subFile : subFiles) {
                        deleteFile(subFile);
                    }
                }
            }
            return file.delete();
        }
        return false;
    }
}
