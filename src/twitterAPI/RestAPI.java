package twitterAPI;

import java.io.File;
import java.util.List;
import java.util.Map;
import twitter4j.*;
import twitter4j.auth.AccessToken;


public class RestAPI {
	private String screenName="";
	private String accessToken="";
	private String accessTokenSecret="";
	private String consumerKey="";
	private String consumerSecret="";
	private Twitter twitter = new TwitterFactory().getInstance();
	public RestAPI(){
		
	}
	public RestAPI(String consumerKey,String consumerSecret){
		this.consumerKey=consumerKey;
		this.consumerSecret=consumerSecret;
		twitter.setOAuthConsumer(consumerKey,consumerSecret);
	}
	public void setConsumer(String consumerKey,String consumerSecret){
		this.consumerKey=consumerKey;
		this.consumerSecret=consumerSecret;
		twitter.setOAuthConsumer(consumerKey,consumerSecret);
	}
	public void setAccessToken(String accessToken,String accessTokenSecret){
		this.accessToken=accessToken;
		this.accessTokenSecret=accessTokenSecret;
		twitter.setOAuthAccessToken(new AccessToken(accessToken,accessTokenSecret));
	}
	public void init(){
		twitter.setOAuthConsumer(consumerKey,consumerSecret);
		twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
	}
	public void tweet(String tweet){
		try{
			twitter.updateStatus(tweet);
			System.out.println("ツイートした");
		} catch(TwitterException e){
			System.err.println("ツイート失敗"+e.getMessage());
			if(e.getErrorCode()==187){
				tweet+=" !";
				tweet(tweet);
			}
		}
	}
	public void reply(String tweet,Status status){
		try{
			twitter.updateStatus(new StatusUpdate(tweet).inReplyToStatusId(status.getId()));
			System.out.println("返信した");
		} catch(TwitterException e){
			System.err.println("返信失敗"+e.getMessage());
			if(e.getErrorCode()==187){
				tweet+=" !";
				reply(tweet,status);
			}
		}
	}
	public boolean follow(long userId) {
		User user=null;
		boolean v=false;
		try {
			user = twitter.createFriendship(userId,true);
		} catch (TwitterException e) {
			e.printStackTrace();
		} finally {
			if(user == null) {
				System.out.println(userId+"のフォローに失敗しました");
				v=false; 
			} else {
				System.out.println(user.getName()+"をフォローしました");
				v=true;
			}
		}
		return v;
	}
	public boolean unfollow(long userId) {
		User user=null;
		boolean v=false;
		try {
			user = twitter.destroyFriendship(userId);
		} catch (TwitterException e) {
			e.printStackTrace();
		} finally {
			if(user == null){ 
				System.out.println(userId+"のフォロー解除に失敗しました");
				v=false;
			}else{
				System.out.println(user.getName()+"をフォロー解除しました");
				v=true;
			}
		}
		return v;
	}
	public boolean updateName(String name){
		User my=null;
		try {
			my=twitter.showUser(screenName);
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
		User user=null;
		boolean v=false;
		try {
			user=twitter.updateProfile(name,my.getURL(),my.getLocation(),my.getDescription());
		} catch (TwitterException e) {
			e.printStackTrace();
		} finally {
			if(user==null){
				System.out.println("プロフィール更新に失敗しました");
				v=false;
			}else{
				System.out.println("プロフィールを更新しました");
				v=true;
			}
		}
		return v;
	}
	public boolean setIcon(String path){
		User user=null;
		boolean v=false;
		try {
			File file=new File(path);
			user=twitter.updateProfileImage(file);
		} catch (TwitterException e) {
			e.printStackTrace();
		} finally {
			if(user==null){
				System.out.println("プロフィール更新に失敗しました");
				v=false;
			}else{
				System.out.println("プロフィールを更新しました");
				v=true;
			}
		}
		return v;
	}
	public boolean favorite(long id){
		Status status=null;
		boolean v=false;
		try {
			status=twitter.createFavorite(id);
		} catch (TwitterException e) {
			e.printStackTrace();
		} finally {
			if(status==null){
				System.out.println("お気に入り登録に失敗しました");
				v=false;
			} else {
				System.out.println("お気に入りに登録しました");
				v=true;
			}
		}
		return v;
	}
	private boolean retweet(long id){
		Status status=null;
		boolean v=false;
		try {
			status=twitter.retweetStatus(id);
		} catch (TwitterException e) {
			e.printStackTrace();
		} finally {
			if(status==null){
				System.out.println("リツイートに失敗しました");
				v=false;
			} else {
				System.out.println("リツイートしました");
				v=true;
			}
		}
		return v;
	}
	public boolean favRet(long id){
		if(favorite(id) && retweet(id))
			return true;
		return false;
	}
	public List<Status> getTweets(String screenName,Paging p){
		ResponseList<Status> list = null;
		try {
			list = twitter.getUserTimeline(screenName,p);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		System.out.println(getRestApiRemaining());
		return list;
	}
	public List<Status> getTweets(String screenName){
		ResponseList<Status> list = null;
		try {
			list = twitter.getUserTimeline(screenName);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		System.out.println(getRestApiRemaining());
		return list;
	}
	public User getUser(String screenName){
		User user=null;
		try {
			user=twitter.showUser(screenName);
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
		return user;
	}
	public Relationship getRelationship(String source,String target){
		Relationship rs=null;
		try {
			rs=twitter.showFriendship(source,target);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public boolean isFollowing(String target){
		Relationship rs=null;
		try {
			rs=twitter.showFriendship(screenName,target);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		if(rs!=null){
			boolean b=rs.isSourceFollowingTarget();
			return b;
		}else{
			return false;
		}
	}
	public boolean isFollowed(String target){
		Relationship rs=null;
		try {
			rs=twitter.showFriendship(screenName,target);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		if(rs!=null){
			boolean b=rs.isSourceFollowedByTarget();
			return b;
		}else{
			return false;
		}
	}
	public Map<String,RateLimitStatus> getRateLimitStatus(){
		Map<String,RateLimitStatus> s=null;
		try {
			s=twitter.getRateLimitStatus();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return s;
	}
	public RateLimitStatus getRateLimitStatus(String key){
		Map<String,RateLimitStatus> s=getRateLimitStatus();
		RateLimitStatus rls=null;
		if(s.containsKey(key)){
			rls=s.get(key);
		}
		return rls;
	}
	public int getRestApiRemaining(){
		int n=0;
		n=getRateLimitStatus("/statuses/user_timeline").getRemaining();
		return n;
	}
}
