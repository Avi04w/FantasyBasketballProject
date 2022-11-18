package Retriever;

//Import Statements
import Objects.PlayerStats;
import org.json.JSONArray;
import org.json.JSONObject;

//This class retrieves the PlayerStats data from the API
public class RetrievePlayerStats {

    /**
     * This method returns a PLayerStats object which represents the latest season the API has data for
     *
     * @param playerID - a string of the id of the player whose stats are being retrieved
     * @return a PlayerStats object
     */
    public static PlayerStats getPlayerStatsFromID(String playerID) {
        PlayerStats stats = new PlayerStats(); //Initialize PlayerStats object that is going to be returned
        JSONObject jobj; //Initialize JSONObject

        //Try to read from API
        try {
            //Get JSON object with data
            jobj = new RetrieveInfo().execute("https://data.nba.net/data/10s/prod/v1/2021/players/" +
                    playerID + "_profile.json");
            //Get JSONArray object from jobj with the information that is needed
            JSONArray data = jobj.getJSONObject("league").getJSONObject("standard").getJSONObject
                    ("stats").getJSONObject("regularSeason").getJSONArray("season");

            //Fill in PLayerStats object with the data
            JSONObject jobjSeason = data.getJSONObject(0).getJSONArray("teams").getJSONObject(0);
            stats.setGamesPlayed(jobjSeason.getString("gamesPlayed"));
            stats.setTeamId(jobjSeason.getString("teamId"));
            stats.setPpg(jobjSeason.getString("ppg"));
            stats.setApg(jobjSeason.getString("apg"));
            stats.setBpg(jobjSeason.getString("bpg"));
            stats.setRpg(jobjSeason.getString("rpg"));
            stats.setSpg(jobjSeason.getString("spg"));
            stats.setTopg(jobjSeason.getString("topg"));
            stats.setFga(jobjSeason.getString("fga"));
            stats.setFgm(jobjSeason.getString("fgm"));
            stats.setFta(jobjSeason.getString("fta"));
            stats.setFtm(jobjSeason.getString("ftm"));
            stats.setTpm(jobjSeason.getString("tpm"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return stats; //return PlayerStats object
    }
}