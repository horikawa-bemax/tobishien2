package bemax.ac.jp.tobishien2;

import java.util.ArrayList;

/**
 * Created by horikawa on 2016/11/18.
 * スケジュールクラス
 */
public class Schedule {
    ArrayList<Card> list;   // カードのリスト
    String name;            // スケジュールの名前

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
    }

    public void addScheduleCard(Card card){
        list.add(card);
    };
}
