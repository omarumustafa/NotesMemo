package com.example.memoapp;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter{
    private ArrayList<Memo> memoData;

    private View.OnClickListener mOnMemoClickListener;

    public class MemoViewHolder extends RecyclerView.ViewHolder{

        public TextView textMemoName;
        public TextView textMemoDate;
        public RadioButton listPriority;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            textMemoName = itemView.findViewById(R.id.textMemoName);
            textMemoDate = itemView.findViewById(R.id.textMemoDate);
            listPriority = itemView.findViewById(R.id.listPriority);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnMemoClickListener);
        }
        public TextView getTextViewName(){
            return textMemoName;
        }
        public TextView getTextViewDate(){
            return textMemoDate;
        }
        public RadioButton getRadioButton(){
            return listPriority;
        }
    }

    public MemoAdapter(ArrayList<Memo> arrayList){
        memoData = arrayList;
    }

    public void setOnMemoClickListener(View.OnClickListener memoClickListener){
        mOnMemoClickListener = memoClickListener;
    }

    public Memo getMemoAtPosition(int position){
        return memoData.get(position);
    }
    public void removeMemo(int position){
        memoData.remove(position);
        notifyItemRemoved(position);
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
        RadioButton radioButton = memoViewHolder.getRadioButton();

        if(memoData.get(position).getPriority().equals("low")){
            radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(radioButton.getContext(), R.color.green)));
        } else if(memoData.get(position).getPriority().equals("medium")){
            radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(radioButton.getContext(), R.color.yellow)));
        } else if(memoData.get(position).getPriority().equals("high")){
            radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(radioButton.getContext(), R.color.red)));
        } else {
            radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(radioButton.getContext(), R.color.grey)));
        }
    }

    @Override
    public int getItemCount() {
        return memoData.size();
    }
}
