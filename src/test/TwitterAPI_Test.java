package test;

import java.io.IOException;
import java.util.Scanner;
import twitter4j.TwitterException;
import twitterAPI.RestAPI;

public class TwitterAPI_Test {
	public static void main(String[] args) throws TwitterException, IOException {
		RestAPI twitter=new RestAPI();
		twitter.setConsumer("write ConsumerKey","write ConsumerSecret");
		twitter.setAccessToken("write AccessToken","write AccessTokenSecret");
		Scanner scan=new Scanner(System.in);
		String text=scan.next();
		twitter.tweet(text);
		scan.close();
	}

}
