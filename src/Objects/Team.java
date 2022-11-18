package Objects;

public class Team {
    //Instance Variables
    private String teamId;
    private String fullName;

    //Constructor
    public Team() {
        teamId = "";
        fullName = "";
    }

    //Modifier
    /**
     * This method sets new value for fullName
     * No return value
     *
     * @param fullName - a String to replace fullName with
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * This method sets new value for teamId
     * No return value
     *
     * @param teamId - a String to replace teamId with
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    //Accessor
    /**
     * This method returns the fullName value
     * No parameters
     *
     * @return fullName String
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method returns the teamId value
     * No parameters
     *
     * @return teamId String
     */
    public String getTeamId() {
        return teamId;
    }
}