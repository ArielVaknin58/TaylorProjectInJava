//import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;// Used to read from the user or files
import java.lang.Integer; // used for the ParseInt method
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class


/*  TO DO LIST
1)  Implement a GUI
2)
3)  ** Comparisons between the artists **
4)
5)
6)

 */

public class Main
{
    private static int NumberOfArtists;
    public static int getNumberOfArtists() {return NumberOfArtists;}
    public static void setNumberOfArtists(int numberOfArtists) {NumberOfArtists = numberOfArtists;}
    public static void main(String[] args)
    {

        int IfRunAgain = 1;

        try(Scanner Keyboard = new Scanner(System.in))
        {
            File file = new File(args[0]);
            Scanner Reader = new Scanner(file);
            String temp = Reader.nextLine();
            temp = temp.substring(16);
            setNumberOfArtists(Integer.parseInt(temp));
            Artist[] ArtistsArray = new Artist[Main.getNumberOfArtists()+1];
            for(int j = 1 ; j <= getNumberOfArtists() ; j++)
            {
                temp = Reader.nextLine();
                File Artistfile = new File(temp);
                Artist artist = new Artist(j-1,Artistfile);
                ArtistsArray[j] = artist;
            }
                while(IfRunAgain != 0) // Main loop. repeats until its requested to exit
                {
                    int counter;
                    int MainPick;
                    PrintWelcomeBanner();
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = myDateObj.format(myFormatObj);
                    System.out.println(formattedDate);

                    System.out.println("Welcome ! Please Choose your artist :\n1) Taylor Swift \n2) Madison Beer");
                    System.out.print("----->");
                    int ArtistPick = Keyboard.nextInt();
                    InputCheck(0,1,Keyboard,ArtistPick);
                    Artist ChosenArtist = ArtistsArray[ArtistPick];
                    System.out.println("Pick one of the options :\n0) to get a random "+ArtistsArray[ArtistPick].getArtistName()+" song\n1) to look up a word \n2) to get Songs/Albums length information \n3) to get information about amount of words");
                    System.out.println("4) get information about explicit songs");
                    System.out.println("5) **If running the program in a new PC for the first time**");
                    System.out.println("6) Compare between artists ");
                    System.out.print("----->");
                    MainPick = Keyboard.nextInt();
                    InputCheck(0,6,Keyboard,MainPick);
                    if(MainPick == 1)//The user picked to look up a word
                    {
                        System.out.println("\nPlease enter the word you'd like to search : ");
                        System.out.print("----->");
                        String word = Keyboard.next();

                        //Scanner FileReader = new Scanner(args[0]);
                        System.out.println("In which album would you like to search ? choose 1-" + ArtistsArray[ArtistPick].getNumOfAlbums() + " or 0 for the entire discography :");
                        System.out.print("----->");
                        ChosenArtist.PrintAlbums();
                        int AlbumPick = Keyboard.nextInt();
                        InputCheck(0, ArtistsArray[ArtistPick].getNumOfAlbums(), Keyboard,AlbumPick);
                        if (AlbumPick == 0)// The user picked to search in the entire discography
                        {
                            System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no : ");
                            int FileChoice = Keyboard.nextInt();
                            if(FileChoice == 1)//The user chose to create a summary file.
                            {
                                ChosenArtist.WordCounterInDiscographySummaryFile(word);
                                System.out.println("*****Summary file has been created successfully !*****");
                            }
                            else if(FileChoice == 0)//The user chose to not create a summary file.
                            {
                                counter = ChosenArtist.WordCounterInDiscography(word);
                                System.out.println("The word \""+word+"\" appears "+counter+" times in "+ArtistsArray[ArtistPick].getArtistName()+"'s discography.");
                            }
                        }
                        else// The user picked a specific album
                        {
                            int FileChoice;
                            System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no : ");
                            System.out.print("----->");
                            FileChoice = Keyboard.nextInt();
                            InputCheck(0,1,Keyboard,FileChoice);
                            if(FileChoice == 1)//If the user wants to create a file
                            {
                                ArtistsArray[ArtistPick].getDiscography()[AlbumPick].WordCounterInAlbumSummaryFile(word);
                                System.out.println("*** Summary file has been created ! ***");
                            }
                            else //if the user doesn't want to create a file
                            {
                                System.out.println("In which song would you like to search ? pick 1-" + ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetNumOfSongs() + " or 0 for the entire album :");
                                ChosenArtist.PrintAlbumsTracks(AlbumPick);
                                System.out.print("----->");
                                int SongChoice = Keyboard.nextInt();
                                InputCheck(0,ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetNumOfSongs(),Keyboard,SongChoice);
                                if (SongChoice > 0) //The user picked a specific song
                                {
                                    counter = ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetSongsArray()[SongChoice].WordCounterInSong(word);
                                    System.out.println("The word \"" + word + "\" appears " + counter + " times in the song " + ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetSongsArray()[SongChoice].GetSongName() + ".");

                                } else if (SongChoice == 0) //The user chose to search in the entire album
                                {
                                    int AlbumCounter = ArtistsArray[ArtistPick].getDiscography()[AlbumPick].WordCounterInAlbum(word);
                                    System.out.println("The word \"" +word + "\" appears " + AlbumCounter + " times in the " + ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetAlbumName() + " album.");
                                }
                            }
                        }
                        Keyboard.nextLine();
                        //FileReader.close();
                    }
                    else if(MainPick == 0)// The user chose to get a random taylor song
                    {
                        Song RandSong = ChosenArtist.GetRandomSong();
                        System.out.println("The chosen song is " + RandSong.GetSongName() + " !");
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
                            ChosenArtist.PrintAlbums();
                            System.out.println("----->");
                            int choice = Keyboard.nextInt();
                            InputCheck(0, ChosenArtist.getNumOfAlbums(),Keyboard,choice);
                            if(choice == 0)//The user chooses Duration information for the entire discography
                            {
                                System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                                System.out.println("----->");
                                int FileChoice = Keyboard.nextInt();
                                InputCheck(0,1,Keyboard,FileChoice);
                                if(FileChoice == 0)//The user chose to not create a file
                                {
                                    Song[] res = ChosenArtist.DurationOfDiscography();
                                    int TotalHours = ArtistsArray[ArtistPick].getDiscographyDurationInSec() / 3600;
                                    int TotalMinutes = (ArtistsArray[ArtistPick].getDiscographyDurationInSec() - TotalHours*3600)/60;
                                    int TotalSeconds = ArtistsArray[ArtistPick].getDiscographyDurationInSec() % 60;
                                    System.out.println("\n***In summary, "+ArtistsArray[ArtistPick].getArtistName()+"'s entire discography is "+TotalHours+"h, "+TotalMinutes+"m and "+TotalSeconds+"s long.");
                                    System.out.println("The Longest "+ArtistsArray[ArtistPick].getArtistName()+" song is "+res[1].GetSongName()+" and is "+Song.GetSongMinutes(res[1])+"m and "+Song.GetSongSeconds(res[1])+"s long." );
                                    System.out.println("The Shortest "+ArtistsArray[ArtistPick].getArtistName()+" song is "+res[0].GetSongName()+" and is "+Song.GetSongMinutes(res[0])+"m and "+Song.GetSongSeconds(res[0])+"s long." );
                                }
                                else//The user chose to create a summary file
                                    ChosenArtist.DurationDiscographySummary();

                            }
                            else//The user chooses to get information about a specific album
                            {
                                System.out.println("Please choose a song or press 0 for the entire album :");
                                ChosenArtist.PrintAlbumsTracks(choice);
                                int input = Keyboard.nextInt();
                                InputCheck(0,ArtistsArray[ArtistPick].getDiscography()[choice].GetNumOfSongs(),Keyboard,input);
                                if(input == 0)//The user wants information about the whole album
                                    ArtistsArray[ArtistPick].getDiscography()[choice].LongestAndShortestSongsInAlbum();

                                else//The user wants a specific song
                                {
                                    Song ChosenSong = ArtistsArray[ArtistPick].getDiscography()[choice].GetSongsArray()[input];
                                    System.out.println("The duration of the song "+ChosenSong.GetSongName()+" is "+Song.GetSongMinutes(ChosenSong)+"h and "+Song.GetSongSeconds(ChosenSong)+"s.");
                                }
                            }
                        }
                        else if(SubPick == 2)//The user chooses to get song average information
                        {
                            System.out.println("Please choose an album or press 0 for the entire discography :");
                            ChosenArtist.PrintAlbums();
                            System.out.println("----->");
                            int AlbumPick = Keyboard.nextInt();
                            InputCheck(0,ChosenArtist.getNumOfAlbums(),Keyboard,AlbumPick);
                            if(AlbumPick == 0)//If the user chooses the entire discography
                                ChosenArtist.AvgSongLengthInDiscography();
                            else//If the user chooses a specific album
                            {
                                int res = ArtistsArray[ArtistPick].getDiscography()[AlbumPick].AvgSongLengthInAlbum();
                                System.out.println("The song average in the "+ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetAlbumName()+" album is "+res/60+"m and "+res%60+"s .");
                            }
                        }

                    }
                    else if(MainPick == 3)//The user chooses to get information about the number of words
                    {
                        System.out.println("Please choose an album or press 0 for the entire discography :");
                        ChosenArtist.PrintAlbums();
                        System.out.println("----->");
                        int AlbumPick = Keyboard.nextInt();
                        InputCheck(0,ChosenArtist.getNumOfAlbums(),Keyboard,AlbumPick);
                        if(AlbumPick == 0)//If the user chooses the entire discography
                        {
                            System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                            System.out.println("----->");
                            int FileChoice = Keyboard.nextInt();
                            Main.InputCheck(0,1,Keyboard,FileChoice);
                            if(FileChoice == 0)//The user doesn't want to create a summary file
                                System.out.println("There are "+ChosenArtist.NumOfWordsInDiscography()+" words in "+ArtistsArray[ArtistPick]+"'s entire discography.");
                            else if(FileChoice == 1)//The user wants to create a summary file
                                ChosenArtist.NumOfWordsInDiscographySummary();
                        }
                        else//If the user chooses a specific album
                        {
                            System.out.println("Please choose a song or press 0 for the entire album :");
                            ChosenArtist.PrintAlbumsTracks(AlbumPick);
                            System.out.println("----->");
                            int SongPick = Keyboard.nextInt();
                            InputCheck(0,ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetNumOfSongs(),Keyboard,SongPick);
                            if(SongPick == 0)//The user chooses to get information about the whole album
                            {
                                System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                                System.out.println("----->");
                                int FileChoice = Keyboard.nextInt();
                                InputCheck(0,1,Keyboard,FileChoice);
                                if(FileChoice == 0)//The user doesn't want to create a summary file
                                {
                                    int AlbumCounter = ArtistsArray[ArtistPick].getDiscography()[AlbumPick].NumOfWordsInAlbum();
                                    System.out.println("There are "+AlbumCounter+" words in The "+ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetAlbumName()+" album.");
                                }
                                else if(FileChoice == 1)//The user chooses to create a summary file
                                    Album.NumOfWordsInAlbumSummary(ArtistsArray[ArtistPick].getDiscography()[AlbumPick]);
                            }
                            else//The user chooses to get information about a specific song
                                System.out.println("There are "+ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetSongsArray()[SongPick].NumOfWordsInASong()+" words in this song.");
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
                            ChosenArtist.PrintAlbums();
                            System.out.println("----->");
                            int AlbumPick = Keyboard.nextInt();
                            InputCheck(0,ChosenArtist.getNumOfAlbums(),Keyboard,AlbumPick);
                            System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                            System.out.println("----->");
                            int FileChoice = Keyboard.nextInt();
                            InputCheck(0,1,Keyboard,FileChoice);
                            ArrayList<ArrayList<Song>> DiscographyExplicits = ChosenArtist.CountExplicitsInDiscography();
                            if(AlbumPick == 0)//The user chooses to get information about the entire discography.
                            {
                                if(FileChoice == 0)//The user doesn't want to create a summary file
                                    System.out.println("In Summary, there are "+ArtistsArray[ArtistPick].getNumOfExplicits()+" explicit songs in "+ArtistsArray[ArtistPick].getArtistName()+"'s discography.");
                                else if(FileChoice == 1)//The user chooses to create a summary file
                                    ChosenArtist.CountExplicitsInDiscographySummary();

                            }
                            else //The user chooses a specific album
                            {
                                System.out.println("Please choose a song or press 0 for the entire album :");
                                ChosenArtist.PrintAlbumsTracks(AlbumPick);
                                System.out.println("----->");
                                int SongPick = Keyboard.nextInt();
                                InputCheck(0,ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetNumOfSongs(),Keyboard,SongPick);
                                if(SongPick == 0)//The user chooses to get information about the entire album.
                                {
                                    if(FileChoice == 0) //The user doesn't want to create a summary file
                                    {
                                        ArrayList<Song> ExplicitSongs = ArtistsArray[ArtistPick].getDiscography()[AlbumPick].CountExplicitsInAlbum();
                                        int NumOfExplicits = ExplicitSongs.size();
                                        System.out.println("There are "+NumOfExplicits+" explicit songs in the"+ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetAlbumName()+" album.");
                                        System.out.println("The songs are :");
                                        for(int i = 0; i < NumOfExplicits ; i++)
                                            System.out.println(i+1+") "+ExplicitSongs.get(i).GetSongName());
                                    }
                                    else if(FileChoice == 1)//The user chooses to create a summary file
                                    {
                                        ArtistsArray[ArtistPick].getDiscography()[AlbumPick].CountExplicitsInAlbumSummary();
                                        System.out.println("*** Summary file has been created ! ***");
                                    }
                                }
                                else//The user chooses to get information about a specific song.
                                {
                                    if(ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetSongsArray()[SongPick].GetIsExplicit())
                                        System.out.println("The song is explicit.");
                                    else
                                        System.out.println("The song isn't explicit.");
                                }
                            }
                        }
                        else if(SubPick == 1)//The user chose to get information about obscenities in Taylor's songs.
                        {
                            System.out.println("Please choose an album or press 0 for the entire discography :");
                            ChosenArtist.PrintAlbums();
                            System.out.println("----->");
                            int AlbumPick = Keyboard.nextInt();
                            InputCheck(0,ChosenArtist.getNumOfAlbums(),Keyboard,AlbumPick);
                            System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                            System.out.println("----->");
                            int FileChoice = Keyboard.nextInt();
                            InputCheck(0,1,Keyboard,FileChoice);
                            if(AlbumPick == 0)//The user chose to get information about the entire discography.
                            {
                                if(FileChoice == 0)//The user chose to not create a summary file.
                                {
                                    int TotalCounter = ChosenArtist.CountObscenitiesInDiscography();
                                    System.out.println("There is a total of "+TotalCounter+" obscenities in "+ArtistsArray[ArtistPick].getArtistName()+"'s discography.");

                                }
                                else if(FileChoice == 1)//The user chose to create a summary file.
                                    ChosenArtist.CountObscenitiesInDiscographySummary();
                            }
                            else //The user chose to get information about a specific album.
                            {
                                System.out.println("Please choose a song or press 0 for the entire album :");
                                ChosenArtist.PrintAlbumsTracks(AlbumPick);
                                System.out.println("----->");
                                int SongPick = Keyboard.nextInt();
                                InputCheck(0,ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetNumOfSongs(),Keyboard,SongPick);
                                if(SongPick == 0)//The user chose to get information about the whole album.
                                {
                                    if(FileChoice == 0)//The user chose to not create a summary file.
                                        System.out.println("There are "+ArtistsArray[ArtistPick].getDiscography()[AlbumPick].CountObscenitiesInAlbum()+" obscenities in the "+ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetAlbumName()+" album.");
                                    else if(FileChoice == 1)//The user chose to create a summary file.
                                        ArtistsArray[ArtistPick].getDiscography()[AlbumPick].CountObscenitiesInAlbumSummary();
                                }
                                else//The user chose to get information about a specific song.
                                {
                                    Song song = ArtistsArray[ArtistPick].getDiscography()[AlbumPick].GetSongsArray()[SongPick];
                                    System.out.println("There are "+song.CountObscenitiesInSong()+" obscenities in the "+song.GetSongName()+" song.");
                                }
                            }
                        }

                    }
                    else if(MainPick == 5)//The user chose to change the files path
                    {
                        String bloop;
                        System.out.println("**How to start**");
                        System.out.println("1) Copy and paste the library (album folders with all the songs + Album'sReadMe file ) to a desired destination ");
                        System.out.println("PRESS ANY KEY WHEN DONE");
                        bloop = Keyboard.next();
                        System.out.println("2) Use the *local* path of the Album'sReadMe as a parameter to the program (Even though it contains old paths), right click on the file and choose \"copy as path\"");
                        System.out.println("PRESS ANY KEY WHEN DONE");
                        bloop = Keyboard.next();
                        System.out.println("3) Provide the copied folder path without quotes (try to avoid hebrew characters !) ");
                        System.out.print("----->");
                        bloop = Keyboard.next();
                        File newfile = new File(args[ArtistPick]);
                        String NewMainPath = ChangeProgramPath(bloop, newfile, ArtistPick);
                        System.out.println("***Files successfully created !***");
                        System.out.println("Please close and re-open the program !");

                    }
                    else if(MainPick == 6)//The user chose to compare between two artists
                    {
                        System.out.println("Please choose an aspect to compare :\n0) Every aspect \n1) Number of appearances of a word \n2) Length information \n3) Number of words");
                        System.out.println("4) Number of explicit songs \n5) Number of obscenities ");
                        System.out.print("----->");
                        int AspectPick = Keyboard.nextInt();
                        InputCheck(0,5,Keyboard,AspectPick);
                        if(AspectPick == 0)//The user chose to compare every aspect
                        {
                            System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
                            System.out.println("----->");
                            int FileChoice = Keyboard.nextInt();
                            InputCheck(0,1,Keyboard,FileChoice);
                            if(FileChoice == 0)//The user chose to not produce a summary file
                                Compare2Artists(ArtistsArray[0],ArtistsArray[1]);
                            else//The user chose to create a summary file
                                Compare2ArtistsSummary(ArtistsArray[0],ArtistsArray[1]);
                        }
                        else if(AspectPick == 1)//The user chose to compare the number of appearances of a given word
                        {
                            System.out.println("\nPlease enter the word you'd like to search : ");
                            System.out.print("----->");
                            String word = Keyboard.next();
                            int TaylorCounter = ArtistsArray[0].WordCounterInDiscography(word);
                            int MadisonCounter = ArtistsArray[1].WordCounterInDiscography(word);
                            System.out.println("The word "+word+" appears "+TaylorCounter+" times in Taylor's discography, and"+MadisonCounter+" times in Madison's discography.");
                        }
                        else if(AspectPick == 2)//The user chose to get length information
                        {
                            int TaylorDuration = ArtistsArray[0].getDiscographyDurationInSec();
                            int MadisonDuration = ArtistsArray[1].getDiscographyDurationInSec();
                            System.out.println("Taylor's discography length is "+ArtistsArray[0].getDiscographyDurationHours()+"h "+ArtistsArray[0].getDiscographyDurationMin()+"m and "+ArtistsArray[0].getDiscographyDurationSec()+"s ");
                            System.out.println("Madison's discography length is "+ArtistsArray[1].getDiscographyDurationHours()+"h "+ArtistsArray[1].getDiscographyDurationMin()+"m and "+ArtistsArray[1].getDiscographyDurationSec()+"s ");
                        }
                        else if(AspectPick == 3)//The user chooses the compare the number of words
                        {
                            int TaylorWordsCounter = ArtistsArray[0].NumOfWordsInDiscography();
                            int MadisonWordCounter = ArtistsArray[1].NumOfWordsInDiscography();
                            System.out.println("There are "+TaylorWordsCounter+" words in "+ArtistsArray[0].getArtistName()+"'s discography");
                            System.out.println("There are "+MadisonWordCounter+" words in "+ArtistsArray[1].getArtistName()+"'s discography");
                        }
                        else if(AspectPick == 4)//The user wants to compare the number of explicit songs
                        {
                            System.out.println("There are "+ArtistsArray[0].getNumOfExplicits()+" explicit songs in "+ArtistsArray[0].getArtistName()+"'s discography.");
                            System.out.println("There are "+ArtistsArray[1].getNumOfExplicits()+" explicit songs in "+ArtistsArray[1].getArtistName()+"'s discography.");
                        }
                        else if(AspectPick == 5)//The user chooses the compare the total number of obscenities
                        {
                            int TaylorObscenitiesCounter = ArtistsArray[0].CountObscenitiesInDiscography();
                            int MadisonObscenitiesCounter = ArtistsArray[1].CountObscenitiesInDiscography();
                            System.out.println("There are "+TaylorObscenitiesCounter+" obscenities in "+ArtistsArray[0].getArtistName()+"'s discography.");
                            System.out.println("There are "+MadisonObscenitiesCounter+" obscenities in "+ArtistsArray[1].getArtistName()+"'s discography.");
                        }
                    }

                    System.out.println("Would you like to go again ? choose any number to continue or 0 to exit ");
                    System.out.print("----->");
                    IfRunAgain = Keyboard.nextInt();
                }
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }

        System.out.println("I'll tell you the truth, but never goodbye");

    }





    public static void Compare2ArtistsSummary(Artist artist1,Artist artist2)
    {
        String FileName = artist1.getArtistName()+" and "+artist2.getArtistName()+" Full Comparison.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("                          Comparison File for "+artist1.getArtistName()+" and "+artist2.getArtistName()+"\n");
            writer.write("1) Number on albums :\n "+artist1.getArtistName()+" : "+artist1.getNumOfAlbums()+"                "+artist2.getArtistName()+" : "+artist2.getNumOfAlbums()+"\n" );
            writer.write("2) Number on songs :\n "+artist1.getArtistName()+" : "+artist1.getNumOfSongs()+"              "+artist2.getArtistName()+" : "+artist2.getNumOfSongs()+"\n" );
            writer.write("3) Number on Explicit songs :\n "+artist1.getArtistName()+" : "+artist1.getNumOfExplicits()+"             "+artist2.getArtistName()+" : "+artist2.getNumOfExplicits()+"\n" );
            writer.write("4) Number of obscenities :\n "+artist1.getArtistName()+" : "+artist1.CountObscenitiesInDiscography()+"                "+artist2.getArtistName()+" : "+artist2.CountObscenitiesInDiscography()+"\n" );
            writer.write("5) Length information :\n "+artist1.getArtistName()+" : "+artist1.getDiscographyDurationHours()+"h "+artist1.getDiscographyDurationMin()+"m "+artist1.getDiscographyDurationSec()+"s                "+artist2.getArtistName()+" : "+artist2.getDiscographyDurationHours()+"h "+artist2.getDiscographyDurationMin()+"m "+artist2.getDiscographyDurationSec()+"s \n");
            Song[] res1 = artist1.DurationOfDiscography();
            Song[] res2 = artist2.DurationOfDiscography();
            writer.write("6) Longest song :\n "+artist1.getArtistName()+" : "+res1[0].GetSongName()+"  "+(res1[0].GetDurationInSec()/60)+"m "+(res1[0].GetDurationInSec()%60)+"s             "+artist2.getArtistName()+" :"+res2[0].GetSongName() +" "+(res2[0].GetDurationInSec()/60)+"m "+(res2[0].GetDurationInSec()%60)+"s\n");
            writer.write("7) Shortest song :\n "+artist1.getArtistName()+" : "+res1[1].GetSongName() +" "+(res1[1].GetDurationInSec()/60)+"m "+(res1[1].GetDurationInSec()%60)+"s             "+artist2.getArtistName()+" : "+res2[1].GetSongName() +" "+(res2[1].GetDurationInSec()/60)+"m "+(res2[1].GetDurationInSec()%60)+"s\n");
            System.out.println("*** Summary file has been created ! ***");
        }
        catch(IOException e) {System.out.println("An error has occurred.");}

    }

    public static void Compare2Artists(Artist artist1, Artist artist2) throws Exception
    {
        System.out.print("              Comparison File for "+artist1.getArtistName()+" and "+artist2.getArtistName()+"\n");
        System.out.print("1) Number on albums :\n "+artist1.getArtistName()+" : "+artist1.getNumOfAlbums()+"        "+artist2.getArtistName()+" : "+artist2.getNumOfAlbums()+"\n" );
        System.out.print("2) Number on songs :\n "+artist1.getArtistName()+" : "+artist1.getNumOfSongs()+"        "+artist2.getArtistName()+" : "+artist2.getNumOfSongs()+"\n" );
        System.out.print("3) Number on Explicit songs :\n "+artist1.getArtistName()+" : "+artist1.getNumOfExplicits()+"        "+artist2.getArtistName()+" : "+artist2.getNumOfExplicits()+"\n" );
        System.out.print("4) Number of obscenities :\n "+artist1.getArtistName()+" : "+artist1.CountObscenitiesInDiscography()+"        "+artist2.getArtistName()+" : "+artist2.CountObscenitiesInDiscography()+"\n" );
        System.out.print("5) Length information :\n "+artist1.getArtistName()+" : "+artist1.getDiscographyDurationHours()+"h "+artist1.getDiscographyDurationMin()+"m "+artist1.getDiscographyDurationSec()+"s          "+artist2.getArtistName()+" : "+artist2.getDiscographyDurationHours()+"h "+artist2.getDiscographyDurationMin()+"m "+artist2.getDiscographyDurationSec()+"s \n");
        Song[] res1 = artist1.DurationOfDiscography();
        Song[] res2 = artist2.DurationOfDiscography();
        System.out.print("6) Longest song :\n "+artist1.getArtistName()+" : "+res1[0].GetSongName()+"  "+(res1[0].GetDurationInSec()/60)+"m "+(res1[0].GetDurationInSec()%60)+"s             "+artist2.getArtistName()+" :"+res2[0].GetSongName() +" "+(res2[0].GetDurationInSec()/60)+"m "+(res2[0].GetDurationInSec()%60)+"s\n");
        System.out.print("7) Shortest song :\n "+artist1.getArtistName()+" : "+res1[1].GetSongName() +" "+(res1[1].GetDurationInSec()/60)+"m "+(res1[1].GetDurationInSec()%60)+"s             "+artist2.getArtistName()+" : "+res2[1].GetSongName() +" "+(res2[1].GetDurationInSec()/60)+"m "+(res2[1].GetDurationInSec()%60)+"s\n");
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

    public static Album[] CreateDiscographyArray(File file,int ArtistNum) throws Exception//This method creates the data structure with which the program mainly accesses information.
    {
        Album[] DiscographyArray = null;

            Scanner FileReader = new Scanner(file);
            String temp = FileReader.nextLine();
            temp = temp.substring(15);
            int DiscographyLength = Integer.parseInt(temp);
            DiscographyArray = new Album[DiscographyLength+1];

            for(int i = 1; i <= DiscographyLength ; i++)
            {
                temp = FileReader.nextLine();
                File AlbumFile = new File(temp);
                Album album = Album.GetAlbumDetails(AlbumFile,i,ArtistNum);
                album.SetSongsArray(Album.CreateAlbumArray(album,ArtistNum));
                DiscographyArray[i] = album;
            }

        FileReader.close();
        return DiscographyArray;
    }

    public static String ChangeProgramPath(String NewPath,File OriginalFile,int ArtistNum)
    {//Words only for taylor currently
        try {
            Scanner OriginalReader = new Scanner(OriginalFile);
            BufferedWriter writer = new BufferedWriter(new FileWriter(NewPath+"\\AlbumsReadMe's.txt"));
            String temp = OriginalReader.nextLine();
            temp = temp.substring(15);
            int NumOfAlbums = Integer.parseInt(temp);
            writer.write("num of albums : "+NumOfAlbums+"\n");
            for(int i = 1 ; i <= NumOfAlbums ; i++)
            {
                if(i != 3)
                    writer.write(NewPath+"\\"+Album.IntToAlbum(i,ArtistNum)+"\\"+Album.IntToAlbum(i,ArtistNum)+"ReadMe.txt\n");
                else
                    writer.write(NewPath+"\\SpeakNow\\SpeakNowReadMe.txt\n");
            }
            writer.close();
            OriginalReader.close();
            Scanner OriginalScanner = new Scanner(OriginalFile);
            File CopyFile = new File(NewPath+"\\AlbumsReadMe's.txt");
            Scanner CopyScanner = new Scanner(CopyFile);
            temp = OriginalScanner.nextLine();
            temp = CopyScanner.nextLine();
            for(int i = 1 ; i <= NumOfAlbums ; i++)
            {
                String newTemp = CopyScanner.nextLine();
                BufferedWriter Writer = new BufferedWriter(new FileWriter(newTemp));
                newTemp = OriginalScanner.nextLine();
                File albumFile = new File(newTemp);
                Scanner AlbumTracksReader = new Scanner(albumFile);
                temp = AlbumTracksReader.nextLine();
                temp = temp.substring(14);
                int NumOfTracks = Integer.parseInt(temp);
                Writer.write("num of songs : "+NumOfTracks+"\n");
                for(int j = 1 ; j <= NumOfTracks ; j++)
                {
                    if(i == 3)
                        Writer.write(NewPath+"\\SpeakNow\\SpeakNow"+j+".txt\n");
                    else if (i == 5)
                        Writer.write(NewPath+"\\1989\\1989-"+j+".txt\n");
                    else
                        Writer.write(NewPath+"\\"+Album.IntToAlbum(i,ArtistNum)+"\\"+Album.IntToAlbum(i,ArtistNum)+j+".txt\n");
                }
                Writer.close();
                AlbumTracksReader.close();
            }
        }catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}

        return (NewPath+"\\NewMainReadMe.txt");
    }


    public static void PrintWelcomeBanner()// This function prints the opening welcome banner
    {
        System.out.print("***************************************************************************************************************\n");
        System.out.print("***************************************************************************************************************\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**            *********    *****   ****     ****    ***       *******  *******     **  ******                **\n");
        System.out.print("**               **       **   **     ***  ***      ***       **   **  **  **    **    **                    **\n");
        System.out.print("**               **      **     **      ***         ***       **   **  ****            ******                **\n");
        System.out.print("**               **     **  ***  **     ***         ***       **   **  **  ***             **                **\n");
        System.out.print("**               **    **         **    ***         *******   *******  **   ***        ******                **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**              ***        ***  *****   *******   ******   **  ******  ***   **                              **\n");
        System.out.print("**               ***      ***   **      **  **    **           **  **  ** *  **                              **\n");
        System.out.print("**                ***    ***    *****   ****      ******   **  **  **  **  * **                              **\n");
        System.out.print("**                 ***  ***     **      **  ***       **   **  **  **  **   ***                              **\n");
        System.out.print("**                  *****       *****   **   ***  ******   **  ******  **    **                              **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("**                               by: Ariel Vaknin                                                            **\n");
        System.out.print("**                                                                                                           **\n");
        System.out.print("***************************************************************************************************************\n");
        System.out.print("***************************************************************************************************************\n");

    }
   /* public static void CreateJsonFile(File file)//This method created a JSON type file of the database.
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
    */
}

