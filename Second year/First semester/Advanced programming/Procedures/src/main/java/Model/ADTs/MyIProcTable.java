package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Statement.IStmt;
import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface MyIProcTable {

    boolean isDefined(String key);
    void put(String key, Pair<List<String>, IStmt> value);
    Pair<List<String>, IStmt> lookUp(String key) throws ADTException;
    void update(String key,  Pair<List<String>, IStmt> value) throws ADTException;
    Collection< Pair<List<String>, IStmt>> values();
    void remove(String key) throws ADTException;
    Set<String> keySet();
    HashMap<String,  Pair<List<String>, IStmt>> getContent();
    MyIDictionary<String, Pair<List<String>, IStmt>> deepCopy() throws ADTException;

}
