package com.surine.todaytodo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.surine.todaytodo.Bean.Todo;
import com.surine.todaytodo.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends RemoteViewsService {
    private static final String TAG = "SURINE_SERVICE";
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "GridWidgetService");
        return new ListRemoteViewsFactory(this, intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private List<Todo> mtodos = new ArrayList<>();
        private Context mContext;
        private int mAppWidgetId;

        /**
         * 构造GridRemoteViewsFactory
         * @author skywang
         */

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            Log.d(TAG, "这个是list:"+mAppWidgetId);
        }

        @Override
        public RemoteViews getViewAt(int position) {
            // 获取xml 对应的RemoteViews
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

            // 设置 第position位的“视图”的数据
            Todo todo = mtodos.get(position);
            Log.d(TAG, "发现数据 getViewAt:"+todo.getContent());
            rv.setTextViewText(R.id.textView3, todo.getContent());
            rv.setTextViewText(R.id.textView4,todo.getTime());
            return rv;
        }

        /**
         * 初始化GridView的数据
         * @author skywang
         */
        private void initListViewData() {
            mtodos.clear();
            //获取数据库
            mtodos = DataSupport.findAll(Todo.class);
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate");
            // 初始化“集合视图”中的数据
        }

        @Override
        public int getCount() {
            // 返回“集合视图”中的数据的总数
            return mtodos.size();
        }

        @Override
        public long getItemId(int position) {
            // 返回当前项在“集合视图”中的位置
            return position;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            // 只有一类 Listview
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        //数据改变的时候
        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged: ");
            initListViewData();
        }


        //销毁的时候清空列表
        @Override
        public void onDestroy() {
            mtodos.clear();
        }


    }

}
