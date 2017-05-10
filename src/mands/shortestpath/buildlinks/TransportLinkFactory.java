package mands.shortestpath.buildlinks;

import mands.exceptions.InvalidDataException;

public class TransportLinkFactory {

    public static TransportLink buildLinks(String facilityIn, String neighborIn, int distanceIn) throws InvalidDataException {

        return new TransportLinkImpl(facilityIn, neighborIn, distanceIn);
    }
}
