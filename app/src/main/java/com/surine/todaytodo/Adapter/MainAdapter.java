package com.surine.todaytodo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.surine.todaytodo.AddActivity;
import com.surine.todaytodo.Bean.Todo;
import com.surine.todaytodo.Interface.onMoveAndSwipeListener;
import com.surine.todaytodo.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by surine on 2017/5/21.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements onMoveAndSwipeListener {
    private List<Todo> mTodos;
    private Context mContext;
    private ViewHolder viewholder;

    public MainAdapter(List<Todo> todos, Context context) {
        mTodos = todos;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        View view;
       // if(!prefs.getBoolean("muti_view",false)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        viewholder = holder;
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int po = holder.getAdapterPosition();
                Intent intent = new Intent(mContext, AddActivity.class);
                intent.putExtra("INTENT",mTodos.get(po).getId());
                intent.putExtra("INTENT_NUMBER",po);
                mContext.startActivity(intent);
            }
        });
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Delete(holder.getAdapterPosition(), view);
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = mTodos.get(position);
        holder.mTextView.setText(todo.getContent());
        holder.color.setImageResource(todo.getColor());
        holder.mTextView2.setText(todo.getTime());
    }
    private void Delete(final int po, View view) {
        final String content = mTodos.get(po).getContent();
        final String date = mTodos.get(po).getTime();
        final int color=mTodos.get(po).getColor();
        DataSupport.delete(Todo.class,mTodos.get(po).getId());
        mTodos.remove(po);
        notifyItemRemoved(po);
        Snackbar.make(view,"事情已经删除",Snackbar.LENGTH_LONG).setAction("撤销", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo the_todo = new Todo();
                the_todo.setContent(content);
                the_todo.setTime(date);
                the_todo.setColor(color);
                the_todo.save();
                mTodos.add(po,the_todo);
                notifyDataSetChanged();
                //widget update
                Intent updateIntent = new Intent("com.widget.surine.WidgetProvider.MY_UPDATA_CHANGE");
                mContext.sendBroadcast(updateIntent);
            }
        }).show();
        //widget update
        Intent updateIntent = new Intent("com.widget.surine.WidgetProvider.MY_UPDATA_CHANGE");
        mContext.sendBroadcast(updateIntent);
    }


    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    @Override
    public void onItemDismiss(int position) {
        Delete(position, viewholder.mCardView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView color;
        TextView mTextView2;
        CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            mTextView2 = (TextView) itemView.findViewById(R.id.textView2);
            color = (ImageView) itemView.findViewById(R.id.color);
            mCardView = (CardView) itemView.findViewById(R.id.card);
        }
    }

}
