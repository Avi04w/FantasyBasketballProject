package Retriever;

import Objects.Scoreboard;
import Objects.TeamScore;
import org.json.JSONArray;
import org.json.JSONObject;

//Class that gets Scoreboard data
public class RetrieveScoreboard {
    /**
     * This method returns an array of Scoreboards objects for a given date
     *
     * @param date - a String of the date for which you want information
     * @return - an array of Scoreboard objects
     */
    public static Scoreboard[] getScoreboardsOnDay(String date) {

        Scoreboard[] sList = new Scoreboard[0]; //Initialize array of Scoreboard objects
        JSONObject jobj; //Initializing JSON object

        //Try to read from API
        try {
            //Get JSON object with data
            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/" + date + "/scoreboard.json");
            //Get JSONArray object from jobj with the information that is needed
            JSONArray data = jobj.getJSONArray("games");

            int i = 0;
            while (data.length() > i) { //While there is still data in data array
                Scoreboard sb = new Scoreboard(); //Initialize Scoreboard object
                JSONObject jobjScore = data.getJSONObject(i);

                //Fill in Scoreboard object with information
                sb.setGameId(jobjScore.getString("gameId")); //set gameId

                TeamScore localT = new TeamScore(); //get information of local team

                localT.setTeamId(jobjScore.getJSONObject("hTeam").getString("teamId"));
                localT.setScore(jobjScore.getJSONObject("hTeam").getString("score"));

                TeamScore visitorT = new TeamScore(); //get information of away team

                visitorT.setTeamId(jobjScore.getJSONObject("vTeam").getString("teamId"));
                visitorT.setScore(jobjScore.getJSONObject("vTeam").getString("score"));

                sb.setLocalTeam(localT); //set local team
                sb.setVisitorTeam(visitorT); //set away team

                //Add Scoreboard to list of Scoreboards that are being returned
                int l = sList.length;
                //Increase length of sList by one
                //Create temporary array with 1 greater length
                Scoreboard[] temp = new Scoreboard[l + 1];
                //Add new Scoreboard to array
                temp[l] = sb;
                for (int j = 0; j < l; j++){
                    temp[j] = sList[j];
                }
                //copy back to sList
                sList = temp;
                i++; //Increase index by 1
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return sList; //return array of Scoreboard objects
    }
}