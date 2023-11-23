import java.io.*;
import java.util.Scanner;// Used to read from the user or files
import java.lang.Integer; // used for the ParseInt method
import java.util.Random;// Used to generate a random song
import java.util.HashMap;



/*  TO DO LIST
1)
2)
3) Implement a GUI
4)
5) Add average song and implement the word counter.
6) Add more function to do with duration (album length,avg song length in album/discography etc.)
//*** word counter, what song is the longest word-wise and seconds wise.

 */

public class Main
{
    public static int DiscographyLength = 11;

    public static void main(String[] args)
    {

        int IfRunAgain = 1;


        Printers.PrintWelcomeBanner();

        try(Scanner Keyboard = new Scanner(System.in)) {

            File file = new File(args[0]);
            Album[] array = CreateDiscographyArray(file);

            while(IfRunAgain != 0) // Main loop. repeats until its requested to exit
            {
                int counter = 0;
                int MainPick = 0;
                System.out.println("Hello ! Pick one of the options :\n0 to get a random Taylor song\n1 to look up a word \n2 to get Songs/Albums length information :");
                System.out.print("----->");
                MainPick = Keyboard.nextInt();
                InputCheck(0,3,Keyboard,MainPick);
                if(MainPick == 1)//The user picked to look up a word
                {
                    System.out.println("\nPlease enter the word you'd like to search : ");
                    System.out.print("----->");
                    String word = Keyboard.next();
                    String temp;
                    Scanner FileReader = new Scanner(file);
                    temp = FileReader.nextLine();
                    temp = temp.substring(15);
                    DiscographyLength = Integer.parseInt(temp);
                    System.out.println("In which album would you like to search ? choose 1-" + DiscographyLength + " or 0 for the entire discography :");
                    System.out.print("----->");
                    Printers.PrintAlbums();
                    int AlbumPick = Keyboard.nextInt();
                    InputCheck(0,DiscographyLength,Keyboard,AlbumPick);
                    if (AlbumPick == 0)// The user picked to search in the entire discography
                    {
                        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no : ");
                        int FileChoice = Keyboard.nextInt();
                        if(FileChoice == 1)
                        {
                            MakeSummaryFileDiscography(word,array);
                            System.out.println("*****Summary file has been created successfully !*****");
                        }
                        else if(FileChoice == 0)
                        {
                            counter = WordCounterInDiscography(word,array);
                            System.out.println("The word \""+word+"\" appears "+counter+" times in Taylor's discography.");
                        }

                    }
                    else// The user picked a specific album
                    {
                        int i;
                        int FileChoice = 0;

                        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no : ");
                        System.out.print("----->");
                        FileChoice = Keyboard.nextInt();
                        InputCheck(0,1,Keyboard,FileChoice);
                        if(FileChoice == 1)//If the user wants to create a file
                        {
                            MakeSummaryFileAlbum(word,array[AlbumPick]);
                            System.out.println("*** Summary file has been created ! ***");
                        }
                        else //if the user doesn't want to create a file
                        {
                            System.out.println("In which song would you like to search ? pick 1-" + array[AlbumPick].NumOfSongs + " or 0 for the entire album :");
                            Printers.PrintAlbumsTracks(AlbumPick);
                            System.out.print("----->");
                            int SongChoice = Keyboard.nextInt();
                            InputCheck(0,array[AlbumPick].NumOfSongs,Keyboard,SongChoice);
                            if (SongChoice > 0) //The user picked a specific song
                            {
                                counter = WordCounterInSong(array[AlbumPick].SongsArray[SongChoice],word);
                                System.out.println("The word \"" + word + "\" appears " + counter + " times in the song " + array[AlbumPick].SongsArray[SongChoice].SongName + ".");

                            } else if (SongChoice == 0) //The user chose to search in the entire album
                            {
                                int AlbumCounter = WordCounterInAlbum(word,array[AlbumPick]);
                                System.out.println("The word \"" +word + "\" appears " + AlbumCounter + " times in the " + array[AlbumPick].AlbumName + " album.");

                            }
                        }
                    }
                    Keyboard.nextLine();
                    FileReader.close();
                }
                else if(MainPick == 0)// The user chose to get a random taylor song
                {
                    Song RandSong = RandomSongArray(array);
                    System.out.println("The chosen song is " + RandSong.SongName + " !");
                }
                else if(MainPick == 2)
                {
                    System.out.println("Please choose an album or press 0 for the entire discography :");
                    Printers.PrintAlbums();
                    System.out.println("----->");
                    int choice = Keyboard.nextInt();
                    InputCheck(0,DiscographyLength,Keyboard,choice);
                    if(choice == 0)//The user chooses Duration information for the entire discography
                    {
                        DurationSummaryDiscography(array);
                    }
                    else//The user chooses to get information about a specific album
                    {
                        System.out.println("Please choose a song or press 0 for the entire album :");
                        Printers.PrintAlbumsTracks(choice);
                        int input = Keyboard.nextInt();
                        InputCheck(0,array[choice].NumOfSongs,Keyboard,input);
                        if(input == 0)//The user wants information about the whole album
                        {
                            Album.LongestAndShortestSongsInAlbum(array[choice]);
                        }
                        else//The user wants a specific song
                        {
                            Song ChosenSong = array[choice].SongsArray[input];
                            System.out.println("The duration of the song "+array[choice].SongsArray[input].SongName+" is "+Song.GetSongHours(array[choice].SongsArray[input])+"h and "+Song.GetSongSeconds(array[choice].SongsArray[input])+"s.");
                        }
                    }
                }
                else if(MainPick == 3)
                {
                    HashMap<String,HashMap<String,DurationAndLyrics>> FullHash = DurationAndLyrics.CreateDiscographyHash(args[0]);
                    System.out.println(FullHash);
                }
                System.out.println("Would you like to go again ? choose any number to continue or 0 to exit ");
                System.out.print("----->");
                IfRunAgain = Keyboard.nextInt();
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("I'll tell you the truth, but never goodbye");

    }



    public static void InputCheck(int MinAllowed,int MaxAllowed,Scanner Keyboard,int input)
    {
        while(!((input >= MinAllowed)&&(input <= MaxAllowed)))
        {
            System.out.println("Invalid input, Please try again.");
            System.out.print("----->");
            input = Keyboard.nextInt();
        }

    }

    public static void DurationSummaryDiscography(Album[] array)
    {
        System.out.println("Would you like to create a summary file ? 1 for yes / 0 for no");
        Scanner Keyboard = new Scanner(System.in);
        int choice = Keyboard.nextInt();
        while((choice != 0 )&& (choice != 1))//Input check
        {
            System.out.println("Invalid input, Please try again.");
            System.out.print("----->");
            choice = Keyboard.nextInt();
        }

        if(choice == 1)//The user chooses to create a summary file
        {
            String FileName = "Discography Duration Summary.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
            {
                writer.write("Summary Duration for Taylor's discography in MM:SS format\n\n");
                int TotalCounter = 0;
                Song TotalMaxSong = array[1].SongsArray[1];
                Song TotalMinSong = array[1].SongsArray[1];

                for(int i = 1 ; i <= DiscographyLength ; i++)
                {
                    int AlbumCounter = 0;
                    writer.write("Album name : "+array[i].AlbumName+"\n");
                    Song[] AlbumArray = array[i].SongsArray;
                    Song MaxSong = AlbumArray[1];
                    Song MinSong = AlbumArray[1];
                    for(int j = 1 ; j <= array[i].NumOfSongs ; j++)
                    {
                        int Counter = AlbumArray[j].DurationInSec;
                        if(AlbumArray[j].DurationInSec >= MaxSong.DurationInSec)
                        {
                            MaxSong = AlbumArray[j];
                        }
                        else if(AlbumArray[j].DurationInSec < MinSong.DurationInSec)
                        {
                            MinSong = AlbumArray[j];
                        }

                        writer.write(j+") "+AlbumArray[j].SongName+" : "+Song.GetSongHours(AlbumArray[j])+":"+Song.GetSongSeconds(AlbumArray[j])+"\n");
                        AlbumCounter += Counter;
                    }
                    int Hours = AlbumCounter / 3600;
                    int Minutes = (AlbumCounter - Hours*3600)/60;
                    int Seconds = AlbumCounter % 60;
                    writer.write("In summary, the "+ array[i].AlbumName+" album is "+Hours+"h, "+Minutes+"m and "+Seconds+"s long.\n");
                    writer.write("The longest song on the album is "+MaxSong.SongName+" which is "+Song.GetSongHours(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.\n");
                    writer.write("The Shortest song on the album is "+MinSong.SongName+" and it's "+Song.GetSongHours(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.\n\n");
                    TotalCounter += AlbumCounter;
                    if(MaxSong.DurationInSec >= TotalMaxSong.DurationInSec)
                    {
                        TotalMaxSong = MaxSong;
                    }
                    if(MinSong.DurationInSec < TotalMinSong.DurationInSec)
                    {
                        TotalMinSong = MinSong;
                    }

                }
                int TotalHours = TotalCounter / 3600;
                int TotalMinutes = (TotalCounter - TotalHours*3600)/60;
                int TotalSeconds = TotalCounter % 60;
                writer.write("\n***In summary, Taylor's entire discography is "+TotalHours+"h, "+TotalMinutes+"m and "+TotalSeconds+"s long.\n");
                writer.write("The Longest Taylor song is "+TotalMaxSong.SongName+" and is "+Song.GetSongHours(TotalMaxSong)+"m and "+Song.GetSongSeconds(TotalMaxSong)+"s long.\n" );
                writer.write("The Shortest Taylor song is "+TotalMinSong.SongName+" and is "+Song.GetSongHours(TotalMinSong)+"m and "+Song.GetSongSeconds(TotalMinSong)+"s long.\n" );
                System.out.println("*** Summary file has been created ! ***");
            }
            catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }else if(choice == 0)
        {
            int TotalCounter = 0;//counts the duration (seconds) of the entire discography
            Song TotalMaxSong = array[1].SongsArray[1];//holds the longest song on Taylor's discography
            Song TotalMinSong = array[1].SongsArray[1];//holds the shortest song on Taylor's discography

            for(int i = 1 ; i <= DiscographyLength ; i++)
            {
                int AlbumCounter = 0;//counts the duration (seconds) of the i-th album.
                Song[] AlbumArray = array[i].SongsArray;//the array of songs of the i-th album
                Song MaxSong = AlbumArray[1];//will hold the longest song on the album
                Song MinSong = AlbumArray[1];//will hold the shortest song on the album
                for(int j = 1 ; j <= array[i].NumOfSongs ; j++)
                {
                    int Counter = AlbumArray[j].DurationInSec;
                    if(AlbumArray[j].DurationInSec >= MaxSong.DurationInSec)//if the current song in longer than the album MaxSong
                    {
                        MaxSong = AlbumArray[j];
                    }
                    else if(AlbumArray[j].DurationInSec < MinSong.DurationInSec)//if the current song in shorter than the album MinSong
                    {
                        MinSong = AlbumArray[j];
                    }
                    AlbumCounter += Counter;
                }
                TotalCounter += AlbumCounter;
                if(MaxSong.DurationInSec >= TotalMaxSong.DurationInSec)//if the MaxSong on the album is longer than the TotalMaxSong
                {
                    TotalMaxSong = MaxSong;
                }
                else if(MinSong.DurationInSec < TotalMinSong.DurationInSec)//if the MinSong on the album is shorter than the TotalMinSong
                {
                    TotalMinSong = MinSong;
                }
            }
            int TotalHours = TotalCounter / 3600;
            int TotalMinutes = (TotalCounter - TotalHours*3600)/60;
            int TotalSeconds = TotalCounter % 60;
            System.out.println("\n***In summary, Taylor's entire discography is "+TotalHours+"h, "+TotalMinutes+"m and "+TotalSeconds+"s long.");
            System.out.println("The Longest Taylor song is "+TotalMaxSong.SongName+" and is "+Song.GetSongHours(TotalMaxSong)+"m and "+Song.GetSongSeconds(TotalMaxSong)+"s long." );
            System.out.println("The Shortest Taylor song is "+TotalMinSong.SongName+" and is "+Song.GetSongHours(TotalMinSong)+"m and "+Song.GetSongSeconds(TotalMinSong)+"s long." );
        }
    }
    public static Song RandomSongArray(Album[] array)
    {
        Random rand = new Random();
        int album = rand.nextInt(1,DiscographyLength+1);
        int song = rand.nextInt(1,array[album].NumOfSongs+1);
        return array[album].SongsArray[song];
    }

    public static int WordCounterInSong(Song song,String word)// This method counts the number of times a word appears in a given song
    {//NEEDED THE SONG PATH AND THE WORD

        int WordCounter = 0;
        String temp;

        File file = new File(song.Path);

        try(Scanner myReader = new Scanner(file))
        {

            song.SongName = myReader.nextLine();
            temp = myReader.nextLine();

            String minutes = temp.substring(0,2);
            song.DurationInSec = (Integer.parseInt(minutes))*60;
            String seconds = temp.substring(3);
            song.DurationInSec += Integer.parseInt(seconds);

            while (myReader.hasNextLine())
            {
                temp = myReader.next();
                if(!temp.matches("[a-zA-Z0-9]+"))
                    temp = temp.replaceAll("[^a-zA-Z0-9]","");

                if(temp.compareToIgnoreCase(word) == 0)
                    WordCounter++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.//");
            e.printStackTrace();
        }

        song.counter = WordCounter;
        return WordCounter;
    }

    public static int WordCounterInAlbum(String word,Album album)// This method counts the number of times a word appears in a given album
    {
        int AlbumCounter = 0;
        Song[] array = album.SongsArray;

        for (int i = 1; i <= album.NumOfSongs; i++)
        {
            int counter = WordCounterInSong(array[i],word);
            AlbumCounter += counter;
        }

        return AlbumCounter;
    }

    public static int WordCounterInDiscography(String word,Album[] array)//This method counts the number of times a word appears in Taylor's entire discography
    {
        int TotalCounter = 0;
        for(int i = 1; i <= DiscographyLength ; i++)
        {
            int counter = 0;
            counter = WordCounterInAlbum(word,array[i]);
            TotalCounter += counter;
        }

        return TotalCounter;
    }

    public static void MakeSummaryFileAlbum(String word,Album album)
    {
        String NewFileName = word+" in "+album.AlbumName+" Summary.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NewFileName)))
        {
            writer.write("Summary file for the word "+word+" in the "+album.AlbumName+" album.\n");
            int AlbumCounter = 0;
            for(int i = 1; i <= album.NumOfSongs ; i++)
            {
                int counter = 0;
                counter = WordCounterInSong(album.SongsArray[i],word);
                writer.write(i+") \""+album.SongsArray[i].SongName+"\" : "+counter+ " times\n");
                AlbumCounter += counter;
            }
            writer.write("*** In Total, the word \""+word+"\" appears "+AlbumCounter+" times in the album \""+album.AlbumName+"\".");
        }
        catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }

    }

    public static void MakeSummaryFileDiscography(String word,Album[] array)
    {
        String FileName = word + " - Full Summary.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for the word \""+word+"\".\n");
            int TotalCounter = 0;
            for(int i = 1; i <= DiscographyLength ; i++)
            {
                Song[] AlbumArray = array[i].SongsArray;
                writer.write("Album name : "+array[i].AlbumName+"\n");
                int AlbumCounter = 0;
                for(int j = 1; j <= array[i].NumOfSongs ; j++)
                {
                    int counter = 0;
                    counter = WordCounterInSong(array[i].SongsArray[j],word);
                    writer.write(j+") "+array[i].SongsArray[j].SongName+" : "+counter+ " times\n");
                    AlbumCounter += counter;
                }
                writer.write("In total, The word appears "+AlbumCounter+" times in the "+array[i].AlbumName+" album.\n\n");
                TotalCounter += AlbumCounter;
            }
            writer.write("***** In Total, The word appears "+TotalCounter +" times in Tyalor's Entire Discography *****");
        }
        catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }

    }

    public static Album[] CreateDiscographyArray(File file)
    {
        Album[] DiscographyArray = new Album[DiscographyLength+1];
        try(Scanner FileReader = new Scanner(file))
        {
            String temp = FileReader.nextLine();
            for(int i = 1; i <= DiscographyLength ; i++)
            {
                temp = FileReader.nextLine();
                File AlbumFile = new File(temp);
                Album album = Album.GetAlbumDetails(AlbumFile,i);
                Song[] AlbumArray = Album.CreateAlbumArray(album);
                album.SongsArray = AlbumArray;
                DiscographyArray[i] = album;
            }
        }catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return DiscographyArray;

    }

}

