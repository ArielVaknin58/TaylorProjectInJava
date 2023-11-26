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

    public static void NumOfWordsInDiscography(Album[] array)
    {
        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
        Scanner Keyboard = new Scanner(System.in);
        int choice = Keyboard.nextInt();
        Main.InputCheck(0,1,Keyboard,choice);
        int TotalCounter = 0;

        if(choice == 0)
        {
            for(int i = 1; i < array.length ; i++)
                TotalCounter += NumOfWordsInAlbum(array[i]);

            System.out.println("There are "+TotalCounter+" words in Taylor's discography.");
        }
        else if(choice == 1)
        {
            Song TotalMaxSong = array[1].SongsArray[1];
            Song TotalMinSong = array[1].SongsArray[1];
            int TotalMaxWords = 0;
            int TotalMinWords = 1000;
            String FileName = "Word counter Full Summary.txt";
            File file = new File(FileName);
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
            {
                writer.write("Summary of the number of words in Taylor's Discography\n\n");
                for(int i = 1; i <= Main.DiscographyLength ; i++)
                {
                    Song AlbumMaxSong = array[1].SongsArray[1];
                    Song AlbumMinSong = array[1].SongsArray[1];
                    int AlbumMaxWords = 0;
                    int AlbumMinWords = 1000;
                    writer.write("Album Name : "+array[i].AlbumName+"\n");
                    int AlbumCounter = 0;
                    for (int j = 1; j <= array[i].NumOfSongs ; j++)
                    {
                        int counter = Song.NumOfWordsInASong(array[i].SongsArray[j]);
                        if(counter >= AlbumMaxWords)
                        {
                            AlbumMaxSong = array[i].SongsArray[j];
                            AlbumMaxWords = counter;
                            if(counter >= TotalMaxWords)
                            {
                                TotalMaxSong = array[i].SongsArray[j];
                                TotalMaxWords = counter;
                            }
                        }
                        else if(counter <= AlbumMinWords)
                        {
                            AlbumMinSong = array[i].SongsArray[j];
                            AlbumMinWords = counter;
                            if(counter < TotalMinWords)
                            {
                                TotalMinSong = array[i].SongsArray[j];
                                TotalMinWords = counter;
                            }
                        }
                        writer.write(j+") "+array[i].SongsArray[j].SongName+" : "+counter +" words\n");
                        AlbumCounter += counter;
                    }
                    writer.write("In summary, in the "+array[i].AlbumName+" album there are "+AlbumCounter+" words.\n");
                    writer.write("The longest song on the album is "+AlbumMaxSong.SongName+" with "+AlbumMaxWords+" words\n");
                    writer.write("The shortest song on the album is "+AlbumMinSong.SongName+" with "+AlbumMinWords+" words.\n\n");
                    TotalCounter += AlbumCounter;
                }
                writer.write("***In summary, there are "+TotalCounter+" words in Taylor's entire discography.\n");
                writer.write("The longest song on Taylor's discography is "+TotalMaxSong.SongName+" with "+TotalMaxWords+" words\n");
                writer.write("The shortest song on the album is "+TotalMinSong.SongName+" with "+TotalMinWords+" words.\n\n");
                System.out.println("*** Summary file has been created ! ***");
            }catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public static int AvgSongLengthInAlbum(Album album)
    {
        Song[] array = album.SongsArray;
        int DurationAccumulator = 0;
        for(int i = 1; i <= album.NumOfSongs ; i++)
            DurationAccumulator += array[i].DurationInSec;

        return (DurationAccumulator / album.NumOfSongs);
    }

    public static void AvgSongLengthInDiscography(Album[] array)
    {
        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
        System.out.print("----->");
        Scanner Keyboard = new Scanner(System.in);
        int FileChoice = Keyboard.nextInt();
        Main.InputCheck(0,1,Keyboard,FileChoice);
        if(FileChoice == 0) {
            int TotalAvgAccumulater = 0;
            System.out.println();
            for (int i = 1; i <= Main.DiscographyLength; i++)
            {
                int AlbumCounter = AvgSongLengthInAlbum(array[i]);
                TotalAvgAccumulater += AlbumCounter;
                System.out.println("In the "+array[i].AlbumName+" album the song average is "+AlbumCounter/60+"m and "+AlbumCounter%60+"s.");
            }
            System.out.println("The total song average in Taylor's discography is "+TotalAvgAccumulater/60+"m and "+TotalAvgAccumulater%60+"s");
        }
        else if(FileChoice == 1)
        {
            String FileName = "Song Average Full Summary.txt";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
            {
                writer.write("Summary file for song averages in Taylor's discography\n\n");
                int TotalAvg = 0;
                for(int i = 1 ; i <= Main.DiscographyLength ; i++)
                {
                    writer.write("Album name : "+array[i].AlbumName+"\n");
                    Song[] AlbumArray = array[i].SongsArray;
                    for(int j = 1 ; j <= array[i].NumOfSongs ; j++)
                    {
                        writer.write(j+") "+AlbumArray[j].SongName+" : "+Song.GetSongHours(AlbumArray[j])+":"+Song.GetSongSeconds(AlbumArray[j])+"\n");
                    }
                    int average = AvgSongLengthInAlbum(array[i]);
                    TotalAvg += average;
                    writer.write("In summary, the song average in the "+array[i].AlbumName+" album is "+average/60+"m and "+average%60+"s.\n\n");
                }
                TotalAvg = TotalAvg / Main.DiscographyLength;
                writer.write("***In summary, the average song length in Taylor's discography is "+TotalAvg/60+"m and "+TotalAvg%60+"s ***");
                System.out.println("*** Summary file has been created ! ***");

            }catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
    }
    public static int NumOfWordsInAlbum(Album album)
    {//NEEDED THE OPEN ALBUM FILE AND ALBUM NUMBER

        int AlbumCounter = 0;
        Song[] AlbumArray = album.SongsArray;
        for(int i = 1; i <= album.NumOfSongs ; i++)
            AlbumCounter += Song.NumOfWordsInASong(AlbumArray[i]);

        System.out.println("There are "+AlbumCounter+" words in The "+album.AlbumName+" album.");
        return AlbumCounter;
    }

    public static void NumOfWordsInAlbumSummary(Album album)
    {
        String FileName = album.AlbumName+" - Word counter.txt";
        File file = new File(FileName);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary for the number of words in the "+album.AlbumName+" album\n\n");
            int AlbumCounter = 0;
            for (int j = 1; j <= album.NumOfSongs ; j++)
            {
                int counter = Song.NumOfWordsInASong(album.SongsArray[j]);
                writer.write(j+") "+album.SongsArray[j].SongName+" : "+counter +" words\n");
                AlbumCounter += counter;
            }
            writer.write("***In summary, in the "+album.AlbumName+" album there are "+AlbumCounter+" words***\n\n");
            System.out.println("*** Summary file has been created ! ***");
        }catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
