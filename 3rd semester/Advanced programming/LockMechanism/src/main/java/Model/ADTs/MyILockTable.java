package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Set;

public interface MyILockTable {
    int getFreeValue();
    HashMap<Integer, Integer> getContent();
    void setContent(HashMap<Integer, Integer> newMap);
    void put(int key, int value) throws ADTException;
    void update(int position, int value) throws ADTException;
    int get(int position) throws ADTException;
    boolean containsKey(int position);
    Set<Integer> keySet();
}