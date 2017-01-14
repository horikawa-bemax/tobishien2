package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bemax_ap01 on 2017/01/10.
 */

public class RectangleCardView extends AbstractCardView{

    public RectangleCardView(Context context, Card card, int width){
        super(context, card);

        final int IW = (int) (width * 0.1F);
        final int TW = (int) (width * 0.8F);
        final int M = (int) (width * 0.05F);

        LayoutParams params;

        super.getImageView().setImageBitmap(card.getImage());
        params = new LayoutParams(IW, IW);
        params.setMargins(M, M, M, M);
        addView(getImageView(), params);

        super.getTextView().setTextSize(TW * 0.1F);
        super.getTextView().setText(card.getName());
        params = new LayoutParams(TW, width);
        params.setMargins(M, M, M, M);
        params.addRule(RIGHT_OF, super.getImageView().getId());
        addView(super.getTextView(), params);

    }
}
