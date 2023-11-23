import java.io.*;
import java.util.Scanner;

public class Album {

    String AlbumName;//Holds a string with the album's name
    int NumOfSongs;//Holds the number of songs on the album

    int AlbumNumber;//Holds the serial number of the album. 1 for debut, 2 for Fearless etc. Used with the IntToAlbum() method.

    File file;//Holds the open file of the album

    Song[] SongsArray;//Holds the array containing the songs of the album

    public Album(String name,int Length,int num,File file,Song[] songsArray)
    {
        this.AlbumName = name;
        this.NumOfSongs = Length;
        this.AlbumNumber = num;
        this.file = file;
        this.SongsArray = songsArray;
    }

    public Album()
    {
        this.AlbumName = "";
        this.NumOfSongs = 0;
        this.AlbumNumber = 0;
        this.file = null;
        this.SongsArray = null;
    }

    public static String IntToAlbum(int num)// This function gets an integer and returns a string which contains the name of the album. Invalid input has already been checked in the main function so there is no default option in the switch.
    {

        return switch (num) {
            case 1 -> "Debut";
            case 2 -> "Fearless";
            case 3 -> "Speak Now";
            case 4 -> "Red";
            case 5 -> "1989";
            case 6 -> "Reputation";
            case 7 -> "Lover";
            case 8 -> "Folklore";
            case 9 -> "Evermore";
            case 10 -> "Midnights";
            case 11 -> "Specials";
            default -> "wrong input";
        };
    }

    public static Album GetAlbumDetails(File AlbumFile,int AlbumNum)//The album's open Readme file should be given as a parameter
    {//NEEDED JUST THE OPENED ALBUM FILE AND THE ALBUM NUMBER
        Album album = null;
        try {
            String temp;
            album = new Album();
            Scanner AlbumReader = new Scanner(AlbumFile);
            temp = AlbumReader.nextLine();
            temp = temp.substring(14);
            album.NumOfSongs = Integer.parseInt(temp);
            album.file = AlbumFile;
            album.AlbumName = IntToAlbum(AlbumNum);
            album.AlbumNumber = AlbumNum;

        }
        catch (FileNotFoundException e)
        {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }

        return album;

    }


    public static void LongestAndShortestSongsInAlbum(Album album)
    {
        Scanner Keyboard = new Scanner(System.in);
        System.out.println("Would you like a summary report ? 1 for yes / 0 for no :");
        int choice = Keyboard.nextInt();
        while((choice != 0 )&& (choice != 1))//Input check
        {
            System.out.println("Invalid input, Please try again.");
            System.out.print("----->");
            choice = Keyboard.nextInt();
        }

        Song[] array = album.SongsArray;
        Song MaxSong = array[1];
        Song MinSong = array[1];
        int AlbumCounter = 0;
        if(choice == 0)//If the user chooses to not create a file
        {
           for(int i = 1 ; i <= album.NumOfSongs ; i++)
           {
               if(array[i].DurationInSec >= MaxSong.DurationInSec)
               {
                   MaxSong = array[i];
               }
               if(array[i].DurationInSec < MinSong.DurationInSec)
               {
                   MinSong = array[i];
               }
               AlbumCounter += array[i].DurationInSec;
           }
           int Hours = AlbumCounter / 3600;
           int Minutes = (AlbumCounter - Hours*3600)/60;
           int Seconds = AlbumCounter % 60;
           System.out.println("In summary, the "+album.AlbumName+" album is "+Hours+"h, "+Minutes+"m and "+Seconds+"s long.\n");
           System.out.println("The longest song on the album is "+MaxSong.SongName);
           System.out.println("The song is "+Song.GetSongHours(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.");
           System.out.println("\nThe Shortest song on the album is "+MinSong.SongName+" and it's "+Song.GetSongHours(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.");
        }
        else if(choice == 1)//If the user chooses to create a summary file
        {
            String FileName = album.AlbumName+" Duration Summary.txt";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))){
                writer.write("Summary for the "+album.AlbumName+" album - Duration in MM:SS format\n");

                for(int i = 1 ; i <= album.NumOfSongs ; i++)
                {
                    writer.write(i+") "+array[i].SongName+" : "+Song.GetSongHours(array[i])+":"+Song.GetSongSeconds(array[i])+"\n");
                    if(array[i].DurationInSec >= MaxSong.DurationInSec)
                    {
                        MaxSong = array[i];
                    }
                    if(array[i].DurationInSec < MinSong.DurationInSec)
                    {
                        MinSong = array[i];
                    }
                }

                writer.write("\n*** In summary, the longest song on the album is "+MaxSong.SongName+" which is "+Song.GetSongHours(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.\n");
                writer.write("The Shortest song on the album is "+MinSong.SongName+" and it's "+Song.GetSongHours(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.***");

            }catch (IOException e)
            {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
            System.out.println("*** Summary file has been created ! ***");
        }
    }
    public static int NumOfWordsInAlbum(Album album)
    {//NEEDED THE OPEN ALBUM FILE AND ALBUM NUMBER
        album = Album.GetAlbumDetails(album.file,album.AlbumNumber);
        int AlbumCounter = 0;
        try(Scanner AlbumReader = new Scanner(album.file))
        {
            String temp = AlbumReader.nextLine();
            for(int i = 1; i <= album.NumOfSongs ; i++)
            {
                int counter = 0;
                Song song = new Song();
                temp = AlbumReader.nextLine();
                song.Path = temp;
                counter = Song.NumOfWordsInASong(song);
                AlbumCounter += counter;
            }
        }
        catch (IOException e)
        {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return AlbumCounter;
    }

    public static Song[] CreateAlbumArray(Album album)
    {
        album = Album.GetAlbumDetails(album.file,album.AlbumNumber);
        Song[] AlbumArray = new Song[album.NumOfSongs + 1];
        try(Scanner AlbumReader = new Scanner(album.file))
        {
            String temp = AlbumReader.nextLine();
            for(int i = 1; i <= album.NumOfSongs ; i++)
            {
                Song song = new Song();
                temp = AlbumReader.nextLine();
                File SongFile = new File(temp);
                song = song.GetSongInformation(SongFile);
                song.Path = temp;
                AlbumArray[i] = song;
            }
        }
        catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }

        return AlbumArray;
    }



}
