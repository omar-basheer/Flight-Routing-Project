/**
 * @author Omar Basheer
 *
 * */

import java.io.*;
import java.util.*;

public class Route{

    /**
     * Instance Variables/Fields
     */

    private String SA_Code;  // IATA code of given airport
    private String SA_ID;      // the Unique OpenFlights identifier for a source airport
    private String DA_Code;   // IATA code of given airport
    private String DA_ID;      // the Unique OpenFlights identifier for a source airport
    private String AirlineCode;    //  unique OpenFlights identifier for  an airline
    private String AirlineID;     // unique OpenFlights identifier for  an airline
    private String Stops;       // number of stops on this flight


    /**
     * Default constructor for an route object
     * */
    public Route(){
        this.SA_Code = "";
        this.SA_ID = "";
        this.DA_ID = "";
        this.DA_Code = "";
        this.AirlineCode = "";
        this.AirlineID = "";
        this.Stops = "";
    }

    /**
     * Constructor:
     * Build and initialise objects of this class
     * @param SA_Code  the IATA code of given airport
     * @param SA_ID the Unique OpenFlights identifier for a source airport
     * @param DA_ID the Unique OpenFlights identifier for a destination airport
     * @param  DA_Code the IATA code of given airport
     * @param AirlineID the unique OpenFlights identifier for  an airline
     * @param Stops the number of stops on this flight ("0" for direct)
     */
    public Route(String SA_Code, String SA_ID, String DA_Code, String DA_ID, String AirlineCode, String AirlineID, String Stops) {
        this.SA_Code = SA_Code;
        this.SA_ID = SA_ID;
        this.DA_ID = DA_ID;
        this.DA_Code = DA_Code;
        this.AirlineCode = AirlineCode;
        this.AirlineID = AirlineID;
        this.Stops = Stops;
    }

    /**
     * All getters and setters for the class
     * */
    public String getSA_Code() {
        return SA_Code;
    }
    public String getSA_ID() {
        return SA_ID;
    }
    public String getDA_Code() {
        return DA_Code;
    }
    public String getDA_ID() {
        return DA_ID;
    }
    public String getAirlineCode() {
        return AirlineCode;
    }
    public String getAirlineID() {
        return AirlineID;
    }

    public String getStops() {
        return Stops;
    }

//    public void setSA_Code(String SA_Code) {
//        this.SA_Code = SA_Code;
//    }
//
//    public void setSA_ID(String SA_ID) {
//        this.SA_ID = SA_ID;
//    }
//
//    public void setDA_Code(String DA_Code) {
//        this.DA_Code = DA_Code;
//    }
//
//    public void setDA_ID(String DA_ID) {
//        this.DA_ID = DA_ID;
//    }
//
//    public void setAirlineCode(String airlineCode) {
//        AirlineCode = airlineCode;
//    }
//
//    public void setAirlineID(String airlineID) {
//        AirlineID = airlineID;
//    }

    public void setStops(String stops) {
        Stops = stops;
    }


    /**
     * toString method for Route objects
     * */
    @Override
    public String toString(){
        return "[Route: " + "(" + getSA_Code() + ", " + getSA_ID() + ") to " + "(" + getDA_Code() + ", " + getDA_ID()+ ");  " + "AID: " + getAirlineID() + ";  AC: " + getAirlineCode() + "; Stops:" + getStops() +"]";
    }


    /**
     * This map contains the list of airports IATA that can be reached from a given IATA
     * The key is a source airport IATA and the value is an ArrayList of the IATA's of all destination airports that have their source airport equal to the key (in effect, all destination airports that can be reached from a particular source)
     */
    static HashMap<String, ArrayList<String>> routeMap = new HashMap<>();

    public class Router{

        /**
         * @return HashMap
         * This method reads the routes file into a HashMap
         */
        public static HashMap getRoutes(String Filename){
            try{
                BufferedReader inputStream = new BufferedReader(new FileReader(Filename));
                String line;

                while((line = inputStream.readLine()) != null){
                    String[]values = line.split(",");
                    String Key = values[2];

                    if(routeMap.containsKey(values[2])){
                        ArrayList<String> routeList = routeMap.get(values[2]);
                        String current = values[4];
                        routeList.add(current);
                    }
                    else {
                        ArrayList<String> routeList = new ArrayList<>();
                        String current = values[4];
                        routeList.add(current);
                        routeMap.putIfAbsent(Key, routeList);
                    }
                }
                inputStream.close();
//                System.out.println(routeMap.get("SCN").size());

            } catch (IOException e){
                throw new RuntimeException(e);
            }
            return routeMap;
        }

        /**
         * @return ArrayList
         * This method implements a breadth first search given a source airport IATA and destination airport IATA
         */
        public static ArrayList<String> findRoute(String startState, String goalState) {

            System.out.println("Source airport: " + startState);
            System.out.println("Destination airport: " + goalState);

            Deque<Node> frontier = new ArrayDeque<>();
            Node node = new Node(startState, null);

            Set<String> keys = routeMap.keySet();
            for(String key : keys ){
                if(key.equals(startState))
                    startState = key;
            }
            frontier.addLast(node);
            System.out.println("added parent " + node + " to frontier");
            ArrayList<String> exploredSet = new ArrayList<>();
            System.out.println("explored set, " +  exploredSet);

            while(!frontier.isEmpty()){

                Node nodeparent = frontier.removeFirst();
                System.out.println("popped parent, " +  nodeparent);
                String nodeString = nodeparent.getAirport_state();
                exploredSet.add(nodeString);
                System.out.println("added node to explored, explored set:" +  exploredSet);

                ArrayList<String> successors = routeMap.get(nodeString);

                if(successors != null){
                    for(int i=0; i<successors.size(); i++){
                        Node child = new Node(successors.get(i), nodeparent);
//                        System.out.println("generated successors:" +  child);
                        if(!exploredSet.contains(child.getAirport_state()) && !frontier.contains(child)){
                            if(child.getAirport_state().equals(goalState)){
                                System.out.println("found goal: " + child.getAirport_state());
                                System.out.println(child.solutionPath());
                                ReadWrite.fileWriter( child.solutionPath());
                                return child.solutionPath();
                            }
                            frontier.add(child);
                        }
                    }
                }
            }
            return null;
        }
    }
}
