/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.modules.oa.web;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.modules.oa.entity.OaNotify;
import com.jctl.cloud.modules.oa.entity.OaNotifyRecord;
import com.jctl.cloud.modules.oa.service.OaNotifyService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通知通告Controller
 * @author ThinkGem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController extends BaseController {

	@Autowired
	private OaNotifyService oaNotifyService;
	
	@ModelAttribute
	public OaNotify get(@RequestParam(required=false) String id) {
		OaNotify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaNotifyService.get(id);
		}
		if (entity == null){
			entity = new OaNotify();
		}
		return entity;
	}


	@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaNotify.setUserId(UserUtils.getUser().getId());
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = "form")
	public String form(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			User user=null;
			oaNotify = oaNotifyService.getRecordList(oaNotify,user);
			model.addAttribute("Users",UserUtils.getUser().getId());
		}
		model.addAttribute("oaNotify", oaNotify);
		return "modules/oa/oaNotifyForm";
	}

	@RequiresPermissions("oa:oaNotify:edit")
	@RequestMapping(value = "save")
	public String save(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaNotify)){
			return form(oaNotify, model);
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(oaNotify.getId())){
			OaNotify e = oaNotifyService.get(oaNotify.getId());
			if ("1".equals(e.getStatus())){
				addMessage(redirectAttributes, "已发布，不能操作！");
				return "redirect:" + adminPath + "/oa/oaNotify/form?id="+oaNotify.getId();
			}
		}
		oaNotify.setUserId(UserUtils.getUser().getId());
		oaNotifyService.save(oaNotify);
		addMessage(redirectAttributes, "保存通知'" + oaNotify.getTitle() + "'成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	@RequiresPermissions("oa:oaNotify:edit")
	@RequestMapping(value = "delete")
	public String delete(OaNotify oaNotify, RedirectAttributes redirectAttributes) {
		oaNotifyService.delete(oaNotify);
		addMessage(redirectAttributes, "删除问题成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaNotify.setSelf(true);
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify); 
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/**
	 * 我的通知列表-数据
	 */
	@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = "selfData")
	@ResponseBody
	public Page<OaNotify> listData(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaNotify.setSelf(true);
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		return page;
	}
	
	/**
	 * 查看我的通知
	 */
	@RequestMapping(value = "view")
	public String view(OaNotify oaNotify,String oaReadFlag, Model model) {
			if (StringUtils.isNotBlank(oaNotify.getId())){
						if(oaReadFlag=="0"||"0".equals(oaReadFlag)) {
							oaNotifyService.updateReadFlag(oaNotify);
				}
				User user=UserUtils.getUser();
				oaNotify = oaNotifyService.getRecordList(oaNotify,user);
				model.addAttribute("oaNotify", oaNotify);
				model.addAttribute("Users",UserUtils.getUser().getId());
				model.addAttribute("oaNotifyReadreadFlag",oaReadFlag);
				return "modules/oa/oaNotifyForm";
			}
		return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
	}

	/**
	 * 回答问题
	 */
	@RequestMapping(value = "updateRecord")
	public String updateRecord(String id,String readDate,String oaRecordUserId,String oaRecordId,String  oaRecordSolution, Model model, RedirectAttributes redirectAttributes) {
		// 如果是修改，则状态为已发布，则不能再进行操作
		OaNotify en = oaNotifyService.get(id);

			if (StringUtils.isNotBlank(id)){

				if ("1".equals(en.getStatus())) {
					if (oaRecordSolution != null && !"".equals(oaRecordSolution)) {
						OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(en);
						oaNotifyRecord.setId(oaRecordId);
					//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//Date date = sdf.parse(readDate);
						oaNotifyRecord.setReadDate(new Date());
						oaNotifyRecord.setUser(UserUtils.get(oaRecordUserId));
						oaNotifyRecord.setReadFlag("2");
						oaNotifyRecord.setSolution(oaRecordSolution);
						oaNotifyService.updateRecordSolution(oaNotifyRecord);
					}
					addMessage(redirectAttributes, "回答成功");

				}
			}
			return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
	}
	/**
	 * 查看我的通知-数据
	 */
	@RequestMapping(value = "viewData")
	@ResponseBody
	public OaNotify viewData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotifyService.updateReadFlag(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 查看我的通知-发送记录
	 */
	@RequestMapping(value = "viewRecordData")
	@ResponseBody
	public OaNotify viewRecordData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			User user=null;
			oaNotify = oaNotifyService.getRecordList(oaNotify,user);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 获取我的通知数目
	 */
	@RequestMapping(value = "self/count")
	@ResponseBody
	public String selfCount(OaNotify oaNotify, Model model) {
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		return String.valueOf(oaNotifyService.findCount(oaNotify));
	}
	/**
	 * 获取我的通知数目
	 */
	@RequestMapping(value = "self/returnCount")
	@ResponseBody
	public String NetCount(OaNotify oaNotify, Model model) {
		oaNotify.setReadFlag("2");
		oaNotify.setUserId(UserUtils.getUser().getId());
		return String.valueOf(oaNotifyService.findReturnCount(oaNotify));
	}

	/**
	 * 查看解答
	 */
	@RequestMapping(value = "record/get")
	public String  getRecord(@RequestParam(required=false) String id, Model model){
	 OaNotifyRecord record=oaNotifyService.getRecord(id);
	model.addAttribute("oaNotifyRecord",record);
	return "modules/oa/oaNotifyRecordView";
	}
}