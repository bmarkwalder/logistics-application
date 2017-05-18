package mands.facilities;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import mands.facilities.inventory.InvItem;

import java.util.TreeMap;

public class FacilityImplFactory{

    public static Facility createFacility(String name, int rate, int cost, TreeMap<String, Integer> links,
                                          TreeMap<String, InvItem> inventory) throws IllegalParameterException,
                                          NullPointerException, InvalidDataException
    {
        return new FacilityImpl(name, rate, cost, links, inventory);
    }
}
