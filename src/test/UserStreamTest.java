package test;

import java.io.IOException;
import twitter4j.TwitterException;
import twitterAPI.UserStream;

public class UserStreamTest {
	public static void main(String[] args) throws TwitterException, IOException {
		UserStream twitter=new UserStream();
		twitter.setConsumer("write ConsumerKey","write ConsumerSecret");
		twitter.setAccessToken("write AccessToken","write AccessTokenSecret");
		twitter.addListener(UserStream.sampleListener);
		twitter.run();
	}

}
