package Retriever;

//Import Statements
import Objects.Player;

import org.json.JSONArray;
import org.json.JSONObject;

//This class retrieves the Player data from the API
public class RetrievePlayer {

    /**
     * This method returns a PLayer object of the player who matches the inputted information
     *
     * @param firstName - String of first name of player
     * @param lastName - String of last name of player
     * @param teamUrl - String of url of team player is on
     * @return a Player object that matches the inputted information
     */
    public static Player getPlayerInfoByName(String firstName, String lastName, String teamUrl) {

        Player player = new Player(); //Initialize Player object

        JSONObject jobj; //Initializing JSONObject

        //Try to read from API
        try {
            //Get JSON object with data
            jobj = new RetrieveInfo().execute("https://data.nba.net/v2015/json/mobile_teams/nba/2021/teams/" + teamUrl + "_roster.json");
            //Get JSONArray object from jobj with the information that is needed
            JSONObject dataTeam = jobj.getJSONObject("t");
            JSONArray dataPlayer = jobj.getJSONObject("t").getJSONArray("pl");

            int i = 0;
            while (dataPlayer.length() > i) { //While there is still data in the dataPlayer array
                if (dataPlayer.getJSONObject(i).getString("fn").equalsIgnoreCase(firstName) &&
                        dataPlayer.getJSONObject(i).getString("ln").equalsIgnoreCase(lastName)) {
                    //If player matches inputted information

                    //Fill in PLayer object with the data
                    player.setPersonId(dataPlayer.getJSONObject(i).get("pid").toString());
                    player.setFirstName(dataPlayer.getJSONObject(i).getString("fn"));
                    player.setLastName(dataPlayer.getJSONObject(i).getString("ln"));
                    player.setPos(dataPlayer.getJSONObject(i).getString("pos"));
                    player.setTeamId(dataTeam.get("tid").toString());
                }
                i++; //Increase index by 1
            }
            return player; //Return Player object
        }
        catch (Exception e) {
            return new Player();
        }
    }
}