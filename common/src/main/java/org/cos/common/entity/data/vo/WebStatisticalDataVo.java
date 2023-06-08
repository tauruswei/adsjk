package org.cos.common.entity.data.vo;

import jnr.ffi.Struct;
import lombok.Data;

import java.util.Map;

@Data
public class WebStatisticalDataVo {
    private long web2Count;
    private long web3Count;
    private long downloadCount;
    private int gameCount;
}
