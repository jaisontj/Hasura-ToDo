package com.example.jaison.hasura_todo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jaison on 11/01/17.
 */

public class ToDoRecyclerViewAdapter extends RecyclerView.Adapter<ToDoViewHolder> {


    public ToDoRecyclerViewAdapter() {
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todo,parent,false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void updateData(int position, ToDo todo) {
    }

    public void removeData(int position) {
    }




}
