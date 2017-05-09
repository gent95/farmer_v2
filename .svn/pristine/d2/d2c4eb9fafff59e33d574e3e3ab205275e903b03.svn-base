<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>作业类型管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {




            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>

    <script src="/static/jctl/img/jcImgUpload.js"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/jobtype/jobType/">作业类型列表</a></li>
    <li class="active"><a href="${ctx}/jobtype/jobType/form?id=${jobType.id}">作业类型<shiro:hasPermission
            name="jobtype:jobType:edit">${not empty jobType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="jobtype:jobType:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="jobType" action="${ctx}/jobtype/jobType/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>

        </div>
    </div>
    <div class="control-group">


        <label class="control-label">图标：</label>
        <div class="controls">
            <form:hidden id="nameImage" path="icon" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
        </div>
    </div>
    <div class="form-actions">
            <%--<shiro:hasPermission name="jobtype:jobType:edit">--%>
        <input id="btnSubmit" class="btn btn-primary" type="submit"
               value="保 存"/>&nbsp;
            <%--</shiro:hasPermission>--%>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>