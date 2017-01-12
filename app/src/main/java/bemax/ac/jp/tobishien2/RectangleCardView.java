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

public class RectangleCardView extends RelativeLayout implements GestureDetector.OnGestureListener{

    private Card card;

    private ImageView imageView;
    private TextView textView;

    GestureDetector gestureDetector;

    public RectangleCardView(Context context, Card card, int width){
        super(context);

        this.card = card;

        final int IW = (int) (width * 0.1F);
        final int TW = (int) (width * 0.8F);
        final int M = (int) (width * 0.05F);

        LayoutParams params;

        imageView = new ImageView(getContext());
        imageView.setId(generateViewId());
        imageView.setImageBitmap(card.getImage());
        params = new LayoutParams(IW, IW);
        params.setMargins(M, M, M, M);
        addView(imageView, params);

        textView = new TextView(getContext());
        textView.setId(generateViewId());
        textView.setTextSize(TW * 0.1F);
        textView.setText(card.getName());
        params = new LayoutParams(TW, width);
        params.setMargins(M, M, M, M);
        params.addRule(RIGHT_OF, imageView.getId());
        addView(textView, params);

        gestureDetector = new GestureDetector(getContext(), this);

    }

    public void setTitle(String title){
        textView.setText(title);
    }

    public void setImage(Bitmap image){
        imageView.setImageBitmap(image);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float vx, float vy) {

        return false;
    }
}
