package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bemax_ap01 on 2017/01/11.
 */

public class ScheduleTitleListTest {
    private Context appContext;
    SQLiteDatabase db;

    @Before
    public void init(){
        appContext = InstrumentationRegistry.getTargetContext();
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(appContext);
        db = helper.getReadableDatabase();
    }

    @Test
    public void test(){
        Schedule.newSchedule(appContext, db, "hogehoge", new Card[0]);
        Schedule.newSchedule(appContext, db, "fugofugo", new Card[0]);

        String[] titles = Schedule.getScheduleTitles(appContext, db);

        assertEquals("hogehoge", titles[0]);
        assertEquals("fugofugo", titles[1]);

        ScheduleListView scheduleListView = new ScheduleListView(appContext);
        scheduleListView.setScheduleAdapter(db);

        assertEquals(scheduleListView.getAdapter().getCount(), 2);
        assertEquals(scheduleListView.getAdapter().getItem(0), "hogehoge");
    }

    @After
    public void after(){
        db.close();
    }
}
