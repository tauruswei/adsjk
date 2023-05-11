package org.cos.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description: 异常处理器
 * @Date: 2019/1/27 19:46
 */
@ApiModel(value = "Result<T>", description = "返回对象实体（泛型）")
public class Result<T> {
	@ApiModelProperty(value = "结果code",name ="code" )
	private int code;
	@ApiModelProperty(value = "结果message",name ="msg" )
	private String msg;
	@ApiModelProperty(value = "结果data",name ="data" )
	private T data;

	public Result() {
		this.code = 0;
		this.msg="success";
	}

	/**
	 *  成功时候的调用
	 * */
	public static  <T> Result<T> success(T data){

		return new Result<T>(data);
	}

	public static  <T> Result<T> success(){

		return new Result();
	}
	
	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> error(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}
	
	private Result(T data) {
		this.code = 0;
		this.msg="success";
		this.data = data;
	}

	
	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
