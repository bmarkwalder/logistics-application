package mands.shortestpath.buildlinks;

import mands.exceptions.InvalidDataException;

public class StandardTransportLink implements TransportLink {

    private TransportLink myTransportLink;

    public StandardTransportLink(String facility, String neighbor, int distance) throws InvalidDataException {
        myTransportLink = TransportLinkFactory.buildLinks(facility, neighbor, distance);
    }

    public String getFacility() {
        return myTransportLink.getFacility();
    }

    public String getNeighbor() {
        return myTransportLink.getNeighbor();
    }

    public int getDistance() {
        return myTransportLink.getDistance();
    }
}
