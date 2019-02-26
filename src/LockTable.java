import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LockTable {
    static HashMap<Object, ArrayList<ArrayList<Runnable>>> table = new HashMap<Object, ArrayList<ArrayList<Runnable>>>();
    static ReentrantLock lock = new ReentrantLock();
    public LockTable() {
    }
    public static boolean acquireSharedLock(Object o, Runnable thread)
    {
        while(lock.tryLock());
        if(table.containsKey(o))
        {
            table.get(o).get(0).add(thread);
        }
        else
        {
            ArrayList<Runnable> sList = new ArrayList<Runnable>();
            ArrayList<Runnable> xList = new ArrayList<Runnable>();
            ArrayList<ArrayList<Runnable>> R = new ArrayList<ArrayList<Runnable>>();
            R.add(sList);
            R.add(xList);
            R.get(0).add(thread);
            table.put(o, R);
            if()
        }
        lock.unlock();
    }
    public static boolean acquireExclusiveLock(Object o, Runnable thread)
    {
        while(lock.tryLock());
        if(table.containsKey(o))
        {
            table.get(o).get(1).add(thread);
        }
        else
        {
            ArrayList<Runnable> sList = new ArrayList<Runnable>();
            ArrayList<Runnable> xList = new ArrayList<Runnable>();
            ArrayList<ArrayList<Runnable>> R = new ArrayList<ArrayList<Runnable>>();
            R.add(sList);
            R.add(xList);
            R.get(1).add(thread);
            table.put(o, R);
        }
        lock.unlock();
    }
    public static void releaseSharedLock(ReentrantLock l)
    {

    }
    public static void releaseExclusiveLock(ReentrantLock l)
    {

    }
}
