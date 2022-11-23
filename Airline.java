/**
 * @author Omar Basheer
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *  This class defines methods that read data from a csv file, construct airline objects from the data, and store the airline objects into a hashmap
 */
public class Airline {

    /**
     * Private instance Variables/Fields
     */
    private String IATA_Code;   // IATA code of given airline
    private String ICAO_Code;   // ICAO code of given airline
    private String AirportID;    // ID of given airline
    private String Country;     // country of given airline

    /**
     * Default constructor for an airline object
     * */
    public Airline(){
        this.IATA_Code = "";
        this.ICAO_Code = "";
        this.AirportID = "";
        this.Country = "";
    }

    /**
     * Constructor:
     * Build and initialise objects of this class
     * @param IATA_Code the 3-letter IATA code
     * @param ICAO_Code the 4-letter ICAO code
     * @param AirportID the unique OpenFlights identifier for the airport.
     * @param Country the country where airport is located.
     */
    public Airline(String IATA_Code, String ICAO_Code, String AirportID, String Country) {
        this.IATA_Code = IATA_Code;
        this.ICAO_Code = ICAO_Code;
        this. AirportID = AirportID;
        this.Country = Country;
    }

    /**
     *  All getters and setters
     */
    public String getIATA_Code() { return IATA_Code; }
    public String getICAO_Code() { return ICAO_Code; }
    public String getAirportID() { return AirportID; }

    public String getCountry() { return Country; }


//    public void setIATA_Code(String IATA_Code) {
//        this.IATA_Code = IATA_Code;
//    }
//
//    public void setICAO_Code(String ICAO_Code) {
//        this.ICAO_Code = ICAO_Code;
//    }
//
//    public void setAirportID(String airportID) {
//        AirportID = airportID;
//    }

    public void setCountry(String country) {
        Country = country;
    }

    /**
     * toString method for Airline objects
     * */
    public String toString(){
        return "Airline -[ " + getIATA_Code() + ", " + getICAO_Code() + ", " + getAirportID() + ", " + getCountry() + " ]";
    }

    /**
     * HashMap that stores all airline objects. Keys are ArrayLists containing the ICAO of an airline, values are airline objects.
     */
    static HashMap<String, Airline> airlinemap = new HashMap<>();

    public static Map AirlinesFileReader(String Filename){

        try{
            BufferedReader inputStream = new BufferedReader(new FileReader(Filename));
            String line;
            String Key;
            Airline airlines;

            while((line = inputStream.readLine()) != null){
                String[]values = line.split(",");
                Key = "[" + values[4] + "]";
                airlines = new Airline(values[3], values[4], values[0], values[5]);
                airlinemap.putIfAbsent(Key, airlines);
                // values[0] = airline id
                // values[3] = Iata
                // values[4] = Icao
                // values[5] = Country
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return airlinemap;
    }
}

