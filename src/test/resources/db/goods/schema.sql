DROP TABLE IF EXISTS goods;

CREATE TABLE goods
(
    goods_id   BIGINT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    goods_name varchar(500)   NOT NULL DEFAULT '' COMMENT 'name',
    subject   varchar(200)   NOT NULL DEFAULT '' COMMENT '标题',
    price     decimal(15, 2) NOT NULL DEFAULT '0.00' COMMENT '价格',
    stock     int(11) NOT NULL DEFAULT '0' COMMENT 'stock',
    PRIMARY KEY (`goods_id`),
    UNIQUE KEY `goods_name` (`goods_name`)
)