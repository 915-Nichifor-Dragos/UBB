package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    HashMap<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        synchronized (this) {
            return this.dictionary.containsKey(key);
        }
    }

    @Override
    public V lookUp(K key) throws ADTException {
        synchronized (this) {
            if (!isDefined(key))
                throw new ADTException(key + " is not defined.");
            return this.dictionary.get(key);
        }
    }

    @Override
    public void update(K key, V value) throws ADTException {
        synchronized (this) {
            if (!isDefined(key))
                throw new ADTException(key + " is not defined.");
            this.dictionary.put(key, value);
        }
    }

    @Override
    public Collection<V> values() {
        synchronized (this) {
            return this.dictionary.values();
        }
    }

    @Override
    public void remove(K key) throws ADTException {
        synchronized (this) {
            if (!isDefined(key))
                throw new ADTException(key + " is not defined.");
            this.dictionary.remove(key);
        }
    }

    @Override
    public Set<K> keySet() {
        synchronized (this) {
            return dictionary.keySet();
        }
    }

    @Override
    public Map<K, V> getContent() {
        synchronized (this) {
            return dictionary;
        }
    }

    @Override
    public MyIDictionary<K, V> deepCopy() throws ADTException {
        MyIDictionary<K, V> toReturn = new MyDictionary<>();
        for (K key : keySet())
            try {
                toReturn.put(key, lookUp(key));
            } catch (ADTException e) {
                throw new ADTException(e.getMessage());
            }
        return toReturn;
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }

    @Override
    public void put(K key, V value) {
        synchronized (this) {
            this.dictionary.put(key, value);
        }

    }
}
