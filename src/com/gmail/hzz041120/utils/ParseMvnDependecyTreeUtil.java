package com.gmail.hzz041120.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 解析MVN包依赖
 * 
 * @author zhongzhou.hanzz 2013-4-2 下午2:19:32
 */
public class ParseMvnDependecyTreeUtil {

    private boolean needParseFlag = false; // 是否开始解析

    public Map<String, DependencyInfo> parseDependencyPath(String path) throws IOException {
        // 执行mvn dependency:tree, 获取依赖信息
        Stack<DependencyInfo> dependencyInfoStack = new Stack<ParseMvnDependecyTreeUtil.DependencyInfo>();
        String command = "mvn dependency:tree";
        Process process = Runtime.getRuntime().exec(command, null, new File(path + "all"));
        // 解析依赖信息
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        while (true) {
            String line = br.readLine();
            if (line != null) {
                parseLine(dependencyInfoStack, line);
            } else {
                break;
            }
        }
        process.destroy();
        Map<String, DependencyInfo> dependencyInfo = new HashMap<String, ParseMvnDependecyTreeUtil.DependencyInfo>();
        // transfer stack to map;
        return dependencyInfo;
    }

    private void parseLine(Stack<DependencyInfo> dependencyInfo, String line) {
        if (!needParseFlag) {
            if (line.startsWith("[INFO] --- maven-dependency-plugin")) {
                needParseFlag = true;
                return;
            }
        } else {
            if (!line.trim().startsWith("[INFO]")) {
                needParseFlag = false;
                return;
            } else if (line.matches(":")) {

            }
        }
    }

    public static class DependencyInfo {

        private String                      fullName;      // 全称
        private String                      currentVersion;
        private String                      gid;
        private String                      aid;
        private String                      finalVersion;
        private Map<String, DependencyInfo> dependencyMap;

        /**
         * @param mvnline mvn dependecy:tree 输出的信息行
         */
        public DependencyInfo(String fullName) {

        }

        public String getCurrentVersion() {
            return currentVersion;
        }

        public void setCurrentVersion(String currentVersion) {
            this.currentVersion = currentVersion;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getFinalVersion() {
            return finalVersion;
        }

        public void setFinalVersion(String finalVersion) {
            this.finalVersion = finalVersion;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Map<String, DependencyInfo> getDependencyMap() {
            return dependencyMap;
        }

        public void setDependencyMap(Map<String, DependencyInfo> dependencyMap) {
            this.dependencyMap = dependencyMap;
        }

    }
}
