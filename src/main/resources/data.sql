
INSERT INTO user_details(id, name, birth_date)
VALUES(10001, 'Rahul', current_date());

INSERT INTO user_details(id, name, birth_date)
VALUES(10002, 'Rahi', current_date());

INSERT INTO user_details(id, name, birth_date)
VALUES(10003, 'Satendra', current_date());

INSERT INTO post(id, description, user_id)
VALUES(20001, 'Complete this project by today', 10001);

INSERT INTO post(id, description, user_id)
VALUES(20002, 'Start Wroking on Resume', 10001);

INSERT INTO post(id, description, user_id)
VALUES(20003, 'Start Applying for Jobs', 10001);

INSERT INTO post(id, description, user_id)
VALUES(20004, 'Work on a project and publish them', 10001);