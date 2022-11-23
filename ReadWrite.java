/**
 * @author Omar Basheer
 *
 * */

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * This class contains methods for reading and writing into files
 * */
public class ReadWrite {

    private static String startCity;
    private static String startCountry;
    private static String destCity;
    private static String destCountry;

    /**
     * @return null.
     * This method reads an input file containing a start location and a destination
     * */
    public static void fileReader(String inputFile){

        ArrayList<String> inline = new ArrayList<>();
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader(inputFile));

            String line;

            while((line = inputStream.readLine()) != null){
                inline.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        inputConvert(inline);
    }

    /**
     * @return null.
     * This method obtains the IATA codes that correspond to the start and end destinations stated in the input file
     */
    public static void inputConvert(ArrayList<String> in_line){

        ArrayList<String> inline = in_line;
        String source = inline.get(0);
        String destination = inline.get(1);

//        ArrayList<String> filekeys = new ArrayList<>();
        String[] sourceArr = source.split(", ");
        startCity = sourceArr[0];
        startCountry = sourceArr[1];
        String[] destArr = destination.split(", ");
        destCity = destArr[0];
        destCountry = destArr[1];

        Set<ArrayList<String>> keys = Airport.airportmap.keySet();
        String startIATA = "";
        String endIATA = "";

        for(ArrayList<String> key : keys ) {
            if (key.get(1).equals(startCity) && key.get(2).equals(startCountry))
                startIATA = key.get(0);
            if (key.get(1).equals(destCity) && key.get(2).equals(destCountry))
                endIATA = key.get(0);
        }

        Route.Router.findRoute(startIATA, endIATA);
    }


    /**
     * @return null.
     * This method converts the IATA codes in the solution path into the different airports each code is assoiciated with and writes the final flight path into a new file.
     */
    public static void fileWriter( ArrayList<String> flightPath){

        try {
            String file = "flight plan.txt";
            PrintWriter outPutstream = new PrintWriter(new FileOutputStream(file));

            ArrayList<String> flightroute = flightPath;

            outPutstream.write("Flight Plan: \n");
            for(int i=0; i<flightroute.size()-1; i++){
//                Airport temp = Airport.objectInit(flightroute.get(i));
                outPutstream.write("> go from " + flightroute.get(i) + " to " + flightroute.get(i+1) + ", 0 stops (direct flight)\n");
            }
            outPutstream.write("Total Flights: "+ Integer.toString(flightroute.size()-1) + "\n");
            outPutstream.write("Total Stops: "+ Integer.toString(flightroute.size()-1));
            outPutstream.close();
        }
        catch (FileNotFoundException f) {
            System.out.println("error opening file");
            System.exit(0);
        }
    }
}
