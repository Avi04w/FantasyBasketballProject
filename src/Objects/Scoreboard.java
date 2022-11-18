package Objects;

public class Scoreboard {

    private String gameId;
    private TeamScore visitorTeam;
    private TeamScore localTeam;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public TeamScore getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(TeamScore visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public TeamScore getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(TeamScore localTeam) {
        this.localTeam = localTeam;
    }
}