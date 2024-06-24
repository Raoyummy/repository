package com.example.wechat.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wechat.untity.User;

public class UserDbHelper extends SQLiteOpenHelper {

    // 以下是3个常量
    private static final String DATABASE_NAME = "weChat.db"; // 数据库名字
    private static final int DATABASE_VERSION = 1; // 数据库版本
    private static final String TABLE_NAME = "user"; // 数据表的名字

    // 构造器
    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表的SQL语句
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name varchar(50) unique,"
                + "phone varchar(11) unique,"
                + "email varchar(50))";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 插入数据
    public boolean insertData(User user) {
        // 获取db对象
        SQLiteDatabase db = this.getWritableDatabase();
        // 将要添加的数据封装到contentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("phone", user.getPhone());
        contentValues.put("email", user.getEmail());
        // 执行添加的SQL语句
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // 查询所有数据
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{"id", "name", "phone", "email"}, null, null, null, null, null);
    }

    // 通过用户名查询数据
    public Cursor getUserByUsername(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{"id", "name", "phone", "email"}, "name=?", new String[]{name}, null, null, null, null);
    }

    // 通过ID更新数据
    public boolean updateUserById(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("phone", user.getPhone());
        contentValues.put("email", user.getEmail());
        int result = db.update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(user.getId())});
        return result > 0;
    }

    // 通过用户名删除数据
    public boolean deleteUserByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "name = ?", new String[]{name});
        return result > 0;
    }
}
