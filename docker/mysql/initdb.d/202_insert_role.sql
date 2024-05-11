USE app;

INSERT INTO role (
  user_id
  , allow_create
  , allow_read
  , allow_update
  , allow_delete
  , updated_by
  , updated_at
  , created_by
  , created_at
)
VALUES (
  1
  , true
  , true
  , true
  , true
  , 'test_user'
  , '2023-01-01 00:00:00'
  , 'test_user'
  , '2023-01-01 00:00:00'
)
, (
  2
  , true
  , true
  , true
  , true
  , 'test_user'
  , '2023-01-01 00:00:00'
  , 'test_user'
  , '2023-01-01 00:00:00'
)
, (
  3
  , true
  , true
  , true
  , true
  , 'test_user'
  , '2023-01-01 00:00:00'
  , 'test_user'
  , '2023-01-01 00:00:00'
)
