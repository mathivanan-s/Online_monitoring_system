package com.lakshmiindustrialautomation.www.lit.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lakshmiindustrialautomation.www.lit.Adapters.NormalAlert;

/**
 * Created by Steephan Selvaraj on 5/3/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private final static String REG_TOKEN = "REG_TOKEN";
    public static final String DATABASE_NAME = "Alerts.dp";
    public static final String FIRST_TABLE_NAME = "NormalAlertsTable";
    public static final String SECOND_TABLE_NAME = "StoppageAlertsTable";
    public static final String THIRD_TABLE_NAME = "UserDetailsTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "BODY";
    public static final String USER_TABLE_COL_1 = "ID";
    public static final String USER_TABLE_COL_2 = "IS_AUTHENTICATED";
    public static final String USER_TABLE_COL_3 = "IS_STOPPAGE_MESSAGE_AVAILABLE_TO_READ";
    public static final String USER_TABLE_COL_4 = "IS_NORMAL_MESSAGE_AVAILABLE_TO_READ";
    public static final String USER_TABLE_COL_5 = "NOTIFICATION_TONE";
    public static final String USER_TABLE_COL_6 = "IS_STOPPAGE_VIBRATE_ENABLED";
    public static final String USER_TABLE_COL_7 = "IS_NORMAL_VIBRATE_ENABLED";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + FIRST_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, BODY TEXT)");
        sqLiteDatabase.execSQL("create table " + SECOND_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, BODY TEXT)");
        sqLiteDatabase.execSQL("create table " + THIRD_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IS_AUTHENTICATED TEXT, IS_STOPPAGE_MESSAGE_AVAILABLE_TO_READ TEXT, " +
                "IS_NORMAL_MESSAGE_AVAILABLE_TO_READ TEXT, NOTIFICATION_TONE TEXT, " +
                "IS_STOPPAGE_VIBRATE_ENABLED TEXT, IS_NORMAL_VIBRATE_ENABLED)");
        sqLiteDatabase.execSQL("INSERT INTO " + THIRD_TABLE_NAME + "(" + USER_TABLE_COL_2 + ", " + USER_TABLE_COL_3 + ", " + USER_TABLE_COL_4 + ", " + USER_TABLE_COL_5 + ", " + USER_TABLE_COL_6 + ", " + USER_TABLE_COL_7 + ") VALUES ('false', 'false','false', 'none', 'true', 'false')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FIRST_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SECOND_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + THIRD_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertData(String title_String, String body_string, String table_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title_String);
        contentValues.put(COL_3, body_string);
        sqLiteDatabase.insert(table_name, null, contentValues);
    }

    public String updateUserPreferenceDetails(String ringtone_path,
                                              Boolean is_stoppage_notification_vibrate_enabled,
                                              Boolean is_normal_notification_vibrate_enabled){
        String error = "no_error";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(USER_TABLE_COL_5, ringtone_path);
        dataToInsert.put(USER_TABLE_COL_6, is_stoppage_notification_vibrate_enabled.toString());
        dataToInsert.put(USER_TABLE_COL_7, is_normal_notification_vibrate_enabled.toString());
        try{
            sqLiteDatabase.update(THIRD_TABLE_NAME, dataToInsert, "ID=1", null);
        }
        catch (Exception e){
            error =  e.getMessage().toString();
        }
        return error;
    }

    public Boolean isStoppageNotificationAvailable() {
        String[] variable = {USER_TABLE_COL_1, USER_TABLE_COL_2, USER_TABLE_COL_3, USER_TABLE_COL_4};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(THIRD_TABLE_NAME, variable,null,null,null,null,null);
        cursor.moveToFirst();
        return Boolean.valueOf(cursor.getString(2));
    }

    public Boolean isNormalNotificationAvailable() {
        String[] variable = {USER_TABLE_COL_1, USER_TABLE_COL_2, USER_TABLE_COL_3, USER_TABLE_COL_4};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(THIRD_TABLE_NAME, variable,null,null,null,null,null);
        cursor.moveToFirst();
        return Boolean.valueOf(cursor.getString(3));
    }

    public Boolean isAuthenticated() {
        String[] variable = {USER_TABLE_COL_1, USER_TABLE_COL_2, USER_TABLE_COL_3, USER_TABLE_COL_4};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(THIRD_TABLE_NAME, variable,null,null,null,null,null);
        cursor.moveToFirst();
        return Boolean.valueOf(cursor.getString(1));
    }

    public Boolean is_stoppage_notification_vibrate_enabled() {
        String[] variable = {USER_TABLE_COL_1, USER_TABLE_COL_2, USER_TABLE_COL_3,
                USER_TABLE_COL_4, USER_TABLE_COL_5, USER_TABLE_COL_6, USER_TABLE_COL_7};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(THIRD_TABLE_NAME, variable,null,null,null,null,null);
        cursor.moveToFirst();
        return Boolean.valueOf(cursor.getString(5));
    }

    public String  getCurrentNotificationTone() {
        String[] variable = {USER_TABLE_COL_1, USER_TABLE_COL_2, USER_TABLE_COL_3,
                USER_TABLE_COL_4, USER_TABLE_COL_5, USER_TABLE_COL_6, USER_TABLE_COL_7};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(THIRD_TABLE_NAME, variable,null,null,null,null,null);
        cursor.moveToFirst();
        return cursor.getString(4);
    }

    public Boolean is_normal_notification_vibrate_enabled() {
        String[] variable = {USER_TABLE_COL_1, USER_TABLE_COL_2, USER_TABLE_COL_3,
                USER_TABLE_COL_4, USER_TABLE_COL_5, USER_TABLE_COL_6, USER_TABLE_COL_7};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(THIRD_TABLE_NAME, variable,null,null,null,null,null);
        cursor.moveToFirst();
        return Boolean.valueOf(cursor.getString(6));
    }

    public String updateUserDetails(String column_name, Boolean state){
        String error = "no_error";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(column_name, state.toString());
        try{
            sqLiteDatabase.update(THIRD_TABLE_NAME, dataToInsert, "ID=1", null);
        }
        catch (Exception e){
            error =  e.getMessage().toString();
        }
        return error;
    }

    public Cursor getAllAlerts(String table_name) {
        String[] variable = {COL_1, COL_2, COL_3};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(table_name, variable,null,null,null,null,null);
        return cursor;
    }

    public void deleteAllNotifications(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(table_name, null, null);
        } catch (Exception e)
        {
        }
        db.close();
    }
}
