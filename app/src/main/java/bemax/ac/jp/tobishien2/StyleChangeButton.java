package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by bemax_ap01 on 2017/01/11.
 */

public class StyleChangeButton extends ImageView implements View.OnTouchListener{
    public StyleChangeButton(Context context) {
        super(context);

        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        //params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        //params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //params.setMargins(20, 250, 20, 20);
        //setLayoutParams(params);

        setBackgroundColor(Color.GRAY);

        Bitmap image = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        Canvas canvas = new Canvas(image);
        canvas.drawText("換", 50, 150, paint);
        setImageBitmap(image);

        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return false;
    }
}
