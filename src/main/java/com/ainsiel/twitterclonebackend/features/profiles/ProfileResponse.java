package com.ainsiel.twitterclonebackend.features.profiles;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    String name;
    String username;
    String bio;
    String location;
    String website;
    String professional;
    String birthdate;
    String joinedAt;
    String coverURL;
    String avatarURL;
    Integer following;
    Integer followers;
    Integer totalTweets;
    Boolean isFollowing;
}
