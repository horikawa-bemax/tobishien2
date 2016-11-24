package bemax.ac.jp.tobishien2;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    ScrollView  scrollView;
    LinearLayout scrollBase;
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

        LayoutInflater inflater = LayoutInflater.from(this);

        // スクロールビュー
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollBase = (LinearLayout) findViewById(R.id.scrollBase);

        // テスト用コード
        for(int i=0; i<5; i++) {
            Card card = new Card("ばんざい", this.getResources(), R.drawable.yatta);
            View view = card.getCardView(inflater, scrollBase, Card.Type.Square);   // scrollBaseに追加したCardView
            view.setOnTouchListener(this);
            scrollBase.addView(view);
        }

        // スケジュール読み込み

        // スケジュール表示

    }

    @Override
    /**
     * タッチイベントの処理
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean res = false;
        if(view instanceof View) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    scrollView.requestDisallowInterceptTouchEvent(true); // scrollViewのスクロールを無効化
                    Log.d("event", "douw");
                    res = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("event", "move");
                    float newX = view.getX() + motionEvent.getX() - view.getWidth() / 2;
                    view.setX(newX);
                    break;
                case MotionEvent.ACTION_UP:
                    //view.setX(0);
                    scrollView.requestDisallowInterceptTouchEvent(false); // scrollViewのスクロールを有効化
                    Log.d("event", "up");
                    res = false;
                    break;
                default:
                    Log.d("event", "default:" + motionEvent.getAction());
                    res = true;
            }
        }
        return res;//super.onTouchEvent(motionEvent);
    }
}
