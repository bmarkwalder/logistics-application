package mands.facilities.inventory;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;

import java.io.StringWriter;
import java.util.TreeMap;

public class InventoryImpl implements Inventory {

    private TreeMap<String, InvItem> inventoryTMap;

    public InventoryImpl(TreeMap<String, InvItem> inventoryIn) throws InvalidDataException {
        setInventoryTMap(inventoryIn);
    }

    private void setInventoryTMap(TreeMap<String, InvItem> inventoryIn) throws InvalidDataException {
        if (inventoryIn == null){
            throw new InvalidDataException("InventoryImpl.setInventoryTMap(collection): collection is null");
        } if (inventoryIn.isEmpty()) {
            throw new InvalidDataException("InventoryImpl.setInventoryTMap(collection): collection is empty");
        } else {inventoryTMap = inventoryIn;}
    }

    //Accessors

    public int getItemQuantity(String idIn) throws InvalidDataException {
        if (idIn == null || idIn.isEmpty()){
            throw new InvalidDataException(idIn == null ? "InventoryImpl.getItemQuantity(string); string is null"
                                                        : "InventoryImpl.getItemQuantity(string); string is empty");
        }
        return inventoryTMap.get(idIn).getQuantity();
    }

    public boolean hasItem(String idIn) throws InvalidDataException {
        if (idIn == null || idIn.isEmpty()){
            throw new InvalidDataException(idIn == null ? "InventoryImpl.hasItem(string); string is null"
                                                        : "InventoryImpl.hasItem(string); string is empty");
        }
        return inventoryTMap.containsKey(idIn);
    }

    //Mutators

    public void decreaseInventory(String id, Integer quantity) throws IllegalParameterException {

        inventoryTMap.get(id).decreaseInvItemQuant(quantity);
    }

    public void increaseInventory(String id, Integer quantity) throws IllegalParameterException {
        inventoryTMap.get(id).increaseInvItemQuant(quantity);
    }

    //Display methods

    public void displayInventory(){

        System.out.println("\nActive Inventory:");
        System.out.println("\tItem ID\t\tQuantity");

        StringWriter depletedString = new StringWriter();

        for (String id: inventoryTMap.keySet()){
            Integer value = inventoryTMap.get(id).getQuantity();
            System.out.println(String.format("\t%-10s\t%s", id, value));
            if (value.equals(0)){
                depletedString.append(" ").append(id);
            }
        }

        if (depletedString.toString().equals("")){
            System.out.println("\nDepleted (Used-Up) Inventory: None");
        } else {
            System.out.println("\nDepleted (Used-Up) Inventory:" + depletedString);
        }
    }
}
