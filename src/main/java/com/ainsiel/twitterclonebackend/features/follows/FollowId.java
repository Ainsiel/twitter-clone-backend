package com.ainsiel.twitterclonebackend.features.follows;

import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FollowId {

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false)
    private ProfileEntity follower;

    @ManyToOne
    @JoinColumn(name = "follows_id", referencedColumnName = "id", nullable = false)
    private ProfileEntity follows;
}
