package mands.catalog;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class CatalogXMLReader implements CatalogReader {

    private String fileName;
    private TreeMap<String, CatalogItem> itemManager = new TreeMap<>();

    public CatalogXMLReader(String fileNameIn) throws InvalidDataException {
        setFileName(fileNameIn);
    }

    private void setFileName(String fileNameIn) throws InvalidDataException {
        if (fileNameIn == null || fileNameIn.isEmpty()){
            throw new InvalidDataException(fileNameIn == null ? "CatalogXMLReader.setFileName(string); string is null"
                                            : "CatalogXMLReader.setFileName(string); string is empty");
        }
        fileName = fileNameIn;
    }

    private String getFileName() {
        return fileName;
    }

    public TreeMap<String, CatalogItem> loadData() throws DOMException, IllegalParameterException, InvalidDataException,
                                                          IOException, ParserConfigurationException, SAXException {

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

            NodeList catalogEntries = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < catalogEntries.getLength(); i++) {
                if (catalogEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }

                String entryName = catalogEntries.item(i).getNodeName();
                if (!entryName.equals("Item")) {
                    System.err.println("Unexpected node found: " + entryName);

                }

                // Get nodes for Id and Cost
                NamedNodeMap aMap = catalogEntries.item(i).getAttributes();
                String itemID = aMap.getNamedItem("Id").getNodeValue();
                Element elem = (Element) catalogEntries.item(i);
                int itemCost = Integer.parseInt(elem.getElementsByTagName("Cost").item(0).getTextContent());

                // Generate new catalog item; store in Tree Map; return to Catalog Manager
                CatalogItem item = new StandardCatItem(itemID, itemCost);
                itemManager.put(item.getId(), item);
            }

        return itemManager;
    }
}

