/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/9/23 13:55:17                           */
/*==============================================================*/


drop table if exists jc_banner;

drop table if exists jc_bus_develop;

drop table if exists jc_bus_develop_type;

drop table if exists jc_contact_us;

drop table if exists jc_news;

drop table if exists jc_node;

drop table if exists jc_partner;

drop table if exists jc_post;

drop table if exists jc_post_type;

drop table if exists jc_social_responsib;

drop table if exists jc_sys_dict;

/*==============================================================*/
/* Table: jc_banner                                             */
/*==============================================================*/
create table jc_banner
(
   id                   bigint(12) not null auto_increment comment '序号',
   title                varchar(128) comment '标题',
   path                 varchar(255) comment '地址',
   url                  varchar(255) comment '图片链接',
   show_flag            char(1) comment '是否展示',
   create_by            varchar(64) comment '添加人',
   create_date          datetime comment '添加时间',
   update_by            varchar(64) comment '修改人',
   update_date          datetime comment '修改时间',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_bus_develop                                        */
/*==============================================================*/
create table jc_bus_develop
(
   id                   bigint(11) not null comment '序号',
   title                varchar(255) comment '标题',
   img                  varchar(300) comment '图片',
   gener                varchar(300) comment '概括',
   content              longtext comment '详细信息',
   home_featured        char(1) comment '首页推荐',
   create_date          datetime comment '创建时间',
   create_by            varchar(64) comment '创建人',
   update_date          datetime comment '修改时间',
   update_by            varchar(64) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_bus_develop_type                                   */
/*==============================================================*/
create table jc_bus_develop_type
(
   id                   bigint(11) not null comment '序号',
   name                 varchar(255) comment '名称',
   gener                varchar(300) comment '简介',
   create_date          datetime comment '创建时间',
   create_by            varchar(64) comment '创建人',
   update_date          datetime comment '修改时间',
   update_by            varchar(64) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_contact_us                                         */
/*==============================================================*/
create table jc_contact_us
(
   id                   bigint(11) not null comment '序号',
   name                 varchar(255) comment '联系人',
   tel                  varchar(64) comment '联系电话',
   fax                  varchar(64) comment '传真',
   email                varchar(64) comment '邮箱',
   lng                  double(5,5) comment '经度',
   lat                  double(5,5) comment '纬度',
   addr                 varchar(250) comment '地址',
   banner_img           varchar(250) comment '图片',
   create_date          datetime comment '创建时间',
   create_by            varchar(64) comment '创建人',
   update_date          datetime comment '修改时间',
   update_by            varchar(64) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_news                                               */
/*==============================================================*/
create table jc_news
(
   id                   bigint(11) not null auto_increment comment '序号',
   title                varchar(64) comment '标题',
   gener                varchar(300) comment '概括',
   content              longtext comment '内容',
   from_path            varchar(64) comment '来源',
   img                  varchar(250) comment '图片',
   home_featured        char(1) comment '首页推荐',
   click                bigint(16) comment '点击量',
   create_by            varchar(64) comment '添加人',
   create_date          datetime comment '添加时间',
   update_by            varchar(64) comment '修改人',
   update_date          datetime comment '修改时间',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_node                                               */
/*==============================================================*/
create table jc_node
(
   id                   bigint(11) not null auto_increment comment '序号',
   name                 varchar(128) comment '名称',
   type                 char(1) comment '类型',
   level                int(11) default 0 comment '级别',
   status               char(1) comment '状态',
   url                  varchar(255) comment '链接',
   css                  varchar(255) comment '样式',
   path                 varchar(255) comment '图片',
   parent_id            bigint(11) comment '父节点ID',
   create_by            varchar(64) comment '添加人',
   create_date          datetime comment '添加时间',
   update_by            varchar(64) comment '修改人',
   update_date          datetime comment '修改时间',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_partner                                            */
/*==============================================================*/
create table jc_partner
(
   id                   bigint(11) not null comment '序号',
   name                 varchar(255) comment '名称',
   url                  varchar(500) comment '地址',
   self_flag            char(1) comment '是否本集团',
   create_date          datetime comment '创建时间',
   create_by            varchar(64) comment '创建人',
   update_date          datetime comment '修改时间',
   update_by            varchar(64) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_post                                               */
/*==============================================================*/
create table jc_post
(
   id                   bigint(11) not null comment '序号',
   name                 varchar(255) comment '名称',
   duty                 longtext comment '岗位职责',
   claim                longtext comment '任职资格',
   type                 bigint(11) comment '类型',
   num                  varchar(250) comment '招聘人数',
   work_addr            varchar(250) comment '工作地址',
   create_date          datetime comment '创建时间',
   create_by            varchar(64) comment '创建人',
   update_date          datetime comment '修改时间',
   update_by            varchar(64) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_post_type                                          */
/*==============================================================*/
create table jc_post_type
(
   id                   bigint(11) not null comment '序号',
   name                 varchar(255) comment '名称',
   create_date          datetime comment '创建时间',
   create_by            varchar(64) comment '创建人',
   update_date          datetime comment '修改时间',
   update_by            varchar(64) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_social_responsib                                   */
/*==============================================================*/
create table jc_social_responsib
(
   id                   bigint(11) not null comment '序号',
   name                 varchar(255) comment '标题',
   img                  varchar(255) comment '图片',
   gener                varchar(250) comment '概括',
   content              longtext comment '详情',
   create_date          datetime comment '创建时间',
   create_by            varchar(64) comment '创建人',
   update_date          datetime comment '修改时间',
   update_by            varchar(64) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Table: jc_sys_dict                                           */
/*==============================================================*/
create table jc_sys_dict
(
   id                   bigint(11) not null auto_increment comment '序号',
   di_key               varchar(64) comment '字典键',
   di_value             varchar(255) comment '字典值',
   type_num             int(11) comment '分类编号',
   create_by            varchar(64) comment '添加人',
   create_date          datetime comment '添加时间',
   update_by            varchar(64) comment '修改人',
   update_date          datetime comment '修改时间',
   primary key (id)
);

