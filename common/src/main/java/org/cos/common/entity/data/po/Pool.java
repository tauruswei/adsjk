package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

@Data
public class Pool {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 质押池类型 0-sl pool；1-club pool；2-defi pool
     */
    private int type;
    /**
     * 表示当前池子是第几期
     */
    private int term;
    /**
     * 池子开始时间/秒
     */
    private Long startTime;
    /**
     * 锁仓时间/秒
     */
    private Long lockTime;
    /**
     * 备注
     */
    private String remark;

}
