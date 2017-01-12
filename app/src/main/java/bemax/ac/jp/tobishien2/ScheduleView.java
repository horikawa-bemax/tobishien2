package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by bemax_ap01 on 2017/01/10.
 */

public class ScheduleView extends ScrollView implements View.OnTouchListener{

    private Schedule schedule;

    private LinearLayout linearLayout;

    GestureDetector gestureDetector;
    boolean flick;

    public ScheduleView(Context context) {
        super(context);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setId(getResources().getIdentifier("schedule_linearLayout", "id", "bemax.ac.jp.tobishien2"));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(linearLayout, params);

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float vx, float vy) {
                float v = event2.getX() - event1.getX();
                Log.d("Gesuture","" + v);

                if(v > 300 && vx > 1000){
                    flick = true;
                }
                return true;
            }
        });
    }

    public void setSchedule(Schedule schedule){
        this.schedule = schedule;

        // 全ての子Viewを削除
        linearLayout.removeAllViews();

        for(Card card: schedule.getScheduleCardList()){
            CardView cardView = new CardView(getContext(), card);
            cardView.setBackgroundColor(Color.rgb(128, 128, 255));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(600, 600);
            params.setMargins(20, 20, 20, 20);
            linearLayout.addView(cardView, params);
            cardView.setOnTouchListener(this);
        }
    }

    public void addCardView(CardView cardView){

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean res = false;
        int index = linearLayout.indexOfChild(view);
        Log.d("touch", "touch");

        gestureDetector.onTouchEvent(motionEvent);

        if(flick){
            view.setVisibility(View.GONE);
            flick = false;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            requestDisallowInterceptTouchEvent(true);
            Log.d("touch", "down");
            res = true;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            requestDisallowInterceptTouchEvent(false);
            Log.d("touch", "up");
            res = false;
        }

        return res;


        /*
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                requestDisallowInterceptTouchEvent(true); // scrollViewのスクロールを無効化
                Log.d("event", "douw" + index);
                res = true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("event", "move" + index);
                float newX = view.getX() + motionEvent.getX() - view.getWidth() / 2;
                view.setX(newX);
                break;
            case MotionEvent.ACTION_UP:
                requestDisallowInterceptTouchEvent(false); // scrollViewのスクロールを有効化
                Log.d("event", "up" + index);
                if(view.getX() < getWidth() / 2)
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
        */

    }
}
