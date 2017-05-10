package mands.shortestpath.loadxml;

import mands.exceptions.InvalidDataException;
import mands.shortestpath.buildlinks.StandardTransportLink;
import mands.shortestpath.buildlinks.TransportLink;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * This class is responsible for parsing the network xml file.
 */
public class NetworkXmlReader implements NetworkReader {

    private String fileName;
    private TreeMap<Integer, TransportLink> transportLinks = new TreeMap<>();

    public NetworkXmlReader(String fileNameIn) throws InvalidDataException {
        if (fileNameIn == null || fileNameIn.isEmpty()){
            throw new InvalidDataException(fileNameIn == null ? "NetworkXmlReader.setFileName(string); string is null"
                    : "NetworkXmlReader.setFileName(string); string is empty");
        }
        setFileName(fileNameIn);
    }

    private void setFileName(String fileNameIn) {
        fileName = fileNameIn;
    }

    private void setTransportLinks() throws IOException, SAXException, ParserConfigurationException, InvalidDataException {
        loadData(fileName);
    }

    public TreeMap<Integer, TransportLink> loadAndReturnData() throws IOException, SAXException, ParserConfigurationException, InvalidDataException {
        setTransportLinks();
        return getTransportLinks();
    }

    public TreeMap<Integer, TransportLink> getTransportLinks() {
        return transportLinks;
    }

    private void loadData(String fileName) throws ParserConfigurationException, IOException, SAXException, InvalidDataException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        File xml = new File(fileName);
        if (!xml.exists()) {
            throw new InvalidDataException ("NetworkXmlReader.loadData(string); **** XML File '" + fileName + "' cannot be found");
        }

        Document doc = db.parse(xml);
        doc.getDocumentElement().normalize();

        NodeList facilityEntries = doc.getDocumentElement().getChildNodes();

        int count = 0;
        for (int i = 0; i < facilityEntries.getLength(); i++) {
            if (facilityEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                continue;
            }

            String entryName = facilityEntries.item(i).getNodeName();
            if (!entryName.equals("Facility")) {
                throw new InvalidDataException("NetworkXmlReader.loadXml(string); Invalid XML entry type, expected: ID, got :" + entryName);
            }

            NamedNodeMap aMap = facilityEntries.item(i).getAttributes();
            String facility = aMap.getNamedItem("ID").getNodeValue();

            Element elem = (Element) facilityEntries.item(i);

            NodeList neighborList = elem.getElementsByTagName("Neighbor");

            for (int j = 0; j < neighborList.getLength(); j++) {
                entryName = neighborList.item(j).getNodeName();
                if (!entryName.equals("Neighbor")) {
                    throw new InvalidDataException("NetworkXmlReader.loadXml(string); Invalid XML entry type, expected: Neighbor, got :" + entryName);
                }

                elem = (Element) neighborList.item(j);
                String neighbor = elem.getElementsByTagName("NeighborID").item(0).getTextContent();
                int distance = Integer.parseInt(elem.getElementsByTagName("DistanceToNeighbor").item(0).getTextContent());

                TransportLink neighbors = new StandardTransportLink(facility, neighbor, distance);
                transportLinks.put(count, neighbors);
                count++;
            }
        }
    }
}
