package com.gamewin.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
public class MobileHttpClient {

	public static String getAccessToken() throws Exception {

		String access_token = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + MobileContants.appID
					+ "&secret=" + MobileContants.appsecret);

			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			JSONObject resultJsonObject = null;
			try {

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
	 * 创建临时二维码ticket
	 * 
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	public static String getJsapi_ticket_WeixinLs(String access_token, Long manageQRcodeId) throws Exception {
		String jsapi_ticket = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("action_name", "QR_SCENE");
			jsonObj.put("expire_seconds", 604800);
			jsonObj.put("action_info", new JSONObject().put("scene", new JSONObject().put("scene_id", manageQRcodeId)));

			StringEntity entity = new StringEntity(jsonObj.toString(), "UTF-8");

			httpPost.setEntity(entity);

			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			JSONObject resultJsonObject = null;
			try {

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

	/**
	 * 创建永久二维码ticket
	 * 
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	public static String getJsapi_ticket_WeixinGd(String access_token, Long manageQRcodeId) throws Exception {
		String jsapi_ticket = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("action_name", "QR_LIMIT_SCENE");
			jsonObj.put("action_info", new JSONObject().put("scene", new JSONObject().put("scene_id", manageQRcodeId)));

			StringEntity entity = new StringEntity(jsonObj.toString(), "UTF-8");

			httpPost.setEntity(entity);

			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			JSONObject resultJsonObject = null;
			try {

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

	public static String getticketImage(String ticket, String url) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpPost = new HttpGet("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);

			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();

				if (response.getStatusLine().getStatusCode() >= 400) {
					throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode());
				}

				if (entity != null) {
					InputStream input = entity.getContent();
					OutputStream output = new FileOutputStream(new File(url));
					IOUtils.copy(input, output);
					output.flush();

					output.close();
					input.close();
				}

			} finally {
				response.close();

			}
		} finally {
			httpclient.close();
		}
		return url;

	}
	 public static Map<String,String> sendWinXinMessage(String access_token,String touser,String msgContents,String title,String url) throws Exception 
	 {
		  Map<String,String> map=new HashMap<String, String>();
		  CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {  
	            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token);
	            
	            JSONObject jsonObj = new JSONObject();
	            System.out.println("给用户发送微信消息-----------"+touser);
	            jsonObj.put("touser", touser); 
	            jsonObj.put("msgtype", "news"); 
	      
	            JSONObject body=new JSONObject();
	            body.put("title", title);
	            body.put("description", msgContents);
	            body.put("url", url); 
	             
	            jsonObj.put("news",new JSONObject().put("articles", new JSONArray().put(body)));
	            System.out.println(jsonObj.toString());
	            StringEntity entity = new StringEntity(jsonObj.toString(),"UTF-8");
	            
	            httpPost.setEntity(entity); 
	             
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);

	            try {
	                System.out.println(response2.getStatusLine());
	                HttpEntity entity2 = response2.getEntity(); 
	                if (entity2 != null) {
	                    try {
	                        BufferedReader bufferedReader = new BufferedReader(
	                        new InputStreamReader(entity2.getContent(),"UTF-8"), 8 * 1024);
	                        StringBuilder entityStringBuilder = new StringBuilder();
	                        String line = null;
	                        while ((line = bufferedReader.readLine()) != null) {
	                            entityStringBuilder.append(line);
	                        }
	                        // 利用从HttpEntity中得到的String生成JsonObject
	                        JSONObject resultJsonObject = new JSONObject(entityStringBuilder.toString());  
	                         
	                        map.put("errcode", resultJsonObject.get("errcode")+"");
	                        map.put("errmsg", resultJsonObject.get("errmsg")+"");
	                       
	                        String invaliduser="";
	                        try {
	                        	invaliduser=resultJsonObject.get("invaliduser")+"";
							} catch (Exception e) { 
							}
	                        map.put("invaliduser",invaliduser);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
	        return map;
	 }
	 /**
	  * 获取用户基本信息
	  * @param access_token
	  * @param openId
	  * @return
	  * @throws Exception
	  */
	 public static Map<String,String> getWinXinUserInfo(String access_token,String openId) throws Exception 
	 {
		  Map<String,String> map=new HashMap<String, String>();
		  CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {  
	            HttpGet httpget = new HttpGet("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openId+"&lang=zh_CN");
	            
	           
	             
	            CloseableHttpResponse response2 = httpclient.execute(httpget);

	            try {
	                System.out.println(response2.getStatusLine());
	                HttpEntity entity2 = response2.getEntity(); 
	                if (entity2 != null) {
	                    try {
	                        BufferedReader bufferedReader = new BufferedReader(
	                        new InputStreamReader(entity2.getContent(),"UTF-8"), 8 * 1024);
	                        StringBuilder entityStringBuilder = new StringBuilder();
	                        String line = null;
	                        while ((line = bufferedReader.readLine()) != null) {
	                            entityStringBuilder.append(line);
	                        }
	                        // 利用从HttpEntity中得到的String生成JsonObject
	                      //  JSONObject resultJsonObject = new JSONObject(entityStringBuilder.toString());  
	                         
	                        System.out.println(entityStringBuilder.toString()); 
	                        
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
	        return map;
	 }
	
	 
	 
	 /**
	  * 通过code换取UserOpenId
	  * @param code
	  * @return
	  * @throws Exception
	  */
	 public static String getUserOpenIdByCode(String code) throws Exception {

			String openid = "";
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+MobileContants.appID+"&secret="+MobileContants.appsecret+"&code="+code+"&grant_type=authorization_code");

				CloseableHttpResponse response1 = httpclient.execute(httpGet);
				JSONObject resultJsonObject = null;
				try {

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
							openid = resultJsonObject.get("openid") + "";
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

			return openid;

		}
	 
	 
	 public static void main(String[] args) {
		 try {
			String access_token=getAccessToken();
			//getWinXinUserInfo(access_token, "or7XwwEpjASO9A5_skvnDf729nJ4");
			sendWinXinMessage(access_token, "or7XwwEpjASO9A5_skvnDf729nJ4", "测试内容", "系统通知", "https://open.weixin.qq.com/connect/oauth2/authorize");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
