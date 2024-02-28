package com.ainsiel.twitterclonebackend.features.follows;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFollowRepository extends JpaRepository<FollowEntity, FollowId> {

    // Encontrar todos los follows de un username
    Optional<List<FollowEntity>> findAllByFollow_Follower_Username(String username);

    // Encontrar todos los followers de un username
    Optional<List<FollowEntity>> findAllByFollow_Follows_Username(String username);

    // Eliminar un follow por username del usuario emisor y el usuario remitente
    void deleteByFollow_Follower_UsernameAndFollow_Follows_Username(
            String followerUsername, String followsUsername);

    // Nuevo método para verificar si un usuario sigue a otro
    boolean existsByFollow_Follower_UsernameAndFollow_Follows_Username(
            String followerUsername, String followsUsername);

    long countByFollow_Follower_Username(String username);

    long countByFollow_Follows_Username(String username);

}
