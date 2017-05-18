package mands;

import mands.catalog.CatalogManager;
import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import mands.facilities.FacilityManager;
import mands.shortestpath.shortestpathalgorithm.ShortestPathManager;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        //Load Catalog first so Facility Manager can validate XML Inventory Items
        try {
            CatalogManager.getInstance().loadData();
        } catch (DOMException | IllegalParameterException | IOException | InvalidDataException |
                ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        //Load and display Facilities Network
        try {
            FacilityManager.getInstance().loadData();
            FacilityManager.getInstance().displayStatusReports();
        } catch (DOMException | IllegalParameterException | IOException | InvalidDataException
                | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        System.out.println("\n");

        //Display item catalog
        CatalogManager.getInstance().displayCatalog();

        System.out.println("\n");


        System.out.println("Shortest Path Tests:\n");

        try {
            ShortestPathManager.getInstance().testShortestPath();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }
}
