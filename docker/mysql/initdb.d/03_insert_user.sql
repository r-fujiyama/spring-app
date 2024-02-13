USE app;

INSERT INTO user (
  user_id
  , password
  , user_type
  , user_status
  , last_name
  , first_name
  , age
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
  , 'tokyo'
  , 'taro'
  , 20
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
  , 'chiba'
  , 'taro'
  , 21
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
  , 'saitama'
  , 'taro'
  , 22
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
