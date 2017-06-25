package com.surine.todaytodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.surine.todaytodo.Adapter.MainAdapter;
import com.surine.todaytodo.Bean.Todo;
import com.surine.todaytodo.Eventbus.SimpleMessage;
import com.surine.todaytodo.Eventbus.TodoEventbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    ImageView empty;
    private List<Todo> mTodoList = new ArrayList<>();
    MainAdapter mainAdapter;
    View view;
    EditText ed;
    Random random = new Random();
    int i = 0;
    //dataformat
    SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd");
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
    private ItemTouchHelper mItemTouchHelper;
    int flag;
    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set the toolbar title
        String date_now = sDateFormat.format(new java.util.Date());
        setTitle("搞事情");

        //create the litepal database
        Connector.getDatabase();

        //init the db data;
        initData();

        //set recycleview info
        mRecyclerView = (RecyclerView) findViewById(R.id.rec);
        initRec();
        //set FloatingActionButton info
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
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

    private void initRec() {
        mainAdapter = new MainAdapter(mTodoList,this);
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
     //  if(!prefs.getBoolean("muti_view",false)) {
           mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     //  }else{
      //     mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
     //  }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mainAdapter);
        //attach ItemTouchHelper and RecyclerView
        ItemTouchHelper.Callback callback = new MainItemTouch(mainAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }


    //add todo
    private void save(String message, boolean b) {
                    //save the todo
                    String date_now = sDateFormat.format(new java.util.Date());
                    Todo the_todo = new Todo();
                    if(b){
                        the_todo = DataSupport.find(Todo.class,flag);
                        the_todo.setContent(message);
                        the_todo.setTime(date_now);
                        the_todo.save();
                        mTodoList.get(number).setContent(message);
                        mainAdapter.notifyDataSetChanged();
                    }else{
                        the_todo.setContent(message);
                        the_todo.setTime(date_now);
                        the_todo.setColor(colors[random.nextInt(11)]);
                        the_todo.save();
                        mTodoList.add(the_todo);
                        mainAdapter.notifyItemInserted(mTodoList.size()-1);
                    }

                    //widget update
                    Intent updateIntent = new Intent("com.widget.surine.WidgetProvider.MY_UPDATA_CHANGE");
                    sendBroadcast(updateIntent);
    }

    //get all data
    private void initData() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.setting){
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



    //intro
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SimpleMessage event) {
        if (event.getId() == 1) {
            save(event.getMessage(), false);
        } else if (event.getId() == 2) {
            initRec();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TodoEventbus event) {
        if(event.getId()==3){
            flag = event.getFlag();
            number = event.getNumber();
            save(event.getText(),true);
        }
    }
}
