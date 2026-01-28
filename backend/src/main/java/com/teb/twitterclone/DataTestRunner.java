package com.teb.twitterclone;

import com.teb.twitterclone.entity.User;
import com.teb.twitterclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

// TODO: This is a temporary test runner for demo purposes.
// Replace with proper unit tests in src/test/java/
// This file will be deleted once we have proper test coverage.

@Component
@RequiredArgsConstructor
@Slf4j
public class DataTestRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("🚀 Starting database tests...");

        // Clear any existing test data
        userRepository.deleteAll();

        // TEST 1: Create a user
        log.info("📝 TEST 1: Creating a new user...");
        User newUser = User.builder()
                .username("johndoe")
                .email("john@example.com")
                .passwordHash("hashed_password_here")
                .displayName("John Doe")
                .bio("Software developer and coffee enthusiast")
                .build();

        User savedUser = userRepository.save(newUser);
        log.info("✅ User created with ID: {}", savedUser.getId());

        // TEST 2: Find user by username
        log.info("🔍 TEST 2: Finding user by username...");
        Optional<User> foundUser = userRepository.findByUsername("johndoe");
        if (foundUser.isPresent()) {
            log.info("✅ Found user: {}", foundUser.get().getUsername());
            log.info("   Email: {}", foundUser.get().getEmail());
            log.info("   Created at: {}", foundUser.get().getCreatedAt());
        } else {
            log.error("❌ User not found!");
        }

        // TEST 3: Check if username exists
        log.info("🔍 TEST 3: Checking if username exists...");
        boolean exists = userRepository.existsByUsername("johndoe");
        log.info("✅ Username 'johndoe' exists: {}", exists);

        // TEST 4: Create another user
        log.info("📝 TEST 4: Creating second user...");
        User user2 = User.builder()
                .username("janedoe")
                .email("jane@example.com")
                .passwordHash("another_hashed_password")
                .displayName("Jane Doe")
                .build();

        userRepository.save(user2);
        log.info("✅ Second user created");

        // TEST 5: Count total users
        log.info("📊 TEST 5: Counting total users...");
        long totalUsers = userRepository.count();
        log.info("✅ Total users in database: {}", totalUsers);

        // TEST 6: Find all users
        log.info("📋 TEST 6: Listing all users...");
        userRepository.findAll().forEach(user -> {
            log.info("   - {} ({})", user.getUsername(), user.getEmail());
        });

        // TEST 7: Update a user
        log.info("✏️ TEST 7: Updating user...");
        if (foundUser.isPresent()) {
            User userToUpdate = foundUser.get();
            userToUpdate.setBio("Updated bio - I love Java!");
            userRepository.save(userToUpdate);
            log.info("✅ User updated");
        }

        // TEST 8: Delete a user
        log.info("🗑️ TEST 8: Deleting a user...");
        userRepository.deleteById(savedUser.getId());
        long remainingUsers = userRepository.count();
        log.info("✅ User deleted. Remaining users: {}", remainingUsers);

        log.info("🎉 All tests completed successfully!");
    }
}