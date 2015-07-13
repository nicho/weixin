/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gamewin.weixin.entity.ManageQRcode;
import com.gamewin.weixin.model.QRcodeByHistory;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author jiulan
 */
@MyBatisRepository
public interface ManageQRcodeMybatisDao {

	List<ManageQRcode> getUserManageQRcodeByTaskId(@Param("taskId") Long taskId);
	
	List<ManageQRcode> getMyUserManageQRcodeByTaskId(@Param("taskId") Long taskId,@Param("userId") Long userId );

	Integer getUserManageQRcodeByTaskIdAndQrType(@Param("taskId") Long taskId,@Param("qrType") String qrType,@Param("userId") Long userId );
	
	List<QRcodeByHistory> getUserQRcodeByHistoryWeixin(@Param("taskId") Long taskId,@Param("userId") Long userId );
	
	List<QRcodeByHistory> getUserQRcodeByHistoryUrl(@Param("qrcodeId") Long qrcodeId,@Param("userId") Long userId );
	
	List<QRcodeByHistory> getUserQRcodeByHistoryWeixinUp(@Param("taskId") Long taskId,@Param("userId") Long userId );
	
	List<QRcodeByHistory> getUserQRcodeByHistoryUrlUp(@Param("qrcodeId") Long qrcodeId,@Param("userId") Long userId );
	
}
