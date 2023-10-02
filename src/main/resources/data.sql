INSERT INTO "USER_ACCOUNT" (ID, VERSION, EMAIL, PASSWORD, ROLE) VALUES
(1, 1, 'admin@example.com', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'ADMIN'),
(2, 1, 'user1@example.com', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'USER'),
(3, 1, 'user2@example.com', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'USER');
INSERT INTO "BABY" (ID, VERSION, NAME, BIRTHDAY, USER_ACCOUNT_ID) VALUES
(6, 1, 'Naima', '2012-01-01', 2),
(7, 1, 'Avaya', '2012-01-01', 3);
INSERT INTO "EVENT" (ID, VERSION, EVENT_TYPE, START_DATE, END_DATE, NOTES, BABY_ID, STATUS) VALUES
(11, 1, 'BREAST_FEEDING', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Had to pause due to baby''s fussiness, resumed later.', 6, 'INTERRUPTED'),
(12, 1, 'SLEEPING', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Peaceful nap, steady breathing observed.',  7, 'INTERRUPTED'),
(13, 1, 'DIAPER', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Successful diaper change, baby now clean and comfy.', 6, 'COMPLETED'),
(14, 1, 'BATHING', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Baby enjoyed the warm bath, splashing and giggling.', 7, 'COMPLETED');
INSERT INTO "EVENT" (ID, VERSION, EVENT_TYPE, START_DATE, END_DATE, NOTES, BABY_ID, STATUS) VALUES
(36, 1, 'CRYING', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Crying episode resolved, baby calm and content.', 7, 'COMPLETED'),
(37, 1, 'DIAPER', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Successful diaper change, baby clean and happy.',  6, 'COMPLETED'),
(38, 1, 'BOTTLE_FEEDING', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Baby finished the bottle, seems satisfied.', 6, 'COMPLETED'),
(39, 1, 'DIAPER', '1987-10-19T05:14:36', '1987-10-19T05:14:36', 'Diaper change process started', 7, 'STARTED');
