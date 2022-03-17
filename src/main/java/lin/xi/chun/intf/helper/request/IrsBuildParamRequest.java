package lin.xi.chun.intf.helper.request;

import java.util.Map;

/**
 * @author zhou.wu
 * @description: YS构建参数请求
 * @date 2022/3/17
 **/
public class IrsBuildParamRequest {

    private Long requestTime;

    private String appKey;

    private String appSecret;

    private Map<String, Object> bizParams;

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

    public Map<String, Object> getBizParams() {
        return bizParams;
    }

    public void setBizParams(Map<String, Object> bizParams) {
        this.bizParams = bizParams;
    }
}
