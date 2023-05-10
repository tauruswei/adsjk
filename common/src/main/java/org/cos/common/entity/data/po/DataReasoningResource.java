package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

@Data
public class DataReasoningResource {

    private Long id;

    private Long reasoningId;

    private String resourceId;

    private Integer participationIdentity;

    private String serverAddress;

    /**
     * 是否删除
     */
    private Integer isDel;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
