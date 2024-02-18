USE app;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID'
    , name VARCHAR (256) UNIQUE NOT NULL COMMENT 'ユーザー名'
    , password VARCHAR (64) NOT NULL COMMENT 'パスワード'
    , type TINYINT NOT NULL COMMENT 'ユーザータイプ\n1:Private, 2:Freelance, 3:Corporate'
    , status TINYINT NOT NULL COMMENT 'ユーザーステータス\n0:Unregistered, 1:Registered, 2:blocked, 3:deleted'
    , first_name VARCHAR (256) NOT NULL COMMENT '名前(名)'
    , last_name VARCHAR (256) NOT NULL COMMENT '名前(性)'
    , age SMALLINT NOT NULL COMMENT '年齢'
    , updated_by VARCHAR (256) NOT NULL COMMENT '更新者'
    , updated_at TIMESTAMP NOT NULL COMMENT '更新日時'
    , created_by VARCHAR (256) NOT NULL COMMENT '登録者'
    , created_at TIMESTAMP NOT NULL COMMENT '登録日時'
    , INDEX index01(name)
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_bin;

CREATE TABLE role (
    user_id BIGINT PRIMARY KEY COMMENT 'ユーザーID'
    , allow_create BOOLEAN NOT NULL COMMENT '作成権限\n0:Disable, 1:Enable'
    , allow_read BOOLEAN NOT NULL COMMENT '読込権限\n0:Disable, 1:Enable'
    , allow_update BOOLEAN NOT NULL COMMENT '更新権限\n0:Disable, 1:Enable'
    , allow_delete BOOLEAN NOT NULL COMMENT '削除権限\n0:Disable, 1:Enable'
    , updated_by VARCHAR (256) NOT NULL COMMENT '更新者'
    , updated_at TIMESTAMP NOT NULL COMMENT '更新日時'
    , created_by VARCHAR (256) NOT NULL COMMENT '登録者'
    , created_at TIMESTAMP NOT NULL COMMENT '登録日時'
    , FOREIGN KEY fk_user_id(user_id) REFERENCES user(id) ON DELETE RESTRICT
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_bin;

CREATE TABLE api_key (
    user_id BIGINT PRIMARY KEY COMMENT 'ユーザーID'
    , api_key VARCHAR (36) UNIQUE NOT NULL COMMENT 'APIKey'
    , updated_by VARCHAR (256) NOT NULL COMMENT '更新者'
    , updated_at TIMESTAMP NOT NULL COMMENT '更新日時'
    , created_by VARCHAR (256) NOT NULL COMMENT '登録者'
    , created_at TIMESTAMP NOT NULL COMMENT '登録日時'
) engine = innodb DEFAULT charset = utf8mb4 COLLATE = utf8mb4_bin;
