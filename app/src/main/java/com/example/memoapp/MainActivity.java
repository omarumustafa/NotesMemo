package com.example.memoapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setRadioButtonColor();
        listImageButton();
        memoSettingButton();
        autoFillDate();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initSaveButton();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initMemo(extras.getInt("memoID"));
        }
        else {
            currentMemo = new Memo();
        }
    }

    private void setRadioButtonColor() {
        RadioButton radioButton = findViewById(R.id.radioButtonLow);
        radioButton.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

        radioButton = findViewById(R.id.radioButtonMedium);
        radioButton.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

        radioButton = findViewById(R.id.radioButtonHigh);
        radioButton.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
    }

    private void listImageButton() {
        ImageButton ibList = findViewById(R.id.listImageButton);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoList.class);
                startActivity(intent);
            }
        });
    }

    private void memoSettingButton(){
        ImageButton msButton = findViewById(R.id.settingsImageButton);
        msButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoSetting.class);
                startActivity(intent);
            }
        });
    }

    private void autoFillDate() {
        EditText editDate = findViewById(R.id.editDate);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String date = dateFormat.format(calendar.getTime());

        editDate.setText(date);
    }

    private void initSaveButton(){
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v ->{
            Memo m = new Memo();
            MemoDataSource dataSource = new MemoDataSource(MainActivity.this);

            dataSource.open();

            String title = ((EditText)findViewById(R.id.editMemoTitle)).getText().toString();
            String memo = ((EditText)findViewById(R.id.editMemoDesc)).getText().toString();
            String date = ((EditText)findViewById(R.id.editDate)).getText().toString();
            RadioButton low = findViewById(R.id.radioButtonLow);
            RadioButton medium = findViewById(R.id.radioButtonMedium);
            RadioButton high = findViewById(R.id.radioButtonHigh);

            m.setTitle(title);
            m.setMemoText(memo);
            m.setDate(date);

            if(low.isChecked()){
                m.setPriority("low");
            } else if(medium.isChecked()){
                m.setPriority("medium");
            } else if (high.isChecked()){
                m.setPriority("high");
            } else {
                m.setPriority("");
            }

            dataSource.insertMemo(m);

            dataSource.close();


        });
    }

    private void initMemo(int id) {

        MemoDataSource ds = new MemoDataSource(MainActivity.this);
        try {
            ds.open();
            currentMemo = ds.getSpecificMemo(id);
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this, "Load Memo Failed", Toast.LENGTH_SHORT).show();
        }

        EditText title = findViewById(R.id.editMemoTitle);
        EditText memo = findViewById(R.id.editMemoDesc);
        EditText date = findViewById(R.id.editDate);
        RadioButton low = findViewById(R.id.radioButtonLow);
        RadioButton medium = findViewById(R.id.radioButtonMedium);
        RadioButton high = findViewById(R.id.radioButtonHigh);

        title.setText(currentMemo.getTitle());
        memo.setText(currentMemo.getMemoText());
        date.setText(currentMemo.getDate());

        if(currentMemo.getPriority().equals("low")){
            low.setChecked(true);
        } else if(currentMemo.getPriority().equals("medium")){
            medium.setChecked(true);
        } else if(currentMemo.getPriority().equals("high")){
            high.setChecked(true);
        }

    }
}


