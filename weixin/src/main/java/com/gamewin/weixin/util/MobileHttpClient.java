package com.gamewin.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class MobileHttpClient {

	public static void main(String[] args) throws Exception {

		//String access_token = getAccessToken();
		//System.out.println(access_token);
		String access_token="cXW9din35BAzEeMAsEY2PAANgs3xDi4hLLzXnL_8N4pJYhB1Yjpavz7tD926yyL-qG00Wr7_M844WQqUk73Sum_9igHiqfYn_Z_G2VBmxTs";
		//String ticket=getJsapi_ticket(access_token);
		//System.out.println(ticket);
		String ticket="gQFr8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzVUdlpJSUxrSUhkWnRZOHVSeFZyAAIETzyCVQMEgDoJAA==";
		getticketImage(URLEncoder.encode(ticket,"UTF-8"));
	}

	public static String getAccessToken() throws Exception {

		String access_token = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + MobileContants.appID
					+ "&secret=" + MobileContants.appsecret);

			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			JSONObject resultJsonObject = null;
			try {
				System.out.println(response1.getStatusLine());
				HttpEntity httpEntity = response1.getEntity();

				if (httpEntity != null) {
					try {
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
						StringBuilder entityStringBuilder = new StringBuilder();
						String line = null;
						while ((line = bufferedReader.readLine()) != null) {
							entityStringBuilder.append(line + "/n");
						}
						// 利用从HttpEntity中得到的String生成JsonObject
						resultJsonObject = new JSONObject(entityStringBuilder.toString());
						access_token = resultJsonObject.get("access_token") + "";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}

		return access_token;

	}

	/**
	 * 创建二维码ticket
	 * 
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	public static String getJsapi_ticket(String access_token) throws Exception {
		String jsapi_ticket = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("expire_seconds", 604800);
			jsonObj.put("action_name", "QR_SCENE"); 
			jsonObj.put("action_info", new JSONObject().put("scene", new JSONObject().put("scene_id", 123))); 
			
			StringEntity entity = new StringEntity(jsonObj.toString(), "UTF-8");

			httpPost.setEntity(entity);
			
			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			JSONObject resultJsonObject = null;
			try {
				System.out.println(response1.getStatusLine());
				HttpEntity httpEntity = response1.getEntity();

				if (httpEntity != null) {
					try {
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
						StringBuilder entityStringBuilder = new StringBuilder();
						String line = null;
						while ((line = bufferedReader.readLine()) != null) {
							entityStringBuilder.append(line);
						}
						// 利用从HttpEntity中得到的String生成JsonObject
						resultJsonObject = new JSONObject(entityStringBuilder.toString());
						jsapi_ticket = resultJsonObject.get("ticket") + "";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}
		return jsapi_ticket;

	}
	public static String getticketImage(String ticket) throws Exception {
		String jsapi_ticket = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpPost = new HttpGet("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);

 
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				 HttpEntity entity = response.getEntity();

			      if (response.getStatusLine().getStatusCode() >= 400) {
			        throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode()
			          );
			      }
			      if (entity != null) {
			        InputStream input = entity.getContent();
			        OutputStream output = new FileOutputStream(new File("D:\\2.jpg"));
			        IOUtils.copy(input, output);
			        output.flush();
			      }

			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return jsapi_ticket;

	}

}
