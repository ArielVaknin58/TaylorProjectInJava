# TaylorProjectInJava

This project is a continuation of my other TaylorProject - but in the Java language.
My main idea to navigate between the files was to create a "monkey ladder" - start from one file and from that file to be able to get to the rest.
I have created some sort of a "local library" for Taylor's songs ; Each album has a folder which contains text files that have the lyrics to each of her songs + an album ReadMe file.
Every song is built in a specific pattern :
- The text file's name for each song is the album's name and it's serial number in the album. for example, The song "All Too Well" (fifth song on the "Red" album) is called "Red5".
- The text first line is the song name between two quotation marks.
- the second line is the duration of the song in MM:SS format and in addition the letter E is appended if the song has the "Explicit" tag.
- after that, the song lyrics were copy-pasted from the Genius website.
- the file ends right after the last sentence,I purposely removed new lines after the end of the song in order for the code to work with a specific command.

Every album has a readme file, which is in the following pattern :
- first line is the number of songs, written as : "num of songs : (some number)"
- the rest of the lines are the local paths for the text files for the album.

**for example, the reputation ReadMe file starts like that :
num of songs: 15
C:\Users\Ariel\Desktop\ProjectTaylor\Reputation\Reputation1.txt
C:\Users\Ariel\Desktop\ProjectTaylor\Reputation\Reputation2.txt
C:\Users\Ariel\Desktop\ProjectTaylor\Reputation\Reputation3.txt
......

The program gets a single file as a parameter called "AlbumsReadMe's.txt" which contains the following information :
- The number of albums Taylor (currently) has - the first line is "num of albums - 11"
- The rest of the file contains the local path for each album's ReadMe file, which allows me to access each and every song from the original file.

**The AlbumsReadMe's file starts like that :
num of albums: 11
C:\Users\Ariel\Desktop\ProjectTaylor\Debut\DebutReadMe.txt
C:\Users\Ariel\Desktop\ProjectTaylor\Fearless\FearlessReadMe.txt
C:\Users\Ariel\Desktop\ProjectTaylor\SpeakNow\SpeakNowReadMe.txt
C:\Users\Ariel\Desktop\ProjectTaylor\Red\RedReadMe.txt
.......

The files were created in a specific format to give clarity and indication about the position of the cursur during the reading of the file, and also because in the C file it made things easier.
As you can see - the program will only work for the specific paths in the files, so changes must be made to the paths only in order to make it work on a different computer.

The program has started with the basic ability to find and count appearences of words in songs, but since has developed to support more function and give more interestiing pieces of information.
currently, the program has 4 main functions :
- count the occurences of a given word in a song/album/discography.
- generate a random Taylor song.
- get information about songs' lengths (like total duration of albums/discography, average song length, longest and shortest songs on an album/discography)
- get information about the number of words in a song/album/discography.
- get information about explicit songs and the obscenities in a song/album/discography.

  All the functions offer an option to produce a summary file in order to save the information in a file after the program ends. When choosing to not create a file and rather print the information on the screen, the bottom lines will be printed and not the entire summary - for example if the user chose to get information about explicit songs in the entire discography, then the produced file will list every song on Taylor's discography and mention if it's explicit or not and also what's the album with the most explicit songs. opposed to not creating a file and then only the conclusions will be printed.

There is also a method to create a JSON file which I left in the program.

The program contains 3 classes ; Song , Album and Printers.
- Printers class : contains the print methods, like printing the welcome banner, the lists of songs and albums.
- Song class : a song object contains the song name as a string, it's path, it's duration in seconds and if the song is explicit or not as a boolean value.
- Album class : an Album object contains the album name as a string, the number of songs in the album, the serial number of the album (1 for the first,2 for the second etc),the open album file and an array of song objects to keep and approach the songs.

At first, when the methods finished running - the information would be printed to the screen and then lost ; If I wanted to re-access the information I had just printed then I had to do it all over again. So in order to get information from a file I had to read it every time- and since it has become tiring I have decided to create a data structor to save the data even after the methods end. 
The information in the program is stored in an array of albums, and as mentioned before - each album contains an array of song objects so that given the Albums array I can access every piece of information in Taylor's discography.
The method that creates this array is called at the very beginning of the program and is named CreateDiscographyArray(file). the method gets the main ReadMe file as a parameter, and reads through all the songs to get the needed information .(the method doesnt scan the entire song - for the name,length and IfExplicit only the first 2 lines are necessary.)
there are 2 initializing methods that get a song path and produce a song object with the appropriate information and a method that does the same with an album.
Storing the information in an array is really simple and has several benefits such as order, which allows me to know the order of the songs and albums and access them accordingly.
  
