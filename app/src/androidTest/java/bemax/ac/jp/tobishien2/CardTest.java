package bemax.ac.jp.tobishien2;

import android.content.Context;
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
    Card card;
    View view;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        String title = "hogehoge";
        Bitmap image = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.yatta);
        card = new Card(title, image);
        LinearLayout layout = new LinearLayout(appContext);
        view = Card.createCardView(layout, card, Card.Style.Square);
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("hogehoge", card.getTitle());
    }

    @Test
    public void getImage() throws Exception {
        // ビットマップクラスを返す
        assertSame(Bitmap.class, card.getImage().getClass());
    }

    @Test
    public void setTitle() throws Exception {
        card.setTitle("fugofugo");
        assertEquals("fugofugo", card.title);
    }

    @Test
    public void getView() throws Exception {
        assertSame(view, card.getView());
    }

    @Test
    public void setImageResource() throws Exception {

    }

    @Test
    public void setImageFile() throws Exception {

    }

    @Test
    public void createCardView() throws Exception {

    }

}