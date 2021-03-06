package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * メインヴュー
 */

public class MainView extends RelativeLayout {
    private MenuView menuView;
//    private ReadButton readButton;
//    private StyleChangeButton styleChangeButton;
//    private CreateCardButton createCardButton;
    private TextView scheduleTitle;
    private ScheduleView scheduleView;

    DisplayMetrics metrics;

    public MainView(Context context, DisplayMetrics metrics) {
        super(context);

        this.metrics = metrics;
        final int MW = (int) (metrics.widthPixels * 0.2F);
        final int SW = (int) (metrics.widthPixels * 0.8F);
        final int STH = (int) (SW * 0.1F);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        menuView = new MenuView(getContext(), MW);
        menuView.setId(generateViewId());
        params = new LayoutParams(MW, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(menuView, params);

        scheduleTitle = new TextView(getContext());
        scheduleTitle.setId(generateViewId());
        params = new LayoutParams(SW, STH);
        params.addRule(RIGHT_OF, menuView.getId());
        scheduleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, STH * 0.8F);
        addView(scheduleTitle, params);

        scheduleView = new ScheduleView(getContext(), metrics);
        scheduleView.setId(generateViewId());
        scheduleView.setVerticalScrollBarEnabled(true);
        params = new LayoutParams(SW, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RIGHT_OF, menuView.getId());
        params.addRule(BELOW, scheduleTitle.getId());
        addView(scheduleView, params);

        menuView.getStyleChangeButton().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ScheduleView.ViewMode mode = scheduleView.getViewMode();

                if(scheduleView.getSchedule() != null) {
                    switch (mode) {
                        case Square:
                            scheduleView.setViewMode(ScheduleView.ViewMode.Rectangle);
                            break;
                        case Rectangle:
                            scheduleView.setViewMode(ScheduleView.ViewMode.Square);
                            break;
                    }
                }

                return false;
            }
        });
    }

    public void setSchedule(Schedule schedule){
        scheduleView.setSchedule(schedule, true);
        scheduleTitle.setText(schedule.getName());
    }

    public void setOnCreateCardButtonTouchListener(OnTouchListener l){
        menuView.getCreateCardButton().setOnTouchListener(l);
    }

    public void setOnCreateScheduleButtonTouchListener(OnTouchListener l){
        menuView.getCreateScheduleButton().setOnTouchListener(l);
    }

    public void setOnReadButtonTouchListener(OnTouchListener l){
        menuView.getReadButton().setOnTouchListener(l);
    }
}
