package com.gmail.hzz041120.btrace;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class FindCreateThread {

    @OnMethod(clazz = "java.util.concurrent.ThreadPoolExecutor", method = "<init>")
    public static void m(@Self Object self) {
        Threads.jstack();
    }

}
