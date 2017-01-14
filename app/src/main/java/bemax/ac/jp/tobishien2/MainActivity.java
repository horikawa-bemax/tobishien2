package bemax.ac.jp.tobishien2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    private MainView mainView;
    private CreateCardView createCardView;

    Schedule schedule;          // スケジュールオブジェクト

    private SQLiteOpenHelper sqLiteOpenHelper;

    private final int GALARY_CODE = 1000;

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
        mainView = new MainView(this, metrics);
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

        //dump();

        ScheduleSelectView ssv = new ScheduleSelectView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(ssv, params);
        ssv.setVisibility(View.INVISIBLE);

        //
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        createCardView = new CreateCardView(this, metrics);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(createCardView, params1);

        final Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        final Activity thisActivity = this;
        createCardView.setOnAddButtonTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                thisActivity.startActivityForResult(intent, GALARY_CODE);
                Log.d("Intent", "CALL");
                return false;
            }
        });

        //startActivityForResult(intent, GALARY_CODE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case GALARY_CODE:
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        createCardView.postImageView(bitmap);
                    }catch(IOException e){}
                }
                break;
        }
    }
}
