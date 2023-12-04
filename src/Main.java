import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;// Used to read from the user or files
import java.lang.Integer; // used for the ParseInt method
import java.util.Random;// Used to generate a random song
//import java.util.HashMap;



/*  TO DO LIST
1)
2)
3) Implement a GUI
4)
5)
6) Add more function to do with duration (album length,avg song length in album/discography etc.)

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
                int counter;
                int MainPick;
                System.out.println("Hello ! Pick one of the options :\n0 to get a random Taylor song\n1 to look up a word \n2 to get Songs/Albums length information \n3 to get information about amount of words");
                System.out.println("4) get information about explicit songs :");
                System.out.print("----->");
                MainPick = Keyboard.nextInt();
                InputCheck(0,4,Keyboard,MainPick);
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
                        if(FileChoice == 1)//The user chose to create a summary file.
                        {
                            WordCounterInDiscographySummaryFile(word,array);
                            System.out.println("*****Summary file has been created successfully !*****");
                        }
                        else if(FileChoice == 0)//The user chose to not create a summary file.
                        {
                            counter = WordCounterInDiscography(word,array);
                            System.out.println("The word \""+word+"\" appears "+counter+" times in Taylor's discography.");
                        }
                    }
                    else if(AlbumPick != 0)// The user picked a specific album
                    {
                        int FileChoice;
                        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no : ");
                        System.out.print("----->");
                        FileChoice = Keyboard.nextInt();
                        InputCheck(0,1,Keyboard,FileChoice);
                        if(FileChoice == 1)//If the user wants to create a file
                        {
                            Album.WordCounterInAlbumSummaryFile(word,array[AlbumPick]);
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
                                counter = Song.WordCounterInSong(array[AlbumPick].SongsArray[SongChoice],word);
                                System.out.println("The word \"" + word + "\" appears " + counter + " times in the song " + array[AlbumPick].SongsArray[SongChoice].SongName + ".");

                            } else if (SongChoice == 0) //The user chose to search in the entire album
                            {
                                int AlbumCounter = Album.WordCounterInAlbum(word,array[AlbumPick]);
                                System.out.println("The word \"" +word + "\" appears " + AlbumCounter + " times in the " + array[AlbumPick].AlbumName + " album.");
                            }
                        }
                    }
                    Keyboard.nextLine();
                    FileReader.close();
                }
                else if(MainPick == 0)// The user chose to get a random taylor song
                {
                    Song RandSong = GetRandomSong(array);
                    System.out.println("The chosen song is " + RandSong.SongName + " !");
                }
                else if(MainPick == 2)// The user chose to get information about the duration of songs/albums.
                {
                    System.out.println("Would you like to get duration info (1) or song average info (2)? ");
                    System.out.println("----->");
                    int SubPick = Keyboard.nextInt();
                    InputCheck(1,2,Keyboard,SubPick);
                    if(SubPick == 1)//The user chooses to get duration information
                    {
                        System.out.println("Please choose an album or press 0 for the entire discography :");
                        Printers.PrintAlbums();
                        System.out.println("----->");
                        int choice = Keyboard.nextInt();
                        InputCheck(0,DiscographyLength,Keyboard,choice);
                        if(choice == 0)//The user chooses Duration information for the entire discography
                            DurationSummaryDiscography(array);

                        else//The user chooses to get information about a specific album
                        {
                            System.out.println("Please choose a song or press 0 for the entire album :");
                            Printers.PrintAlbumsTracks(choice);
                            int input = Keyboard.nextInt();
                            InputCheck(0,array[choice].NumOfSongs,Keyboard,input);
                            if(input == 0)//The user wants information about the whole album
                                Album.LongestAndShortestSongsInAlbum(array[choice]);

                            else//The user wants a specific song
                            {
                                Song ChosenSong = array[choice].SongsArray[input];
                                System.out.println("The duration of the song "+ChosenSong.SongName+" is "+Song.GetSongHours(ChosenSong)+"h and "+Song.GetSongSeconds(ChosenSong)+"s.");
                            }
                        }
                    }
                    else if(SubPick == 2)//The user chooses to get song average information
                    {
                        System.out.println("Please choose an album or press 0 for the entire discography :");
                        Printers.PrintAlbums();
                        System.out.println("----->");
                        int AlbumPick = Keyboard.nextInt();
                        InputCheck(0,DiscographyLength,Keyboard,AlbumPick);
                        if(AlbumPick == 0)//If the user chooses the entire discography
                            Song.AvgSongLengthInDiscography(array);
                        else//If the user chooses a specific album
                        {
                            int res = Album.AvgSongLengthInAlbum(array[AlbumPick]);
                            System.out.println("The song average in the "+array[AlbumPick].AlbumName+" album is "+res/60+"m and "+res%60+"s .");
                        }
                    }

                }
                else if(MainPick == 3)//The user chooses to get information about the number of words
                {
                    ///HashMap<String,HashMap<String,DurationAndLyrics>> FullHash = DurationAndLyrics.CreateDiscographyHash(args[0]);
                    /// System.out.println(FullHash);
                    /// System.out.println(FullHash.get(array[3].AlbumName));
                    System.out.println("Please choose an album or press 0 for the entire discography :");
                    Printers.PrintAlbums();
                    System.out.println("----->");
                    int AlbumPick = Keyboard.nextInt();
                    InputCheck(0,DiscographyLength,Keyboard,AlbumPick);
                    if(AlbumPick == 0)//If the user chooses the entire discography
                        Album.NumOfWordsInDiscography(array);
                    else//If the user chooses a specific album
                    {
                        System.out.println("Please choose a song or press 0 for the entire album :");
                        Printers.PrintAlbumsTracks(AlbumPick);
                        System.out.println("----->");
                        int SongPick = Keyboard.nextInt();
                        InputCheck(0,array[AlbumPick].NumOfSongs,Keyboard,SongPick);
                        if(SongPick == 0)//The user chooses to get information about the whole album
                        {
                            System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                            System.out.println("----->");
                            int AlbumChoice = Keyboard.nextInt();
                            InputCheck(0,1,Keyboard,AlbumChoice);
                            if(AlbumChoice == 0)//The user doesn't want to create a summary file
                                Album.NumOfWordsInAlbum(array[AlbumPick]);
                            else if(AlbumChoice == 1)//The user chooses to create a summary file
                                Album.NumOfWordsInAlbumSummary(array[AlbumPick]);
                        }
                        else//The user chooses to get information about a specific song
                            System.out.println("There are "+Song.NumOfWordsInASong(array[AlbumPick].SongsArray[SongPick])+" words in this song.");
                    }
                }
                else if(MainPick == 4)//The user chooses to get information about explicit songs.
                {
                    System.out.println("Would you like to get information about obscenities (1) or about the amount of explicit songs (2)? ");
                    System.out.println("----->");
                    int SubPick = Keyboard.nextInt();
                    InputCheck(1,2,Keyboard,SubPick);
                    if(SubPick == 2)//The user chose to get information about songs with the explicit tag.
                    {
                        System.out.println("Please choose an album or press 0 for the entire discography :");
                        Printers.PrintAlbums();
                        System.out.println("----->");
                        int AlbumPick = Keyboard.nextInt();
                        InputCheck(0,DiscographyLength,Keyboard,AlbumPick);
                        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                        System.out.println("----->");
                        int FileChoice = Keyboard.nextInt();
                        InputCheck(0,1,Keyboard,FileChoice);
                        ArrayList<ArrayList<Song>> DiscographyExplicits = CountExplicitsInDiscography(array);
                        if(AlbumPick == 0)//The user chooses o=to get information about the entire discography.
                        {
                            if(FileChoice == 0)//The user doesn't want to create a summary file
                            {
                                int TotalCounter = 0;
                                for(int i = 0 ; i < DiscographyExplicits.size() ; i++)
                                {
                                    System.out.println("There are "+DiscographyExplicits.get(i).size()+" explicit songs in the"+array[i+1].AlbumName+" album.");
                                    TotalCounter += DiscographyExplicits.get(i).size();
                                }
                                System.out.println("In Summary, there are "+TotalCounter+" explicit songs in Taylor's discography.");
                            }
                            else if(FileChoice == 1)//The user chooses to create a summary file
                            {
                                CountExplicitsInDiscographySummary(array);
                                System.out.println("*** Summary file has been created ! ***");
                            }
                        }
                        else if(AlbumPick != 0)//The user chooses a specific album
                        {
                            System.out.println("Please choose a song or press 0 for the entire album :");
                            Printers.PrintAlbumsTracks(AlbumPick);
                            System.out.println("----->");
                            int SongPick = Keyboard.nextInt();
                            InputCheck(0,array[AlbumPick].NumOfSongs,Keyboard,SongPick);
                            if(SongPick == 0)//The user chooses to get information about the entire album.
                            {
                                if(FileChoice == 0) //The user doesn't want to create a summary file
                                {
                                    ArrayList<Song> ExplicitSongs = Album.CountExplicitsInAlbum(array[AlbumPick]);
                                    int NumOfExplicits = ExplicitSongs.size();
                                    System.out.println("There are "+NumOfExplicits+" explicit songs in the"+array[AlbumPick].AlbumName+" album.");
                                    System.out.println("The songs are :");
                                    for(int i = 0; i < NumOfExplicits ; i++)
                                        System.out.println(i+1+") "+ExplicitSongs.get(i).SongName);
                                }
                                else if(FileChoice == 1)//The user chooses to create a summary file
                                {
                                    Album.CountExplicitsInAlbumSummary(array[AlbumPick]);
                                    System.out.println("*** Summary file has been created ! ***");
                                }
                            }
                            else if(SongPick != 0)//The user chooses to get information about a specific song.
                            {
                                if(array[AlbumPick].SongsArray[SongPick].IsExplicit)
                                    System.out.println("The song is explicit.");
                                else
                                    System.out.println("The song isn't explicit.");
                            }
                        }
                    }
                    else if(SubPick == 1)//The user chose to get information about obscenities in Taylor's songs.
                    {
                        System.out.println("Please choose an album or press 0 for the entire discography :");
                        Printers.PrintAlbums();
                        System.out.println("----->");
                        int AlbumPick = Keyboard.nextInt();
                        InputCheck(0,DiscographyLength,Keyboard,AlbumPick);
                        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                        System.out.println("----->");
                        int FileChoice = Keyboard.nextInt();
                        InputCheck(0,1,Keyboard,FileChoice);
                        if(AlbumPick == 0)//The user chose to get information about the entire discography.
                        {
                            if(FileChoice == 0)//The user chose to not create a summary file.
                                CountObscenitiesInDiscography(array);
                            else if(FileChoice == 1)//The user chose to create a summary file.
                                CountObscenitiesInDiscographySummary(array);
                        }
                        else if(AlbumPick != 0)//The user chose to get information about a specific album.
                        {
                            System.out.println("Please choose a song or press 0 for the entire album :");
                            Printers.PrintAlbumsTracks(AlbumPick);
                            System.out.println("----->");
                            int SongPick = Keyboard.nextInt();
                            InputCheck(0,array[AlbumPick].NumOfSongs,Keyboard,SongPick);
                            if(SongPick == 0)//The user chose to get information about the whole album.
                            {
                                if(FileChoice == 0)//The user chose to not create a summary file.
                                    System.out.println("There are "+Album.CountObscenitiesInAlbum(array[AlbumPick])+" obscenities in the "+array[AlbumPick].AlbumName+" album.");
                                else if(FileChoice == 1)//The user chose to create a summary file.
                                    Album.CountObscenitiesInAlbumSummary(array[AlbumPick]);
                            }
                            else if(SongPick != 0)//The user chose to get information about a specific song.
                            {
                                Song song = array[AlbumPick].SongsArray[SongPick];
                                System.out.println("There are "+Song.CountObscenitiesInSong(song)+" obscenities in the "+song.SongName+" song.");
                            }
                        }
                    }


                    //CreateJsonFile(file);bu
                    //System.out.println("file has been successfully created !");
                }

                System.out.println("Would you like to go again ? choose any number to continue or 0 to exit ");
                System.out.print("----->");
                IfRunAgain = Keyboard.nextInt();
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
        }

        System.out.println("I'll tell you the truth, but never goodbye");

    }






    public static void CountExplicitsInDiscographySummary(Album[] array)//This method creates a summary file with details about explicit Taylor songs.
    {
        String FileName = "Discography Explicits Summary.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for Explicit songs in Taylor's discography.\n");
            int TotalCounter = 0;
            int MaxIndex = 1;
            int MaxCounter = 0;
            for(int i = 1 ; i <= DiscographyLength ; i++)
            {
                writer.write("Album name : "+Album.IntToAlbum(i)+"\n");
                int AlbumCounter = 0;
                for(int j = 1; j <= array[i].NumOfSongs ; j++)
                {
                    if(array[i].SongsArray[j].IsExplicit)
                    {
                        writer.write(j+") "+array[i].SongsArray[j].SongName+" - Explicit\n");
                        AlbumCounter++;
                    }
                    else
                        writer.write(j+") "+array[i].SongsArray[j].SongName+" - Clean\n");
                }
                writer.write("In summary, there are "+AlbumCounter+" explicit songs in the "+array[i].AlbumName+" album.\n\n");
                TotalCounter += AlbumCounter;
                if(AlbumCounter >= MaxCounter){
                    MaxCounter = AlbumCounter;
                    MaxIndex = i;
                }
            }
                writer.write("***In summary, there are "+TotalCounter+" explicit songs in Taylor's discography.");
                writer.write("The album with the most explicit songs is "+array[MaxIndex].AlbumName+" with "+MaxCounter+" explicit songs***");
        }
        catch(IOException e)
        {
            System.out.println("An error has occurred.");
        }
    }

    public static ArrayList<ArrayList<Song>> CountExplicitsInDiscography(Album[] array)//This method creates an ArrayList containing the explicit songs in the discography. can extract size and length.
    {
        ArrayList<ArrayList<Song>> DiscographyExplicits = new ArrayList<>();
        for(int i = 1 ; i <= DiscographyLength ; i++)
        {
            ArrayList<Song> AlbumExplicits = Album.CountExplicitsInAlbum(array[i]);
            DiscographyExplicits.add(AlbumExplicits);
        }
        return DiscographyExplicits;
    }

    public static void InputCheck(int MinAllowed,int MaxAllowed,Scanner Keyboard,int input)//This method ensures the input is within range.
    {
        while(!((input >= MinAllowed)&&(input <= MaxAllowed)))
        {
            System.out.println("Invalid input, Please try again.");
            System.out.print("----->");
            input = Keyboard.nextInt();
        }
    }

    public static void DurationSummaryDiscography(Album[] array)//This method summarizes the duration information in a summary file if requested.
    {
        System.out.println("Would you like to create a summary file ? 1 for yes / 0 for no");
        Scanner Keyboard = new Scanner(System.in);
        int choice = Keyboard.nextInt();
        InputCheck(0,1, Keyboard,choice);

        int TotalCounter = 0;//counts the duration (seconds) of the entire discography
        Song TotalMaxSong = array[1].SongsArray[1];//holds the longest song on Taylor's discography
        Song TotalMinSong = array[1].SongsArray[1];//holds the shortest song on Taylor's discography

        if(choice == 1)//The user chooses to create a summary file
        {
            String FileName = "Discography Duration Summary.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
            {
                writer.write("Summary Duration for Taylor's discography in MM:SS format\n\n");
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
                            MaxSong = AlbumArray[j];
                        else if(AlbumArray[j].DurationInSec < MinSong.DurationInSec)
                            MinSong = AlbumArray[j];

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
                        TotalMaxSong = MaxSong;

                    if(MinSong.DurationInSec < TotalMinSong.DurationInSec)
                        TotalMinSong = MinSong;
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
            }
        }else if(choice == 0)//The user chooses to not create a summary file
        {
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
                        MaxSong = AlbumArray[j];
                    else if(AlbumArray[j].DurationInSec < MinSong.DurationInSec)//if the current song in shorter than the album MinSong
                        MinSong = AlbumArray[j];

                    AlbumCounter += Counter;
                }
                TotalCounter += AlbumCounter;

                if(MaxSong.DurationInSec >= TotalMaxSong.DurationInSec)//if the MaxSong on the album is longer than the TotalMaxSong
                    TotalMaxSong = MaxSong;
                else if(MinSong.DurationInSec < TotalMinSong.DurationInSec)//if the MinSong on the album is shorter than the TotalMinSong
                    TotalMinSong = MinSong;

            }
            int TotalHours = TotalCounter / 3600;
            int TotalMinutes = (TotalCounter - TotalHours*3600)/60;
            int TotalSeconds = TotalCounter % 60;
            System.out.println("\n***In summary, Taylor's entire discography is "+TotalHours+"h, "+TotalMinutes+"m and "+TotalSeconds+"s long.");
            System.out.println("The Longest Taylor song is "+TotalMaxSong.SongName+" and is "+Song.GetSongHours(TotalMaxSong)+"m and "+Song.GetSongSeconds(TotalMaxSong)+"s long." );
            System.out.println("The Shortest Taylor song is "+TotalMinSong.SongName+" and is "+Song.GetSongHours(TotalMinSong)+"m and "+Song.GetSongSeconds(TotalMinSong)+"s long." );
        }
    }
    public static Song GetRandomSong(Album[] array)//This method generates a random Taylor song.
    {
        Random rand = new Random();
        int album = rand.nextInt(1,DiscographyLength+1);
        int song = rand.nextInt(1,array[album].NumOfSongs+1);
        return array[album].SongsArray[song];
    }

    public static int WordCounterInDiscography(String word,Album[] array)//This method counts the number of times a word appears in Taylor's entire discography
    {
        int TotalCounter = 0;
        for(int i = 1; i <= DiscographyLength ; i++)
        {
            int counter;
            counter = Album.WordCounterInAlbum(word,array[i]);
            TotalCounter += counter;
        }

        return TotalCounter;
    }

    public static void CountObscenitiesInDiscography(Album[] array)//This method counts the number of obscenities in Taylor's discography and prints the result.
    {
        int TotalCounter = 0;
        Album MaxAlbum = new Album();
        int MaxCount = 0;
        for(int i = 1 ; i <= DiscographyLength ; i++)
        {
            int AlbumCounter = Album.CountObscenitiesInAlbum(array[i]);
            if(AlbumCounter > MaxCount)
            {
                MaxAlbum = array[i];
                MaxCount = AlbumCounter;
            }
            TotalCounter += AlbumCounter;
        }
        System.out.println("There is a total of "+TotalCounter+" obscenities in Taylor's discography. The Album with the highest count is "+MaxAlbum.AlbumName);
    }

    public static void CountObscenitiesInDiscographySummary(Album[] array)//This method counts the obscenities in Tyalor's discography and produces a file with the details.
    {
        String FileName = "Obscenities - Full Summary.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for obscenities in Taylor's discography.\n");
            int TotalCounter = 0;
            Album MaxAlbum = new Album();
            int MaxAlbumCount = 0;
            Song MaxSong = new Song();
            int MaxSongCount = 0;
            for(int i = 1; i <= DiscographyLength ; i++)
            {
                Song[] AlbumArray = array[i].SongsArray;
                writer.write("Album name : "+array[i].AlbumName+"\n");
                int AlbumCounter = 0;
                for(int j = 1; j <= array[i].NumOfSongs ; j++)
                {
                    int counter = Song.CountObscenitiesInSong(array[i].SongsArray[j]);
                    writer.write(j+") "+AlbumArray[j].SongName+" : "+counter+ " times\n");
                    if(counter > MaxSongCount)
                    {
                        MaxSong = array[i].SongsArray[j];
                        MaxSongCount = counter;
                    }
                    AlbumCounter += counter;
                }
                writer.write("In total, There are "+AlbumCounter+" obscenities in the "+array[i].AlbumName+" album.\n\n");
                if(AlbumCounter > MaxAlbumCount)
                {
                    MaxAlbum = array[i];
                    MaxAlbumCount = AlbumCounter;
                }
                TotalCounter += AlbumCounter;
            }
            writer.write("***** In Total, There are "+TotalCounter +" obscenities in Taylor's Entire Discography *****");
            System.out.println("*****Summary file has been created successfully !*****");
        }
        catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
    public static void WordCounterInDiscographySummaryFile(String word,Album[] array)//This method counts the appearances of a given word in Taylor's discography and produces a file with the details.
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
                    int counter = Song.WordCounterInSong(AlbumArray[j],word);
                    writer.write(j+") "+AlbumArray[j].SongName+" : "+counter+ " times\n");
                    AlbumCounter += counter;
                }
                writer.write("In total, The word appears "+AlbumCounter+" times in the "+array[i].AlbumName+" album.\n\n");
                TotalCounter += AlbumCounter;
            }
            writer.write("***** In Total, The word appears "+TotalCounter +" times in Taylor's Entire Discography *****");
        }
        catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    public static Album[] CreateDiscographyArray(File file)//This method creates the data structure with which the program mainly accesses information.
    {
        Album[] DiscographyArray = new Album[DiscographyLength+1];
        try(Scanner FileReader = new Scanner(file))
        {
            @SuppressWarnings("UnusedAssignment")
            String temp = FileReader.nextLine();
            for(int i = 1; i <= DiscographyLength ; i++)
            {
                temp = FileReader.nextLine();
                File AlbumFile = new File(temp);
                Album album = Album.GetAlbumDetails(AlbumFile,i);
                album.SongsArray = Album.CreateAlbumArray(album);
                DiscographyArray[i] = album;
            }
        }catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return DiscographyArray;

    }

    public static void CreateJsonFile(File file)//This method created a JSON type file of the database.
    {
        String FileName = "Test JSON file.json";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("{\n");
            writer.write("\"Albums\":\n");
            Scanner reader = new Scanner(file);
            String temp = reader.nextLine();
            for(int i = 1 ; i <= DiscographyLength ; i++)
            {
                String AlbumPath = reader.nextLine();
                File albumFile = new File(AlbumPath);
                Scanner AlbumReader = new Scanner(albumFile);
                temp = AlbumReader.nextLine();
                temp = temp.substring(14);
                int numOfSongs = Integer.parseInt(temp);
                writer.write("  {\n");
                writer.write("      \"AlbumName\": \""+Album.IntToAlbum(i)+"\",\n");
                writer.write("      \"NumOfSongs\": "+numOfSongs+",\n");
                writer.write("      \"AlbumNumber\": "+i+",\n");
                writer.write("      \"Path\": \""+AlbumPath+"\",\n");
                writer.write("      \"SongsArray\": [ \n");
                for(int j = 1; j <= numOfSongs ; j++)
                {
                    String SongPath = AlbumReader.nextLine();
                    File SongFile = new File(SongPath);
                    Scanner SongReader = new Scanner(SongFile);
                    String SongTemp = SongReader.nextLine();
                    writer.write("              {\n");
                    writer.write("              \"SongName\": \""+SongTemp+"\",\n");
                    writer.write("              \"SongPath\": \""+SongPath+"\",\n");
                    String Duration = SongReader.nextLine();
                    String minutes = Duration.substring(0,2);
                    int DurationInSec = (Integer.parseInt(minutes))*60;
                    String seconds = Duration.substring(3);
                    DurationInSec += Integer.parseInt(seconds);//calculate the duration of the song
                    writer.write("              \"DurationInSeconds\": "+DurationInSec+",\n");
                    writer.write("              },\n\n");
                    SongReader.close();
                }
                writer.write("  ]\n\n       },\n\n");
                AlbumReader.close();
            }
            reader.close();
        }catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }

    }
}

