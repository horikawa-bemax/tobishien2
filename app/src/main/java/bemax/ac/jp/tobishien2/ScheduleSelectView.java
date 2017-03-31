package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bemax_ap01 on 2017/01/11.
 */

public class ScheduleSelectView extends RelativeLayout implements View.OnTouchListener{
    private ScheduleListView listView;
    private ImageButton returnButton;

    public ScheduleSelectView(Context context, ArrayAdapter<String> adapter) {
        super(context);

        SQLiteOpenHelper helper = new MySQLiteOpenHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        setBackgroundColor(Color.argb(200,100,100,100));

        returnButton = new ImageButton(context);
        returnButton.setId(generateViewId());
        returnButton.setBackgroundColor(Color.BLUE);
        returnButton.setImageResource(R.drawable.ic_clear);
        LayoutParams params = new LayoutParams(100,100);
        params.setMargins(100,0,0,100);
        params.addRule(ALIGN_PARENT_BOTTOM);
        addView(returnButton,params);

        listView = new ScheduleListView(getContext(), adapter);
        listView.setBackgroundColor(Color.WHITE);
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(100,100,100,0);
        params.addRule(ABOVE, returnButton.getId());
        addView(listView, params);

        returnButton.setOnTouchListener(this);
    }

    private List<String> getScheduleTitles(){
        ArrayList<String> list = new ArrayList<String>();

        return list;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener l){
        listView.setOnItemClickListener(l);
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener l){
        listView.setOnItemLongClickListener(l);
    }

    public ScheduleListView getListView(){
        return listView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()==returnButton.getId() && motionEvent.getAction()==MotionEvent.ACTION_UP){
            this.setVisibility(INVISIBLE);
        }
        return false;
    }
}
