package com.example.sqlite_project.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Baby implements Parcelable {

    private int id;
    private String name;
    private String gender;
    private String dob;
    private String issue;

    public Baby() {
    }

    protected Baby(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.gender = in.readString();
        this.dob = in.readString();
        this.issue = in.readString();
    }

    public static final Creator<Baby> CREATOR = new Creator<Baby>() {
        @Override
        public Baby createFromParcel(Parcel source) {
            return new Baby(source);
        }

        @Override
        public Baby[] newArray(int size) {
            return new Baby[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.gender);
        dest.writeString(this.dob);
        dest.writeString(this.issue);
    }

}