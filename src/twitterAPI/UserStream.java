package twitterAPI;

import java.io.IOException;

import twitter4j.*;
import twitter4j.auth.AccessToken;

public class UserStream{
	private String consumerKey="";
	private String consumerSecret="";
	private String accessToken="";
	private String accessTokenSecret="";
	public void run() throws TwitterException, IOException{
	    TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	    twitterStream.setOAuthConsumer(consumerKey, consumerSecret);
		twitterStream.setOAuthAccessToken(new AccessToken(accessToken,accessTokenSecret));
	    twitterStream.addListener(listener);
	    twitterStream.user();
	}
	private final UserStreamListener listener = new UserStreamListener(){
		@Override
        public void onStatus(Status status) {
            System.out.println(status.getUser().getName()+" / @" + status.getUser().getScreenName() + " : " + status.getText());
        }
        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        @Override
        public void onDeletionNotice(long directMessageId, long userId) {}
        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        }
        @Override
        public void onScrubGeo(long userId, long upToStatusId) {}
        @Override
        public void onStallWarning(StallWarning warning) {}
        @Override
        public void onFriendList(long[] friendIds) {}
        @Override
        public void onFavorite(User source, User target, Status favoritedStatus) {
            System.out.println("onFavorite source:@"
                    + source.getScreenName() + " target:@"
                    + target.getScreenName() + " @"
                    + favoritedStatus.getUser().getScreenName() + " - "
                    + favoritedStatus.getText());
        }
        @Override
        public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
        	System.out.println("onFavorite source:@"
                    + source.getScreenName() + " target:@"
                    + target.getScreenName() + " @"
                    + unfavoritedStatus.getUser().getScreenName() + " - "
                    + unfavoritedStatus.getText());
        }
        @Override
        public void onFollow(User source, User followedUser) {
           /* System.out.println("onFollow source:@"
                    + source.getScreenName() + " target:@"
                    + followedUser.getScreenName());*/
        	System.out.println("@"+source.getScreenName()+"が"
                    +"@"+followedUser.getScreenName()+"をフォローしました");
        }
        @Override
        public void onUnfollow(User source, User followedUser) {
            /*System.out.println("onFollow source:@"
                    + source.getScreenName() + " target:@"
                    + followedUser.getScreenName());*/
        	System.out.println("@"+source.getScreenName()+"が"
                    +"@"+followedUser.getScreenName()+"をアンフォローしました");
        }
        @Override
        public void onDirectMessage(DirectMessage directMessage) {}
        @Override
        public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {}
        @Override
        public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {}
        @Override
        public void onUserListSubscription(User subscriber, User listOwner, UserList list) {}
        @Override
        public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {}
        @Override
        public void onUserListCreation(User listOwner, UserList list) {}
        @Override
        public void onUserListUpdate(User listOwner, UserList list) {}
        @Override
        public void onUserListDeletion(User listOwner, UserList list) {}
        @Override
        public void onUserProfileUpdate(User updatedUser) {}
        @Override
        public void onBlock(User source, User blockedUser) {}
        @Override
        public void onUnblock(User source, User unblockedUser) {}
        @Override
        public void onException(Exception ex) {}
		@Override
		public void onUserSuspension(long suspendedUser) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		@Override
		public void onUserDeletion(long deletedUser) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
    };
}
