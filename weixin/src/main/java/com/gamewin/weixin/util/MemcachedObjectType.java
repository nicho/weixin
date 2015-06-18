package com.gamewin.weixin.util;

 
/**
 * 统一定义Memcached中存储的各种对象的Key前缀和超时时间.
 * 
 */
public enum MemcachedObjectType {
	USER("user:", 60 * 60 * 1),
	WEIXIN("weixin:", 7000),//缓存(秒) 用于微信
	PCEMAIL("pcemail:", 400);  //缓存(秒) 用于邮箱
	private String prefix;
	private int expiredTime;

	MemcachedObjectType(String prefix, int expiredTime) {
		this.prefix = prefix;
		this.expiredTime = expiredTime;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiredTime() {
		return expiredTime;
	}

}
