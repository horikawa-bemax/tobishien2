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

        ll = (LinearLayout) findViewById(R.id.thisLayout);

        ivs = new ArrayList<ImageView>();
        for(int i=0; i<10; i++){
            ImageView view = new ImageView(this);
            view.setImageResource(R.drawable.yatta);
            ivs.add(view);
            ll.addView(view);
            view.setOnTouchListener(this);
        }



    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean res = false;
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    res = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newX = view.getX() + motionEvent.getX();
                    tv.setText("" + newX);
                    view.setX(newX);
                    break;
                case MotionEvent.ACTION_UP:
                    view.setX(0);
                    res = false;
            }
        return res;//super.onTouchEvent(motionEvent);
    }
}
