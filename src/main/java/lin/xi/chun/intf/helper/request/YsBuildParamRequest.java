package lin.xi.chun.intf.helper.request;

/**
 * @author zhou.wu
 * @description: YS构建参数请求
 * @date 2022/3/17
 **/
public class YsBuildParamRequest {

    private Long requestTime;

    private String requestUrl;

    private String appKey;

    private String requestSecret;

    private String paramUri;

    public Long getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getRequestSecret() {
        return this.requestSecret;
    }

    public void setRequestSecret(String requestSecret) {
        this.requestSecret = requestSecret;
    }

    public String getParamUri() {
        return this.paramUri;
    }

    public void setParamUri(String paramUri) {
        this.paramUri = paramUri;
    }
}
