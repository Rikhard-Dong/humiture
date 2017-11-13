CREATE DATABASE IF NOT EXISTS db_humiture
  CHARSET utf8;

USE db_humiture;

ALTER TABLE db_humiture.t_user_bind
  DROP FOREIGN KEY t_user_bind_ibfk_1;
ALTER TABLE db_humiture.t_user_author
  DROP FOREIGN KEY t_user_author_ibfk_1;
ALTER TABLE db_humiture.t_user_author
  DROP FOREIGN KEY t_user_author_ibfk_2;
ALTER TABLE db_humiture.t_temper_humid
  DROP FOREIGN KEY t_temper_humid_ibfk_1;
ALTER TABLE db_humiture.t_repair
  DROP FOREIGN KEY t_repair_ibfk_1;
ALTER TABLE db_humiture.t_rent
  DROP FOREIGN KEY t_rent_ibfk_1;
ALTER TABLE db_humiture.t_rent
  DROP FOREIGN KEY t_rent_ibfk_2;
ALTER TABLE db_humiture.t_user_info
  DROP FOREIGN KEY t_user_info_ibfk_1;
ALTER TABLE db_humiture.t_node
  DROP FOREIGN KEY t_node_ibfk_1;
DROP TABLE db_humiture.t_user_bind;
DROP TABLE db_humiture.t_user_author;
DROP TABLE db_humiture.t_temper_humid;
DROP TABLE db_humiture.t_repair;
DROP TABLE db_humiture.t_rent;
DROP TABLE db_humiture.t_user_info;
DROP TABLE db_humiture.t_node;
DROP TABLE db_humiture.t_unit;
DROP TABLE db_humiture.t_getway;
DROP TABLE IF EXISTS test;


CREATE TABLE IF NOT EXISTS t_getway (
  getway_id    INT(4)      NOT NULL AUTO_INCREMENT
  COMMENT '网关ID',
  getway_mark  VARCHAR(50) NOT NULL UNIQUE
  COMMENT '节点盒子对应的ID',
  spare_node   BOOLEAN     NOT NULL DEFAULT FALSE
  COMMENT '是否为备用节点',
  node_num     VARCHAR(50) NOT NULL
  COMMENT 'DTU的SNR',
  status       INT(4)      NOT NULL
  COMMENT '节点状态',
  now_temper   FLOAT COMMENT '当前温度',
  now_humidity FLOAT COMMENT '当前湿度',
  `time_inter` INT(4)      NOT NULL
  COMMENT '上报时间间隔',
  memo         VARCHAR(50) COMMENT '当前湿度',
  PRIMARY KEY (`getway_id`),
  INDEX (`getway_mark`)
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT '网关信息表';


CREATE TABLE IF NOT EXISTS t_node (
  node_id        INT(4)      NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  node_mark      VARCHAR(50) NOT NULL UNIQUE
  COMMENT '节点盒子对应ID',
  `getway_id`    INT(4)
  COMMENT '所属网关节点ID',
  `spare_node`   BOOLEAN     NOT NULL DEFAULT FALSE
  COMMENT '是否备用节点',
  `node_num`     VARCHAR(50) NOT NULL
  COMMENT '节点号',
  `type`         SMALLINT(2) NOT NULL
  COMMENT '节点类型 0为网关, 1为节点',
  `status`       SMALLINT(2) NOT NULL
  COMMENT '节点状态 0 在线 1不在线',
  `now_temper`   FLOAT(4)
  COMMENT '当前节点温度',
  `now_humidity` FLOAT(4)
  COMMENT '当前节点湿度',
  `time_inter`   INT(4)      NOT NULL
  COMMENT '上报时间间隔',
  `memo`         VARCHAR(100) COMMENT '备注',
  PRIMARY KEY (`node_id`),
  INDEX (`getway_id`),
  INDEX (`node_mark`),
  INDEX (`node_num`),
  FOREIGN KEY (`getway_id`) REFERENCES t_getway (`getway_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'node节点信息表';

CREATE TABLE IF NOT EXISTS t_unit (
  `unit_id`   INT(4)       NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `title`     VARCHAR(100) NOT NULL UNIQUE
  COMMENT '单位名称',
  `address`   VARCHAR(100) COMMENT '单位地址',
  `person`    VARCHAR(50) COMMENT '联系人',
  `phone`     VARCHAR(50) COMMENT '联系人电话',
  `email`     VARCHAR(20) COMMENT '联系人邮箱',
  `unit_type` SMALLINT(2)  NOT NULL
  COMMENT '单位类型',
  `memo`      VARCHAR(100) COMMENT '备注',
  PRIMARY KEY (`unit_id`)
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'Unit单位信息表';

CREATE TABLE IF NOT EXISTS t_user_info (
  `user_id`   INT(4)      NOT NULL  AUTO_INCREMENT
  COMMENT '用户ID, 主键',
  `unit_id`   INT(4)
  COMMENT '单位ID, 外键',
  `name`      VARCHAR(20) COMMENT '用户姓名',
  `user_type` SMALLINT(2) NOT NULL
  COMMENT '用户类型 0 系统管理员 1 特权单位管理员 2 单位管理员 3. 特权单位管理员',
  `username`  VARCHAR(32) NOT NULL UNIQUE
  COMMENT '登录名',
  `password`  VARCHAR(50) NOT NULL
  COMMENT '用户密码',
  `memo`      VARCHAR(50) NOT NULL
  COMMENT '备注',
  PRIMARY KEY (`user_id`),
  FOREIGN KEY (`unit_id`) REFERENCES t_unit (`unit_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE

)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'UserInfo用户信息表';

CREATE TABLE IF NOT EXISTS t_rent (
  `rent_id`     INT(4)   NOT NULL AUTO_INCREMENT
  COMMENT '租赁ID 主键',
  `getway_id`   INT(4)   NOT NULL
  COMMENT '节点ID 外键',
  `unit_id`     INT(4)   NOT NULL
  COMMENT '单位ID 外键',
  `start_time`  DATETIME NOT NULL
  COMMENT '租用开始时间',
  `end_time`    DATETIME NOT NULL
  COMMENT '租用结束时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '提交时间',
  `pay`         FLOAT    NOT NULL
  COMMENT '费用',
  `status`      INT(4)
  COMMENT '租赁状态, 0-过期 1-租约中 2-未开始',
  PRIMARY KEY (`rent_id`),
  FOREIGN KEY (`getway_id`) REFERENCES t_getway (`getway_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (`unit_id`) REFERENCES t_unit (`unit_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'Rent租赁表';

CREATE TABLE IF NOT EXISTS t_user_author (
  `user_author_id` INT(4) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `node_id`        INT(4) NOT NULL
  COMMENT '节点ID',
  `user_id`        INT(4) NOT NULL
  COMMENT '用户ID',
  PRIMARY KEY (`user_author_id`),
  FOREIGN KEY (`node_id`) REFERENCES t_node (`node_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES t_user_info (`user_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'UserAuthor用户授权表';


CREATE TABLE IF NOT EXISTS t_temper_humid (
  `temper_humid_id` INT(4)      NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `node_id`         INT(4)      NOT NULL
  COMMENT '节点ID',
  `temper`          FLOAT       NOT NULL
  COMMENT '温度',
  `humidity`        FLOAT       NOT NULL
  COMMENT '湿度',
  `report_time`     DATETIME    NOT NULL
  COMMENT '上报时间',
  `type`            SMALLINT(1) NOT NULL
  COMMENT '上报类型(0为网管, 1为节点)',
  PRIMARY KEY (`temper_humid_id`),
  FOREIGN KEY (`node_id`) REFERENCES t_node (`node_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'Temper温湿度信息表';

CREATE TABLE IF NOT EXISTS t_repair (
  `repair_id`  INT(4)            NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `node_id`    INT(4)            NOT NULL
  COMMENT '故障节点ID, 外键',
  `fault_desc` VARCHAR(100)      NOT NULL
  COMMENT '故障描述',
  `phone`      VARCHAR(20)       NOT NULL
  COMMENT '联系人电话',
  `person`     VARCHAR(50)       NOT NULL
  COMMENT '联系人',
  `address`    VARCHAR(50)       NOT NULL
  COMMENT '联系地址',
  `fault_time` DATETIME                   DEFAULT CURRENT_TIMESTAMP
  COMMENT '故障上报时间',
  `status`     INT(4) DEFAULT 0  NOT NULL
  COMMENT '状态 0 未处理 1 处理中 2 处理完成',
  PRIMARY KEY (`repair_id`),
  FOREIGN KEY (`node_id`) REFERENCES t_node (`node_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'Repair报修表';

CREATE TABLE IF NOT EXISTS t_user_bind (
  `user_bind_id` INT(4)      NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `open_id`      VARCHAR(50) NOT NULL
  COMMENT '微信号',
  `node_id`      INT(4)      NOT NULL
  COMMENT '节点ID',
  `status`       INT(4)      NOT NULL
  COMMENT '节点状态',
  `name`         VARCHAR(50) NOT NULL
  COMMENT '用户姓名',
  PRIMARY KEY (`user_bind_id`),
  FOREIGN KEY (`node_id`) REFERENCES t_node (`node_id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB, CHARACTER SET utf8, COMMENT 'UserBind用户绑定表';

CREATE TABLE IF NOT EXISTS test (
  `id`    INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `value` INT NOT NULL
);

# CREATE PROCEDURE test_procedure()
#   BEGIN
#     INSERT INTO test (value) VALUES ('111');
#   END;
#
# CREATE EVENT second_event
#   ON SCHEDULE EVERY 1 HOUR
#   ON COMPLETION PRESERVE ENABLE
# DO CALL test_procedure();