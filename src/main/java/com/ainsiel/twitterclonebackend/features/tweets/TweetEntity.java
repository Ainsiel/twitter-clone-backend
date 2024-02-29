package com.ainsiel.twitterclonebackend.features.tweets;


import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import jakarta.persistence.*;
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
@Table(name = "tweets")
public class TweetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(name = "tweeted_at")
    private LocalDate tweetedAt;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private ProfileEntity profile;
}
