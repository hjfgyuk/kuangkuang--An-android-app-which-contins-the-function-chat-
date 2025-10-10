create table user
(
    id       int auto_increment
        primary key,
    name     varchar(50)                 not null,
    password varchar(50)                 null,
    sex      enum ('0', '1') default '1' null,
    avatar   varchar(70)                 null
);

INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (1, 'zty123', '123456', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (2, 'zty1234567', '15512121', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (3, 'zty', '123456', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (4, 'jsontest', '456123', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (5, 'opjhjjh', '153246', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (6, '1511515', '202020202', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (7, '151515151', '222626262262', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (8, '5415151', '15151515151', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (9, '51517', '789654', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (10, '181811818', '7878787878', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (11, '151515', '9797979797', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (12, 'heibeizhangtianyi', '123456', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (13, 'dsjdhsjdhsjh', '123456', '1', null);
INSERT INTO kuangkuang.user (id, name, password, sex, avatar) VALUES (14, 'zty222', '123456', '1', null);
