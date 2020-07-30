package tools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * 记录时间间隔的工具类，主要用来统计方法的调用时间
 * Created by Niki on 2018/10/9 11:13
 */
public class TimeGap {
    LinkedList<TimeItem> queue = new LinkedList<>();
    Map<String, Long> result = new HashMap<>();

    public void start(String title) {
        queue.add(new TimeItem(title));
    }

    public void end() {
        if (queue.size() > 0) {
            TimeItem timeItem = queue.getLast();
            result.put(getTile(), (System.currentTimeMillis() - timeItem.getTime()));
            queue.removeLast();
        }
    }

    /**
     * 打印统计结果     * @return
     */
    public void print() {
        Set<String> keys = result.keySet();
        for (String key : keys) {
            System.out.println(key + " = " + result.get(key));
        }
    }


    /**
     * 获取父uri
     * * @return
     */
    public String getTile() {
        StringBuffer sb = new StringBuffer(50);
        for (TimeItem item : queue) {
            sb.append(item.getTitle()).append('+');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    class TimeItem {
        String title;
        long time;

        public TimeItem() {
            this.time = System.currentTimeMillis();
        }

        public TimeItem(String title) {
            this();
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public long getTime() {
            return time;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimeGap timeGap = new TimeGap();
        timeGap.start("method1");
        Thread.sleep(500);
        timeGap.start("method2");
        timeGap.end();
        timeGap.print();
        Thread.sleep(1000);
        timeGap.end();
        timeGap.print();
    }
}
