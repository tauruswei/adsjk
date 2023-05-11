package org.cos.common.result;
/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description: 异常处理器
 * @Date: 2019/1/27 19:46
 */
public class CodeMsg {
	
	private int code;
	private String msg;
	
//	通用的错误码  5001XX
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg PARAMETER_VALID_ERROR = new CodeMsg(500101, "参数校验异常：%s");

//	业务用户组模块
	public static CodeMsg CREATE_GROUP_ERROR = new CodeMsg(500201, "Create business group error：%s");
	public static CodeMsg EDIT_GROUP_ERROR = new CodeMsg(500202, "Edit business group error：%s");
	public static CodeMsg GET_GROUP_ERROR = new CodeMsg(500203, "Query business group error：%s");
	public static CodeMsg DELETE_GROUP_ERROR = new CodeMsg(500204, "Delete business group error：%s");
	public static CodeMsg GROUP_EXIST_ERROR = new CodeMsg(500205, "Group name already exists,please change");
	public static CodeMsg GROUP_NOT_EXIST_ERROR = new CodeMsg(500206, "Business group is not exist");

//	业务用户模块
	public static CodeMsg CREATE_BUSER_ERROR = new CodeMsg(500301, "Create business user error：%s");
	public static CodeMsg EDIT_BUSER_ERROR = new CodeMsg(500302, "Edit business user error：%s");
	public static CodeMsg GET_BUSER_ERROR = new CodeMsg(500303, "Query business user error：%s");
	public static CodeMsg DELETE_NUSER_ERROR = new CodeMsg(500304, "Delete business user error：%s");
	public static CodeMsg UPLOAD_CERT_ERROR = new CodeMsg(500305, "Upload cert error：%s");
	public static CodeMsg BUSER_EXIST_ERROR = new CodeMsg(500306, "User name already exists,please change");
	public static CodeMsg BUSER_NOT_EXIST_ERROR = new CodeMsg(500307, "Business user is not exist");

//	证书模块
	public static CodeMsg CREATE_CERT_RQUEST_ERROR = new CodeMsg(500401, "Create certificate request error：%s");
	public static CodeMsg CONSTRUCT_X509_ERROR = new CodeMsg(500402, "Construct X509 string error：%s");
	public static CodeMsg EDIT_CERT_ERROR = new CodeMsg(500403, "Eidt certificate error：%s");
	public static CodeMsg INSTALL_CERT_ERROR = new CodeMsg(500404, "Install certificate error：%s");
	public static CodeMsg CERT_NOT_EXIST_ERROR = new CodeMsg(500405, "Certificate is not exist:%s");
	public static CodeMsg DOWNLOAD_CERT_ERROR = new CodeMsg(500406, "Download certificate error:%s");
	public static CodeMsg PARSE_CERT_ERROR = new CodeMsg(500407, "Parse certificate error:%s");
	public static CodeMsg CERT_DURATION_ERROR = new CodeMsg(500408, "Certificate duration error:%s");
	public static CodeMsg CERT_REQUEST_ERROR = new CodeMsg(500409, "Certificate request error:%s");
	public static CodeMsg SIGN_CERT_REQUEST_ERROR = new CodeMsg(500410, "Sign certificate request error:%s");
	public static CodeMsg DOWNLOAD_CERT_REQUEST_ERROR = new CodeMsg(500411, "Download certificate request error");
	public static CodeMsg DOWNLOAD_SIGNED_CERT_ERROR = new CodeMsg(500412, "Download signed certificate error");
	public static CodeMsg CERT_KEY_SIZE_ERROR = new CodeMsg(500413, "Key size is not supported");
	public static CodeMsg CERT_FORMAT_ERROR = new CodeMsg(500414, "Certificate format error:%s");


//  密钥模块
	public static CodeMsg CREATE_KEY_PAIR_ERROR = new CodeMsg(500501, "Create key parir error：%s");
	public static CodeMsg ENCRYPT_PRI_KEY_ERROR = new CodeMsg(500502, "Encrypt private key error：%s");

//	CA模块
	public static CodeMsg CA_EXIST_ERROR = new CodeMsg(500601, "CA is not exist");
	public static CodeMsg CA_STATUS_ERROR = new CodeMsg(500602, "CA status error：%s");
	public static CodeMsg CA_EXPIRATION_ERROR = new CodeMsg(500603, "CA expiration error：%s");
	public static CodeMsg CA_KEY_ERROR = new CodeMsg(500604, "CA key error：%s");
	public static CodeMsg CA_UPDATE_ERROR = new CodeMsg(500605, "CA update error：%s");
	public static CodeMsg CREATE_CA_ERROR = new CodeMsg(500606, "Create CA error：%s");


//	token
	public static CodeMsg TOKEN_NOT_EXIST = new CodeMsg(500701, "Token cannot be empty");
	public static CodeMsg TOKEN_EXPIRED_ERROR = new CodeMsg(500702, "Token expired error：%s");
	public static CodeMsg TOKEN_SIGNATURE_ERROR = new CodeMsg(500703, "Token signature error：%s");
	public static CodeMsg TOKEN_OTHER_ERROR = new CodeMsg(500704, "Token other error：%s");

//	login
	public static CodeMsg LOGIN_ERROR = new CodeMsg(500801, "Login error");

	private CodeMsg( ) {
	}
			
	private CodeMsg(int code, String msg ) {
		this.code = code;
		this.msg = msg;
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
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
	
	
}
