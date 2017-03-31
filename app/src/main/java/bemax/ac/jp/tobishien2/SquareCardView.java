package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bemax_ap01 on 2017/01/10.
 */

public class SquareCardView extends AbstractCardView{

    private Card card;

    public SquareCardView(Context context, Card card, int width){
        super(context, card);

        final int IH = (int) (width * 0.7F);
        final int TH = (int) (width * 0.3F);
        final int M = (int) (width * 0.05F);

        LayoutParams params;

        getImageView().setImageBitmap(card.getImage());
        params = new LayoutParams(width, IH);
        params.setMargins(M, M, M, 0);
        addView(super.getImageView(), params);

        super.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, TH * 0.4F);
        super.getTextView().setText(card.getName());
        params = new LayoutParams(width, TH);
        params.setMargins(0, 0, 0, 0);
        params.addRule(BELOW, super.getImageView().getId());
        addView(super.getTextView(), params);

    }
}
