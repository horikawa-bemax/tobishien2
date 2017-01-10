package bemax.ac.jp.tobishien2;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;


public class MainActivity extends AppCompatActivity{
    ScheduleView scheduleView;     // スクロールView（スクロール関連の操作を行う）
    ViewGroup scheduleLayout;   // スケジュールを置くViewGroup（カードの配置順を取得できる）
    MenuView menuView;
    TextView scheduleTitle;     // スケジュール名を表示するTextView
    Point displaySize;          // ディスプレイのサイズ
    Point contentsSize;         // コンテンツ領域のサイズ
    Point scrollSize;           // スクロールViewのサイズ

    Schedule schedule;          // スケジュールオブジェクト

    private File filesDir;
    private File externalFilseDir;
    private File pictureDir;
    private AssetManager assetManager;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ディスプレイのサイズを取得
        Display disp = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        disp.getSize(displaySize);

        // データベース
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        database = helper.getWritableDatabase();

        // メインView
        MainView mainView = new MainView(this);
        setContentView(mainView);
        RelativeLayout.LayoutParams params;

        // メニューView
        menuView = new MenuView(this);
        menuView.setId(View.generateViewId());
        params = new RelativeLayout.LayoutParams(displaySize.x / 5, displaySize.y);
        mainView.addView(menuView, params);

        // スケジュールタイトル
        scheduleTitle = new TextView(this);
        scheduleTitle.setId(View.generateViewId());
        params = new RelativeLayout.LayoutParams(displaySize.x * 4 / 5, 100);
        params.addRule(RelativeLayout.RIGHT_OF, menuView.getId());
        scheduleTitle.setText("");
        mainView.addView(scheduleTitle, params);

        // スクロールビュー
        scheduleView = new ScheduleView(this);
        scheduleView.setId(View.generateViewId());

        // テスト用のスケジュールサンプル作成
        database.beginTransaction();
        try {
            Card c1 = Card.newCard(this, database, "聞く", Card.FolderTypeAsset, "kiku.gif");
            Card c2 = Card.newCard(this, database, "話す", Card.FolderTypeAsset, "hanasu.gif");
            Card c3 = Card.newCard(this, database, "電話", Card.FolderTypeStrage, "Camera/IMG_20170107_172518.jpg");
            Schedule.newSchedule(this, database, "初めてのスケジュール", new Card[]{c1, c2, c3});
        }catch(SQLiteException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
        schedule = Schedule.selectSchedule(this, database, "初めてのスケジュール");
        scheduleTitle.setText(schedule.getName());
        scheduleView.setSchedule(schedule);

        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.RIGHT_OF, menuView.getId());
        params.addRule(RelativeLayout.BELOW, scheduleTitle.getId());
        params.setMargins(10, 10, 10, 10);
        mainView.addView(scheduleView, params);

        // スケジュール表示
        //scheduleLayout = Schedule.createScheduleLayout(scrollView, schedule, Card.Style.Square);

        // スケジュール読み込み

        // スケジュール表示

    }

    /**
     * ファイルからスケジュールを読み込む
     * @param filepath
     * @return
     */
    public Schedule loadScheduleFromFile(String filepath){

        return null;
    }

    /**
     * ファイルから画像を読み込む
     * @param filePath
     * @return
     */
    public Bitmap loadBitmapFromFile(String filePath){

        return null;
    }

    /**
     * コンテンツ領域の大きさが決まったタイミングで行う処理
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        ViewGroup vg = (ViewGroup)findViewById(R.id.activity_main);
//        contentsSize = new Point(vg.getWidth(), vg.getHeight());
//        Log.d("contentsSize","w=" + vg.getWidth() + ",h=" + vg.getHeight());
//        scrollSize = new Point(scheduleView.getWidth(), scheduleView.getHeight());
//        Log.d("scrollSize","w=" + scrollSize.x + ",h=" + scrollSize.y);
    }
}
