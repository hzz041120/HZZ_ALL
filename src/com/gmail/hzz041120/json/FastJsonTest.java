package com.gmail.hzz041120.json;

import com.alibaba.fastjson.JSON;

public class FastJsonTest {

    public static void main(String[] args) {
        MyObject obj = new MyObject();
        obj.setA("fdafdas\u00A0\r\nfdafdsafdasf");
        System.out.println(JSON.toJSONString(obj));
    }
}
