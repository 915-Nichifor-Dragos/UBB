package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    HashMap<K, V> dictionary;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (K key : dictionary.keySet()) {
            try {
                string.append("(").append(key.toString()).append(" = ").append(lookUp(key)).append(")");
            } catch (ADTException e) {
                throw new RuntimeException(e);
            }
        }

        return string.toString();
    }

    public MyDictionary() {
        this.dictionary = new HashMap<K, V>();
    }

    @Override
    public HashMap<K, V> getContent() {
        return dictionary;
    }

    @Override
    public void put(K key, V value) throws ADTException {
        if (dictionary.containsKey(key))
            throw new ADTException("This key :" + key + " is already in the hash map!");
        dictionary.put(key, value);
    }

    @Override
    public V remove(K key) throws ADTException {
        if (!dictionary.containsKey(key))
            throw new ADTException("This key :" + key.toString() + " is not in the hash map!");
        return dictionary.remove(key);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!dictionary.containsKey(key))
            throw new ADTException("This key :" + key.toString() + " is not in the hash map!");
        dictionary.remove(key);
        dictionary.put(key, value);
    }

    @Override
    public V lookUp(K key) throws ADTException {
        if (!dictionary.containsKey(key))
            throw new ADTException("This key :" + key.toString() + " is not in the hash map, so it does not have a value!");
        return dictionary.get(key);
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public boolean isDefined(K id) {
        return this.dictionary.get(id) != null;
    }

    @Override
    public Set<K> keySet() {
        return dictionary.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.dictionary.values();
    }

}
