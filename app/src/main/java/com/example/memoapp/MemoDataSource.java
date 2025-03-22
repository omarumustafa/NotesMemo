package com.example.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.session.PlaybackState;
import android.util.Log;

import java.util.ArrayList;

public class MemoDataSource {

    private SQLiteDatabase database;

    public MemoDBHelper dbHelper;

    public MemoDataSource(Context context) {
        dbHelper = new MemoDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertMemo(Memo m) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("title", m.getTitle());
            initialValues.put("memo", m.getMemoText());
            //this is going to need to be changed later
            initialValues.put("date", m.getDate());
            initialValues.put("priority", m.getPriority());

            long rowId = database.insert("memo", null, initialValues);
            if (rowId > 0) {
                m.setMemoID((int) rowId);
                didSucceed = true;
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR", "ERROR INSERTING TO DATABASE  " + e.getMessage());
        }
        return didSucceed;
    }

    public boolean updateMemo(Memo m) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) m.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("title", m.getTitle());
            updateValues.put("memo", m.getMemoText());
            //this is going to need to be changed later
            updateValues.put("date", m.getDate());
            updateValues.put("priority", m.getPriority());

            didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
        } catch (Exception e) {
            Log.d("UPDATE ERROR", "ERROR UPDATING MEMO");
        }
        return didSucceed;
    }

    public int getLastMemoID() {
        int lastId = -1;
        try {
            String query = "SELECT MAX(_id) FROM memo";
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst() && cursor.getCount() > 0) {
                lastId = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error fetching last memo ID", e);
        }
        return lastId;
    }

    public ArrayList<Memo> getMemos() {
        ArrayList<Memo> memoList = new ArrayList<>();
        try {
            String query = "Select * from memo";
            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setTitle(cursor.getString(1));
                newMemo.setMemoText(cursor.getString(2));
                newMemo.setDate(cursor.getString(3));
                newMemo.setPriority(cursor.getString(4));
                memoList.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memoList = new ArrayList<Memo>();
        }
        return memoList;
    }
    public ArrayList<Memo> getMemos(String sortBy) {
        ArrayList<Memo> memoList = new ArrayList<>();
        String orderBy;

        switch (sortBy) {
            case "date":
                orderBy = "date DESC";  // Sort by newest first
                break;
            case "subject":
                orderBy = "LOWER(title) COLLATE NOCASE  ASC";
                break;
            default:

                orderBy = "CASE priority " +
                        "WHEN 'high' THEN 3 " +
                        "WHEN 'medium' THEN 2 " +
                        "WHEN 'low' THEN 1 " +
                        "ELSE 0 END DESC";
                break;
        }

        try {
            String query = "SELECT * FROM memo ORDER BY " + orderBy;
            Cursor cursor = database.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {  // Ensure cursor has data
                do {
                    Memo newMemo = new Memo();
                    newMemo.setMemoID(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                    newMemo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                    newMemo.setMemoText(cursor.getString(cursor.getColumnIndexOrThrow("memo")));
                    newMemo.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    newMemo.setPriority(cursor.getString(cursor.getColumnIndexOrThrow("priority")));
                    memoList.add(newMemo);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error fetching memos: " + e.getMessage());
            memoList = new ArrayList<>();  // Ensure memoList is empty instead of null
        }
        return memoList;
    }


}
