package bemax.ac.jp.tobishien2;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    ScrollView  scrollView;     // スクロールView（スクロール関連の操作を行う）
    ViewGroup scheduleLayout;   // スケジュールを置くViewGroup（カードの配置順を取得できる）
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
        setContentView(R.layout.activity_main);

        // ディスプレイのサイズを取得
        Display disp = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        disp.getSize(displaySize);
        Log.d("displaySize","w=" + displaySize.x + ",h=" + displaySize.y);

        // 画像の保存先パス
        pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //Log.d("picturePath",pictureDir.getPath());
        // アセットマネージャ
        assetManager = getAssets();

        // データベース
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        database = helper.getWritableDatabase();

        // スケジュールタイトル
        scheduleTitle = (TextView)findViewById(R.id.scheduleTitle);

        // スクロールビュー
        scrollView = (ScrollView)findViewById(R.id.scrollView);     // スクロールView本体

        // テスト用のスケジュールサンプル作成
        schedule = new Schedule("初めてのスケジュール");
        for(int i=0; i<8; i++){
            Card card = new Card("聞く"+i, BitmapFactory.decodeResource(getResources(), R.drawable.kiku));
            schedule.addScheduleCard(card);
        }

        // スケジュール表示
        scheduleTitle.setText(schedule.getName());
        scheduleLayout = Schedule.createScheduleLayout(scrollView, schedule, Card.Style.Square);

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

    @Override
    /**
     * タッチイベントの処理
     *
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean res = false;
        int index = scheduleLayout.indexOfChild(view);

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollView.requestDisallowInterceptTouchEvent(true); // scrollViewのスクロールを無効化
                Log.d("event", "douw" + index);
                res = true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("event", "move" + index);
                float newX = view.getX() + motionEvent.getX() - view.getWidth() / 2;
                view.setX(newX);
                break;
            case MotionEvent.ACTION_UP:
                scrollView.requestDisallowInterceptTouchEvent(false); // scrollViewのスクロールを有効化
                Log.d("event", "up" + index);
                if(view.getX() < scrollSize.x / 2)
                    view.setX(0);
                else{
                    view.setVisibility(View.GONE);  // 間隔を詰めて非表示
                }
                res = false;
                break;
            default:
                Log.d("event", "default:" + motionEvent.getAction());
                res = true;
        }

        return res;//super.onTouchEvent(motionEvent);
    }

    /**
     * コンテンツ領域の大きさが決まったタイミングで行う処理
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ViewGroup vg = (ViewGroup)findViewById(R.id.activity_main);
        contentsSize = new Point(vg.getWidth(), vg.getHeight());
        Log.d("contentsSize","w=" + vg.getWidth() + ",h=" + vg.getHeight());
        scrollSize = new Point(scrollView.getWidth(), scrollView.getHeight());
        Log.d("scrollSize","w=" + scrollSize.x + ",h=" + scrollSize.y);
    }
}
