package ru.sbt.javaschool.homeworks.hw4;

import java.util.*;

/**
 * Created by скурихин on 13.08.2016.
 */
public class Application {

    //public static final int LIST_SIZE = 20;
    //public static final int MAX_RANDOM = 100;

    private TruckDao truckDao;
    private Map<Long, Truck> truckRegistry = new TreeMap<Long, Truck>();

    public Application(TruckDao truckDao) {
        List<Truck> list = truckDao.list();
        for (Truck truck : list) {
            Truck previousValue = truckRegistry.put(truck.getId(), truck);
            if (null != previousValue) {
                throw new IllegalStateException("Two truck with same ID");
            }
        }
    }

    public Truck getTrackById(long truckId) {
        Truck truck = truckRegistry.get(truckId);
        if (truck == null) {
            throw new IllegalArgumentException("not found truck with ID=" + truckId);
        }
        return truck;
    }

    void viewTruck() {
        System.out.println(truckRegistry);
    }

    void viewTruckRegistry(){
        for(Map.Entry<Long, Truck> truckEntry : truckRegistry.entrySet()){
            System.out.println(truckEntry.getKey()+": " + truckEntry.getValue());
        }

    }

    private static void printHelp() {
        System.out.println("Use first argument for Id");
    }

    public static void main(String[] args) {

        TruckDao truckDao = new TruckDaoMemoryImpl();
        MultiMapTypes multiMapTypes = new MultiMapTypes(truckDao);
        multiMapTypes.put(new Truck(54,3545,"asdf"));
        System.out.println(multiMapTypes.toString());

        MultiMap<String, Truck> multiMap = new MultiMap<String, Truck>();
        multiMap.put("Kamaz",new Truck(12,34,"Kamaz"));
        multiMap.put("Kamaz",new Truck(43,54,"Kamaz"));
        multiMap.put("asd",new Truck(12,34,"asd"));
        System.out.println(multiMap.get("Kamaz"));

        MultiMap<Integer,Truck> integerTruckMultiMap = new MultiMap<Integer, Truck>();
        integerTruckMultiMap.put(new Integer(12),new Truck(12,34,"Kamaz"));
        integerTruckMultiMap.put(43,new Truck(43,54,"Kamaz"));
        integerTruckMultiMap.put(12,new Truck(12,34,"asd"));
        System.out.println(integerTruckMultiMap);

    }

}
