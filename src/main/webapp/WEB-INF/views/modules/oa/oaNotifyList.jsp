<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>问答管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        document.execCommand('Refresh')
		$(document).ready(function() {
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">


	<li class="active"><a href="${ctx}/oa/oaNotify/${oaNotify.self?'self':''}"><c:choose><c:when test="${!oaNotify.self}">问题</c:when><c:otherwise>通知</c:otherwise></c:choose>列表</a></li>
	<c:if test="${!oaNotify.self}"><shiro:hasPermission name="oa:oaNotify:edit"><li><a href="${ctx}/oa/oaNotify/form">问题添加</a></li></shiro:hasPermission></c:if>
</ul>
	<form:form id="searchForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/${oaNotify.self?'self':''}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
		<%--	<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>--%>
			<c:if test="${!requestScope.oaNotify.self}"><li><label>状态：</label>
				<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li></c:if>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>状态</th>
				<th>查阅状态</th>
				<th>更新时间</th>
				<c:choose>
					<c:when test="">
						<shiro:hasPermission name="oa:oaNotify:edit"><th>操作</th></shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<th>操作</th>
					</c:otherwise>
				</c:choose>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaNotify">
			<tr>
				<td>
					${fns:abbr(oaNotify.title,50)}
				</td>
				<td>
					${fns:getDictLabel(oaNotify.status, 'oa_notify_status', '')}
				</td>
				<td>
					<c:if test="${requestScope.oaNotify.self}">
						${fns:getDictLabel(oaNotify.readFlag, 'oa_notify_read', '')}
					</c:if>
					<c:if test="${!requestScope.oaNotify.self}">
						${oaNotify.readNum} / ${oaNotify.readNum + oaNotify.unReadNum}
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${oaNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<c:choose>
					<c:when test="${!requestScope.oaNotify.self}"><shiro:hasPermission name="oa:oaNotify:edit"><td>
						<div class="two">
							<c:choose>
								<c:when test="${oaNotify.status ne '1'}"><a href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}">修改</a></c:when>
								<c:otherwise><a href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}">查看</a></c:otherwise>
							</c:choose>
						</div>
						<div class="one"><a href="${ctx}/oa/oaNotify/delete?id=${oaNotify.id}" onclick="return confirmx('确认要删除该通知吗？', this.href)">删除</a></div>
					</td></shiro:hasPermission></c:when>
					<c:otherwise>
						<td>
						<div class="two" style="margin-left: 30%;margin-right: 30%;">
							<a href="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}&oaReadFlag=${oaNotify.readFlag}">查看</a>
						</div>
					</td></c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>