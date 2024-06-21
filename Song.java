import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;// Used to read from the user or files
import java.lang.Integer; // used for the ParseInt method


public class Song {
    private String SongName;//Holds a string containing the name of the song.
    private String Path;//Holds the path to the song,to be given in order to open the file in other methods.
    private int DurationInSec;//Holds the duration of the song in seconds.
    private boolean IsExplicit;//If the song has the Explicit label.

    public Song()
    {
        this.SongName = "";
        this.Path = "bloop";
        this.DurationInSec = 0;
        this.IsExplicit = false;
    }

    public void SetSongName(String Name) { SongName = Name;}
    public String GetSongName() {return SongName;}
    public void SetSongPath(String SongPath) { Path = SongPath;}
    public String GetSongPath() {return Path;}
    public int GetDurationInSec() {return DurationInSec;}
    public void SetDurationInSec(int Duration){ if(Duration >= 0) {DurationInSec = Duration;}}
    public boolean GetIsExplicit() {return IsExplicit;}
    public void SetIsExplicit(boolean IfEx) {IsExplicit = IfEx;}
    public static int GetSongMinutes(Song song) {return song.DurationInSec  / 60; }
    public static int GetSongSeconds(Song song) {return song.DurationInSec % 60; }

    public static Song GetSongInformation(File file) throws FileNotFoundException//This method constructs a song object from its file.
    {//Modifies all except path.
        Song song = new Song();

        Scanner SongReader = new Scanner(file);
        String temp = SongReader.nextLine();
        song.SetSongName(temp);//read the song name

        temp = SongReader.nextLine();
        String minutes = temp.substring(0,2);
        String seconds = temp.substring(3,5);
        int Min = (Integer.parseInt(minutes))*60;
        int Sec = Integer.parseInt(seconds);
        int Duration = Min + Sec;
        song.SetDurationInSec(Duration);//calculate the duration of the song
        String IsEx = temp.substring(5);
        if(IsEx.equals("E"))
            song.SetIsExplicit(true);
        SongReader.close();
        return song;
    }

    public int WordCounterInSong(String word) throws FileNotFoundException// This method counts the number of times a word appears in a given song
    {
        int WordCounter = 0;
        String temp;

        File file = new File(this.GetSongPath());
            Scanner myReader = new Scanner(file);
            temp = myReader.nextLine();
            this.SetSongName(temp);
            temp = myReader.nextLine();

            String minutes = temp.substring(0,2);
            String seconds = temp.substring(3,5);
            int Min = (Integer.parseInt(minutes))*60;
            int Sec = Integer.parseInt(seconds);
            int Duration = Min + Sec;
            this.SetDurationInSec(Duration);

            while (myReader.hasNextLine())
            {
                temp = myReader.next();
                if(!temp.matches("[a-zA-Z0-9]+"))
                    temp = temp.replaceAll("[^a-zA-Z0-9]","");

                if(temp.compareToIgnoreCase(word) == 0)
                    WordCounter++;
            }
        myReader.close();
        return WordCounter;
    }

    public int CountObscenitiesInSong() throws IOException//This method counts and returns the number of obscenities in a given song.
    {
        File SongFile = new File(this.GetSongPath());
        int ObscenitiesCounter  = 0;

        @SuppressWarnings("UnusedAssignment")
        Scanner SongReader = new Scanner(SongFile);
        String temp = SongReader.nextLine();//reads the song's name
        temp = SongReader.nextLine();//reads the song's duration
        while(SongReader.hasNext())
        {
            temp = SongReader.next();
            if(temp.equalsIgnoreCase("bitch")||temp.equalsIgnoreCase("damn")||temp.equalsIgnoreCase("shit")||temp.equalsIgnoreCase("fuck")||temp.equalsIgnoreCase("damned"))
                ObscenitiesCounter++;
        }
        SongReader.close();
        return ObscenitiesCounter;
    }

    public int NumOfWordsInASong() throws IOException//counts the number of words in a song
    {
        File SongFile = new File(this.GetSongPath());
        int WordCounter = 0;

            Scanner SongReader = new Scanner(SongFile);
            String temp = SongReader.nextLine();//reads the song's name
            temp = SongReader.nextLine();//reads the song's duration

            while(SongReader.hasNext())
            {
                temp = SongReader.next();
                if(!(temp.contains("[") || (temp.contains("]"))))
                    WordCounter++;
            }
        SongReader.close();
        return WordCounter;
    }

    public Map<String, Integer> CountDifferentWordsInSong(String path)
    {
        Map<String, Integer> wordCount = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+"); // split by whitespace
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase(); // remove non-alphabetic characters and convert to lowercase
                    if (!word.isEmpty()) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1); // increment count
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordCount;
    }
    /*
    public static void StaticLexicographicalRichnessInFile(String path)
    {
        Map<String, Integer> wordCount = new HashMap<>();
        double wordCounter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+"); // split by whitespace
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase(); // remove non-alphabetic characters and convert to lowercase
                    wordCounter++;
                    if (!word.isEmpty()) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1); // increment count
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double lexi_richness = (double)wordCount.size() / wordCounter;
        System.out.println("the lexi richness of the text is : " + lexi_richness);
    }
*/

}
