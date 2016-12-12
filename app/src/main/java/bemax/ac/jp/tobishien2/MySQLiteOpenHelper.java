package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;

import java.io.File;
import java.sql.PreparedStatement;

/**
 * Created by bemax_ap01 on 2016/12/12.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    static final String DB = "tobishien.db";
    static final int DB_VERSION = 3;

    static final String CARD_TABLE = "cardTable";
    static final String SCHEDULE_TABLE = "scheduleTable";
    static final String INTO_CARD_TABLE = "intoCardTable";

    static final String CREATE_CARD_TABLE = "create table cardTable( _id integer primary key autoincrement, name string not null, folderType integer not null, imageFile string not null );";
    static final String CREATE_SCHEDULE_TABLE = "create table scheduleTable( _id integer primary key autoincrement, name string not null);";
    static final String CREATE_SCHEDULE_INTO_CARD_TABLE = "create table intoCardTable( schedule_id integer, card_id integer, rank integer);";

    private File pictureFolder;
    private AssetManager assetManager;

    enum folder{ASSETS, PICTURE};

    public MySQLiteOpenHelper(Context context){
        super(context, DB, null, DB_VERSION);
        pictureFolder = Environment.getExternalStorageDirectory();
        assetManager = context.getAssets();
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

        SQLiteStatement statement = sqLiteDatabase.compileStatement("insert into cardTable (name, folderType, imageFile) values (?,?,?);");
        statement.bindString(1, "話す");
        statement.bindLong(2, folder.ASSETS.ordinal());
        statement.bindString(3, "hanasu.gif");
        long card1 = statement.executeInsert();

        //statement = sqLiteDatabase.compileStatement("insert into cardTable values (?,?,?)");
        statement.bindString(1, "聞く");
        statement.bindLong(2, folder.ASSETS.ordinal());
        statement.bindString(3, "kiku.gif");
        long card2 = statement.executeInsert();

        statement = sqLiteDatabase.compileStatement("insert into scheduleTable (name) values (?);");
        statement.bindString(1, "初めてのスケジュール");
        long schedule1 = statement.executeInsert();

        statement = sqLiteDatabase.compileStatement("insert into intoCardTable values (?,?,?);");
        statement.bindLong(1, schedule1);
        statement.bindLong(2, card1);
        statement.bindLong(3, 1);
        statement.executeInsert();

        statement.bindLong(1, schedule1);
        statement.bindLong(2, card2);
        statement.bindLong(3, 2);
        statement.executeInsert();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table intoCardTable;");
        sqLiteDatabase.execSQL("drop table cardTable;");
        sqLiteDatabase.execSQL("drop table scheduleTable;");
        onCreate(sqLiteDatabase);
    }
}
