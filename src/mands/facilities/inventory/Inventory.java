package mands.facilities.inventory;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;

public interface Inventory {

    boolean hasItem(String idIn) throws InvalidDataException;

    int getItemQuantity(String idIn) throws InvalidDataException;

    void decreaseInventory(String id, Integer quantity) throws IllegalParameterException;

    void increaseInventory(String id, Integer quantity) throws IllegalParameterException;

    void displayInventory();

}
