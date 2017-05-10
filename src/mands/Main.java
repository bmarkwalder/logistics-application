package mands;

import mands.exceptions.InvalidDataException;
import mands.shortestpath.shortestpathalgorithm.ShortestPathManager;

public class Main {

    public static void main(String[] args) {

        System.out.println("Shortest Path Tests:\n");

        try {
            ShortestPathManager.getInstance().testShortestPath();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }
}
