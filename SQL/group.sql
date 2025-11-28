create table `group`
(
    id          int auto_increment
        primary key,
    name        varchar(255)                       not null,
    description text                               null,
    created_by  int                                not null,
    created_at  datetime default CURRENT_TIMESTAMP null,
    avatar_url  varchar(500)                       null,
    max_members int      default 500               null
);

INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (1, '测试组', '测试组描述', 3, '2025-08-29 15:26:42', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (2, '测试组2', '测试组描述', 2, '2025-09-08 12:24:03', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (3, '测试组2', '测试组描述', 2, '2025-09-15 10:40:50', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (4, '测试组2', '测试组描述', 2, '2025-09-15 10:40:51', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (5, '测试组2', '测试组描述', 2, '2025-09-15 10:40:53', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (6, '测试组2', '测试组描述', 2, '2025-09-15 10:40:53', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (7, '测试组2', '测试组描述', 2, '2025-09-15 10:40:54', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (8, '测试组2', '测试组描述', 2, '2025-09-15 10:40:54', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (9, '测试组2', '测试组描述', 2, '2025-09-15 10:40:54', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (10, '测试组2', '测试组描述', 2, '2025-09-15 10:40:54', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (11, '测试组2', '测试组描述', 2, '2025-09-15 10:40:55', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (12, '测试组2', '测试组描述', 2, '2025-09-15 10:40:55', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (13, '测试组2', '测试组描述', 2, '2025-09-15 10:40:55', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (14, '测试组2', '测试组描述', 2, '2025-09-15 10:40:56', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (15, '测试组2', '测试组描述', 2, '2025-09-15 10:40:56', null, 500);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (21, 'qq', 'test', 3, '2025-09-28 21:43:28', null, 50);
INSERT INTO kuangkuang.`group` (id, name, description, created_by, created_at, avatar_url, max_members) VALUES (22, 'wx', 'test', 3, '2025-09-28 22:11:00', null, 50);
