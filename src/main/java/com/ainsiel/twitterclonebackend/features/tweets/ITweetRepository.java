package com.ainsiel.twitterclonebackend.features.tweets;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITweetRepository extends JpaRepository<TweetEntity, Integer> {

    Optional<List<TweetEntity>> findAllByProfile_Username(String username);

    long countByProfile_Username(String username);
}
