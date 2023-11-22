import java.io.*;
import java.util.Arrays;
import java.util.Scanner;// Used to read from the user or files
import java.lang.Integer; // used for the ParseInt method
import java.util.Random;// Used to generate a random song



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

        int again = 1;


        Printers.PrintWelcomeBanner();

        try(Scanner Keyboard = new Scanner(System.in)) {

            File file = new File(args[0]);
            Album[] array = CreateDiscographyArray(file);

            while(again != 0) // Main loop. repeats until its requested to exit
            {
                int counter = 0;
                int pick = 0;
                Song song = new Song();
                System.out.println("Hello ! Pick one of the options :\n0 to get a random Taylor song\n1 to look up a word \n2 to get Songs/Albums length information :");
                System.out.print("----->");
                pick = Keyboard.nextInt();
                while((pick != 0 )&& (pick != 1) && (pick != 2))//Input check
                {
                    System.out.println("Invalid input, Please try again.");
                    System.out.print("----->");
                    pick = Keyboard.nextInt();
                }
                if(pick == 1)//The user picked to look up a word
                {
                    System.out.println("\nPlease enter the word you'd like to search : ");
                    System.out.print("----->");
                    song.Word = Keyboard.next();
                    String temp;
                    Scanner FileReader = new Scanner(file);
                    temp = FileReader.nextLine();
                    temp = temp.substring(15);
                    DiscographyLength = Integer.parseInt(temp);
                    System.out.println("In which album would you like to search ? choose 1-" + DiscographyLength + " or 0 for the entire discography :");
                    System.out.print("----->");
                    Printers.PrintAlbums();
                    pick = Keyboard.nextInt();
                    while((pick > DiscographyLength)||(pick < 0))//Input check
                    {
                        System.out.println("Invalid input, Please try again.");
                        System.out.print("----->");
                        pick = Keyboard.nextInt();
                    }
                    if (pick == 0)// The user picked to search in the entire discography
                    {
                        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no : ");
                        int choice = Keyboard.nextInt();
                        if(choice == 1)
                        {
                            MakeSummaryFileDiscography(song.Word,file);
                            System.out.println("*****Summary file has been created successfully !*****");
                        }
                        else if(choice == 0)
                        {
                            counter = WordCounterInDiscography(song.Word,file);
                            System.out.println("The word \""+song.Word+"\" appears "+counter+" times in Taylor's discography.");
                        }

                    }
                    else// The user picked a specific album
                    {
                        int i;
                        int choice = 0;
                        for (i = 1; i <= pick; i++)
                            temp = FileReader.nextLine();

                        File AlbumFile = new File(temp);
                        Album album = new Album();
                        album.file = AlbumFile;

                        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no : ");
                        System.out.print("----->");
                        choice = Keyboard.nextInt();
                        while((choice != 0 )&& (choice != 1))//Input check
                        {
                            System.out.println("Invalid input, Please try again.");
                            System.out.print("----->");
                            choice = Keyboard.nextInt();
                        }

                        album = Album.GetAlbumDetails(album.file,i-1);
                        if(choice == 1)//If the user wants to create a file
                        {
                            MakeSummaryFileAlbum(song.Word,album);
                            System.out.println("*** Summary file has been created ! ***");
                        }
                        else //if the user doesn't want to create a file
                        {
                            Scanner AlbumReader = new Scanner(AlbumFile);

                            System.out.println("In which song would you like to search ? pick 1-" + album.NumOfSongs + " or 0 for the entire album :");
                            Printers.PrintAlbumsTracks(pick);
                            System.out.print("----->");
                            pick = Keyboard.nextInt();
                            while ((pick < 0) || (pick > album.NumOfSongs))//Inout check
                            {
                                System.out.println("Invalid input, Please try again.");
                                System.out.print("----->");
                                pick = Keyboard.nextInt();
                            }
                            if (pick > 0) //The user picked a specific song
                            {
                                for (i = 1; i <= pick; i++)
                                    temp = AlbumReader.nextLine();

                                song.Path = temp;
                                File SongFile = new File(song.Path);
                                counter = WordCounterInSong(song);
                                System.out.println("The word \"" + song.Word + "\" appears " + counter + " times in the song " + song.SongName + ".");

                            } else if (pick == 0) //The user chose to search in the entire album
                            {
                                int AlbumCounter = WordCounterInAlbum(song.Word, album);
                                System.out.println("The word \"" + song.Word + "\" appears " + AlbumCounter + " times in the " + album.AlbumName + " album.");

                            }
                            AlbumReader.close();
                        }
                    }
                    Keyboard.nextLine();
                    FileReader.close();
                }
                else if(pick == 0)// The user chose to get a random taylor song
                {
                    Song RandSong = RandomSongArray(array);
                    System.out.println("The chosen song is " + RandSong.SongName + " !");
                }
                else if(pick == 2)
                {
                    System.out.println("Please choose an album or press 0 for the entire discography :");
                    Printers.PrintAlbums();
                    System.out.println("----->");
                    int choice = Keyboard.nextInt();
                    while((choice > DiscographyLength)||(choice < 0))//Input check
                    {
                        System.out.println("Invalid input, Please try again.");
                        System.out.print("----->");
                        choice = Keyboard.nextInt();
                    }
                    if(choice == 0)//The user chooses Duration information for the entire discography
                    {
                        DurationSummaryDiscography(array);
                    }
                    else//The user chooses to get information about a specific album
                    {
                        System.out.println("Please choose a song or press 0 for the entire album :");
                        Printers.PrintAlbumsTracks(choice);
                        int input = Keyboard.nextInt();
                        while ((input < 0) || (input > array[choice].NumOfSongs))//Inout check
                        {
                            System.out.println("Invalid input, Please try again.");
                            System.out.print("----->");
                            input = Keyboard.nextInt();
                        }

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
                System.out.println("Would you like to go again ? choose any number to continue or 0 to exit ");
                System.out.print("----->");
                again = Keyboard.nextInt();
            }

        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("I'll tell you the truth, but never goodbye");

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
            int TotalCounter = 0;
            Song TotalMaxSong = array[1].SongsArray[1];
            Song TotalMinSong = array[1].SongsArray[1];

            for(int i = 1 ; i <= DiscographyLength ; i++)
            {
                int AlbumCounter = 0;
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
                    AlbumCounter += Counter;
                }
                TotalCounter += AlbumCounter;
                if(MaxSong.DurationInSec >= TotalMaxSong.DurationInSec)
                {
                    TotalMaxSong = MaxSong;
                }
                else if(MinSong.DurationInSec < TotalMinSong.DurationInSec)
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

    public static void RandomSong(File file) {
        try (Scanner FileReader = new Scanner(file))
        {
            int RandAlbum, RandSong;
            String temp;
            Random rand = new Random();
            RandAlbum = rand.nextInt(1, DiscographyLength + 1);
            temp = FileReader.nextLine();
            int i;
            for (i = 1; i <= RandAlbum; i++)
                temp = FileReader.nextLine();

            File RandAlbumFile = new File(temp);
            Scanner RandAlbumReader = new Scanner(RandAlbumFile);
            Album album = Album.GetAlbumDetails(RandAlbumFile, i);
            RandSong = rand.nextInt(1, album.NumOfSongs + 1);
            for (i = 1; i <= RandSong; i++)
                temp = RandAlbumReader.nextLine();

            File RandSongFile = new File(temp);
            Scanner SongReader = new Scanner(RandSongFile);
            temp = SongReader.nextLine();
            System.out.println("The chosen song is " + temp + " !");
            RandAlbumReader.close();
            SongReader.close();
        }catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
    public static int WordCounterInSong(Song song)// This method counts the number of times a word appears in a given song
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

                if(temp.compareToIgnoreCase(song.Word) == 0)
                    WordCounter++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        song.counter = WordCounter;
        return WordCounter;
    }

    public static int WordCounterInAlbum(String word,Album album)// This method counts the number of times a word appears in a given album
    {
        int AlbumCounter = 0;

        try (Scanner reader = new Scanner(album.file)) {
            String temp = new String();
            temp = reader.nextLine();

            for (int i = 1; i <= album.NumOfSongs; i++) {

                int counter = 0;
                temp = reader.nextLine();
                Song CurrSong = new Song();
                CurrSong.Word = word;
                CurrSong.Path = temp;
                counter = WordCounterInSong(CurrSong);
                AlbumCounter += counter;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return AlbumCounter;
    }

    public static int WordCounterInDiscography(String word,File file)//This method counts the number of times a word appears in Taylor's entire discography
    {
        int TotalCounter = 0;
        String temp;
        try(Scanner reader = new Scanner(file))
        {
            temp = reader.nextLine();
            for(int i = 1; i <= DiscographyLength ; i++)
            {
                int counter = 0;
                temp = reader.nextLine();
                File AlbumFile = new File(temp);
                Album album = Album.GetAlbumDetails(AlbumFile,i);
                counter = WordCounterInAlbum(word,album);
                //System.out.println("album name : " +album.AlbumName +"it appears : "+ counter +" times ");
                TotalCounter += counter;

            }
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return TotalCounter;
    }

    public static void MakeSummaryFileAlbum(String word,Album album)
    {
        String NewFileName = word+" in "+album.AlbumName+" Summary.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NewFileName)))
        {
            writer.write("Summary file for the word "+word+" in the "+album.AlbumName+" album.\n");
            Scanner AlbumReader = new Scanner(album.file);
            String temp = AlbumReader.nextLine();
            int AlbumCounter = 0;
            for(int i = 1; i <= album.NumOfSongs ; i++)
            {
                int counter = 0;
                Song song = new Song();
                temp = AlbumReader.nextLine();
                song.Path = temp;
                song.Word = word;
                counter = WordCounterInSong(song);
                writer.write(i+") \""+song.SongName+"\" : "+counter+ " times\n");
                AlbumCounter += counter;
            }
            writer.write("*** In Total, the word \""+word+"\" appears "+AlbumCounter+" times in the album \""+album.AlbumName+"\".");
        }
        catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }

    }

    public static void MakeSummaryFileDiscography(String word,File MainFile)
    {
        String FileName = word + " - Full Summary.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for the word \""+word+"\".\n");
            Scanner MainFileScanner = new Scanner(MainFile);
            String temp = MainFileScanner.nextLine();
            int TotalCounter = 0;
            for(int i = 1; i <= DiscographyLength ; i++)
            {
                temp = MainFileScanner.nextLine();
                File AlbumFile = new File(temp);
                Scanner AlbumFileScanner = new Scanner(AlbumFile);
                Album albumObj = new Album();
                albumObj = Album.GetAlbumDetails(AlbumFile,i);
                writer.write("Album name : "+albumObj.AlbumName+"\n");
                int AlbumCounter = 0;
                String Line = AlbumFileScanner.nextLine();
                for(int j = 1; j <= albumObj.NumOfSongs ; j++)
                {
                    int counter = 0;
                    Song song = new Song();
                    Line = AlbumFileScanner.nextLine();
                    song.Word = word;
                    song.Path = Line;
                    counter = WordCounterInSong(song);
                    writer.write(j+") "+song.SongName+" : "+counter+ " times\n");
                    AlbumCounter += counter;
                }
                writer.write("In total, The word appears "+AlbumCounter+" times in the "+albumObj.AlbumName+" album.\n\n");
                TotalCounter += AlbumCounter;
                AlbumFileScanner.close();
            }
            writer.write("***** In Total, The word appears "+TotalCounter +" times in Tyalor's Entire Discography *****");
            MainFileScanner.close();
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

