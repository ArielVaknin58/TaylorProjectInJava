import java.io.*;
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
    public static int GetSongHours(Song song) {return song.DurationInSec  / 60; }
    public static int GetSongSeconds(Song song) {return song.DurationInSec % 60; }

    public static Song GetSongInformation(File file)//This method constructs a song object from its file.
    {//Modifies all except path.
        Song song = new Song();
        try(Scanner SongReader = new Scanner(file))
        {
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

        }
        catch(FileNotFoundException e) {System.out.println("An error occurred.");}
        return song;
    }

    public static int WordCounterInSong(Song song,String word)// This method counts the number of times a word appears in a given song
    {
        int WordCounter = 0;
        String temp;

        File file = new File(song.GetSongPath());
        try(Scanner myReader = new Scanner(file))
        {
            temp = myReader.nextLine();
            song.SetSongName(temp);
            temp = myReader.nextLine();

            String minutes = temp.substring(0,2);
            String seconds = temp.substring(3,5);
            int Min = (Integer.parseInt(minutes))*60;
            int Sec = Integer.parseInt(seconds);
            int Duration = Min + Sec;
            song.SetDurationInSec(Duration);

            while (myReader.hasNextLine())
            {
                temp = myReader.next();
                if(!temp.matches("[a-zA-Z0-9]+"))
                    temp = temp.replaceAll("[^a-zA-Z0-9]","");

                if(temp.compareToIgnoreCase(word) == 0)
                    WordCounter++;
            }
        } catch (FileNotFoundException e) {System.out.println("An error occurred.//");}
        return WordCounter;
    }

    public static int CountObscenitiesInSong(Song song)//This method counts and returns the number of obscenities in a given song.
    {
        File SongFile = new File(song.GetSongPath());
        int ObscenitiesCounter  = 0;
        try(Scanner SongReader = new Scanner(SongFile))
        {
            @SuppressWarnings("UnusedAssignment")
            String temp = SongReader.nextLine();//reads the song's name
            temp = SongReader.nextLine();//reads the song's duration
            while(SongReader.hasNext())
            {
                temp = SongReader.next();
                if(temp.equalsIgnoreCase("bitch")||temp.equalsIgnoreCase("damn")||temp.equalsIgnoreCase("shit")||temp.equalsIgnoreCase("fuck")||temp.equalsIgnoreCase("damned"))
                    ObscenitiesCounter++;
            }
        }catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}
        return ObscenitiesCounter;
    }
    public static int NumOfWordsInASong(Song song)//counts the number of words in a song
    {
        File SongFile = new File(song.GetSongPath());
        int WordCounter = 0;
        try(Scanner SongReader = new Scanner(SongFile))
        {
            String temp = SongReader.nextLine();//reads the song's name
            temp = SongReader.nextLine();//reads the song's duration

            while(SongReader.hasNext())
            {
                temp = SongReader.next();
                if(!(temp.contains("[") || (temp.contains("]"))))
                    WordCounter++;
            }
        }catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}
        return WordCounter;
    }

    public static void AvgSongLengthInDiscography(Album[] array)//This method calculates the average song length in Taylor's discography.
    {
        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
        System.out.print("----->");
        Scanner Keyboard = new Scanner(System.in);
        int FileChoice = Keyboard.nextInt();
        Main.InputCheck(0,1,Keyboard,FileChoice);
        if(FileChoice == 0)//The user chose to not create a summary file.
        {
            int TotalAvgAccumulator = 0;
            System.out.println();
            for (int i = 1; i <= Main.getDiscographyLength(); i++)
            {
                int AlbumCounter = Album.AvgSongLengthInAlbum(array[i]);
                TotalAvgAccumulator += AlbumCounter;
                System.out.println("In the "+array[i].GetAlbumName()+" album the song average is "+AlbumCounter/60+"m and "+AlbumCounter%60+"s.");
            }
            System.out.println("The total song average in Taylor's discography is "+TotalAvgAccumulator/60+"m and "+TotalAvgAccumulator%60+"s");
        }
        else if(FileChoice == 1)//The user chose to create a summary file.
        {
            String FileName = "Song Average Full Summary.txt";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
            {
                writer.write("Summary file for song averages in Taylor's discography\n\n");
                int TotalAvg = 0;
                for(int i = 1 ; i <= Main.getDiscographyLength() ; i++)
                {
                    writer.write("Album name : "+array[i].GetAlbumName()+"\n");
                    Song[] AlbumArray = array[i].GetSongsArray();
                    for(int j = 1 ; j <= array[i].GetNumOfSongs() ; j++)
                        writer.write(j+") "+AlbumArray[j].GetSongName()+" : "+Song.GetSongHours(AlbumArray[j])+":"+Song.GetSongSeconds(AlbumArray[j])+"\n");

                    int average = Album.AvgSongLengthInAlbum(array[i]);
                    TotalAvg += average;
                    writer.write("In summary, the song average in the "+array[i].GetAlbumName()+" album is "+average/60+"m and "+average%60+"s.\n\n");
                }
                TotalAvg = TotalAvg / Main.getDiscographyLength();
                writer.write("***In summary, the average song length in Taylor's discography is "+TotalAvg/60+"m and "+TotalAvg%60+"s ***");
                System.out.println("*** Summary file has been created ! ***");
            }catch (IOException e) {System.out.println("An error occurred.");}
        }
    }
}
