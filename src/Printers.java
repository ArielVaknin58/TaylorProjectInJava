public class Printers {

    public static void PrintAlbums()// The method prints the album's names in order.
    {
        System.out.print("\n0) Whole Discography \n1) Taylor Swift (Debut) \n2) Fearless \n3) Speak Now \n4) Red \n5) 1989 \n6) Reputation ");
        System.out.print("\n7) Lover \n8) Folklore \n9) Evermore \n10) Midnights \n11) Special Songs (Soundtracks and Non-Album Collabs)\n");
    }

    public static void PrintAlbumsTracks(int pick)// given a number that represents number of the album, the function prints the tracklist of the album.
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
        }

        System.out.println("\n");
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
}
