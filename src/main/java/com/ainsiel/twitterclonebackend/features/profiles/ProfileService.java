package com.ainsiel.twitterclonebackend.features.profiles;


import com.ainsiel.twitterclonebackend.features.users.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Autowired
    private final IProfileRepository profileRepository;

    public ProfileEntity createProfile(String username){
        return ProfileEntity.builder()
                .username(username)
                .name(username)
                .avatarURL("https://picsum.photos/200")
                .coverURL("https://picsum.photos/1000")
                .joinedAt(LocalDate.now())
                .build();
    }

    private ProfileEntity getProfileByUsername(String username){
        return profileRepository.findByUsername(username).orElse(null);
    }

    public ProfileEntity modifyProfile(ProfileRequest profileRequest, String username) {
        ProfileEntity p = getProfileByUsername(username);

        p.setName(profileRequest.getName());
        p.setCoverURL(profileRequest.getCoverURL());
        p.setAvatarURL(profileRequest.getAvatarURL());
        p.setBio(profileRequest.getBio());
        p.setProfessional(profileRequest.getProfessional());
        p.setLocation(profileRequest.getLocation());
        p.setWebsite(profileRequest.getWebsite());
        p.setBirthdate(profileRequest.getBirthdate());

        return profileRepository.save(p);
    }
}
