package mands.facilities;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.TreeMap;

public class FacilityManager {

    //TODO Implement DTO?

    private final String FACILITY_FILE_NAME = "data/FacilityXML.xml";
    private static FacilityManager ourInstance;
    private TreeMap<String, Facility> objectManager;

    private FacilityManager(){}

    public static FacilityManager getInstance(){
        if (ourInstance == null){
            ourInstance = new FacilityManager();
        }
        return ourInstance;
    }

    public void loadData() throws DOMException, InvalidDataException, IOException, IllegalParameterException,
                                 ParserConfigurationException, SAXException {

        FacilityReader fr = new FacilityXMLReader(FACILITY_FILE_NAME);
        objectManager = fr.loadData();
        //fr.displayInitialStatusReport();
    }

    //Facility accessors

    public int getFacilityRate(String nameIn){
        return objectManager.get(nameIn).getProductionRate();
    }

    public int getFacilityCost(String nameIn){
        return objectManager.get(nameIn).getDailyProductionCost();
    }

    public void displayStatusReports() {
        for (String name: objectManager.keySet()){
            objectManager.get(name).displayStatusReport();
        }
    }

    public void displayIndividualReport(String nameIn){
        objectManager.get(nameIn).displayStatusReport();
    }

    public boolean hasItem(String facilityIn, String itemIn) throws InvalidDataException {
        if (facilityIn == null || facilityIn.isEmpty()){
            throw new InvalidDataException(facilityIn == null ? "FacilityManager.getInstance.hasItem(string1, string2); string1 is null"
                                                              : "FacilityManager.getInstance.hasItem(string1, string2); string1 is empty");
        }
        return objectManager.get(facilityIn).hasItem(itemIn);
    }

    public int getItemQuantity(String facilityIn, String itemIn) throws InvalidDataException {
        if (facilityIn == null || facilityIn.isEmpty()){
            throw new InvalidDataException(facilityIn == null ? "FacilityManager.getInstance.getItemQuantity(string1, string2); string1 is null"
                                                              : "FacilityManager.getInstance.getItemQuantity(string1, string2); string1 is empty");
        }

        return objectManager.get(facilityIn).getItemQuantity(itemIn);
    }

    //Facility mutators

    public void decreaseInventory(String nameIn, String itemIn, Integer valueIn) throws IllegalParameterException {

        objectManager.get(nameIn).decreaseInventory(itemIn, valueIn);
    }

    public void increaseInventory(String nameIn, String itemIn, Integer valueIn) throws IllegalParameterException {
        objectManager.get(nameIn).increaseInventory(itemIn, valueIn);
    }

}




