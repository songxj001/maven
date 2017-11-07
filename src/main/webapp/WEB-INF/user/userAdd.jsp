<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加用户</title>
	<script src="../js/easyui/jquery.min.js"></script> 
	<script src="../js/easyui/jquery.easyui.min.js"></script>
	<script src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet" href="../js/easyui/themes/icon.css"/>
	<link rel="stylesheet" href="../js/easyui/themes/default/easyui.css"/>
</head>
<body>
	<form action="">
		<div>   
	        <label for="name">账号:</label>   
	        <input class="easyui-validatebox" type="text" name="loginNumber" data-options="required:true" />   
	    </div>   
	    <div>   
	        <label for="email">Email:</label>   
	        <input class="easyui-validatebox" type="text" name="email" data-options="validType:'email'" />   
	    </div>
	</form>
</body>
</html>