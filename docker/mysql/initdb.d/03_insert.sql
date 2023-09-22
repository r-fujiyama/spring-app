USE app;

INSERT INTO user (
  id
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
  1
  , 1
  , 2
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
  2
  , 2
  , 2
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
  3
  , 3
  , 2
  , 'corporate'
  , 'user'
  , 22
  , 15
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
