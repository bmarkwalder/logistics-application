package mands.shortestpath.loadxml;

import mands.exceptions.InvalidDataException;
import mands.shortestpath.buildlinks.TransportLink;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.TreeMap;


public final class LoadNetworkLinks {

    private final String NETWORK_FILE_NAME = "data/TransportationNetwork.xml";
    private TreeMap<Integer, TransportLink> network = new TreeMap<>();
    private static LoadNetworkLinks ourInstance = new LoadNetworkLinks();

    public static LoadNetworkLinks getInstance() {
        if (ourInstance == null){
            ourInstance = new LoadNetworkLinks();
        }
        return ourInstance;
    }

    private LoadNetworkLinks(){
        setNetwork();
    }

    private void setNetwork(){
        try {
            network = new NetworkXmlReader(NETWORK_FILE_NAME).loadAndReturnData();
        } catch (IOException | SAXException | ParserConfigurationException |InvalidDataException e) {
            e.printStackTrace();
        }
    }

    public TreeMap<Integer, TransportLink> getNetwork(){
        return network;
    }
}
