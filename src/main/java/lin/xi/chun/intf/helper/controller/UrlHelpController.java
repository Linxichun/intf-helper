package lin.xi.chun.intf.helper.controller;

import com.alibaba.fastjson.JSON;
import lin.xi.chun.intf.helper.request.IrsBuildParamRequest;
import lin.xi.chun.intf.helper.request.YsBuildParamRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static lin.xi.chun.intf.helper.constant.Constant.*;

/**
 * @author zhou.wu
 * @description: 接口辅助
 * @date 2022/3/17
 **/
@RestController
@RequestMapping({"/url-help"})
public class UrlHelpController {

    private static final Logger log = LoggerFactory.getLogger(UrlHelpController.class);

    @Value("${os.type:windows}")
    private String osType;

    @RequestMapping(value = {"/ys-url"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String getYsUrl(@RequestBody YsBuildParamRequest request) {
        String appKey = request.getAppKey();
        String requestSecret = request.getRequestSecret();
        Long requestTime = request.getRequestTime();
        if (requestTime == null)
            requestTime = Long.valueOf(System.currentTimeMillis());
        String sign = buildYsSign(appKey, requestTime, requestSecret);
        String url = buildYsUrl(request.getRequestUrl(), appKey, requestTime, sign, request.getParamUri());
        return url;
    }

    @RequestMapping(value = {"/ys-url/build"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> buildYsUrl(@RequestBody YsBuildParamRequest request) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        String appKey = request.getAppKey();
        String requestSecret = request.getRequestSecret();
        Long requestTime = request.getRequestTime();
        if (requestTime == null)
            requestTime = Long.valueOf(System.currentTimeMillis());
        resultMap.put(YS_PARAM_NAME_APP_KEY, appKey);
        resultMap.put(YS_PARAM_NAME_REQUEST_TIME, requestTime);
        String sign = buildYsSign(appKey, requestTime, requestSecret);
        resultMap.put(YS_PARAM_NAME_SIGN, sign);
        String url = buildYsUrl(request.getRequestUrl(), appKey, requestTime, sign, request.getParamUri());
        resultMap.put("getUrl", url);
        return resultMap;
    }

    @RequestMapping(value = {"/irs-request-body/build"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> buildIrsUrl(@RequestBody IrsBuildParamRequest request) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        String appKey = request.getAppKey();
        String appSecret = request.getAppSecret();
        Long requestTime = request.getRequestTime();
        if (requestTime == null)
            requestTime = Long.valueOf(System.currentTimeMillis());
        String sign = buildIrsSign(appKey, appSecret, requestTime);
        Map<String, Object> requestBody = buildIrsRequestBody(appKey, requestTime, sign, request.getBizParams());
        return requestBody;
    }

    @RequestMapping(value = {"/str/encode"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> urlEncode(String str, String enc) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("str", str);
        if (enc == null || enc.equals(""))
            enc = "UTF-8";
        resultMap.put("enc", enc);
        try {
            String encodeStr = URLEncoder.encode(str, enc);
            resultMap.put("encodeStr", encodeStr);
            System.out.println("encodeStr=" + encodeStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /******************************************************************************************************/
    /**
     * 构建单个参数URI
     * @author zhou.wu
     * @date 2022/3/17
     * */
    private static String buildOneParamUri(String name, String value) {
        return name + "=" + value;
    }

    /**
     * 获取分割字符（根据操作系统不同）
     * @author zhou.wu
     * @date 2022/3/17
     * */
    private String splitStr() {
        String str = "&";
        switch (this.osType) {
            case "linux":
                str = "\\&";
                break;
        }
        return str;
    }

    /**
     * 构建YS Sign
     * @author zhou.wu
     * @date 2022/3/17
     * */
    public String buildYsSign(String appKey, Long requestTime, String requestSecret) {
        log.info("***** build YS sign *****");
        log.info("{}={}", YS_PARAM_NAME_APP_KEY, appKey);
        log.info("{}={}", YS_PARAM_NAME_REQUEST_TIME, requestTime);
        log.info("{}={}", YS_PARAM_NAME_REQUEST_SECRET, requestSecret);
        String str = appKey + requestSecret + requestTime;
        log.info("signStr={}", str);
        String sign = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8)).toLowerCase();
        return sign;
    }

    /**
     * 构建YS URL
     * @author zhou.wu
     * @date 2022/3/17
     * */
    public String buildYsUrl(String requestUrl, String appKey, Long requestTime, String sign, String paramUri) {
        log.info("***** build YS url *****");
        log.info("requestUrl={}", requestUrl);
        log.info("{}={}", YS_PARAM_NAME_APP_KEY, appKey);
        log.info("{}={}", YS_PARAM_NAME_REQUEST_TIME, requestTime);
        log.info("{}={}", YS_PARAM_NAME_SIGN, sign);
        log.info("paramUri={}", paramUri);
        StringBuffer getUrl = new StringBuffer((requestUrl == null) ? "" : requestUrl);
        getUrl.append("?").append(buildOneParamUri(YS_PARAM_NAME_REQUEST_TIME, String.valueOf(requestTime)))
                .append(splitStr()).append(buildOneParamUri(YS_PARAM_NAME_APP_KEY, appKey))
                .append(splitStr()).append(buildOneParamUri(YS_PARAM_NAME_SIGN, sign))
                .append((paramUri == null) ? "" : paramUri);
        return getUrl.toString();
    }

    /**
     * 构建IRS Sign
     * @author zhou.wu
     * @date 2022/3/17
     * */
    public String buildIrsSign(String appKey, String appSecret, Long requestTime) {
        log.info("***** build IRS sign *****");
        log.info("{}={}", IRS_PARAM_NAME_APP_KEY, appKey);
        log.info("{}={}", IRS_PARAM_NAME_APP_SECRET, appSecret);
        log.info("{}={}", IRS_PARAM_NAME_REQUEST_TIME, requestTime);
        String str = appKey + appSecret + requestTime;
        log.info("signStr={}", str);
        String sign = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8)).toLowerCase();
        return sign;
    }

    /**
     * 构建IRS请求体
     * @author zhou.wu
     * @date 2022/3/17
     * */
    public Map<String, Object> buildIrsRequestBody(
            String appKey,
            Long requestTime,
            String sign,
            Map<String, Object> otherParams
    ) {
        log.info("***** build IRS url *****");
        log.info("{}={}", IRS_PARAM_NAME_APP_KEY, appKey);
        log.info("{}={}", IRS_PARAM_NAME_REQUEST_TIME, requestTime);
        log.info("{}={}", IRS_PARAM_NAME_SIGN, sign);
        log.info("otherParams={}", JSON.toJSONString(otherParams));
        Map<String,Object> body = new HashMap<>();
        body.put(IRS_PARAM_NAME_APP_KEY, appKey);
        body.put(IRS_PARAM_NAME_REQUEST_TIME, requestTime);
        body.put(IRS_PARAM_NAME_SIGN, sign);
        body.putAll(otherParams);
        return body;
    }

    public static void main(String[] args) {
        String jsonStr = "[{\"ZSXMMC\":\"\",\"JSJE\":2026710.79,\"NSRMC\":\"\",\"SKSSQQ\":\"2019-07-01\",\"NSRQ\":\"2019-07-18\",\"BZ\":\"\",\"SWJGMC\":\"\",\"NSRSBH\":\"330282198910030048\",\"SJJE\":30400.66,\"SKSSQZ\":\"2019-07-31\",\"DZSPHM\":\"333021190700092281\",\"ZSPMMC\":\"\",\"KSSL\":143.79,\"SL_1\":0.03},{\"ZSXMMC\":\"\",\"JSJE\":228000,\"NSRMC\":\"\",\"SKSSQQ\":\"2019-07-01\",\"NSRQ\":\"2019-07-18\",\"BZ\":\"\",\"SWJGMC\":\"\",\"NSRSBH\":\"330282198910030048\",\"SJJE\":6840,\"SKSSQZ\":\"2019-07-31\",\"DZSPHM\":\"333021190700100095\",\"ZSPMMC\":\"\",\"KSSL\":15.17,\"SL_1\":0.03}]";
    }
}
