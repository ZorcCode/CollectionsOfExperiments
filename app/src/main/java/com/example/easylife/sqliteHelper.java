package com.example.easylife;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class sqliteHelper extends SQLiteOpenHelper {

    Context context;
    public sqliteHelper(@Nullable Context context){
        super(context,"Easy_Life",null,8);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists alarms(alarm_name text,time integer primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void storedata(long time,String alarmname)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format(Locale.US,
                "insert into alarms values('%s',%s)",
                alarmname,time));
    }
    public Map<Long,String> getdata()
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from alarms",null);
        if(cursor.getCount()==0)
        {
            cursor.close();
            return null;
        }
        else
        {
            if(cursor.moveToFirst())
            {
                int name = cursor.getColumnIndex("alarm_name");
                int time = cursor.getColumnIndex("time");
                Map<Long,String> map = new HashMap<>();
                while (!cursor.isAfterLast())
                {
                    map.put(cursor.getLong(time),cursor.getString(name));
                    cursor.moveToNext();
                }
                cursor.close();
                return map;
            }
            else
            {
                cursor.close();
                return null;
            }
        }
    }

    public void deletedata(long time)
    {
      SQLiteDatabase database = getWritableDatabase();
      database.execSQL(String.format(Locale.US,"delete from alarms where time=%d",time));
    }
}
