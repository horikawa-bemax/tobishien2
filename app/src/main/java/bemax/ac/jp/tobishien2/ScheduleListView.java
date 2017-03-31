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

    public ScheduleListView(Context context, ArrayAdapter<String> adapter) {
        super(context);
        setAdapter(adapter);
    }

}
