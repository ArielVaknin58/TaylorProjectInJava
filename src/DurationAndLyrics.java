import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;


public class DurationAndLyrics {
    int Duration;

    String lyrics;

    public DurationAndLyrics(int duration, String Lyrics)
    {
        this.Duration = duration;
        this.lyrics = Lyrics;
    }

    public DurationAndLyrics() {
        this.Duration = 0;
        this.lyrics = "bloop";
    }

    public static DurationAndLyrics CreateSongHash(String path)
    {
        StringBuilder lyrics = new StringBuilder();
        DurationAndLyrics DAL = new DurationAndLyrics();
        try (Scanner reader = new Scanner(new FileReader(path)))
        {
            String SongName = reader.nextLine();
            String duration = reader.nextLine();
            String minutes = duration.substring(0,2);
            int DurationInSec = (Integer.parseInt(minutes))*60;
            String seconds = duration.substring(3);
            DurationInSec += Integer.parseInt(seconds);//calculate the duration of the song

            String line = reader.nextLine();
            while (reader.hasNextLine())
            {
                line = reader.nextLine();
                lyrics.append(line).append("\n");// Append the current line to the lyrics
            }

            String Lyrics = lyrics.toString();
            DAL.lyrics = Lyrics;
            DAL.Duration = DurationInSec;

        } catch (IOException e) {
            System.err.println("Error reading lyrics from file: " + e.getMessage());
            // Handle the error appropriately in your application
        }
        return DAL;
    }

    public static HashMap<String,HashMap<String,DurationAndLyrics>> CreateDiscographyHash(String MainFilePath)
    {
        HashMap<String,HashMap<String,DurationAndLyrics>> FullHash = new HashMap<String,HashMap<String,DurationAndLyrics>>();
        try(Scanner reader = new Scanner(new File(MainFilePath)))
        {
            String temp = reader.nextLine();
            temp = temp.substring(15);
            int NumOfAlbums = Integer.parseInt(temp);
            for(int i = 1; i <= NumOfAlbums ; i++)
            {
                temp = reader.nextLine();
                FullHash.put(Album.IntToAlbum(i),CreateAlbumHash(temp));
            }
        }
        catch(IOException e)
        {
            System.err.println("Error reading lyrics from file: " + e.getMessage());
        }

        return FullHash;

    }
    public static HashMap<String,DurationAndLyrics> CreateAlbumHash(String AlbumPath)
    {
        HashMap<String,DurationAndLyrics> AlbumHash = new HashMap<String, DurationAndLyrics>();
        try(Scanner reader = new Scanner(new File(AlbumPath)))
        {
            String temp = reader.nextLine();
            temp = temp.substring(14);
            int NumOfTracks = Integer.parseInt(temp);
            for(int i = 1; i <= NumOfTracks ; i++)
            {
                temp = reader.nextLine();
                AlbumHash.put(ReadSongName(temp),CreateSongHash(temp));
            }
        }
        catch(IOException e)
        {
              System.err.println("Error reading lyrics from file: " + e.getMessage());
        }
        return AlbumHash;
    }

    public static String ReadSongName(String path)
    {
        File file = new File(path);
        String res = "";
        try(Scanner reader = new Scanner(file))
        {
            res = reader.nextLine();
        }
        catch(IOException e)
        {
            System.err.println("Error reading lyrics from file: " + e.getMessage());
            // Handle the error appropriately in your application
        }

        return res;
    }
}