package com.example.memoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter{
    private ArrayList<Memo> memoData;

    private View.OnClickListener onMemoClickListener;

    public class MemoViewHolder extends RecyclerView.ViewHolder{

        public TextView textMemoName;
        public TextView textMemoDate;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            textMemoName = itemView.findViewById(R.id.textMemoName);
            textMemoDate = itemView.findViewById(R.id.textMemoDate);
            itemView.setTag(this);
            itemView.setOnClickListener(onMemoClickListener);
        }
        public TextView getTextViewName(){
            return textMemoName;
        }
        public TextView getTextViewDate(){
            return textMemoDate;
        }
    }

    public MemoAdapter(ArrayList<Memo> arrayList){
        memoData = arrayList;
    }

    public void setOnMemoClickListener(View.OnClickListener onMemoClickListener){
        this.onMemoClickListener = onMemoClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_list, parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MemoViewHolder memoViewHolder = (MemoViewHolder) holder;
        memoViewHolder.getTextViewName().setText(memoData.get(position).getTitle());
        memoViewHolder.getTextViewDate().setText(memoData.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return memoData.size();
    }
}
