package org.cos.common.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description: 异常处理器
 * @Date: 2019/1/27 19:46
 */
@JsonSerialize
public class TestResult {
	public String returnCode;
	public String error;
	public Result1 result;

}

