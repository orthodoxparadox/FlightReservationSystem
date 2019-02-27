import java.util.ArrayList;
import java.util.HashMap;

public class LockTable {
    static volatile HashMap<LockTable, ArrayList<ArrayList<Runnable>>> table;
    volatile int holders;
    volatile int locktype;

    public LockTable() {
        table = new HashMap<LockTable, ArrayList<ArrayList<Runnable>>>();
        holders = 0;
        locktype = 0;
    }

    public void acquireSharedLock(Runnable thread)
    {
        synchronized (this){
            if(table.containsKey(this))
            {
                table.get(this).get(0).add(thread);
            }
            else
            {
                ArrayList<Runnable> sList = new ArrayList<Runnable>();
                ArrayList<Runnable> xList = new ArrayList<Runnable>();
                ArrayList<ArrayList<Runnable>> R = new ArrayList<ArrayList<Runnable>>();
                R.add(sList);
                R.add(xList);
                R.get(0).add(thread);
//                whichLock.put(this, 0);
                locktype = 0;
                table.put(this, R);
            }
//        }
//        assert (!table.get(this).get(0).isEmpty());
//        assert (table.containsKey(this));
//        while(table.get(this).get(0).get(0) != thread);
//        synchronized (this)
//        {
            while(locktype ==  2);
//            whichLock.put(this, 1);
            locktype = 1;
//            table.get(this).get(2).add(thread);
            holders++;
            table.get(this).get(0).remove(thread);
        }
    }
    public void acquireExclusiveLock(Runnable thread)
    {
        synchronized (this){
            if(table.containsKey(this))
            {
                table.get(this).get(1).add(thread);
            }
            else
            {
                ArrayList<Runnable> sList = new ArrayList<Runnable>();
                ArrayList<Runnable> xList = new ArrayList<Runnable>();
                ArrayList<ArrayList<Runnable>> R = new ArrayList<ArrayList<Runnable>>();
                R.add(sList);
                R.add(xList);
                R.get(1).add(thread);
//                whichLock.put(this, 0);
                locktype = 0;
                table.put(this, R);

            }
//        }
//        assert (table.containsKey(this));
//        while(table.get(this).get(1).get(0) != thread);
//        synchronized (this)
//        {
            while(locktype != 0);
            locktype = 2;
//            table.get(this).get(2).add(thread);
            holders++;
            table.get(this).get(1).remove(thread);
        }
    }

    public void releaseSharedLock(Runnable thread)
    {
        holders--;
//            table.get(this).get(2).remove(thread);
        if (holders == 0) {
//                whichLock.put(this, 0);
            locktype = 0;
        }
    }

    public void releaseExclusiveLock(Runnable thread)
    {
            holders--;
//            table.get(this).get(2).remove(thread);
//            whichLock.put(this, 0);
            locktype = 0;
    }
}
