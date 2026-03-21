package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectAuditLog;
import com.acha.project.model.vo.project.audit.ProjectAuditLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全局审批历史流水表数据库操作接口
 */
public interface ProjectAuditLogMapper extends BaseMapper<ProjectAuditLog> {

    /**
     * 新增审核流水记录（自定义 SQL）
     * @param auditLog 流水实体
     * @return 影响行数
     */
    int insertAuditLog(ProjectAuditLog auditLog);

    /**
     * 统计审批日志数量
     */
    long countAuditLogs(@Param("projectId") Long projectId,
                        @Param("moduleType") Integer moduleType,
                        @Param("currentUserId") Long currentUserId,
                        @Param("onlySelfRelated") boolean onlySelfRelated);

    /**
     * 分页查询审批日志
     */
    List<ProjectAuditLogVO> listAuditLogsByPage(@Param("projectId") Long projectId,
                                                @Param("moduleType") Integer moduleType,
                                                @Param("currentUserId") Long currentUserId,
                                                @Param("onlySelfRelated") boolean onlySelfRelated,
                                                @Param("offset") long offset,
                                                @Param("limit") long limit);
}
