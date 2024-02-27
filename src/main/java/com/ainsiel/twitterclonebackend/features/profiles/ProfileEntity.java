package com.ainsiel.twitterclonebackend.features.profiles;


import com.ainsiel.twitterclonebackend.features.users.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "profiles")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 25, unique = true, nullable = false)
    private String username;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(name = "cover_url")
    private String coverURL;

    @Column(name = "avatar_url")
    private String avatarURL;

    @Column(length = 160)
    private String bio;

    @Column(length = 50)
    private String professional;

    @Column(length = 30)
    private String location;

    @Column(length = 100)
    private String website;

    private String birthdate;

    @Column(name = "joined_at")
    private LocalDate joinedAt;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

}
