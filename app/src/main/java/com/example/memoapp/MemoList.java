package com.example.memoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoList extends AppCompatActivity {
    ArrayList<Memo> memos;
    MemoDataSource ds = new MemoDataSource(this);
    RecyclerView memoList;
    MemoAdapter memoAdapter;

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

        listImageButton();
        memoSettingButton();
        addMemoButton();

        SharedPreferences sharedPreferences = getSharedPreferences("MemoPreferences", Context.MODE_PRIVATE);
        String sortBy = sharedPreferences.getString("sortfield", "priority");

        try {
            ds.open();
            memos = ds.getMemos(sortBy);
            ds.close();
            memoList = findViewById(R.id.rvMemo);
            RecyclerView.LayoutManager RecyclerView = new LinearLayoutManager(this);
            memoList.setLayoutManager(RecyclerView);
            memoAdapter = new MemoAdapter(memos);
            memoAdapter.setOnMemoClickListener(onMemoClickListener);
            memoList.setAdapter(memoAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // this gives it the item touch Helper
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Memo memoToDelete = memoAdapter.getMemoAtPosition(position);

                ds.open();
                SQLiteDatabase db = ds.dbHelper.getWritableDatabase();
                db.delete("memo", "_id=?", new String[]{String.valueOf(memoToDelete.getMemoID())});
                ds.close();

                memoAdapter.removeMemo(position);
                Toast.makeText(MemoList.this, "Memo deleted", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(memoList);


//        ds = new MemoDataSource(this);
//        memoList = findViewById(R.id.rvMemo);
//        memoList.setLayoutManager(new LinearLayoutManager(this));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.memoList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadMemos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMemos();

    }

    private void loadMemos() {
        SharedPreferences sharedPreferences = getSharedPreferences("MemoPreferences", Context.MODE_PRIVATE);
        String sortBy = sharedPreferences.getString("sortfield", "priority");

        try {
            ds.open();
            memos = ds.getMemos(sortBy);
            ds.close();

            // Ensure the adapter is always updated with the latest memos
            memoAdapter = new MemoAdapter(memos);
            memoAdapter.setOnMemoClickListener(onMemoClickListener);
            memoList.setAdapter(memoAdapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void listImageButton() {
        ImageButton ibList = findViewById(R.id.listImageButton);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MemoList.this, MemoList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void memoSettingButton() {
        ImageButton msButton = findViewById(R.id.settingsImageButton);
        msButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoList.this, MemoSetting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void addMemoButton() {
        Button addMemoButton = findViewById(R.id.addMemoButton);
        addMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}