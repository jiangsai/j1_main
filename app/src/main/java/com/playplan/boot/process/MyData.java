package com.playplan.boot.process;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : jyt
 * time   : 2021/10/14
 * desc   :
 */
public class MyData implements Parcelable {
    String name;

    public MyData(String in) {
        name = in;
    }

    protected MyData(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };
}
