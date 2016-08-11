package com.example.wordmaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "word";

    private static final String DATABASE_NAME = "wordDB";
    private static final String DATABASE_TABLE = "wordsTable";
    private static final int DATABASE_VERSION = 1;


    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL);"

            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DatabaseHelper(Context c) {
        ourContext = c;
    }

    public DatabaseHelper open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long saveEntry(String name) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public Cursor getData() {
        String[] columns = new String[]{KEY_ROWID, KEY_NAME};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        return c;
    }

    public void deleteWord(long lRow) throws SQLException {
        ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow, null);
    }
}
