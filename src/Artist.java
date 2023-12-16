import java.io.*;
import java.util.*;

public class Artist {

    private String Name;//Contains the name of the artist.
    private int NumOfSongs;//Contains the total number of songs in The artist's discography.
    private Album[] Discography;//Contains an array of the artist's albums.
    private int NumOfAlbums;//Contains the number of album in the artist's discography (including specials)
    private int NumOfExplicits;//Containing the number of songs marked as explicit.
    private int ArtistNum;//Contains the serial number of the artist.
    private int DiscographyDurationInSec;//Contains the length of the discography in seconds.

    public Artist(int ArtistPick,File file) throws Exception
    {
        setDiscography(Main.CreateDiscographyArray(file,ArtistPick));
        setNumOfAlbums(this.getDiscography().length-1);
        int sum = 0;
        for(int i = 1; i <= this.getNumOfAlbums(); i++)
            sum += getDiscography()[i].GetNumOfSongs();
        setNumOfSongs(sum);
        setName(NumToArtists(ArtistPick));
        setArtistNum(ArtistPick);
        this.DurationOfDiscography();
        ArrayList<ArrayList<Song>> explicits = this.CountExplicitsInDiscography();
        int numExplicits = 0;
        for(int j = 0; j < explicits.size() ; j++)
            numExplicits += explicits.get(j).size();
        setNumOfExplicits(numExplicits);
    }


    public void setDiscography(Album[] discography) {Discography = discography;}
    public Album[] getDiscography() {return Discography;}
    public int getNumOfAlbums() {return NumOfAlbums;}
    public void setNumOfAlbums(int numOfAlbums) {NumOfAlbums = numOfAlbums;}
    public int getNumOfExplicits() {return NumOfExplicits;}
    public void setNumOfExplicits(int numOfExplicits) {NumOfExplicits = numOfExplicits;}
    public int getNumOfSongs() {return NumOfSongs;}
    public void setNumOfSongs(int numOfSongs) {NumOfSongs = numOfSongs;}
    public String getArtistName() {return Name;}
    public void setArtistNum(int artistNum) {ArtistNum = artistNum;}
    public String getName() {return Name;}
    public void setName(String name) {Name = name;}
    public int getDiscographyDurationInSec() {return DiscographyDurationInSec;}
    public void setDiscographyDurationInSec(int discographyDurationInSec) {DiscographyDurationInSec = discographyDurationInSec;}
    public int getArtistNum() {return ArtistNum;}
    public int getDiscographyDurationHours() {return (this.getDiscographyDurationInSec() / 3600);}
    public int getDiscographyDurationMin() {return ((this.getDiscographyDurationInSec() - this.getDiscographyDurationHours()*3600)/60);}
    public int getDiscographyDurationSec() {return this.getDiscographyDurationInSec()%60;}

    public static String NumToArtists(int ArtistNum)
    {
        if(ArtistNum == 0)
            return "Taylor";
        else
            return "Madison";
    }

    public static int ArtistsToNum(String ArtistName)
    {
        if(ArtistName.equals("Taylor"))
            return 0;
        else
            return 1;
    }

    public void CountExplicitsInDiscographySummary()//This method creates a summary file with details about explicit Taylor songs.
    {
        String FileName = "Discography Explicits Summary - "+this.getArtistName()+".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for Explicit songs in "+this.getArtistName()+"'s discography.\n");
            int TotalCounter = 0;
            int MaxIndex = 1;
            int MaxCounter = 0;
            for(int i = 1 ; i <= this.getNumOfAlbums() ; i++)
            {
                writer.write("Album name : "+Album.IntToAlbum(i,Artist.ArtistsToNum(this.getArtistName()))+"\n");
                int AlbumCounter = 0;
                for(int j = 1; j <= this.getDiscography()[i].GetNumOfSongs() ; j++)
                {
                    if(this.getDiscography()[i].GetSongsArray()[j].GetIsExplicit())
                    {
                        writer.write(j+") "+this.getDiscography()[i].GetSongsArray()[j].GetSongName()+" - Explicit\n");
                        AlbumCounter++;
                    }
                    else
                        writer.write(j+") "+this.getDiscography()[i].GetSongsArray()[j].GetSongName()+" - Clean\n");
                }
                writer.write("In summary, there are "+AlbumCounter+" explicit songs in the "+this.getDiscography()[i].GetAlbumName()+" album.\n\n");
                TotalCounter += AlbumCounter;
                if(AlbumCounter >= MaxCounter){
                    MaxCounter = AlbumCounter;
                    MaxIndex = i;
                }
            }
            writer.write("***In summary, there are "+TotalCounter+" explicit songs in "+this.getArtistName()+"'s discography.\n");
            writer.write("The album with the most explicit songs is "+this.getDiscography()[MaxIndex].GetAlbumName()+" with "+MaxCounter+" explicit songs***");
        }
        catch(IOException e) {System.out.println("An error has occurred.");}

        System.out.println("*** Summary file has been created ! ***");
    }

    public ArrayList<ArrayList<Song>> CountExplicitsInDiscography()//This method creates an ArrayList containing the explicit songs in the discography. can extract size and length.
    {
        ArrayList<ArrayList<Song>> DiscographyExplicits = new ArrayList<>();
        for(int i = 1 ; i <= this.getNumOfAlbums() ; i++)
        {
            ArrayList<Song> AlbumExplicits = this.getDiscography()[i].CountExplicitsInAlbum();
            DiscographyExplicits.add(AlbumExplicits);
        }
        return DiscographyExplicits;
    }

    public void DurationDiscographySummary()//This method summarizes the duration information in a summary file if requested.
    {
        int TotalCounter = 0;//counts the duration (seconds) of the entire discography
        Song TotalMaxSong = this.getDiscography()[1].GetSongsArray()[1];//holds the longest song on Taylor's discography
        Song TotalMinSong = this.getDiscography()[1].GetSongsArray()[1];//holds the shortest song on Taylor's discography

        String FileName = "Discography Duration Summary -"+this.getArtistName()+".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary Duration for "+this.getArtistName()+"'s discography in MM:SS format\n\n");
            for(int i = 1 ; i <= this.getNumOfAlbums() ; i++)
            {
                int AlbumCounter = 0;
                writer.write("Album name : "+this.getDiscography()[i].GetAlbumName()+"\n");
                Song[] AlbumArray = this.getDiscography()[i].GetSongsArray();
                Song MaxSong = AlbumArray[1];
                Song MinSong = AlbumArray[1];
                for(int j = 1 ; j <= this.getDiscography()[i].GetNumOfSongs() ; j++)
                {
                    int Counter = AlbumArray[j].GetDurationInSec();
                    if (AlbumArray[j].GetDurationInSec() >= MaxSong.GetDurationInSec())
                        MaxSong = AlbumArray[j];
                    else if (AlbumArray[j].GetDurationInSec() < MinSong.GetDurationInSec())
                        MinSong = AlbumArray[j];

                    writer.write(j+") "+AlbumArray[j].GetSongName()+" : "+Song.GetSongMinutes(AlbumArray[j])+":"+Song.GetSongSeconds(AlbumArray[j])+"\n");
                    AlbumCounter += Counter;
                }
                int Hours = AlbumCounter / 3600;
                int Minutes = (AlbumCounter - Hours*3600)/60;
                int Seconds = AlbumCounter % 60;
                writer.write("In summary, the "+ this.getDiscography()[i].GetAlbumName()+" album is "+Hours+"h, "+Minutes+"m and "+Seconds+"s long.\n");
                writer.write("The longest song on the album is "+MaxSong.GetSongName()+" which is "+Song.GetSongMinutes(MaxSong)+"m and "+Song.GetSongSeconds(MaxSong)+"s long.\n");
                writer.write("The Shortest song on the album is "+MinSong.GetSongName()+" and it's "+Song.GetSongMinutes(MinSong)+"m and "+Song.GetSongSeconds(MinSong)+"s long.\n\n");
                TotalCounter += AlbumCounter;
                if(MaxSong.GetDurationInSec() >= TotalMaxSong.GetDurationInSec())
                    TotalMaxSong = MaxSong;

                if(MinSong.GetDurationInSec() < TotalMinSong.GetDurationInSec())
                    TotalMinSong = MinSong;
            }
            int TotalHours = TotalCounter / 3600;
            int TotalMinutes = (TotalCounter - TotalHours*3600)/60;
            int TotalSeconds = TotalCounter % 60;
            writer.write("\n***In summary, "+this.getArtistName()+"'s entire discography is "+TotalHours+"h, "+TotalMinutes+"m and "+TotalSeconds+"s long.\n");
            writer.write("The Longest "+this.getArtistName()+" song is "+TotalMaxSong.GetSongName()+" and is "+Song.GetSongMinutes(TotalMaxSong)+"m and "+Song.GetSongSeconds(TotalMaxSong)+"s long.\n" );
            writer.write("The Shortest "+this.getArtistName()+" song is "+TotalMinSong.GetSongName()+" and is "+Song.GetSongMinutes(TotalMinSong)+"m and "+Song.GetSongSeconds(TotalMinSong)+"s long.\n" );
            System.out.println("*** Summary file has been created ! ***");
            this.setDiscographyDurationInSec(TotalCounter);
        }
        catch (IOException e) {System.out.println("An error occurred.");}

    }

    public Song[] DurationOfDiscography()
    {
        int TotalCounter = 0;//counts the duration (seconds) of the entire discography
        Song TotalMaxSong = this.getDiscography()[1].GetSongsArray()[1];//holds the longest song on Taylor's discography
        Song TotalMinSong = this.getDiscography()[1].GetSongsArray()[1];//holds the shortest song on Taylor's discography
        Song[] res = new Song[2];

        for(int i = 1 ; i <= this.getNumOfAlbums() ; i++)
        {
            int AlbumCounter = 0;//counts the duration (seconds) of the i-th album.
            Song[] AlbumArray = this.getDiscography()[i].GetSongsArray();//the array of songs of the i-th album
            Song MaxSong = AlbumArray[1];//will hold the longest song on the album
            Song MinSong = AlbumArray[1];//will hold the shortest song on the album
            for(int j = 1 ; j <= this.getDiscography()[i].GetNumOfSongs() ; j++)
            {
                int Counter = AlbumArray[j].GetDurationInSec();
                if(AlbumArray[j].GetDurationInSec() >= MaxSong.GetDurationInSec())//if the current song in longer than the album MaxSong
                    MaxSong = AlbumArray[j];
                else if(AlbumArray[j].GetDurationInSec() < MinSong.GetDurationInSec())//if the current song in shorter than the album MinSong
                    MinSong = AlbumArray[j];

                AlbumCounter += Counter;
            }
            TotalCounter += AlbumCounter;

            if(MaxSong.GetDurationInSec() >= TotalMaxSong.GetDurationInSec())//if the MaxSong on the album is longer than the TotalMaxSong
                TotalMaxSong = MaxSong;
            else if(MinSong.GetDurationInSec() < TotalMinSong.GetDurationInSec())//if the MinSong on the album is shorter than the TotalMinSong
                TotalMinSong = MinSong;

        }

        this.setDiscographyDurationInSec(TotalCounter);
        res[0] = TotalMinSong;
        res[1] = TotalMaxSong;

        return res;

    }

    public Song GetRandomSong()//This method generates a random Taylor song.
    {
        Random rand = new Random();
        int album = rand.nextInt(1,this.getNumOfAlbums() +1);
        int song = rand.nextInt(1,this.getDiscography()[album].GetNumOfSongs()+1);
        return this.getDiscography()[album].GetSongsArray()[song];
    }

    public int WordCounterInDiscography(String word) throws FileNotFoundException//This method counts the number of times a word appears in Taylor's entire discography
    {
        int TotalCounter = 0;
        for(int i = 1; i <= this.getNumOfAlbums() ; i++)
        {
            int counter;
            counter = this.getDiscography()[i].WordCounterInAlbum(word);
            TotalCounter += counter;
        }
        return TotalCounter;
    }

    public int CountObscenitiesInDiscography() throws IOException//This method counts the number of obscenities in Taylor's discography and prints the result.
    {
        int TotalCounter = 0;
        int MaxCount = 0;
        for(int i = 1 ; i <= this.getNumOfAlbums() ; i++)
        {
            int AlbumCounter = this.getDiscography()[i].CountObscenitiesInAlbum();
            if(AlbumCounter > MaxCount)
                MaxCount = AlbumCounter;

            TotalCounter += AlbumCounter;
        }
        return TotalCounter;
    }

    public void CountObscenitiesInDiscographySummary()//This method counts the obscenities in Taylor's discography and produces a file with the details.
    {
        String FileName = "Obscenities - Full Summary - "+this.getArtistName()+".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for obscenities in "+this.getArtistName()+"'s discography.\n");
            int TotalCounter = 0;
            Album MaxAlbum = new Album();
            int MaxAlbumCount = 0;
            Song MaxSong = new Song();
            int MaxSongCount = 0;
            for(int i = 1; i <= this.getNumOfAlbums() ; i++)
            {
                Song[] AlbumArray = this.getDiscography()[i].GetSongsArray();
                writer.write("Album name : "+this.getDiscography()[i].GetAlbumName()+"\n");
                int AlbumCounter = 0;
                for(int j = 1; j <= this.getDiscography()[i].GetNumOfSongs() ; j++)
                {
                    int counter = this.getDiscography()[i].GetSongsArray()[j].CountObscenitiesInSong();
                    writer.write(j+") "+AlbumArray[j].GetSongName()+" : "+counter+ " times\n");
                    if(counter > MaxSongCount)
                    {
                        MaxSong = this.getDiscography()[i].GetSongsArray()[j];
                        MaxSongCount = counter;
                    }
                    AlbumCounter += counter;
                }
                writer.write("In total, There are "+AlbumCounter+" obscenities in the "+this.getDiscography()[i].GetAlbumName()+" album.\n\n");
                if(AlbumCounter > MaxAlbumCount)
                {
                    MaxAlbum = this.getDiscography()[i];
                    MaxAlbumCount = AlbumCounter;
                }
                TotalCounter += AlbumCounter;
            }
            writer.write("***** In Total, There are "+TotalCounter +" obscenities in "+this.getArtistName()+"'s Entire Discography.\n");
            writer.write("The song with the most obscenities from "+this.getArtistName()+"'s discography is "+MaxSong.GetSongName()+" with "+MaxSongCount+" obscenities.\n");
            writer.write("The album with the most obscenities is "+MaxAlbum.GetAlbumName()+" with "+MaxAlbumCount+" obscenities***");
            System.out.println("*****Summary file has been created successfully !*****");
        }
        catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}
    }

    public void WordCounterInDiscographySummaryFile(String word)//This method counts the appearances of a given word in Taylor's discography and produces a file with the details.
    {
        String FileName = word + " - Full Summary/"+this.getArtistName()+".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
        {
            writer.write("Summary file for the word \""+word+"\".\n");
            int TotalCounter = 0;
            for(int i = 1; i <= this.getNumOfAlbums() ; i++)
            {

                writer.write("Album name : "+this.getDiscography()[i].GetAlbumName()+"\n");
                int AlbumCounter = 0;
                for(int j = 1; j <= this.getDiscography()[i].GetNumOfSongs() ; j++)
                {
                    int counter = this.getDiscography()[i].GetSongsArray()[j].WordCounterInSong(word);
                    writer.write(j+") "+this.getDiscography()[i].GetSongsArray()[j].GetSongName()+" : "+counter+ " times\n");
                    AlbumCounter += counter;
                }
                writer.write("In total, The word appears "+AlbumCounter+" times in the "+this.getDiscography()[i].GetAlbumName()+" album.\n\n");
                TotalCounter += AlbumCounter;
            }
            writer.write("***** In Total, The word appears "+TotalCounter +" times in "+this.getArtistName()+"'s Entire Discography *****");
        }
        catch (IOException e) {System.err.println("Error writing to the file: " + e.getMessage());}
    }

    public int NumOfWordsInDiscography() throws IOException
    {
        int TotalCounter = 0;
        for(int i = 1; i <= this.getNumOfAlbums() ; i++)
            TotalCounter += this.getDiscography()[i].NumOfWordsInAlbum();

        return TotalCounter;
    }

    public void NumOfWordsInDiscographySummary()//This method counts the number of words in Taylor's discography.
    {
        int TotalCounter = 0;
        Song TotalMaxSong = this.getDiscography()[1].GetSongsArray()[1];
        Song TotalMinSong = this.getDiscography()[1].GetSongsArray()[1];
        int TotalMaxWords = 0;
        int TotalMinWords = 1000;
        String FileName = "Word counter Full Summary " +this.getArtistName() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {
            writer.write("Summary of the number of words in " + this.getArtistName() + "'s Discography\n\n");
            for (int i = 1; i <= this.getNumOfAlbums(); i++) {
                Song AlbumMaxSong = this.getDiscography()[1].GetSongsArray()[1];
                Song AlbumMinSong = this.getDiscography()[1].GetSongsArray()[1];
                int AlbumMaxWords = 0;
                int AlbumMinWords = 1000;
                writer.write("Album Name : " + this.getDiscography()[i].GetAlbumName() + "\n");
                int AlbumCounter = 0;
                for (int j = 1; j <= this.getDiscography()[i].GetNumOfSongs(); j++) {
                    int counter = this.getDiscography()[i].GetSongsArray()[j].NumOfWordsInASong();
                    if (counter >= AlbumMaxWords) {
                        AlbumMaxSong = this.getDiscography()[i].GetSongsArray()[j];
                        AlbumMaxWords = counter;
                        if (counter >= TotalMaxWords) {
                            TotalMaxSong = this.getDiscography()[i].GetSongsArray()[j];
                            TotalMaxWords = counter;
                        }
                    } else if (counter <= AlbumMinWords) {
                        AlbumMinSong = this.getDiscography()[i].GetSongsArray()[j];
                        AlbumMinWords = counter;
                        if (counter < TotalMinWords) {
                            TotalMinSong = this.getDiscography()[i].GetSongsArray()[j];
                            TotalMinWords = counter;
                        }
                    }
                    writer.write(j + ") " + this.getDiscography()[i].GetSongsArray()[j].GetSongName() + " : " + counter + " words\n");
                    AlbumCounter += counter;
                }
                writer.write("In summary, in the " + this.getDiscography()[i].GetAlbumName() + " album there are " + AlbumCounter + " words, among them "+this.getDiscography()[i].CountDifferentWordsInAlbum().size()+" unique words.\n");
                writer.write("The longest song on the album is " + AlbumMaxSong.GetSongName() + " with " + AlbumMaxWords + " words\n");
                writer.write("The shortest song on the album is " + AlbumMinSong.GetSongName() + " with " + AlbumMinWords + " words.\n\n");
                TotalCounter += AlbumCounter;
            }
            writer.write("***In summary, there are " + TotalCounter + " words in " + this.getArtistName() + "'s entire discography.\n");
            writer.write("There are "+this.CountDifferentWordsInDiscography()+" unique words in Taylor's entire discography.\n");
            writer.write("The longest song on " + this.getArtistName() + "'s discography is " + TotalMaxSong.GetSongName() + " with " + TotalMaxWords + " words\n");
            writer.write("The shortest song on the album is " + TotalMinSong.GetSongName() + " with " + TotalMinWords + " words.\n\n");
            System.out.println("*** Summary file has been created ! ***");
        } catch (IOException e) {System.out.println("An error occurred.");}

    }

    public void AvgSongLengthInDiscography()//This method calculates the average song length in Taylor's discography.
    {
        System.out.println("Would you like to create a summary file ? press 1 for yes / 0 for no");
        System.out.print("----->");
        Scanner Keyboard = new Scanner(System.in);
        int FileChoice = Keyboard.nextInt();
        Main.InputCheck(0,1,Keyboard,FileChoice);
        if(FileChoice == 0)//The user chose to not create a summary file.
        {
            int TotalAvgAccumulator = 0;
            System.out.println();
            for (int i = 1; i <= this.getNumOfAlbums(); i++)
            {
                int AlbumCounter = this.getDiscography()[i].AvgSongLengthInAlbum();
                TotalAvgAccumulator += AlbumCounter;
                System.out.println("In the "+this.getDiscography()[i].GetAlbumName()+" album the song average is "+AlbumCounter/60+"m and "+AlbumCounter%60+"s.");
            }
            System.out.println("The total song average in "+this.getArtistName()+"'s discography is "+TotalAvgAccumulator/60+"m and "+TotalAvgAccumulator%60+"s");
        }
        else if(FileChoice == 1)//The user chose to create a summary file.
        {
            String FileName = "Song Average Full Summary - "+this.getArtistName()+".txt";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)))
            {
                writer.write("Summary file for song averages in "+this.getArtistName()+"'s discography\n\n");
                int TotalAvg = 0;
                for(int i = 1 ; i <= this.getNumOfAlbums() ; i++)
                {
                    writer.write("Album name : "+this.getDiscography()[i].GetAlbumName()+"\n");
                    Song[] AlbumArray = this.getDiscography()[i].GetSongsArray();
                    for(int j = 1 ; j <= this.getDiscography()[i].GetNumOfSongs() ; j++)
                        writer.write(j+") "+AlbumArray[j].GetSongName()+" : "+Song.GetSongMinutes(AlbumArray[j])+":"+Song.GetSongSeconds(AlbumArray[j])+"\n");

                    int average = this.getDiscography()[i].AvgSongLengthInAlbum();
                    TotalAvg += average;
                    writer.write("In summary, the song average in the "+this.getDiscography()[i].GetAlbumName()+" album is "+average/60+"m and "+average%60+"s.\n\n");
                }
                TotalAvg = TotalAvg / this.getNumOfAlbums();
                writer.write("***In summary, the average song length in "+this.getArtistName()+"'s discography is "+TotalAvg/60+"m and "+TotalAvg%60+"s ***");
                System.out.println("*** Summary file has been created ! ***");
            }catch (IOException e) {System.out.println("An error occurred.");}
        }
    }


    public int CountDifferentWordsInDiscography() throws IOException
    {
        Map<String, Integer> DiscographyWordCount = new HashMap<>();
        for(int i = 1 ; i <= this.getNumOfAlbums() ; i++)
        {
            Map<String, Integer> temp = this.getDiscography()[i].CountDifferentWordsInAlbum();
            // Merge map2 into map1
            temp.forEach((key, value) -> DiscographyWordCount.merge(key, value, (v1, v2) -> v1 + v2));
        }

        int res = DiscographyWordCount.size();
        return res;
    }
    public void PrintAlbums()// The method prints the album's names in order.
    {
        switch (this.getArtistNum())
        {
            case 0:
                System.out.print("\n0) Whole Discography \n1) Taylor Swift (Debut) \n2) Fearless \n3) Speak Now \n4) Red \n5) 1989 \n6) Reputation ");
                System.out.print("\n7) Lover \n8) Folklore \n9) Evermore \n10) Midnights \n11) The Tortured Poets Department (Coming Soon..) \n12) Special Songs (Soundtracks and Non-Album Collabs)");
                break;
            case 1:
                System.out.print("\n0) Whole Discography \n1) As She Pleases \n2) Life Support \n3) Silence Between Songs \n4) Specials & Collabs\n");
                break;
        }
    }

    public void PrintAlbumsTracks(int pick)// given a number that represents number of the album, the function prints the tracklist of the album.
    {
        switch (this.getArtistNum())
        {
            case 0:
            {
                switch (pick)
                {
                    case 1://For picking Debut
                        System.out.print("\n0) Entire Album \n1) Tim McGraw \n2) Picture To Burn \n3) Teardrops On My Guitar \n4) A Place In This World \n5) Cold As You \n6) The Outside \n7) Tied Together With A Smile \n8) Stay Beautiful");
                        System.out.print("\n9) Should've Said No \n10) Mary's Song (Oh My My My) \n11) Our Song \n12) I'm Only Me When I'm With You \n13) Invisible ");
                        System.out.print("\n14) A Perfectly Good Heart ");
                        break;
                    case 2://For picking Fearless (Taylor's Version)
                        System.out.print("\n0) Entire Album \n1) Fearless \n2) Fifteen \n3) Love Story \n4) Hey Stephen \n5) White Horse \n6) You Belong With Me \n7) Breath ft. Colbie Caillat \n8) Tell Me Why ");
                        System.out.print("\n9) You're Not Sorry \n10) The Way I Loved You \n11) Forever & Always \n12) The Best Day \n13) Change ");
                        System.out.print("\n14) Jump Than Fall \n15) Untouchable \n16) Come In With The Rain \n17) Superstar \n18) The Other Side Of The Door \n19) Today Was A Fairytale \n20) You All Over Me ft. Maren Morris \n21) Mr. Perfectly Fine ");
                        System.out.print("\n22) We Were Happy \n23) That's when ft. Keith Urban \n24) Don't You \n25) Bye Bye Baby ");
                        break;
                    case 3://For picking Speak Now (Taylor's Version)
                        System.out.print("\n0) Entire Album \n1) Mine \n2) Sparks Fly \n3) Back To December \n4) Speak Now \n5) Dear John \n6) Mean \n7) The Story Of Us \n8) Never Grow Up ");
                        System.out.print("\n9) Enchanted \n10) Better Than Revenge \n11) Innocent \n12) Haunted \n13) Lasy Kiss ");
                        System.out.print("\n14) Long Live \n15) Ours \n16) Superman \n17) Electric Touch ft. Fall Out Boy \n18) When Emma Falls In Love \n19) I Can See You \n20) Castles Crumbling \n21) Foolish One ");
                        System.out.print("\n22) Timeless ");
                        break;
                    case 4://For picking Red (Taylor's Version)
                        System.out.print("\n0) Entire Album \n1) State Of Grace \n2) Red \n3) Treacherous \n4) I Knew You Were Trouble \n5) All Too Well \n6) 22 \n7) I Almost Do \n8) We Are Never Getting Back Together ");
                        System.out.print("\n9) Stay Stay Stay \n10) The Last Time ft. Gary Lightbody \n11) Holy Ground \n12) Sad Beautiful Tragic \n13) The Lucky One ");
                        System.out.print("\n14) Everything Has Changed ft. Ed Sheeran \n15) Starlight \n16) Begin Again \n17) The Moment I Knew \n18) Come Back... Be Here \n19) Girl At Home \n20) Ronan \n21) Better Man ");
                        System.out.print("\n22) Nothing New ft. Phoebe Bridgers \n23) Babe \n24) Message In A Bottle \n25) I Bet You Think About Me ft. Chris Stapleton \n26) Forever Winter ");
                        System.out.print("\n27) Run ft. Ed Sheeran \n28) The Very First Night \n29) All Too Well (10 Minutes Version) ");
                        break;
                    case 5://For picking 1989 (Taylor's Version)
                        System.out.print("\n0) Entire Album \n1) Welcome to New York \n2) Blank Space \n3) Style \n4) Out Of The Woods \n5) All You Had To Do Was Stay \n6) Shake It Off \n7) I Wish You Would \n8) Bad Blood (Remix) ft. Kendrick Lamar");
                        System.out.print("\n9) Wildest Dreams \n10) How To Get The Girl \n11) This Love \n12) I Know Places \n13) Clean ");
                        System.out.print("\n14) Wonderland \n15) You Are In Love \n16) New Romantics \n17) \"Slut!\" \n18) Say Don't Go \n19) Now That We Don't Talk \n20) Suburban Legends \n21) Is It Over Now ?  ");
                        break;
                    case 6://For picking Reputation
                        System.out.print("\n0) Entire Album \n1) ...Ready For It ? \n2) End Game ft. Ed Sheeran and Future \n3) I Did Something Bad \n4) Don't Blame Me \n5) Delicate \n6) Look What You Made Me Do \n7) ...So It Goes \n8) Gorgeous");
                        System.out.print("\n9) Getaway Car \n10) King Of My Heart \n11) Dancing With Our Hands Tied \n12) Dress \n13) This Is Why We Can't Have Nice Things ");
                        System.out.print("\n14) Call It What You Want \n15) New Year's Day");
                        break;
                    case 7://For picking Lover
                        System.out.print("\n0) Entire Album \n1) I Forgot That You Existed \n2) Cruel Summer \n3) Lover \n4) The Man \n5) The Archer \n6) I Think He Knows \n7) Miss Americana & The Heartbreak Prince \n8) Paper Rings");
                        System.out.print("\n9) Cornelia Street \n10) Death By A Thousand Cuts \n11) London Boy \n12) Soon You'll Get Better ft. The Chicks \n13) False God ");
                        System.out.print("\n14) You Need To Calm Down \n15) Afterglow \n16) ME! ft. Brendon Urie \n17) It's Nice To Have A Friend \n18) Daylight \n19) All Of The Girls You Loved Before ");
                        break;
                    case 8://For picking Folklore
                        System.out.print("\n0) Entire Album \n1) The 1 \n2) Cardigan \n3) The Last Great American Dynasty \n4) Exile ft. Bon Iver \n5) My Tears Ricochet \n6) Mirrorball \n7) Seven \n8) August");
                        System.out.print("\n9) This Is Me Trying \n10) Illicit Affairs \n11) Invisible String \n12) Mad Woman \n13) Epiphany ");
                        System.out.print("\n14) Betty \n15) Peace \n16) Hoax \n17) The Lakes ");
                        break;
                    case 9://For picking Evermore
                        System.out.print("\n0) Entire Album \n1) Willow \n2) Champagne Problems \n3) Gold Rush \n4) 'Tis the Damn Season \n5) Tolerate It \n6) No Body, No Crime ft. HAIM \n7) Happiness \n8) Dorothea");
                        System.out.print("\n9) Coney Island ft. The National \n10) Ivy \n11) Cowboy Like Me \n12) Long Story Short \n13) Marjorie ");
                        System.out.print("\n14) Closure \n15) Evermore ft. Bon Iver \n16) Right Where You Left Me \n17) Time To Go ");
                        break;
                    case 10://For picking Midnights
                        System.out.print("\n0) Entire Album \n1) Lavender Haze \n2) Maroon \n3) Anti Hero \n4) Snow On The Beach ft. Lana Del Ray \n5) You're On Your Own, Kid \n6) Midnight Rain \n7) ?...Question \n8) Vigilante Shit");
                        System.out.print("\n9) Bejeweled \n10) Labyrinth \n11) Karma \n12) Sweet Nothing \n13) Mastermind ");
                        System.out.print("\n14) The Great War \n15) Bigger Than The Whole Sky \n16) Paris \n17) High Indifelity \n18) Glitch \n19) Would've, Could've, Should've \n20) Dear Reader ");
                        System.out.print("\n21) Hits Different \n22) You're Loosing Me");
                        break;
                    case 11://For picking Specials
                        System.out.print("\n0) All Songs \n1) Safe & Sound ft. The Civil Wars \n2) Eyes Open \n3) Crazier \n4) I Don't Wanna Live Forever ft. Zayn \n5) Carolina \n6) Renegade ft. Big Red Machine \n7) Christmas Tree Farm \n8) Beautiful Ghosts");
                        System.out.print("\n9) Only The Young \n10) The Joker And The Queen ft. Ed Sheeran \n11) Sweeter Than Fiction ");
                        break;
                    case 12:
                        System.out.print("\n0) All Songs \n1) Fortnight ft. Post Malone \n2) The Tortured Poets Department \n3) My Boy Only Breaks His Favorite Toys \n4) Down Bad \n5) So long,London \n6) But Daddy I Love Him \n7) Fresh Out The Slammer \n8) Florida!!! ft. Florence + The Machine");
                        System.out.print("\n9) Guilty As Sin? \n10) Who's Afraid of Little Old Me? \n11) I Can Fix Him (No I Really Can) ");
                        System.out.print("\n12) loml \n13) I Can Do it With A Broken Heart \n14) The Smallest Man Who Ever Lived \n15) The Alchemy \n16) Clara Bow \n17) --- \n18) --- ");
                        System.out.print("\n19) --- \n20) ---");
                        break;
                }
                break;
            }
            case 1:
            {
                switch(pick)
                {
                    case 1:
                        System.out.print("\n0) Entire Album \n1) Dead \n2) Fools \n3) Heartless \n4) Tyler Durden \n5) Home With You \n6) Teenage In Love \n7) Say It To My Face");
                        break;
                    case 2:
                        System.out.print("\n0) Entire Album \n1) The Beginning \n2) Good In Goodbye \n3) Default \n4) Follow The White Rabbit \n5) Effortlessly \n6) Stay Numb And Carry On \n7) Blue \n8) Interlude");
                        System.out.print("\n9) Homesick \n10) Selfish \n11) Sour Times \n12) BOYSHIT \n13) Baby ");
                        System.out.print("\n14) Stained Glass \n15) Emotional Bruises \n16) Everything Happens For a Reason \n17) Channel Surfing / The End ");
                        break;
                    case 3:
                        System.out.print("\n0) Entire Album \n1) Spinning \n2) Sweet Relief \n3) Envy The Leaves \n4) 17 \n5) Ryder \n6) Nothing Matters But You \n7) I Wonder \n8) At Your Worst");
                        System.out.print("\n9) Showed Me (How I Fell In Love With You) \n10) Home To Another One \n11) Dangerous \n12) Reckless \n13) Silence Between Songs ");
                        System.out.print("\n14) King Of Everything ");
                        break;
                }
                break;
            }
        }
        System.out.println("\n");
    }
}
