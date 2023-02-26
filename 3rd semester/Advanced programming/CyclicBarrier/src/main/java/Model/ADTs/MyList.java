package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    ArrayList<T> list;

    @Override
    public String toString()
    {
        StringBuilder string= new StringBuilder();

        for (T element : list)
        {
            string.append(element.toString());
        }

        return string.toString();
    }

    public MyList()
    {
        this.list = new ArrayList<T>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T remove(int pos) throws ADTException {
       if (pos > list.size())
           throw new ADTException("This position does not exits in the list!");
        return list.remove(pos);
    }

    @Override
    public int size() {
        return list.size();
    }

    public List<T> getList() {
        return this.list;
    }

}
