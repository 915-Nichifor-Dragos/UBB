package Model.ADTs;

import Model.Exceptions.ADTException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class MySemaphoreTable implements MyISemaphoreTable {

    private HashMap<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private int freeLocation = 0;

    public MySemaphoreTable()
    {
        this.semaphoreTable = new HashMap<>();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this)
        {
            if (semaphoreTable.containsKey(key))
                throw new ADTException(String.format("The key %d is already in the semaphore table!", key));
            semaphoreTable.put(key, value);
        }
    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this)
        {
            if (! semaphoreTable.containsKey(key))
                throw new ADTException(String.format("The key %d is not in the semaphore table!", key));
            semaphoreTable.replace(key, value);
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) throws ADTException {
        synchronized (this)
        {
            if (! semaphoreTable.containsKey(key))
                throw new ADTException(String.format("The key %d is not in the semaphore table!", key));
            return semaphoreTable.get(key);
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this)
        {
            return semaphoreTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this)
        {
            freeLocation ++;
            return freeLocation;
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this)
        {
            this.freeLocation = freeAddress;
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return this.semaphoreTable;
    }

    @Override
    public void setSemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> newSemaphoreTable) {
        this.semaphoreTable = newSemaphoreTable;
    }

    @Override
    public String toString() {return semaphoreTable.toString();}

}
