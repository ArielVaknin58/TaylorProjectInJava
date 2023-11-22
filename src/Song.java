import java.io.*;
import java.util.Scanner;// Used to read from the user or files
import java.lang.Integer; // used for the ParseInt method


public class Song {
    public String SongName;//Holds a string containing the name of the song.
    public String Path;//Holds the path to the song,to be given in order to open the file in other methods.
    public String Word;//Contains the word the program searches for.
    int counter;//Holds the number of appearances of the given word in the song

    int DurationInSec;//Holds the duration of the song in seconds.


    public Song()
    {
        this.SongName = "";
        this.Path = "";
        this.Word = "";
        this.counter = 0;
        this.DurationInSec = 0;
    }

    public static int GetSongHours(Song song)
    {
        return song.DurationInSec  / 60;
    }

    public static int GetSongSeconds(Song song)
    {
        return song.DurationInSec % 60;
    }

    public Song GetSongInformation(File file)
    {//Modifies only SongName and it's duration.
        Song song = new Song();
        try(Scanner SongReader = new Scanner(file);)
        {
            String temp = SongReader.nextLine();
            song.SongName = temp;//read the file name

            temp = SongReader.nextLine();
            String minutes = temp.substring(0,2);
            song.DurationInSec = (Integer.parseInt(minutes))*60;
            String seconds = temp.substring(3);
            song.DurationInSec += Integer.parseInt(seconds);//calculate the duration of the song
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return song;
    }

    public static int NumOfWordsInASong(Song song)//counts the number of words in a song
    {//ONLY SONG PATH IS NEEDED
        File SongFile = new File(song.Path);
        song = song.GetSongInformation(SongFile);

        int WordCounter = 0;
        try(Scanner SongReader = new Scanner(SongFile))
        {
            String temp = SongReader.nextLine();//reads the song's name
            temp = SongReader.nextLine();//reads the song's duration

            while(SongReader.hasNext())
            {
                temp = SongReader.next();
                if(temp.contains("[") || (temp.contains("]")))
                    continue;
                else
                    WordCounter++;
            }
        }catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }

        return WordCounter;
    }
}
