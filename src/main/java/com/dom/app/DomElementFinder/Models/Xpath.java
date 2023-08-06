package com.dom.app.DomElementFinder.Models;

public class Xpath {
    public long id;
    public String path;
    public String pathOuterHtml;
    public String pathText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath(){
        return path;
    }
    public void setPath(String path){
        this.path = path;
    }
    public String getPathOuterHtml(){
        return pathOuterHtml;
    }
    public void setPathOuterHtml(String pathOuterHtml){
        this.pathOuterHtml = pathOuterHtml;
    }
    public String getText(){
        return pathText;
    }
    public void setPathText(String pathText){
        this.pathText = pathText;
    }

    public void path(String path) {
        setPath(path);
    }

    public void pathOuterHtml(String pathOuterHtml) {
        setPathOuterHtml(pathOuterHtml);
    }

    public void pathText(String pathText) {
        setPathText(pathText);
    }
}
