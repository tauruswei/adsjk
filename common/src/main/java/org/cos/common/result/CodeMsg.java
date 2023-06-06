package org.cos.common.result;


/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description: 异常处理器
 * @Date: 2019/1/27 19:46
 */
public class CodeMsg {
	
	private Integer code;
	private String msg;
	
//	通用的错误码  5001XX
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500000, "服务端异常");
	public static CodeMsg SERVER_DETAILED_ERROR = new CodeMsg(500100, "服务端异常：%s");
	public static CodeMsg CLIENT_ERROR = new CodeMsg(500100, "客户端异常");
	public static CodeMsg PARAMETER_VALID_ERROR = new CodeMsg(500101, "参数校验异常：%s");

//====================================================
//                用户 模块
//====================================================
	public static CodeMsg USER_SENDCODE_ERROR = new CodeMsg(500101,"发送邮箱验证码异常: %s");
	public static CodeMsg USER_ADD_ERROR = new CodeMsg(500102,"添加用户异常: %s");
	public static CodeMsg USER_EXIST_ERROR = new CodeMsg(500103,"用户已存在: %s");
	public static CodeMsg USER_NOT_EXIST_ERROR = new CodeMsg(500104,"用户不存在");
	public static CodeMsg USER_EMAIL_NOT_EXIST_ERROR = new CodeMsg(500104,"该邮箱没有注册，请输入正确的邮箱");

	public static CodeMsg USER_QUERY_ERROR = new CodeMsg(500105,"查询用户失败：%s");
	public static CodeMsg USER_LOGIN_ERROR = new CodeMsg(500106,"用户登录失败：%s");
	public static CodeMsg USER_UPDATE_ERROR = new CodeMsg(500107,"更新用户失败：%s");
	public static CodeMsg USER_RELATION_ADD_ERROR = new CodeMsg(500108,"添加用户关系异常: %s");
	public static CodeMsg USER_RELATION_NOT_EXIST_ERROR = new CodeMsg(500109,"用户关系不存在");
	public static CodeMsg USER_ENCRYPT_ERROR = new CodeMsg(500110,"用户加密失败：%s");
	public static CodeMsg USER_DECRYPT_ERROR = new CodeMsg(500111,"用户解密失败：%s");


//====================================================
//                网站交易模块
//====================================================
	public static CodeMsg TRANS_WEBSITE_ADD_ERROR = new CodeMsg(500201,"添加交易异常: %s");
	public static CodeMsg TRANS_WEBSITE_TYPE_ERROR = new CodeMsg(500202,"交易类型错误");
	public static CodeMsg TRANS_WEBSITE_TX_EXIST_ERROR = new CodeMsg(500203,"交易已经存在,请检查 txid 是否正确");
	public static CodeMsg TRANS_WEBSITE_WITHDRAW_ERROR = new CodeMsg(500204,"用户提现失败：%s");
	public static CodeMsg TRANS_WEBSITE_UPCHAIN_ERROR = new CodeMsg(500205,"用户交易上链失败：%s");


//====================================================
//                资产模块
//====================================================
	public static CodeMsg ASSET_ADD_ERROR = new CodeMsg(500301,"添加资产异常: %s");
	public static CodeMsg ASSET_WITHDRAW_ERROR = new CodeMsg(500302,"用户提现失败: %s");
	public static CodeMsg ASSET_UPDATE_ERROR = new CodeMsg(500303,"更新用户资产异常: %s");
	public static CodeMsg ASSET_NOT_EXIST_ERROR = new CodeMsg(500304,"用户资产不存在");
	public static CodeMsg ASSET_TYPE_ERROR = new CodeMsg(500305,"用户资产类型错误");
	public static CodeMsg ASSET_ACOUNT_ERROR = new CodeMsg(500306,"用户资产不能为负数");

//====================================================
//                质押池模块
//====================================================
	public static CodeMsg POOL_USER_ADD_ERROR = new CodeMsg(500401,"用户质押失败: %s");
	public static CodeMsg POOL_USER_UPDATE_ERROR = new CodeMsg(500402,"更新用户质押异常: %s");
	public static CodeMsg POOL_USER_BALANCE_ERROR = new CodeMsg(500403,"用户质押余额不能为负: %s");
	public static CodeMsg POOL_NOT_EXIST_ERROR = new CodeMsg(500404,"质押池不存在");
	public static CodeMsg POOL_TYPE_ERROR = new CodeMsg(500405,"质押池类型错误");
	public static CodeMsg POOL_USER_NOT_EXIST_ERROR = new CodeMsg(500406,"用户没有在质押池中质押");



//====================================================
//                NFT 模块
//====================================================
	public static CodeMsg NFT_EXIST_ERROR = new CodeMsg(500401,"NFT 已存在");
	public static CodeMsg NFT_NOT_EXIST_ERROR = new CodeMsg(500402,"NFT 不存在: %s");
	public static CodeMsg NFT_ADD_ERROR = new CodeMsg(500403,"NFT 添加失败: %s");
	public static CodeMsg NFT_CHANCE_NOT_BE_NEGATIVE = new CodeMsg(500404,"NFT 次数不能为负数");







	//====================================================
//                token 模块
//====================================================
	public static CodeMsg TOKEN_NOT_EXIST = new CodeMsg(500201, "Token cannot be empty");
	public static CodeMsg TOKEN_EXPIRED_ERROR = new CodeMsg(500202, "Token expired error：%s");
	public static CodeMsg TOKEN_SIGNATURE_ERROR = new CodeMsg(500203, "Token signature error：%s");
	public static CodeMsg TOKEN_OTHER_ERROR = new CodeMsg(500204, "Token other error：%s");

//====================================================
//                交易 模块
//====================================================
	public static CodeMsg TRAN_NOT_EXIST = new CodeMsg(500201, "Token cannot be empty");
	public static CodeMsg TRAN_EXPIRED_ERROR = new CodeMsg(500202, "Token expired error：%s");
	public static CodeMsg TRAN_SIGNATURE_ERROR = new CodeMsg(500203, "Token signature error：%s");
	public static CodeMsg TRAN_OTHER_ERROR = new CodeMsg(500204, "Token other error：%s");


//====================================================
//                Authgw 模块
//====================================================

	//用户管理模块
	public static CodeMsg CREATE_USER_ERROR = new CodeMsg(520101, "创建用户失败");
//	public static CodeMsg USER_EXIST_ERROR = new CodeMsg(520102, "用户已存在");
//	public static CodeMsg USER_NOT_EXIST_ERROR = new CodeMsg(520103, "用户不存在");
	public static CodeMsg DELETE_USER_ERROR = new CodeMsg(520104, "删除用户失败");
	public static CodeMsg EDIT_USER_ERROR = new CodeMsg(520105, "编辑用户失败");
	public static CodeMsg USER_ATTRIBUTE_EXIST_ERROR = new CodeMsg(520106, "用户属性已存在");
	public static CodeMsg USER_ATTRIBUTE_NOT_EXIST_ERROR = new CodeMsg(520107, "用户属性不存在");
	public static CodeMsg ADD_USER_ATTRIBUTE_ERROR = new CodeMsg(520108, "添加用户属性失败");
	public static CodeMsg DELETE_USER_ATTRIBUTE_ERROR = new CodeMsg(520109, "删除用户属性失败");
	public static CodeMsg EDIT_USER_ATTRIBUTE_ERROR = new CodeMsg(520110, "编辑用户属性失败");
	public static CodeMsg OLD_PASSWD_ERROR = new CodeMsg(520111, "原密码不正确");

	//角色管理模块
	public static CodeMsg CREATE_ROLE_ERROR = new CodeMsg(520201, "创建角色失败");
	public static CodeMsg ROLE_EXIST_ERROR = new CodeMsg(520202, "角色已存在");
	public static CodeMsg ROLE_NOT_EXIST_ERROR = new CodeMsg(520203, "角色不存在：%s");
	public static CodeMsg DELETE_ROLE_ERROR = new CodeMsg(520204, "删除角色失败");
	public static CodeMsg EDIT_ROLE_ERROR = new CodeMsg(520205, "编辑角色失败");
	public static CodeMsg USER_ROLE_BIND_ERROR = new CodeMsg(520206, "用户角色绑定失败：%s");
	public static CodeMsg USER_ROLE_UNBIND_ERROR = new CodeMsg(520207, "用户角色解绑失败：%s");
	public static CodeMsg USER_ROLE_MAPPING_NOT_EXIST_ERROR = new CodeMsg(520208, "用户角色绑定关系不存在：%s");
	public static CodeMsg USER_ROLE_MAPPING_EXIST_ERROR = new CodeMsg(520209, "用户角色绑定关系已存在：%s");
	public static CodeMsg ROLE_USED_ERROR = new CodeMsg(520210, "角色已绑定用户或者某些应用使用了该角色");


	//应用管理模块
	public static CodeMsg ADD_APP_ERROR = new CodeMsg(520301, "添加应用失败");
	public static CodeMsg APP_EXIST_ERROR = new CodeMsg(520302, "应用已存在");
	public static CodeMsg APP_NOT_EXIST_ERROR = new CodeMsg(520303, "应用不存在");
	public static CodeMsg DELETE_APP_ERROR = new CodeMsg(520304, "删除应用失败");
	public static CodeMsg EDIT_APP_ERROR = new CodeMsg(520305, "编辑应用失败");
	public static CodeMsg APP_USED_ERROR = new CodeMsg(520306, "某些策略使用该应用");

	//策略管理模块
	public static CodeMsg ADD_POLICY_ERROR = new CodeMsg(520401, "添加策略失败");
	public static CodeMsg POLICY_EXIST_ERROR = new CodeMsg(520402, "策略已存在");
	public static CodeMsg POLICY_NOT_EXIST_ERROR = new CodeMsg(520403, "策略不存在");
	public static CodeMsg DELETE_POLICY_ERROR = new CodeMsg(520404, "删除策略失败");
	public static CodeMsg SEARCH_POLICY_ERROR = new CodeMsg(520405, "查询策略失败");

	//CA管理模块
	public static CodeMsg ADD_CA_ERROR = new CodeMsg(520501, "添加 CA 失败：%s");
	public static CodeMsg CA_EXIST_ERROR = new CodeMsg(520502, "CA 已存在");
	public static CodeMsg CA_NOT_EXIST_ERROR = new CodeMsg(520503, "CA 不存在");
	public static CodeMsg DELETE_CA_ERROR = new CodeMsg(520504, "删除 CA 失败");
	public static CodeMsg DELETE_OCSP_ERROR = new CodeMsg(520504, "删除 OCSP 失败：%s");
	public static CodeMsg DELETE_CRL_ERROR = new CodeMsg(520504, "删除 CRL 失败：%s");
	public static CodeMsg DELETE_AUTH_TYPW_ERROR = new CodeMsg(520504, "删除 AuthType 失败：%s");
	public static CodeMsg EDIT_CA_ERROR = new CodeMsg(520305, "编辑 CA 失败：%s");
	public static CodeMsg DOWNLOAD_HTTPS_CRL_ERROR = new CodeMsg(520306, "下载 HTTPS-CRL 失败：%s");
	public static CodeMsg DOWNLOAD_LDAP_CRL_ERROR = new CodeMsg(520307, "下载 LDAP-CRL 失败：%s");
	public static CodeMsg DOWNLOAD_LDAP_CA_ERROR = new CodeMsg(520308, "下载 LDAP-CA 失败：%s");
	public static CodeMsg ADD_OCSP_ERROR = new CodeMsg(520309, "添加 OCSP 失败");
	public static CodeMsg ADD_CRL_ERROR = new CodeMsg(5203010, "添加 CRL 失败");
	public static CodeMsg CRL_EXIST_ERROR = new CodeMsg(5203011, "CRL 已经存在");
	public static CodeMsg UPDATE_CA_ERROR = new CodeMsg(5203012, "更新 CA 失败");
	public static CodeMsg PARSE_CRL_ERROR = new CodeMsg(5203013, "解析 CRL 失败");
	public static CodeMsg PARSE_CA_ERROR = new CodeMsg(5203014, "解析 CA 失败");
	public static CodeMsg PARSE_CERT_ERROR = new CodeMsg(5203014, "解析证书失败");
	public static CodeMsg DOWNLOAD_CA_ERROR = new CodeMsg(5203015, "下载 CA 失败");
	public static CodeMsg OCSP_NOT_EXIST_ERROR = new CodeMsg(5203016, "OCSP 不存在");
	public static CodeMsg OCSP_EXIST_ERROR = new CodeMsg(5203016, "OCSP 已存在");
	public static CodeMsg OCSP_CONNECT_ERROR = new CodeMsg(5203016, "OCSP 连接失败");
	public static CodeMsg OCSP_PASSWORD_ERROR = new CodeMsg(5203016, "密码不正确");
	public static CodeMsg CRL_NOT_EXIST_ERROR = new CodeMsg(5203017, "CRL 不存在");
	public static CodeMsg UPDATE_CRL_ERROR = new CodeMsg(5203018, "更新 CRL 失败：%s");
	public static CodeMsg UPDATE_OCSP_ERROR = new CodeMsg(5203018, "更新 OCSP 失败");
	public static CodeMsg UPDATE_AUTH_TYPW_ERROR = new CodeMsg(5203018, "更新 AuthType 失败");
	public static CodeMsg AUTH_TYPE_ERROR = new CodeMsg(5203019, "验证类型有误(NO/OCSP/CRL)");
	public static CodeMsg CERT_VALID_TIME_ERROR = new CodeMsg(5203020, "证书不在有效期内");
	public static CodeMsg CA_USED_ERROR = new CodeMsg(5203021, "CA 已经和应用绑定");
	public static CodeMsg CA_FILE_NOT_EXIST_ERROR = new CodeMsg(520522, "CA证书文件不存在");
	public static CodeMsg CA_CRL_ALG_NOT_NAME_ERROR = new CodeMsg(520522, "CA 和 CRL 算法不一致");
	public static CodeMsg CRL_SIGN_VERIFY_ERROR = new CodeMsg(520522, "CRL 签名验证失败");
	public static CodeMsg CERT_SIGN_VERIFY_ERROR = new CodeMsg(520522, "证书签名验证失败");
	public static CodeMsg CA_SUBJECT_NOT_SAME_ERROR = new CodeMsg(520522, "新CA和旧CA的使用者不一致");
	public static CodeMsg CA_PUB_KEY_NOT_SAME_ERROR = new CodeMsg(520522, "新CA和旧CA的公钥不一致");

	//高可用模块
	public static CodeMsg PASSWORD_NOT_SAME = new CodeMsg(520601, "密码不一致");
	public static CodeMsg BACKUP_ERROR = new CodeMsg(520502, "备份失败");
	public static CodeMsg BACKUP_DATABASE_ERROR = new CodeMsg(520502, "备份数据库失败");
	public static CodeMsg BACKUP_REDIS_ERROR = new CodeMsg(520502, "备份 redis 失败");
	public static CodeMsg BACKUP_PACKAGE_ERROR = new CodeMsg(520502, "备份----压缩文件失败");
	public static CodeMsg SIGN_ERROR = new CodeMsg(520503, "签名失败");
	public static CodeMsg SIGNATURE_ERROR = new CodeMsg(520503, "写入签名失败");
	public static CodeMsg ENCRYPYT_ERROR = new CodeMsg(520503, "加密失败");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500101, "验证备份密码失败");
	public static CodeMsg RESTORE_ERROR = new CodeMsg(520503, "恢复失败：%s");
	public static CodeMsg RESTORE_GET_SQL_NAME_ERROR = new CodeMsg(520503, "获取 sql 文件名失败");
	public static CodeMsg RESTORE_DATABASE_ERROR = new CodeMsg(520503, "恢复数据库失败");

	//会话管理模块
	public static CodeMsg SEARCH_SESSION_ERROR = new CodeMsg(520601, "查询会话失败");
	public static CodeMsg SEARCH_USER_SESSION_ERROR = new CodeMsg(520602, "查询用户会话失败");
	public static CodeMsg SEARCH_APP_SESSION_ERROR = new CodeMsg(520603, "查询应用会话失败");

	//日志管理模块
	public static CodeMsg ADD_LOG_ERROR = new CodeMsg(520701, "添加日志失败");
	public static CodeMsg EDIT_LOG_ERROR = new CodeMsg(520702, "编辑日志失败");

	//redis 模块
	public static CodeMsg REDIS_HASH_DEL_KEY_ERROR = new CodeMsg(520701, "删除 hash key 失败");
	public static CodeMsg REDIS_LIST_DEL_VALUE_ERROR = new CodeMsg(520702, "删除 list value 失败");

	//管理员管理模块
	public static CodeMsg ADMIN_VERIFY_ERROR = new CodeMsg(520901, "");
	public static CodeMsg ADMIN_CERT_ALREADY_REG = new CodeMsg(520902, "相同证书已被其他管理员注册");
	public static CodeMsg ADMIN_NOT_EXIST_ERROR = new CodeMsg(520903, "管理员不存在");

	// 系统配置模块
	public static CodeMsg SYS_CONFIG_NOT_EXIST_ERROR = new CodeMsg(521001, "系统配置不存在");
	public static CodeMsg EDIT_SYS_CONFIG_ERROR = new CodeMsg(521001, "编辑系统配置失败");

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
