package edu.jmu.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jmu.http.HttpClientService;
import edu.jmu.http.HttpResult;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Rest请求服务类
 *
 * @author guantianmin
 * @date 2018/6/25 9:49
 */
public class RestApiRequestService {
    private String username;
    private String password;
    private String companyName;
    private Integer loginType;
    private String homeUrl;
    @Value("${jwt.route.authentication.path}")
    private String loginPath;
    private HttpClientService httpClientService;
    @Autowired
    private ObjectMapper jsonMapper;
    @Value("${jwt.header}")
    private String tokenHeader;
    private String token;

    private String getToken() throws IOException {
        String token = null;
        String url = "".concat(homeUrl).concat(loginPath);
        HashMap<String, Object> user = new HashMap<>(5);
        user.put("username", username);
        user.put("password", password);
        user.put("companyName", companyName);
        user.put("loginType", loginType);
        String userJson = jsonMapper.writeValueAsString(user);
        HttpResult result = httpClientService.doPostJson(url, userJson, null, null, "utf-8");
        if (result.getStatus().equals(200)) {
            token = jsonMapper.readValue(result.getData(), HashMap.class).get("token").toString();
        }
        return token;
    }

    public String requestApi(Function<List<Header>, HttpResult> request) throws IOException {
        List<Header> headerList = new ArrayList<>();
        if (token != null) {
            headerList.add(new BasicHeader(tokenHeader, "Bearer ".concat(token)));
        }
        HttpResult result = request.apply(headerList);
        Integer status = result == null ? 400 : result.getStatus();
        if (status.equals(401) || status.equals(403) || status.equals(202)) {
            token = getToken();
            if (token != null) {
                headerList.add(new BasicHeader(tokenHeader, "Bearer ".concat(token)));
                result = request.apply(headerList);
            }
        }
        if (result != null && result.getStatus().equals(200)) {
            return result.getData();
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public HttpClientService getHttpClientService() {
        return httpClientService;
    }

    public void setHttpClientService(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }
}
