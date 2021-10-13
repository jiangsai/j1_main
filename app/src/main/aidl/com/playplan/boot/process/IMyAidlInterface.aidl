// IMyAidlInterface.aidl
package com.playplan.boot.process;

// Declare any non-default types here with import statements

/**
* https://www.jianshu.com/p/d1fac6ccee98
*/
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getString();
    void setData();
}