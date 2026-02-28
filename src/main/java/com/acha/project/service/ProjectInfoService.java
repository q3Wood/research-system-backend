package com.acha.project.service;

import com.acha.project.model.entity.ProjectInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectInfoService extends IService<ProjectInfo>{

    /**
     * 创建科研项目
     * @param projectName 项目名称
     * @param description 项目描述
     * @param budget 项目预算
     * @return 创建成功的项目信息
     */
    void addProject(String projectName, String description, java.math.BigDecimal budget);
    
}
