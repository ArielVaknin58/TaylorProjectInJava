import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Album {

    String AlbumName;//Holds a string with the album's name
    int NumOfSongs;//Holds the number of songs on the album

    int AlbumNumber;//Holds the serial number of the album. 1 for debut, 2 for Fearless etc. Used with the IntToAlbum() method.

    private File file;//Holds the open file of the album

    Song[] SongsArray;//Holds the array containing the songs of the album

    public Song[] GetSongsArray() {return SongsArray;}
    public void SetSongsArray(Song[] songsArray) {SongsArray = songsArray;}
    public File GetFile() {return file;}
    public void SetFile(File AlbumFile) {file = AlbumFile;}
    public int GetAlbumNumber() {return AlbumNumber;}
    public void SetAlbumNumber(int albumNumber) {AlbumNumber = albumNumber;}
    public int GetNumOfSongs() {return NumOfSongs;}
    public void SetNumOfSongs(int numOfSongs) {if(numOfSongs > 0){NumOfSongs = numOfSongs;}}
    public String GetAlbumName() {return AlbumName;}
    public void SetAlbumName(String albumName) {AlbumName = albumName;}


    public Album(String name, int Length, int num, File file, Song[] songsArray)
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
            album.SetNumOfSongs(Integer.parseInt(temp));
            album.SetFile(AlbumFile);
            album.SetAlbumName(IntToAlbum(AlbumNum));
            album.SetAlbumNumber(AlbumNum);
        }
        catch (FileNotFoundException e) {System.out.println("An error occurred.");}
        return album;
    }


    public static void LongestAndShortestSongsInAlbum(Album album)//This method finds the longest and shortest songs in a given album.
    {
        Scanner Keyboard = new Scanner(System.in);
        System.out.println("Would you like a summary report ? 1 for yes / 0 for no :");
        int choice = Keyboard.nextInt();
        Main.InputCheck(0,1,Keyboard,choice);

        Song[] array = album.GetSongsArray();
        Song MaxSong = array[1];
        Song MinSong = array[1];
        int AlbumCounter = 0;
        if(choice == 0)//If the user chooses to not create a file
        {
            for(int i = 1 ; i <= album.GetNumOfSongs() ; i++)
            {
               if(array[i].GetDurationInSec() >= MaxSong.GetDurationInSec())
                   MaxSong = array[i];
               if(array[i].GetDurationInSec() < MinSong.GetDurationInSec())
                   MinSong = array[i];

               AlbumCounter += array[i].GetDurationInSec();
            }
           int Hours = AlbumCounter / 3600;
           int Minutes = (AlbumCounter - Hours*3600)/60;
           int Seconds = AlbumCounter % 60;
           System.out.println("In summary, the "+album.GetAlbumName()+" album is "+Hours+"h, "+Minutes+"m and "+Seconds+"s long.\n");
           System.out.println("The longest song on the album is "+MaxSong.GetSongName());
           System.out.println("The song is "+Song.GetSongHours(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.");
           System.out.println("\nThe Shortest song on the album is "+MinSong.GetSongName()+" and it's "+Song.GetSongHours(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.");
        }
        else if(choice == 1)//If the user chooses to create a summary file
        {
            String FileName = album.GetAlbumName()+" Duration Summary.txt";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))){
                writer.write("Summary for the "+album.GetAlbumName()+" album - Duration in MM:SS format\n");

                for(int i = 1 ; i <= album.GetNumOfSongs() ; i++)
                {
                    writer.write(i+") "+array[i].GetSongName()+" : "+Song.GetSongHours(array[i])+":"+Song.GetSongSeconds(array[i])+"\n");
                    if(array[i].GetDurationInSec() >= MaxSong.GetDurationInSec())
                        MaxSong = array[i];

                    if(array[i].GetDurationInSec() < MinSong.GetDurationInSec())
                        MinSong = array[i];
                }

                writer.write("\n*** In summary, the longest song on the album is "+MaxSong.GetSongName()+" which is "+Song.GetSongHours(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.\n");
                writer.write("The Shortest song on the album is "+MinSong.GetSongName()+" and it's "+Song.GetSongHours(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.***");

            }catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}

            System.out.println("*** Summary file has been created ! ***");
        }
    }

    public static void NumOfWordsInDiscography(Album[] array)//This method counts the number of words in Taylor's discography.
    {
        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
        Scanner Keyboard = new Scanner(System.in);
        int choice = Keyboard.nextInt();
        Main.InputCheck(0,1,Keyboard,choice);
        int TotalCounter = 0;

        if(choice == 0)//The user chose to not create a summary file.
        {
            for(int i = 1; i <= Main.getDiscographyLength() ; i++)
                TotalCounter += NumOfWordsInAlbum(array[i]);

            System.out.println("There are "+TotalCounter+" words in Taylor's discography.");
        }
        else if(choice == 1)//The user chose to create a summary file.
        {
            Song TotalMaxSong = array[1].GetSongsArray()[1];
            Song TotalMinSong = array[1].GetSongsArray()[1];
            int TotalMaxWords = 0;
            int TotalMinWords = 1000;
            String FileName = "Word counter Full Summary.txt";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
            {
                writer.write("Summary of the number of words in Taylor's Discography\n\n");
                for(int i = 1; i <= Main.getDiscographyLength() ; i++)
                {
                    Song AlbumMaxSong = array[1].GetSongsArray()[1];
                    Song AlbumMinSong = array[1].GetSongsArray()[1];
                    int AlbumMaxWords = 0;
                    int AlbumMinWords = 1000;
                    writer.write("Album Name : "+array[i].GetAlbumName()+"\n");
                    int AlbumCounter = 0;
                    for (int j = 1; j <= array[i].GetNumOfSongs() ; j++)
                    {
                        int counter = Song.NumOfWordsInASong(array[i].GetSongsArray()[j]);
                        if(counter >= AlbumMaxWords)
                        {
                            AlbumMaxSong = array[i].GetSongsArray()[j];
                            AlbumMaxWords = counter;
                            if(counter >= TotalMaxWords)
                            {
                                TotalMaxSong = array[i].GetSongsArray()[j];
                                TotalMaxWords = counter;
                            }
                        }
                        else if(counter <= AlbumMinWords)
                        {
                            AlbumMinSong = array[i].GetSongsArray()[j];
                            AlbumMinWords = counter;
                            if(counter < TotalMinWords)
                            {
                                TotalMinSong = array[i].GetSongsArray()[j];
                                TotalMinWords = counter;
                            }
                        }
                        writer.write(j+") "+array[i].GetSongsArray()[j].GetSongName()+" : "+counter +" words\n");
                        AlbumCounter += counter;
                    }
                    writer.write("In summary, in the "+array[i].GetAlbumName()+" album there are "+AlbumCounter+" words.\n");
                    writer.write("The longest song on the album is "+AlbumMaxSong.GetSongName()+" with "+AlbumMaxWords+" words\n");
                    writer.write("The shortest song on the album is "+AlbumMinSong.GetSongName()+" with "+AlbumMinWords+" words.\n\n");
                    TotalCounter += AlbumCounter;
                }
                writer.write("***In summary, there are "+TotalCounter+" words in Taylor's entire discography.\n");
                writer.write("The longest song on Taylor's discography is "+TotalMaxSong.GetSongName()+" with "+TotalMaxWords+" words\n");
                writer.write("The shortest song on the album is "+TotalMinSong.GetSongName()+" with "+TotalMinWords+" words.\n\n");
                System.out.println("*** Summary file has been created ! ***");
            }catch (IOException e) {System.out.println("An error occurred.");}
        }
    }

    public static int WordCounterInAlbum(String word,Album album)//This method counts the number of times a word appears in a given album
    {
        int AlbumCounter = 0;
        Song[] array = album.GetSongsArray();

        for (int i = 1; i <= album.GetNumOfSongs(); i++)
        {
            int counter = Song.WordCounterInSong(array[i],word);
            AlbumCounter += counter;
        }

        return AlbumCounter;
    }

    public static void WordCounterInAlbumSummaryFile(String word,Album album)//This method counts the appearances of a given word in a given album and produces a file with the details.
    {
        String NewFileName = word+" in "+album.GetAlbumName()+" Summary.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NewFileName)))
        {
            writer.write("Summary file for the word "+word+" in the "+album.GetAlbumName()+" album.\n");
            int AlbumCounter = 0;
            for(int i = 1; i <= album.GetNumOfSongs() ; i++)
            {
                int counter;
                counter = Song.WordCounterInSong(album.GetSongsArray()[i],word);
                writer.write(i+") "+album.GetSongsArray()[i].GetSongName()+" : "+counter+ " times\n");
                AlbumCounter += counter;
            }
            writer.write("\n*** In Total, the word \""+word+"\" appears "+AlbumCounter+" times on the album \""+album.GetAlbumName()+"\".");
        }
        catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}

    }
    public static ArrayList<Song> CountExplicitsInAlbum(Album album)//This method creates and returns an ArrayList of the explicit songs in a given album.
    {
        ArrayList<Song> ExplicitSongs = new ArrayList<>();
        for(int i = 1; i <= album.GetNumOfSongs(); i++)
            if(album.GetSongsArray()[i].GetIsExplicit())
                ExplicitSongs.add(album.GetSongsArray()[i]);
        return ExplicitSongs;
    }

    public static int CountObscenitiesInAlbum(Album album)//This method counts the number of obscenities in a given album.
    {
        int AlbumCounter = 0;
        for(int i = 1 ; i <= album.GetNumOfSongs() ; i++)
            AlbumCounter += Song.CountObscenitiesInSong(album.GetSongsArray()[i]);
        return AlbumCounter;
    }

    public static void CountObscenitiesInAlbumSummary(Album album)//This method counts the number of obscenities in a given album and produces a file with the details.
    {
        String FileName = album.GetAlbumName()+" Obscenities Summary.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for the number of obscenities in the "+album.GetAlbumName()+" album.\n");
            int AlbumCounter = 0;
            for(int i = 1 ; i <= album.GetNumOfSongs() ; i++)
            {
                int counter = Song.CountObscenitiesInSong(album.GetSongsArray()[i]);
                writer.write(i+") "+album.GetSongsArray()[i].GetSongName()+" - "+counter+"\n");
                AlbumCounter+= counter;
            }
            writer.write("\n***In Summary, the "+album.GetAlbumName()+" album has "+AlbumCounter+" obscenities in it.***");
        }
        catch (IOException e) {System.out.println("an error has occurred.");}
    }
    public static void CountExplicitsInAlbumSummary(Album album)//This method counts the number of explicit songs in a given album and creates a file with the details.
    {
        String FileName = album.GetAlbumName()+" Explicits Summary.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for Explicit songs in the "+album.GetAlbumName()+" album.\n");
            int counter = 0;
            for(int i = 1 ; i <= album.GetNumOfSongs() ; i++)
            {
                if(album.GetSongsArray()[i].GetIsExplicit())
                {
                    writer.write(i+") "+album.GetSongsArray()[i].GetSongName()+" - Explicit\n");
                    counter++;
                }
                else
                    writer.write(i+") "+album.GetSongsArray()[i].GetSongName()+" - Clean\n");
            }
            writer.write("\n***In Summary, the "+album.GetAlbumName()+" album has "+counter+" explicit songs in it.");
        }
        catch (IOException e) {System.out.println("an error has occurred.");}
    }

    public static int AvgSongLengthInAlbum(Album album)//This function returns the average song length in a given album in seconds.
    {
        Song[] array = album.GetSongsArray();
        int DurationAccumulator = 0;
        for(int i = 1; i <= album.GetNumOfSongs() ; i++)
            DurationAccumulator += array[i].GetDurationInSec();

        return (DurationAccumulator / album.GetNumOfSongs());
    }


    public static int NumOfWordsInAlbum(Album album)//This method returns the number of words in a given album.
    {//NEEDED THE OPEN ALBUM FILE AND ALBUM NUMBER

        int AlbumCounter = 0;
        Song[] AlbumArray = album.GetSongsArray();
        for(int i = 1; i <= album.GetNumOfSongs() ; i++)
            AlbumCounter += Song.NumOfWordsInASong(AlbumArray[i]);

        System.out.println("There are "+AlbumCounter+" words in The "+album.GetAlbumName()+" album.");
        return AlbumCounter;
    }

    public static void NumOfWordsInAlbumSummary(Album album)//This method counts the number of words in a given album and produces a file with the details.
    {
        String FileName = album.GetAlbumName()+" - Word counter.txt";
        File file = new File(FileName);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary for the number of words in the "+album.GetAlbumName()+" album\n\n");
            int AlbumCounter = 0;
            for (int j = 1; j <= album.GetNumOfSongs() ; j++)
            {
                int counter = Song.NumOfWordsInASong(album.GetSongsArray()[j]);
                writer.write(j+") "+album.GetSongsArray()[j].GetSongName()+" : "+counter +" words\n");
                AlbumCounter += counter;
            }
            writer.write("***In summary, in the "+album.GetAlbumName()+" album there are "+AlbumCounter+" words***\n\n");
            System.out.println("*** Summary file has been created ! ***");
        }catch (IOException e) {System.out.println("An error occurred.");}
    }

    public static Song[] CreateAlbumArray(Album album)//This method creates and returns an array of the songs of the given album.
    {
        album = Album.GetAlbumDetails(album.GetFile(),album.GetAlbumNumber());
        Song[] AlbumArray = new Song[album.GetNumOfSongs() + 1];
        try(Scanner AlbumReader = new Scanner(album.GetFile()))
        {
            String temp = AlbumReader.nextLine();
            for(int i = 1; i <= album.GetNumOfSongs() ; i++)
            {
                Song song = new Song();
                temp = AlbumReader.nextLine();
                File SongFile = new File(temp);
                song = Song.GetSongInformation(SongFile);
                song.SetSongPath(temp);
                AlbumArray[i] = song;
            }
        }
        catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}

        return AlbumArray;
    }



}
