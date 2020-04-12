package rank;

import java.util.*;

public class Calculator {

    public static HashMap<String, Double[]> teamsWithNormalDist = new HashMap<String, Double[]>();
    public static HashMap<String , Double> RankMap = new HashMap<String , Double>();
    public static String[] teamList = new String[] {  "Liverpool", "Norwich City", "West Ham", "Manchester City", "Burnley", "Southampton",
            "Bournemouth", "Sheffield Utd", "Watford", "Brighton", "Crystal Palace", "Everton", "Tottenham", "Aston Villa", "Leicester City",
            "Wolves", "Newcastle Utd", "Arsenal", "Chelsea", "Manchester Utd"};

    public static void predictNormalDist(Match match){
        String homeTeam = match.homeTeam;
        String awayTeam = match.awayTeam;
        int homeTeamGoals = match.homeTeamGoals;
        int awayTeamGoals = match.awayTeamGoals;

        String gameHA = homeTeam + " --VS-- " + awayTeam; // a vd b
        String gameAH = awayTeam + " --VS-- " + homeTeam; // b vs a
        String game;
        if ( teamsWithNormalDist.containsKey(gameAH) ){
            homeTeamGoals = match.awayTeamGoals;
            awayTeamGoals = match.homeTeamGoals;
            game = gameAH;
        } else {
            game = gameHA;
        }

        // prediction for suspended matches.
        if(homeTeamGoals == -1 && awayTeamGoals == -1){
            Random random = new Random();
            if (teamsWithNormalDist.containsKey(game)){
                Double[] NDList = teamsWithNormalDist.get(game);
                Double previousMean = NDList[0];
                Double previousVariance = NDList[1];

                int predictScoreDiff = 0;
                do{
                    // using random.nextGaussian() to generate random score based on Gaussian distribution.
                    double predictScoreDiffDouble = Math.sqrt(previousVariance) * random.nextGaussian() + previousMean;
                    predictScoreDiff = (int)Math.round(predictScoreDiffDouble);
                }while(predictScoreDiff > 6 || predictScoreDiff < -6);

                if(predictScoreDiff <= 0){
                    homeTeamGoals = 0;
                    awayTeamGoals = 0 - predictScoreDiff;
                }else{
                    homeTeamGoals = predictScoreDiff;
                    awayTeamGoals = 0;
                }
            } else{
                homeTeamGoals = 0;
                awayTeamGoals = 0;
            }
            System.out.println("Prediction for match of " + match.getMatchDate() +
                    ": HomeTeam: " + match.getHomeTeam() +
                    " Goals: " + homeTeamGoals +
                    " AwayTeam: " + match.getAwayTeam() +
                    " Goals: " + awayTeamGoals );
        };

        int mean;
        int absMean = Math.min(Math.abs(homeTeamGoals - awayTeamGoals), 6);
        if( homeTeamGoals - awayTeamGoals >= 0 ){
            mean = Math.min(homeTeamGoals - awayTeamGoals, 6);
        } else {
            mean = Math.max(-6, homeTeamGoals - awayTeamGoals);
        }

        if (!teamsWithNormalDist.containsKey(game)){
            // Whenever the gap between two teams greater than six we decreasing it to 6 manually
            // because this situation is too rare to repeat
            if( absMean <= 3 ){
                // when the goals different are 0, 1, 2, 3, I assume the variance to be 4, the standard deviation to be 2
                teamsWithNormalDist.put(game, new Double[]{Double.valueOf(mean), 4.0 });
            } else {
                // when the goals different are 4, 5, 6, I assume the variance to be 1, the standard deviation to be 1
                teamsWithNormalDist.put(game, new Double[]{Double.valueOf(mean), 1.0 });
            }
        } else {
            // If the two teams have already played
            Double[] NDList = teamsWithNormalDist.get(game);
            Double previousMean = NDList[0];
            Double previousVariance = NDList[1];
            Double newVariance;
            Double newMean = (mean + previousMean)/2;
            if( absMean <= 3 ){
                newVariance = previousVariance + 4.0;
            } else {
                newVariance = previousVariance + 1.0;
            }
            teamsWithNormalDist.replace(game, new Double[]{ newMean, newVariance });
        }
    }

    public static void calculateRank() {
        for (String team : teamList) {
            int count = 0;
            double totalMean = 0;
            for (String key : teamsWithNormalDist.keySet()) {
                if (key.contains(team) && key.indexOf(team) == 0) { // if team name in the first position of key
                    count++;
                    totalMean += teamsWithNormalDist.get(key)[0];
                } else if (key.contains(team) && key.indexOf(team) != 0) { // if team name in the second position of key
                    count++;
                    totalMean += (-teamsWithNormalDist.get(key)[0]);
                }
            }
            double averageMean = totalMean/count;
            RankMap.put(team, averageMean);
        }
        Map<String, Double> sortedRankMap = sortByValue(RankMap);
        int rank = 1;
        for (Map.Entry<String, Double> entry : sortedRankMap.entrySet()) {
            System.out.println("Rank:"+ rank + "  Team: " + entry.getKey());
            rank++;
            // System.out.println("Team = " + entry.getKey() + ", AverageMean = " + entry.getValue());
        }
    }

    // Normal distribution pdf (probability distribution function ) with expectation
    public static void calculatePDF(String homeTeam, String awayTeam, Integer expectedGoal){
        try{
            String keyHA = homeTeam + " --VS-- " + awayTeam;
            String keyAH = awayTeam + " --VS-- " + homeTeam;
            double mean;
            double variance;
            if (teamsWithNormalDist.containsKey(keyHA)){
                mean = teamsWithNormalDist.get(keyHA)[0];
                variance = teamsWithNormalDist.get(keyHA)[1];
            } else if (teamsWithNormalDist.containsKey(keyAH)){
                mean = -teamsWithNormalDist.get(keyAH)[0];
                variance = teamsWithNormalDist.get(keyAH)[1];
            } else {
                System.out.println( "oops, Unable to find previous match between these two teams, please re-enter.");
                return;
            };

            double winningPercentage = 1 - calculateCDF(0, mean, variance);
            double Prediction = calculateCDF(expectedGoal + 0.5, mean, variance) -
                    calculateCDF(expectedGoal - 0.5, mean, variance);

            System.out.println( "Team " + homeTeam + " has " + String.format("%.2f", winningPercentage * 100) +
                    "% chances of winning Team " + awayTeam + " at next match."+ "\n" +
                    "The chance of team " + homeTeam + " winning team " + awayTeam +
                    " by " + expectedGoal + " goals is "+ String.format("%.2f", Prediction * 100) +"%");

        }catch(Exception e){
            System.out.println( "oops, Unable to find previous match between these two teams, please re-enter.");
        }
    }

    // Normal distribution CDF (Cumulative distribution function ) with mean and variance
    public static Double calculateCDF(double expectedGoal, double mean, double variance){
        double variable = (expectedGoal - mean) / Math.sqrt(variance);
        if (variable < -8.0) return 0.0;
        if (variable >  8.0) return 1.0;
        double sum = 0.0, term = variable;
        for (int i = 3; sum + term != sum; i += 2) {
            sum  = sum + term;
            term = term *  Math.pow((expectedGoal - mean), 2) / (i * variance);
        }
        double CDF = 0.5 + sum * Math.exp( - Math.pow((expectedGoal - mean),2)/(2 * variance)) / Math.sqrt(2 * Math.PI);
        return CDF;
    }

    public static void updateNewMatch(Match match){
        try{
            predictNormalDist(match);
            calculateRank();
//            teamsWithNormalDist.forEach((k, v) ->
//                    System.out.println("Teams: " + k + " Mean: " + v[0] + " variance: " + v[1]));
        }catch(Exception e){
            System.out.println("oops, something went wrong,please re-enter.");
        }
    }

    // function to sort HashMap by values
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> map)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list = new LinkedList<Map.Entry<String, Double>>(map.entrySet());

        // Sort the list
        list.sort(new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return -(o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to HashMap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
