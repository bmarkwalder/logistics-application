package mands.shortestpath.buildlinks;


import mands.exceptions.InvalidDataException;

public class TransportLinkImpl implements TransportLink {

    private String facility;
    private String neighbor;
    private int distance;

    public TransportLinkImpl(String facilityIn, String neighborIn, int distanceIn) throws InvalidDataException {
        setFacility(facilityIn);
        setNeighbor(neighborIn);
        setDistance(distanceIn);
    }

    private void setFacility(String facilityIn) throws InvalidDataException {
        if (facilityIn == null || facilityIn.isEmpty()) {
            throw new InvalidDataException(facilityIn == null ? "TransportLinkImpl.setFacility(string); string is null"
                    : "TransportLinkImpl.setFacility(string); string is empty");
        }
        facility = facilityIn;
    }

    private void setNeighbor(String neighborIn) throws InvalidDataException {
        if (neighborIn == null || neighborIn.isEmpty()) {
            throw new InvalidDataException(neighborIn == null ? "TransportLinkImpl.setNeighbor(string); string is null"
                    : "TransportLinkImpl.setNeighbor(string); string is empty");
        }
        neighbor = neighborIn;
    }

    private void setDistance(int distanceIn) throws InvalidDataException {
        if (distanceIn <= 0) {
            throw new InvalidDataException("TransportLinkImpl.setDistance(int); Expected non-negative int");
        }
        distance = distanceIn;
    }

    public String getFacility() {
        return facility;
    }

    public String getNeighbor(){
        return neighbor;
    }

    public int getDistance(){
        return distance;
    }
}
