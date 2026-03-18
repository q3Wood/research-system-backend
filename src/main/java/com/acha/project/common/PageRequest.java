package com.acha.project.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页请求基类
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    @Schema(description = "当前页号", defaultValue = "1", type = "integer")
    private long current = 1;

    /**
     * 页面大小
     */
    @Schema(description = "页面大小", defaultValue = "10", type = "integer")
    private long pageSize = 10;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段(可选)")
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    @Schema(description = "排序顺序(可选)", defaultValue = "ascend")
    private String sortOrder = "ascend";

    /**
     * 转换为 MyBatis-Plus 的 Page 对象
     * 注意：加 @JsonIgnore 是为了防止 Swagger 把它当成请求参数解析出来
     * @param <T>
     * @return
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public <T> Page<T> getMyBatisPage() {
        return new Page<>(this.current, this.pageSize);
    }
}
