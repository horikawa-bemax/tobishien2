package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by bemax_ap01 on 2016/12/12.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    static final String DB = "tobishien.db";
    static final int DB_VERSION = 9;

    public static final String CARD_TABLE = "cardTable";
    public static final String SCHEDULE_TABLE = "scheduleTable";
    public static final String INTO_CARD_TABLE = "intoCardTable";

    static final String CREATE_CARD_TABLE = "create table cardTable( _id integer primary key autoincrement, name string not null unique, folderType integer not null, imageFile string not null);";
    static final String CREATE_SCHEDULE_TABLE = "create table scheduleTable( _id integer primary key autoincrement, name string not null unique);";
    static final String CREATE_SCHEDULE_INTO_CARD_TABLE = "create table intoCardTable( schedule_id integer not null, card_id integer not null, rank integer not null);";

    private File pictureFolder;
    private AssetManager assetManager;

    static public final long ASSETS = 1;
    static public final long PICTURE = 2;

    public MySQLiteOpenHelper(Context context){
        super(context, DB, null, DB_VERSION);
        //pictureFolder = Environment.getExternalStorageDirectory();
        //assetManager = context.getAssets();
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CARD_TABLE);
        sqLiteDatabase.execSQL(CREATE_SCHEDULE_TABLE);
        sqLiteDatabase.execSQL(CREATE_SCHEDULE_INTO_CARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table intoCardTable;");
        sqLiteDatabase.execSQL("drop table cardTable;");
        sqLiteDatabase.execSQL("drop table scheduleTable;");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from cardTable;", null);
        while(cursor.moveToNext()){
            Log.d("cardTable", "" + cursor.getLong(0) + "," + cursor.getString(1) + "," + cursor.getLong(2) + "," + cursor.getString(3));
        }
    }
}
