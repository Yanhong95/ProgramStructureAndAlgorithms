package rank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Match {

    private static int globalIndex;
    int index;
    Date matchDate;
    String homeTeam;
    String awayTeam;
    int homeTeamGoals;
    int awayTeamGoals;


    public Match(String index, String date, String homeTeam, String awayTeam, String homeTeamGoals, String awayTeamGoals) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
        try{
            this.matchDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.index = Integer.parseInt(index);
        globalIndex = this.index;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        // Once this match was delayed by the the COVID-19, we assume the goals to be -1
        this.homeTeamGoals = homeTeamGoals.equals("") ?  -1 : Integer.parseInt(homeTeamGoals);
        this.awayTeamGoals = awayTeamGoals.equals("") ?  -1 : Integer.parseInt(awayTeamGoals);
    }

    public Match( String date, String homeTeam, String awayTeam, String homeTeamGoals, String awayTeamGoals) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
        try{
            this.matchDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.index = globalIndex + 1;
        globalIndex ++;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        // Once this match was delayed by the the COVID-19, we assume the goals to be -1
        this.homeTeamGoals = homeTeamGoals.equals("") ?  -1 : Integer.parseInt(homeTeamGoals);
        this.awayTeamGoals = awayTeamGoals.equals("") ?  -1 : Integer.parseInt(awayTeamGoals);
    }

    @Override
    public String toString() {
        return "Match:  " +
                "matchDate=" + matchDate +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", homeTeamGoals=" + homeTeamGoals +
                ", awayTeamGoals=" + awayTeamGoals;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMatchDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(matchDate);
        return date;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }
}
