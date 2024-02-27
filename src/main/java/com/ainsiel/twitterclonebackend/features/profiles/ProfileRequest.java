package com.ainsiel.twitterclonebackend.features.profiles;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    private String name;
    private String coverURL;
    private String avatarURL;
    private String bio;
    private String professional;
    private String location;
    private String website;
    private String birthdate;
}
