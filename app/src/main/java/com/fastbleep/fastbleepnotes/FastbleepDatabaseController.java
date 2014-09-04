package com.fastbleep.fastbleepnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ianar on 03/09/2014.
 */
public class FastbleepDatabaseController extends SQLiteOpenHelper {

    String KEY_FASTBLEEP_ID = "fastbleepid";
    String KEY_TITLE = "title";
    String KEY_CONTENT = "content";
    String TABLE_FASTBLEEP_NOTES = "fastbleepnotes";

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "fastbleep";

    public FastbleepDatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_FASTBLEEP_NOTES + "( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fastbleepid INTEGER, " +
                "title TEXT, " +
                "content TEXT )";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FASTBLEEP_NOTES);

        this.onCreate(db);
    }

    public void cleanDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FASTBLEEP_NOTES);

        this.onCreate(db);
    }

    public void addFavorite(RowItem item) {
        Log.d("log", item.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FASTBLEEP_ID, item.getId());
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_CONTENT, item.getContent().replace("\n", "").replace("\r", "").replace("\"", "\'").replace(",", ""));

        // 3. insert
        db.insert(TABLE_FASTBLEEP_NOTES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public List getAllFavorites() {
        List<RowItem> articles = new LinkedList<RowItem>();
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FASTBLEEP_NOTES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        RowItem article = null;
        if (cursor.moveToFirst()) {
            do {
                article = new RowItem();
                article.setId((cursor.getInt(cursor.getColumnIndex("fastbleepid"))));
                article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                article.setContent(cursor.getString(cursor.getColumnIndex("content")));

                articles.add(article);
            } while (cursor.moveToNext());
        }

        Log.d("getAllFavorites()", articles.toString());

        return articles;
    }
}