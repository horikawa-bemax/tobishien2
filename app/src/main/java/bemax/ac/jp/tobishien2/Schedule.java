package bemax.ac.jp.tobishien2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;

/**
 * Created by horikawa on 2016/11/18.
 * スケジュールクラス
 */
public class Schedule {
    ArrayList<Card> list;   // カードのリスト
    String name;            // スケジュールの名前
    LinearLayout layout;
    Card.Style style;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getScheduleList() {
        return list;
    }

    public void setScheduleList(ArrayList<Card> list) {
        this.list = list;
    }

    public Schedule(String name) {
        this.list = new ArrayList<Card>();
        this.name = name;
        this.layout = null;
        this.style = null;
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
            View cardView = Card.createCardView(layout, card, style);
            layout.addView(cardView);
            cardView.setOnTouchListener((View.OnTouchListener) root.getContext());
        }

        // 親ViewにこのViewGroupを追加する
        root.addView(layout);   // 親Viewにこのレイアウトを追加する

        // インスタンス変数に参照をセット
        schedule.layout = layout;
        schedule.style = style;

        return layout;
    }


}
