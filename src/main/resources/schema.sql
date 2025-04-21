/*drop table if exists user cascade;
drop table if exists matching cascade;
drop table if exists room cascade;
drop table if exists chat cascade;
drop table if exists blocking cascade;
*/

-- ブロックユーザーテーブル
CREATE TABLE blocking(
	block_user_id INTEGER REFERENCES user(user_id)
);


--ユーザーテーブル
CREATE TABLE user(
	-- 会員ID : 主キー
	user_id SERIAL PRIMARY KEY,
	-- 外部認証 : 
	user_pass VARCHAR(100) NOT NULL,
	-- 登録名
	user_name VARCHAR(20) NOT NULL,
	-- 年齢
	user_age INTEGER NOT NULL,
	-- 性別
	user_gender BOOLEAN NOT NULL,
	-- 好きな部位
	user_likes VARCHAR(20),
	-- 焼肉奉行
	user_bugyo BOOLEAN,
	-- 喫煙者か
	user_smoker BOOLEAN,
	-- 飲酒するか
	user_drinker BOOLEAN,
	-- 紹介文
	user_detail VARCHAR(50),
	-- 評価good
	user_good INTEGER,
	-- 評価bad
	user_bad INTEGER,
	-- アイコン
	user_icom BYTEA,
	-- アドレス
	user_address VARCHAR(100) NOT NULL,
	-- ブロックユーザー
	blocking_user_id INTEGER REFERENCES blocking(block_user_id)
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
	user_id INTEGER NOT NULL REFERENCES user(user_id),
	-- 性別 
	matching_gender BOOLEAN,
	-- 年齢
	matching_age INTEGER,
	-- 人数
	matching_member INTEGER,
	-- ブロックしているユーザー
	blocking_user_id INTEGER REFERENCES user(blocking_user_id),
	);

-- ルームテーブル
CREATE TABLE room(
 	-- ルームID : 主キー
	room_id SERIAL PRIMARY KEY,
	-- ユーザーID
	user_id INTEGER REFERENCES user(user_id),
	--　ルーム期限
	delete_at INTEGER,
	-- マッチング希望条件id
	matching_id INTEGER REFERENCES matching(matching_id)
);

-- チャットテーブル
CREATE TABLE chat(
	-- ルームID 
	room_id INTEGER REFERENCES room(room_id),
	-- ユーザーID
	user_id INTEGER REFERENCES user(user_id),
	-- 登録名
	user_name VARCHAR(20) REFERENCES user(user_name),
	-- コメント
	chat_comment TEXT,
	-- 時間
	create_at TIMESTAMP
);

