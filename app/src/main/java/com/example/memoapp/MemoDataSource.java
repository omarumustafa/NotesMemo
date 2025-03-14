package com.example.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.session.PlaybackState;
import android.util.Log;

public class MemoDataSource {

    private SQLiteDatabase database;

    private MemoDBHelper dbHelper;

    public MemoDataSource(Context context){
        dbHelper = new MemoDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public boolean insertMemo(Memo m){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("title", m.getTitle());
            initialValues.put("memo", m.getMemoText());
            //this is going to need to be changed later
            initialValues.put("date", m.getDate());

            long rowId = database.insert("memo", null, initialValues);
            if (rowId > 0) {
                m.setMemoID((int) rowId);
                didSucceed = true;
            }
        }
        catch (Exception e){
            Log.e("DATABASE ERROR", "ERROR INSERTING TO DATABASE");
        }
        return didSucceed;
    }

    public boolean updateMemo(Memo m){
        boolean didSucceed = false;
        try{
            Long rowId = (long) m.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("title", m.getTitle());
            updateValues.put("memo", m.getMemoText());
            //this is going to need to be changed later
            updateValues.put("date", m.getDate());

            didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e){
            Log.d("UPDATE ERROR", "ERROR UPDATING MEMO");
        }
        return didSucceed;
    }

    public int getLastMemoID(){
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

}
