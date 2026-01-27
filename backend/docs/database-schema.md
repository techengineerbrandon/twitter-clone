# Database Schema Design

## Users Table
Stores user account information and profile data.

| Column Name        | Data Type      | Constraints                    | Description                        |
|--------------------|----------------|--------------------------------|------------------------------------|
| id                 | BIGINT         | PRIMARY KEY, AUTO_INCREMENT    | Unique user identifier             |
| username           | VARCHAR(50)    | UNIQUE, NOT NULL               | User's unique handle (@username)   |
| email              | VARCHAR(100)   | UNIQUE, NOT NULL               | User's email address               |
| password_hash      | VARCHAR(255)   | NOT NULL                       | Bcrypt hashed password             |
| display_name       | VARCHAR(100)   | NULL                           | User's display name                |
| bio                | TEXT           | NULL                           | User biography/description         |
| profile_image_url  | VARCHAR(500)   | NULL                           | URL to profile picture             |
| created_at         | TIMESTAMP      | DEFAULT CURRENT_TIMESTAMP      | Account creation timestamp         |
| updated_at         | TIMESTAMP      | DEFAULT CURRENT_TIMESTAMP      | Last profile update timestamp      |

**Indexes:**
- Primary: id
- Unique: username, email
- Index on: created_at (for sorting new users)

---

## Follows Table
Represents follower/following relationships between users.

| Column Name   | Data Type | Constraints                      | Description                           |
|---------------|-----------|----------------------------------|---------------------------------------|
| id            | BIGINT    | PRIMARY KEY, AUTO_INCREMENT      | Unique relationship identifier        |
| follower_id   | BIGINT    | FOREIGN KEY (users.id), NOT NULL | User who is following                 |
| following_id  | BIGINT    | FOREIGN KEY (users.id), NOT NULL | User who is being followed            |
| created_at    | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP        | When the follow relationship started  |

**Constraints:**
- UNIQUE (follower_id, following_id) - prevents duplicate follows
- follower_id != following_id - users cannot follow themselves
- ON DELETE CASCADE - if user deleted, remove their follow relationships

**Indexes:**
- Primary: id
- Composite index: (follower_id, following_id)
- Index on: follower_id (to quickly get who someone follows)
- Index on: following_id (to quickly get someone's followers)

---

## Posts Table (Future - Week 2)
Will store user posts/tweets.

| Column Name   | Data Type    | Constraints                      | Description                    |
|---------------|--------------|----------------------------------|--------------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT      | Unique post identifier         |
| user_id       | BIGINT       | FOREIGN KEY (users.id), NOT NULL | Author of the post             |
| content       | VARCHAR(280) | NOT NULL                         | Post text content              |
| created_at    | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP        | Post creation time             |
| updated_at    | TIMESTAMP    | NULL                             | Last edit time (if edited)     |

**Note:** We'll expand this next week with likes, retweets, replies, etc.