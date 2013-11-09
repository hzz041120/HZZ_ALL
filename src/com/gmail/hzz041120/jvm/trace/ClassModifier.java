package com.gmail.hzz041120.jvm.trace;

public class ClassModifier {

    private static final int   CONSTANT_POOL_COUNT_INDEX = 8;
    private static final int   CONSTANT_UTF8_INFO        = 1;
    private static final int[] CONSTANT_ITEM_LENGTH      = { -1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5 };
    private static final int   U1                        = 1;
    private static final int   U2                        = 2;
    private byte[]             classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + U2;
        for (int i = 0; i < cpc; i++) {
            int tag = ByteUtils.bytes2Int(classByte, offset, U1);
            if (tag == CONSTANT_UTF8_INFO) {
                int len = ByteUtils.bytes2Int(classByte, offset + U1, U2);
                offset += (U1 + U2);
                String str = ByteUtils.bytes2String(classByte, offset, len);
                if (str.equalsIgnoreCase(oldStr)) {
                    byte[] strBytes = ByteUtils.string2Byte(newStr);
                    byte[] strLen = ByteUtils.int2Byte(newStr.length(), U2);
                    classByte = ByteUtils.bytesReplace(classByte, offset - U2, U2, strLen);
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
                    return classByte;
                } else {
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

    private int getConstantPoolCount() {
        return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, U2);
    }
}
