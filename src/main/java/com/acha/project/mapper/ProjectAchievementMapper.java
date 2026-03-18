package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectAchievement;
import com.acha.project.model.vo.ProjectAchievementVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 科研项目成果表数据库操作接口
 */
public interface ProjectAchievementMapper extends BaseMapper<ProjectAchievement> {

    /**
     * 查询项目状态并校验用户是否为该项目成员或负责人
     * @param projectId 项目ID
     * @param userId 用户ID
     * @return 返回项目状态(如果不是成员或项目不存在则返回null)
     */
    Integer checkProjectStatusForMember(@Param("projectId") Long projectId, @Param("userId") Long userId);

    /**
     * 根据成果ID查询其对应项目的负责人ID
     * @param achievementId 成果ID
     * @return 负责人ID
     */
    Long getProjectLeaderIdByAchievementId(@Param("achievementId") Long achievementId);

    /**
     * 查询指定项目下的成果列表（带分页和提交人姓名左连接）
     */
    List<ProjectAchievementVO> listAchievementsWithSubmitterName(
            @Param("projectId") Long projectId, 
            @Param("offset") long offset, 
            @Param("limit") long limit
    );

    /**
     * 查询指定项目下的成果总数
     */
    long countAchievements(@Param("projectId") Long projectId);
}
