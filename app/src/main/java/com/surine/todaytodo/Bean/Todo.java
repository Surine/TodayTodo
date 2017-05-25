package com.surine.todaytodo.Bean;

import org.litepal.crud.DataSupport;

/**
 * Created by surine on 2017/5/21.
 */

public class Todo extends DataSupport {
    private String content;
    private String time;
    private int id;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
