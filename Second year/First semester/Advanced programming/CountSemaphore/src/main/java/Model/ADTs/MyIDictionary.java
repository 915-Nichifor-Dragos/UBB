package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Value.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public interface MyIDictionary<K, V>  {

    public HashMap<K, V> getContent();
    void put(K key, V value) throws ADTException;
    V remove(K key) throws ADTException;
    void update(K key, V value) throws ADTException;
    int size();
    V lookUp(K key) throws ADTException;
    boolean isDefined(K id);
    public Set<K> keySet();
    public Collection<V> values();

}
