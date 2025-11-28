create table friend
(
    user1_id      int                                not null,
    user2_id      int                                not null,
    meet_at       datetime default CURRENT_TIMESTAMP null,
    friend_avatar varchar(50)                        null
);

INSERT INTO kuangkuang.friend (user1_id, user2_id, meet_at, friend_avatar) VALUES (3, 1, '2025-09-22 16:02:51', null);
INSERT INTO kuangkuang.friend (user1_id, user2_id, meet_at, friend_avatar) VALUES (3, 14, '2025-09-27 15:30:00', null);
