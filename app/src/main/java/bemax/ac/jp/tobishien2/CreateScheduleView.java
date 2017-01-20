package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by bemax_ap01 on 2017/01/20.
 */

public class CreateScheduleView extends RelativeLayout {
    private ListView selectionCardList;
    private ListView scheduleCardList;
    private ArrayAdapter<String> scheduleCardAdapter;
    private SQLiteOpenHelper helper;

    public CreateScheduleView(Context context, SQLiteOpenHelper helper) {
        super(context);

        this.helper = helper;

        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayAdapter<String> selectCardViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Card[] cards = Card.selectAllCards(context, db);
        for(Card card: cards){
            selectCardViewAdapter.add(card.getName());
        }
        selectionCardList = new ListView(context);
        selectionCardList.setId(generateViewId());
        selectionCardList.setAdapter(selectCardViewAdapter);

        scheduleCardAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        scheduleCardList = new ListView(context);
        scheduleCardList.setAdapter(scheduleCardAdapter);

        // 背景を白にする
        setBackgroundColor(Color.WHITE);

        LayoutParams params;

        params = new LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(selectionCardList, params);

        params = new LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RIGHT_OF, selectionCardList.getId());
        addView(scheduleCardList, params);

        selectionCardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListView listView = (ListView) adapterView;
                String name = (String) listView.getItemAtPosition(position);
                scheduleCardAdapter.add(name);
            }
        });

    }
}
