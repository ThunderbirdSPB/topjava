DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00:00', 'Завтрак', 500, 100000),
       ('2020-01-30 13:00:00', 'Обед', 1000, 100000),
       ('2020-01-30 20:00:00', 'Ужин', 500, 100000),
       ('2020-01-31 0:00:00', 'Еда на граничное значение', 100, 100000),
       ('2020-01-31 10:00:00', 'Завтрак', 500, 100000),
       ('2020-01-31 13:00:00', 'Обед', 1000, 100000),
       ('2020-01-31 20:00:00', 'Ужин', 510, 100000),
       ('2020-01-31 14:00:00', 'Админ ланч', 510, 100001),
       ('2020-01-31 21:00:00', 'Админ ужин', 1500, 100001);

insert into meals (user_id, date_time, description, calories) values (100001, '2020-12-19 10:07:04', 'Breakfast', 416);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-07-11 06:51:10', 'Breakfast', 2128);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-11-12 05:31:33', 'Afternoon snack', 1084);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-04-15 07:37:50', 'Afternoon snack', 1907);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-03-12 10:28:08', 'Breakfast', 637);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-04-27 17:25:54', 'Lunch', 1021);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-11-06 20:52:52', 'Afternoon snack', 1016);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-23 13:29:11', 'Lunch', 1488);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-24 18:05:52', 'Breakfast', 1885);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-07-31 05:01:50', 'Dinner', 1806);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-04-02 03:11:45', 'Lunch', 2141);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-11-04 16:57:43', 'Lunch', 1516);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-09-02 14:08:47', 'Afternoon snack', 1223);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-21 18:27:00', 'Dinner', 1151);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-06-21 03:23:57', 'Dinner', 2311);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-10-20 15:00:41', 'Afternoon snack', 188);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-02-23 13:02:57', 'Lunch', 2424);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-12-11 08:33:31', 'Afternoon snack', 630);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-07-26 20:14:39', 'Breakfast', 124);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-01-28 02:36:48', 'Breakfast', 193);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-10-30 15:17:21', 'Lunch', 393);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-01-14 16:20:49', 'Dinner', 1763);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-08-21 02:26:31', 'Dinner', 915);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-09-22 07:18:20', 'Afternoon snack', 671);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-11-28 06:55:57', 'Lunch', 1094);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-05-24 12:40:46', 'Breakfast', 1027);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-07 23:40:14', 'Breakfast', 1264);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-09 21:07:11', 'Breakfast', 237);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-02-23 13:13:34', 'Breakfast', 458);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-12-19 15:59:33', 'Breakfast', 1027);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-01-28 15:07:42', 'Afternoon snack', 665);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-02-15 22:47:26', 'Dinner', 523);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-05-01 17:05:41', 'Breakfast', 1584);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-04-26 12:27:14', 'Afternoon snack', 1223);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-06-24 14:54:56', 'Breakfast', 2403);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-03-05 09:29:18', 'Dinner', 1779);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-08-07 01:08:14', 'Afternoon snack', 447);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-02-15 07:53:14', 'Dinner', 1753);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-03-18 04:42:59', 'Dinner', 1835);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-06-16 12:48:25', 'Afternoon snack', 2190);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-07-17 17:23:08', 'Dinner', 535);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-03-24 19:22:11', 'Afternoon snack', 739);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-04-09 12:54:06', 'Breakfast', 790);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-01-25 22:27:16', 'Afternoon snack', 849);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-12-07 16:26:23', 'Lunch', 502);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-12-07 10:26:48', 'Lunch', 2042);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-10-25 10:30:16', 'Lunch', 1921);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-11-12 21:36:36', 'Afternoon snack', 1728);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-07-03 02:35:27', 'Dinner', 2160);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-05-26 08:29:47', 'Dinner', 1488);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-05 04:23:25', 'Breakfast', 767);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-12-30 04:48:41', 'Afternoon snack', 680);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-03-28 07:20:17', 'Dinner', 1434);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-04-15 01:31:39', 'Afternoon snack', 1887);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-12-03 12:14:47', 'Dinner', 613);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-11-08 09:21:10', 'Lunch', 2300);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-04-10 19:50:50', 'Lunch', 145);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-09-08 04:41:04', 'Afternoon snack', 1231);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-09-30 12:04:16', 'Lunch', 2206);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-02-27 01:53:06', 'Afternoon snack', 2359);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-21 07:22:37', 'Lunch', 2048);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-01-22 21:36:03', 'Lunch', 384);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-09-30 07:12:44', 'Breakfast', 2297);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-07-13 21:12:28', 'Lunch', 1294);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-11-03 05:32:54', 'Afternoon snack', 630);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-12-28 18:00:21', 'Lunch', 1065);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-08-19 10:16:37', 'Breakfast', 1332);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-12-17 17:22:44', 'Lunch', 1745);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-06-29 04:33:40', 'Breakfast', 1100);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-10-17 00:19:45', 'Breakfast', 705);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-12-28 09:15:09', 'Afternoon snack', 440);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-02-06 09:50:00', 'Dinner', 18);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-10-02 14:03:40', 'Lunch', 1156);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-11-17 06:54:01', 'Breakfast', 149);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-01-21 12:08:01', 'Afternoon snack', 1099);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-09-19 07:02:42', 'Lunch', 1236);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-06-17 06:06:11', 'Afternoon snack', 414);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-12-28 05:53:39', 'Lunch', 1307);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-12-06 14:16:22', 'Lunch', 17);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-02-24 17:08:12', 'Dinner', 519);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-02-19 04:23:16', 'Afternoon snack', 819);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-04-15 08:03:04', 'Lunch', 932);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-06-12 20:04:10', 'Afternoon snack', 821);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-02-24 19:36:58', 'Dinner', 1953);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-05-20 23:40:18', 'Afternoon snack', 1156);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-04-14 11:08:17', 'Breakfast', 652);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-05-20 18:48:53', 'Lunch', 1830);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-01-02 08:14:00', 'Afternoon snack', 568);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-03-12 23:24:25', 'Dinner', 71);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-07-14 06:44:41', 'Breakfast', 2286);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-10-21 12:24:49', 'Dinner', 1904);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-06-12 02:30:43', 'Dinner', 987);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-04-27 11:53:14', 'Lunch', 663);
insert into meals (user_id, date_time, description, calories) values (100001, '2020-11-14 09:20:21', 'Dinner', 1526);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-01-20 01:30:42', 'Lunch', 54);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-04-12 03:28:57', 'Lunch', 1590);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-04-13 04:02:44', 'Lunch', 1733);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-05-10 00:28:50', 'Dinner', 1501);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-05-16 18:51:13', 'Dinner', 8);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-05-23 23:35:49', 'Breakfast', 442);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-04-08 05:50:52', 'Dinner', 1047);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-08-23 23:51:44', 'Lunch', 1916);
insert into meals (user_id, date_time, description, calories) values (100000, '2020-12-09 02:56:40', 'Afternoon snack', 2226);
insert into meals (user_id, date_time, description, calories) values (100001, '2021-08-04 11:35:03', 'Dinner', 972);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-08-14 04:23:01', 'Dinner', 1194);
insert into meals (user_id, date_time, description, calories) values (100000, '2021-06-25 16:41:38', 'Lunch', 1671);