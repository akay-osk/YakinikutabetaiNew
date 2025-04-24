--　ダミーデータ　作成者　奥野



-- user 1件目
INSERT INTO users(user_pass,user_name,user_age,user_gender,user_likes,user_detail,user_address)
VALUES
('aaa','じろう',35,false,'上ミノ','お酒が本体','aba@aaa');s

-- user 2件目
INSERT INTO users(user_pass,user_name,user_age,user_gender,user_likes,user_detail,user_address)
VALUES
('bbb','ハラミちゃん',25,true,'ハラミ','ピアノ弾けません','ccc@paa');

-- user 3件目
INSERT INTO users(user_pass,user_name,user_age,user_gender,user_likes,user_detail,user_address)
VALUES
('ccc','すたみな太郎',28,false,'カルビ','米も喰え！！','bbb@kar');


-- tags 1件目 
INSERT INTO tags(tag_name) VALUES('タバコ〇');
-- tags
INSERT INTO tags(tag_name) VALUES('タバコ×');
-- tags
INSERT INTO tags(tag_name) VALUES('お酒好き');
-- tags
INSERT INTO tags(tag_name) VALUES('下戸です');
-- tags
INSERT INTO tags(tag_name) VALUES('焼肉奉行！');
-- tags
INSERT INTO tags(tag_name) VALUES('食べる専門！');
-- tags
INSERT INTO tags(tag_name) VALUES('ご飯派');
-- tags
INSERT INTO tags(tag_name) VALUES('デザート必須');
-- tags
INSERT INTO tags(tag_name) VALUES('よく焼き派');
-- tags
INSERT INTO tags(tag_name) VALUES('野菜必須！');
-- tags
INSERT INTO tags(tag_name) VALUES('冷麺必須！')