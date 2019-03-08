package edu.jmu.http; /**
 * Created by guantianmin on 2017/10/17.
 */

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * httpClient服务类
 *
 * @author guantianmin
 * @date 2018-06-22
 */
public class HttpClientService {

    private CloseableHttpClient httpClient;

    private RequestConfig requestConfig;

    public HttpClientService(CloseableHttpClient httpClient, RequestConfig requestConfig) {
        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    public String doGet(String url) throws IOException {
        return doGet(url, (ArrayList<Cookie>) null);
    }

    public String doGet(String url, Cookie cookie) throws IOException {
        ArrayList<Cookie> cookies = new ArrayList<Cookie>();
        cookies.add(cookie);
        return doGet(url, cookies);
    }

    /**
     * 执行GET请求
     *
     * @param url
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String doGet(String url, List<Cookie> cookies, List<Header> headers) throws IOException {
        return doRequestByContentType(HttpMethod.GET, url, null, null, "UTF-8", cookies, headers).getData();
    }

    public HttpResult doGetJson(String url, List<Header> headers) throws IOException {
        return doRequestByContentType(HttpMethod.GET, url, null, ContentType.APPLICATION_JSON, "UTF-8", null, headers);
    }

    public String doGet(String url, List<Cookie> cookies) throws IOException {
        return doGet(url, cookies, null);
    }

    /**
     * 带有参数的GET请求
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String doGet(String url, Map<String, String> params)
            throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        for (String key : params.keySet()) {
            uriBuilder.addParameter(key, params.get(key));
        }
        return this.doGet(uriBuilder.build().toString());
    }

    /**
     * 执行POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) throws IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (params != null) {
            // 设置2个post参数，一个是scope、一个是q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 执行POST请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url) throws IOException {
        return this.doPost(url, null);
    }

    public HttpResult doRequestByContentType(HttpMethod httpMethod, String url, String content, ContentType contentType) throws IOException {
        return doRequestByContentType(httpMethod, url, content, contentType, "UTF-8", null);
    }

    public HttpResult doRequestByContentType(HttpMethod httpMethod, String url, String content, ContentType contentType, String encoding) throws IOException {
        return doRequestByContentType(httpMethod, url, content, contentType, encoding, null);
    }

    public HttpResult doPostXml(String url, String content) throws IOException {
        return doPostXml(url, content, "UTF-8");
    }

    public HttpResult doPostXml(String url, String content, String encoding) throws IOException {
        return doRequestByContentType(HttpMethod.POST, url, content, ContentType.APPLICATION_XML, encoding);
    }

    public HttpResult doPostJson(String url, String json, List<Cookie> cookies) throws IOException {
        return doPostJson(url, json, cookies, "UTF-8");
    }

    public HttpResult doPostJson(String url, String json, List<Cookie> cookies, String encoding) throws IOException {
        return doPostJson(url, json, cookies, null, encoding);
    }

    public HttpResult doPostJson(String url, String json, List<Cookie> cookies, List<Header> headers, String encoding) throws IOException {
        return doRequestByContentType(HttpMethod.POST, url, json, ContentType.APPLICATION_JSON, encoding, cookies, headers);
    }

    /**
     * 提交json数据
     *
     * @param url
     * @param json
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPostJson(String url, String json) throws IOException {
        return doPostJson(url, json, "UTF-8");
    }

    public HttpResult doPostJson(String url, String json, String encoding) throws IOException {
        return doPostJson(url, json, null, encoding);
    }

    private String getCookies(List<Cookie> cookies) {
        StringBuilder sb = new StringBuilder();
        if (cookies != null && cookies.size() > 0) {
            for (Cookie cookie :
                    cookies) {
                sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
            }
        }
        return sb.toString();
    }

    private HttpResult doRequestByContentType(HttpMethod httpMethod, String url, String content, ContentType contentType, String encoding, List<Cookie> cookies, List<Header> headers) throws IOException {
        // 创建http POST请求
        HttpRequestBase request;
        if (httpMethod.equals(HttpMethod.POST)) {
            request = new HttpPost(url);
        } else {
            request = new HttpGet(url);
        }
        request.setConfig(this.requestConfig);
        String contentTypeStr = "";
        if (contentType != null) {
            if (contentType.equals(ContentType.APPLICATION_JSON)) {
                contentTypeStr = "application/json";
            }
            if (contentType.equals(ContentType.APPLICATION_XML)) {
                contentTypeStr = "application/xml";
            }
        }
        if (!contentTypeStr.equals("")) {
            if (encoding != null && !encoding.equals("")) {
                contentTypeStr += "; charset=" + encoding;
            }
            request.setHeader("Content-Type", contentTypeStr);
        }
        if (cookies != null && cookies.size() > 0) {
            request.setHeader("Cookie", getCookies(cookies));
        }
        if (headers != null && headers.size() > 0) {
            for (Header header : headers) {
                request.setHeader(header);
            }
        }
        if (content != null && httpMethod.equals(HttpMethod.POST)) {
            StringEntity stringEntity = new StringEntity(content, contentType);
            // 将请求实体设置到httpPost对象中
            HttpPost post = (HttpPost) request;
            post.setEntity(stringEntity);
            request = post;
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.httpClient.execute(request);
            return new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), encoding));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private HttpResult doRequestByContentType(HttpMethod httpMethod, String url, String content, ContentType contentType, String encoding, List<Cookie> cookies) throws IOException {
        return doRequestByContentType(httpMethod, url, content, contentType, encoding, cookies, null);
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public enum HttpMethod {
        POST, GET
    }
}