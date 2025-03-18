package com.example.memoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MemoSetting extends AppCompatActivity {
    private RadioGroup sortOptionsGroup;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memo_setting);

        listImageButton();
        memoSettingButton();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.memoSettings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sortOptionsGroup = findViewById(R.id.sortOptionsGroup);
        sharedPreferences = getSharedPreferences("MemoPreferences", Context.MODE_PRIVATE);

        initSettings();
        initSortClick();

    }
    private void initSettings(){
        String sortBy = sharedPreferences.getString("sortfield", "priority");

        RadioButton rbPriority = findViewById(R.id.sortByPriority);
        RadioButton rbDate = findViewById(R.id.sortByDate);
        RadioButton rbSubject = findViewById(R.id.sortBySubject);

        if (sortBy.equals("priority")) {
            rbPriority.setChecked(true);
        } else if (sortBy.equals("date")) {
            rbDate.setChecked(true);
        } else {
            rbSubject.setChecked(true);
        }
    }
    private void initSortClick(){
        sortOptionsGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            RadioButton rbPriority = findViewById(R.id.sortByPriority);
            RadioButton rbDate = findViewById(R.id.sortByDate);


            if (rbPriority.isChecked()) {
                editor.putString("sortfield", "priority");
            } else if (rbDate.isChecked()) {
                editor.putString("sortfield", "date");
            } else {
                editor.putString("sortfield", "subject");
            }
            editor.apply();



            setResult(RESULT_OK);
            finish();
        });
    }
    private void listImageButton() {
        ImageButton ibList = findViewById(R.id.listImageButton);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MemoSetting.this, MemoList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void memoSettingButton(){
        ImageButton msButton = findViewById(R.id.settingsImageButton);
        msButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoSetting.this, MemoSetting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}