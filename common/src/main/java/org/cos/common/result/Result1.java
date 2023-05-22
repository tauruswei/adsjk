package org.cos.common.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
public class Result1{
    public String txid;
    public String payload;
    public String encode;
}
