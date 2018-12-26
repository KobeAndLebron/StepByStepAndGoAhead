package com.gohead.shared.test;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author bohan
 * @remark 获取金风全部表
 * @create 11/5/18 4:41 PM
 **/
public class getGwFieldGroup{

    public static void main(String[] args) throws IOException, HttpException {
        String dataSource = args[0];
        dataOutput(dataSource, args);
    }

    public static void dataOutput(String dataSource, String[] args) throws IOException, HttpException {
        String url_batch = "http://20.78.13.216:8124/batch-rest/dataset";
        String k2key = "eyJjdHkiOiJKV1QiLCJlbmMiOiJBMTkyQ0JDLUhTMzg0IiwiYWxnIjoiZGlyIn0..apUQ7MjpV2b7Y6C4qhWFlw.4VFS8DQC4EWCJe3nw_krP0s65yDSiJ3yKqL2fM3qRTc_3tsbIVopRrJ5KgQD6EVOcun9GzmqrOwFJKdDtLPmAB53PT0A5XienZjObXsHCXcg8Ao61aN4bf7a1A397IMy___z1GJNviGJ6glP5Y60lbYo3e-hg5P4IEc1cJSioeeBjusKzbMnPq54_QHTxT-FCxyR1pTTpBjHWT_lYc6p61W5jrKaFV9tgX_e1ONl0rn8wYOKnknNLcu0bXRwEnCI3mxtx9XLBhzQ_EFo75zQJQ.bfEofzcFP0i1Eniv8GwX2bxfnSIJNtOC";

        List resultArray;
        if ("sdm".equalsIgnoreCase(dataSource)) {
            String url_sdm = "http://20.78.13.216:8081/data-service/v2/field-groups";
            String res = httpGet(url_sdm, k2key);
            Gson gson = new Gson();
            Map resultMap = gson.fromJson(res, Map.class);
            resultArray = (List) resultMap.get("fieldGroups");
        } else if ("input".equalsIgnoreCase(dataSource)) {
            resultArray = new ArrayList();
            for (int i = 1; i < args.length; i++) {
                resultArray.add(args[i]);
            }
        } else {
            System.out.println("程序需要一个参数, 代表查询的表集合来源, 值为'sdm'和'input'(忽略大小写), " +
                    "分别表示查询SDM获取所有表和仅查询自己输入的表.");
            System.exit(-1);
            return;
        }

        // 数据总数
        long sum = 0;
        // 总花费时间.
        long sumTime = 0;
        for (Object fg: resultArray) {
            String fgId = (String) ((Map)fg).get("id");

            Map params = new HashMap();
            params.put("query", "select count(*) as count from " + fgId);
            params.put("resultType", "REST");
            long start = System.currentTimeMillis();
            Gson gson = new Gson();
            String resPost = httpPost(url_batch, k2key, gson.toJson(params));
            long end = System.currentTimeMillis();

            long cost = end - start;
            Map resMap = gson.fromJson(resPost,Map.class);
            Map results = (Map) resMap.get("body");
            String count = "0";

            if (!results.isEmpty()) {
                List resultCount = (List) results.get("results");
                count = (String) ((Map) resultCount.get(0)).get("count");
            }
            System.out.println("表名: " + fgId + "; 总数: " + count + "; 花费时间(ms): " + cost);
            sum += Long.parseLong(count);
            sumTime += cost;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("数据总数: " + sum + "; 总花费时间(ms): " + sumTime);
    }

    public static String httpGet(String url,String k2key)
            throws HttpException, IOException {
        String json = null;
        HttpGet httpGet = new HttpGet();
        HttpClient client = HttpClients.createDefault();
        // 设置参数
        try {
            httpGet.setURI(new URI(url));
            httpGet.setHeader("K2_KEY",k2key);
        } catch (URISyntaxException e) {
            throw new HttpException("请求url格式错误。"+e.getMessage());
        }
        // 发送请求
        HttpResponse httpResponse = client.execute(httpGet);
        // 获取返回的数据
        HttpEntity entity = httpResponse.getEntity();
        byte[] body = EntityUtils.toByteArray(entity);
        StatusLine sL = httpResponse.getStatusLine();
        int statusCode = sL.getStatusCode();
        if (statusCode == 200) {
            json = new String(body, "UTF-8");
            entity.consumeContent();
        } else {
            throw new HttpException("statusCode="+statusCode);
        }
        return json;
    }

    public static String httpPost(String url, String k2key, String requestBody)
            throws HttpException, IOException {
        String json = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("K2_KEY",k2key);
        httpPost.setHeader("Content-Type","application/json");
        HttpClient client = HttpClients.createDefault();

        StringEntity se = new StringEntity(requestBody);
//        se.setContentType("text/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(se);

        // 发送请求
        HttpResponse httpResponse = client.execute(httpPost);
        // 获取返回的数据
        HttpEntity entity = httpResponse.getEntity();
        byte[] body = EntityUtils.toByteArray(entity);
        StatusLine sL = httpResponse.getStatusLine();
        int statusCode = sL.getStatusCode();
        if (statusCode == 200) {
            json = new String(body, "UTF-8");
            entity.consumeContent();
        } else {
            throw new HttpException("statusCode="+statusCode);
        }
        return json;
    }
}
