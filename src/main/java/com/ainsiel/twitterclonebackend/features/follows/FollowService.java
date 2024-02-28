package com.ainsiel.twitterclonebackend.features.follows;


import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import com.ainsiel.twitterclonebackend.features.profiles.ProfileResponse;
import com.ainsiel.twitterclonebackend.features.profiles.ProfileService;
import com.ainsiel.twitterclonebackend.features.tweets.TweetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    @Autowired
    private final IFollowRepository followRepository;

    @Autowired
    private final ProfileService profileService;

    @Autowired
    private final TweetService tweetService;

    private List<FollowEntity> getFollowsByUsername(String username){

        return followRepository
                .findAllByFollow_Follower_Username(username)
                .orElse(Collections.emptyList());
    }

    private List<FollowEntity> getFollowersByUsername(String username){

        return followRepository
                .findAllByFollow_Follows_Username(username)
                .orElse(Collections.emptyList());
    }

    private FollowEntity createFollow(
            String followerUsername,
            String followsUsername){

        ProfileEntity follower = profileService.getProfileByUsername(followerUsername);
        ProfileEntity follows = profileService.getProfileByUsername(followsUsername);

        if (follower != null && follows != null) {
            FollowId id = new FollowId(follower,follows);
            return followRepository.save(new FollowEntity(id));
        }

        return null;
    }

    @Transactional
    private void deleteFollow(
            String followerUsername,
            String followsUsername) {

        followRepository
                .deleteByFollow_Follower_UsernameAndFollow_Follows_Username(
                        followerUsername,
                        followsUsername
                );
    }


    public Integer getUsernameFollowingCount(String username) {

        return (int) followRepository.countByFollow_Follower_Username(username);
    }

    public Integer getUsernameFollowersCount(String username) {

        return (int) followRepository.countByFollow_Follows_Username(username);
    }

    public Boolean isUsernameRequesterFollowingUsernameRequested(
            String usernameRequester,
            String usernameRequested) {

        return followRepository
                .existsByFollow_Follower_UsernameAndFollow_Follows_Username(
                        usernameRequester, usernameRequested);
    }

    public List<ProfileResponse> getFollowsByUsername(
            String username,
            String usernameFromRequestHeader) {

        List<FollowEntity> follows = getFollowsByUsername(username);
        List<ProfileEntity> profiles = follows.stream().map( follow -> follow
                .getFollow().getFollows()).toList();

        return profiles.stream().map(profile -> ProfileResponse.builder()
                .name(profile.getName())
                .username(profile.getUsername())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .website(profile.getWebsite())
                .professional(profile.getProfessional())
                .birthdate(profile.getBirthdate())
                .joinedAt(profile.getJoinedAt().toString())
                .coverURL(profile.getCoverURL())
                .avatarURL(profile.getAvatarURL())
                .following(getUsernameFollowingCount(profile.getUsername()))
                .followers(getUsernameFollowersCount(profile.getUsername()))
                .totalTweets(tweetService.getUsernameTweetsCount(profile.getUsername()))
                .isFollowing(isUsernameRequesterFollowingUsernameRequested(
                        usernameFromRequestHeader,
                        username))
                .build()).toList();
    }

    public List<ProfileResponse> getFollowersByUsername(
            String username,
            String usernameFromRequestHeader) {

        List<FollowEntity> follows = getFollowersByUsername(username);
        List<ProfileEntity> profiles = follows.stream().map( follow -> follow
                .getFollow().getFollower()).toList();

        return profiles.stream().map(profile -> ProfileResponse.builder()
                .name(profile.getName())
                .username(profile.getUsername())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .website(profile.getWebsite())
                .professional(profile.getProfessional())
                .birthdate(profile.getBirthdate())
                .joinedAt(profile.getJoinedAt().toString())
                .coverURL(profile.getCoverURL())
                .avatarURL(profile.getAvatarURL())
                .following(getUsernameFollowingCount(profile.getUsername()))
                .followers(getUsernameFollowersCount(profile.getUsername()))
                .totalTweets(tweetService.getUsernameTweetsCount(profile.getUsername()))
                .isFollowing(isUsernameRequesterFollowingUsernameRequested(
                        usernameFromRequestHeader,
                        username))
                .build()).toList();
    }

    public ProfileResponse followProfileByUsername(
            String username,
            String usernameFromRequestHeader) {
        FollowEntity follow = createFollow(usernameFromRequestHeader,username);
        ProfileEntity profile = follow.getFollow().getFollows();

        return ProfileResponse.builder()
                .name(profile.getName())
                .username(profile.getUsername())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .website(profile.getWebsite())
                .professional(profile.getProfessional())
                .birthdate(profile.getBirthdate())
                .joinedAt(profile.getJoinedAt().toString())
                .coverURL(profile.getCoverURL())
                .avatarURL(profile.getAvatarURL())
                .following(getUsernameFollowingCount(profile.getUsername()))
                .followers(getUsernameFollowersCount(profile.getUsername()))
                .totalTweets(tweetService.getUsernameTweetsCount(profile.getUsername()))
                .isFollowing(isUsernameRequesterFollowingUsernameRequested(
                        usernameFromRequestHeader,
                        username))
                .build();
    }

    public ProfileResponse unfollowProfileByUsername(
            String username,
            String usernameFromRequestHeader) {
        deleteFollow(usernameFromRequestHeader,username);
        ProfileEntity profile = profileService.getProfileByUsername(username);

        return ProfileResponse.builder()
                .name(profile.getName())
                .username(profile.getUsername())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .website(profile.getWebsite())
                .professional(profile.getProfessional())
                .birthdate(profile.getBirthdate())
                .joinedAt(profile.getJoinedAt().toString())
                .coverURL(profile.getCoverURL())
                .avatarURL(profile.getAvatarURL())
                .following(getUsernameFollowingCount(profile.getUsername()))
                .followers(getUsernameFollowersCount(profile.getUsername()))
                .totalTweets(tweetService.getUsernameTweetsCount(profile.getUsername()))
                .isFollowing(isUsernameRequesterFollowingUsernameRequested(
                        usernameFromRequestHeader,
                        username))
                .build();
    }
}
