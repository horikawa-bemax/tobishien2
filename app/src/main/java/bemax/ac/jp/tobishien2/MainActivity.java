package bemax.ac.jp.tobishien2;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    TextView tv;
    ScrollView sv;
    LinearLayout ll;
    ArrayList<ImageView> ivs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display disp = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        disp.getSize(point);

        tv = (TextView)findViewById(R.id.textView);
        tv.setText(""+point.x+"*"+point.y);

        sv = (ScrollView)findViewById(R.id.scrollView);
        ll = (LinearLayout) findViewById(R.id.thisLayout);

        ivs = new ArrayList<ImageView>();
        for(int i=0; i<10; i++){
            ImageView view = new ImageView(this);
            view.setImageResource(R.drawable.yatta);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layout);
            ivs.add(view);
            ll.addView(view);
            view.setOnTouchListener(this);
        }

        //sv.setVerticalScrollbarPosition(5);
        //sv.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean res = false;
        if(view instanceof ImageView) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    sv.requestDisallowInterceptTouchEvent(true); // scrollViewのスクロールを無効化
                    Log.d("event", "douw");
                    res = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("event", "move");
                    float newX = view.getX() + motionEvent.getX() - view.getWidth() / 2;
                    tv.setText("");
                    view.setX(newX);
                    break;
                case MotionEvent.ACTION_UP:
                    //view.setX(0);
                    sv.requestDisallowInterceptTouchEvent(false); // scrollViewのスクロールを有効化
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
