package com.example.hp.beginnerproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyDATABase {

    MyDataBaseHelper helper;


    public MyDATABase(Context context) {

        helper = new MyDataBaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
    }

    /**
     * @param From
     * @param texbody
     * @param time
     */
    public long insertData(String From, String texbody, String time, int status, int sendOrReceive) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBaseHelper.MSSG_FROM, From);
        contentValues.put(MyDataBaseHelper.MSSG_BODY, texbody);
        contentValues.put(MyDataBaseHelper.MSSG_time, time);
        contentValues.put(MyDataBaseHelper.STATUS, status);
        contentValues.put(MyDataBaseHelper.SENDORRECEEIVE,sendOrReceive);
        return db.insert(MyDataBaseHelper.TABLE_NAME, null, contentValues);


    }

    public List<ListItemClass> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();


        String[] column = {MyDataBaseHelper.UID, MyDataBaseHelper.MSSG_FROM, MyDataBaseHelper.MSSG_BODY, MyDataBaseHelper.MSSG_time,
                MyDataBaseHelper.STATUS, MyDataBaseHelper.SENDORRECEEIVE};
        Cursor cursor = db.query(MyDataBaseHelper.TABLE_NAME, column, null, null, null, null, null);
        //returning a cursor object


        List<ListItemClass> data_name = new ArrayList<ListItemClass>();


        if (cursor != null && cursor.moveToFirst()) {

            do {
                ListItemClass listItemClass = new ListItemClass();

                listItemClass.setMssg_from(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_FROM)));
                listItemClass.setMssg_body(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_BODY)));
                listItemClass.setMssg_time(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_time)));
                listItemClass.setStatus(cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.STATUS)));
                data_name.add(listItemClass);


            } while (cursor.moveToNext());
        }

        return data_name;
    }

    public int upDateStatus(String phonenumber, int changeVal) {
        int count = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDataBaseHelper.STATUS, changeVal);
         //   String k = Integer.toString(pos);
            String[] whereARGS = {phonenumber};
            count = db.update(MyDataBaseHelper.TABLE_NAME, contentValues, MyDataBaseHelper.MSSG_FROM + " =?", whereARGS);
            return count;
        } catch (Exception e) {
            Log.d("ohLord", e.toString());
        }
        return count;
    }


    public List<ListItemClass> getReceivedMessage(String phonenumber) {
        //select message tv_from VIVZTABLE where phonenumber = 'phonenumber'

        List<ListItemClass> dataMessage = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] column = {MyDataBaseHelper.UID, MyDataBaseHelper.MSSG_FROM,
                MyDataBaseHelper.MSSG_BODY, MyDataBaseHelper.MSSG_time, MyDataBaseHelper.STATUS,MyDataBaseHelper.SENDORRECEEIVE};
        Cursor cursor = db.query(MyDataBaseHelper.TABLE_NAME, column,
                MyDataBaseHelper.MSSG_FROM + " = '" + phonenumber + "'", null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            do {
                ListItemClass listItemClass = new ListItemClass();

                listItemClass.setMssg_from(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_FROM)));
                listItemClass.setMssg_body(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_BODY)));
                listItemClass.setMssg_time(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_time)));
                listItemClass.setStatus(cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.STATUS)));
                listItemClass.setSend_or_receive(cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.SENDORRECEEIVE)));
                dataMessage.add(listItemClass);


            } while (cursor.moveToNext());
        }


        return dataMessage;
    }


    public List<ListItemClass> getReceivedMessageGroupBy() {
        List<ListItemClass> dataMessage = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] column = {MyDataBaseHelper.UID, MyDataBaseHelper.MSSG_FROM, MyDataBaseHelper.MSSG_BODY,
                MyDataBaseHelper.MSSG_time, MyDataBaseHelper.STATUS,MyDataBaseHelper.SENDORRECEEIVE};
        String[] whereARGS = {"0"};

        Cursor cursor = db.query(MyDataBaseHelper.TABLE_NAME, column, MyDataBaseHelper.SENDORRECEEIVE + " =?", whereARGS, MyDataBaseHelper.MSSG_FROM, null, "MAX(" + MyDataBaseHelper.MSSG_time + ")");

        if (cursor != null && cursor.moveToFirst()) {

            do {
                ListItemClass listItemClass = new ListItemClass();

                listItemClass.setMssg_from(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_FROM)));
                listItemClass.setMssg_body(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_BODY)));
                listItemClass.setMssg_time(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.MSSG_time)));
                listItemClass.setSend_or_receive(cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.SENDORRECEEIVE)));

                // listItemClass.setStatus(cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.STATUS)));
                dataMessage.add(listItemClass);


            } while (cursor.moveToNext());
        }


        return dataMessage;
    }

    public int getStatus(String phonenumber) {
        Cursor cursor = null;
        String z = "";
        int k = 0;
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            String[] column = {MyDataBaseHelper.UID, MyDataBaseHelper.MSSG_FROM, MyDataBaseHelper.MSSG_BODY,
                    MyDataBaseHelper.MSSG_time, MyDataBaseHelper.STATUS,MyDataBaseHelper.SENDORRECEEIVE};

            String sql = "SELECT  *,count(mssg_from) AS totalmessage " +
                    " FROM  " + MyDataBaseHelper.TABLE_NAME
                    + " WHERE" + " mssg_from ='" + phonenumber + "'" +
                    " AND flag= '0' " +
                    "group by mssg_from";

            cursor = db.rawQuery(sql, null);


            if (cursor != null && cursor.moveToFirst())
                z = cursor.getString(cursor.getColumnIndex("totalmessage"));
            if (!z.isEmpty())
            k = Integer.parseInt(z);
            Log.e("countstatus", String.valueOf(k));

        } catch (Exception e) {

            Log.d("helpman", "getStatus: " + e);
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return k;
    }


    public class MyDataBaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "vivzdatabase";
        private static final String TABLE_NAME = "VIVZTABLE";
        private static final String UID = "_id";
        private static final String MSSG_FROM = "mssg_from";
        private static final String MSSG_BODY = "mssg_body";
        private static final String MSSG_time = "mssg_time";
        private static final String STATUS = "flag";
        private static final String SENDORRECEEIVE = "send_receive";
        private static final int DATABASE_VERSION = 4;
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MSSG_FROM + " TEXT, " + MSSG_BODY + " TEXT, " + MSSG_time + " TEXT, "
                + STATUS + " INTEGER DEFAULT 0, "+SENDORRECEEIVE+" INTEGER);";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        Context context;

        MyDataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context, "onCreate called", Toast.LENGTH_LONG).show();

            } catch (SQLException e) {
                Toast.makeText(this.context, " " + e, Toast.LENGTH_LONG).show();


            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);

            } catch (SQLException e) {
                Toast.makeText(context, " " + e, Toast.LENGTH_LONG).show();

            }

        }

    }
}

