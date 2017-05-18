package mands.facilities;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import mands.facilities.inventory.InvItem;

import java.util.TreeMap;

public class StandardFacility implements Facility {

    private Facility myFacility;



    public StandardFacility(String name, int rate, int cost, TreeMap<String, Integer> links,
                            TreeMap<String, InvItem> inventory) throws IllegalParameterException, NullPointerException,
            InvalidDataException {
        myFacility = FacilityImplFactory.createFacility(name, rate, cost, links, inventory);
    }

    public void displayStatusReport() {
        myFacility.displayStatusReport();
    }

    public String getFacilityName() {
        return myFacility.getFacilityName();
    }

    public int getProductionRate() {
        return myFacility.getProductionRate();
    }

    public int getDailyProductionCost() {
        return myFacility.getDailyProductionCost();
    }

    //Inventory methods

    public boolean hasItem(String idIn) throws InvalidDataException {
        return myFacility.hasItem(idIn);
    }

    public int getItemQuantity(String idIn) throws InvalidDataException {
        return myFacility.getItemQuantity(idIn);
    }

    public void decreaseInventory(String id, Integer quantity) throws IllegalParameterException {
        myFacility.decreaseInventory(id, quantity);
    }

    public void increaseInventory(String id, Integer quantity) throws IllegalParameterException {
        myFacility.increaseInventory(id, quantity);
    }
}
