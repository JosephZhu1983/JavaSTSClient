package com.kongzhonghd.sts.pool;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-8-1
 * Time: PM4:00`
 * To change this template use File | Settings | File Templates.
 * 轮训调度容器
 */
public class RoundRobbinScheduler<T> implements Iterable<T>
{
    private final AtomicInteger counter = new AtomicInteger();
    private final List<T> items = new ArrayList<T>();
    private final ReadWriteLock locker = new ReentrantReadWriteLock();

    public int size()
    {
        locker.readLock().lock();
        try
        {
            return items.size();
        }
        finally
        {
            locker.readLock().unlock();
        }
    }

    public Iterator<T> iterator()
    {
        return items.iterator();
    }

    public void remove(T item)
    {
        locker.writeLock().lock();
        try
        {
            items.remove(item);
        }
        finally
        {
            locker.writeLock().unlock();
        }
    }

    public void add(T item)
    {
        locker.writeLock().lock();
        try
        {
            items.add(item);
        }
        finally
        {
            locker.writeLock().unlock();
        }
    }

    public T get()
    {
        locker.readLock().lock();
        try
        {
            if (items.size() == 0)
                return null;
            T item = items.get(counter.incrementAndGet() % items.size());
            return item;
        }
        finally
        {
            locker.readLock().unlock();
        }
    }
}
