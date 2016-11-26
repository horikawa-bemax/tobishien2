package bemax.ac.jp.tobishien2;

import android.graphics.Bitmap;
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
    View view;
    enum Style {Square, Rectangle};  // 正方形, 長方形

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
     *
     * @return
     */
    public View getView(){
        return view;
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

    /**
     * カードを生成する
     * @param root      親となるViewGroup
     * @param card      カード
     * @param style      カードのスタイル
     * @return          カードView
     */
    public static View createCardView(ViewGroup root, Card card, Card.Style style){
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(root.getContext());
        switch(style){
            case Square:
                view = inflater.inflate(R.layout.squarecardlayout, root, false);
                break;
            case Rectangle:
                view = inflater.inflate(R.layout.rectanglecardlayout, root, false);
                break;
            default:
                return null;
        }

        // テキストを入力
        TextView tv = (TextView)view.findViewById(R.id.cardText);
        tv.setText(card.getTitle());

        // 画像を入力
        ImageView iv = (ImageView)view.findViewById(R.id.cardImage);
        iv.setImageBitmap(card.getImage());

        card.view = view;
        return view;
    }

}
