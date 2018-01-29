
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class PA2Main {

    private static final boolean False = false;

    public static void main(String[] args) throws FileNotFoundException {
        // TODO: write Scanner declaration here
        Scanner in = null;
        try {
            in = new Scanner(new File(args[0]));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Stores the HashMap created by countDepartures for later printing
        //HashMap<String, Integer> flights;
        
        
          if (args[1].equals("MAX")) {
          HashMap<String, Integer> flights = countMax(in);
          ArrayList<String> airportsSorted = new ArrayList<String>(
          flights.keySet());
          Collections.sort(airportsSorted);

            double max = 1;
            for (String airport : airportsSorted) {
                if (Double.valueOf(flights.get(airport)) > max) {
                    max = Double.valueOf(flights.get(airport));
                }
            }
          for (String airport : airportsSorted) {
                if (Double.valueOf(flights.get(airport)) == max) {
                    System.out.println("MAX FLIGHTS " + flights.get(airport)
                            + " : " + airport);
                }
            }
        }
          else if(args[1].equals("LIMIT")){
          HashMap<String, Integer> flights = countLimit(in);
            ArrayList<String> airportsSorted = new ArrayList<String>(
                    flights.keySet());
            Collections.sort(airportsSorted);
            for (String airport : airportsSorted) {
            if (Double.valueOf(flights.get(airport)) > Double
                        .valueOf(args[2])) {
                    System.out.println(airport + " - " + flights.get(airport));
                }
            }
          }
          else {
            HashMap<String, String> flights = countDepartures(in);
            ArrayList<String> airportsSorted = new ArrayList<String>(
                    flights.keySet());
            Collections.sort(airportsSorted);
            for (String airport : airportsSorted) {
                System.out
                        .println(airport + " flys to " + flights.get(airport));
            }
        }


        
    }

    /*
     * function: countDepartures(Scanner in)
     *
     * parameter in: in Scanner is used to loop over input file
     * 
     * This function loops over the input file to create a HashMap
     * that maps from airport codes to number of flights.
     * 
     * returns: HashMap<String, Integer> that was created
     */
    public static HashMap<String, Integer> countMax(Scanner in) {
        HashMap<String, Integer> airportToNumFlights = new HashMap<String, Integer>();
        // Skips the first line of the input file as it is just descriptions
        String str = in.nextLine();
        while (in.hasNextLine()) {
            String[] temp = in.nextLine().split(",");
            if (airportToNumFlights.get(temp[4]) != null) {
                airportToNumFlights.put(temp[4],
                        airportToNumFlights.get(temp[4]) + 1);
            } else {
                airportToNumFlights.put(temp[4], 1);
            }
        }
        return airportToNumFlights;
    }

    public static HashMap<String, Integer> countLimit(Scanner in) {
        HashMap<String, Integer> airportToNumFlights = new HashMap<String, Integer>();
        // Skips the first line of the input file as it is just descriptions
        String str = in.nextLine();
        while (in.hasNextLine()) {
            String[] temp = in.nextLine().split(",");

            if (airportToNumFlights.get(temp[4]) != null) {
                airportToNumFlights.put(temp[4],
                        airportToNumFlights.get(temp[4]) + 1);
            } else {
                airportToNumFlights.put(temp[4], 1);
            }
        }
        return airportToNumFlights;
    }

    public static HashMap<String, String> countDepartures(Scanner in) {
        HashMap<String, String> airportToNumFlights = new HashMap<String, String>();
        // Skips the first line of the input file as it is just descriptions
        String str = in.nextLine();
        while (in.hasNextLine()) {
            String[] temp = in.nextLine().split(",");
            if (airportToNumFlights.get(temp[2]) != null) {
                airportToNumFlights.put(temp[2],
                        airportToNumFlights.get(temp[2]) + " " + temp[4]);
            } else {
                airportToNumFlights.put(temp[2], temp[4]);
            }
        }
        return airportToNumFlights;
    }
}
