package com.shenxian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author shenxian
 * @since 2022-07-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysLog对象", description="系统日志")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    private String description;

    private String logType;

    private String method;

    private String params;

    private String requestIp;

    private Long time;

    private String username;

    private String address;

    private String browser;

    private String exceptionDetail;

    private LocalDateTime createTime;

    public SysLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }

}
