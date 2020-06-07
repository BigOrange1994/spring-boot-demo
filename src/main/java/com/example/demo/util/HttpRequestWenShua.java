package com.example.demo.util;

import com.example.demo.testutil.ProtoBufUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestWenShua {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpRequestWenShua.class);

    private static PoolingHttpClientConnectionManager cm = null;
    
    private static RequestConfig defaultRequestConfig;

    private static final String BAIDU_URL = "http://wenshu.court.gov.cn/ValiCode/GetCode";
    // private static final String BAIDU_URL = "https://www.baidu.com";

    static {
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                .getSocketFactory();
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(10000);
        cm.setDefaultMaxPerRoute(cm.getMaxTotal());
        defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setConnectTimeout(2000)
                .setConnectionRequestTimeout(120000)
                .build();
        
    }

    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
        return httpClient;
    }
    
    /**
     * post请求
     * @param url
     * @param param
     * @return
     */
    public static String post(String url, String param) {
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpRequestWenShua.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        // 发送post请求
        try {

            String testJSON = "{\"id\":\"25cdb7aaf0515a \",\"os_type\":1,\"did_md5\":\"0f4e4715b415a794082644e92f72779b\"}";
            byte[] testData = ProtoBufUtil.serializer(testJSON);
            System.out.println(String.valueOf(testData));

            // 用post方法发送http请求
            HttpPost post = new HttpPost(url + param);
            post.setEntity( new ByteArrayEntity(testData, ContentType.create("application/x-protobuf")));

            post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            //post.setHeader("Host", "wenshu.court.gov.cn");
            post.setHeader("X-Requested-With", "XMLHttpRequest");
            post.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
            post.setHeader("Connection", "keep-alive");
            post.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
            //post.setHeader("Cookie","_gscu_2116842793=34518532cmg2sr13; UM_distinctid=1657a58c272b31-069c16c344fad-34677908-13c680-1657a58c273654; _gscu_125736681=38290988n33w1i17; Hm_lvt_9e03c161142422698f5b0d82bf699727=1538290989; Hm_lvt_d2caefee2de09b8a6ea438d74fd98db2=1537001550,1537586419,1538127399,1538962446; _gscbrs_2116842793=1; Hm_lpvt_d2caefee2de09b8a6ea438d74fd98db2=1538977751; vjkl5=" + vjkl5);
            //post.setHeader("Cookie","");
            //post.setHeader("Origin", "http://wenshu.court.gov.cn");
            //post.setHeader("Referer", "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1++%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6+%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6");
            httpResponse = httpClient.execute(post);
            //httpResponse = httpClient.execute(post);
            // response实体
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {
                byte[] bytes = EntityUtils.toByteArray(entity);
                //KuaiShouActiveResult d = ProtoBufUtil.deserializer(bytes, KuaiShouActiveResult.class);
                String json =  ProtoBufUtil.deserializer(bytes, String.class);
                System.out.println(json);
            }
            return null;
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            boolean proxyHostUnavaliable = errorMessage.contains("failed to respond") || errorMessage.contains("Connection refused")
                    || errorMessage.contains("connect timed out") || errorMessage.contains("Connection reset") || errorMessage.contains("Read timed out");
            if(!proxyHostUnavaliable){
                LOGGER.error("[HttpRequestWenShu][post]请求失败", e);
            }
            return null;
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                } catch (Exception e) {
                    LOGGER.error("[HttpRequestWenShu][post]关闭response失败", e);
                }
            }
        }
    }


    public static void main(String[] args) {
        //post("https://zhuan.58.com/activity/rta.kuaishou", "?id=111111&did_md5=16f6ba18963223ba912080140afd820b");
        //post("http://192.168.66.103:8025/activity/rta.kuaishou", "?id=111111&did_md5=d7d6004690f6f6044b0ec16d34c92d98");
        String testJSON = "{\"id\":\"25cdb7aaf0515a \",\"os_type\":1,\"did_md5\":\"0f4e4715b415a794082644e92f72779b\"}";
        byte[] testData = ProtoBufUtil.serializer(testJSON);
        String aaa = new String(testData);
        System.out.println(aaa);
        String json =  ProtoBufUtil.deserializer(testData, String.class);
        System.out.println(json);
        System.out.println("测试".length());
        /*List<String> aaList = new ArrayList<String>();
        aaList.add("1");
        aaList.add("2");

        List<String> aaaList = new ArrayList<String>();
        aaaList.addAll(aaList.subList(0,aaList.size()));
        System.out.println(aaaList.size());*/

    }
    
}