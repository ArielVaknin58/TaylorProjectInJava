import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Album {

    private String AlbumName;//Holds a string with the album's name
    private int NumOfSongs;//Holds the number of songs on the album
    private int AlbumNumber;//Holds the serial number of the album. 1 for debut, 2 for Fearless etc. Used with the IntToAlbum() method.
    private File file;//Holds the open file of the album
    private Song[] SongsArray;//Holds the array containing the songs of the album
    private String ArtistName;//Contains the name of the artist.
    private int AlbumDurationInSec;//Contains the duration of the album in seconds, the accumulated duration of all the songs on the album.


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
    public String GetArtistName() {return ArtistName;}
    public void SetArtistName(String artistName) {ArtistName = artistName;}
    public int getDurationHours() {return (this.AlbumDurationInSec / 3600);}
    public int getDurationMin() {return ((this.AlbumDurationInSec - this.getDurationHours()*3600)/60);}
    public int getDurationSec() {return this.AlbumDurationInSec%60;}

    public Album(String name, int Length, int num, File file, Song[] songsArray, String artistName)
    {
        this.AlbumName = name;
        this.NumOfSongs = Length;
        this.AlbumNumber = num;
        this.file = file;
        this.SongsArray = songsArray;
        this.ArtistName = artistName;
    }

    public Album()
    {
        this.AlbumName = "";
        this.NumOfSongs = 0;
        this.AlbumNumber = 0;
        this.file = null;
        this.SongsArray = null;
        this.ArtistName = "";
    }

    public static String IntToAlbum(int num,int ArtistNum)// This function gets an integer and returns a string which contains the name of the album. Invalid input has already been checked in the main function so there is no default option in the switch.
    {
        if(ArtistNum == 0)
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
                case 11 -> "The Tortured Poets Department";
                case 12 -> "Specials";
                default -> "wrong input";
            };
        }
        else
        {
            return switch (num) {
                case 1 -> "As She Pleases";
                case 2 -> "Life Support";
                case 3 -> "Silence Between Songs";
                case 4 -> "Specials";
                default -> "wrong input";
            };
        }

    }

    public static Album GetAlbumDetails(File AlbumFile,int AlbumNum,int ArtistNum) throws FileNotFoundException//The album's open Readme file should be given as a parameter
    {//NEEDED JUST THE OPENED ALBUM FILE AND THE ALBUM NUMBER
        Album album = null;

            String temp;
            album = new Album();
            Scanner AlbumReader = new Scanner(AlbumFile);
            temp = AlbumReader.nextLine();
            temp = temp.substring(14);
            album.SetNumOfSongs(Integer.parseInt(temp));
            album.SetFile(AlbumFile);
            album.SetAlbumName(IntToAlbum(AlbumNum,ArtistNum));
            album.SetAlbumNumber(AlbumNum);
            album.SetArtistName(Artist.NumToArtists(ArtistNum));

        return album;
    }


    public void LongestAndShortestSongsInAlbum() throws IOException//This method finds the longest and shortest songs in a given album.
    {
        Scanner Keyboard = new Scanner(System.in);
        System.out.println("Would you like a summary report ? 1 for yes / 0 for no :");
        int choice = Keyboard.nextInt();
        Main.InputCheck(0,1,Keyboard,choice);

        Song[] array = this.GetSongsArray();
        Song MaxSong = array[1];
        Song MinSong = array[1];
        int AlbumCounter = 0;
        if(choice == 0)//If the user chooses to not create a file
        {
            for(int i = 1 ; i <= this.GetNumOfSongs() ; i++)
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
           System.out.println("In summary, the "+this.GetAlbumName()+" album is "+Hours+"h, "+Minutes+"m and "+Seconds+"s long.\n");
           System.out.println("The longest song on the album is "+MaxSong.GetSongName());
           System.out.println("The song is "+Song.GetSongMinutes(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.");
           System.out.println("\nThe Shortest song on the album is "+MinSong.GetSongName()+" and it's "+Song.GetSongMinutes(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.");
        }
        else if(choice == 1)//If the user chooses to create a summary file
        {
            String FileName = Main.getOutputPath() + this.GetAlbumName()+" Duration Summary -"+this.GetArtistName()+".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(FileName));
            writer.write("Summary for the "+this.GetAlbumName()+" album - Duration in MM:SS format\n");

            for(int i = 1 ; i <= this.GetNumOfSongs() ; i++)
            {
                writer.write(i+") "+array[i].GetSongName()+" : "+Song.GetSongMinutes(array[i])+":"+Song.GetSongSeconds(array[i])+"\n");
                if(array[i].GetDurationInSec() >= MaxSong.GetDurationInSec())
                    MaxSong = array[i];
                if(array[i].GetDurationInSec() < MinSong.GetDurationInSec())
                    MinSong = array[i];
            }
            writer.write("\n*** In summary, the longest song on the album is "+MaxSong.GetSongName()+" which is "+Song.GetSongMinutes(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.\n");
            writer.write("The Shortest song on the album is "+MinSong.GetSongName()+" and it's "+Song.GetSongMinutes(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.***");

            writer.close();
            System.out.println("*** Summary file has been created ! ***");
        }
    }



    public int WordCounterInAlbum(String word) throws FileNotFoundException//This method counts the number of times a word appears in a given album
    {
        int AlbumCounter = 0;
        for (int i = 1; i <= this.GetNumOfSongs(); i++)
        {
            int counter = this.GetSongsArray()[i].WordCounterInSong(word);
            AlbumCounter += counter;
        }
        return AlbumCounter;
    }

    public void WordCounterInAlbumSummaryFile(String word) throws IOException//This method counts the appearances of a given word in a given album and produces a file with the details.
    {
        String NewFileName = Main.getOutputPath() + word+" in "+this.GetAlbumName()+" Summary - "+this.GetArtistName()+".txt";

            BufferedWriter writer = new BufferedWriter(new FileWriter(NewFileName));
            writer.write("Summary file for the word "+word+" in the "+this.GetAlbumName()+" album.\n");
            int AlbumCounter = 0;
            for(int i = 1; i <= this.GetNumOfSongs() ; i++)
            {
                int counter;
                counter = this.GetSongsArray()[i].WordCounterInSong(word);
                writer.write(i+") "+this.GetSongsArray()[i].GetSongName()+" : "+counter+ " times\n");
                AlbumCounter += counter;
            }
            writer.write("\n*** In Total, the word \""+word+"\" appears "+AlbumCounter+" times on the album \""+this.GetAlbumName()+"\".");
            writer.close();
        System.out.println("*** Summary file has been created ! ***");
    }
    public ArrayList<Song> CountExplicitsInAlbum()//This method creates and returns an ArrayList of the explicit songs in a given album.
    {
        ArrayList<Song> ExplicitSongs = new ArrayList<>();
        for(int i = 1; i <= this.GetNumOfSongs(); i++)
            if(this.GetSongsArray()[i].GetIsExplicit())
                ExplicitSongs.add(this.GetSongsArray()[i]);
        return ExplicitSongs;
    }

    public int CountObscenitiesInAlbum() throws IOException//This method counts the number of obscenities in a given album.
    {
        int AlbumCounter = 0;
        for(int i = 1 ; i <= this.GetNumOfSongs() ; i++)
            AlbumCounter += this.GetSongsArray()[i].CountObscenitiesInSong();
        return AlbumCounter;
    }

    public void CountObscenitiesInAlbumSummary() throws IOException//This method counts the number of obscenities in a given album and produces a file with the details.
    {
        String FileName = Main.getOutputPath() + this.GetAlbumName()+" Obscenities Summary - "+this.GetArtistName()+".txt";

        BufferedWriter writer = new BufferedWriter(new FileWriter(FileName));
        writer.write("Summary file for the number of obscenities in the "+this.GetAlbumName()+" album.\n");
        int AlbumCounter = 0;
        for(int i = 1 ; i <= this.GetNumOfSongs() ; i++)
        {
            int counter = this.GetSongsArray()[i].CountObscenitiesInSong();
            writer.write(i+") "+this.GetSongsArray()[i].GetSongName()+" - "+counter+"\n");
            AlbumCounter+= counter;
        }
        writer.write("\n***In Summary, the "+this.GetAlbumName()+" album has "+AlbumCounter+" obscenities in it.***");
        writer.close();
    }
    public void CountExplicitsInAlbumSummary() throws IOException//This method counts the number of explicit songs in a given album and creates a file with the details.
    {
        String FileName = Main.getOutputPath() + this.GetAlbumName()+" Explicits Summary - "+this.GetArtistName()+".txt";

            BufferedWriter writer = new BufferedWriter(new FileWriter(FileName));
            writer.write("Summary file for Explicit songs in the "+this.GetAlbumName()+" album.\n");
            int counter = 0;
            for(int i = 1 ; i <= this.GetNumOfSongs() ; i++)
            {
                if(this.GetSongsArray()[i].GetIsExplicit())
                {
                    writer.write(i+") "+this.GetSongsArray()[i].GetSongName()+" - Explicit\n");
                    counter++;
                }
                else
                    writer.write(i+") "+this.GetSongsArray()[i].GetSongName()+" - Clean\n");
            }
            writer.write("\n***In Summary, the "+this.GetAlbumName()+" album has "+counter+" explicit songs in it.");
            writer.close();
        System.out.println("*** Summary file has been created ! ***");

    }
    public int AvgSongLengthInAlbum()//This function returns the average song length in a given album in seconds.
    {
        Song[] array = this.GetSongsArray();
        int DurationAccumulator = 0;
        for(int i = 1; i <= this.GetNumOfSongs() ; i++)
            DurationAccumulator += array[i].GetDurationInSec();

        return (DurationAccumulator / this.GetNumOfSongs());
    }


    public int NumOfWordsInAlbum() throws IOException//This method returns the number of words in a given album.
    {//NEEDED THE OPEN ALBUM FILE AND ALBUM NUMBER

        int AlbumCounter = 0;
        Song[] AlbumArray = this.GetSongsArray();
        for(int i = 1; i <= this.GetNumOfSongs() ; i++)
            AlbumCounter += AlbumArray[i].NumOfWordsInASong();

        return AlbumCounter;
    }

    public static void NumOfWordsInAlbumSummary(Album album) throws IOException//This method counts the number of words in a given album and produces a file with the details.
    {
        String FileName = Main.getOutputPath() + album.GetAlbumName()+" - Word counter - "+album.GetArtistName()+".txt";

            BufferedWriter writer = new BufferedWriter(new FileWriter(FileName));
            writer.write("Summary for the number of words in the "+album.GetAlbumName()+" album\n\n");
            int AlbumCounter = 0;
            for (int j = 1; j <= album.GetNumOfSongs() ; j++)
            {
                int counter = album.GetSongsArray()[j].NumOfWordsInASong();
                writer.write(j+") "+album.GetSongsArray()[j].GetSongName()+" : "+counter +" words\n");
                AlbumCounter += counter;
            }
            writer.write("***In summary, in the "+album.GetAlbumName()+" album there are "+AlbumCounter+" words***\n\n");
            System.out.println("*** Summary file has been created ! ***");
            writer.close();
    }

    public static Song[] CreateAlbumArray(Album album,int ArtistNum) throws IOException//This method creates and returns an array of the songs of the given album.
    {
        album = Album.GetAlbumDetails(album.GetFile(),album.GetAlbumNumber(),ArtistNum);
        Song[] AlbumArray = new Song[album.GetNumOfSongs() + 1];
        Scanner AlbumReader = new Scanner(album.GetFile());
        String temp = AlbumReader.nextLine();
        for(int i = 1; i <= album.GetNumOfSongs() ; i++)
        {
            Song song;
            temp = AlbumReader.nextLine();
            File SongFile = new File(temp);
            song = Song.GetSongInformation(SongFile);
            song.SetSongPath(temp);
            AlbumArray[i] = song;
        }
        AlbumReader.close();
        return AlbumArray;
    }

    public Map<String, Integer> CountDifferentWordsInAlbum()
    {
        Map<String, Integer> AlbumWordCount = new HashMap<>();
        for(int i = 1 ; i <= this.GetNumOfSongs() ; i++)
        {
            Map<String, Integer> temp = this.GetSongsArray()[i].CountDifferentWordsInSong(this.GetSongsArray()[i].GetSongPath());
            // Merge map2 into map1
            temp.forEach((key, value) -> AlbumWordCount.merge(key, value, (v1, v2) -> v1 + v2));
        }
        return AlbumWordCount;
    }

}
