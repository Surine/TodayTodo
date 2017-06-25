package com.surine.todaytodo.Eventbus;

/**
 * Created by surine on 2017/6/25.
 */

public class TodoEventbus {
    private int id;
    private String text;
    private int flag;
    private int number;

    public TodoEventbus(int id, String text, int flag, int number) {
        this.id = id;
        this.text = text;
        this.flag = flag;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
