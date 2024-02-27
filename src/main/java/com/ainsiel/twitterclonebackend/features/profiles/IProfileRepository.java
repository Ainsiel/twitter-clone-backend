package com.ainsiel.twitterclonebackend.features.profiles;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByUsername(String username);

}
