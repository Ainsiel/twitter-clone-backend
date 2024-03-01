package com.ainsiel.twitterclonebackend.features.replies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IReplyRepository extends JpaRepository<ReplyEntity, ReplyId> {

    Optional<List<ReplyEntity>> findAllByReply_Reply_Profile_Username(String username);

    Optional<List<ReplyEntity>> findAllByReply_Tweet_Id(Long id);
}
