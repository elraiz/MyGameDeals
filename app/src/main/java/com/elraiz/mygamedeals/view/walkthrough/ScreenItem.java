package com.elraiz.mygamedeals.view.walkthrough;

public class ScreenItem {

    //inisialisasi variable
    String Title,Desc;
    int Screenimg;

    //constructor
    public ScreenItem(String title, String desc, int screenimg) {
        Title = title;
        Desc = desc;
        Screenimg = screenimg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesc() {
        return Desc;
    }

    public int getScreenimg() {
        return Screenimg;
    }

}
