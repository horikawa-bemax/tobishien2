package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bemax_ap01 on 2017/01/11.
 */

public class ScheduleSelectView extends RelativeLayout {
    private ListView listView;

    public ScheduleSelectView(Context context) {
        super(context);

        listView = new ListView(getContext());
        addView(listView);

        ListAdapter adapter;
    }

    private List<String> getScheduleTitles(){
        ArrayList<String> list = new ArrayList<String>();

        return list;
    }
}
