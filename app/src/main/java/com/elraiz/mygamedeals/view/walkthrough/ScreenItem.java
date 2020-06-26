package com.elraiz.mygamedeals.view.walkthrough;

public class ScreenItem {

    String Title,Desc;
    int Screenimg;

    public ScreenItem(String title, String desc, int screenimg) {
        Title = title;
        Desc = desc;
        Screenimg = screenimg;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getScreenimg() {
        return Screenimg;
    }

    public void setScreenimg(int screenimg) {
        Screenimg = screenimg;
    }
}
