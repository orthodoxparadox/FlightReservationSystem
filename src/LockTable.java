import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LockTable {
    volatile static HashMap<Object, ArrayList<ArrayList<Runnable>>> table = new HashMap<Object, ArrayList<ArrayList<Runnable>>>();
    volatile static HashMap<Object, Integer> whichLock = new HashMap<Object, Integer>();
    public LockTable() {
    }
    public static void acquireSharedLock(Object o, Runnable thread)
    {
        synchronized (o){
            if(table.containsKey(o))
            {
                table.get(o).get(0).add(thread);
            }
            else
            {
                ArrayList<Runnable> sList = new ArrayList<Runnable>();
                ArrayList<Runnable> xList = new ArrayList<Runnable>();
                ArrayList<Runnable> lockHolders = new ArrayList<Runnable>();
                ArrayList<ArrayList<Runnable>> R = new ArrayList<ArrayList<Runnable>>();
                R.add(sList);
                R.add(xList);
                R.add(lockHolders);
                R.get(0).add(thread);
                whichLock.put(o, 0);
                table.put(o, R);
            }
        }
        while(table.get(o).get(0).get(0) != thread);
        synchronized (o)
        {
            while(whichLock.get(o) == 2);
            whichLock.replace(o, 1);
            table.get(o).get(2).add(thread);
            table.get(o).get(0).remove(thread);
        }
    }
    public static void acquireExclusiveLock(Object o, Runnable thread)
    {
        synchronized (o){
            if(table.containsKey(o))
            {
                table.get(o).get(1).add(thread);
            }
            else
            {
                ArrayList<Runnable> sList = new ArrayList<Runnable>();
                ArrayList<Runnable> xList = new ArrayList<Runnable>();
                ArrayList<Runnable> lockHolders = new ArrayList<Runnable>();
                ArrayList<ArrayList<Runnable>> R = new ArrayList<ArrayList<Runnable>>();
                R.add(sList);
                R.add(xList);
                R.add(lockHolders);
                R.get(1).add(thread);
                whichLock.put(o, 0);
                table.put(o, R);
            }
        }
        while(table.get(o).get(1).get(0) != thread);
        synchronized (o)
        {
            while(whichLock.get(o) != 0);
            whichLock.replace(o, 2);
            assert(table.get(o).get(2).isEmpty());
            table.get(o).get(2).add(thread);
            table.get(o).get(1).remove(thread);
        }
    }

    public static void releaseSharedLock(Object o, Runnable thread)
    {
        table.get(o).get(2).remove(thread);
        if(table.get(o).get(2).isEmpty())
        {
            whichLock.replace(o, 0);
        }
    }

    public static void releaseExclusiveLock(Object o, Runnable thread)
    {
        table.get(o).get(2).remove(thread);
        whichLock.replace(o, 0);
    }
}
