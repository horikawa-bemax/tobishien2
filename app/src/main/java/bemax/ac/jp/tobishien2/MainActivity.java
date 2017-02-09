package bemax.ac.jp.tobishien2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private MainView mainView;
    private CreateCardView createCardView;
    private ScheduleSelectView scheduleSelectView;
    private CreateScheduleView createScheduleView;

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

//        Log.d("metorics","" + metrics.scaledDensity);

        // データベース
        sqLiteOpenHelper = new MySQLiteOpenHelper(this);
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();

        // メインView
        mainView = new MainView(this, metrics);
        setContentView(mainView);

 /*       database.beginTransaction();
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
*/
        // スケジュールセレクト
        scheduleSelectView = new ScheduleSelectView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(scheduleSelectView, params);
        scheduleSelectView.setVisibility(View.INVISIBLE);
        scheduleSelectView.setOnItemClickListener(this);

        // カード新規作成
        createCardView = new CreateCardView(this, sqLiteOpenHelper, metrics);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params1.setMargins(100,100,100,100);
        addContentView(createCardView, params1);
        createCardView.setVisibility(View.INVISIBLE);

        // スケジュール作成画面
        // スケジュール選択画面のアダプターを取得
        ArrayAdapter<String> adapter = scheduleSelectView.getListView().getAdapter();
        // スケジュール作成画面をインスタンス化
        createScheduleView = new CreateScheduleView(this, sqLiteOpenHelper, adapter);
        // レイアウト
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params2.setMargins(100,100,100,100);
        // コンテンツに追加
        addContentView(createScheduleView, params2);
        createScheduleView.setVisibility(View.INVISIBLE);


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

        mainView.setOnCreateCardButtonTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                createCardView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mainView.setOnCreateScheduleButtonTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                createScheduleView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mainView.setOnReadButtonTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scheduleSelectView.setVisibility(View.VISIBLE);
                return false;
            }
        });


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
                        Bitmap bitmap = null;
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        InputStream is = getContentResolver().openInputStream(uri);
                        BitmapFactory.decodeStream(is, null, options);
                        is.close();
                        int scaleX = options.outWidth / 300;
                        int scaleY = options.outHeight / 300;
                        options = new BitmapFactory.Options();

                        int scale = scaleX < scaleY ? scaleX : scaleY;
                        if(scaleX > 2 && scaleY > 2){
                            for(int i=2; i<scale; i*=2) options.inSampleSize = i;
                            is = getContentResolver().openInputStream(uri);
                            bitmap = BitmapFactory.decodeStream(is, null, options);
                        }else {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        }
                        String imagePath = uri.getPath();
                        createCardView.postImageView(bitmap);
                    }catch(IOException e){}
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        scheduleSelectView.setVisibility(View.INVISIBLE);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        String s = (String)adapterView.getItemAtPosition(i);
        schedule = Schedule.selectSchedule(this, db, s);
        mainView.setSchedule(schedule);
        db.close();
    }
}
