USE app;

CREATE TABLE users(
    id BIGINT auto_increment PRIMARY KEY COMMENT 'ID'
    , type TINYINT NOT NULL COMMENT 'ユーザータイプ\n1:Private, 2:Freelance, 3:Corporate'
    , last_name VARCHAR (256) NOT NULL COMMENT '名前(性)'
    , first_name VARCHAR (256) NOT NULL COMMENT '名前(名)'
    , age SMALLINT NOT NULL COMMENT '年齢'
    , role TINYINT NOT NULL COMMENT '権限\n作成,読込,更新,削除を2進数で表現.\n例:"0110"では読込,更新を許可する.'
    , created_by VARCHAR (256) NOT NULL COMMENT '登録者'
    , created_at TIMESTAMP NOT NULL COMMENT '登録日時'
    , updated_by VARCHAR (256) NOT NULL COMMENT '更新者'
    , updated_at TIMESTAMP NOT NULL COMMENT '更新日時'
    , INDEX index01(type)
) engine = innodb DEFAULT charset = utf8mb4 COLLATE=utf8mb4_bin;
