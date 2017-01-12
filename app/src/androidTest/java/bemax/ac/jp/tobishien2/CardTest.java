package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by bemax_ap01 on 2016/11/29.
 */
@RunWith(AndroidJUnit4.class)
public class CardTest {
    Context appContext;
    SQLiteDatabase database;
    Card card1, card2;
    View view;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(appContext);
        database = helper.getWritableDatabase();

        Card card1 = Card.newCard(appContext, database, "hogehoge", Card.FolderTypeAsset, "kiku.gif");
        Card card2 = Card.newCard(appContext, database, "電話", Card.FolderTypeAsset, "hanasu.gif");
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("hogehoge", card1.getName());
        assertEquals("電話", card2.getName());
    }

    @Test
    public void getImage() throws Exception {
        assertNotNull(card1.getImage());
        assertNotNull(card2.getImage());
    }


}