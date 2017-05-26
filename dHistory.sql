/*
Navicat SQL Server Data Transfer

Source Server         : 44
Source Server Version : 105000
Source Host           : 61.149.204.178:1433
Source Database       : data
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2017-05-26 14:48:39
*/


-- ----------------------------
-- Table structure for [dbo].[dHistory]
-- ----------------------------
DROP TABLE [dbo].[dHistory]
GO
CREATE TABLE [dbo].[dHistory] (
[idm] int NOT NULL IDENTITY(1,1) ,
[idfac] nvarchar(5) NULL ,
[time] datetime NULL ,
[e1] nvarchar(50) NULL ,
[e2] nvarchar(50) NULL ,
[e3] nvarchar(50) NULL ,
[e4] nvarchar(50) NULL ,
[e5] nvarchar(50) NULL ,
[e6] nvarchar(50) NULL ,
[e7] nvarchar(50) NULL ,
[e8] nvarchar(50) NULL ,
[e9] nvarchar(50) NULL ,
[e10] nvarchar(50) NULL ,
[e11] nvarchar(50) NULL ,
[e12] nvarchar(50) NULL ,
[e13] nvarchar(50) NULL ,
[e14] nvarchar(50) NULL ,
[e15] nvarchar(50) NULL ,
[e16] nvarchar(50) NULL 
)
