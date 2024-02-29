package com.ainsiel.twitterclonebackend.features.follows;


import com.ainsiel.twitterclonebackend.features.profiles.IProfileRepository;
import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import com.ainsiel.twitterclonebackend.features.profiles.ProfileResponse;
import com.ainsiel.twitterclonebackend.features.tweets.ITweetRepository;
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
    private final IProfileRepository profileRepository;

    @Autowired
    private final ITweetRepository tweetRepository;

    private ProfileEntity getProfileByUsername(String username){

        return profileRepository.findByUsername(username).orElse(null);
    }

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

        ProfileEntity follower = getProfileByUsername(followerUsername);
        ProfileEntity follows = getProfileByUsername(followsUsername);

        if (follower != null && follows != null) {
            FollowId id = new FollowId(follower,follows);
            return followRepository.save(new FollowEntity(id));
        }

        return null;
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

        return profiles.stream().map(profile ->
                buildProfileResponse(profile, username, usernameFromRequestHeader)).toList();
    }

    public List<ProfileResponse> getFollowersByUsername(
            String username,
            String usernameFromRequestHeader) {

        List<FollowEntity> follows = getFollowersByUsername(username);
        List<ProfileEntity> profiles = follows.stream().map( follow -> follow
                .getFollow().getFollower()).toList();

        return profiles.stream().map(profile ->
                buildProfileResponse(profile, username, usernameFromRequestHeader)
        ).toList();
    }

    public ProfileResponse followProfileByUsername(
            String username,
            String usernameFromRequestHeader) {

        FollowEntity follow = createFollow(usernameFromRequestHeader,username);
        if (follow != null){
            ProfileEntity profile = follow.getFollow().getFollows();

            return buildProfileResponse(profile, username, usernameFromRequestHeader);
        }

        return null;
    }

    @Transactional
    public ProfileResponse unfollowProfileByUsername(
            String username,
            String usernameFromRequestHeader) {

        followRepository
                .deleteByFollow_Follower_UsernameAndFollow_Follows_Username(
                        usernameFromRequestHeader,
                        username
                );
        ProfileEntity profile = getProfileByUsername(username);

        return buildProfileResponse(profile, username, usernameFromRequestHeader);
    }

    public List<ProfileEntity> getFollowsProfilesByUsername(String username){

        List<FollowEntity> follows = getFollowsByUsername(username);

        return follows.stream().map( follow -> follow
                .getFollow().getFollows()).toList();

    }
    private ProfileResponse buildProfileResponse(
            ProfileEntity profile,
            String username,
            String usernameFromRequestHeader){

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
                .totalTweets( (int) tweetRepository.countByProfile_Username(profile.getUsername()) )
                .isFollowing(isUsernameRequesterFollowingUsernameRequested(
                        usernameFromRequestHeader,
                        username))
                .build();
    }
}
