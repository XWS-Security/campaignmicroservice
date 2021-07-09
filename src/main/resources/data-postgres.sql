INSERT INTO auth_role (id, name) VALUES ('1', 'NISTAGRAM_USER_ROLE');
INSERT INTO auth_role (id, name) VALUES ('2', 'AGENT_ROLE');

INSERT INTO nistagram_user(id, enabled, username, profile_private) VALUES ('1', true, 'luka', true);
INSERT INTO nistagram_user(id, enabled, username, profile_private) VALUES ('2', true, 'vlado', false);
INSERT INTO nistagram_user(id, enabled, username, profile_private) VALUES ('3', true, 'vidoje', true);
INSERT INTO nistagram_user(id, enabled, username, profile_private) VALUES ('4', true, 'milica', true);
INSERT INTO nistagram_user(id, enabled, username, profile_private) VALUES ('5', true, 'duja', false);
INSERT INTO nistagram_user(id, enabled, username, profile_private) VALUES ('6', true, 'kobra', true);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO user_role (user_id, role_id) VALUES (6, 1);
