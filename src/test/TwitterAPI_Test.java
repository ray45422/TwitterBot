package test;

import java.io.IOException;
import java.util.Scanner;
import twitter4j.TwitterException;
import twitterAPI.Tweet;

public class TwitterAPI_Test {
	public static void main(String[] args) throws TwitterException, IOException {
		Tweet twitter=new Tweet();
		twitter.setConsumer("write ConsumerKey","write ConsumerSecret");
		twitter.setAccessToken("write AccessToken","write AccessTokenSecret");
		Scanner scan=new Scanner(System.in);
		String text=scan.next();
		twitter.tweet(text);
		scan.close();
	}

}
