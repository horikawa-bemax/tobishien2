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
    private ArrayList<Card> list;   // カードのリスト
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

    public ArrayList<Card> getScheduleList() {
        return list;
    }

    public void setScheduleList(ArrayList<Card> list) {
        this.list = list;
    }

    private Schedule(Context context) {
        this.context = context;
    }

    public void addScheduleCard(Card card){
        list.add(card);
    };

    public void setTouchListenerToChards(View.OnTouchListener listener){
        for(Card card: list){
            card.getView().setOnTouchListener(listener);
        }
    }

    /**
     * スケジュールのカードリストに従って、スケジュール用のViewGroupを作成する
     * @param root
     * @param schedule
     * @param style
     * @return スケジュールカードViewの入ったLinearLayoutを
     */
    public static LinearLayout createScheduleLayout(ViewGroup root, Schedule schedule, Card.Style style){
        // LinearLayoutを新規作成（スケジュールのベースとなるViewGroup）
        LinearLayout layout = new LinearLayout(root.getContext());  // このレイアウト

        // レイアウトの設定
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        // レイアウトにCardのViewを追加する
        for(Card card: schedule.list){
            try {
                View cardView = Card.createCardView(layout, card, style);
                layout.addView(cardView);
                cardView.setOnTouchListener((View.OnTouchListener) root.getContext());
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // 親ViewにこのViewGroupを追加する
        root.addView(layout);   // 親Viewにこのレイアウトを追加する

        // インスタンス変数に参照をセット
        //schedule.layout = layout;

        return layout;
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
            schedule.list = new ArrayList<Card>();

            Cursor ictc = db.query(INTO_CARD_TABLE, null, "schedule_id=?", new String[]{"" + schedule.id}, null, null, null, null);
            while (ictc.moveToNext()) {
                long cardId = ictc.getLong(ictc.getColumnIndex("card_id"));
                Card card = Card.selectCard(context, db, cardId);
                schedule.list.add(card);
            }
        }

        return schedule;
    }

    public static boolean deleteSchedule(Context context, SQLiteDatabase db, long id){

        return false;
    }
}
