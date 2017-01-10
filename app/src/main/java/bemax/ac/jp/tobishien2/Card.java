package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by horikawa on 2016/11/18.
 * カードクラス
 */
public class Card {
    private Context context;
    private long id;
    private String name;   // カードのタイトル
    private String imageFile;
    private int folderType;

    View view;
    enum Style {Square, Rectangle};  // 正方形, 長方形

    public static final int FolderTypeAsset = 1;
    public static final int FolderTypeStrage = 2;

    /**
     * コンストラクタ
     * @param context
     */
    public Card(Context context){
        this.context = context;
        this.id = -1;
    }

    public long getId(){
        return id;
    }

    /**
     * カードのタイトルを返す
     * @return  タイトルテキスト
     */
    public String getName() {
        return name;
    }

    /**
     * カードの画像を返す
     * @return  画像
     */
    public Bitmap getImage(){
        Bitmap image = null;

        try {
            switch (this.folderType) {
                case FolderTypeAsset:
                    AssetManager manager = context.getAssets();
                    InputStream is = manager.open(this.imageFile);
                    image = BitmapFactory.decodeStream(is);
                    break;
                case FolderTypeStrage:
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(dir, this.imageFile);
                    Log.d("FilePath", file.getPath());
                    image = BitmapFactory.decodeFile(file.getPath());
                    break;
            }
        }catch(IOException e){
            e.printStackTrace();
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.yatta);
        }
        return image;
    }

    /**
     *
     * @return
     */
    public View getView(){
        return view;
    }

    private void update(){

    }

    private void delete(){
        this.id = -1;
    }

    /**
     * カードを生成する
     * @param root      親となるViewGroup
     * @param card      カード
     * @param style      カードのスタイル
     * @return          カードView
     */
    public static View createCardView(ViewGroup root, Card card, Card.Style style) throws IOException {
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
        tv.setText(card.getName());

        // 画像を入力
        ImageView iv = (ImageView)view.findViewById(R.id.cardImage);
        iv.setImageBitmap(card.getImage());

        card.view = view;
        return view;
    }

    public static Card newCard(Context context, SQLiteDatabase db, String name, int folderType, String imageFile){
        long cid;

        SQLiteStatement statement = db.compileStatement("insert into cardTable (name, folderType, imageFile) values (?, ?, ?)");
        statement.bindString(1, name);
        statement.bindLong(2, folderType);
        statement.bindString(3, imageFile);
        cid = statement.executeInsert();
        statement.close();

        return Card.selectCard(context, db, cid);
    }

    public static Card selectCard(Context context, SQLiteDatabase db, long id){
        Card card = null;

        Cursor cc = db.query("cardTable", null, "_id=?", new String[]{"" + id}, null, null, null, null);
        if (cc.moveToNext()) {
            card = new Card(context);
            card.id = cc.getLong(cc.getColumnIndex("_id"));
            card.name = cc.getString(cc.getColumnIndex("name"));
            card.folderType = cc.getInt(cc.getColumnIndex("folderType"));
            card.imageFile = cc.getString(cc.getColumnIndex("imageFile"));
        }

        return card;
    }
}
