USE app;

INSERT INTO user (
  id
  , name
  , password
  , type
  , status
  , last_name
  , first_name
  , age
  , updated_by
  , updated_at
  , created_by
  , created_at
)
VALUES (
  1
  , 'private_user'
  , 'password'
  , 1
  , 1
  , 'tokyo'
  , 'taro'
  , 20
  , 'test_user'
  , '2023-01-01 00:00:00'
  , 'test_user'
  , '2023-01-01 00:00:00'
)
, (
  2
  , 'freelance_user'
  , 'password'
  , 2
  , 1
  , 'chiba'
  , 'taro'
  , 21
  , 'test_user'
  , '2023-01-01 00:00:00'
  , 'test_user'
  , '2023-01-01 00:00:00'
)
, (
  3
  , 'corporate_user'
  , 'password'
  , 3
  , 1
  , 'saitama'
  , 'taro'
  , 22
  , 'test_user'
  , '2023-01-01 00:00:00'
  , 'test_user'
  , '2023-01-01 00:00:00'
)
