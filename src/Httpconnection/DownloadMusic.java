package Httpconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * HttpClient 娴嬭瘯绫伙紝鎻愪緵get post鏂规硶瀹炰緥
 */
public class DownloadMusic {
    private static final String URL = "http://service.uspacex.com/music.server/downloadMusic?md5={md5value of music}";
    private static final int PORT = 80;

    public static void main(String[] args) throws Exception {
        // Test GET method
        doGetMethod(URL, PORT);
    }

    /**
     * HTTP GET鏂规硶
     */
    public static String doGetMethod(String url, int port) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        // 璁剧疆浠ｇ悊鏈嶅姟鍣ㄥ湴鍧�鍜岀鍙�
        client.getHostConfiguration().setHost(url, port);

        // 浣跨敤 GET 鏂规硶
        GetMethod method = new GetMethod(url);
        // 鎵ц璇锋眰
        client.executeMethod(method);

        // 鎵撳嵃鏈嶅姟鍣ㄨ繑鍥炵殑鐘舵��
        System.out.println(method.getStatusLine());
        // &#x93b5;&#x64b3;&#x5d43;&#x6769;&#x65bf;&#x6d16;&#x9428;&#x52ea;&#x4fca;&#x93ad;&#xfffd;
        // System.out.println(method.getResponseBodyAsString());
        // response body of large or unknown size. Using getResponseBodyAsStream
        // instead is recommended.
        InputStream bodystreams = method.getResponseBodyAsStream();
        String body = convertStreamToString(bodystreams);
        System.out.println(body);
        // 閲婃斁杩炴帴
        method.releaseConnection();

        return body;
    }
 public void fastJson(String body){

 }
    /**
     * HTTP POST鏂规硶
     */
    public static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
        // BufferedReader reader = new BufferedReader(new
        // InputStreamReader(is));//杈撳嚭鐨勪腑鏂囦贡鐮�
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf8")); // GBK
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}