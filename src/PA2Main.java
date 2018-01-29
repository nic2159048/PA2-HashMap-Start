
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        calcPrint(args, in);

    }

    public static void calcPrint(String[] args, Scanner in) {
        if (args[1].equals("MAX")) {
            HashMap<String, Integer> flights = countMax(in, args);
            ArrayList<String> airportsSorted = new ArrayList<String>(
                    flights.keySet());
            Collections.sort(airportsSorted);
            double max = 1;
            for (String airport : airportsSorted) {
                if (Double.valueOf(flights.get(airport)) > max) {
                    max = Double.valueOf(flights.get(airport));
                }
            }
            String multi="False";
            for (String airport : airportsSorted) {
                if (Double.valueOf(
                        flights.get(airport)) == max && multi == "False") {
                    System.out.print("MAX FLIGHTS " + flights.get(airport)
                            + " : " + airport);
                    multi = "True";
                }
                else if(Double.valueOf(
                        flights.get(airport)) == max) {
                    System.out.print(' ' + airport);
                }
            }
        } else if (args[1].equals("LIMIT")) {
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
        } else {
            HashMap<String, String> flights = countDepartures(in);
            ArrayList<String> airportsSorted = new ArrayList<String>(
                    flights.keySet());
            Collections.sort(airportsSorted);
            for (String airport : airportsSorted) {
                String[] str = flights.get(airport).split(" ");

                Set<String> targetSet = new HashSet<String>(
                        Arrays.asList(str));
                String[] targetArray = targetSet
                        .toArray(new String[targetSet.size()]);
                Arrays.sort(targetArray);
                StringBuilder builder = new StringBuilder();
                for (String value : targetArray) {
                    builder.append(' ');
                    builder.append(value);
                }
                String text = builder.toString();
                System.out
                        .println(airport + " flys to" + text);
            }
        }
    }

    public static HashMap<String, Integer> countMax(Scanner in, String[] args) {
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
        in.close();
        Scanner in2 = null;
        try {
            in2 = new Scanner(new File(args[0]));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String str2 = in2.nextLine();

        while (in2.hasNextLine()) {
            String[] temp = in2.nextLine().split(",");
            if (airportToNumFlights.get(temp[2]) != null) {
                airportToNumFlights.put(temp[2],
                        airportToNumFlights.get(temp[2]) + 1);
            } else {
                airportToNumFlights.put(temp[2], 1);
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
        HashSet<String> h = new HashSet<String>();
        while (in.hasNextLine()) {
            String[] temp = in.nextLine().split(",");
            if (airportToNumFlights.get(temp[2]) != null
            ) {
                airportToNumFlights.put(temp[2],
                        airportToNumFlights.get(temp[2]) + " " + temp[4]);
            } else {
                airportToNumFlights.put(temp[2], temp[4]);
                // h.add(temp[4]);
            }
        }
        return airportToNumFlights;
    }
}
