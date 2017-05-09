/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.datection.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.datection.entity.DatectionDate;
import com.jctl.cloud.manager.datection.dao.DatectionDateDao;

/**
 * 设备数据Service
 * @author mjt
 * @version 2017-05-04
 */
@Service
@Transactional(readOnly = true)
public class DatectionDateService extends CrudService<DatectionDateDao, DatectionDate> {

	public DatectionDate get(String id) {
		return super.get(id);
	}
	
	public List<DatectionDate> findList(DatectionDate datectionDate) {
		return super.findList(datectionDate);
	}
	
	public Page<DatectionDate> findPage(Page<DatectionDate> page, DatectionDate datectionDate) {
		return super.findPage(page, datectionDate);
	}
	
	@Transactional(readOnly = false)
	public void save(DatectionDate datectionDate) {
		super.save(datectionDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(DatectionDate datectionDate) {
		super.delete(datectionDate);
	}
	
}