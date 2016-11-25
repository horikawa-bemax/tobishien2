package bemax.ac.jp.tobishien2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    ScrollView  scrollView;
    LinearLayout scrollBase;
    TextView scheduleTitle;
    Point displaySize;

    Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ディスプレイのサイズを取得
        Display disp = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        disp.getSize(displaySize);

        // コンテンツ領域のサイズを取得


        // スケジュールタイトル
        scheduleTitle = (TextView)findViewById(R.id.scheduleTitle);

        // スクロールビュー
        scrollView = (ScrollView)findViewById(R.id.scrollView);     // スクロールView本体
        scrollBase = (LinearLayout) findViewById(R.id.scrollBase);  // スクロールViewのコンテンツベース

        // テスト用サンプル作成
        schedule = new Schedule("初めてのスケジュール");
        for(int i=0; i<8; i++){
            Card card = new Card("バンザイ"+i, BitmapFactory.decodeResource(getResources(), R.drawable.yatta));
            schedule.addScheduleCard(card);
        }

        // スケジュール表示（サンプル）
        scheduleTitle.setText(schedule.getName());
        for(Card card: schedule.getScheduleList()) {
            View view = getCardView(card, Card.Type.Square, scrollBase);   // scrollBaseに追加したCardView
            view.setOnTouchListener(this);
            scrollBase.addView(view);
        }

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
     * カードオブジェクトからCardViewを作成する
     * @param card  カードオブジェクト
     * @param type  カードタイプ。正方形（square）、長方形（rectangle）
     * @param root  カード Viewの親View
     * @return View
     */
    public View getCardView(Card card, Card.Type type, ViewGroup root){
        View view = null;
        switch(type){
            case Square:
                view = getLayoutInflater().inflate(R.layout.squarecardlayout, root, false);
                break;
            case Rectangle:
                view = getLayoutInflater().inflate(R.layout.rectanglecardlayout, root, false);
                break;
            default:
                return null;
        }

        // テキストを入力
        TextView tv = (TextView)view.findViewById(R.id.cardText);
        tv.setText(card.getTitle());

        // 画像を入力
        ImageView iv = (ImageView)view.findViewById(R.id.cardImage);
        iv.setImageBitmap(card.getImage());

        return view;
    }

    @Override
    /**
     * タッチイベントの処理
     *
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean res = false;

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollView.requestDisallowInterceptTouchEvent(true); // scrollViewのスクロールを無効化
                //Log.d("event", "douw");
                res = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d("event", "move");
                float newX = view.getX() + motionEvent.getX() - view.getWidth() / 2;
                view.setX(newX);
                break;
            case MotionEvent.ACTION_UP:
                scrollView.requestDisallowInterceptTouchEvent(false); // scrollViewのスクロールを有効化
                //Log.d("event", "up");
                if(view.getX() < 400)
                    view.setX(0);
                else{
                   // schedule.removeCard(view.getCard());
                }
                res = false;
                break;
            default:
                Log.d("event", "default:" + motionEvent.getAction());
                res = true;
        }
        return res;//super.onTouchEvent(motionEvent);
    }
}
