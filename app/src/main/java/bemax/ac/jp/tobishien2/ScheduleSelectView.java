package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bemax_ap01 on 2017/01/11.
 */

public class ScheduleSelectView extends RelativeLayout {
    private ScheduleListView listView;

    public ScheduleSelectView(Context context) {
        super(context);

        SQLiteOpenHelper helper = new MySQLiteOpenHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        setBackgroundColor(Color.WHITE);

        listView = new ScheduleListView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setScheduleAdapter(db);
        addView(listView, params);
    }

    private List<String> getScheduleTitles(){
        ArrayList<String> list = new ArrayList<String>();

        return list;
    }
}
