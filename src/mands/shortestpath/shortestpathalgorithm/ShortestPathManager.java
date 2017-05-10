package mands.shortestpath.shortestpathalgorithm;

import mands.exceptions.InvalidDataException;
import mands.shortestpath.ShortestPathTest;

import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * This facade accepts requests for the shortest path between two facilities and can return
 * the path, the distance, and the start and end facilities. Additionally it can print the
 * shortest path details
 */
public final class ShortestPathManager {

    private static String startFacility;
    private static String endFacility;
    private static ArrayList path = new ArrayList();
    private static int distance;
    private static ShortestPathManager ourInstance = new ShortestPathManager();
    private final float DIST_PER_DAY = 400; //8 hours per day * 50 mph

    public static ShortestPathManager getInstance() {

        if (ourInstance == null){
            ourInstance = new ShortestPathManager();
        }
        return ourInstance;
    }

    private ShortestPathManager() {
    }

    // Temporary method for demonstrating the algorithm
    public void testShortestPath() throws InvalidDataException {
        ShortestPathTest spt = new ShortestPathTest();
        ArrayList<String> s = spt.returnSource();
        ArrayList<String> t = spt.returnTarget();

        for (int i = 0; i < s.size(); i++) {
            getShortestPath(s.get(i), t.get(i));
            printPathDetails();
        }
    }

    public void getShortestPath(String startFacilityIn, String endFacilityIn) throws InvalidDataException {
        if (startFacilityIn == null || startFacilityIn.isEmpty()) {
            throw new InvalidDataException(startFacilityIn == null ? "ShortestPathManager.getShortestPath(string, string); string startFacilityIn is null"
                    : "ShortestPathManager.getShortestPath(string, string); string startFacilityIn is empty");
        }
        if (endFacilityIn == null || endFacilityIn.isEmpty()) {
            throw new InvalidDataException(endFacilityIn == null ? "ShortestPathManager.getShortestPath(string, string); string endFacilityIn is null"
                    : "ShortestPathManager.getShortestPath(string, string); string endFacilityIn is empty");
        }
        startFacility = startFacilityIn;
        endFacility = endFacilityIn;
        findShortestPath();
    }

    private void findShortestPath() throws InvalidDataException {
        path = new ShortestPath(startFacility, endFacility).getPath();
        distance = ShortestPath.getDistance();
    }

    public int getDistance(){
        return distance;
    }

    public ArrayList totalPath(){
        return path;
    }

    public String getStartFacility(){
        return startFacility;
    }

    public String getEndFacility(){
        return endFacility;
    }

    private String formatDistance(){
        int pathDistance = getDistance();
        String formattedDistance = NumberFormat.getIntegerInstance().format(pathDistance);
        return formattedDistance;
    }

    private float travelDays(){
        float travelDays = (distance / DIST_PER_DAY);
        return travelDays;
    }

    //Generate and print sample shortest path output
    private StringWriter printPath(){
        ArrayList pathToPrint = path;
        StringWriter shortestPath = new StringWriter();

        for (int i = 0; i < pathToPrint.size(); i++){
            String facility = pathToPrint.get(i).toString();
            if (i < pathToPrint.size()-1){
                shortestPath.append(facility + " -> ");
            }
            else {
                shortestPath.append(facility + " =" + " " + formatDistance() + " mi");
            }
        }

        pathToPrint.clear(); //required to remove the path from previous calls to printPath
        return shortestPath;
    }

    public void printPathDetails() {
        System.out.println(startFacility + " " + "to " + endFacility + ":");
        System.out.println(printPath());
        System.out.printf(formatDistance() + " mi / (8 hours per day * 50 mph) = " + "%.2f days", travelDays());
        System.out.println("\n");
    }
}
