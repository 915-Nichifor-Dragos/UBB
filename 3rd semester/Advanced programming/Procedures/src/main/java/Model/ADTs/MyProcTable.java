package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Statement.IStmt;
import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyProcTable implements MyIProcTable {
    private HashMap<String, Pair<List<String>, IStmt>> procTable;

    public MyProcTable() {
        this.procTable = new HashMap<>();
    }

    @Override
    public boolean isDefined(String key) {
        synchronized (this) {
            return this.procTable.containsKey(key);
        }
    }

    @Override
    public void put(String key, Pair<List<String>, IStmt> value) {
        synchronized (this) {
            this.procTable.put(key, value);
        }
    }

    @Override
    public Pair<List<String>, IStmt> lookUp(String key) throws ADTException {
        synchronized (this) {
            if (!isDefined(key))
                throw new ADTException(key + " is not defined.");
            return this.procTable.get(key);
        }
    }

    @Override
    public void update(String key, Pair<List<String>, IStmt> value) throws ADTException {
        synchronized (this) {
            if (!isDefined(key))
                throw new ADTException(key + " is not defined.");
            this.procTable.put(key, value);
        }
    }

    @Override
    public Collection<Pair<List<String>, IStmt>> values() {
        synchronized (this) {
            return this.procTable.values();
        }
    }

    @Override
    public void remove(String key) throws ADTException {
        synchronized (this) {
            if (!isDefined(key))
                throw new ADTException(key + " is not defined.");
            this.procTable.remove(key);
        }
    }

    @Override
    public Set<String> keySet() {
        synchronized (this) {
            return procTable.keySet();
        }
    }

    @Override
    public HashMap<String, Pair<List<String>, IStmt>> getContent() {
        synchronized (this) {
            return procTable;
        }
    }

    @Override
    public MyIDictionary<String, Pair<List<String>, IStmt>> deepCopy() throws ADTException {
        MyIDictionary<String, Pair<List<String>, IStmt>> toReturn = new MyDictionary<>();
        for (String key: keySet())
            try {
                toReturn.put(key, lookUp(key));
            } catch (ADTException e) {
                throw new ADTException(e.getMessage());
            }
        return toReturn;
    }

    @Override
    public String toString() {
        return procTable.toString();
    }
}
