package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.HashMap;

public interface MyILatchTable {

    void put(int key, int value) throws ADTException;
    void update(int key, int value) throws ADTException;
    int get(int key) throws ADTException;
    boolean containsKey(int key);

    int getFreeAddress();
    void setFreeAddress(int freeAddress);

    HashMap<Integer, Integer> getLatchTable();
    void setLatchTable(HashMap<Integer, Integer> newLatchTable);

}
