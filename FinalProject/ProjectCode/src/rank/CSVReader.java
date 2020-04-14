package rank;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    public ArrayList<Match> getData(String filePath) {

        String line = "";
        String cvsSplitBy = ",";
        ArrayList<Match> matches = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] matchList = line.split(cvsSplitBy);
                Match match = new Match(matchList[0],matchList[1],matchList[2],matchList[5],matchList[3],matchList[4]);
                // System.out.println(match.getMatchDate());
                matches.add(match);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matches;
    }
}
