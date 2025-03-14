package com.example.memoapp;

public class Memo {

    public Memo(){
        memoID = -1;
    }


    public String title;

    public String memoText;
    //The dates going to have to be changed to a later value
    //When we implement the CalendarView
    public String date;

    private int memoID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemoText() {
        return memoText;
    }

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMemoID() {
        return memoID;
    }

    public void setMemoID(int memoID) {
        this.memoID = memoID;
    }
}
