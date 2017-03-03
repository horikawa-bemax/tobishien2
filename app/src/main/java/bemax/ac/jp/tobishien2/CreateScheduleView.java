package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * スケジュール新規作成ビュー
 */

public class CreateScheduleView extends RelativeLayout implements View.OnTouchListener{
    private ImageButton updateButton;
    private ImageButton deleteButton;
    private ImageButton newButton;
    private ImageButton returnButton;
    private ImageButton saveButton;
    private EditText scheduleName;
    private ListView selectionCardList;
    private ListView scheduleCardList;
    private ScheduleCardAdapter scheduleCardAdapter;
    private SQLiteOpenHelper helper;
    private ArrayAdapter<String> listAdapter;

    public CreateScheduleView(Context context, SQLiteOpenHelper helper, ArrayAdapter<String> adapter) {
        super(context);

        this.helper = helper;
        this.listAdapter = adapter;

        // 選択用カードリスト作成
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayAdapter<String> selectCardViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Card[] cards = Card.selectAllCards(context, db);
        if(cards != null)
            for(Card card: cards){
                selectCardViewAdapter.add(card.getName());
            }
        selectionCardList = new ListView(context);
        selectionCardList.setId(generateViewId());
        selectionCardList.setAdapter(selectCardViewAdapter);

        // スケジュール用アダプタ
        scheduleCardAdapter = getNewScheduleCardAdapter();
        scheduleCardList = new ListView(context);
        scheduleCardList.setId(generateViewId());
        scheduleCardList.setAdapter(scheduleCardAdapter);
        scheduleCardList.setBackgroundColor(Color.rgb(255,200,200));

        // 背景を白にする
        setBackgroundColor(Color.argb(200,100,100,100));

        LayoutParams params;

        // 戻るボタン
        returnButton = new ImageButton(context);
        returnButton.setBackgroundColor(Color.BLUE);
        returnButton.setImageResource(android.R.drawable.ic_menu_revert);
        returnButton.setId(generateViewId());
        params = new LayoutParams(100,100);
        params.setMargins(100, 0, 0, 100);
        params.addRule(ALIGN_PARENT_BOTTOM);
        addView(returnButton, params);

        // 白背景
        RelativeLayout whiteLayout = new RelativeLayout(context);
        whiteLayout.setBackgroundColor(Color.WHITE);
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(100,100,100,0);
        params.addRule(ABOVE, returnButton.getId());
        addView(whiteLayout, params);

        // スケジュール名入力テキスト
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        params.setMargins(0,0,0,100);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(RIGHT_OF, selectionCardList.getId());
        scheduleName = new EditText(context);
        scheduleName.setId(generateViewId());
        scheduleName.setSingleLine();
        whiteLayout.addView(scheduleName, params);

        // 保存ボタン
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(RIGHT_OF, selectionCardList.getId());
        saveButton = new ImageButton(context);
        saveButton.setImageResource(R.drawable.ic_done);
        whiteLayout.addView(saveButton, params);

        // 選択可能カードリスト
        params = new LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0,0);
        params.addRule(ABOVE, scheduleName.getId());
        whiteLayout.addView(selectionCardList, params);

        // スケジュールリスト
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RIGHT_OF, selectionCardList.getId());
        params.addRule(ABOVE, scheduleName.getId());
        whiteLayout.addView(scheduleCardList, params);

        // 選択可能カードリストのタッチイベント処理
        selectionCardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListView listView = (ListView) adapterView;
                String name = (String) listView.getItemAtPosition(position);
                scheduleCardAdapter.add(name);
                scheduleCardAdapter.notifyDataSetChanged();
            }
        });

        // スケジュールリストのタッチイベント処理
        scheduleCardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                scheduleCardAdapter.delete(position);
                scheduleCardAdapter.notifyDataSetChanged();
            }
        });

        // 保存ボタンのタッチイベント処理
        saveButton.setOnTouchListener(this);

        // 戻るボタンのタッチイベント処理
        returnButton.setOnTouchListener(this);
    }

    private ScheduleCardAdapter getNewScheduleCardAdapter(){
        List<String> list = new ArrayList<String>();
        ScheduleCardAdapter adapter = new ScheduleCardAdapter(this.getContext(), list);
        return adapter;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // 保存ボタンを押した場合
        if(view.getId() == saveButton.getId()){
            switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:

                    List<String> cardNames = scheduleCardAdapter.getCardNames();
                    // カードが１枚も無い場合
                    if(cardNames.size() == 0){
                        Toast.makeText(getContext(), "スケジュールがありません", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    // スケジュール名が無い場合
                    if(scheduleName.getText() == null || scheduleName.getText().toString().length() == 0){
                        Toast.makeText(getContext(), "スケージュール名がありません", Toast.LENGTH_LONG).show();
                        return false;
                    }

                    SQLiteDatabase db = helper.getWritableDatabase();

                    Card[] cards = new Card[cardNames.size()];
                    for (int i = 0; i < cards.length; i++) {
                        cards[i] = Card.selectCardByName(getContext(), db, cardNames.get(i));
                    }

                    Schedule.newSchedule(getContext(), db, scheduleName.getText().toString(), cards);

                    // ソフトウェアキーボードを消去
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(scheduleName.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // Schedule登録画面を消す
                    setVisibility(INVISIBLE);
                    //
                    listAdapter.add(scheduleName.getText().toString());

            }
        }

        // 戻るボタンを押した場合
        if(view.getId() == returnButton.getId()){
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                this.setVisibility(View.INVISIBLE);
                scheduleCardAdapter = getNewScheduleCardAdapter();
                scheduleCardList.setAdapter(scheduleCardAdapter);
            }
        }

        return false;
    }
}
