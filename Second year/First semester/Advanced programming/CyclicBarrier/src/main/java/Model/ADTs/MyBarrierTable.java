package Model.ADTs;

import Model.Exceptions.ADTException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class MyBarrierTable implements MyIBarrierTable {

    private HashMap<Integer, Pair<Integer, List<Integer>>> barrierTable;
    private int freeLocation = 0;

    public MyBarrierTable()
    {
        this.barrierTable = new HashMap<>();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this) {
            if (this.barrierTable.containsKey(key))
                throw new ADTException(String.format("Key %d already in barrier table", key));
            barrierTable.put(key, value);
        }
    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this) {
            if (!this.barrierTable.containsKey(key))
                throw new ADTException(String.format("Key %d not in barrier table", key));
            barrierTable.replace(key, value);
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) throws ADTException {
        synchronized (this) {
            if (!this.barrierTable.containsKey(key))
                throw new ADTException(String.format("Key %d not in barrier table", key));
            return barrierTable.get(key);
        }
    }

    @Override
    public boolean containsKey(int key) {
        return this.barrierTable.containsKey(key);
    }

    @Override
    public int getFreeAddress() {
        this.freeLocation ++;
        return freeLocation;
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        this.freeLocation = freeAddress;
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return this.barrierTable;
    }

    @Override
    public void setBarrierTable(HashMap<Integer, Pair<Integer, List<Integer>>> newBarrierTable) {
        this.barrierTable = newBarrierTable;
    }

    @Override
    public String toString() {return this.barrierTable.toString();}

}
