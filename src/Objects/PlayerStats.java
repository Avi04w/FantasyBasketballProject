package Objects;

public class PlayerStats extends Player { //Inherits from Player class
    //Instance variables
    private String ppg;
    private String rpg;
    private String apg;
    private String topg;
    private String spg;
    private String bpg;
    private String gamesPlayed;
    private String fgm;
    private String fga;
    private String fta;
    private String ftm;
    private String tpm;

    //Constructor
    public PlayerStats() {
        ppg = "";
        rpg = "";
        apg = "";
        topg = "";
        spg = "";
        bpg = "";
        gamesPlayed = "";
        fgm = "";
        fga = "";
        fta = "";
        ftm = "";
        tpm = "";
    }

    //Modifiers
    /**
     * This method sets new value for ppg
     * No return value
     *
     * @param ppg - a String to replace ppg with
     */
    public void setPpg(String ppg) {
        this.ppg = ppg;
    }

    /**
     * This method sets new value for rpg
     * No return value
     *
     * @param rpg - a String to replace rpg with
     */
    public void setRpg(String rpg) {
        this.rpg = rpg;
    }

    /**
     * This method sets new value for apg
     * No return value
     *
     * @param apg - a String to replace apg with
     */
    public void setApg(String apg) {
        this.apg = apg;
    }

    /**
     * This method sets new value for topg
     * No return value
     *
     * @param topg - a String to replace topg with
     */
    public void setTopg(String topg) {
        this.topg = topg;
    }

    /**
     * This method sets new value for spg
     * No return value
     *
     * @param spg - a String to replace spg with
     */
    public void setSpg(String spg) {
        this.spg = spg;
    }

    /**
     * This method sets new value for bpg
     * No return value
     *
     * @param bpg - a String to replace bpg with
     */
    public void setBpg(String bpg) {
        this.bpg = bpg;
    }

    /**
     * This method sets new value for gamesPlayed
     * No return value
     *
     * @param gamesPlayed - a String to replace gamesPayed with
     */
    public void setGamesPlayed(String gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * This method sets new value for fga
     * No return value
     *
     * @param fga - a String to replace fga with
     */
    public void setFga(String fga) {
        this.fga = fga;
    }

    /**
     * This method sets new value for fgm
     * No return value
     *
     * @param fgm - a String to replace fgm with
     */
    public void setFgm(String fgm) {
        this.fgm = fgm;
    }

    /**
     * This method sets new value for fta
     * No return value
     *
     * @param fta - a String to replace fta with
     */
    public void setFta(String fta) {
        this.fta = fta;
    }

    /**
     * This method sets new value for ftm
     * No return value
     *
     * @param ftm - a String to replace ftm with
     */
    public void setFtm(String ftm) {
        this.ftm = ftm;
    }

    /**
     * This method sets new value for tpm
     * No return value
     *
     * @param tpm - a String to replace tpm with
     */
    public void setTpm(String tpm) {
        this.tpm = tpm;
    }

    //Accessors
    /**
     * This method returns the ppg value
     * No parameters
     *
     * @return ppg String
     */
    public String getPpg() {
        return ppg;
    }

    /**
     * This method returns the rpg value
     * No parameters
     *
     * @return rpg String
     */
    public String getRpg() {
        return rpg;
    }

    /**
     * This method returns the apg value
     * No parameters
     *
     * @return apg String
     */
    public String getApg() {
        return apg;
    }

    /**
     * This method returns the topg value
     * No parameters
     *
     * @return topg String
     */
    public String getTopg() {
        return topg;
    }

    /**
     * This method returns the spg value
     * No parameters
     *
     * @return spg String
     */
    public String getSpg() {
        return spg;
    }

    /**
     * This method returns the bpg value
     * No parameters
     *
     * @return bpg String
     */
    public String getBpg() {
        return bpg;
    }

    /**
     * This method returns the gamesPlayed value
     * No parameters
     *
     * @return gamesPlayed String
     */
    public String getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * This method returns the fga value
     * No parameters
     *
     * @return fga String
     */
    public String getFga() {
        return fga;
    }

    /**
     * This method returns the fgm value
     * No parameters
     *
     * @return fgm String
     */
    public String getFgm() {
        return fgm;
    }

    /**
     * This method returns the fta value
     * No parameters
     *
     * @return fta String
     */
    public String getFta() {
        return fta;
    }

    /**
     * This method returns the ftm value
     * No parameters
     *
     * @return ftm String
     */
    public String getFtm() {
        return ftm;
    }

    /**
     * This method returns the tpm value
     * No parameters
     *
     * @return tpm String
     */
    public String getTpm() {
        return tpm;
    }
}