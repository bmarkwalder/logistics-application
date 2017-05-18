package mands.facilities;

import mands.catalog.CatalogManager;
import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import mands.facilities.inventory.InvItem;
import mands.facilities.inventory.StandardInvItem;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class FacilityXMLReader implements FacilityReader {

    private String fileName;
    private TreeMap<String, Facility> objectManager = new TreeMap<>();

    public FacilityXMLReader(String fileNameIn) {
        setFileName(fileNameIn);
    }

    private void setFileName(String fileNameIn) {
        fileName = fileNameIn;
    }

    private String getFileName() {
        return fileName;
    }

    public TreeMap<String, Facility> loadData() throws DOMException, InvalidDataException, IOException,
            IllegalParameterException, ParserConfigurationException,
                                                      SAXException {

            String fileName = getFileName();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File xml = new File(fileName);
            if (!xml.exists()) {
                System.err.println("**** XML File '" + fileName + "' cannot be found");
                System.exit(-1);
            }

            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList facilityEntries = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < facilityEntries.getLength(); i++) {
                if (facilityEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }

                String entryName = facilityEntries.item(i).getNodeName();
                if (!entryName.equals("Facility")) {
                    System.err.println("Unexpected node found: " + entryName);
                }

                // Get nodes for Name, Rate, Cost
                NamedNodeMap aMap = facilityEntries.item(i).getAttributes();
                String facilityName = aMap.getNamedItem("Id").getNodeValue();
                Element elem = (Element) facilityEntries.item(i);
                Integer dailyRate = Integer.parseInt(elem.getElementsByTagName("Rate").item(0).getTextContent());
                int facilityCost = Integer.parseInt(elem.getElementsByTagName("Cost").item(0).getTextContent());

                // Get nodes for Links and Inventory Item; Store in Tree Map
                TreeMap<String, Integer> links = new TreeMap<>();
                TreeMap<String, InvItem> inventory = new TreeMap<>();
                NodeList linkList = elem.getElementsByTagName("Link");
                NodeList inventoryList = elem.getElementsByTagName("InvItem");

                // Generate TreeMap for Links
                for (int j = 0; j < linkList.getLength(); j++) {
                    if (linkList.item(j).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                    entryName = linkList.item(j).getNodeName();
                    if (!entryName.equals("Link")) {
                        System.err.println("Unexpected node found: " + entryName);
                        //return;
                    }

                    elem = (Element) linkList.item(j);
                    String linkCity = elem.getElementsByTagName("City").item(0).getTextContent();
                    Integer linkDistance = Integer.parseInt(elem.getElementsByTagName("Distance").item(0).getTextContent());
                    links.put(linkCity, linkDistance);

                }

                // Generate TreeMap for Inventory Items
                for (int k = 0; k < inventoryList.getLength(); k++) {

                    if (inventoryList.item(k).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                    entryName = inventoryList.item(k).getNodeName();
                    if (!entryName.equals("InvItem")) {
                        System.err.println("Unexpected node found: " + entryName);
                        //return;
                    }

                    elem = (Element) inventoryList.item(k);
                    String invId = elem.getElementsByTagName("ID").item(0).getTextContent();
                    Integer invQuantity = Integer.parseInt(elem.getElementsByTagName("Quantity").item(0).getTextContent());

                    if (CatalogManager.getInstance().isValidInvItem(invId)){
                        InvItem item = new StandardInvItem(invId, invQuantity);
                        inventory.put(item.getId(), item);
                    } else {
                        throw new IllegalParameterException("FacilityXMLReader.loadData(): " + invId + " is not a valid inventory item." +
                                " Will not be added to " + facilityName + "'s inventory.");
                    }

                }

                // Create Facility Object and put in HashMap objectManager; returned to Facility Manager Facade
                Facility f = new StandardFacility(facilityName, dailyRate, facilityCost, links, inventory);
                objectManager.put(f.getFacilityName(), f);

            }

        return objectManager;
    }

}


