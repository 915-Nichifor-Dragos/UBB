package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.List;

public interface MyIStack<T> {

    T pop() throws ADTException;
    void push(T v);
    boolean isEmpty();
    List<T> getReversed();

}