package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bemax_ap01 on 2017/01/13.
 */

public abstract class AbstractCardView extends RelativeLayout {

    private Card card;
    private ImageView imageView;
    private TextView textView;

    public AbstractCardView(Context context, Card card) {
        super(context);

        this.card = card;

        imageView = new ImageView(context);
        imageView.setId(generateViewId());

        textView = new TextView(context);
        textView.setId(generateViewId());
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
