package com.aloel.maribelajar.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Quiz implements Parcelable {

    public int id;
    public String subject;
    public String classStudent;
    public String type;
    public String question;
    public String image;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String answer;

    public Quiz() {}

    public Quiz(Parcel in) {
        id              = in.readInt();
        subject         = in.readString();
        classStudent    = in.readString();
        type            = in.readString();
        question        = in.readString();
        image           = in.readString();
        option1         = in.readString();
        option2         = in.readString();
        option3         = in.readString();
        option4         = in.readString();
        answer          = in.readString();
    }

    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {

        @Override
        public Quiz createFromParcel(Parcel in) {

            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {

            return new Quiz[size];
        }

    };

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeInt(id);
        out.writeString(subject);
        out.writeString(classStudent);
        out.writeString(type);
        out.writeString(question);
        out.writeString(image);
        out.writeString(option1);
        out.writeString(option2);
        out.writeString(option3);
        out.writeString(option4);
        out.writeString(answer);
    }
}
