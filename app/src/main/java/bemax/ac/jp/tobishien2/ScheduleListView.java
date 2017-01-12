package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
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

        scheduleAdapter = new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int i) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getCount() {
                return scheduleTitles.length;
            }

            @Override
            public String getItem(int i) {
                String title = scheduleTitles[i];
                return title;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public TextView getView(int i, View view, ViewGroup viewGroup) {
                TextView textView = new TextView(viewGroup.getContext());
                textView.setText(scheduleTitles[i]);
                return null;
            }

            @Override
            public int getItemViewType(int i) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                if(scheduleTitles.length > 0){
                    return false;
                }else {
                    return true;
                }
            }
        };

        setAdapter(scheduleAdapter);
    }
}
