package com.elraiz.mygamedeals.api;

public class APIDataArticle {
    private String mTitle;
    private String mSummary;
    private String mAuthor;
    private String mImageUrl;
    private String mPublish;
    private String mWebUrl;

    //constructor
    public APIDataArticle(String title, String summary, String author, String imageURL, String publish, String webURL) {
        mTitle = title;
        mSummary = summary;
        mAuthor = author;
        mImageUrl  = imageURL;
        mPublish = publish;
        mWebUrl = webURL;
    }
    public String getTitle() {
        return mTitle;
    }
    public String getSummary() {
        return mSummary;
    }
    public String getAuthor() {return mAuthor;}
    public String getImageUrl(){return mImageUrl;}
    public String getPublishDate() {return  mPublish;}
    public String getWebUrl() {return mWebUrl;}
}
