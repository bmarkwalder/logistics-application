package mands.shortestpath;

import java.util.ArrayList;

//This class is for testing purposes only.

public class ShortestPathTest {

    private ArrayList<String> s;
    private ArrayList<String> t;

    public ShortestPathTest() {
        setLists();
    }

    private void setLists() {
        s = new ArrayList<>();
        t = new ArrayList<>();
        s.add("Santa Fe, NM"); t.add("Chicago, IL");
        s.add("Atlanta, GA");  t.add("St. Louis, MO");
        s.add("Seattle, WA"); t.add("Nashville, TN");
        s.add("New York City, NY"); t.add("Phoenix, AZ");
        s.add("Fargo, ND"); t.add("Austin, TX");
        s.add("Denver, CO"); t.add("Miami, FL");
        s.add("Austin, TX"); t.add("Norfolk, VA");
        s.add("Miami, FL"); t.add("Seattle, WA");
        s.add("Los Angeles, CA"); t.add("Chicago, IL");
        s.add("Detroit, MI"); t.add("Nashville, TN");
    }

    public ArrayList<String> returnSource(){
        return s;
    }

    public ArrayList<String> returnTarget() {
        return t;
    }
}
