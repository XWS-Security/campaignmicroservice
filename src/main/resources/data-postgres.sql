
INSERT INTO public.auth_role (id, name) VALUES ('1', 'NISTAGRAM_USER_ROLE');
INSERT INTO public.auth_role (id, name) VALUES ('2', 'ADMINISTRATOR_ROLE');

INSERT INTO public.nistagram_user(id, enabled, last_password_reset_date, username)
	VALUES ('1', true, '2017-10-01 21:58:58.508-07', 'luka');