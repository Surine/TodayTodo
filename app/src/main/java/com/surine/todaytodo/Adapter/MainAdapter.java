package com.surine.todaytodo.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.surine.todaytodo.Bean.Todo;
import com.surine.todaytodo.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by surine on 2017/5/21.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Todo> mTodos;
    private Context mContext;

    public MainAdapter(List<Todo> todos, Context context) {
        mTodos = todos;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int po = holder.getAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提醒");
                builder.setMessage("今天["+mTodos.get(po).getContent()+"]完成了么？");
                builder.setPositiveButton("嗯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      Delete(po,view);
                    }
                });
                builder.setNegativeButton("没", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext,"还不快去做！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        holder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final int po = holder.getAdapterPosition();
                Delete(po,view);
                return true;
            }
        });
        return holder;
    }

    private void Delete(final int po, View view) {
        final String content = mTodos.get(po).getContent();
        final String date = mTodos.get(po).getTime();
        final int color=mTodos.get(po).getColor();
        DataSupport.delete(Todo.class,mTodos.get(po).getId());
        mTodos.remove(po);
        notifyItemRemoved(po);
        Snackbar.make(view,"事情已经删除",Snackbar.LENGTH_SHORT).setAction("撤销", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo the_todo = new Todo();
                the_todo.setContent(content);
                the_todo.setTime(date);
                the_todo.setColor(color);
                the_todo.save();
                mTodos.add(po,the_todo);
                notifyDataSetChanged();
            }
        }).show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       Todo todo = mTodos.get(position);
        holder.mTextView.setText(todo.getContent());
        holder.color.setImageResource(todo.getColor());
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView color;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            color = (ImageView) itemView.findViewById(R.id.color);
        }
    }
}
