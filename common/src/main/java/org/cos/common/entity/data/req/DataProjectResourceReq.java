package org.cos.common.entity.data.req;

import lombok.Data;

@Data
public class DataProjectResourceReq {

    /**
     * 机构ID
     */
    private String organId;
    /**
     * 资源ID
     */
    private String resourceId;
    /**
     * 机构项目中参与身份 1发起者 2协作者
     */
    private Integer participationIdentity;
}
