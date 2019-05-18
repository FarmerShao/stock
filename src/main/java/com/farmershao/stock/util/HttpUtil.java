package com.farmershao.stock.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static final int SO_TIMEOUT = 5000;

    private static final int CONNECTION_TIMEOUT = 5000;

    public static String doGet(String url, Map<String, String> map) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT).build(); // 设置请求和传输超时时间
        HttpGet httpGet = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            httpGet = new HttpGet(url + "?" + str);
            httpGet.setConfig(requestConfig);

            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                return IOUtils.toString(entity.getContent(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("http请求错误，url为:{}", url);
            log.error("http请求错误，param为:{}", map.toString());
            log.error("http get request is error!", e);
        } finally {
            try {
                if(httpGet != null) {
                    httpGet.abort();
                }
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error("http关闭异常：", e);
            }
        }
        return null;
    }
}
