package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.List;

public interface MyIList<T> {

    void add(T v);

    T remove(int pos) throws ADTException;

    int size();

    List<T> getList();
}
