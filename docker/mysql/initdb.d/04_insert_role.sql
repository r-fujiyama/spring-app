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
  'private_user'
  , true
  , true
  , true
  , true
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
, (
  'freelance_user'
  , true
  , true
  , true
  , true
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
, (
  'corporate_user'
  , true
  , true
  , true
  , true
  , 'test user'
  , '2023-01-01 00:00:00'
  , 'test user'
  , '2023-01-01 00:00:00'
)
