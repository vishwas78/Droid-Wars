package com.example.vishwasmittal.droidwars;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DataBaseClass extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "db_hitlist.db";
    public static final String TABLE_HITLIST = "table_hitlist";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "_name";
    public static final String COL_HOUSE = "_house";
    public static final String COL_REASON = "_reason";
    public static final String COL_STATUS = "_status";
    public static final String COL_KILLWAY = "_killWay";
    public static final String COL_KILLPLACE = "_killPlace";
    public static final String COL_IMAGEADDR = "_imageAddr";


    public DataBaseClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        Log.e("Database class", "constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_HITLIST + " ( " +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_HOUSE + " TEXT NOT NULL, " +
                COL_REASON + " TEXT, " +
                COL_STATUS + " TEXT DEFAULT \"Not Killed\" NOT NULL, " +
                COL_KILLWAY + " TEXT, " +
                COL_KILLPLACE + " TEXT, " +
                COL_IMAGEADDR + " TEXT " +
                ");";
        db.execSQL(createQuery);
  //      db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String updateQuery = "DROP TABLE IF EXISTS " + TABLE_HITLIST + ";";
        db.execSQL(updateQuery);
        onCreate(db);
    }


    public String[][] returnListItems() {
        Log.e("Database class", "returnListItems() called");
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_HITLIST + ";", null);
        c.moveToFirst();

        String[][] temp = new String [3][c.getCount()];
         /* * temp [0] stores the names
            * temp [1] stores kill status
            * temp [2] stores image address
          */

        int i=0;
        while(!c.isAfterLast()){
            temp[0][i] = c.getString(c.getColumnIndex(COL_NAME));
            temp[1][i] = c.getString(c.getColumnIndex(COL_STATUS));
            temp[2][i] = c.getString(c.getColumnIndex(COL_IMAGEADDR));
            i++;
            c.moveToNext();
        }
        //db.close();
        return temp;
    }

    public String[][] returnSpinnerList(){
        Log.e("Database class", "returnSpinnerList() called");
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_HITLIST + ";", null);
        c.moveToFirst();

        String[][] temp = new String [2][c.getCount()];
         /* * temp [0] stores the id
            * temp [1] stores kill names
          */

        int i=0;
        while(!c.isAfterLast()){
            temp[0][i] = String.valueOf(c.getInt(c.getColumnIndex(COL_ID)));
            temp[1][i] = c.getString(c.getColumnIndex(COL_NAME));
            i++;
            c.moveToNext();
        }
        //db.close();
        return temp;
    }


    public int getNoOfItem(){
        Log.e("Database class", "getNoOfItem() called");
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_HITLIST + ";", null);
        c.moveToFirst();
        return c.getCount();
    }

    public int[] return_idList(){
        Log.e("Database class", "return_idList() called");
        int n = getNoOfItem();
        int[] temp = new int[n];
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_ID + " FROM " + TABLE_HITLIST +";", null);
        c.moveToFirst();
        int i=0;
        while(i<n){
            temp[i] = c.getInt(c.getColumnIndex(COL_ID));
            c.moveToNext();
            i++;
        }
        //db.close();
        return temp;
    }

    public TargetLayoutClass returnTargetInfo(int _id){
        Log.e("Database class", "returnTargetInfo() called");
        TargetLayoutClass tempInfo = new TargetLayoutClass();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_HITLIST + " WHERE " + COL_ID + " IS " + _id + ";", null);
        c.moveToFirst();

        tempInfo.set_id(c.getInt(c.getColumnIndex(COL_ID)));
        tempInfo.set_name(c.getString(c.getColumnIndex(COL_NAME)));
        tempInfo.set_reason(c.getString(c.getColumnIndex(COL_REASON)));
        tempInfo.set_status(c.getString(c.getColumnIndex(COL_STATUS)));
        tempInfo.set_killPlace(c.getString(c.getColumnIndex(COL_KILLPLACE)));
        tempInfo.set_killWay(c.getString(c.getColumnIndex(COL_KILLWAY)));
        tempInfo.set_imgAddr(c.getString(c.getColumnIndex(COL_IMAGEADDR)));
        //db.close();
        return tempInfo;
    }


    public void addNewTarget(TargetLayoutClass newTargetInfo) {
        Log.e("Database class", "addNewTarget() called");
        ContentValues values = new ContentValues();
        values.put(COL_NAME, newTargetInfo.get_name());
        values.put(COL_HOUSE, newTargetInfo.get_house());
        values.put(COL_REASON, newTargetInfo.get_reason());
        values.put(COL_STATUS, newTargetInfo.get_status());
        values.put(COL_KILLPLACE, newTargetInfo.get_killPlace());
        values.put(COL_KILLWAY, newTargetInfo.get_killWay());
        values.put(COL_IMAGEADDR, newTargetInfo.get_imgAddr());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_HITLIST, null, values);
        //db.close();
        return;
    }

    public void updateTarget(TargetLayoutClass targetInfo) {
        Log.e("Database class", "updateTarget() called");
        String updateQuery = "UPDATE " + TABLE_HITLIST + " SET " +
                COL_NAME + " = \'" + targetInfo.get_name() + "\' , " +
                COL_REASON + " = \'" + targetInfo.get_reason() + "\' , " +
                COL_HOUSE + " = \'" + targetInfo.get_house() + "\' , " +
                COL_KILLPLACE + " = \'" + targetInfo.get_killPlace() + "\' , " +
                COL_KILLWAY + " = \'" + targetInfo.get_killWay() + "\' , " +
                COL_STATUS + " = \'" + targetInfo.get_status() + "\' , " +
                COL_IMAGEADDR + " = \'" + targetInfo.get_imgAddr() + "\'" +
                " WHERE " + COL_ID + " = " + targetInfo.get_id() + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(updateQuery);
        //db.close();
    }

    public void deleteTarget(final Context context, final int _id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Caution!")
                .setMessage("This target will be deleted...")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getWritableDatabase();
                        db.execSQL("DELETE FROM " + TABLE_HITLIST + " WHERE " + COL_ID + " = " + _id + ";");
                        db.close();
                        Toast.makeText(context, "Target Removed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Operation Cancelled", Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .create()
                .show();
    }

}
