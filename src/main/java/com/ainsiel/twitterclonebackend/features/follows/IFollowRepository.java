package com.ainsiel.twitterclonebackend.features.follows;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFollowRepository extends JpaRepository<FollowEntity, FollowId> {


    Optional<List<FollowEntity>> findAllByFollow_Follower_Username(String username);


    Optional<List<FollowEntity>> findAllByFollow_Follows_Username(String username);


    void deleteByFollow_Follower_UsernameAndFollow_Follows_Username(
            String followerUsername, String followsUsername);


    boolean existsByFollow_Follower_UsernameAndFollow_Follows_Username(
            String followerUsername, String followsUsername);

    long countByFollow_Follower_Username(String username);

    long countByFollow_Follows_Username(String username);

}
