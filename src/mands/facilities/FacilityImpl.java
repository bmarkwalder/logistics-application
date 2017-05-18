package mands.facilities;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import mands.facilities.inventory.InvItem;
import mands.facilities.inventory.Inventory;
import mands.facilities.inventory.InventoryFactory;
import mands.facilities.schedule.Schedule;
import mands.facilities.schedule.ScheduleFactory;

import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.TreeMap;

public class FacilityImpl implements Facility {

    private String facilityName;
    private Integer productionRate;
    //TODO Use int for now based on sample output; adapt later
    private int dailyProductionCost;
    private TreeMap<String, Integer> links;
    private Inventory myInventory;
    private Schedule mySchedule;

    public FacilityImpl(String nameIn, Integer rateIn, int costIn, TreeMap<String, Integer> links,
                        TreeMap<String, InvItem> inventoryIn) throws IllegalParameterException, InvalidDataException
    {
        setFacilityName(nameIn);
        setProductionRate(rateIn);
        setDailyProductionCost(costIn);
        setLinks(links);
        setMyInventory(inventoryIn);
        setMySchedule(rateIn);
    }

    private void setFacilityName(String facilityNameIn) throws InvalidDataException {
        if (facilityNameIn == null || facilityNameIn.isEmpty()){
            throw new InvalidDataException(facilityNameIn == null ? "FacilityImpl.setFacilityName(string): string is null"
                                                                  : "FacilityImpl.setFacilityName(string): string is empty");
        }
        facilityName = facilityNameIn;
    }

    private void setProductionRate(Integer productionRateIn) throws IllegalParameterException {
        if (productionRateIn <= 0){
            throw new IllegalParameterException("FacilityImpl.setProductionRate(Integer): Integer must be >0; input:" + productionRateIn);
        }
        productionRate = productionRateIn;
    }

    private void setDailyProductionCost(int dailyProductionCostIn) throws IllegalParameterException {
        if (dailyProductionCostIn <= 0){
            throw new IllegalParameterException("FacilityImpl.setProductionCost(int): int must be >0; input:" + dailyProductionCostIn);
        }
        dailyProductionCost = dailyProductionCostIn;
    }

    private void setLinks(TreeMap<String, Integer> linksIn) throws InvalidDataException {
        if (linksIn == null){
            throw new InvalidDataException("FacilityImpl.setLinks(collection): collection is null");
        } if (linksIn.isEmpty()) {
            throw new InvalidDataException("FacilityImpl.setLinks(collection): collection is empty");
        } else {links = linksIn;}
    }

    private void setMyInventory(TreeMap<String, InvItem> inventoryIn) throws InvalidDataException {
        myInventory = InventoryFactory.createInventory(inventoryIn);
    }

    private void setMySchedule(Integer rateIn) throws IllegalParameterException, InvalidDataException {
        mySchedule = ScheduleFactory.createSchedule(rateIn);
    }

    public String getFacilityName() {
        return facilityName;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public int getDailyProductionCost() {
        return dailyProductionCost;
    }

    private void displayLinks(){

        StringWriter linksString = new StringWriter();
        for (String city: links.keySet()){

            double value = links.get(city);
            double day = Math.round((value/400)*10d)/10d;
            String days = Double.toString(day);
            linksString.append(city).append(" (").append(days).append("d); ");
        }
        System.out.println("Direct Links:\n" + linksString);
    }

    public void displayStatusReport() {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println(getFacilityName());
        System.out.println("-------");
        System.out.println("Rate per day: " + getProductionRate());
        NumberFormat n = NumberFormat.getNumberInstance(Locale.US);
        System.out.println("Cost per Day: $" + n.format(getDailyProductionCost()) + "\n");
        this.displayLinks();
        myInventory.displayInventory();
        mySchedule.displaySchedule();
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    //Inventory methods

    public boolean hasItem(String idIn) throws InvalidDataException {
        return myInventory.hasItem(idIn);
    }

    public int getItemQuantity(String idIn) throws InvalidDataException {
        return myInventory.getItemQuantity(idIn);
    }

    public void decreaseInventory(String id, Integer quantity) throws IllegalParameterException {
        myInventory.decreaseInventory(id, quantity);
    }

    public void increaseInventory(String id, Integer quantity) throws IllegalParameterException {
        myInventory.increaseInventory(id, quantity);
    }

}
