package mands.facilities.inventory;

import mands.exceptions.InvalidDataException;

import java.util.TreeMap;

public class InventoryFactory {

    public static Inventory createInventory(TreeMap<String, InvItem> inventory) throws NullPointerException,
            InvalidDataException {
        return new InventoryImpl(inventory);
    }
}
