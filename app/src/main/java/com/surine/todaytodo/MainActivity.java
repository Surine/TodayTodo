package com.surine.todaytodo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.surine.todaytodo.Adapter.MainAdapter;
import com.surine.todaytodo.Bean.Todo;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private List<Todo> mTodoList = new ArrayList<>();
    MainAdapter mainAdapter;
    View view;
    EditText ed;
    Random random = new Random();
    int i = 0;
    //dataformat
    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set the toolbar title
        String date_now = sDateFormat.format(new java.util.Date());
        setTitle("搞事情 ·"+date_now);

        //create the litepal database
        Connector.getDatabase();

        //init the db data;
        initData();

        //set recycleview info
        mainAdapter = new MainAdapter(mTodoList,this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mainAdapter);

        //set FloatingActionButton info
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        //show the intro view
        showTheIntro(fab);

        //first use info
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        if(!pref.getBoolean("first_use",false)) {
            editor.putBoolean("first_use", true);
            editor.apply();
        }


    }


    //add todo 
    private void showAddDialog() {
        view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_view,null);
        ed = (EditText) view.findViewById(R.id.editText);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        String date_now = sDateFormat.format(new java.util.Date());
        builder.setTitle("添加"+date_now+"事情");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String todo = ed.getText().toString();
                if(todo.equals("")){
                    Toast.makeText(MainActivity.this,"啥都没写",Toast.LENGTH_SHORT).show();
                }else{
                    String date_now = sDateFormat.format(new java.util.Date());
                    Todo the_todo = new Todo();
                    the_todo.setContent(todo);
                    the_todo.setTime(date_now);
                    the_todo.setColor(colors[random.nextInt(11)]);
                    the_todo.save();
                    mTodoList.add(the_todo);
                    mainAdapter.notifyItemChanged(mTodoList.size()-1);
                }
            }
        });
        builder.show();
    }

    private void initData() {
        String date_now = sDateFormat.format(new java.util.Date());
        mTodoList = DataSupport.findAll(Todo.class);
        for (int e = 0;e<mTodoList.size();e++) {
            Todo todo = mTodoList.get(e);
        }
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
       //如果是第一次使用，那么不删库
        if(!pref.getBoolean("first_use",false)) {

        }else {
                for (int e = 0;e<mTodoList.size();e++) {
                    Todo todo = mTodoList.get(e);
                    if (!(todo.getTime().equals(date_now))) {
                        DataSupport.delete(Todo.class, todo.getId());
                        //记录是否有删库现象
                        i = 1;
                    }
                }
        }
        mTodoList = DataSupport.findAll(Todo.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.about);
            builder.setPositiveButton("好",null);
            builder.show();
            return true;
        }else if(id == R.id.action_huaji){
            View view = LayoutInflater.from(this).inflate(R.layout.huaji,null);
            TextView textView = (TextView) view.findViewById(R.id.huajibi);
            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
            textView.setText(pref.getInt("coolb",0)+"枚滑稽币");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("滑稽币数量");
            builder.setView(view);
            builder.setPositiveButton("好",null);
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }



    private void showTheIntro(View fab) {
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText(getString(R.string.start))
                        .setTarget(fab)
                        .setUsageId("ok_button")
                        .setListener(new MaterialIntroListener() {
                            @Override
                            public void onUserClicked(String materialIntroViewId) {

                            }
                        })
                        .show();
    }


}
