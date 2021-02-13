package com.suret.stopwatchandtimer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suret.stopwatchandtimer.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<String> mData;
    private LayoutInflater mInflater;


    public RecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.result_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        String result = mData.get(position);
        holder.textResult.setText(result);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView textResult;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            textResult = itemView.findViewById(R.id.txtContent);

        }

    }
}
