package Model.ADTs;

import Model.Exceptions.ADTException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface MyIBarrierTable {
    void put(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    void update(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    Pair<Integer, List<Integer>> get(int key) throws ADTException;
    boolean containsKey(int key);

    int getFreeAddress();
    void setFreeAddress(int freeAddress);

    HashMap<Integer, Pair<Integer, List<Integer>>> getBarrierTable();
    void setBarrierTable(HashMap<Integer, Pair<Integer, List<Integer>>> newBarrierTable);
}
