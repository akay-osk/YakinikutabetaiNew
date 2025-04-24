drop table if exists users cascade;
drop table if exists blocking cascade;
drop table if exists matching cascade;
drop table if exists room cascade;
drop table if exists chat cascade;





--ユーザーテーブル
CREATE TABLE users(
	-- ユーザーID : 主キー
	user_id SERIAL PRIMARY KEY,
	-- パスワード : 
	user_pass VARCHAR(100) NOT NULL,
	-- 登録名
	user_name VARCHAR(50) NOT NULL,
	-- 年齢
	user_age INTEGER NOT NULL,
	-- 性別
	user_gender BOOLEAN NOT NULL,
	-- 好きな部位
	user_likes VARCHAR(20),
	-- 紹介文
	user_detail VARCHAR(15),
	-- アイコン
	user_icon BYTEA,
	-- アドレス
	user_address VARCHAR(255) NOT NULL
);


-- ブロックユーザーテーブル
CREATE TABLE blocking(
	-- ユーザーID
	 user_id INTEGER REFERENCES users(user_id),
	-- 上記ユーザーIDがブロックしているユーザーID
	blocking_user_id INTEGER REFERENCES users(user_id),
	  -- 一意制約を追加
    CONSTRAINT unique_block UNIQUE (user_id, blocking_user_id)
);


-- マッチング希望条件保存テーブル
CREATE TABLE matching(
	-- マッチング条件ID : 主キー
	matching_id SERIAL PRIMARY KEY,
	-- 参加日
	matching_day DATE NOT NULL,
	-- 時間
	matching_time INTEGER NOT NULL,
	-- 会員ID : user_id引用
	user_id INTEGER NOT NULL REFERENCES users(user_id),
	-- 性別 
	matching_gender BOOLEAN,
	-- 年齢
	matching_age INTEGER,
	-- 人数
	matching_member INTEGER,
	-- ブロックしているユーザー
	blocking_user_id INTEGER REFERENCES users(user_id),
	-- 希望エリア
	matching_area VARCHAR(10)
	);

-- ルームテーブル
CREATE TABLE room(
 	-- ルームID : 主キー
	room_id SERIAL PRIMARY KEY,
	-- ユーザーID
	user_id INTEGER REFERENCES users(user_id),
	--　ルーム期限
	delete_at TIMESTAMP,
	-- マッチング希望条件id
	matching_id INTEGER REFERENCES matching(matching_id)
);

-- チャットテーブル
CREATE TABLE chat(
	-- ルームID 
	room_id INTEGER REFERENCES room(room_id),
	-- ユーザーID
	user_id INTEGER REFERENCES users(user_id),
	-- 登録名
	user_name VARCHAR(20),
	-- コメント
	chat_comment TEXT,
	-- 時間
	create_at TIMESTAMP
);

--　タグテーブル
CREATE TABLE tags(
	-- タグID
	tag_id SERIAL PRIMARY KEY,
	tag_name VARCHAR(10) NOT NULL
);

-- 	ユーザーとタグの中間テーブル
CREATE TABLE user_tags(
	user_id INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
	tag_id INTEGER NOT NULL REFERENCES tags(tag_id) ON DELETE CASCADE,
	PRIMARY KEY (user_id,tag_id)
);

