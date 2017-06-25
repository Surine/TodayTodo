package com.surine.todaytodo.Eventbus;

/**
 * Created by surine on 2017/6/4.
 *
 * 2
 */

public class SimpleMessage {
    private int id;
    private String message;
    private int todo_id;

    public SimpleMessage(int id, String message, int todo_id) {
        this.id = id;
        this.message = message;
        this.todo_id = todo_id;
    }

    public int getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
