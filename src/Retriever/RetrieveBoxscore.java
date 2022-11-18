package Retriever;

import Objects.Boxscore;

import org.json.JSONArray;
import org.json.JSONObject;

//Class that gets Box score data
public class RetrieveBoxscore {

    /**
     * This method gets an array of Boxscore objects from a given date and game id
     *
     * @param date - String of date that game was played (YYYYMMDD)
     * @param gameId - String of id of the game the user wants the information of
     * @return an array of Boxscore objects
     */
    public static Boxscore[] getBoxscore(String date, String gameId) {

        //Initialize array of Boxscore objects
        Boxscore[] bsList = new Boxscore[0];
        //Initializing JSON object
        JSONObject jobj;

        //Try to read from API
        try {
            //Get JSON object with data
            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/" + date + "/" + gameId + "_boxscore.json");
            //Get JSONArray object from jobj with the information that is needed
            JSONArray data = jobj.getJSONObject("stats").getJSONArray("activePlayers");

            int i = 0;
            while (data.length() > i) { //While there is still data in data array
                Boxscore sb = new Boxscore(); //Initialize Boxscore object
                JSONObject jobjScore = data.getJSONObject(i);

                //Fill in Boxscore object with the data
                sb.setPersonId(jobjScore.getString("personId"));
                sb.setFirstName(jobjScore.getString("firstName"));
                sb.setLastName(jobjScore.getString("lastName"));
                sb.setTeamId(jobjScore.getString("teamId"));
                sb.setAssists(jobjScore.getString("assists"));
                sb.setPoints(jobjScore.getString("points"));
                sb.setPos(jobjScore.getString("pos"));
                sb.setTotReb(jobjScore.getString("totReb"));

                //Add Boxscore to list of Boxscores that are being returned
                int l = bsList.length;
                //Increase length of bsList by one
                //Create temporary array with 1 greater length
                Boxscore[] temp = new Boxscore[l + 1];
                //Add new Boxscore to array
                temp[l] = sb;
                for (int j = 0; j < l; j++){
                    temp[j] = bsList[j];
                }
                //copy back to bsList
                bsList = temp;
                i++; //Increase index by 1
            }
        }
        catch (Exception e) {
            return new Boxscore[0];
        }
        return bsList; //Return list of Boxscores
    }
}