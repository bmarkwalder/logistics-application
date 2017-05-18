package mands.facilities;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;

public interface Facility {

    void displayStatusReport();

    String getFacilityName();

    int getProductionRate();

    int getDailyProductionCost();

    //Inventory methods

    boolean hasItem(String idIn) throws InvalidDataException;

    int getItemQuantity(String id) throws InvalidDataException;

    void decreaseInventory(String id, Integer quantity) throws IllegalParameterException;

    void increaseInventory(String id, Integer quantity) throws IllegalParameterException;
}
