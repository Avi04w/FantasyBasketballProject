/* Avi Walia
 * June 16
 *
 * This program is a tool for fantasy basketball players to use. When you play fantasy basketball, you have to set
 * your roster to put out at the beginning of each week. The process of comparing every single player on your roster
 * to other players and determining which ones you should put out is a very time-consuming process that sometimes
 * ruins the experience of fantasy basketball. People become too lazy to update their roster and end up not putting
 * out their best lineup which means they end up losing every week. My program is a tool that can be used by fantasy
 * players to see what the most ideal roster they can put out is and see suggestions for their roster. There are also
 * additional features such as viewing the scores of yesterday’s games. You can also view more information of each
 * game such as the box score stats (points, rebounds, and assists of each player in the game).
 */

package Main;

//Import Statements
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

//Importing Classes from Controller and Model packages
import Retriever.*;
import Objects.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Using these variables for date since the NBA season is over (There are no games being played right now)
        //When the season is going on, LocalDate class can be used to get current date
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        //final Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, -1);

        //String yesterday = dateFormat.format(cal.getTime());
        //yesterday = yesterday.substring(0, 11).replace("/", "");

        String yesterday = "20220322";

        //Categories that are worth points in Fantasy Basketball
        String[] leagueInfoCategories = {"Field Goals Made(FGM)", "Field Goals Attempted(FGA)", "Free Throws Made(FTM)",
                "Free Throws Attempted(FTA)", "Three Pointers Made(3PM)", "Rebounds(REB)", "Assists(AST)", "Steals(STL)",
                "Blocks(BLK)", "Turnovers(TO)", "Points(PTS)"};

        //Initializing arrays
        LinkedList roster = new LinkedList();
        float[] leagueInfo;

        //Introduction
        System.out.println("Welcome to the Fantasy Roster Tool!\n");
        System.out.println("If you have already used this program and set up your roster and league info, enter 'yes' when prompted.");

        //Asking user if they have already set up the information of their league and roster
        System.out.print("Have you already set up your team and league? (y/n) ");
        String a = input.nextLine();

        //Making sure input is correct
        boolean flag = false;
        while (!flag) {
            if (a.equalsIgnoreCase("yes") || a.equalsIgnoreCase("y") ||
                    a.equalsIgnoreCase("no") || a.equalsIgnoreCase("n")) {
                //If the input is correct
                flag = true; //End loop
            } else {
                //Else ask the user to input answer again
                System.out.println("\nInvalid Input! (enter 'yes' or 'no')");
                System.out.print("Have you already set up you team and league? ");
                a = input.nextLine();
            }
        }
        if (!a.equalsIgnoreCase("y") && !a.equalsIgnoreCase("yes")) {
            //If they say no, use getLeagueInfo and getRoster methods to ask user to fill in the information
            System.out.println();
            leagueInfo = getLeagueInfo(leagueInfoCategories);
            System.out.println("\n");
            roster = getRoster();
        } else {
            //If they say yes, then read the already existing information from files

            //Read league info from file
            File leagueInfoFile = new File("/Users/aviwalia/IdeaProjects/FantasyBasketballProject/src/Main/LeagueInfo");
            leagueInfo = fileToFloatArray(leagueInfoFile);

            //Read roster from file
            File rosterFile = new File("/Users/aviwalia/IdeaProjects/FantasyBasketballProject/src/Main/Roster");
            String[][] rosterInfoArray = fileTo2DArray(rosterFile);
            for (String[] strings : rosterInfoArray) {
                roster.addToFront(new Player(strings[0], strings[1], strings[2],
                        strings[3], strings[4]));
            }
        }

        //Menu for user to see what they can do in the program
        String menu = """

                What would you like to do?
                1. View Roster
                2. Update Roster
                3. See Suggested Roster
                4. Get Team Suggestions
                5. See Box Scores for Yesterday's Games
                6. View League Info
                7. Edit League Info
                8. Exit
                Enter Choice:\s""";

        boolean running = true;

        while (running) {
            //Asking user what they would like to do
            System.out.print(menu);
            String choice = input.nextLine();
            System.out.println();

            switch (choice) {
                case "1" ->  //Choice 1 - View Roster~~
                        System.out.println(roster);
                case "2" ->  //Choice 2 - Update Roster
                    //Call replacePlayer method
                        replacePlayer(roster);
                case "3" -> {  //Choice 3 - See Suggested Roster
                    System.out.println("Loading...");

                    //Split players in roster into 3 arrays based on position
                    Player[] guards = getGuards(roster);
                    Player[] forwards = getForwards(roster);
                    Player[] centres = getCentres(roster);

                    //Empty array to be filled with players that have been added to suggested roster
                    Player[] used = new Player[7];
                    if (guards.length < 3) {
                        //If the user has less than 3 guards, their team is not eligible
                        System.out.println("You do not have enough guards");
                        System.out.println("Drop one of your forwards or centres for a guard.");
                    } else if (forwards.length < 3) {
                        //If the user has less than 3 forwards, their team is not eligible
                        System.out.println("You do not have enough forwards");
                        System.out.println("Drop one of your guards or centres for a forward.");

                    } else if (centres.length < 1) {
                        //If the user has less than 1 centre, their team is not eligible
                        System.out.println("You do not have enough centres");
                        System.out.println("Drop one of your guards or forwards for a centre.");

                    } else {
                        System.out.println("Suggested Roster");

                        //Sorting the guards by most fantasy points per game to least ppg
                        bubbleSort(guards, leagueInfo);
                        //Printing to user highest 3 scoring guards and their fantasy points per game
                        System.out.print("Guard 1: " + guards[0]);
                        System.out.printf(" - %.2f Fantasy Points Per Game\n", getFantasyPoints(guards[0], leagueInfo));
                        System.out.print("Guard 2: " + guards[1]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(guards[1], leagueInfo));
                        System.out.print("Guard 3: " + guards[2]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(guards[2], leagueInfo));
                        //Adding top 3 to used array
                        System.arraycopy(guards, 0, used, 0, 3);

                        //Sorting forwards by most points per game to least ppg
                        bubbleSort(forwards, leagueInfo);
                        //Printing to user highest 3 scoring forwards and their fantasy points per game
                        System.out.print("Forward 1: " + forwards[0]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(forwards[0], leagueInfo));
                        System.out.print("Forward 2: " + forwards[1]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(forwards[1], leagueInfo));
                        System.out.print("Forward 3: " + forwards[2]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(forwards[2], leagueInfo));
                        //Adding top 3 forwards to used array
                        System.arraycopy(forwards, 0, used, 3, 3);

                        //Sorting centres by most fantasy points per game to least ppg
                        bubbleSort(centres, leagueInfo);
                        //Printing to user the highest scoring centre and his fantasy points per game
                        System.out.print("Centre: " + centres[0]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(centres[0], leagueInfo));
                        //Adding top centre to used array
                        used[6] = centres[0];

                        //Creating an array of players who have not been used in the roster yet
                        Player[] unused = new Player[6];
                        int size = 0;
                        //Looping through roster and finding ones not in used array
                        for (int i = 0; i < 13; i++) {
                            Player p = roster.get(i);
                            boolean found = false;
                            for (Player u : used) {
                                if (p.equals(u)) {
                                    //If player is in used array then make found true
                                    found = true;
                                }
                            }
                            if (!found) {
                                //If the player is not in the used array
                                unused[size] = p; //Add it to unused array
                                size++;
                            }
                        }

                        //Sorting the unused players by most fantasy points per game to least ppg
                        bubbleSort(unused, leagueInfo);
                        //Printing to user highest 3 scoring unused players and their fantasy points per game
                        //They will fill the utility spots on the fantasy roster
                        System.out.print("Utility 1: " + unused[0]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(unused[0], leagueInfo));
                        System.out.print("Utility 2: " + unused[1]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(unused[1], leagueInfo));
                        System.out.print("Utility 3: " + unused[2]);
                        System.out.printf(" - %.2f Fantasy PPG\n\n", getFantasyPoints(unused[2], leagueInfo));

                        //Printing to user the remaining 3 players that should be placed on the bench
                        System.out.println("Bench");
                        System.out.print(unused[3]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(unused[3], leagueInfo));
                        System.out.print(unused[4]);
                        System.out.printf(" - %.2f Fantasy PPG\n", getFantasyPoints(unused[4], leagueInfo));
                        System.out.print(unused[5]);
                        System.out.printf(" - %.2f Fantasy PPG\n\n", getFantasyPoints(unused[5], leagueInfo));
                    }
                }
                case "4" -> {  //Choice 4 - Get Team Suggestions
                    System.out.println("This may take a few seconds");
                    System.out.println("Loading...\n");
                    Player[] tempRoster = new Player[roster.size()];
                    for (int i = 0; i < roster.size(); i++) {
                        tempRoster[i] = roster.get(i);
                    }

                    //Sorting the roster by most fantasy points per game to least ppg
                    bubbleSort(tempRoster, leagueInfo);
                    //Printing to user the best 3 players on the roster
                    System.out.println("Your Best Players Are:");
                    for (int i = 0; i < 3; i++) {
                        System.out.println(tempRoster[i]);
                    }
                    System.out.println();

                    //Printing to the user the worst 3 players on the roster
                    System.out.println("Your Worst Players Are:");
                    for (int i = 12; i > 9; i--) {
                        System.out.println(tempRoster[i]);
                    }
                    System.out.println("You should consider dropping these players and looking for alternates.");
                }
                case "5" ->  //Choice 5 - See Box Scores for Yesterday's Games
                    //Calls the getBoxScoresForDate method
                        getBoxScoresForDate(yesterday);
                case "6" ->  //Choice 6 - View League Info

                        printArray(leagueInfo, 0, leagueInfoCategories);
                case "7" ->  //Choice 7 - Edit League Info
                    //Will call the getLeagueInfo method to replace the information currently inside LeagueInfo
                        leagueInfo = getLeagueInfo(leagueInfoCategories);
                case "8" -> {  //Choice 8 - Exit
                    System.out.println("Goodbye!");
                    //Stop loop
                    running = false;
                }
                default -> System.out.println("Invalid input! Please enter a number 1-8");
            }
        }

        //Writing Info to Files (Roster and LeagueInfo)
        try {
            //Refreshing LeagueInfo file after user is done with program
            FileWriter fwLeagueInfo = new FileWriter("/Users/aviwalia/IdeaProjects/FantasyBasketballProject/src/Main/LeagueInfo", false);
            PrintWriter pwLeagueInfo = new PrintWriter(fwLeagueInfo, false);

            //Erasing the current contents of LeagueInfo file
            pwLeagueInfo.flush();

            //Writing info of updated LeagueInfo
            for (float f : leagueInfo) {
                fwLeagueInfo.write(f + "\n");
            }
            //closing FileWriter and PrintWriter
            fwLeagueInfo.close();
            pwLeagueInfo.close();

            //Refreshing RosterFile after user is done with program
            FileWriter fwRoster = new FileWriter("/Users/aviwalia/IdeaProjects/FantasyBasketballProject/src/Main/Roster", false);
            PrintWriter pwRoster = new PrintWriter(fwLeagueInfo, false);

            //Erasing the current contents of Roster file
            pwRoster.flush();

            //Writing info of updated Roster
            for (int i = 0; i < roster.size(); i++) {
                Player p = roster.get(i);
                fwRoster.write(p.getFirstName() + "," + p.getLastName() + "," + p.getPersonId() + "," +
                        p.getTeamId() + "," + p.getPos() + "\n");
            }

            //closing FileWriter and PrintWriter
            fwRoster.close();
            pwRoster.close();

        } catch (IOException e) {
            //If an error occurs while writing to file, this message will be displayed
            System.out.println("An error occurred while updating files.");
        }
    }

    /**
     * This method displays the value of each point category using recursion to the user.
     * Does not return anything.
     *
     * @param leagueInfo - the array of floats to be printed to the user
     * @param index1     - starting index of array
     * @param categories - the array of Strings to be printed to user
     */
    private static void printArray(float[] leagueInfo, int index1, String[] categories) {
        if (index1 >= leagueInfo.length) { //Base case:
            System.out.print(""); //If you reach end of array, print nothing
        } else { //General Case
            System.out.println(categories[index1] + ": " + leagueInfo[index1]); //Print element in array
            printArray(leagueInfo, index1 + 1, categories); //continue to next element
        }
    }

    /**
     * This method sorts an array of Player objects by the highest fantasy points per game to the lowest ppg.
     * Nothing is returned.
     *
     * @param arr        - an array of Player objects to be sorted
     * @param leagueInfo - a float array of the worth of each category
     */
    public static void bubbleSort(Player[] arr, float[] leagueInfo) {
        int correct = 0;
        //Loop until all numbers are in the correct position
        while (correct != arr.length) {
            for (int i = (arr.length - 1); i > correct; i--) {
                //Start at the last number and end when the last unsorted number has been reached
                Player first = arr[i];
                Player second = arr[i - 1];
                //If the Player before the current one has a better ppg
                if (getFantasyPoints(first, leagueInfo) > getFantasyPoints(second, leagueInfo)) {
                    //Switch their positions
                    arr[i] = second;
                    arr[i - 1] = first;
                }
            }
            correct += 1;
        }
    }

    /**
     * This method returns fantasy points per game of an inputted player.
     * This number is based off their season average.
     *
     * @param p          - a Player object
     * @param leagueInfo - a float array of the worth of each category
     * @return a float which represents the fantasy points per game of the inputted player
     */
    public static float getFantasyPoints(Player p, float[] leagueInfo) {
        float fPoints = 0f; //total fantasy points

        //Get season stats for player
        PlayerStats stats = RetrievePlayerStats.getPlayerStatsFromID(p.getPersonId());
        float gamesPlayed = Float.parseFloat(stats.getGamesPlayed());

        //Calculate how many points the player averages per game for each category and add it to the total
        fPoints += (Float.parseFloat(stats.getFgm()) / gamesPlayed) * leagueInfo[0]; //field goals made
        fPoints += (Float.parseFloat(stats.getFga()) / gamesPlayed) * leagueInfo[1]; //field goals attempted
        fPoints += (Float.parseFloat(stats.getFtm()) / gamesPlayed) * leagueInfo[2]; //free throws made
        fPoints += (Float.parseFloat(stats.getFta()) / gamesPlayed) * leagueInfo[3]; //free throws attempted
        fPoints += (Float.parseFloat(stats.getTpm()) / gamesPlayed) * leagueInfo[4]; //three pointers made
        fPoints += Float.parseFloat(stats.getRpg()) * leagueInfo[5]; //rebounds
        fPoints += Float.parseFloat(stats.getApg()) * leagueInfo[6]; //assists
        fPoints += Float.parseFloat(stats.getSpg()) * leagueInfo[7]; //steals
        fPoints += Float.parseFloat(stats.getBpg()) * leagueInfo[8]; //blocks
        fPoints += Float.parseFloat(stats.getTopg()) * leagueInfo[9]; //turnovers
        fPoints += Float.parseFloat(stats.getPpg()) * leagueInfo[10]; //points

        return fPoints; //return total
    }

    /**
     * This method takes in an array of Players objects and returns an array of the centre in the array
     *
     * @param roster - an array of Player objects
     * @return an array of Player objects that play the centre position
     */
    public static Player[] getCentres(LinkedList roster) {
        Player[] returnArray = new Player[0];
        for (int i = 0; i < roster.size(); i++) { //for each player in roster
            Player p = roster.get(i);
            //If they play the centre position
            if (p.getPos().equalsIgnoreCase("C") || p.getPos().equalsIgnoreCase("C-F")) {
                //Increase the array size by one and add them to the returnArray
                Player[] temp = new Player[returnArray.length + 1];
                temp[returnArray.length] = p;
                System.arraycopy(returnArray, 0, temp, 0, returnArray.length);
                //Assign the temp array with the player added to the returnArray
                returnArray = temp;
            }
        }
        return returnArray;
    }

    /**
     * This method takes in an array of Player objects and returns an array of the forwards in the array
     *
     * @param roster - an array of Player objects
     * @return an array of Player objects that play the forward position
     */
    public static Player[] getForwards(LinkedList roster) {
        Player[] returnArray = new Player[0];
        for (int i = 0; i < roster.size(); i++) { //for each player in roster
            Player p = roster.get(i);
            //If they play the forward position
            if (p.getPos().equalsIgnoreCase("F") || p.getPos().equalsIgnoreCase("F-G") ||
                    p.getPos().equalsIgnoreCase("F-C")) {
                //Increase the array size by one and add them to the returnArray
                Player[] temp = new Player[returnArray.length + 1];
                temp[returnArray.length] = p;
                System.arraycopy(returnArray, 0, temp, 0, returnArray.length);
                //Assign the temp array with the player added to the returnArray
                returnArray = temp;
            }
        }
        return returnArray;
    }

    /**
     * This method takes in an array of Player objects and returns an array of the guards in the array
     *
     * @param roster - an array of Player objects
     * @return an array of Player objects that play the guards position
     */
    public static Player[] getGuards(LinkedList roster) {
        Player[] returnArray = new Player[0];
        for (int i = 0; i < roster.size(); i++) { //for each player in roster
            Player p = roster.get(i);
            //If they play the guard position
            if (p.getPos().equalsIgnoreCase("G") || p.getPos().equalsIgnoreCase("G-F")) {
                //Increase the array size by one and add them to the returnArray
                Player[] temp = new Player[returnArray.length + 1];
                temp[returnArray.length] = p;
                System.arraycopy(returnArray, 0, temp, 0, returnArray.length);
                //Assign the temp array with the player added to the returnArray
                returnArray = temp;
            }
        }
        return returnArray;
    }

    /**
     * This method displays to the user the score of all the games played on an inputted date.
     * It then asks the user if they would like the box score of any of the games and will
     * display the points, rebounds, and assists of each player in the game if the user says yes.
     * Nothing is returned
     *
     * @param date - a String that represents yesterday's date
     */
    public static void getBoxScoresForDate(String date) {
        Scanner input = new Scanner(System.in);

        // Get all games played on given date
        Scoreboard[] sbList = RetrieveScoreboard.getScoreboardsOnDay(date);

        //For each game in scoreboard list
        for (int i = 0; i < sbList.length; i++) {
            Scoreboard sb = sbList[i];

            //Get list of box score for each player
            Boxscore[] bsList = RetrieveBoxscore.getBoxscore(date, sb.getGameId());

            //Print the final score of the game
            System.out.println("Game " + (i + 1));
            //Print home team name and points
            System.out.println(RetrieveTeam.getTeamById(bsList[17].getTeamId()).getFullName() + ": " + sb.getLocalTeam().getScore());
            //Print away team name and points
            System.out.println(RetrieveTeam.getTeamById(bsList[0].getTeamId()).getFullName() + ": " + sb.getVisitorTeam().getScore());

            System.out.println();
        }

        System.out.print("Would you like to see the box score of any of these games? (y/n) ");
        String yN = input.nextLine();
        //Making sure input is correct
        boolean flag = false;
        while (!flag) {
            if (yN.equalsIgnoreCase("yes") || yN.equalsIgnoreCase("y") ||
                    yN.equalsIgnoreCase("no") || yN.equalsIgnoreCase("n")) {
                //If the input is correct
                flag = true; //End loop
            } else {
                //Else ask the user to input answer again
                System.out.println("\nInvalid Input! (enter 'yes' or 'no')");
                System.out.print("Would you like to see the box score of any of these games? ");
                yN = input.nextLine();
            }
        }

        while (yN.equalsIgnoreCase("y") || yN.equalsIgnoreCase("yes")) {
            //Loop until user says they do not want to see any more boxscores
            //Ask user which game they want more info for
            System.out.print("Which game would you like to see? (Enter Number): ");
            int num = 0;

            //Making sure that the user enters an int
            boolean flag2 = false;
            while (!flag2) {
                try {
                    num = input.nextInt() - 1;
                    flag2 = true;
                } catch (Exception e) {
                    System.out.print("Invalid input. Enter an integer please: ");
                    input.next();
                }
            }
            input.nextLine();

            System.out.println();

            Boxscore[] bsList = new Boxscore[0];

            //Checking to make sure input is correct
            boolean flag3 = false;
            while (!flag3) {
                try {
                    bsList = RetrieveBoxscore.getBoxscore(date, sbList[num].getGameId());
                    flag3 = true;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid input! Enter a number that corresponds to the game you wish to see.\n");
                    System.out.print("Which game would you like to see? (Enter Number): ");
                    num = input.nextInt() - 1;
                    input.nextLine();
                }
            }

            String id = bsList[0].getTeamId(); //get team id of first player inn bsList
            //Print team name
            System.out.println("\n" + RetrieveTeam.getTeamById(id).getFullName());
            System.out.println("                         ---------------------------");

            for (Boxscore b : bsList) { //For each Boxscore object in bsList
                if (!b.getTeamId().equals(id)) { //If the team of the player is different from the last
                    System.out.println("\n");
                    id = b.getTeamId();
                    //Print new team name
                    System.out.println(RetrieveTeam.getTeamById(id).getFullName());
                    System.out.println("                         ---------------------------");

                }
                //Show information for every Player
                //Show player name
                System.out.print(b.getFullName());
                for (int j = b.getFullName().length(); j < 25; j++) {
                    System.out.print(" ");
                }
                //Show points
                System.out.print("PTS: " + b.getPoints());
                if (Integer.parseInt(b.getPoints()) < 10) {
                    System.out.print("  | ");
                } else {
                    System.out.print(" | ");
                }
                //Show assists
                System.out.print("AST: " + b.getAssists());
                if (Integer.parseInt(b.getAssists()) < 10) {
                    System.out.print("  | ");
                } else {
                    System.out.print(" | ");
                }
                //Show rebounds
                System.out.println("REB: " + b.getTotReb());
                System.out.println("                         ---------------------------");
            }

            System.out.print("\nWould you like to see the boxscore of another game? (y/n) ");
            yN = input.nextLine();
            //Making sure input is correct
            boolean flag4 = false;
            while (!flag4) {
                if (yN.equalsIgnoreCase("yes") || yN.equalsIgnoreCase("y") ||
                        yN.equalsIgnoreCase("no") || yN.equalsIgnoreCase("n")) {
                    //If the input is correct
                    flag4 = true; //End loop
                } else {
                    //Else ask the user to input answer again
                    System.out.println("\nInvalid Input! (enter 'yes' or 'no')");
                    System.out.print("Would you like to see the boxscore of another game? ");
                    yN = input.nextLine();
                }
            }
        }
    }

    /**
     * This method asks the user to input league information (the worth of each category - points, rebounds, assists,
     * etc.) and returns it.
     *
     * @param categories - a String array of the categories for the league information
     * @return a float array of the information that the user inputted
     */
    public static float[] getLeagueInfo(String[] categories) {
        Scanner input = new Scanner(System.in);
        float[] info = new float[categories.length];

        System.out.println("Enter the amount of fantasy points each category will be worth.");
        System.out.println("This value should already be set up in your league (check league settings in the app)");
        System.out.println("For example, in a standard league setup, points are worth 1 fantasy point, " +
                "assists are worth 2 fantasy points, etc.");

        System.out.println("\nEnter League Info:");
        System.out.println("Type in negative numbers for negative points\n");

        for (int i = 0; i < categories.length; i++) { //for each category
            //Ask user how many points each category is worth
            System.out.printf("How many points are %s? ", categories[i]);
            //Add it to Info

            float num = 0;
            boolean flag = false;
            while (!flag) {
                try {
                    num = input.nextFloat();
                    flag = true;
                } catch (Exception e) {
                    System.out.print("Invalid input. Enter a number please: ");
                    input.next();
                }
            }
            info[i] = num;
        }

        return info;
    }

    /**
     * This method asks the user to input their roster. It asks them to input the first name, last name, and the
     * team that they play on. It then searches for the player using this information and adds them to an
     * array of Player objects that will be returned
     * No parameters
     *
     * @return a float array of the information that the user inputted
     */
    public static LinkedList getRoster() {
        Scanner input = new Scanner(System.in);

        int numPlayers = 13; //this is the standard size for a fantasy team

        LinkedList roster = new LinkedList();

        //Instructions for user
        System.out.println("For each player, enter first name, last name, and team");
        System.out.println("For example, if you want to add Lebron James");
        System.out.println("Enter information in this format:");
        System.out.println("Lebron");
        System.out.println("James");
        System.out.println("Lakers");

        System.out.println("\nEnter your roster:");

        int index = 0;

        //Filling up array with players
        while (index < numPlayers) {
            System.out.printf("Enter Player %d\n", (index + 1));
            //Ask first name
            System.out.print("First Name: ");
            String firstName = input.nextLine();
            //Ask last name
            System.out.print("Last Name: ");
            String lastName = input.nextLine();
            //Ask team
            System.out.print("Enter Team - ");
            System.out.print("Enter team name only, not city (Ex.'Raptors', 'Trail_Blazers'): ");
            String team = input.nextLine().toLowerCase();

            boolean found = false;
            while (!found) { //Loop until correct information has been entered
                //Searching for player using parameters given by user and adding them to roster
                Player p = RetrievePlayer.getPlayerInfoByName(firstName, lastName, team);
                //Making sure inputted information is correct
                if (!p.getFirstName().equals("")) {
                    //If info is correct, then add player to roster and increase index by 1
                    roster.addToFront(p);
                    index++;
                    System.out.println();
                    found = true; //Break loop
                } else {
                    //If info is incorrect, then ask user again
                    System.out.println("\nPlayer not found. Make sure input all the information correctly!\n");
                    System.out.printf("Enter Player %d\n", (index + 1));
                    //Ask first name
                    System.out.print("First Name: ");
                    firstName = input.nextLine();
                    //Ask last name
                    System.out.print("Last Name: ");
                    lastName = input.nextLine();
                    //Ask team
                    System.out.print("Enter Team - ");
                    System.out.print("Enter team name only, not city (Ex.'Raptors', 'Trail_Blazers'): ");
                    team = input.nextLine().toLowerCase();
                }
            }
        }
        return roster;
    }

    /**
     * This method allows the user to replace any player on their roster.
     * Nothing is returned
     *
     * @param roster - an array of Player objects
     */
    private static void replacePlayer(LinkedList roster) {
        Scanner input = new Scanner(System.in);

        //Asking user for information of player to be replaced
        System.out.println("Which player would you like to replace?");
        //Asking first name
        System.out.print("Enter first name: ");
        String fName = input.nextLine();
        //Asking last name
        System.out.print("Enter last name: ");
        String lName = input.nextLine();

        Player foundPlayer = null;
        boolean found = false;

        while (!found) {
            //Loop through until player to be replaced has been found
            for (int i = 0; i < 13; i++) {

                Player p = roster.get(i);

                //If a player that matches the user's input is found
                if (p.getFirstName().equalsIgnoreCase(fName) && p.getLastName().equalsIgnoreCase(lName)) {
                    foundPlayer = p;
                    found = true; //end loop
                    roster.remove(p.getFirstName(), p.getLastName());
                    i = 13; //break for loop
                }
            }
            //If the inputted information does not match any player on the roster
            if (foundPlayer == null) {
                System.out.println("\nThat player is not on your roster! Try Again\n");

                //Ask user to re-enter information
                System.out.println("Which player would you like to replace?");
                System.out.print("Enter first name: ");
                fName = input.nextLine();
                System.out.print("Enter last name: ");
                lName = input.nextLine();
            }
        }

        //Asking information of new player
        System.out.println("Who would you like to add?");
        //Asking first name
        System.out.print("First Name: ");
        String firstName = input.nextLine();
        //Asking last name
        System.out.print("Last Name: ");
        String lastName = input.nextLine();
        //Asking team
        System.out.print("Enter Team - ");
        System.out.print("Enter team name only, not city (Ex.'Raptors', 'Trail_Blazers'): ");
        String team = input.nextLine().toLowerCase();

        boolean flag = false;
        while (!flag) { //Loop until correct information has been entered
            //Searching for player using parameters given by user and adding them to roster
            Player p2 = RetrievePlayer.getPlayerInfoByName(firstName, lastName, team);
            //Making sure inputted information is correct
            if (!p2.getFirstName().equals("")) {
                //If i®nfo is correct, then add player to roster
                roster.addToFront(p2);
                System.out.println();
                flag = true; //Break while loop
            } else {
                //If info is incorrect, then ask user again
                System.out.println("\nPlayer not found. Make sure input all the information correctly!\n");
                //Ask first name
                System.out.print("First Name: ");
                firstName = input.nextLine();
                //Ask last name
                System.out.print("Last Name: ");
                lastName = input.nextLine();
                //Ask team
                System.out.print("Enter Team - ");
                System.out.print("Enter team name only, not city (Ex.'Raptors', 'Trail_Blazers'): ");
                team = input.nextLine().toLowerCase();
            }
        }

    }

    /**
     * This method reads from a file and returns an array of floats.
     *
     * @param file text file of floats which are separated by new line characters.
     * @return a float array
     */
    public static float[] fileToFloatArray(File file) {
        float[] returnArray = new float[0];
        try {
            //Opening file reader
            Scanner myReader = new Scanner(file);
            //Loop after each line and stop looping when there are no more lines to read in file
            while (myReader.hasNextLine()) {
                //Increase length of array by one and adding float on next line to it
                int l = returnArray.length;
                float[] temp = new float[l + 1];
                temp[l] = Float.parseFloat(myReader.nextLine());
                System.arraycopy(returnArray, 0, temp, 0, l);
                returnArray = temp;
            }
        } catch (FileNotFoundException e) { //catches FileNotFoundError
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return returnArray; //returns float array
    }

    /**
     * This method reads from a file and returns a 2D array of the information
     *
     * @param file text file of information about an object on each line. Info is separated by commas
     * @return a 2D String array. Each row represents a Player object and the columns are the constructor parameters.
     */
    public static String[][] fileTo2DArray(File file) {
        String[][] returnArray = new String[0][]; //initializing array
        try {
            //Opening file reader
            Scanner myReader = new Scanner(file);
            int index = 0;
            //Loops after each line and will stop looping when there are no more lines to read in file
            while (myReader.hasNextLine()) {
                //Increasing length of array by one
                int length = returnArray.length;
                String[][] temp = new String[length + 1][];
                //Copying data from returnArray to temp array
                System.arraycopy(returnArray, 0, temp, 0, length);
                //Copying temp array back to returnArray so there is now an extra empty cell at the end
                returnArray = temp;
                //Split the line into an array of Strings
                String[] data = myReader.nextLine().split(","); //commas separate each String
                returnArray[index] = data; //assigning data to last index of returnArray
                index += 1;
            }
        } catch (FileNotFoundException e) { //Catches FileNotFoundError
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return returnArray; //returns 2D String array
    }
}