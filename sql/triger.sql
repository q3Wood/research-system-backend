-- 1. 临时改变 MySQL 的结束符。因为触发器内部有很多分号，为了防止 MySQL 提前结束执行，我们把结束符临时改成 $$
DELIMITER $$

-- 2. 创建触发器，起名叫 trg_fund_approve_deduct_balance
CREATE TRIGGER `trg_fund_approve_deduct_balance`
-- 3. 监听时机：在 project_fund_record 表发生 UPDATE (修改) 操作【之后】执行
AFTER UPDATE ON `project_fund_record`
-- 4. 针对每一行受影响的数据都执行
FOR EACH ROW
BEGIN
    -- 5. 核心判断逻辑！
    -- OLD 代表修改前的数据，NEW 代表修改后的数据
    -- 只有当原来的状态是 0 (待审)，并且现在被改成了 1 (通过) 时，才执行扣款！
    IF OLD.status = 0 AND NEW.status = 1 THEN
        
        -- 6. 自动去项目主表里扣除这笔钱
        UPDATE `research_project` 
        SET `balance` = `balance` - NEW.amount 
        WHERE `id` = NEW.project_id;
        
    END IF;
END$$

-- 7. 把结束符改回正常的分号
DELIMITER ;