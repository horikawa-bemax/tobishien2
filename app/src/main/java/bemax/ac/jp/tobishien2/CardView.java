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

public class CardView extends RelativeLayout implements GestureDetector.OnGestureListener{

    public enum CardStyle {Square, Rectangle};

    private Card card;
    private CardStyle cardStyle;

    private ImageView imageView;
    private TextView textView;

    GestureDetector gestureDetector;

    public CardView(Context context, Card card){
        super(context);

        this.card = card;

        cardStyle = CardStyle.Square;

        imageView = new ImageView(getContext());
        imageView.setId(getResources().getIdentifier("card_image", "id", "bemax.ac.jp.tobishien2"));
        imageView.setImageBitmap(card.getImage());
        addView(imageView);

        textView = new TextView(getContext());
        textView.setId(getResources().getIdentifier("card_text", "id", "bemax.ac.jp.tobishien2"));
        textView.setText(card.getName());
        addView(textView);

        gestureDetector = new GestureDetector(getContext(), this);

    }

    public void setTitle(String title){
        textView.setText(title);
    }

    public void setImage(Bitmap image){
        imageView.setImageBitmap(image);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        if(hasWindowFocus){
            LayoutParams params;
            int width = getWidth();
            int height = getHeight();

            switch(cardStyle){
                case Square:
                    params = new LayoutParams(500, 500);
                    params.setMargins(10,10,10,10);
                    imageView.setLayoutParams(params);

                    params = new LayoutParams(500, 50);
                    params.setMargins(10, 500, 10, 10);
                    textView.setLayoutParams(params);

                    break;
                case Rectangle:
                    params = new LayoutParams(200, 200);
                    params.setMargins(10, 10, 10, 10);
                    imageView.setLayoutParams(params);

                    params = new LayoutParams(300, 200);
                    params.setMargins(10, 320, 10, 10);
                    textView.setLayoutParams(params);
                    break;
            }
        }
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
