package com.aloel.maribelajar.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {

    public String number1;
    public String number2;
    public String number3;
    public String number4;
    public String number5;
    public String number6;
    public String number7;
    public String number8;
    public String number9;
    public String number10;

    public Answer() {}

    public Answer(Parcel in) {
        number1        = in.readString();
        number2        = in.readString();
        number3        = in.readString();
        number4        = in.readString();
        number5        = in.readString();
        number6        = in.readString();
        number7       = in.readString();
        number8        = in.readString();
        number9        = in.readString();
        number10        = in.readString();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {

        @Override
        public Answer createFromParcel(Parcel in) {

            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {

            return new Answer[size];
        }

    };

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(number1);
        out.writeString(number2);
        out.writeString(number3);
        out.writeString(number4);
        out.writeString(number5);
        out.writeString(number6);
        out.writeString(number7);
        out.writeString(number8);
        out.writeString(number9);
        out.writeString(number10);
    }
}
