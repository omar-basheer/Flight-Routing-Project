/**
 * @author Omar Basheer
 *
 * */
import java.util.Map;

public class Main {

    public static void main(String[]args) {

        /*
        intialize Airport map and Route Map
      */
        Map AirportsMap = Airport.AirportFileReader("airports.csv");
        Map RoutesMap = Route.Router.getRoutes("routes.csv");

        /*
        pass string name of an input file as argument
        */
//        readWrite.fileReader("accra_to_newyork.txt");
//        System.out.println(Route.Router.findRoute("SPI", "YWG"));
//        ReadWrite.fileReader("sochi_to_kazan.txt");
        ReadWrite.fileReader("accra_to_winnipeg.txt");


//        System.out.println(Airport.objectInit("ACC"));
//        System.out.println(RoutesMap.get("ACC"));
//        System.out.println(Airport.objectInit("NBO"));
//        System.out.println(RoutesMap.get("NBO"));
//        System.out.println(Airport.objectInit("MGQ"));
//        System.out.println(RoutesMap.get("MGQ"));
    }
}
