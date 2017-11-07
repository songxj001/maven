<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="../js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" href="../js/easyui/themes/icon.css">
	<link rel="stylesheet" href="../js/easyui/themes/color.css">
	<script src="../js/easyui/jquery.min.js"></script> 
	<script src="../js/easyui/jquery.easyui.min.js"></script>
	<script src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet" type="text/css" href="../js/uploadify/uploadify.css">
	<script src="../js/uploadify/jquery.uploadify.js"></script>
	<link rel="stylesheet" type="text/css" href="../js/webuploader-dist-v0.1.3/webuploader.css">
	<script type="text/javascript" src="../js/webuploader-dist-v0.1.3/webuploader.js"></script>
</head>
<body>
	<div id="toolbar">
		<div>
			<a id="createFolderBut" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新建文件夹</a>
			<a id="uploadFileBut" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">上传</a>
		</div>
		当前位置：<span id="link"><a id="0" href="javascript:loadFileList(0);">/</a></span>
	</div>
	<table id="fileList"></table>
	<!-- 文件上传 -->
	<div id="fileDialog" class="easyui-dialog" data-options="title:'上传文件',iconCls:'icon-add',resizable:true,modal:true,closed:true" style="width:400px;height:200px;" >
		<form action="" method="post" id="fileForm">
			<input type="hidden" name="pid" id="fileFloderId" value="0"/>
			<input type="file" id="uploadify" style="width:200px;"/>
		</form>
	</div>
	<!-- 创建修改文件夹 -->
	<div id="floderDialog" class="easyui-dialog" data-options="title:'创建文件夹',iconCls:'icon-add',resizable:true,modal:true,closed:true,
		buttons:[
			{
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
					saveFolder();
				}
			}]" style="width:400px;height:200px;" >
		<form action="" method="post" id="floderForm">
			<input type="hidden" name="pid" id="foldId" value="0"/>
			<table style="margin: 44px;">
				<tr>
					<td>名称：</td>
					<td><input type="text" class="easyui-textbox" name="fileName"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">

	function uploadFile(){
		
	}
	
	
	$('#uploadFileBut').click(function(){
		var fileFloderId = $('#fileFloderId').val();
		$("#uploadify").uploadify({
		    //插件自带  不可忽略的参数
		  	'swf': '../js/uploadify/uploadify.swf',
			//前台请求后台的url 不可忽略的参数
			'uploader': '../user/upload.do?id='+fileFloderId,
		    //给div的进度条加背景 不可忽略
		    'queueID': 'fileQueue',
		    //上传文件文件名
		    'fileObjName' : 'file',
		    //给上传按钮设置文字
		    'buttonText': '上传文件',
		    //设置文件是否自动上传
		    'auto': true,
		    //可以同时选择多个文件 默认为true  不可忽略
		    'multi': true,
		    //上传后队列是否消失
		    'removeCompleted': true,
		    //允许上传的文件后缀  
		    'fileExt': '*.jpg;*.gif;*.png,*.txt,*.doc,*.docx,*.xls,*.xlsx,*.exe,*.html,*.css,*.js,*.ppt', 
		    //
		    'cancelImg': '../js/uploadify/uploadify-cancel.png',  
		    //队列消失时间
		    'removeTimeout' : 1,
		    //上传文件的个数，项目中一共可以上传文件的个数
		    'uploadLimit':  10,
		   // 'fileTypeExts': '*.jpg;*.png',
		    //开始执行上传
		    'onUploadStart':function(files){
		    	$.messager.progress({ title:'提示',msg:'上传中...' });
		    },
		    //上传失败
		    'onUploadError':function(){
		    	$.messager.progress('close');
		    	$.messager.alert('提示','上传失败');
		    },
			//成功回调函数 file：文件对象。data后台输出数据
		    'onUploadSuccess':function(file,data,response){
		    	$.messager.progress('close');
		    	//$.messager.alert('提示','上传成功','info');
		    	//$('#fileDialog').dialog('close');
		    	$('#fileList').datagrid('reload');
		    }
		});
		
		$('#fileDialog').dialog({
			title:'上传文件',
			iconCls:'icon-add',
			closed: false
		})
	})
	
	function saveFolder(){
		$('#floderForm').form('submit',{
			url:'../user/saveFolder.do',
			success:function(data){
				$('#floderDialog').dialog('close');
				$('#floderForm').form('reset');
				$('#fileList').datagrid('reload');
			}
		})
	}
	$('#createFolderBut').click(function(){
		$('#floderDialog').dialog({
			title:'新建文件夹',
			iconCls:'icon-add',
			closed: false,
			onClose:function(){
				$('#floderForm').form('reset');
			}
		})
	})

	function loadFileList(id){
		$('#foldId').val(id);
		$('#fileFloderId').val(id);
		$('#'+id+'').nextAll().remove();
		$('#fileList').datagrid('reload',{
    		'id':id
    	});
	}
	$('#fileList').datagrid({    
	    url:'../user/getFileList.do', 
	    toolbar:'#toolbar',
	    pageNumber:1,
	    pageSize:10,
	    fit:true,
	    pageList:[10,20,50,100],
	    pagination:true,
	    loadMsg:'数据加载中...',
	    rownumbers:true,
	    columns:[[    
	        {field:'id',title:'ID',width:'8%',checkbox:true},    
	        {field:'fileName',title:'名称',width:'30%',formatter:function(value,row,index){
	        	if(row.type == null || row.type == ''){
		        	return '<img width="20px" height="20px" src="../images/folder (1).png"/> '+value;
	        	}else if(row.type == 'jpg' || row.type == 'png' || row.type == 'gif' || row.type == 'ico'){
	        		return '<img width="20px" height="20px" src="../images/file-picture-icon.png"/> '+value;
	        	}else if(row.type == 'txt'){
	        		return '<img width="20px" height="20px" src="../images/txt_files.png"/> '+value;
	        	}else if(row.type == 'exe'){
	        		return '<img width="20px" height="20px" src="../images/font-409.png"/> '+value;
	        	}else if(row.type == 'doc' || row.type == 'docx'){
	        		return '<img width="20px" height="20px" src="../images/file-word-icon.png"/> '+value;
	        	}else if(row.type == 'xls' || row.type == 'xlsx'){
	        		return '<img width="20px" height="20px" src="../images/file-excel-icon.png"/> '+value;
	        	}else if(row.type == 'zip' || row.type == 'rar'){
	        		return '<img width="20px" height="20px" src="../images/Package.png"/> '+value;
	        	}else if(row.type == 'js'){
	        		return '<img width="20px" height="20px" src="../images/js_file.png"/> '+value;
	        	}else if(row.type == 'css'){
	        		return '<img width="20px" height="20px" src="../images/File_CSS_Stylesheet.png"/> '+value;
	        	}else if(row.type == 'java'){
	        		return '<img width="20px" height="20px" src="../images/java-icon.png"/> '+value;
	        	}else if(row.type == 'html'){
	        		return '<img width="20px" height="20px" src="../images/file_html.png"/> '+value;
	        	}else{
	        		return '<img width="20px" height="20px" src="../images/text_file.png"/> '+value;
	        	}
	        }},
	        {field:'type',title:'类型',width:'30%',formatter:function(value,row,index){
	        	if(value == null || value == ''){
		        	return "文件夹";
	        	}else{
	        		return value;
	        	}
	        }},    
	        {field:'uploadTime',title:'上传时间',width:'30%'}
	    ]],
	    onDblClickRow:function(index,row){
	    	if(row.type == null || row.type == ''){
	    	$('#foldId').val(row.id);
	    	$('#fileFloderId').val(row.id);
	    	$('#link').append('<a  style="margin-left:10px" id='+row.id+' href="javascript:loadFileList(\''+row.id+'\');">'+row.fileName+'</a>');
	    		$('#fileList').datagrid('reload',{
		    		'id':row.id
		    	});
	    	}
	    }
	});  
</script>
</html>