package bemax.ac.jp.tobishien2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by horikawa on 2016/11/18.
 * カードクラス
 */
public class Card {
    String title;   // カードのタイトル
    Bitmap image;   // カードの画像
    enum Type {Square, Rectangle};  // 正方形, 長方形

    /**
     * カードのタイトルを返す
     * @return  タイトルテキスト
     */
    public String getTitle() {
        return title;
    }

    /**
     * カードの画像を返す
     * @return  画像
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * カードのタイトルを入れる
     * @param title タイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * カードの画像をリソースから作成する
     * @param image   画像
     */
    public void setImageResource(Bitmap image) {
        this.image = image;
    }

    public void setImageFile(){

    }

    /**
     * コンストラクタ リソースから
     * @param title
     * @param image
     */
    public Card(String title, Bitmap image) {
        setTitle(title);
        setImageResource(image);
    }
}
