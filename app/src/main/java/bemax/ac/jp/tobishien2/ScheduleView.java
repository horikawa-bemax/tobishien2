package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
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
    private int width;
    private int[] visible;

    enum ViewMode {Square, Rectangle};
    private ViewMode viewMode;

    private LinearLayout linearLayout;

    GestureDetector gestureDetector;
    boolean flick;

    public ScheduleView(Context context, int width) {
        super(context);

        this.width = width;
        this.viewMode = ViewMode.Rectangle;

        linearLayout = new LinearLayout(getContext());
        linearLayout.setId(getResources().getIdentifier("schedule_linearLayout", "id", "bemax.ac.jp.tobishien2"));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(linearLayout, params);

        final int flicWidth = width / 3;
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

                if(v > flicWidth && vx > flicWidth * 5){
                    flick = true;
                }
                return true;
            }
        });
    }

    public void setSchedule(Schedule schedule, boolean isNew){
        this.schedule = schedule;

        // 全ての子Viewを削除
        linearLayout.removeAllViews();

        final int W = (int) (width * 0.7F);
        LinearLayout.LayoutParams params;

        int count = schedule.getScheduleCardList().size();

        if(isNew){
            visible = new int[count];
            for(int i=0; i<count; i++){ visible[i] = 1; }
        }

        for(int i=0; i<count; i++){
            Card card = schedule.getScheduleCardList().get(i);
            switch(viewMode) {
                case Square:
                    SquareCardView sCardView = new SquareCardView(getContext(), card, W);
                    sCardView.setBackgroundColor(Color.rgb(128, 128, 255));
                    params = new LinearLayout.LayoutParams(W, W);
                    params.setMargins(20, 20, 20, 20);
                    linearLayout.addView(sCardView, params);
                    sCardView.setOnTouchListener(this);
                    if(visible[i] == 1){
                        sCardView.setVisibility(VISIBLE);
                    }else{
                        sCardView.setVisibility(GONE);
                    }
                    break;
                case Rectangle:
                    RectangleCardView rCardView = new RectangleCardView(getContext(), card, W);
                    rCardView.setBackgroundColor(Color.rgb(128, 128, 255));
                    params = new LinearLayout.LayoutParams(W, (int) (W * 0.2F));
                    params.setMargins(20, 20, 20, 20);
                    linearLayout.addView(rCardView, params);
                    rCardView.setOnTouchListener(this);
                    if(visible[i] == 1){
                        rCardView.setVisibility(VISIBLE);
                    }else{
                        rCardView.setVisibility(GONE);
                    }
                    break;
            }
        }
    }

    public void addCardView(SquareCardView cardView){

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean res = false;
        int index = linearLayout.indexOfChild(view);
        Log.d("touch", "touch");

        gestureDetector.onTouchEvent(motionEvent);

        if(flick){
            view.setVisibility(View.GONE);
            visible[index] = 0;
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
    }

    public void setViewMode(ViewMode mode){
        this.viewMode = mode;
        setSchedule(this.schedule, false);
    }

    public ViewMode getViewMode(){
        return viewMode;
    }
}
