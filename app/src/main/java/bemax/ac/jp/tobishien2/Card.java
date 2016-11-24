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
     * @param res   リソース
     * @param id    リソース番号
     */
    public void setImageResource(Resources res, int id) {
        this.image = BitmapFactory.decodeResource(res, id);
    }

    public void setImageFile(){

    }

    /**
     * コンストラクタ リソースから
     * @param title
     * @param res
     * @param id
     */
    public Card(String title, Resources res, int id) {
        setTitle(title);
        setImageResource(res, id);
    }

    /**
     * インフレーターからViewを作成し、親Viewにセットして返す
     * @param inflater  インフレーター
     * @return  カードのView
     */
    public View getCardView(LayoutInflater inflater, ViewGroup root, Type type){
        // カードView作成
        View cardView;
        switch(type) {
            case Square:
                cardView = inflater.inflate(R.layout.squarecardlayout, root, false);    // falseにしないと、くっつく。（なぜだかは不明）
                break;
            case Rectangle:
                cardView = inflater.inflate(R.layout.rectanglecardlayout, root, false); // ここも
                break;
            default:
                return null;
        }

        // テキストView初期化
        TextView tv = (TextView)cardView.findViewById(R.id.cardText);
        tv.setText(title);

        // イメージView初期化
        ImageView iv = (ImageView)cardView.findViewById(R.id.cardImage);
        iv.setImageBitmap(image);

        return cardView;
    }


}
