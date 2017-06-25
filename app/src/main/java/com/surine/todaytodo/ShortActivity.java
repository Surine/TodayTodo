package com.surine.todaytodo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.surine.todaytodo.Bean.Todo;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShortActivity extends AppCompatActivity {
    EditText ed;
    Random random = new Random();
    int[] colors = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10,
            R.color.color11
    };
    //dataformat
    SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short);
        showAddDialog();
    }

    //add todo
    private void showAddDialog() {
        View view = LayoutInflater.from(ShortActivity.this).inflate(R.layout.dialog_view, null);
        ed = (EditText) view.findViewById(R.id.editText);
        ed.setFocusable(true);
        ed.setFocusableInTouchMode(true);
        ed.requestFocus();

        //delay show the ime
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager)ed.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(ed, 0);
            }
        },300);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShortActivity.this);
        String date_now = sDateFormat.format(new java.util.Date());
        builder.setTitle("添加"+date_now+"事情");
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String todo = ed.getText().toString();
                if(todo.equals("")){
                    Toast.makeText(ShortActivity.this,"啥都没写",Toast.LENGTH_SHORT).show();
                }else{
                    String date_now = sDateFormat.format(new java.util.Date());
                    Todo the_todo = new Todo();
                    the_todo.setContent(todo);
                    the_todo.setTime(date_now);
                    the_todo.setColor(colors[random.nextInt(11)]);
                    the_todo.save();
                    Toast.makeText(ShortActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
                //widget update
                Intent updateIntent = new Intent("com.widget.surine.WidgetProvider.MY_UPDATA_CHANGE");
                sendBroadcast(updateIntent);
                finish();
            }
        });
        builder.show();
    }
}
