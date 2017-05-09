<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>问答管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        document.execCommand('Refresh');
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function  lookView(obj) {
		    var id=$(obj).next("#oaId").val();
            parent.layer.open({
                title: "解决方法",
                type: 2,
                area: ['500px', '550px'],
 /*            offset:['100','600'],*/
                fixed: false, //不固定
                maxmin: true,
                content: "${ctx}/oa/oaNotify/record/get?id=" + id
            });
        }
        function f1() {
		    var oaRecordSolution=$("[name='RecordSolution']").val();
			$("[name='oaRecordSolution']").val(oaRecordSolution);
			$("[name='oaRecordUserId']").val($("[name='oaRecordUserIds']").val());
            $("[name='oaRecordId']").val($("[name='oaRecordIds']").val());
            $("[name='id']").val($("[name='ids']").val());
            $("#f1").submit();
        }
	</script>
</head>
<body onload="location.replace()">
	<ul class="nav nav-tabs">
		<c:if test="${oaNotify.status ne '1'}">
		<li><a href="${ctx}/oa/oaNotify/">问题列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}"><c:if test="${oaNotify.status eq '1' }">查看通知</c:if><shiro:hasPermission name="oa:oaNotify:edit">${oaNotify.status eq '1' ? '' : not empty oaNotify.id ? '问题修改' : '问题添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="uName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">QQ号码：</label>
			<div class="controls">
				<form:input path="qqNum" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<c:if test="${oaNotify.status ne '1'}">
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> 发布后不能进行操作。</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">接受人：</label>
				<div class="controls">
					<sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
									title="专家" url="/sys/office/treeData?type=3" cssClass="input-xxlarge1 required" notAllowSelectParent="true" checked="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${oaNotify.status eq '1'&& oaNotify.userId eq Users}">
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">接受人：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>接受人</th>
								<th>接受部门</th>
								<th>阅读状态</th>
								<th>阅读时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
							<tr>
								<td>
									${oaNotifyRecord.user.name}
								</td>
								<td>
									${oaNotifyRecord.user.office.name}
								</td>
								<td>
									${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
								</td>
								<td>
									<fmt:formatDate value="${oaNotifyRecord.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
							    <div class="two" style="margin-left: 30%;margin-right: 30%;">
									<a class="btn" onclick="javascript:lookView(this)" href="#">查看</a>
									<input type="hidden" id="oaId" name="oaId" value="${oaNotifyRecord.id}"/>
								</div>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					已查阅：${oaNotify.readNum} &nbsp; 未查阅：${oaNotify.unReadNum} &nbsp; 总共：${oaNotify.readNum + oaNotify.unReadNum}
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>


			<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
				<c:if test="${Users eq oaNotifyRecord.user.id&& oaNotifyRecord.oaNotify.id eq oaNotify.id}">
					<c:if test="${oaNotifyRecord.readFlag eq '1'||oaNotifyRecord.readFlag eq '2'}">
						<input type="hidden" name="oaRecordUserIds" value="${oaNotifyRecord.user.id}"/>
						<input type="hidden" name="oaRecordIds" value="${oaNotifyRecord.id}"/>
						<input type="hidden" name="ids" value="${oaNotify.id}"/>
				<div class="control-group">
					<label class="control-label">解答：</label>
					<div class="controls">
						<textarea name="RecordSolution" htmlEscape="false" rows="6"  class="input-xxlarge required" <c:if test="${oaNotifyRecord.readFlag eq '2'}">readonly="readonly"</c:if>>${oaNotifyRecord.solution}</textarea>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				</c:if>
				</c:if>
			</c:forEach>

		<div class="form-actions">
					<c:if test="${oaNotify.status ne '1'&& oaNotify.status ne '2'}">
						<shiro:hasPermission name="oa:oaNotify:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					</c:if>
			<c:if test="${oaNotify.status eq '1'&& Users ne oaNotify.userId && oaNotifyReadreadFlag eq '1'}">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="f1()" value="提交回答"/>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<form:form id="f1" action="${ctx}/oa/oaNotify/updateRecord" method="post">
					<input type="hidden" name="oaRecordSolution"/>
					<input type="hidden" name="oaRecordUserId" />
					<input type="hidden" name="oaRecordId"/>
					<input type="hidden" name="id"/>
	</form:form>
</body>
</html>