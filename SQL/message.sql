create table message
(
    id        int auto_increment
        primary key,
    message   varchar(500)                       not null,
    group_id  int                                not null,
    user_id   int                                not null,
    time      datetime default CURRENT_TIMESTAMP null,
    user_name varchar(50)                        null
);

INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (1, '测试消息', 1, 3, '2025-09-15 10:47:53', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (2, '测试消息2', 1, 3, '2025-09-15 10:47:53', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (3, '測試', 1, 3, '2025-09-20 15:24:31', null);
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (4, 'hello', 1, 1, '2025-09-20 15:36:47', null);
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (5, 'hello', 1, 3, '2025-09-20 15:37:24', null);
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (6, '完全', 1, 3, '2025-09-20 17:12:27', null);
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (7, 'qq', 1, 3, '2025-09-21 22:23:13', null);
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (8, 'wwww', 1, 3, '2025-09-21 22:23:35', null);
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (9, 'qq', 1, 3, '2025-09-21 22:28:28', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (10, 'aa', 1, 1, '2025-09-21 22:36:13', 'zty123');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (11, 'qq', 1, 3, '2025-09-22 10:32:40', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (12, 'rr', 1, 3, '2025-09-22 10:32:46', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (13, 'wqw', 1, 14, '2025-09-27 15:48:42', 'zty222');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (14, '你好', 1, 3, '2025-10-09 16:29:47', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (15, 'gg', 1, 1, '2025-10-09 16:30:06', 'zty123');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (16, 'ww', 1, 1, '2025-10-09 16:34:13', 'zty123');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (17, '问问
', 1, 3, '2025-10-09 16:49:35', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (18, '往往', 1, 3, '2025-10-09 16:51:38', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (19, 'QQ', 1, 3, '2025-10-09 16:51:57', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (20, 'QQ', 1, 3, '2025-10-09 16:54:31', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (21, '你好', 1, 3, '2025-10-09 17:03:00', 'zty');
INSERT INTO kuangkuang.message (id, message, group_id, user_id, time, user_name) VALUES (22, '哈哈哈哈', 1, 3, '2025-10-09 17:03:31', 'zty');
