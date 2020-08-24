package com.example.studentdatabasemanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "student.db";
    private static final String TABLE_NAME = "student_table";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ROLL = "roll";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table  " + TABLE_NAME + "(" +
                        COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_NAME + " varchar(10) ," +
                        COLUMN_ROLL + " varchar(4) unique, " +
                        COLUMN_PHONE + " varchar(15)," +
                        COLUMN_ADDRESS + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    long insertStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_ROLL, student.getRoll());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_PHONE, student.getPhone());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;

    }

    List<Student> getStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Student> students = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Student student = new Student();

                student.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                student.setRoll(cursor.getString(cursor.getColumnIndex(COLUMN_ROLL)));
                student.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                student.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));

              /*  Log.d("CHECKING_STUDENT_DATA",cursor.getInt(cursor.getColumnIndex(COLUMN_ID))+"");
                Log.d("CHECKING_STUDENT_DATA",cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                Log.d("CHECKING_STUDENT_DATA",cursor.getString(cursor.getColumnIndex(COLUMN_ROLL)));
                Log.d("CHECKING_STUDENT_DATA",cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                Log.d("CHECKING_STUDENT_DATA",cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));*/

                students.add(student);

            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return students;
    }

    Student getStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_ROLL, COLUMN_PHONE, COLUMN_ADDRESS};
        String selection = COLUMN_ID + " =?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            Student student = new Student();

            student.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            student.setRoll(cursor.getString(cursor.getColumnIndex(COLUMN_ROLL)));
            student.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            student.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            cursor.close();
            db.close();
            return student;

        } else {
            db.close();
            return null;
        }
    }

    int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_ROLL, student.getRoll());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_PHONE, student.getPhone());
        int noOfRowsUpdate = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{student.getId() + ""});
        db.close();
        return noOfRowsUpdate;
    }

    int deleteStudent(int stdId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int noOfRowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{stdId + ""});
        db.close();
        return noOfRowsDeleted;
    }
}


