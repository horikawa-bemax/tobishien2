package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bemax_ap01 on 2017/01/11.
 */

public class ScheduleListView extends ListView {
    private ListAdapter scheduleAdapter;

    public ScheduleListView(Context context) {
        super(context);

        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(getContext());
        SQLiteDatabase db = openHelper.getReadableDatabase();

        setScheduleAdapter(db);

        db.close();
    }

    public void setScheduleAdapter(SQLiteDatabase db){
        final String[] scheduleTitles = Schedule.getScheduleTitles(getContext(), db);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

        for(String title: scheduleTitles){
            adapter.add(title);
        }

        setAdapter(adapter);
    }
}
