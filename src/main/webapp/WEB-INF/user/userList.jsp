<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>列表</title>
	<link rel="stylesheet" href="../js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" href="../js/easyui/themes/icon.css">
	<link rel="stylesheet" href="../js/easyui/themes/color.css">
	<script src="../js/easyui/jquery.min.js"></script> 
	<script src="../js/easyui/jquery.easyui.min.js"></script>
	<script src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="../js/webuploader-dist-v0.1.3/webuploader.css">
	<script type="text/javascript" src="../js/webuploader-dist-v0.1.3/webuploader.js"></script>
	
</head>
<body>
	<div class="easyui-panel" data-options="border:false,fit:true">
		<!-- 搜索面板 -->
		<div class="easyui-panel" data-options="title:'高级搜索',border:false,height:'20%'">
			<table width="100%" onkeydown="enterSearch()">
				<tr>
					<td>姓名：
						<input type="text" class="easyui-searchbox" id="searchUserNameId"/> 
				 	</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;"> 
						<a href="javascript:searchUser();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a> 
						<a class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a> 
					</td>
				</tr>
			</table>
		</div>
		<!-- 列表面板 -->
		<div class="easyui-panel" data-options="border:false,height:'80%'">
			<div id="toolbar">
				<a class="easyui-linkbutton" href="javascript:openAddDialog();" data-options="iconCls:'icon-add',plain:true">新增</a> |
				<a class="easyui-linkbutton" href="javascript:delUser();" data-options="iconCls:'icon-remove',plain:true">删除</a> |
				<a class="easyui-linkbutton" href="javascript:opendUpdateUserDialog();" data-options="iconCls:'icon-edit',plain:true">修改</a>
				<a class="easyui-linkbutton" href="javascript:void();" data-options="iconCls:'icon-lock',plain:true">批量冻结</a>
				<a class="easyui-linkbutton" href="javascript:void();" data-options="iconCls:'icon-unlock',plain:true">批量解冻</a>
				<a class="easyui-linkbutton" href="javascript:exportExcel();" data-options="iconCls:'icon-redo',plain:true">导出(Excel)</a>
				<a class="easyui-linkbutton" href="javascript:downloadExcel();" data-options="iconCls:'icon-redo',plain:true">下载导入模板(Excel)</a>
				<a class="easyui-linkbutton" href="javascript:openUploadExcel();" data-options="iconCls:'icon-undo',plain:true">上传(Excel)</a>
				<a class="easyui-linkbutton" href="javascript:downloadWord();" data-options="iconCls:'icon-undo',plain:true">下载(word)</a>
				
			</div>
			<table id="userList"></table>
		</div>
		<!-- 文件上传弹框 -->
		<div id="uploadDialog" class="easyui-dialog" 
		data-options="title:'上传文件',iconCls:'icon-save',resizable:true,modal:true,closed:true,
		buttons:[
			{
				text:'上传',
				iconCls:'icon-ok',
				handler:function(){
					uploadExcelForm();
				}
			}]" 
		style="width:400px;height:200px;" >
			<form id="excelForm" action="" method="post" enctype="multipart/form-data">
				<div style="margin: 11% 66px;">
					Excel:<input type="text" name="excelfile" class="easyui-filebox" width="200" data-options="buttonText:'请选择Excel'"/>
				</div>
			</form>
		</div>
	<!-- 表单弹框 -->
	<div id="FormDialog" class="easyui-dialog" 
		data-options="title:'添加',iconCls:'icon-save',resizable:true,modal:true,closed:true,
		buttons:[
			{
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
					saveUserForm();
				}
			}]" 
		style="width:600px;height:500px;" >
		<div style="padding:10px 60px 20px 60px">
		    <form id="userForm" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>账号:</td>
		    			<td>
		    				<input type="hidden" class="easyui-textbox" name="id" />
		    				<input class="easyui-textbox" type="text" name="loginNumber" data-options="required:true"></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>
		    				<img width="50px" height="50px" id="testimg" src=""/>
		    				<input type="hidden" id="headImg" name="headImg"/>
		    			</td>
		    			<td>
							<!--用来存放文件信息-->
							<div id="picker">选择图片</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>姓名:</td>
		    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
		    		</tr>
		    		
			    		<tr id="passwordDiv1">
			    			<td>密码:</td>
			    			<td><input class="easyui-passwordbox" id="password" type="text" name="" data-options=""></input></td>
			    		</tr>
			    		<tr id="passwordDiv2">
			    			<td>确认密码:</td>
			    			<td><input class="easyui-passwordbox" id="confirmPassword" type="text" name="password" data-options="required:true"></input></td>
			    		</tr>
		    		
		    		<tr>
		    			<td>性别:</td>
		    			<td>
		    				<label>
		    					<input type="radio" name="sex" value="1"/>男
		    				</label>
		    				<label>
		    					<input type="radio" name="sex" value="0"/>女
		    				</label>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>邮箱:</td>
		    			<td><input class="easyui-textbox" name="email" data-options="required:true,validType:'email'"></input></td>
		    		</tr>
		    		<tr>
		    			<td>配置角色：</td>
		    			<td>
		    				<select id="roleListId" name="roleId"  data-options="editable:false,required:true" style="width:200px;"></select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>部门:</td>
		    			<td>
		    				<select id="selectDeptId" class="easyui-combotree" name="deptId"  data-options="required:true"
		    					style="width:200px;">
		    				</select>
		    			</td>
		    		</tr>
		    	</table>
		    	<hr>
		    </form>
	    </div>
	</div>
	</div>

	
</body>
<script type="text/javascript">
	function downloadWord(){
		var rows = $('#userList').datagrid('getChecked');
		if(rows == null || rows.length <=0){
			$.messager.alert('提示','请选着需要下载的员工信息','info');
		}else{
			var ids = "";
			for (var i = 0; i < rows.length; i++) {
				ids += ids == "" ? rows[i].id : ","+rows[i].id;
			}
			location.href="../user/downloadWord.do?ids="+ids;
		}
	}
	function uploadExcelForm(){
		$.messager.progress({ title:'提示',msg:'上传中...' });
		$('#excelForm').form('submit',{
			url:'../user/uploadExcel.do',
			success:function(data){
				$.messager.progress('close');
				$('#excelForm').form('reset');
				$('#uploadDialog').dialog('close');
				searchUser();
			}
		})
	}
	
	function openUploadExcel(){
		$('#uploadDialog').dialog('open');
	}
	
	function downloadExcel(){
		window.location.href='../user/downloadExcel.do';
	}
	
	function exportExcel(){
		window.location.href='../user/exportExcel.do';
	}
	$('#roleListId').combobox({    
	    url:'../user/getUserRoleList.do',    
	    valueField:'id',    
	    textField:'name'   
	}); 
	//打开修改框
	function opendUpdateUserDialog(){
		var rows = $('#userList').datagrid('getChecked');
		if(rows == null || rows.length <=0){
			$.messager.alert('提示','请选着需要修改的数据','info');
		}else if(rows.length > 1){
			$.messager.alert('提示','只能选择一条数据修改','info');
		}else{
			$('#passwordDiv1').hide();
			$('#passwordDiv2').hide();
			$('#FormDialog').dialog({
				title:'编辑员工信息',
				iconCls:'icon-edit',
				closed: false,
				onClose:function(){
					$('#userForm').form('reset');
					$('#testimg').attr('src','');
				}
			});
			$.messager.progress({title:'提示', msg:'数据加载中...'});	// 显示进度条
			$.ajax({
				url:'../user/getUserInfoById.do',
				type:'post',
				data:{
					id:rows[0].id
				},
				dataType:'json',
				success:function(data){
					$('#userForm').form('load',data);
					$('#testimg').attr('src',data.headImg)
					$('#password').textbox('setValue',data.password)
					$.messager.progress('close');
				},
				error:function(){
					$.messager.progress('close');
					$.messager.alert('提示','网络异常','info');
				}
			})
			
		}
		
	}
	//删除
	function delUser(){
		var rows = $('#userList').datagrid('getChecked');
		if(rows == null || rows.length <= 0){
			$.messager.alert('提示','请选择需要删除的数据','warning')
		}else{
			$.messager.confirm('提示','你真的要删除吗？',function(r){
				if(r){
					var ids = "";
					for (var i = 0; i < rows.length; i++) {
						ids += ids == "" ? rows[i].id : ","+rows[i].id;
					}
					$.ajax({
						url:'../user/deleteUserByIds.do',
						type:'post',
						data:{
							'ids':ids
						},
						dataType:'json',
						success:function(data){
							searchUser();
						}
					})
				}
			})
		}
	}
	
	
	var uploader = WebUploader.create({
		//选完文件后自动上传
		auto: true,
	    // swf文件路径
	    swf: '../js/webuploader-dist-v0.1.3/Uploader.swf',
	    // 文件接收服务端。
	    server: '../user/fileupload.do',
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#picker',
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	 	
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
		$.messager.progress({ title:'提示',msg:'上传中...' });
	});
	
	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function(file,response) {
		$('#testimg').attr('src',response._raw);
		$('#headImg').val(response._raw);
	});
	
	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file ) {
		$.messager.alert('提示','上传出错')
	});
	
	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
		$.messager.progress('close');
	});
	
	
	//保存或修改
	function saveUserForm(){
		$.messager.progress();	// 显示进度条
		$('#userForm').form('submit',{
			url:'../user/saveUserForm.do',
			onSubmit:function(){
				var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
				}else{
					var confirmPassword = $('#confirmPassword').textbox('getValue');
					var password = $('#password').textbox('getValue');
					if(confirmPassword != password){
						$.messager.progress('close');
						$.messager.alert('提示','两次密码不一致');
						return false;
					}
				}
				return isValid;	// 返回false终止表单提交
			},
			success:function(data){
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
				$('#userForm').form('reset');
				$('#FormDialog').dialog('close');
				$('#testimg').attr('src','');
				searchUser();
			}
		})
	}
	
	var selectDept;
	$('#selectDeptId').combotree({
		url:'../user/getDeptList.do',
		onClick:function(node){
			if(node.children.length > 0){
				$('#selectDeptId').combotree('setValue',selectDept);
				$.messager.alert('警告','该项不能选中','warning');
			}
		},
		onBeforeSelect:function(node){
			selectDept = $('#selectDeptId').combotree('getValue');
		}
	});
	//打开添加框
	function openAddDialog(){
		$('#passwordDiv1').show();
		$('#passwordDiv2').show();
		$('#FormDialog').dialog({
			title:'录入员工',
			closed: false,
			onClose:function(){
				$('#userForm').form('reset');
				$('#testimg').attr('src','');
			}
		});
		
	}
	//回车搜索
	function enterSearch(){
		var event = arguments.callee.caller.arguments[0]||window.event;
		if(event.keyCode == 13){
			searchUser();
		}
	}
	//搜索
	function searchUser(){
		var searchUserName = $('#searchUserNameId').textbox('getValue');
		$('#userList').datagrid('reload',{
			'name':searchUserName
		});
	}
	$('#userList').datagrid({    
	    url:'../user/getUserList.do', 
	    toolbar:'#toolbar',
	    pageNumber:1,
	    pageSize:10,
	    fit:true,
	    pageList:[10,20,50,100],
	    pagination:true,
	    loadMsg:'数据加载中...',
	    rownumbers:true,
	    columns:[[    
	        {field:'id',title:'ID',checkbox:true,width:'5%'},    
	        {field:'headImg',title:'头像',width:'10%',formatter:function(value,row,index){
	        	return "<img width='50px' height='50px' src='"+value+"'/>";
	        }},
	        {field:'loginNumber',title:'账号',width:'10%'},    
	        {field:'name',title:'姓名',width:'10%'},
	        {field:'password',title:'密码',width:'10%'},
	        {field:'sex',title:'性别',width:'3%',formatter:function(value,row,index){
	        	return value == 1 ? "男":"女";
	        }},
	        {field:'email',title:'邮箱',width:'10%'},
	        {field:'registerTime',title:'开号时间',width:'12%'},
	        {field:'ulevel',title:'等级',width:'5%',formatter:function(value,row,index){
	        	return value <=8 ? "<b><font color='red'>vip"+value+"</font></b>":"<b><font color='gold'>svip"+(value-8)+"</font></b>";
	        }},
	        {field:'showDeptName',title:'部门',width:'10%'},
	        {field:'status',title:'状态',width:'5%',formatter:function(value,row,index){
	        	return value == 0 ? "正常":"<font color='red'>冻结</font>";
	        }},
	        {field:'abc',title:'操作',width:'5%',formatter:function(value,row,index){
	        	return row.status == 0 ? "<a href='javascript:lock(\""+row.id+"\");'>冻结</a>":"<a href='javascript:unlock(\""+row.id+"\");'>解冻</a>";
	        }}
	    ]]    
	});  
	//冻结用户
	function lock(id){
		$.ajax({
			url:'../user/lockUserById.do',
			type:'post',
			data:{
				id:id
			},
			dataType:'json',
			success:function(data){
				searchUser();
			}
		})
	}
	//解冻用户
	function unlock(id){
		$.ajax({
			url:'../user/unlockUserById.do',
			type:'post',
			data:{
				id:id
			},
			dataType:'json',
			success:function(data){
				searchUser();
			}
		})
	}
</script>
</html>