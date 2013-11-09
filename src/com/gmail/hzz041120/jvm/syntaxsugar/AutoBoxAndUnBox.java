package com.gmail.hzz041120.jvm.syntaxsugar;

public class AutoBoxAndUnBox {

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3l;
        Boolean x = true;
        Boolean y = true;
        Boolean z = new Boolean(true);
        //toBoolean("true") 然后 new Boolean(boolean);
        Boolean n = new Boolean("true");
        //true 1比特的整型会被载入运行时常量池
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        // a + b 被包装为Long true
        System.out.println(g == a + b);
        // a + b 被包装为Integer false
        System.out.println(g.equals(a + b));
        System.out.println(x == y);
        System.out.println(x == z);
        System.out.println(x == n);
    }
}
