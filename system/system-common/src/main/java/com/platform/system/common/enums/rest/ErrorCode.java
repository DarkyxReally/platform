package com.platform.system.common.enums.rest;

/**
 * 错误码定义
 * @version: 1.0
 */
public enum ErrorCode {
	
    /** 执行成功 */
    ERROR_100("100", "执行成功"),
    
    RET_HEADER_ERROR("101","header参数错误"),
    RET_PARAMERROR("102","参数格式不正确"),
    RET_SYSTEMEXCEPTION("104","系统异常"),
    RET_FAIL("105","执行失败"),
    RET_NULLDATA("106","未找到数据"),
    RET_FLOW_ACTIVITY_ERROR_CODE("108","您已经参加过该活动了！"),
    RET_EXIST("110","帐号已经存在"),
    RET_VCODE_TIMEOUT("111","验证码错误或者已过期"),
    RET_VCODE_ERRO("112","验证码不正确"),
    RET_NOT_EXIST("114","账号不存在"),
    
    RET_CODE_NOUPDATE("115","数据无更新"),
    RET_CODE_REQUESTED("116","退款请求已发送，不用重复操作"),
    RET_URNPAY_CODE_NOPERMISSION("117","你的订单不允许退款"),
    RET_CODE_UPDATETOSERVER("118","需要将客户端数据同步到服务器"),
    RET_REDENVELOPES_CODE("121","至少选择一种流量套餐并填写红包数量"),
    RET_REDENVELOPES_USE_CODE("122","红包领取失败。"),
    RET_IMG_VCODE_ERRO("131","图片验证码不正确"),
    RET_IMG_VCODE_TIMEOUT("132","图片验证码已超时"),
    RET_SESSIONINVALID("202","会话ID无效"),
    
    RET_THIRDLOGIN_FAIL("133","第三方登陆验证不通过"),
    /** 管理员限制登录客户端*/
    RET_MANAGER_CANNOT_LOGIN_APP("203","管理员限制登录客户端"),
    RET_NOPERMISSION("203","没有接入权限"),
    /** 帐号或密码错误*/
    RET_ERROR_ACCOUNT_PASSWORD("206","帐号或密码错误"),  
    RET_ERRORPASSORACCOUNT206("206","密码错误，请重新输入或找回密码"),
    RET_ERROR_PASSORD("206","密码错误,请重新输入"),
    RET_NOACTIVE("207","帐号未激活,请联系贵公司管理员!"),
    RET_NOACTTIVE_ZHONG("208","帐号未激活,请联系中琛源管理员!"),
    RET_ERRORPASSORACCOUNT208("208","帐号或密码错误,请重新输入"),
    /** 第三方登录未验证手机号码*/
    RET_THIRD_ERROR_CODE("234","未验证手机号码"),
    
    RET_ALREADY_JOIN_CODE("300","您已经加入其他公司，请退出原公司后再申请！"),
    RET_ALREADY_JOIN_REPEAT_CODE("301","您的申请已经通知管理员，请耐心等待！"),
    RET_NOCOMPANY("302","公司不存在"),
    RET_NOCOMPANYID("303","公司id不能为空"),
    
    ACCOUNT_LOCK_ERROR("505","您账号密码在短时间连续输错5次，账户已被系统自动锁定，请于24小时后尝试重新登录。"),//"本帐号已被限制手机登录，请取消限制后重新登录"
    RET_NOTUNIQUE("707","没有该适配组！"),
    
    
    RET_LAST_PAGE("998","已经是最后一页"),
    
    RET_INVALID_CODE("404","页面已失效！"),
	
    
    
    /** 该操作需要登陆，请登录后重试 */
    RET_SESSIONID_NOT_FOUND("901","session不能为空"),
    ERROR_902("902","该操作需要登录，请登录后重试"),
    ERROR_903("903","您的账号在其他设备登录，请重新登录"),
    RET_FORBIDDEN("303","公司不存在！"),
    
    RET_ManagerIsExist("506","客户名称已经存在"),
    RET_isRelated("508","该客户已经关联了项目，不可删除!"),
    RET_isCreated("509","该客户下已创建联系人，不可删除!"),
    markMountResultCode("800","您的费用申请已超过最高审批权限，请在额度范围内进行申请!"),
    
    RET_UNATTENDANCESETTTING("510","考勤方案未设置"),
    RET_ORDER_FAILED("520","下单失败"),
    RET_PHONE_IS_ERROR("1001","手机号码错误！"),
    RET_PRIZE("521","快去赚点积分再来吧~"),
    RET_FLOW("2","您的号码信息未知，暂时无法充值"),
    RET_SEND_MESSAGE("522","短信发送失败"),
    RET_PASSWORD("523","密码不能为空"),
    RET_MESSAGE("524","短信验证失败"),
    RET_REGISTER("525","注册失败"),
    RET_ACTIVITY("526","该账号已经参加过该活动"),
    RET_IS_PHONE("527","该账号电话号码错误，请重新输入"),
    RET_IS_COUNT("528","活动礼品已经发放完毕"),
    RET_IS_FILE("529","改号码已经注册"),
    NOT_SUPPORT_PRODUCT("1001","不支持此手机号码订购此款产品"),
    NO_PRODUCT("1002","产品信息不存在！"),
    PAY_SUCCESS("1003","支付成功！"),
    BUY_SUCCESS("1004","购买成功！"),
    BUY_FAIL("1005","购买失败未退款！"),
    BUY_FAIL_REFUND("1006","购买失败已退款！"),
    
    RET_INPUT("1007","姓名或手机号码或验证码不能为空"),
    RET_DISSLOVE("1008","解散团队失败！"),
    RET_NODISSLOVE("1009","不是公司创始人,没有解散权限！"),
    RET_NOATTCRE_REFID("1010","已经关联过日程！"),
   
    RET_NUM_EXCEEDED("1112","您好，您已绑定十张卡"),
    RET_BLIND_NO("1113","您好，该卡已被绑定，请确定您的卡号"),
    RET_BLIND_OK("1114","绑定成功"),
    RET_BLIND_WORRY("1115","绑定失败，未查询到该卡信息，请确认卡号是否输入正确"),
    RET_CARD_NONE("1116","卡号不存在"),
    
    RET_VERIFIED_IN("1117","您的证件正在认证或者已经认证成功"),
    RET_BUYNUM_EXCEED("1119","购买数量超出范围"),
    RET_STOCK_LACK("1120","库存不足"),
    RET_CARD_ANNORMAL("1121","卡号未激活或者已停机"),
    RET_SYSTEM_MAINTAIN("1122","抱歉，每月1号凌晨5点前为系统数据维护时间，暂不受理业务"),
    RET_VERIFIED_NO("1123","您的账户未完成实名认证，请在认证成功后再进行绑卡"),
    RET_FORMAT_ERROR("1118","图片上传失败"),
    RET_EDIT_TYPE("1124","由于一个月只能变更一次套餐，您当月已经更改过套餐，不可再次变更"),
    RET_CARDNO_RESTORY("1125","卡号已注销"),
    RET_SYSTEM_LASTTHREE("1126","月末最后三天不受理套餐变更业务，给您带来不变敬请谅解"),
    RET_SYSTEM_LASTDAY("1127","月末最后一天不可订购加油包，请您择日再办理此业务"),
    RET_VERIFIED_ERROR("1128","认证失败，请重新提交资料"),
    RET_BUYPACK_EXCEED("1129","本月已超出可办理次数，请于下月再办理"),
    RET_NOADDEDRECORD("1130","该用户没有补卡记录！"),
    RET_ADDEDAUTDIT("1131","该卡已经补过卡 或者 该卡正在补卡审核！"),
    RET_NULLPRODUCT("1132","该卡绑定的套餐为空或已下架"),
    RET_OUT_DATE_EXPIRE("1133","该卡已到期"),
    RET_SYSTEM_MAINTAINLT("1134","抱歉，每月25号、26号为联通账单日，暂不受理业务"),
    RET_CHANGEPACKAGE_ERROR("1135","该卡已到期停机，不可订购加油包"),
    RET_LIMITERROR("1136","该卡正在变更套餐中，暂不受理续费业务"),
    RET_KEYNOTTRUE("1137","密钥验证验证不通过"),
    RET_LIMITIP("1138","限制白名单之外的ip地址访问"),
    RET_NOWECOMPANY("1139","非本公司的用户禁止登陆oa系统"),
    RET_NOCHARGE("1140","月租欠费，请联系管理员充值缴费"),
    
    /* 3.0分享圈的异常 */
    RET_URL_INVALID("600","该地址为无效地址"), 
    RET_UPORSELECT("601","请选择图片或者上传图片"),
    RET_WEIBONOEXIST("602","该分享圈不存在"),
    RET_UPLOADFAIL("603","上传图片失败"),
    RET_KEEPWEIBO("604","您已经收藏了该条分享圈"),
    RET_IMGNULL("605","上传图片为空"),
    RET_DELETED("606","该分享圈不存在或者被删除"),
    RET_WEIBODELETED("607","该分享圈的原文不存在或者被删除"),
    
    //web角色权限异常提示信息
    WEB_ROLE_EXISTEMPLOYEE("150","该角色下有员工，不允许删除"),
    WEB_ROLE_EXISTROLENAME("151","该角色名称已存在！"),
    //web数据权限异常提示信息
    WEB_PERMISSION_EXISTROLENAME("152","该数据权限名称已存在！"),
    
    /* 图片异常 */ 
    RET_WRONG_FORMAT("1200","文件格式不正确!"),
    RET_WRONG_SIZE("1201","文件超出指定大小"),
    
    RET_NOEXIST_REFID("1011","不存在该日程！"),
    RET_NOBINDCARD("1012","该用户没有绑定物联卡！"),
    ERROR_BINDCARD("1013","您的卡号有误！"),
    RET_UPPER_LIMIT("1100","已达到置顶个数上限，请取消其他发文置顶设置后再操作"),
    
    RET_IDENTIFIER_NOT_FOUND("1010", "用户不能为空"),
    RET_NOROOT("1150","不属于自己的流程，无权限撤销"),
    
    RET_VOUCHER_IS_EMPTY("1800", "兑奖券码不能为空"),
    RET_VOUCHER_ERROR("1900", "兑奖券码有误"),
    
    RET_FAIL_1151("1151","该讨论组不存在"),
    RET_FAIL_1152("1152","不好意思，只有管理员才能删除成员"),
    RET_FAIL_1153("1153","不好意思，你不是该讨论组成员"),
    
    RET_ACCOUNT_FAIL("1154","输入账号有误，请重新输入！"),
    
    
    //名片异常码
    RET_GROUPNAME_NULL("750","名片组名称不能为空"),
    RET_GROUPNAME_EXIST("751","该名片组已存在！"),
    RET_EXISTCARD("752","该名片组下面有名片，请先移除后再删除！"),
    RET_CARD_NOEXIST("753","该名片不存在，或者被删除！"),
    RET_CARD_NUMOVER("754","您的名片已达到五张上限，请先删除后再添加！"),
    RET_TEMPLATE_IS_EMPTY("755", "名片模板id不能为空"),
    RET_ACTIVITY_IS_EMPTY("756", "未找到对应房间"),
    
    
    //微信小程序异常码
    WE_CHAT_NOREGISTER("760", "该用户未注册过"),
    WE_CHAT_SMSFAIL("761", "发送短信异常"),
    RET_CHATCODE_IS_FAIL("762", "验证码验证失败"),
    RET_SENDMAIL_FAIL("763", "发送邮件失败"),
    RET_EMAILNULL("764", "收件人邮箱未设置，无法发送邮件"),
    RET_BINDACCOUNT("765", "该手机号已绑定过微信"),
    RET_PHONEEXIST("766", "您已报过名"),
    
    
    //名片支付
    MP_CREATE_ERROR("771","订单创建失败"),
    MP_SELECT_ERROR("766","订单不存在"),
    MP_CANCEL_ERROR("767","修改订单状态失败"),
    MP_FIND_ERROR("768","查询订单状态失败"),
    MP_RECEIVE_ERROR("769","没有足够名片收取"),
    MP_TIME_ERROR("770","距上次下单没有超过5分钟"),
    
    //2018-7-26新版登陆注册接口异常码
    RET_OLDACCOUNTFAIL("150", "原手机号码未找到"),
    RET_NEWACCOUNT_EXIST("151", "新手机号码已存在"),
    RET_UPDATEOTHER_EXIST("152", "不能修改他人的手机号码"),
    //职在 心礼
    RET_GIFT_NOTEXIST("250", "该礼物未找到，可能被删除"),
    RET_PHONE_EXIST("251", "该手机号已注册过职在用户"),
    NOT_PREROGATIVE("252","用户特权次数不足"),
    RECIVER_ALREADY("253","该礼物已经被领取"),
    
    //2018-9-11脉圈小程序V2版本接口异常码
    PHONT_NOT_EXIST("260","该手机号码已经被使用"),
    
    RET_COMPANYNOEXSIT("651","公司账户不存在"),
    RET_AMOUNTLESS("650","账户余额不足"),
    
    
	ORDER_PAST("772","订单已过期"),
	
	//立咕互动错误信息
	RET_POINTNOTEMOUGH("160","用户积分不足"),
	
	//web脉圈名片/群体分析异常提示信息
	DATE_NOT_DATA("2000","当前时间段里没有脉圈信息"),
	//设备被绑定
	RET_DEVICE_BOUND_ERROR("504","该设备已被绑定！"),
	
	//非绑定设备登录提示
	RET_NOTBOUND_DEVICE_ERROR("503","签到设备错误，请更换与账号绑定一致的设备签到！"),
	
	//换新手机号提示
	RET_NEWDEVICE_BOUND_ERROR("507","您的账号已绑定其他签到设备，请联系人事绑定新设备！"),
	
	//换新手机号提示
	RET_VERSION_TOOLOW_ERROR("608","您当前使用的版本过低，请先升级！");
	
	private String code;
	private String msg;

	private ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
