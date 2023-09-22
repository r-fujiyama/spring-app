USE app;

INSERT INTO user (
  user_id
  , password
  , user_type
  , user_status
  , last_name
  , first_name
  , age
  , role
  , updated_by
  , updated_at
  , created_by
  , created_at
)
VALUES (
  'private_user'
  , 'password'
  , 1
  , 1
  , 'private'
  , 'user'
  , 20
  , 15
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
, (
  'freelance_user'
  , 'password'
  , 2
  , 1
  , 'freelance'
  , 'user'
  , 21
  , 15
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
, (
  'corporate_user'
  , 'password'
  , 3
  , 1
  , 'corporate'
  , 'user'
  , 22
  , 15
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
