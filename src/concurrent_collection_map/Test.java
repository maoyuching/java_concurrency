package concurrent_collection_map;

import com.sun.xml.internal.bind.v2.model.core.EnumConstant;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Test {

    public static void main(String[] args) {

        /*
        hashtable 中 元素不可以为 null
        映射是无序的
         */
        Hashtable<String, String> en2chDict = new Hashtable<>();
        //java.util.Hashtable 实现 Map接口 方法都是synchronized的

        en2chDict.put("hello", "你好");

        en2chDict.containsKey("hello"); //请问尊敬的词典先生，您知道hello么？
        en2chDict.containsValue("你好");

        String ans = en2chDict.get("hello");
        en2chDict.remove("word");


        /*
        concurrentHashMap 性能更高，允许多个修改操作concurrent进行
        使用了锁分离 锁更小 更细致
         */
        ConcurrentHashMap<String, String> person2nickname = new ConcurrentHashMap<>();
        person2nickname.put("罗永浩", "罗老师");
        person2nickname.get("马化腾");
        Enumeration<String> it = person2nickname.keys();
        person2nickname.clear();


        /*
        写时复制 数组
        当要写时候，写到副本上，在把指针指向副本
        高性能concurrent读取， 且保证读取正确
         */
        CopyOnWriteArrayList<String> stars = new CopyOnWriteArrayList<>();
        boolean addsuccess = stars.add("高圆圆");
        stars.add(0, "刘亦菲");
        stars.get(0);
        stars.isEmpty();
        stars.contains("who");
        stars.isEmpty();
        stars.toArray();
        stars.iterator();


        /*
        和CopyOnWriteArrayList的很相似
        copy on write 机制 适合 读取多 写的少
         */
        CopyOnWriteArraySet<String> s = new CopyOnWriteArraySet<>();
        s.add("");
        s.contains("");
        s.iterator();
        s.remove("");


        /*
        vector 是线程安全的， 而 类似的 ArrayList 则不是
        vector 在方法上修饰 synchronized
         */
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add(0,"a");
        v.capacity();
        v.contains("");
        v.indexOf("");
        v.isEmpty();
        v.remove("");


        /*
        StringBuffer 是线程安全的 而类似的StringBuilder则不是
        StringBuffer 在方法上修饰 synchronized
         */
        StringBuffer strb = new StringBuffer();
        strb.append("a");
        strb.length();
        strb.toString();
    }
}
