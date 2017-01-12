package bemax.ac.jp.tobishien2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

public class MainActivity extends AppCompatActivity{

    Schedule schedule;          // スケジュールオブジェクト

    private SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ディスプレイのサイズを取得
        Display disp = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        disp.getMetrics(metrics);

        // データベース
        sqLiteOpenHelper = new MySQLiteOpenHelper(this);
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();

        // メインView
        MainView mainView = new MainView(this, metrics);
        setContentView(mainView);

        database.beginTransaction();
        try {
            Card c1 = Card.newCard(this, database, "聞く", Card.FolderTypeAsset, "kiku.gif");
            Card c2 = Card.newCard(this, database, "話す", Card.FolderTypeAsset, "hanasu.gif");
            Card c3 = Card.newCard(this, database, "電話", Card.FolderTypeStrage, "Camera/IMG_20170107_172518.jpg");
            Schedule.newSchedule(this, database, "初めてのスケジュール", new Card[]{c1, c2, c3});


            database.setTransactionSuccessful();
        }catch(SQLiteException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();

            schedule = Schedule.selectSchedule(this, database, "初めてのスケジュール");
            database.close();
        }

        //
        mainView.setSchedule(schedule);

        dump();

    }

    /**
     * ファイルからスケジュールを読み込む
     * @param filepath
     * @return
     */
    public Schedule loadScheduleFromFile(String filepath){

        return null;
    }

    private void dump(){

        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String[] fealds = {"_id","name","folderType","imageFile"};
        Cursor cursor = db.rawQuery("select * from cardTable", null);
        while(cursor.moveToNext()){
            StringBuffer sb = new StringBuffer("");
            sb.append("_id:" + cursor.getLong(cursor.getColumnIndex("_id")) + " ");
            sb.append("name:" + cursor.getString(cursor.getColumnIndex("name")) + " ");
            sb.append("folderType:" + cursor.getLong(cursor.getColumnIndex("folderType")) + " ");
            sb.append("imageFile:" + cursor.getString(cursor.getColumnIndex("imageFile")));
            Log.d("CardTable",sb.toString());
        }

    }
}
