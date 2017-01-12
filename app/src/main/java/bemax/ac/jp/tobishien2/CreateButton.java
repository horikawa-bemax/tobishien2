package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by bemax_ap01 on 2017/01/11.
 */

public class CreateButton extends ImageView implements View.OnTouchListener{

    public CreateButton(Context context) {
        super(context);

        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //params.setMargins(20, 20, 20, 20);
        //setLayoutParams(params);

        setBackgroundColor(Color.YELLOW);

        Bitmap image = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        Canvas canvas = new Canvas(image);
        canvas.drawText("新", 50, 150, paint);
        setImageBitmap(image);

        setOnTouchListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        RelativeLayout.LayoutParams params;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("TouchButton", "Create");

        return false;
    }
}
