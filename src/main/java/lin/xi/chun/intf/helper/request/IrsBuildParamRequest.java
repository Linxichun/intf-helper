package lin.xi.chun.intf.helper.request;

import java.util.Map;

/**
 * @author zhou.wu
 * @description: YS构建参数请求
 * @date 2022/3/17
 **/
public class IrsBuildParamRequest {

    private String requestUrl;

    private Long requestTime;

    private String appKey;

    private String appSecret;

    private String requestSecret;

    private String paramUri;

    private Map<String, Object> bizParams;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Long getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRequestSecret() {
        return requestSecret;
    }

    public void setRequestSecret(String requestSecret) {
        this.requestSecret = requestSecret;
    }

    public String getParamUri() {
        return paramUri;
    }

    public void setParamUri(String paramUri) {
        this.paramUri = paramUri;
    }

    public Map<String, Object> getBizParams() {
        return bizParams;
    }

    public void setBizParams(Map<String, Object> bizParams) {
        this.bizParams = bizParams;
    }
}
