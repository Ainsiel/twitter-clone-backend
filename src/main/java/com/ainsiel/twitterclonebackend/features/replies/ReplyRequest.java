package com.ainsiel.twitterclonebackend.features.replies;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequest {

    private Integer tweetIdRequested;
    private String content;
}
