package com.teb.twitterclone.repository;

import com.teb.twitterclone.entity.Follow;
import com.teb.twitterclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Find a specific follow relationship
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    // Get all users that a specific user follows
    List<Follow> findByFollower(User follower);

    // Get all users that follow a specific user (followers)
    List<Follow> findByFollowing(User following);

    // Check if a follow relationship exists
    boolean existsByFollowerAndFollowing(User follower, User following);

    // Delete a specific follow relationship
    void deleteByFollowerAndFollowing(User follower, User following);

    // Count how many users someone follows
    long countByFollower(User follower);

    // Count how many followers someone has
    long countByFollowing(User following);

}
