
/*
 * INSTRUCTIONS
 * 
 * This program can determine the
 * MAX - This function prints the airport with the maximum number of total
 * flights. The total flights includes both arriving and departing flights for
 * each airport.
 * 
 * DEPARTURES - The goal of this function is to print an alphabetical list of
 * all destinations an airport flies to.
 * 
 * LIMIT - The limit function requires an additional integer argument on the
 * command line. This integer is used as a cut off to eliminate airports that
 * have a total number of flights less than or equal to the limit.
 * 
 * For a CSV file of flight data with input in the following format
 * 
 * CSV file with form(
 * airline airline ID source airport source airport id destination airport
 * destination airport id codeshare stops equipment)
 * MAX, LIMIT, or DEPARTURES
 * An integer only necessary for use with limit
 * 
 * Written By Nicholas Hernandez
 */

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
    // Processes the input file and calls the calculation function
    public static void main(String[] args) throws FileNotFoundException {
        // Initializes scanner
        Scanner in = null;
        try {
            in = new Scanner(new File(args[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Calls the method that does the calculations and printing
        calcPrint(args, in);
    }

    // Calculates and prints the a hash map depending on input MAX,
    // DEPARTURES, or LIMIT input
    public static void calcPrint(String[] args, Scanner in) {
        if (args[1].equals("MAX")) {
            printMax(args, in);
            }
        else if (args[1].equals("LIMIT")) {
            printLimit(args, in);

        } else {
            // If the user did not input MAX or LIMIT then it must be DEPARTURES
            printDepartures(args, in);
        }
    }

    // Prints the airports with the max amount of flights
    public static void printMax(String[] args, Scanner in) {
        // Creates the hash map for the airports and their total number of
        // flights
        HashMap<String, Integer> flights = countMaxLimit(in, args);

        // Sorts the airlines alphabetically
        ArrayList<String> airportsSorted = new ArrayList<String>(
            flights.keySet());
        Collections.sort(airportsSorted);

        // Finds the max number of flights with a minimum of 1
        double max = 1;
        for (String airport : airportsSorted) {
            if (Double.valueOf(flights.get(airport)) > max) {
                max = Double.valueOf(flights.get(airport));
        }
    }
        // Multi is used for output formatting past the first print statement
        String multi = "False";
        // Prints the airports that match the maximum amount of flights
        for (String airport : airportsSorted) {
            if (Double.valueOf(flights.get(airport)) == max
                    && multi == "False") {
                System.out.print("MAX FLIGHTS " + flights.get(airport) + " : "
                        + airport);
            multi = "True";
        }
            // If this iteration is not the first print, no new line is output
        else if(Double.valueOf(
                flights.get(airport)) == max) {
            System.out.print(' ' + airport);
        }
        }
    }

    // Prints the airports with a number of flights above a user input number
    public static void printLimit(String[] args, Scanner in) {
        // Creates the hashmap for the total flights each airline has
        HashMap<String, Integer> flights = countMaxLimit(in, args);
        // Sorts the airlines alphabetically
        ArrayList<String> airportsSorted = new ArrayList<String>(
                flights.keySet());
        Collections.sort(airportsSorted);

        // Prints the airlines that have a total flight number above a user's
        // input number
        for (String airport : airportsSorted) {
            if (Double.valueOf(flights.get(airport)) > Double
                    .valueOf(args[2])) {
                System.out.println(airport + " - " + flights.get(airport));
            }
        }
    }

    // Prints from where and to where for the departures
    public static void printDepartures(String[] args, Scanner in) {
        // Creates the hashmap for where each flight is going to and from where
        HashMap<String, String> flights = countDepartures(in);
        // Sorts the from where for the airlines alphabetically
        ArrayList<String> airportsSorted = new ArrayList<String>(
                flights.keySet());
        Collections.sort(airportsSorted);

        // Prints the departing flights in alphabetical order
        for (String airport : airportsSorted) {
            // Sorts the to where for the airlines alphabetically and eliminates
            // duplicates
            String[] str = flights.get(airport).split(" ");
            Set<String> finalSet = new HashSet<String>(Arrays.asList(str));
            String[] finalArray = finalSet.toArray(new String[finalSet.size()]);
            Arrays.sort(finalArray);

            // Converts the sorted array to a string in the proper format
            StringBuilder strbuilder = new StringBuilder();
            for (String value : finalArray) {
                strbuilder.append(' ');
                strbuilder.append(value);
            }
            String towhere = strbuilder.toString();
            System.out.println(airport + " flys to" + towhere);
        }
    }

    // Calculates and returns the number of incoming and outgoing flights for
    // max and limit
    public static HashMap<String, Integer> countMaxLimit(Scanner in,
            String[] args) {
        // A hashmap that maps destinations to their occurrences
        HashMap<String, Integer> airportToNumFlights = new HashMap<String, Integer>();
        // Skips the first line of the input file as it is just descriptions
        String str = in.nextLine();
        // Loops through the file collecting destination flights
        while (in.hasNextLine()) {
            String[] temp = in.nextLine().split(",");
            if (airportToNumFlights.get(temp[4]) != null) {
                airportToNumFlights.put(temp[4],
                        airportToNumFlights.get(temp[4]) + 1);
            } else {
                airportToNumFlights.put(temp[4], 1);
            }
        }
        // Resets the scanner to get the source flights
        in.close();
        Scanner in2 = null;
        try {
            in2 = new Scanner(new File(args[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Skips the first line as it is just descriptions
        String str2 = in2.nextLine();
        // Loops through the file collecting source flights
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

    // Calcuslates and returns the departures for a set of flight data
    public static HashMap<String, String> countDepartures(Scanner in) {
        // Maps source airports to destination airports
        HashMap<String, String> airportToNumFlights = new HashMap<String, String>();
        // Skips the first line of the input file as it is just descriptions
        String str = in.nextLine();

        // Collects the flights
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