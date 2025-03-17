package com.example.memoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoList extends AppCompatActivity {
    ArrayList<Memo> memos;
    MemoDataSource ds = new MemoDataSource(this);

    private final View.OnClickListener onMemoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int memoID = memos.get(position).getMemoID();
            Intent intent = new Intent(MemoList.this, MainActivity.class);
            intent.putExtra("memoID", memoID);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memo_list);

        try {
            ds.open();
            memos = ds.getMemos();
            ds.close();
            RecyclerView memoList = findViewById(R.id.rvMemo);
            RecyclerView.LayoutManager RecyclerView = new LinearLayoutManager(this);
            memoList.setLayoutManager(RecyclerView);
            MemoAdapter memoAdapter = new MemoAdapter(memos);
            memoAdapter.setOnMemoClickListener(onMemoClickListener);
            memoList.setAdapter(memoAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.memoList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}