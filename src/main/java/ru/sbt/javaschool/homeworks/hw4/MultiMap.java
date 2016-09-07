package ru.sbt.javaschool.homeworks.hw4;

import java.util.*;

/**
 * Created by скурихин on 18.08.2016.
 */
public class MultiMap<K,V extends Truck> {
    private Map<K,ArrayList<V>> multiMap = new HashMap<K, ArrayList<V>>();

    ArrayList<V> get(K key){
        return multiMap.get(key);

    }

    void put(K key, V value){
        ArrayList<V> valueList = multiMap.get(key);
        if(valueList==null){
            valueList = new ArrayList<V>();
            valueList.add(value);
            multiMap.put(key,valueList);
        }
        else{
            valueList.add(value);
        }
    }

    @Override
    public String toString() {
        String result="";
        for (Map.Entry<K, ArrayList<V>> stringArrayListEntry : multiMap.entrySet()) {
            result+=stringArrayListEntry.getKey()+" : "+ stringArrayListEntry.getValue().toString()+"\n";
        }
        return result;
    }
}
