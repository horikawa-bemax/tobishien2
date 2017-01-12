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

    public MenuView(Context context, int width) {
        super(context);

        setBackgroundColor(Color.RED);

        final int W = (int) (width * 0.8F);
        final int M = (int) (width * 0.1F);

        LayoutParams params;

        readButton = new ReadButton(getContext());
        readButton.setId(generateViewId());
        params = new LayoutParams(W, W);
        params.setMargins(M, M, M, M);
        params.addRule(CENTER_HORIZONTAL);
        addView(readButton, params);

        createButton = new CreateButton(getContext());
        createButton.setId(generateViewId());
        params = new LayoutParams(W, W);
        params.setMargins(M, M, M, M);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        addView(createButton, params);

        styleChangeButton = new StyleChangeButton(getContext());
        styleChangeButton.setId(generateViewId());
        params = new LayoutParams(W, W);
        params.setMargins(M, M, M, M);
        params.addRule(BELOW, readButton.getId());
        params.addRule(CENTER_HORIZONTAL);
        addView(styleChangeButton, params);
    }

    public ImageView getStyleChangeButton() {
        return styleChangeButton;
    }

    public ImageView getCreateButton() {
        return createButton;
    }

    public ImageView getReadButton() {
        return readButton;
    }
}
