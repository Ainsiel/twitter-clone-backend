package com.ainsiel.twitterclonebackend.features.profiles;


import com.ainsiel.twitterclonebackend.features.follows.FollowService;
import com.ainsiel.twitterclonebackend.features.tweets.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Autowired
    private final IProfileRepository profileRepository;

    @Autowired
    private final FollowService followService;

    @Autowired
    private final TweetService tweetService;

    public ProfileEntity createProfile(String username){

        return ProfileEntity.builder()
                .username(username)
                .name(username)
                .avatarURL("https://picsum.photos/200")
                .coverURL("https://picsum.photos/1000")
                .joinedAt(LocalDate.now())
                .build();
    }

    public ProfileEntity getProfileByUsername(String username){

        return profileRepository.findByUsername(username).orElse(null);
    }

    private ProfileEntity modifyProfile(ProfileRequest profileRequest, String username) {

        ProfileEntity p = getProfileByUsername(username);

        p.setName(profileRequest.getName());
        p.setCoverURL(profileRequest.getCoverURL());
        p.setAvatarURL(profileRequest.getAvatarURL());
        p.setBio(profileRequest.getBio());
        p.setProfessional(profileRequest.getProfessional());
        p.setLocation(profileRequest.getLocation());
        p.setWebsite(profileRequest.getWebsite());
        p.setBirthdate(profileRequest.getBirthdate());

        return profileRepository.save(p);
    }

    public ProfileResponse getProfileByUsername(String usernameRequested, String usernameRequester){

        ProfileEntity p = getProfileByUsername(usernameRequested);

        return ProfileResponse.builder()
                .name(p.getName())
                .username(p.getUsername())
                .bio(p.getBio())
                .location(p.getLocation())
                .website(p.getWebsite())
                .professional(p.getProfessional())
                .birthdate(p.getBirthdate())
                .joinedAt(p.getJoinedAt().toString())
                .coverURL(p.getCoverURL())
                .avatarURL(p.getAvatarURL())
                .following(followService.getUsernameFollowingCount(usernameRequested))
                .followers(followService.getUsernameFollowersCount(usernameRequested))
                .totalTweets(tweetService.getUsernameTweetsCount(usernameRequested))
                .isFollowing(followService
                        .isUsernameRequesterFollowingUsernameRequested(
                                usernameRequester,
                                usernameRequested))
                .build();
    }

    public UserProfileResponse getUserProfileByUsername(String username) {

        ProfileEntity p = getProfileByUsername(username);

        return UserProfileResponse.builder()
                .name(p.getName())
                .username(p.getUsername())
                .bio(p.getBio())
                .location(p.getLocation())
                .website(p.getWebsite())
                .professional(p.getProfessional())
                .birthdate(p.getBirthdate())
                .joinedAt(p.getJoinedAt().toString())
                .coverURL(p.getCoverURL())
                .avatarURL(p.getAvatarURL())
                .following(followService.getUsernameFollowingCount(username))
                .followers(followService.getUsernameFollowersCount(username))
                .totalTweets(tweetService.getUsernameTweetsCount(username))
                .build();
    }

    public UserProfileResponse modifyUserProfileByUsername(
            ProfileRequest profileRequest,
            String username) {

        ProfileEntity p = modifyProfile(profileRequest,username);

        return UserProfileResponse.builder()
                .name(p.getName())
                .username(p.getUsername())
                .bio(p.getBio())
                .location(p.getLocation())
                .website(p.getWebsite())
                .professional(p.getProfessional())
                .birthdate(p.getBirthdate())
                .joinedAt(p.getJoinedAt().toString())
                .coverURL(p.getCoverURL())
                .avatarURL(p.getAvatarURL())
                .following(followService.getUsernameFollowingCount(username))
                .followers(followService.getUsernameFollowersCount(username))
                .totalTweets(tweetService.getUsernameTweetsCount(username))
                .build();
    }
}
