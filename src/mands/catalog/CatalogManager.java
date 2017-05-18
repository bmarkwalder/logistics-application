package mands.catalog;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.TreeMap;

public class CatalogManager {

    private final String CATALOG_FILE_NAME = "data/CatalogXML.xml";
    private static CatalogManager ourInstance;
    private TreeMap<String, CatalogItem> itemManager;

    private CatalogManager(){}

    public static CatalogManager getInstance(){
        if (ourInstance == null){
            ourInstance = new CatalogManager();
        }
        return ourInstance;
    }

    public void loadData() throws DOMException, IllegalParameterException,
            InvalidDataException, IOException,
                                 ParserConfigurationException, SAXException {

        CatalogReader cr = new CatalogXMLReader(CATALOG_FILE_NAME);
        itemManager = cr.loadData();
    }

    // Accessors
    public int getItemCost(String itemIdIn){
        return itemManager.get(itemIdIn).getCost();
    }

    public boolean isValidInvItem(String idIn) throws InvalidDataException {
        if (idIn == null || idIn.isEmpty()){
            throw new InvalidDataException(idIn == null ? "CatalogManager.getInstance.isValidInvItem(string): string is null"
                    : "CatalogManager.getInstance.isValidInvItem(string): string is empty");
        }

        return itemManager.containsKey(idIn);
    }

    // Mutators
    /*Don't know we'll need this for project. But useful if we expand later to allow for updating
    inventories. Setting to private for now. */
    private void addItem(String idIn, CatalogItem itemIn){
        itemManager.put(idIn, itemIn);
    }

    //Display methods
    public void displayCatalog() {

        int i = 0;
        StringWriter catString = new StringWriter();

        for (String id: itemManager.keySet()){

            catString.append(String.format("%-20s", itemManager.get(id).returnItem())).append("\t\t");
            i+=1;
            if(i % 4 == 0){
                catString.append("\n");
            }
        }
        System.out.println(catString);
    }


}
