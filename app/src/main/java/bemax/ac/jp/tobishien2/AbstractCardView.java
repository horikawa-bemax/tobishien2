package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * CardViewの抽象クラス
 */

public abstract class AbstractCardView extends RelativeLayout {
    private Card card;
    private ImageView imageView;
    private TextView textView;
    private Handler handler;

    public AbstractCardView(Context context, Card card) {
        super(context);

        this.card = card;

        imageView = new ImageView(context);
        imageView.setId(generateViewId());

        textView = new TextView(context);
        textView.setId(generateViewId());

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(msg.what == 100){
                    setAlpha(0.2F);
                }else{
                    setAlpha(1.0F);
                }
            }
        };
    }

    public Handler getHandler(){
        return handler;
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
