package Objects;

import Retriever.RetrieveTeam;

public class Player {
    //Instance variables
    private String firstName;
    private String lastName;
    private String personId;
    private String teamId;
    private String pos;

    //Constructor
    public Player(){
        firstName = "";
        lastName = "";
        personId = "";
        teamId = "";
        pos = "";
    }

    //Overloaded Constructor
    public Player(String firstName, String lastName, String personId, String teamId, String pos){
        this.firstName = firstName;
        this.lastName = lastName;
        this.personId = personId;
        this.teamId = teamId;
        this.pos = pos;
    }

    //Modifiers
    /**
     * This method sets new value for firstName
     * No return value
     *
     * @param firstName - a String to replace firstName with
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method sets new value for lastName
     * No return value
     *
     * @param lastName - a String to replace lastName with
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method sets new value for personId
     * No return value
     *
     * @param personId - a String to replace personId with
     */
    public void setPersonId(String personId) {
        this.personId = personId;
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

    /**
     * This method sets new value for pos
     * No return value
     *
     * @param pos - a String to replace pos with
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    //Accessors
    /**
     * This method returns the firstName value
     * No parameters
     *
     * @return firstName String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method returns the lastName value
     * No parameters
     *
     * @return lastName String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method returns the full name of a player by returning string with first name and last name
     * No parameters
     *
     * @return String of Player's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * This method returns the personId value
     * No parameters
     *
     * @return personId String
     */
    public String getPersonId() {
        return personId;
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

    /**
     * This method returns the pos value
     * No parameters
     *
     * @return pos String
     */
    public String getPos() {
        return pos;
    }

    //Helpers

    /**
     * Overrides already existing toString method and instead returns info formatted as a String.
     * Will return String of first name, last name, and team
     * No parameters
     *
     * @return a String of the information to be printed
     */
    public String toString(){
        return firstName + " " + lastName + " " + pos + " - " + RetrieveTeam.getTeamById(teamId).getFullName();
    }

    /**
     * This method returns if the information of 2 Player objects are equal
     * @param p a Player object
     * @return - boolean if the 2 player objects are equal or not
     */
    public boolean equals (Player p){
        return (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName) &&
                p.getTeamId().equals(teamId));
    }
}
