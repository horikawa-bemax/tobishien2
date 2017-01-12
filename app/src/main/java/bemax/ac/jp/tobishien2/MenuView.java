package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by bemax_ap01 on 2017/01/10.
 */

public class MenuView extends RelativeLayout {

    private ImageView styleChangeButton;
    private ImageView createButton;
    private ImageView readButton;

    public MenuView(Context context) {
        super(context);

        setBackgroundColor(Color.RED);

        readButton = new ReadButton(getContext());
        readButton.setId(generateViewId());
        readButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        addView(readButton);

        createButton = new CreateButton(getContext());
        createButton.setId(generateViewId());
        createButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        addView(createButton);

        styleChangeButton = new StyleChangeButton(getContext());
        styleChangeButton.setId(generateViewId());
        styleChangeButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        addView(styleChangeButton);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("MenuView", "" + hasWindowFocus);

        int w = (int) (getWidth() * 0.8F);
        int m = (int) (getWidth() * 0.1F);

        RelativeLayout.LayoutParams params;

        params = new LayoutParams(w, w);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_TOP);
        params.setMargins(m,m,m,m);
        readButton.setLayoutParams(params);

        params = new LayoutParams(w , w);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.setMargins(m,m,m,m);
        createButton.setLayoutParams(params);

        params = new LayoutParams(w ,w);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, readButton.getId());
        params.setMargins(m,m,m,m);
        styleChangeButton.setLayoutParams(params);

    }
}
