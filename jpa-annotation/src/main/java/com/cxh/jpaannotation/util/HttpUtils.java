package com.cxh.jpaannotation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>Title: HttpUtils</p>
 */
@Slf4j
@Component
public class HttpUtils {
	private static Logger logger = LogManager.getLogger(HttpUtils.class);

	private static ObjectMapper mapper = new ObjectMapper();
	private static final String APPLICATION_JSON = "application/json";

	@Value("${server.port}")
	private String local_port;

	//本地服务端口号
	private static String serviceLocalPort;
	//利用@PostConstruct将yml中配置的值赋给本地的变量
	@PostConstruct
	public void getEnvironment(){
		serviceLocalPort = this.local_port;
	}


	private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000)
			.setConnectTimeout(20000).setSocketTimeout(20000).build();

	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            +"|windows (phone|ce)|blackberry"
            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            +"|laystation portable)|nokia|fennec|htc[-_]"
            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

	/**
	 * 获取请求的IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestAddr(HttpServletRequest request) {
		if (request == null)
		{
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}

		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

	/**
	 * @Comment 获取服务器Ip
	 * @return
	 */
	public static boolean isLocalAddr(String ip) {
		List<String> ipConfig = new ArrayList<>();
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						ipConfig.add(inetAddress.getHostAddress().toString());
					}

				}
			}
		} catch (SocketException ex) {
		}
		return ipConfig.contains(ip);
	}

	/**
	 * GET请求
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpGet(String url) throws Exception {
		String resContent = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		try {
			HttpGet httpGet = new HttpGet(url.replace(" ", "%20"));
			httpGet.setConfig(requestConfig);
			response = client.execute(httpGet);
			int resCode = response.getStatusLine().getStatusCode();
			if (resCode == 200) {
				resContent = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resContent;
	}

	public static String sendHttpGet(String url, HttpServletRequest request) throws Exception {
		String resContent = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		try {
			HttpGet httpGet = new HttpGet(url.replace(" ", "%20"));
			httpGet.setConfig(requestConfig);
			httpGet.setHeader(new BasicHeader("Cookie", "SESSION=" + request.getRequestedSessionId()));
			response = client.execute(httpGet);

			int resCode = response.getStatusLine().getStatusCode();
			if (resCode == 200) {
				resContent = EntityUtils.toString(response.getEntity(),"UTF-8");
			}
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resContent;
	}

	/**
	 * POST请求
	 *
	 * @param url
	 * @param params
	 *            key value
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpPost(String url, Map<String, Object> params) throws Exception {
		String resContent = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				nvps.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = client.execute(httpPost);
			int resCode = response.getStatusLine().getStatusCode();
			if (resCode == 200) {
				resContent = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return resContent;
	}

	public static String sendHttpPostJson(String url, Object obj, HttpServletRequest request) throws Exception {
		String resContent = null;
		// HttpResponse response = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String jsonStr = mapper.writeValueAsString(obj);
			// DefaultHttpClient httpClient1 = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			httpPost.setHeader(new BasicHeader("Cookie", "SESSION=" + request.getRequestedSessionId()));
			StringEntity se = new StringEntity(jsonStr, "UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			httpPost.setEntity(se);
			response = httpClient.execute(httpPost);
			int resCode = response.getStatusLine().getStatusCode();
			if (resCode == 200) {
				HttpEntity httpEntity = response.getEntity();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(httpEntity.getContent(), "UTF-8"));
				StringBuilder entityStringBuilder = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					entityStringBuilder.append(line);
				}
				resContent = entityStringBuilder.toString();
			} else {
				logger.warn("Response code:{}", resCode);
			}
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}
			try {
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resContent;
	}

	/**
	 * @description 得到本地IP地址
	 * @author Ron
	 * @date 2020年4月27日-下午9:03:05
	 * @return
	 */
	public static String getLocalAddress(){
		String ipAddr = "";
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				ipAddr = getHostAddr("eth1", netInterface);
				return (ipAddr == "") ? getHostAddr("eth0", netInterface) : ipAddr;
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return ipAddr;
	}

	private static String getHostAddr(String netInterName, NetworkInterface netInterface){
		if(netInterName.equals(netInterface.getName())){
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					return ip.getHostAddress();
				}
			}
		}
		return "";
	}

	/**
	 * @description 获取根目录
	 * @author Ron
	 * @date 2020年5月6日-下午5:15:35
	 * @param request
	 * @return
	 */
	public static String getRequestBaseUrl(HttpServletRequest request) {
		String url = request.getScheme()+"://" + request.getServerName() + request.getContextPath();
		if (request.getServerPort() != 80 && request.getServerPort() != 443) {
			url = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
		return url;
	}

	/**
	 * @author Ron
	 * @description 获取当前服务器下的url请求地址
	 * @date 15:12 2021/2/2
	 * @return java.lang.String
	 **/
	public static String getLocalHttpBaseUrl(HttpServletRequest request) {
		String url = "http://127.0.0.1:"+ serviceLocalPort + request.getContextPath();
		return url;
	}

	public static String sendGet(String url, String param, String contentType)
	{
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try
		{
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}
		}
		catch (ConnectException e)
		{
			log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
		}
		catch (SocketTimeoutException e)
		{
			log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
		}
		catch (IOException e)
		{
			log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
		}
		catch (Exception e)
		{
			log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			}
			catch (Exception ex)
			{
				log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
			}
		}
		return result.toString();
	}
}
