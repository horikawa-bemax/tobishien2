package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bemax_ap01 on 2017/01/10.
 */

public class MainView extends RelativeLayout {
    private MenuView menuView;
    private TextView scheduleTitle;
    private ScheduleView scheduleView;

    DisplayMetrics metrics;

    public MainView(Context context, DisplayMetrics metrics) {
        super(context);
        this.metrics = metrics;

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        menuView = new MenuView(getContext());
        menuView.setId(generateViewId());
        addView(menuView);

        scheduleTitle = new TextView(getContext());
        scheduleTitle.setId(generateViewId());
        addView(scheduleTitle);

        scheduleView = new ScheduleView(getContext());
        scheduleView.setId(generateViewId());
        addView(scheduleView);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        if(!hasWindowFocus) return;

        int mw = (int) (getWidth() * 0.2F);
        int sw = (int) (getWidth() * 0.8F);
        int sth = (int) (sw * 0.1F);

        LayoutParams params;

        params = new LayoutParams(mw, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0,0);
        menuView.setLayoutParams(params);

        params = new LayoutParams(sw, sth);
        params.setMargins(mw, 0, 0, 0);
        scheduleTitle.setLayoutParams(params);
        scheduleTitle.setTextSize((int)(sth * 0.9F / metrics.density));

        params = new LayoutParams(sw, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(mw, sth + 10, 0, 0);
        scheduleView.setLayoutParams(params);
    }

    public void setSchedule(Schedule schedule){
        scheduleView.setSchedule(schedule);
        scheduleTitle.setText(schedule.getName());
    }
}
