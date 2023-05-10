package org.cos.common.entity.data.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceOrganReq {
    private String organGlobalId;
    private String organName;
    private String organServerAddress;


}
