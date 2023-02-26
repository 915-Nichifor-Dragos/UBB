package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {

    Stack<T> stack;

    @Override
    public String toString()
    {
        StringBuilder string= new StringBuilder();

        for (T element : stack)
        {
            string.append(element.toString());
        }

        return string.toString();
    }

    public MyStack()
    {
        this.stack = new Stack<T>();
    }

    @Override
    public void push(T v) {
        stack.add(v);
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty())
            throw new ADTException("Stack is empty!");
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public List<T> getReversed() {
        List<T> list = new ArrayList<T>(this.stack);
        Collections.reverse(list);
        return list;
    }

}
