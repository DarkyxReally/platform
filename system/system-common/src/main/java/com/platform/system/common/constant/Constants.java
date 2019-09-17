package com.platform.system.common.constant;

public interface Constants {
	
	/** 2015-11-30 公司资金冻结状态 0:冻结 1:正常*/
    public static final String FREEZE_TYPE_FREEZE = "0";
    public static final String FREEZE_TYPE_NORMAL = "1";
	
	public static final String RET_SUCCESS = "100";
	public static final String RET_SUCCESS_DESC = "执行成功";
	
	public static final String RET_PARAMERROR = "102";
	public static final String RET_PARAMERROR_DESC = "参数格式不正确";
	
	public static final String RET_NORESOURCE = "103";
	public static final String RET_NORESOURCE_DESC = "没有设置resource请求头参数";
	
	public static final String RET_SYSTEMEXCEPTION = "104";
	public static final String RET_SYSTEMEXCEPTION_DESC = "系统异常";
	
	public static final String RET_FAIL = "105";
	public static final String RET_FAIL_DESC = "执行失败";
	
	public static final String RET_NULLDATA = "106";
	public static final String RET_NULLDATA_DESC = "未找到数据";
	
	public static final String RET_CODEINVALID = "201";
	public static final String RET_CODEINVALID_DESC = "验证码已经失效";
	public static final String RET_VCODE_ERRO_DESC = "验证码不正确";
	
	public static final String ACCOUNT_LOCK_ERROR="505";
	public static final String ACCOUNT_LOCK_MSG="本帐号已被限制手机登录，请取消限制后重新登录";
	
	public static final String RETURN_CODE="code";
	public static final String RETURN_TIP="tip";
	public static final String RETURN_TOTOLPAGE="totalpage";
	public static final String RETURN_CURRENYPAGE="currentpage";
	public static final String RETURN_ITEMS="items";
	public static final String RETURN_SUCCESS="获取成功";
	public static final String RETURN_RESULT="result";
	public static final String RETURN_STATUS="status";
	//BZB
	public static final String RET_VCODE_TIMEOUT = "111";
	public static final String RET_VCODE_TIMEOUT_DESC = "验证码已超时";
	
	public static final String RET_VCODE_ERRO = "112";
	
	public static final String RET_SUCCESS_CODE = "100";
	public static final String RET_SUCCESS_CODE_DESC = "校验成功";	
	//职在企业版
	public static final String USER_PRODUCT_TYPECODE_MAJOR = "1";
	//职在企业版
	public static final String USER_PRODUCT_TYPECODE_STAND = "2";
	
	public static final String RET_CODE_USE_FLOWCARD ="充值失败";
	public static final String RET_USE_FLOWCARD_ERROR ="-99999";
	
	public static final String RET_SUCCESS_CODE_USE_FLOWCARD ="充值成功";
	public static final String RET_SUCCESS_USE_FLOWCARD ="00";
	
	public static final String RET_REDENVELOPES_USE_CODE = "122";
	public static final String RET_REDENVELOPES_USE_CODE_DESC = "红包领取失败。";
	
	public static final String RET_FLOW_ACTIVITY_ERROR_CODE="108";
	public static final String RET_FLOW_ACTIVITY_ERROR_DESC="您已经参加过该活动了！";
	
	public static final String RET_FINANCE_ERROR_CODE = "120";
	public static final String RET_FINANCE_ERROR_DESC = "公司资金已冻结！";
	
	public static final String RET_ALREADY_JOIN_CODE = "300";
	public static final String RET_ALREADY_JOIN_DESC = "您已经加入其他公司，请退出原公司后再申请！";
	
	public static final String RET_ALREADY_JOIN_REPEAT_CODE = "301";
	public static final String RET_ALREADY_JOIN_REPEAT_DESC = "您的申请已经通知管理员，请耐心等待！";
	
	public static final String RET_INVALID_CODE = "404";
	public static final String RET_INVALID_DESC = "页面已经失效！";
	
	
	public static final String PUSH_TYPE_3 = "3"; // 费用申请
	public static final String PUSH_TYPE_4 = "4"; // im推送
	public static final String PUSH_TYPE_5 = "5"; //
	public static final String PUSH_TYPE_6 = "6"; //
	public static final String PUSH_TYPE_7 = "7"; //
	public static final String PUSH_TYPE_8 = "8"; // 政策申请
	public static final String PUSH_TYPE_9 = "9"; // 项目申请
	public static final String PUSH_TYPE_10 = "10"; // 政策申请变更
	public static final String PUSH_TYPE_11 = "11"; // 续案申请
	
	

	public static final String PUSH_TYPE_18 = "18"; // 发文推送
	public static final String PUSH_TYPE_19 = "19"; // 工作流程
	public static final String PUSH_TYPE_20 = "20"; // 邀请在职员工加入
	public static final String PUSH_TYPE_21 = "21"; // 邀请在职员工加入
	public static final String PUSH_TYPE_22 = "22"; // 邀请在职员工加入
	public static final String PUSH_TYPE_23 = "23"; // 拒绝邀请推送
	public static final String PUSH_TYPE_24 = "24"; // 取消管理员权限
	public static final String PUSH_TYPE_25 = "25"; // 任命管理员
	public static final String PUSH_TYPE_26 = "26"; // 快递单号变更推送
	public static final String PUSH_TYPE_27 = "27"; // 账号下线提醒（被挤）
	public static final String PUSH_TYPE_28 = "28"; // 无忧助手
	public static final String PUSH_TYPE_29 = "29"; // 解散团队提醒（被挤）
	
	public static final String PUSH_TYPE_30 = "30"; // 日程评论推送
	public static final String PUSH_TYPE_31 = "31"; // 周报评论推送
	public static final String PUSH_TYPE_32 = "32"; // 月报评论推送
	
	public static final String PUSH_TYPE_33="33";//刷新主页汇报提醒
	
	public static final String INVOICE_TYPE_1="INVOICE_TYPE_1";//定额发票  -个人消费类
	public static final String INVOICE_TYPE_2="INVOICE_TYPE_2";//增值税普通发票-企业消费类
	public static final String INVOICE_TYPE_3="INVOICE_TYPE_3";//值税专用发票-企业消费类
	
	/**
	 * 文本text/plain
	 */
	public static final String TEXT_PLAIN = "text/plain";

	/**
	 * 路径分隔符
	 */
	public static final char PATH_SEPARATOR = '/';

	/**
	 * 以逗号或分号分隔
	 */
	public static final String COMMA_SEMICOLON = "\\s*,\\s*|\\s*;\\s*";

	/**
	 * utf-8编码
	 */
	public static final String UTF_8 = "utf-8";


	/**
	 * resources目录
	 */
	public static final String RESOURCES = "resources";

	/**
	 * contextPath目录
	 */
	public static final String CONTEXT_PATH = "contextPath";

	/**
	 * version版本号
	 */
	public static final String VERSION = "version";

	/**
	 * id
	 */
	public static final String ID = "id";

	/**
	 * ?
	 */
	public static final String QUESTION = "?";

	/**
	 * &
	 */
	public static final String AND = "&";

	/**
	 * =
	 */
	public static final String EQUALS = "=";
	
	/**
	 * 加密值
	 */
	public static final String SIGN = "sign";
	
	/**
	 * -1
	 */
	int NEGATIVE_ONE = -1;
	
	/**
	 * 发生异常时要跳转的页面名字和从request中获取异常信息的name
	 */
	public static final String EXCEPTION = "exception";
	
	public static final String ERROR_FILE_UPLOAD = "error_fileupload";
	
	/**
	 * 用户的ip地址
	 */
	public static final String IP = "ip";
	
	/**
	 * Jsonp请求格式参数
	 */
	public static final String CALLBACK = "callback";
	
	/**
	 * 活动房间默认图片
	 */
	public static final String ACTIVITY_IMG = "/cardsfolder/activity/bf430c1f-e198-4784-bf31-60be0993f1fa.png";
}
