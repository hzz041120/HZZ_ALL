package com.gmail.hzz041120.http;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientTest {

    public static void main(String[] args) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet fileGet = new HttpGet(
                                      "http://10.20.146.11:8780/api/param2/1/alibaba.open/margin.downloadAttach/0?loginId=hzzcgs");
        HttpResponse res = client.execute(fileGet);
        HttpEntity entity = res.getEntity();
        InputStream is = entity.getContent();
        FileOutputStream fos = new FileOutputStream("/Users/hzz041120/Desktop/filetest/test.zip");
        while (is.available() > 0) {
            fos.write(is.read());
        }
        fos.flush();
        fos.close();
        is.close();
    }
}
