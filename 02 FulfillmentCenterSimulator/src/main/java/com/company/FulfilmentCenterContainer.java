package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FulfilmentCenterContainer {

    private Map<String, FulfilmentCenter> centerMap;

    public FulfilmentCenterContainer() {
        centerMap = new HashMap<>();
    }

    public void addCenter(String centerName, double maxCapcity) {
        if (centerMap.containsKey(centerName))
            System.err.println("Center with such a name already exists in container!");
        else {
            centerMap.put(centerName, new FulfilmentCenter(centerName, maxCapcity));
            System.out.println("Center " + centerName + " added successfully!");
        }
    }

    public void removeCenter(String centerName) {
        if (centerMap.containsKey(centerName)) {
            centerMap.remove(centerName);
            System.out.println("Center " + centerName + " removed successfully!");
        } else
            System.err.println("Center with name " + centerName + " not exists!");
    }

    public List<FulfilmentCenter> findEmpty() {
        List<FulfilmentCenter> emptyCenters = new LinkedList<>();

        for (Map.Entry<String, FulfilmentCenter> entry : centerMap.entrySet()) {
            if (entry.getValue().isEmpty())
                emptyCenters.add(entry.getValue());
        }

        return emptyCenters;
    }

    public void summary() {
        for (Map.Entry<String, FulfilmentCenter> entry : centerMap.entrySet()) {
            System.out.println("Center name { " + entry.getKey() + " }" + " percentageFill { "
                    + entry.getValue().getPercentageFill() + " % }");
        }
    }
}
