<%--
  Created by IntelliJ IDEA.
  User: anyang
  Date: 2015/8/11 0030
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/pageContext.jsp"%>
<%@include file="../../common/common.jsp"%>
<html>
<head>
    <title></title>
</head>
<body >

<div id="inner_container" class="easyui-layout" style="width:100%;height:100%;">
    <!-- 查询form -->
    <div data-options="region:'north',title:'高级查询'" style="width:100%;height:120px;background: #F4F4F4;">
        <form id="search_form" action="" class="form-inline">
            <br/>
            <table>
            <tr>
                <td align="right" width="5%" style="font-size: 12px;">姓名：</td>
                <td width="25%"><input id="displayNameSearch" name="displayName" style="width: 200px;"/></td>
                <td  align="right" width="5%" style="font-size: 12px;">工号：</td>
                <td width="25%"><input id="adminCodes" name="adminCode" style="width: 200px;"/></td>
                <td  align="right" width="7%" style="font-size: 12px;">邮箱：</td>
                <td width="25%"><input id="email" name="email" style="width: 200px;"/></td>
            </tr>
            <tr>
                <td align="right" width="5%" style="font-size: 12px;">手机号：</td>
                <td width="25%"><input id="telPhone" name="telPhone" style="width: 200px;"/></td>
                <%--<span>职位:</span><input id="positionId" name="positionId" value="" size=10>--%>
                <td align="right" width="5%" style="font-size: 12px;">状态：</td>
                <td width="25%"><input id="adminStateValue" name="adminState" style="width: 200px;"/></td>
                <td align="right" width="7%" style="font-size: 12px;">组织机构：</td>
                <td width="25%"><input id="searchDepartment" style="width: 145px;"/>
                    <a href="javascript:void(0);" class="easyui-linkbutton" style="width: 45px;" onclick="selectSearchDepartment();">选择</a>
                </td>
                <%--<input type="hidden" name="isAgent" value="0"/>--%>
                <input type="hidden" id="searchDepartmentCode" name="departmentCode" value="" size=10/>
            </tr>

            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                <a style="padding-left: 5px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0);" onclick="doSearch();">查找</a>
                <a style="padding-left: 5px;" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" href="javascript:void(0);" onclick="doClear();">清空</a>
                </td>
            </tr>
            </table>
        </form>
    </div>

    <!-- 按钮 -->
    <div id="toolbar">
        <auth:PermisTag code="01010101">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="toAddAdmin();">新增</a>
        </auth:PermisTag>
    </div>

    <!-- 列表 -->
    <div id="main_admin" toolbar="#toolbar"  data-options="region:'center'">
        <table id="admin_adminsList_list" style="height: 100%"></table>
    </div>

</div>
<!-- 页面滚动条 -->
<script type="text/javascript" src="${path}/js/scrollPage.js"></script>
<script>
    window.onresize = function(){
        //debugger;
        $("#main").parent().width($("body").width() -310);
        $("#main").width($("body").width() -310).height($("body").height() -60);
        $("#main_admin").parent().width($("body").width() -310);
        var parentWidth = $("body").width() -310;
        $("#main_admin").width(parentWidth);
        $("#main_admin").height($("body").height() - 70);
        resizeFunc("inner_container","admin_adminsList_list",parentWidth);
    }
$(document).ready(function(){
    $("#admin_adminsList_list").datagrid({
        title: '员工列表',
        url: '${path}/system/admininfo/list',
        idField: 'adminId',
        fitColumns:false,
        resize:false,
        striped:true,
        pagination : true,
        pageSize : 10,
        height : $("#inner_container").height() - 125,
        singleSelect : true,
        rownumbers : true,
        toolbar : '#toolbar',
        columns:[[
            {field:'adminId',checkbox:true},
            {field:'adminCode',title:'工号'},
            {field:'displayName',title:'姓名'},
            {field:'adminIDCode',title:'身份证'},
            {field:'email',title:'邮箱'},
            {field:'telPhone',title:'手机号'},
            {field:'departmentName',title:'组织机构'},
            {field:'roleName',title:'角色'},
            {field:'isAgent',title:'是否为居间人',align:'center'},
            {field:'adminState',title:'状态',align:'center'},
            {field:'action',title:'操作',align:'center',
                formatter:function(value,row,index){
                    var value = '<a href="#" class="easyui-linkbutton" onclick="showAdmin('+index+')">详情</a> &nbsp;';
                    value += '<auth:PermisTag code="01010102"><a class="easyui-linkbutton" onclick="editAdmin(' + index + ')">修改</a></auth:PermisTag> &nbsp;';
                    value += '<auth:PermisTag code="01010103"><a class="easyui-linkbutton" onclick="showFunctionArea(' + index + ')">职能区域</a></auth:PermisTag>';
                    return value;
                }
            }
        ]],
        onBeforeLoad: function (value, rec) {
            var adminState = $(this).datagrid("getColumnOption", "adminState");
            if (adminState) {
                adminState.formatter = function (value, rowData, rowIndex) {
                    if (value == "3") {
                        return "无效";
                    } else if (value == "1") {
                        return "有效";
                    } else if (value == "2") {
                        return "离职";
                    }
                }
            }
            var isAgent = $(this).datagrid("getColumnOption", "isAgent");
            if (isAgent) {
                isAgent.formatter = function (value, rowData, rowIndex) {
                    if (value == 1) {
                        return "是";
                    } else if (value == 2) {
                        return "否";
                    }
                }
            }
        }
    });

    //状态
    $("#search_form #adminStateValue").combobox({
        valueField: 'value',
        textField: 'label',
        data: [{
            label: '有效',
            value: '1'
        },{
            label: '无效',
            value: '3'
        },{
            label: '离职',
            value: '2'
        }]
    });

    //点击combobox显示下拉
    $(".combo").click(function(){
        $(this).prev().combobox("showPanel");
    });
});

//职能区域
function showFunctionArea(index) {
    $("#admin_adminsList_list").datagrid("selectRow",index);
    var selRow = $("#admin_adminsList_list").datagrid("getSelected");
    if (selRow) {
        $("#admin_adminsList_list").after("<div id='addFunctionArea' style=' padding:10px; '></div>");
        $("#addFunctionArea").dialog({
            resizable: false,
            title: '员工职能区域管理',
            href: '${path}/system/functionarea/areasList?adminId='+selRow.adminId+'&adminName='+selRow.displayName,
            width: 500,
            modal: true,
            height: 600,
            top: 200,
            left: 700,
            buttons: [
                {
                    text: '提交',
                    iconCls: 'icon-ok',
                    handler: function () {
                        try {
                            var rows = $("#addFunctionArea").contents().find("#showTree").treegrid('getSelections');
                            if(selRow.adminId == 1) {
                                $.messager.confirm("系统提示", "当前是系统管理员，确认修改？", function (data) {
                                    if (data) {
                                        var functionIdStr = "";
                                            $.each(rows,function(key,value){
                                            functionIdStr = functionIdStr + value.id;
                                            if(key != rows.length - 1){
                                                functionIdStr = functionIdStr + '-';
                                            }
                                        });
                                        $.post("${path}/system/functionarea/saveFunctionArea?departmentCodes="+functionIdStr+'&adminId='+selRow.adminId,
                                                function(data){
                                                    if(data == "success"){
                                                        $.messager.alert("系统提示", "操作成功!", "info");
                                                        $("#addFunctionArea").dialog('close');
                                                    }
                                                    if(data == "error"){
                                                        $.messager.alert("系统提示", "操作失败!", "info");
                                                        return;
                                                    }
                                                    if(data == "empty"){
                                                        $.messager.alert("系统提示", "请选择要匹配的权限!", "info");
                                                        return;
                                                    }
                                                });
                                    }
                                });
                            }else {
                                var functionIdStr = "";
                                $.each(rows,function(key,value){
                                    functionIdStr = functionIdStr + value.id;
                                    if(key != rows.length - 1){
                                        functionIdStr = functionIdStr + '-';
                                    }
                                });
                                $.post("${path}/system/functionarea/saveFunctionArea?departmentCodes="+functionIdStr+'&adminId='+selRow.adminId,
                                        function(data){
                                            if(data == "success"){
                                                $.messager.alert("系统提示", "操作成功!", "info");
                                                $("#addFunctionArea").dialog('close');
                                            }
                                            if(data == "error"){
                                                $.messager.alert("系统提示", "操作失败!", "info");
                                                return;
                                            }
                                            if(data == "empty"){
                                                $.messager.alert("系统提示", "请选择要匹配的权限!", "info");
                                                return;
                                            }
                                        });
                            }

                        } catch (e) {
                            alert("操作失败！");
                            return;
                        }
                    }
                },
                {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $("#addFunctionArea").dialog('close');
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    } else {
        $.messager.alert("系统提示", "请选择要授权的角色!", "info");
    }
}

//修改
function editAdmin(index) {
    $("#admin_adminsList_list").datagrid("selectRow",index);
    var selRow = $("#admin_adminsList_list").datagrid("getSelected");
    if (selRow) {
        $("#admin_adminsList_list").after("<div id='toAddAdmin' style=' padding:10px; '></div>");
        $("#toAddAdmin").dialog({
            resizable: false,
            title: '修改员工信息',
            href: '${path}/system/admininfo/editAdmin?adminId=' + selRow.adminId,
            modal: true,
            width: 900,
            height: 880,
            top: 50,
            left: 500,
            buttons: [
                {
                    text: '提交',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $.messager.confirm('确认信息','是否确认修改员工信息？',function(r){
                            if(r){
                                try {
                                    $("#toAddAdmin").contents().find("#admin_addAdmin_form").submit();
                                } catch (e) {
                                    alert(e);
                                }
                            }
                        });
                    }
                },
                {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $("#toAddAdmin").dialog('close');
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    } else {
        $.messager.alert("系统提示", "请选择要修改的员工!", "info");
    }
}

//添加
function toAddAdmin() {
    $("#admin_adminsList_list").after("<div id='toAddAdmin' style=' padding:10px; '></div>");
    $("#toAddAdmin").dialog({
        resizable: false,
        title: '新增员工信息',
        href: '${path}/system/admininfo/addAdmin',
        modal: true,
        width: 900,
        height: 850,
        top: 50,
        left: 500,
        buttons: [
            {
                text: '提交',
                iconCls: 'icon-ok',
                handler: function () {
                    try {
                        $("#toAddAdmin").contents().find("#admin_addAdmin_form").submit();
                    } catch (e) {
                        $.messager.alert("系统提示", e, "info");
                    }
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $("#toAddAdmin").dialog('close');
                }
            }
        ],
        onClose: function () {
            $(this).dialog('destroy');
        }
    });
}

//查看
function showAdmin(index) {
    $("#admin_adminsList_list").datagrid("selectRow",index);
    var selRow = $("#admin_adminsList_list").datagrid("getSelected");
    if (selRow) {
        $("#admin_adminsList_list").after("<div id='showAdmin' style=' padding:10px; '></div>");
        $("#showAdmin").dialog({
            resizable: false,
            title: '员工信息',
            href: '${path}/system/admininfo/showAdmin?adminId='+selRow.adminId,
            width: 700,
            height: 650,
            modal: true,
            top: 100,
            left: 400,
            buttons: [
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $("#showAdmin").dialog('close');
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    } else {
        $.messager.alert("系统提示", "请选择要查看的员工!", "info");
    }
}

//用于选择组织机构节点
function selectSearchDepartment() {
    $("#admin_adminsList_list").after("<div id='department_departmentsTree' style=' padding:10px; '></div>");
    $("#department_departmentsTree").dialog({
        resizable: false,
        title: '组织结构选择',
        href: '${path}/system/department/showDepartmentTree',
        width: 390,
        height: 500,
        modal: true,
        top: 150,
        left: 1000,
        buttons: [
            {
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#searchDepartment").val('');
                    $("#searchDepartmentCode").val('');
                    var editingId = $("#department_departmentsTree").contents().find("#editingId").text();
                    var editingName = $("#department_departmentsTree").contents().find("#editingName").text();
                    //alert(editingId + "==" + editingName);
                    $("#searchDepartment").attr("value",editingName);
                    $("#searchDepartmentCode").attr("value",editingId);
                    $("#department_departmentsTree").dialog('close');
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $("#department_departmentsTree").dialog('close');
                }
            }
        ],
        onClose: function () {
            $(this).dialog('destroy');
        }
    });
}

<%--PS:error[400 (Bad Request)] springMVC 在http请求时对bean中int类型为空的值绑定失败。解决方案：改为Integer或传递时附默认值0--%>
function doSearch() {
    /*var vitemKind = $("#search_form #itemKind").combobox('getValue');
    var vitemState = $("#search_form #itemState").combobox('getValue');
    if(vitemKind == '') {
        $("#search_form #itemKind").combobox('setValue','0')
    }
    if(vitemState == '') {
        $("#search_form #itemState").combobox('setValue','0');
    }*/


    /*var positionId = $("#search_form #positionId").combobox('getValue');
    var adminStateValue = $("#search_form #adminStateValue").combobox('getValue');
    if(positionId == ''){
        $("#search_form #positionId").combobox('setValue','0');
    }
    if(adminStateValue == ''){
        $("#search_form #adminStateValue").combobox('setValue','0');
    }
    $("#admin_adminsList_list").datagrid("load", changFormData2Obj('search_form'));
    if(positionId == 0) {
        $("#search_form #positionId").combobox('clear');
    }
    if(adminStateValue == 0) {
        $("#search_form #adminStateValue").combobox('clear');
    }*/

    var adminInfo={};
    if($("#displayNameSearch").val() != ""){
        adminInfo.displayName = $("#displayNameSearch").val();
    }
    if($("#adminCodes").val() != ""){
        adminInfo.adminCode = $("#adminCodes").val();
    }
    if($("#email").val() != ""){
        adminInfo.email = $("#email").val();
    }
    if($("#telPhone").val() != ""){
        adminInfo.telPhone = $("#telPhone").val();
    }
    /*if($("#positionId").combobox("getValue") != ""){
     adminInfo.positionId = $("#positionId").combobox("getValue");
     }*/
    if($("#adminStateValue").combobox("getValue") != ""){
        adminInfo.adminState = $("#adminStateValue").combobox("getValue");
    }
    if($("#searchDepartment").val() != ""){
        adminInfo.departmentCode = $("#searchDepartmentCode").val();
    }else {
        $("#searchDepartmentCode").val("");
    }
    $("#admin_adminsList_list").datagrid("load", adminInfo);
}

function doClear() {
    $("#search_form")[0].reset();
    $("#searchDepartment").val('');
    $("#searchDepartmentCode").val('');
    $("#admin_adminsList_list").datagrid("load", {});
}

</script>
</body>
