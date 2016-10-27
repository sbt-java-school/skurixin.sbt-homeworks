package ru.sbt.javaschool.homeworks.hw4;

import java.util.*;

/**
 * Created by скурихин on 18.08.2016.
 */
public class MultiMapTypes {
    private Map<String,ArrayList<Truck>> multiMap = new HashMap<String, ArrayList<Truck>>();

    public MultiMapTypes() {
    }

    public MultiMapTypes(TruckDao truckDao) {

        for (Truck truck : truckDao.list()) {
            String keyType = truck.getType();
            ArrayList<Truck> array = multiMap.get(keyType);
            if (array == null) {
                array = new ArrayList<Truck>();
                array.add(truck);
                multiMap.put(keyType, array);
            } else {
                multiMap.get(keyType).add(truck);
            }
        }
    }

    ArrayList<Truck> get(String type){
        return multiMap.get(type);

    }

    void put(Truck truck){
        ArrayList<Truck> trucks = multiMap.get(truck.getType());
        if(trucks==null){
            trucks = new ArrayList<Truck>();
            trucks.add(truck);
            multiMap.put(truck.getType(),trucks);
        }
        else{
            trucks.add(truck);
        }
    }

    @Override
    public String toString() {
        String result="";
        for (Map.Entry<String, ArrayList<Truck>> stringArrayListEntry : multiMap.entrySet()) {
            result+=stringArrayListEntry.getKey()+" : "+ stringArrayListEntry.getValue().toString()+"\n";
        }
        return result;
    }
}
