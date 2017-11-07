<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" href="../js/easyui/themes/icon.css"/>
<link rel="stylesheet" href="../js/easyui/themes/color.css"/>
<script src="../js/easyui/jquery.min.js"></script> 
<script src="../js/easyui/jquery.easyui.min.js"></script>
<script src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<title>Insert title here</title>
</head>
<body>
	<table id="loginHistoryList"></table>
</body>
<script type="text/javascript">
$('#loginHistoryList').datagrid({    
    url:'../user/getMyLoginHistoryList.do', 
    //toolbar:'#toolbar',
    pageNumber:1,
    pageSize:10,
    fit:true,
    //pageList:[10,20,50,100],
    //pagination:true,
    loadMsg:'数据加载中...',
    rownumbers:true,
    columns:[[    
       {field:'id',title:'ID',checkbox:true},    
       {field:'userName',title:'用户名',width:'20%'},
       {field:'loginTime',title:'登陆时间',width:'15%'},
       {field:'ipAddr',title:'IP',width:'20%'},
       {field:'ipRealAddr',title:'ip位置',width:'20%'},
       {field:'status',title:'状态',width:'10%',formatter:function(value,row,index){
       	return value == 1 ? "失败":"成功";
       }},
       {field:'node',title:'备注',width:'10%'}
       
    ]]
});  

</script>
</html>