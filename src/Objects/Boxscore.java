package Objects;

public class Boxscore extends Player { //Inherits from Player class
    //Instance Variables
    private String points;
    private String totReb;
    private String assists;

    //Constructor
    public Boxscore(){
        points = "";
        totReb = "";
        assists = "";
    }

    //Modifiers

    /**
     * This method sets points to inputted points value
     * No return value
     *
     * @param points - String of new points value to assign
     */
    public void setPoints(String points) {
        this.points = points;
    }

    /**
     * This method sets total rebounds value to inputted value
     * @param totReb - String of total rebound
     */
    public void setTotReb(String totReb) {
        this.totReb = totReb;
    }

    /**
     * This method sets assists value to inputted value
     * @param assists - String of number of assists
     */
    public void setAssists(String assists) {
        this.assists = assists;
    }


    //Accessors
    /**
     * This method returns the points scored value
     * No parameters
     *
     * @return points String
     */
    public String getPoints() {
        return points;
    }

    /**
     * This method returns the total rebounds value
     * No parameters
     *
     * @return points totReb
     */
    public String getTotReb() {
        return totReb;
    }

    /**
     * This method returns the number of assists value
     * No parameters
     *
     * @return assists String
     */
    public String getAssists() {
        return assists;
    }
}