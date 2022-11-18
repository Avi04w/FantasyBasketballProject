package Objects;

public class TeamScore extends Team {
    //Instance variables
    private String score;

    //Constructor
    public TeamScore (){
        score = "";
    }

    //Modifier
    /**
     * This method sets new value for score
     * No return value
     *
     * @param score - a String to replace score with
     */
    public void setScore(String score) {
        this.score = score;
    }

    //Accessor
    /**
     * This method returns the score value
     * No parameters
     *
     * @return score String
     */
    public String getScore() {
        return score;
    }
}