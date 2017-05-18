package mands.facilities;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.TreeMap;

public interface FacilityReader {

    TreeMap<String, Facility> loadData() throws DOMException, InvalidDataException, IOException,
            IllegalParameterException, ParserConfigurationException,
                                                SAXException;

}
