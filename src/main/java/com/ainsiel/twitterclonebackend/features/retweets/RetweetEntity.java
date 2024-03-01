package com.ainsiel.twitterclonebackend.features.retweets;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "retweets")
public class RetweetEntity {

    @EmbeddedId
    private RetweetId retweet;

    @Column(name = "retweeted_at")
    private LocalDate retweetedAt;


}
