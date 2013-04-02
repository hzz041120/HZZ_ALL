package com.gmail.hzz041120.jvm.pkgtest;

public interface ConstInterface extends SuperInterface {

    String     INTERFACE_CONST = "interface const";

    SubClass   SUB_CLASS       = new SubClass();
    OtherClass OTHER_CLASS     = new OtherClass();
}
