package com.example.final_project;

public class Recipe_Maker_Get {
  String title;
    String imageLink;
    String summary;
    public Recipe_Maker_Get()
    {

    }
    public Recipe_Maker_Get(String title,String imageLink, String summary)
    {
        this.title = title;
        this.imageLink = imageLink;
        this.summary = summary;

    }
    public String getTitle()
    {
        return title;
    }
    public String getImageLink()
    {
        return imageLink;
    }
    public String getSummary()
    {
        return summary;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setImageLink(String imageLink)
    {
        this.imageLink=imageLink;
    }
    public void setSummary(String summary)
    {
        this.summary=summary;
    }
}
