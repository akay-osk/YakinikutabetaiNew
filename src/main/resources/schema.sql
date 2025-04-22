drop table if exists users cascade;
drop table if exists blocking cascade;
drop table if exists matching cascade;
drop table if exists room cascade;
drop table if exists chat cascade;




--ユーザーテーブル
CREATE TABLE users(
	-- ユーザーID : 主キー
	user_id SERIAL PRIMARY KEY,
	-- 外部認証 : 
	user_pass VARCHAR(100) NOT NULL,
	-- 登録名
	user_name VARCHAR(50) NOT NULL,
	-- 年齢
	user_age INTEGER NOT NULL,
	-- 性別
	user_gender BOOLEAN NOT NULL,
	-- 好きな部位
	user_likes VARCHAR(50),
	-- 焼肉奉行
	user_bugyo BOOLEAN DEFAULT false,
	-- 喫煙者か
	user_smoker BOOLEAN DEFAULT false,
	-- 飲酒するか
	user_drinker BOOLEAN DEFAULT false,
	-- 紹介文
	user_detail VARCHAR(50),
	-- 評価good
	user_good INTEGER DEFAULT 0,
	-- 評価bad
	user_bad INTEGER DEFAULT 0,
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
	blocking_user_id INTEGER REFERENCES users(user_id)
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



