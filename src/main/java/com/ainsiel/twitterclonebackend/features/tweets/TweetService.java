package com.ainsiel.twitterclonebackend.features.tweets;

import com.ainsiel.twitterclonebackend.features.follows.FollowService;
import com.ainsiel.twitterclonebackend.features.likes.ILikeRepository;
import com.ainsiel.twitterclonebackend.features.likes.LikeEntity;
import com.ainsiel.twitterclonebackend.features.likes.LikeService;
import com.ainsiel.twitterclonebackend.features.profiles.IProfileRepository;
import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import com.ainsiel.twitterclonebackend.features.replies.ReplyEntity;
import com.ainsiel.twitterclonebackend.features.replies.ReplyRequest;
import com.ainsiel.twitterclonebackend.features.replies.ReplyService;
import com.ainsiel.twitterclonebackend.features.retweets.IRetweetRepository;
import com.ainsiel.twitterclonebackend.features.retweets.RetweetEntity;
import com.ainsiel.twitterclonebackend.features.retweets.RetweetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetService {

    @Autowired
    private final ITweetRepository tweetRepository;

    @Autowired
    private final IProfileRepository profileRepository;

    @Autowired
    private final IRetweetRepository retweetRepository;

    @Autowired
    private final ILikeRepository likeRepository;

    @Autowired
    private final FollowService followService;

    @Autowired
    private final ReplyService replyService;

    @Autowired
    private final RetweetService retweetService;

    @Autowired
    private final LikeService likeService;


    public Integer getUsernameTweetsCount(String username) {

        return (int) tweetRepository.countByProfile_Username(username);
    }

    public TweetDetailsResponse getTweetDetailsById(
            Integer id, String authHeader) {

        TweetEntity tweetDetails = getTweetById(id);

        TweetEntity parentEntity = getParentTweetEntity(id);
        List<TweetEntity> repliesEntity = getRepliesTweetEntity(id);

        TweetResponse parent = parentEntity != null ? buildTweetResponse(parentEntity, authHeader) : null;

        List<TweetResponse> replies = repliesEntity.stream().map(
                reply -> buildTweetResponse(reply, authHeader)
        ).toList();

        return buildTweetDetailsResponse(tweetDetails, authHeader, parent, replies);
    }

    public List<TweetResponse> getRandomTweetsOrderByTweetedAt(String authHeader) {

        final int MAX_TWEETS = 20;

        List<TweetEntity> randomsTweets = getRandomsTweetsEntitiesOrderByTweetedAt(MAX_TWEETS);

        return randomsTweets.stream().map(tweet -> buildTweetResponse(tweet, authHeader)).toList();
    }

    public List<TweetResponse> getFollowingTweetsOrderByTweetedAt(String authHeader) {

        List<ProfileEntity> profiles = followService.getFollowsProfilesByUsername(authHeader);

        List<TweetEntity> tweets = getAllProfilesTweetsOrderByTweetedAt(profiles);

        return tweets.stream().map(tweet -> buildTweetResponse(tweet, authHeader)).toList();
    }

    public List<TweetResponse> getTweetsByUsernameOrderByTweetedAt(
            String username, String authHeader) {

        List<TweetEntity> tweets = getAllTweetsEntitiesByUsernameOrderByTweetedAt(username);

        return tweets.stream().map(tweet -> buildTweetResponse(tweet, authHeader)).toList();
    }

    public TweetResponse createTweet(
            TweetRequest tweet, String authHeader) {

        TweetEntity t = createTweetEntity(tweet,authHeader);

        return buildTweetResponse(t, authHeader);
    }

    private TweetEntity getTweetById(Integer id){

        return tweetRepository.findById(id).orElse(null);
    }

    private List<TweetEntity> getAllTweets(){

        return tweetRepository.findAll();
    }


    private List<TweetEntity> getRepliesTweetEntity(Integer id) {

        return replyService.getRepliesByTweetId(id);
    }

    private TweetEntity getParentTweetEntity(Integer id) {

        return replyService.getParentByTweetId(id);
    }

    private TweetDetailsResponse buildTweetDetailsResponse(
            TweetEntity tweet,
            String username,
            TweetResponse parent,
            List<TweetResponse> replies){

        return TweetDetailsResponse.builder()
                .id(tweet.getId())
                .profileAvatar(tweet.getProfile().getAvatarURL())
                .profileName(tweet.getProfile().getName())
                .profileUsername(tweet.getProfile().getUsername())
                .tweetedAt(tweet.getTweetedAt().toString())
                .content(tweet.getContent())
                .isRetweeted(retweetService.isTweetRetweetedByUsername(tweet, username))
                .isLiked(likeService.isTweetLikedByUsername(tweet, username))
                .replies(replyService.getTweetTotalReplies(tweet))
                .retweets(retweetService.getTweetTotalRetweets(tweet))
                .likes(likeService.getTweetTotalLikes(tweet))
                .analytics(((int) (Math.random() * 90_001) + 10_000))
                .parent(parent)
                .repliesTweets(replies)
                .build();
    }

    private TweetResponse buildTweetResponse(TweetEntity tweet, String username){

        return TweetResponse.builder()
                .id(tweet.getId())
                .profileAvatar(tweet.getProfile().getAvatarURL())
                .profileName(tweet.getProfile().getName())
                .profileUsername(tweet.getProfile().getUsername())
                .tweetedAt(tweet.getTweetedAt().toString())
                .content(tweet.getContent())
                .isRetweeted(retweetService.isTweetRetweetedByUsername(tweet, username))
                .isLiked(likeService.isTweetLikedByUsername(tweet, username))
                .replies(replyService.getTweetTotalReplies(tweet))
                .retweets(retweetService.getTweetTotalRetweets(tweet))
                .likes(likeService.getTweetTotalLikes(tweet))
                .analytics(((int) (Math.random() * 90_001) + 10_000))
                .build();
    }

    private List<TweetEntity> getRandomsTweets(int quantity) {

        if (quantity >= 0) {
            List<TweetEntity> tweets = getAllTweets();

            Collections.shuffle(tweets);

            if (quantity >= tweets.size()) {
                return tweets;
            } else {
                return tweets.subList(0, quantity);
            }

        } else {
            return Collections.emptyList();
        }

    }

    private List<TweetEntity> getRandomsTweetsEntitiesOrderByTweetedAt(int quantity) {

        List<TweetEntity> randomTweets = getRandomsTweets(quantity);

        return randomTweets.stream()
                .sorted(Comparator.comparing(TweetEntity::getTweetedAt).reversed())
                .collect(Collectors.toList());
    }

    private List<TweetEntity> getAllTweetsEntitiesByUsername(String username) {

        return tweetRepository
                .findAllByProfile_Username(username)
                .orElse(Collections.emptyList());
    }

    private List<TweetEntity> getAllTweetsEntitiesByUsernameOrderByTweetedAt(String username) {

        List<TweetEntity> usernameTweets = getAllTweetsEntitiesByUsername(username);

        return usernameTweets.stream()
                .sorted(Comparator.comparing(TweetEntity::getTweetedAt).reversed())
                .collect(Collectors.toList());
    }

    private TweetEntity createTweetEntity(TweetRequest tweet, String username) {

        ProfileEntity profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Profile with username not found : " + username));

        TweetEntity t = TweetEntity.builder()
                .content(tweet.getContent())
                .tweetedAt(LocalDate.now())
                .profile(profile)
                .build();

        return tweetRepository.save(t);
    }

    private List<TweetEntity> getAllProfilesTweetsOrderByTweetedAt(List<ProfileEntity> profiles) {

        List<TweetEntity> profilesTweets = getAllProfilesEntitiesTweets(profiles);

        return profilesTweets
                .stream()
                .sorted(Comparator.comparing(TweetEntity::getTweetedAt).reversed())
                .collect(Collectors.toList());
    }

    private List<TweetEntity> getAllProfilesEntitiesTweets(List<ProfileEntity> profiles) {

        return profiles.stream()
                .map(ProfileEntity::getUsername)
                .map(this::getAllTweetsEntitiesByUsername)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public TweetResponse createReply(
            ReplyRequest reply,
            String usernameFromRequestHeader) {

        TweetEntity tweet = getTweetById(reply.getTweetIdRequested());
        TweetEntity replyEntity = createTweetEntity(reply.getContent(), usernameFromRequestHeader);
        ReplyEntity createdReply = replyService.saveReply(tweet,replyEntity);

        return buildTweetResponse(
                createdReply.getReply().getReply(), usernameFromRequestHeader);
    }

    private TweetEntity createTweetEntity(String content, String username) {

        ProfileEntity profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Profile with username not found : " + username));

        TweetEntity t = TweetEntity.builder()
                .content(content)
                .tweetedAt(LocalDate.now())
                .profile(profile)
                .build();

        return tweetRepository.save(t);
    }

    public List<TweetResponse> getAllProfileRepliesTweetsOrderByTweetedAt(
            String username,
            String usernameFromRequestHeader) {

        List<ReplyEntity> repliesEntities = replyService.getAllRepliesByUsername(username);
        List<TweetEntity> tweetsEntities = repliesEntities.stream()
                .map(reply -> reply.getReply().getReply()).toList();

        List<TweetEntity> sortedTweets = tweetsEntities.stream()
                .sorted(Comparator.comparing(TweetEntity::getTweetedAt).reversed())
                .toList();

        return sortedTweets.stream().map(tweet ->
                buildTweetResponse(tweet, usernameFromRequestHeader)).toList();
    }

    public List<TweetResponse> getAllProfileRetweetsTweetsOrderByTweetedAt(
            String username,
            String usernameFromRequestHeader) {

        List<RetweetEntity> retweetEntities = retweetService.getAllRetweetsByUsername(username);
        List<TweetEntity> tweetsEntities = retweetEntities.stream()
                .map(retweet -> retweet.getRetweet().getTweet()).toList();

        List<TweetEntity> sortedTweets = tweetsEntities.stream()
                .sorted(Comparator.comparing(TweetEntity::getTweetedAt).reversed())
                .toList();

        return sortedTweets.stream().map(tweet ->
                buildTweetResponse(tweet, usernameFromRequestHeader)).toList();
    }

    public TweetResponse createRetweet(Integer tweetId, String usernameFromRequestHeader) {

        ProfileEntity profile = profileRepository.findByUsername(usernameFromRequestHeader)
                .orElseThrow(() -> new EntityNotFoundException("Profile with username not found : " + usernameFromRequestHeader));
        TweetEntity tweet = getTweetById(tweetId);

        if (profile != null && tweet != null) {
            RetweetEntity retweet = retweetService.createRetweet(profile, tweet);
            return buildTweetResponse(retweet.getRetweet().getTweet(),usernameFromRequestHeader);
        }
        return null;
    }

    @Transactional
    public TweetResponse deleteRetweet(Integer tweetId, String usernameFromRequestHeader) {

        ProfileEntity profile = profileRepository.findByUsername(usernameFromRequestHeader)
                .orElseThrow(() -> new EntityNotFoundException("Profile with username not found : " + usernameFromRequestHeader));
        TweetEntity tweet = getTweetById(tweetId);

        if (profile != null && tweet != null) {
            retweetRepository
                    .deleteByRetweet_Profile_UsernameAndRetweet_Tweet_Id(
                            profile.getUsername(),
                            tweet.getId());
            return buildTweetResponse(tweet,usernameFromRequestHeader);
        }

        return null;
    }

    public List<TweetResponse> getAllProfileLikesTweetsOrderByTweetedAt(
            String username, String usernameFromRequestHeader) {

        List<LikeEntity> likeEntities = likeService.getAllLikesByUsername(username);
        List<TweetEntity> tweetsEntities = likeEntities.stream()
                .map(retweet -> retweet.getLike().getTweet()).toList();

        List<TweetEntity> sortedTweets = tweetsEntities.stream()
                .sorted(Comparator.comparing(TweetEntity::getTweetedAt).reversed())
                .toList();

        return sortedTweets.stream().map(tweet ->
                buildTweetResponse(tweet, usernameFromRequestHeader)).toList();
    }

    public TweetResponse createLike(Integer tweetId, String usernameFromRequestHeader) {

        ProfileEntity profile = profileRepository.findByUsername(usernameFromRequestHeader)
                .orElseThrow(() -> new EntityNotFoundException("Profile with username not found : " + usernameFromRequestHeader));
        TweetEntity tweet = getTweetById(tweetId);

        if (profile != null && tweet != null) {
            LikeEntity like = likeService.createLike(profile, tweet);
            return buildTweetResponse(like.getLike().getTweet(),usernameFromRequestHeader);
        }

        return null;
    }

    @Transactional
    public TweetResponse deleteLike(Integer tweetId, String usernameFromRequestHeader) {

        ProfileEntity profile = profileRepository.findByUsername(usernameFromRequestHeader)
                .orElseThrow(() -> new EntityNotFoundException("Profile with username not found : " + usernameFromRequestHeader));
        TweetEntity tweet = getTweetById(tweetId);

        if (profile != null && tweet != null) {
            likeRepository
                    .deleteByLike_Profile_UsernameAndLike_Tweet_Id(
                            profile.getUsername(),
                            tweet.getId());
            return buildTweetResponse(tweet,usernameFromRequestHeader);
        }

        return null;
    }
}
