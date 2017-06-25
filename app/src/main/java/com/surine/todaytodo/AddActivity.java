package com.surine.todaytodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.surine.todaytodo.Bean.Todo;
import com.surine.todaytodo.Eventbus.SimpleMessage;
import com.surine.todaytodo.Eventbus.TodoEventbus;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.Timer;
import java.util.TimerTask;

public class AddActivity extends AppCompatActivity {
    EditText editText;
    int flag;
    int pos;
    Todo mTodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();
        flag = intent.getIntExtra("INTENT",-1);
        pos = intent.getIntExtra("INTENT_NUMBER",0);
        mTodo = DataSupport.find(Todo.class,flag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("添加事情");

        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editText = (EditText) findViewById(R.id.add);

        if(flag != -1){
            editText.setText(mTodo.getContent());
        }

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        //delay show the ime
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        },300);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            saveandfinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                saveandfinish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveandfinish() {
        String text = editText.getText().toString();

        if (text.equals("")) {
            Toast.makeText(AddActivity.this,"输入为空",Toast.LENGTH_SHORT).show();
        } else {
            if(flag != -1){
                EventBus.getDefault().post(
                        new TodoEventbus(3, text,flag,pos));
            }else{
                EventBus.getDefault().post(
                        new SimpleMessage(1, text,0));
            }
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }
}
