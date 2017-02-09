package bemax.ac.jp.tobishien2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bemax_ap01 on 2017/02/08.
 */

public class ScheduleCardAdapter extends BaseAdapter {
    public class ViewHolder{
        int position;
        TextView positionText;
        TextView cardNameText;
    }
    private long id;
    private List<String> cardNames;
    private Context context;
    private LayoutInflater inflater;

    public ScheduleCardAdapter(Context context, List<String> list){
        super();
        this.context = context;
        cardNames = list;
        inflater = LayoutInflater.from(context);
    }

    public void add(String s){
        cardNames.add(s);
    }

    public void delete(int position){
        cardNames.remove(position);
    }

    @Override
    public int getCount() {
        return cardNames.size();
    }

    @Override
    public Object getItem(int i) {
        return cardNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.d("getView","");
        if(convertView == null){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 100);
            convertView = inflater.inflate(R.layout.schedule_card_layout, null);
            convertView.setLayoutParams(params);

            holder = new ViewHolder();
            holder.cardNameText = (TextView)convertView.findViewById(R.id.scheduleCardLayoutCardName);
            holder.positionText = (TextView)convertView.findViewById(R.id.scheduleCardLayoutNumber);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.cardNameText.setText(cardNames.get(position));
        holder.positionText.setText("" + (position + 1));

        return convertView;
    }

    public List<String> getCardNames(){
        return cardNames;
    }
}
