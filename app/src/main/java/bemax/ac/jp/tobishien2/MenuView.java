package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by bemax_ap01 on 2017/01/10.
 */

public class MenuView extends RelativeLayout {

    private ImageView styleChangeButton;
    private ImageView editButton;
    private ImageView loadButton;

    public MenuView(Context context) {
        super(context);

        setBackgroundColor(Color.RED);

        loadButton = new ImageView(getContext());
        loadButton.setId(getResources().getIdentifier("menu_loadButton", "id", "bemax.ac.jp"));
        loadButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        addView(loadButton);

        editButton = new ImageView(getContext());
        editButton.setId(getResources().getIdentifier("menu_editButton", "id", "bemax.ac.jp"));
        editButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        addView(editButton);

        styleChangeButton = new ImageView(getContext());
        styleChangeButton.setId(getResources().getIdentifier("menu_styleChangeButton", "id", "bemax.ac.jp"));
        styleChangeButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        addView(styleChangeButton);
    }
}
