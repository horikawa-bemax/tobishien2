package bemax.ac.jp.tobishien2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by horikawa on 2016/11/18.
 * スケジュールクラス
 */
public class Schedule {
    private static final String SCHEDULE_TABLE = "scheduleTable";
    private static final String INTO_CARD_TABLE = "intoCardTable";

    private Context context;
    private ArrayList<Card> cardList;   // カードのリスト
    private String name;            // スケジュールの名前
    private long id;

    private void update(SQLiteDatabase db, ContentValues cv){
        try {
            db.update("scheduleTable", cv, "_id=" + this.id, null);
        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
            Log.d("SQLite_UPDATE", this.name);
        }
    }

    public long getId(){
        return this.id;
    }

    public void setName(SQLiteDatabase db, String name) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        try {
            update(db, cv);
            this.name = name;
        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
            Log.d("SQLite_UPDATE", this.name);
        }
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Card> getScheduleCardList() {
        return cardList;
    }

    private Schedule(Context context) {
        this.context = context;
    }

    public void addScheduleCard(Card card){

    }

    public static Schedule newSchedule(Context context, SQLiteDatabase db, String title, Card[] cards){
        Schedule schedule = null;
        long id;

        SQLiteStatement statement = db.compileStatement("insert into scheduleTable (name) values (?)");
        statement.bindString(1, title);
        id = statement.executeInsert();
        statement.close();

        int rank = 1;
        statement = db.compileStatement("insert into intoCardTable (schedule_id, card_id, rank) values (?,?,?)");
        for(Card c: cards) {
            statement.bindLong(1, id);
            statement.bindLong(2, c.getId());
            statement.bindLong(3, rank++);
            statement.executeInsert();
        }
        statement.close();

        return selectSchedule(context, db, title);
    }

    public static Schedule selectSchedule(Context context, SQLiteDatabase db, String title){
        Schedule schedule = null;

        Cursor sc = db.query(SCHEDULE_TABLE, null, "name=?", new String[]{title}, null, null, null, null);

        if (sc.moveToNext()) {
            schedule = new Schedule(context);
            schedule.id = sc.getLong(sc.getColumnIndex("_id"));
            schedule.name = sc.getString(sc.getColumnIndex("name"));
            schedule.cardList = new ArrayList<Card>();

            Cursor ictc = db.query(INTO_CARD_TABLE, null, "schedule_id=?", new String[]{"" + schedule.id}, null, null, null, null);
            while (ictc.moveToNext()) {
                long cardId = ictc.getLong(ictc.getColumnIndex("card_id"));
                Card card = Card.selectCard(context, db, cardId);
                schedule.cardList.add(card);
            }
        }

        return schedule;
    }

    public static boolean deleteSchedulebyTitle(Context context, SQLiteDatabase db, String title){
        boolean result = false;
        db.beginTransaction();
        try {
            String sql = "delete from intoCardTable where schedule_id in (select _id from scheduleTable where name = '" + title + "')";
            db.execSQL(sql);
            sql = "delete from scheduleTable where name = '" + title + "'";
            db.execSQL(sql);
            Toast.makeText(context, "削除しました", Toast.LENGTH_LONG).show();
            result = true;
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return result;
    }

    public static String[] getScheduleTitles(Context context, SQLiteDatabase db){
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select name from scheduleTable", null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();

        final String[] s = new String[list.size()];
        list.toArray(s);

        return s;
    }
}
