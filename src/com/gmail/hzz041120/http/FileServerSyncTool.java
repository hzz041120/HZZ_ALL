//package com.gmail.hzz041120.http;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//public class FileServerSyncTool {
//    private static final HttpClient client = new DefaultHttpClient();
//    //http://upload.alibaba.com/download/  hz下载前缀
//    //http://172.30.96.77 杭州fileadmin
//    //http://172.20.154.75 美国fileadmin
//    //form 表单结构
//    /*
//     * <form id="uploadForm" enctype="multipart/form-data" action="/info.htm" method="post">
//     * <input type="hidden" value="allin_repository" name="volume">
//     * <input type="hidden" value="FileAction" name="action">
//     * <input type="file" name="file">
//     * <input type="text" value="" size="50" name="path">
//     * <input type="text" value="" size="50" name="name">
//     * </form>
//     */
//    public static void main(String[] args) {
//        try {
//            URI uri = new URI("http://www.baidu.com");
//            downloadFile(uri);
//        } catch (URISyntaxException e) {
//            System.err.println(e);
//        }
//    }
//    
//    public static int downloadFile(URI uri) {
//        HttpGet fileGet = new HttpGet(uri);
//        try {
//            HttpResponse response = client.execute(fileGet);
//            HttpEntity entity = response.getEntity();
//            BufferedReader contentStream = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
//            
//            while(true) {
//                
//            }
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    
//    public static boolean uploadFile(String path) {
//        return true;
//    }
//}
