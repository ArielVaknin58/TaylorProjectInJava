# TaylorProjectInJavaa

This project is a continuation of my other TaylorProject - but in the Java language.
My main idea to navigate between the files was to create a "monkey ladder" - start from one file and from that to be able to get to the rest.
I have created some sort of local library for Taylor's songs. Each album has a folder which contains text files that have the lyrics to each of her songs + an album ReadMe file.
Every song is built in a specific pattern :
- The text file's name for each song is the album's name and it's serial number in the album. for example, The song "All Too Well" (fifth song on the "Red" album) is called "Red5".
- The text first line is the song name between two quotation marks.
- the second line is the duration of the song in MM:SS format.
- after that, the song lyrics were copy pasted from the Genius website.
- the file ends right after the last sentence,there is no new line on purpose.

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

The files were created in a specific format to give clarity and indication about the position of the cursur during reading the file, and also because in the C file it made things easier.
As you can see - the program will only work for the specific paths in the files, so changes must be made to the paths only in order to make it work in a different computer.
  
