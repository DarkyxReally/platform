package com.platform.system.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import com.google.common.primitives.Bytes;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.web.filter.ResettableStreamHttpServletRequest;

public class WebUtil {

    /** Wap网关Via头信息中特有的描述信息 */
    private static String mobileGateWayHeaders[] = new String[]{"ZXWAP",// 中兴提供的wap网关的via信息，例如：Via=ZXWAP GateWayZTE Technologies，
            "chinamobile.com",// 中国移动的诺基亚wap网关，例如：Via=WTP/1.1 GDSZ-PB-GW003-WAP07.gd.chinamobile.com (Nokia WAP Gateway 4.1 CD1/ECD13_D/4.1.04)
            "monternet.com",// 移动梦网的网关，例如：Via=WTP/1.1 BJBJ-PS-WAP1-GW08.bj1.monternet.com. (Nokia WAP Gateway 4.1 CD1/ECD13_E/4.1.05)
            "infoX",// 华为提供的wap网关，例如：Via=HTTP/1.1 GDGZ-PS-GW011-WAP2 (infoX-WISG Huawei Technologies)，或Via=infoX WAP Gateway V300R001 Huawei Technologies
            "XMS 724Solutions HTG",// 国外电信运营商的wap网关，不知道是哪一家
            "Bytemobile",// 貌似是一个给移动互联网提供解决方案提高网络运行效率的，例如：Via=1.1 Bytemobile OSN WebProxy/5.1
    };
    /** 电脑上的IE或Firefox浏览器等的User-Agent关键词 */
    private static String[] pcHeaders = new String[]{"Windows 98", "Windows ME", "Windows 2000", "Windows XP", "Windows NT", "Ubuntu"};
    /** ipad的User-Agent关键字 **/
    private static String[] ipadUserAgents = new String[]{"iPad"// iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko) Version/4.0.4 Mobile/7B367
                                                                // Safari/531.21.10
    };
    /** 安卓pad*/
    private static String[] androidPadUserAgents = new String[]{"AndroidPad"};

    /** 手机浏览器的User-Agent里的关键词 */
    private static String[] mobileUserAgents = new String[]{"Nokia",// 诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0 (Nokia5800 XpressMusic)UC AppleWebkit(like Gecko) Safari/530
            "SAMSUNG",// 三星手机 SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1
            "MIDP-2",// j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2 NokiaE75-1 /110.48.125 Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like Gecko) Safari/413
            "CLDC1.1",// M600/MIDP2.0/CLDC1.1/Screen-240X320
            "SymbianOS",// 塞班系统的，
            "MAUI",// MTK山寨机默认ua
            "UNTRUSTED/1.0",// 疑似山寨机的ua，基本可以确定还是手机
            "Windows CE",// Windows CE，Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; IEMobile 7.11)
            "iPhone",// iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9 (KHTML like Gecko) Mobile/8B117
            "Android",// Android是否也转wap？Mozilla/5.0 (Linux; U; Android 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7) AppleWebKit/530.17 (KHTML like Gecko) Version/4.0 Mobile Safari/530.17
            "BlackBerry",// BlackBerry8310/2.7.0.106-4.5.0.182
            "UCWEB",// ucweb是否只给wap页面？ Nokia5800 XpressMusic/UCWEB7.5.0.66/50/999
            "ucweb",// 小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE 6.0;) Opera ucweb-squid
            "BREW",// 很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId: 40105; Lang: zhcn) ucweb-squid
            "J2ME",// 很奇怪的ua，只有J2ME四个字母
            "YULONG",// 宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0
            "YuLong",// 还是宇龙
            "COOLPAD",// 宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0
            "TIANYU",// 天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320
            "TY-",// 天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0
            "K-Touch",// 还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0
            "Haier",// 海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007 Browser/WAP2.0
            "DOPOD",// 多普达手机
            "Lenovo",// 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22 Profile/MIDP2.0 Configuration/CLDC1.1
            "LENOVO",// 联想手机，比如：LENOVO-P780/176A
            "HUAQIN",// 华勤手机
            "AIGO-",// 爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0
            "CTC/1.0",// 含义不明
            "CTC/2.0",// 含义不明
            "CMCC",// 移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0
            "DAXIAN",// 大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0
            "MOT-",// 摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006 Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1 Software/R533_G_11.10.54R
            "SonyEricsson",// 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0 (compatible; MSIE 6.0; Symbian OS; 405) Opera 8.65 [zh-CN]
            "GIONEE",// 金立手机
            "HTC",// HTC手机
            "ZTE",// 中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile
            "HUAWEI",// 华为手机，
            "webOS",// palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN) AppleWebKit/532.2 (KHTML like Gecko) Version/1.0 Safari/532.2 Pre/1.0
            "GoBrowser",// 3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290 Safari
            "IEMobile",// Windows CE手机自带浏览器，
            "WAP2.0"// 支持wap 2.0的
    };

    /**
     * ajax请求方式的请求头
     */
    public static final String AJAX_REQUEST = "XMLHttpRequest";

    /**
     * 判断是否为ajax请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        String requestedWay = request.getHeader("X-Requested-With");
        if(AJAX_REQUEST.equalsIgnoreCase(requestedWay)){
            return true;
        }
        return false;
    }

    /**
     * 获取IP地址
     * @param request
     */
    public static final String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip)) {
            String[] ips = ip.split(",");
            int i = 0;
            do{
                ip = ips[i].trim();
                if(RegexUtil.isIPAddress(ip)){
                    return ip;
                }
                ++i;
            }while(i < ips.length);
        }
        return ip;
    }

    /** 
     * 获取一个请求一个请求最初从浏览器发出时候，是使用什么协议。因为有可能当一个请求最初和反向代理通信时，是使用https，但反向代理和服务器通信时改变成http协议，这个时候，X-Forwarded-Proto的值应该是https
     * @param request
     * @return
     */
    public static final String getScheme(HttpServletRequest request){
        String scheme = request.getHeader("X-Forwarded-Proto");
        return StringUtils.isBlank(scheme) ? request.getScheme() : scheme;
    }

    /**
     * 获取当前请求的域名及端口
     * @param request
     * @param withPort 是否包含端口
     * @return
     */
    public static final String getFullHostUrl(HttpServletRequest request, boolean withPort){
        StringBuilder sb = new StringBuilder();
        String scheme = getScheme(request);
        String domain = request.getServerName();
        sb.append(scheme).append("://").append(domain);
        if(withPort){
            int port = request.getServerPort();
            if(port != 80){
                sb.append(":").append(port).toString();
            }
        }
        return sb.toString();
    }

    /**
     * 输出JSON
     * @param response
     * @param json
     * @throws IOException
     */
    public static final void writeJson(HttpServletResponse response, String json) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
    }

    /**
     * 表单csrf防护
     * @param request
     * @return
     */
    public static final String generatorFormToken(){
        /* 保护表单产生随机token */
        try{
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            long seq = random.nextLong();
            return String.valueOf(seq);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成表单防护key
     * @param request
     * @return
     */
    public static final String generatorFormTokenKey(String formUri){
        return "form_csrf_token" + formUri;
    }

    /**
     * 判断是否为微信浏览器
     * @param request
     * @return
     */
    public static final boolean isWxBrowser(HttpServletRequest request){
        String agent = "";
        try{
            agent = request.getHeader("User-Agent").toLowerCase();
        }catch(Exception e){
            return false;
        }
        return agent.indexOf("micromessenger") != -1;
    }

    /**
     * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
     * @param request http请求
     * @return 如果命中手机特征规则，则返回true
     */
    public static boolean isMobileDevice(HttpServletRequest request){
        boolean mobileFlag = false;
        String via = request.getHeader("Via");
        if(StringUtils.isNotBlank(via)){
            for(int i = 0; i < mobileGateWayHeaders.length; i++){
                if(via.contains(mobileGateWayHeaders[i])){
                    mobileFlag = true;
                    break;
                }
            }
        }

        String userAgent = request.getHeader("user-agent");
        if(!mobileFlag && StringUtils.isNotBlank(userAgent)){
            for(int i = 0; i < mobileUserAgents.length; i++){
                if(userAgent.contains(mobileUserAgents[i])){
                    mobileFlag = true;
                    break;
                }
            }
        }
        return mobileFlag;
    }

    /**
     * 判断请求是否来自PC
     * @param request
     * @return
     */
    public static boolean isPCDevice(HttpServletRequest request){
        boolean pcFlag = false;
        String userAgent = request.getHeader("user-agent");
        if(StringUtils.isNotBlank(userAgent)){
            for(int i = 0; i < pcHeaders.length; i++){
                if(userAgent.contains(pcHeaders[i])){
                    pcFlag = true;
                }
            }
        }
        return pcFlag;
    }

    /**
     * 判断请求是否来自Iphone
     * @param request
     * @return
     */
    public static boolean isIPhoneDevice(HttpServletRequest request){
        boolean ipadFlag = false;
        String userAgent = request.getHeader("user-agent");
        if(StringUtils.isNotBlank(userAgent)){
            if(userAgent.contains("iPhone")){
                ipadFlag = true;
            }
        }
        return ipadFlag;
    }

    /**
     * 判断请求是否来自Ipad
     * @param request
     * @return
     */
    public static boolean isIPadDevice(HttpServletRequest request){
        boolean ipadFlag = false;
        String userAgent = request.getHeader("user-agent");
        if(StringUtils.isNotBlank(userAgent)){
            for(int i = 0; i < ipadUserAgents.length; i++){
                if(userAgent.contains(ipadUserAgents[i])){
                    ipadFlag = true;
                }
            }
        }
        return ipadFlag;
    }

    /**
     * 判断请求是否来自安卓
     * @param request
     * @return
     */
    public static boolean isAndroidDevice(HttpServletRequest request){
        boolean flag = false;
        String userAgent = request.getHeader("user-agent");
        if(StringUtils.isNotBlank(userAgent)){
            if(userAgent.contains("Android")){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断请求是否来自安卓pad
     * @param request
     * @return
     */
    public static boolean isAndroidPadDevice(HttpServletRequest request){
        boolean flag = false;
        String userAgent = request.getHeader("user-agent");
        if(StringUtils.isNotBlank(userAgent)){
            for(int i = 0; i < androidPadUserAgents.length; i++){
                if(userAgent.contains(androidPadUserAgents[i])){
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 判断请求是否来自pad
     * @param request
     * @return
     */
    public static boolean isPadDevice(HttpServletRequest request){
        boolean flag = isIPadDevice(request) || isAndroidPadDevice(request);
        if(!flag){
            String userAgent = request.getHeader("user-agent");
            if(StringUtils.isNotBlank(userAgent)){
                if(userAgent.contains("pad")){
                    flag = true;
                }
            }
        }

        return flag;
    }

    /**
     * redirectURL是否合法
     * @param serverName
     * @param redirectURL
     * @return
     */
    public static boolean isRedirectUrlValid(String serverName, String redirectURL){
        try{
            URI uri = new URI(redirectURL);
            if(StringUtils.isNotBlank(uri.getHost())){
                if(!StringUtils.equals(serverName, uri.getHost())){
                    return false;
                }
            }
            URL url = new URL(redirectURL);
            if(StringUtils.isNotBlank(url.getHost())){
                if(!StringUtils.equals(serverName, url.getHost())){
                    return false;
                }
            }
        }catch(Exception e){}

        while(redirectURL.startsWith("//")){
            redirectURL = redirectURL.substring(2);
        }

        if(!redirectURL.startsWith("http")){
            return true;
        }

        int index = 0;
        if(StringUtils.indexOf(redirectURL, "https://") == 0){
            index = 8;
        }else if(StringUtils.indexOf(redirectURL, "http://") == 0){
            index = 7;
        }

        boolean isValid = false;
        // 判断跳转页面是否同属一个域名
        if(StringUtils.isNotBlank(serverName)){
            // 比较长度
            if(redirectURL.length() - index > serverName.length()){
                // 比较字符串（主机名称）是否相同
                String redirect = redirectURL.substring(index, index + serverName.length());
                if(serverName.equalsIgnoreCase(redirect)){
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    /**
     * 拼接URL参数
     * @param url
     * @param params
     * @return
     */
    public static String appendUrlParams(String url, Map<String, String> params){
        if(StringUtils.isBlank(url) || CollectionUtils.isEmpty(params)){
            return url;
        }

        StringBuilder paramsStr = new StringBuilder();
        String key = null;
        String value = null;
        for(Entry<String, String> entry : params.entrySet()){
            key = entry.getKey();
            if(StringUtils.isBlank(key)){
                continue;
            }
            key = UrlEnDecode.encode(key);
            value = entry.getValue();
            if(null == value){
                value = "";
            }else{
                key = UrlEnDecode.encode(value);
            }
            paramsStr.append(key).append("=").append(value).append("&");
        }

        if(paramsStr.length() == 0){
            return url;
        }

        String strParams = paramsStr.substring(0, paramsStr.length() - 1);
        if(url.indexOf("?") != -1){
            url += "&" + strParams;
        }else{
            url += "?" + strParams;
        }
        return url;
    }
    
    /**
     * 获取请求引用页
     * @param request
     * @return
     */
    public static String getReferer(HttpServletRequest request){
        return request.getHeader("Referer");
    }
    
    /**
     * 获取请求代理头
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request){
        return request.getHeader("user-agent");
    }
    
    /**
     * 是否http开头
     * @param url
     * @return
     */
    public static boolean startWithHttp(String url){
        return url.startsWith("http");
    }
    
    /**
     * 是否https开头
     * @param url
     * @return
     */
    public static boolean startWithHttps(String url){
        return url.startsWith("https");
    }

    /**
     * 获取版本号
     * @param request
     * @return
     */
    public static final int getVersionNo(HttpServletRequest request){
        String version = request.getHeader(CommonConstants.HEADER_VERSION);
        if(StringUtils.isBlank(version)){
            // 没有版本号
            return 0;
        }
        try{
            return Integer.parseInt(version.replace(".", ""));
        }catch(Exception e){
            throw new NumberFormatException("版本号格式不正确");
        }
    }
    
    /**
     * 获取提交上来的内容
     * @version: 1.0
     * @param request
     * @return
     * @throws IOException
     */
    public static final byte[] getRequestBody(HttpServletRequest request) throws IOException{
        byte[] requestBody = new byte[0];
        InputStream inputStream = request.getInputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer)) != -1){
            requestBody = Bytes.concat(requestBody, Arrays.copyOfRange(buffer, 0, bytesRead));
        }
        return Arrays.copyOf(requestBody, requestBody.length);
    }
    
    /**
     * 获取提交上来的内容
     * @version: 1.0
     * @param request
     * @return
     * @throws IOException
     */
    public static final String getRequestBodyString(HttpServletRequest request) throws IOException{
        String reqBody = null;
        try{
            reqBody = (String)request.getAttribute(RequestAttributeConst.REQUEST_BODY_KEY);
        }catch(Exception e){
            // 忽略掉
        }
        if(StringUtils.isBlank(reqBody)){
            byte[] requestBody = null;
            if(request instanceof ResettableStreamHttpServletRequest){
                requestBody = ((ResettableStreamHttpServletRequest)request).getRequestBody();
            }else{
                requestBody = getRequestBody(request);
            }
            if(null != requestBody){
                reqBody = new String(requestBody, CommonConstants.UTF_8);
                request.setAttribute(RequestAttributeConst.REQUEST_BODY_KEY, reqBody);
            }
        }
        return reqBody;
    }
    
    /**
     * 判断IP地址是否本地内部网络
     * @version: 1.0
     * @param ip
     * @return
     */
    public static final boolean isInnerNetwork(String ip){
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }
}
