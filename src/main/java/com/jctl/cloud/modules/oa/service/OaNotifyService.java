/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.modules.oa.service;

import java.util.Date;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.modules.oa.dao.OaNotifyRecordDao;
import com.jctl.cloud.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.modules.oa.entity.OaNotify;
import com.jctl.cloud.modules.oa.entity.OaNotifyRecord;
import com.jctl.cloud.modules.oa.dao.OaNotifyDao;

/**
 * 通知通告Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyService extends CrudService<OaNotifyDao, OaNotify> {

	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;

	public OaNotify get(String id) {
		OaNotify entity = dao.get(id);
		return entity;
	}
	public OaNotifyRecord getRecord(String id){
		OaNotifyRecord en=oaNotifyRecordDao.get(id);
		return en;
	}
	/**
	 * 获取通知发送记录
	 * @param oaNotify
	 * @return
	 */
	public OaNotify getRecordList(OaNotify oaNotify,User user) {
		OaNotifyRecord oaNotifyRecord=new OaNotifyRecord(oaNotify);
		oaNotifyRecord.setUser(user);
		oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findList(oaNotifyRecord));
		return oaNotify;
	}
	
	public Page<OaNotify> find(Page<OaNotify> page, OaNotify oaNotify) {
		oaNotify.setPage(page);
		page.setList(dao.findList(oaNotify));
		return page;
	}
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify) {
		return dao.findCount(oaNotify);
	}
	/**
	 * 获取通知数目
	 * @param
	 * @return
	 */
	public Long findReturnCount(OaNotify oaNotify) {
		return dao.findReturnCount(oaNotify);
	}
	@Transactional(readOnly = false)
	public void save(OaNotify oaNotify) {
		super.save(oaNotify);
		
		// 更新发送接受人记录
		oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
		if (oaNotify.getOaNotifyRecordList().size() > 0){
			oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
		}
    }
	
	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(OaNotify oaNotify) {
		OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
		oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
		oaNotifyRecord.setReadDate(new Date());
		oaNotifyRecord.setReadFlag("1");
		oaNotifyRecordDao.update(oaNotifyRecord);
	}

	/**
	 * 问题解答
	 */
	@Transactional(readOnly = false)
	public void updateRecordSolution(OaNotifyRecord oaNotifyRecord){
		oaNotifyRecordDao.update(oaNotifyRecord);
	}
}