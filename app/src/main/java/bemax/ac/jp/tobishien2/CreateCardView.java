package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bemax_ap01 on 2017/01/13.
 */

public class CreateCardView extends RelativeLayout {

    private SQLiteOpenHelper helper;

    private ImageButton newButton;
    private ImageButton updateButton;
    private ImageButton deleteButton;

    private FloatingActionButton addImageButton;
    private ImageView imageView;
    private EditText editText;
    private ImageButton doneButton, closeButton;
    private Bitmap addedCardImage, defaultImage;

    public CreateCardView(Context context, SQLiteOpenHelper helper, DisplayMetrics metrics) {
        super(context);
        this.helper = helper;

        // 画像表示View
        imageView = new ImageView(context);
        imageView.setId(generateViewId());
        // 編集可能テキスト
        editText = new EditText(context);
        editText.setId(generateViewId());
        editText.setSingleLine();
        // 画像読み込み用ボタン
        addImageButton = new FloatingActionButton(context);
        addImageButton.setId(generateViewId());
        addImageButton.setImageResource(R.drawable.ic_photo_album);
        addImageButton.setSize(FloatingActionButton.SIZE_NORMAL);
        ColorStateList csl = addImageButton.getBackgroundTintList();
        // 確定ボタン
        doneButton = new ImageButton(getContext());
        doneButton.setId(generateViewId());
        doneButton.setImageResource(R.drawable.ic_done);
        doneButton.setBackgroundTintList(csl);
        //　消去ボタン
        closeButton = new ImageButton(getContext());
        closeButton.setImageResource(R.drawable.ic_clear);
        closeButton.setBackgroundColor(Color.BLUE);
        // 画面サイズから色々計算
        LayoutParams params;
        int displayH = metrics.heightPixels;
        final int CH = (int)(displayH * 0.4F);
        final int IH = (int)(CH * 0.8F);
        final int TH = (int)(CH * 0.2F);
        // 背景を塗りつぶす
        setBackgroundColor(Color.WHITE);
        // 初期表示用の画像を作成
        defaultImage = Bitmap.createBitmap(IH, IH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(defaultImage);
        canvas.drawColor(Color.GRAY);
        imageView.setImageBitmap(defaultImage);
        // 画像Viewのレイアウト設定
        params = new LayoutParams(IH, IH);
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(20,20,20,20);
        addView(imageView, params);
        // 編集テキストのレイアウト設定
        params = new LayoutParams(IH, TH);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, imageView.getId());
        params.setMargins(20,20,20,20);
        addView(editText, params);
        // 画像読み込みボタンのレイアウト設定
        params = new LayoutParams(TH, TH);
        params.addRule(ALIGN_RIGHT, imageView.getId());
        params.addRule(ALIGN_BOTTOM, imageView.getId());
        params.setMargins(10,10,10,10);
        addView(addImageButton, params);
        // 確定ボタンのレイアウト設定
        params = new LayoutParams(IH, TH);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, editText.getId());
        addView(doneButton, params);
        // 閉じるボタンのレイアウト設定
        params = new LayoutParams(TH, TH);
        params.setMargins(10,10,10,10);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(ALIGN_PARENT_TOP);
        addView(closeButton, params);
        // 確定ボタンがタッチされた時の処理
        doneButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (editText.getText() != null && editText.getText().length() != 0) {
                    String s = storeImageFile();
                    if (s.length() == 0){
                        Toast.makeText(getContext(), "登録に失敗しました", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "「" + editText.getText() + "」を登録しました", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                        imageView.setImageBitmap(defaultImage);
                    }
                }else{
                    Toast.makeText(getContext(), "テキストを入力してください", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        // 閉じるボタンが押された時の処理
        closeButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // ソフトウェアキーボードを消去
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                // Card登録画面を消す
                setVisibility(INVISIBLE);
                return false;
            }
        });
    }

    public void setOnAddButtonTouchListener(OnTouchListener listener){
        addImageButton.setOnTouchListener(listener);
    }

    /**
     * 取得した画像を元に表示用画像を作成し、表示する
     * @param image         取得した画像
     */
    public void postImageView(Bitmap image){
        // 画像のサイズを取得
        final int W = image.getWidth();
        final int H = image.getHeight();
        // 画像の中央を切り出す
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
        // 表示用画像作成のための計算
        int x = (int) left;
        int y = (int) top;
        int width = (int)(right - left);
        int height = (int)(buttom - top);
        // 表示用画像を作成
        Bitmap temp = Bitmap.createBitmap(image, x, y, width, height);
        addedCardImage = Bitmap.createScaledBitmap(temp, 300, 300, true);

        // 表示用画像をViewにセットする
        imageView.setImageBitmap(addedCardImage);
    }

    private String storeImageFile(){
        String result = "";
        // 保存先フォルダのパスを指定
        File storePath = getContext().getExternalFilesDir(null);
        // 保存する画像の名前を決める
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("'TOBI'_yyyyMMdd_HHmmss'.jpg'");
        String fileName = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        // 保存先ファイルを指定
        File storeFile = new File(storePath, fileName);
        // フォルダに画像を保存する
        FileOutputStream fos = null;
        try {
            // ファイルを保存
            fos = new FileOutputStream(storeFile);
            addedCardImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            // dbに保存する
            SQLiteDatabase db = helper.getWritableDatabase();
            String name = editText.getText().toString();
            insertCard(db, name, fileName);
            db.close();
            result = fileName;
        }catch(IOException e){
            e.printStackTrace();
            try{
                fos.close();
            }catch (Exception ex){}
        }catch(SQLiteConstraintException e){
            Toast.makeText(getContext(),"「"+ editText.getText() +"」は、すでに登録されています", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return result;
    }

    private void insertCard(SQLiteDatabase db, String name, String fileName)throws SQLiteException{
        Card.newCard(getContext(), db, name, Card.FolderTypeStrage, fileName);
    }
}
