package com.ainsiel.twitterclonebackend.features.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikeRepository extends JpaRepository<LikeEntity, LikeId> {

    Optional<List<LikeEntity>> findAllByLike_Profile_Username(String username);

    long countByLike_Tweet_Id(Integer tweetId);

    boolean existsByLike_Profile_UsernameAndLike_Tweet_Id(
            String username, Integer tweetId);

    void deleteByLike_Profile_UsernameAndLike_Tweet_Id(String profileUsername, Integer tweetId);
}
