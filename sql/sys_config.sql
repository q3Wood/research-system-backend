-- ==========================================================
-- 表名：sys_config (系统参数表)
-- 作用：供超级管理员动态配置系统参数（如：成果类型下拉框数据、系统名称等）。
-- 约束设计：
-- 1. 主码：id
-- 2. 唯一约束：config_key (参数键名必须唯一)
-- ==========================================================
-- ==========================================================
-- 表名：sys_config (系统参数与字典表)
-- 作用：统一管理系统的一对一全局参数，以及一对多的下拉框分组选项。
-- ==========================================================
CREATE TABLE `sys_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_key` VARCHAR(50) NOT NULL COMMENT '参数键名/分组标识(如: sys_name 或 achievement_type)',
  `config_label` VARCHAR(100) NOT NULL COMMENT '前端显示文本(如: 系统名称 或 学术论文)',
  `config_value` VARCHAR(500) NOT NULL COMMENT '具体的参数值或代码(如: 星火科研平台 或 paper)',
  `sort_order` INT DEFAULT 0 COMMENT '排序号(数字越小越靠前)',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 1-正常, 0-停用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_config_key` (`config_key`) -- 加上普通索引，加速后端的 WHERE 查询
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置与字典表';

-- 1. 插入一对一的“系统参数”
INSERT INTO `sys_config` (`config_key`, `config_label`, `config_value`, `sort_order`) VALUES 
('sys_name', '系统顶栏名称', '星火高校科研管理系统', 1),
('max_budget', '项目最大申报预算(元)', '500000', 1);

-- 2. 插入一对多的“字典下拉框选项”
INSERT INTO `sys_config` (`config_key`, `config_label`, `config_value`, `sort_order`) VALUES 
('achievement_type', '学术论文', 'paper', 1),
('achievement_type', '发明专利', 'patent', 2),
('achievement_type', '软件著作权', 'software', 3),
('project_level', '国家级', 'national', 1),
('project_level', '省部级', 'provincial', 2);