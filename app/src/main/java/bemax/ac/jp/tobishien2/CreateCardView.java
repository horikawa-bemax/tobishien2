package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by bemax_ap01 on 2017/01/13.
 */

public class CreateCardView extends RelativeLayout {

    private FloatingActionButton addImageButton;
    private ImageView imageView;
    private EditText editText;
    private ImageButton imageButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CreateCardView(Context context, DisplayMetrics metrics) {
        super(context);

        imageView = new ImageView(context);
        imageView.setId(generateViewId());

        editText = new EditText(context);
        editText.setId(generateViewId());
        editText.setSingleLine();

        addImageButton = new FloatingActionButton(context);
        addImageButton.setId(generateViewId());
        addImageButton.setImageResource(R.drawable.ic_photo_album);
        addImageButton.setSize(FloatingActionButton.SIZE_NORMAL);
        ColorStateList csl = addImageButton.getBackgroundTintList();

        imageButton = new ImageButton(getContext());
        imageButton.setImageResource(R.drawable.ic_done);
        imageButton.setBackgroundTintList(csl);

        LayoutParams params;
        int displayH = metrics.heightPixels;
        final int CH = (int)(displayH * 0.4F);
        final int IH = (int)(CH * 0.8F);
        final int TH = (int)(CH * 0.2F);

        setBackgroundColor(Color.WHITE);

        Bitmap image = Bitmap.createBitmap(IH, IH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.GRAY);
        imageView.setImageBitmap(image);

        params = new LayoutParams(IH, IH);
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(20,20,20,20);
        addView(imageView, params);

        params = new LayoutParams(IH, TH);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, imageView.getId());
        params.setMargins(20,20,20,20);
        addView(editText, params);

        params = new LayoutParams(TH, TH);
        params.addRule(ALIGN_RIGHT, imageView.getId());
        params.addRule(ALIGN_BOTTOM, imageView.getId());
        params.setMargins(10,10,10,10);
        addView(addImageButton, params);

        params = new LayoutParams(IH, TH);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, editText.getId());
        addView(imageButton, params);
    }

    public void setOnAddButtonTouchListener(OnTouchListener listener){
        addImageButton.setOnTouchListener(listener);
    }

    public void postImageView(Bitmap image){
        final int W = image.getWidth();
        final int H = image.getHeight();
        final float CW = W / 2;
        final float CH = H / 2;

        float left, top, right, buttom;
        if (W < H){
            left = 0;
            right = W;
            top = (H - W) / 2;
            buttom = top + W;
        }else{
            top = 0;
            buttom = H;
            left = (W - H) / 2;
            right = left + H;
        }

        int x = (int) left;
        int y = (int) top;
        int width = (int)(right - left);
        int height = (int)(buttom - top);

        Bitmap bitmap = Bitmap.createBitmap(image, x, y, width, height);
        //
        imageView.setImageBitmap(bitmap);
    }

}
