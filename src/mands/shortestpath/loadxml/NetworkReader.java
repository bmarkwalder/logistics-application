package mands.shortestpath.loadxml;

import mands.exceptions.InvalidDataException;
import mands.shortestpath.buildlinks.TransportLink;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Interface for network data loaders
 */
public interface NetworkReader {

    TreeMap<Integer, TransportLink> loadAndReturnData() throws IOException, SAXException, ParserConfigurationException, InvalidDataException;

    TreeMap<Integer, TransportLink> getTransportLinks();
}
