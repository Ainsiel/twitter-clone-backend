package com.ainsiel.twitterclonebackend.features.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikeRepository extends JpaRepository<LikeEntity, LikeId> {

    Optional<List<LikeEntity>> findAllByLike_Profile_Username(String username);

    Optional<List<LikeEntity>>  findAllByLike_Tweet_Id(Integer tweetId);

    void deleteByLike_Profile_UsernameAndLike_Tweet_Id(String profileUsername, Integer tweetId);
}
