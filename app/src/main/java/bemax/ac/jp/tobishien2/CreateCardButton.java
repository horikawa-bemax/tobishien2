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
 * カード新規作成ボタン
 */

public class CreateCardButton extends ImageView implements View.OnTouchListener{

    public CreateCardButton(Context context) {
        super(context);

        setBackgroundColor(Color.YELLOW);

        Bitmap image = Bitmap.createBitmap(160, 160, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        Canvas canvas = new Canvas(image);
        canvas.drawText("C", 30, 130, paint);
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
