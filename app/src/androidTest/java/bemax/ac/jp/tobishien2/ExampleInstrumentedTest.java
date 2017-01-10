package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext;
    SQLiteDatabase database;

    @Before
    public void databaseInit() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();

        // データベース
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(appContext);
        database = helper.getWritableDatabase();

        database.execSQL("delete from cardTable;");
        database.execSQL("delete from scheduleTable;");
        database.execSQL("delete from intoCardTable;");
    }

    @After
    public void databaseClose() throws Exception{
        //cursor.close();
        database.close();
    }

    @Test
    public void databaseTest() throws  Exception{
        // DB TEST

        database.beginTransaction();
        try {
            String[] cardName = {"話す", "聞く", "電話"};
            int[] cardFolderType = {Card.FolderTypeAsset, Card.FolderTypeAsset, Card.FolderTypeStrage};
            String[] cardFile = {"hanasu.gif", "kiku.gif", "Camera/IMG_20170107_172518.jpg"};
            Card[] cards = new Card[3];

            for (int i = 0; i < 3; i++) {
                cards[i] = Card.newCard(appContext, database, cardName[i], cardFolderType[i], cardFile[i]);
            }

            Schedule s = Schedule.newSchedule(appContext, database, "初めてのスケジュール", cards);
        }finally {
            database.endTransaction();
        }


        /*
        statement = database.compileStatement("insert into scheduleTable (name) values (?);");
        for(int i=0; i<schedule_id.length; i++){
            statement.bindString(1, scheduleName[i]);
            schedule_id[i] = statement.executeInsert();
        }

        statement = database.compileStatement("insert into intoCardTable values (?,?,?);");
        for(int i=0; i<schedule_id.length; i++) {
            for(int j=0; j<card_id.length; j++){
                statement.bindLong(1, schedule_id[i]);
                statement.bindLong(2, card_id[j]);
                statement.bindLong(3, j + 1);
                statement.executeInsert();
            }
        }

        Cursor cursor = database.rawQuery("select * from cardTable;", null);
        while(cursor.moveToNext()){
            int idx = cursor.getPosition();
            assertEquals(card_id[idx], cursor.getLong(0));
            assertEquals(cardName[idx], cursor.getString(1));
            assertEquals(cardFolderType[idx], cursor.getLong(2));
            assertEquals(cardFile[idx], cursor.getString(3));
        }
        cursor.close();

        cursor = database.rawQuery("select * from scheduleTable;", null);
        while(cursor.moveToNext()){
            int idx = cursor.getPosition();
            assertEquals(schedule_id[idx], cursor.getLong(0));
            assertEquals(scheduleName[idx], cursor.getString(1));
        }
        cursor.close();

        cursor = database.rawQuery("select cardTable.name from intoCardTable, scheduleTable, cardTable where scheduleTable._id = intoCardTable.schedule_id and cardTable._id = intoCardTable.card_id and scheduleTable.name = ?;", new String[]{scheduleName[0]});
        while(cursor.moveToNext()){
            int idx = cursor.getPosition();
            assertEquals(cardName[idx], cursor.getString(0));
        }
        cursor.close();
*/

    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("bemax.ac.jp.tobishien2", appContext.getPackageName());

    }
}
