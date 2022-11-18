package Retriever;

import Objects.Team;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

//Class that gets Team data
public class RetrieveTeam {
    /**
     * This method returns an array of all the teams in the league
     * No parameters
     *
     * @return an array of Team objects
     */
    public static Team[] getTeams() {
        Team[] tList = new Team[0]; //Initialize array of Team objects
        JSONObject jobj; //Initialize JSON object

        //Try to read from API
        try {
            //Create URL object
            URL url = new URL("http://data.nba.net/data/10s/prod/v1/2021/teams.json");
            //Open HTTP connection with the URL
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //set request method to 'GET'
            con.setRequestMethod("GET");
            String inline = "";
            //Create connection
            con.connect();

            //Get response from server
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                //If the server does not give a response code of 200, there was an error
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) { //While there is more information, continue to loop
                    inline += sc.nextLine();
                }
                sc.close();
            }

            //Format String to JSONObject
            jobj = new JSONObject(inline);
            //Get JSONArray object from jobj with the information that is needed
            JSONArray data = jobj.getJSONObject("league").getJSONArray("standard");

            int i = 0;
            while (data.length() > i) { //While there is still data in data array
                Team tm = new Team(); //Initialize Team object

                //Fill in Team object with the data
                tm.setFullName(data.getJSONObject(i).getString("fullName"));
                tm.setTeamId(data.getJSONObject(i).getString("teamId"));

                //Add Team to list of Teams that are being returned
                int l = tList.length;
                //Increase length of tList by one
                //Create temporary array with 1 greater length
                Team[] temp = new Team[l + 1];
                //Add new Team to array
                temp[l] = tm;
                for (int j = 0; j < l; j++){
                    temp[j] = tList[j];
                }
                //copy back to tList
                tList = temp;
                i++; //Increase index by 1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tList; //Return array of Team objects
    }

    /**
     * This method returns a Team object that matches the inputted data
     *
     * @param id - a String that represents the ID of a team
     * @return a Team object that matches the ID
     */
    public static Team getTeamById(String id) {
        Team[] tList = getTeams(); //Get array of all teams using getTeams method

        Team foundTeam = null; //Initialize return value

        for (int i = 0; i < tList.length; i++) { //For each team in the list of teams
            if (tList[i].getTeamId().equals(id)) { //If the inputted id matches the team id
                foundTeam = tList[i]; //Make the return value equal to the Team
                i = tList.length; //Stop looping through for loop
            }
        }
        return foundTeam; //Return Team
    }
}