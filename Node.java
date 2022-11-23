import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author Omar Basheer
 *
 */
public class Node {

    /**
     * Instance variables
     */
    private String Airport_state;   // current airport IATA code
    private  Node Airport_parent;  // parent airport IATA code


    /**
     * Constructor:
     * Build and initialise objects of this class
     * @param Airport_state the IATA of an airport
     * @param Airport_parent the IATA of an airport's parent

     */
    public Node(String Airport_state, Node Airport_parent) {
        this.Airport_state = Airport_state;
        this.Airport_parent = Airport_parent;
    }


    /**
     * All getters in the class
     */
    public  String getAirport_state() {
        return Airport_state;
    }
    public  Node getAirport_parent() {
        return Airport_parent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return getAirport_state().equals(node.getAirport_state());
    }


    @Override
    public String toString() {
        return "Node {" +
                "Airport_state='" + Airport_state + '\'' +
                ", Airport_parent=" + Airport_parent +
                '}';
    }

    /**
     * @return ArrayList
     * This method returns a list of all nodes in solution path
     */

    public ArrayList<String> solutionPath(){

        ArrayList<String> solution_path = new ArrayList<>();
        solution_path.add(this.Airport_state);

        Node currentState = this.getAirport_parent();
        while(!(currentState == null)){
            solution_path.add(currentState.Airport_state);
            currentState = currentState.Airport_parent;
        }
        Collections.reverse(solution_path);

        return solution_path;

    }

}
