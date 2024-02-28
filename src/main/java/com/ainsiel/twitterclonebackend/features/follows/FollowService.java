package com.ainsiel.twitterclonebackend.features.follows;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {


    public Integer getUsernameFollowingCount(String username) {
        return 0;
    }

    public Integer getUsernameFollowersCount(String username) {
        return 0;
    }

    public Boolean isUsernameRequesterFollowingUsernameRequested(String usernameRequester, String usernameRequested) {
        return false;
    }
}
