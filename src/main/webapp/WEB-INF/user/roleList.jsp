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
<title>角色管理</title>
</head>
<body>
	<div class="easyui-panel" data-options="fit:true">
		<div id="rolePanel" class="easyui-panel" data-options="title:'添加角色'" style="height: 20%">
			<form id="roleForm">
				<table>
					<tr>
						<td>角色名称：</td>
						<td>
							<input type="hidden" id="roleIdInput" name="id"/>
							<input type="text" name="name" class="easyui-textbox"/>
						</td>
						<td>备注：</td>
						<td>
							<input type="text" name="node" class="easyui-textbox"/>
						</td>
						<td>
							<a id="saveButton" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">添加</a>
							
							<a id="editButton" style="display: none;" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="easyui-panel" data-options="title:'角色列表'" style="height: 80%">
			<table id="roleList"></table>
		</div>
		<div id="rolePower" class="easyui-dialog" data-options="title:'角色绑定权限',resizable:true,modal:true,closed:true,
		buttons:[
			{
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
					saveRolePower();
				}
			}]" style="width: 500px;height: 600px">
			<div style="padding:10px 60px 20px 60px">
				<input type="hidden" id="roleId"/>
				<ul id="powerTreeId"></ul>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

function saveRolePower(){
	var roleId = $('#roleId').val();
	var treeNode = $('#powerTreeId').tree('getChecked');
	var ids = "";
	for (var i = 0; i < treeNode.length; i++) {
		ids += ids == "" ? treeNode[i].id : ","+treeNode[i].id;
	}
	$.ajax({
		url:'../user/saveRolePower.do',
		type:'post',
		data:{
			id:roleId,
			ids:ids
		},
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.confirm('提示','保存成功，请问您还想要绑定权限吗？',function(r){
					if(r){
						$('#powerTreeId').tree({
							url:'../user/getPowerList.do',
							lines:true,
							checkbox:true
						});
					}else{
						$('#rolePower').dialog('close');
					}
				})
			}else{
				$.messager.alert('提示','绑定失败，请稍后再试','info');
			}
		}
	})
	
}

function openBindPower(roleId){
	$('#rolePower').dialog('open');
	$('#roleId').val(roleId);
	$('#powerTreeId').tree({
		url:'../user/getPowerList.do?roleId='+roleId,
		lines:true,
		checkbox:true
	});
}

$('#editButton').click(function(){
	$('#roleForm').form('submit',{
		url:'../user/saveRole.do',
		onSubmit:function(){
			return true;
		},
		success:function(data){
			if(data){
				searchRole();
				$('#roleForm').form('reset');
				$('#roleIdInput').val('');
				$('#saveButton').show();
		    	$('#editButton').hide();
		    	$('#rolePanel').panel({
		    		title:'添加角色'
		    	})
			}else{
				$.messager.alert('提示','保存失败','info');
			}
		}
	})
})

$('#saveButton').click(function(){
	$('#roleForm').form('submit',{
		url:'../user/saveRole.do',
		onSubmit:function(){
			return true;
		},
		success:function(data){
			if(data){
				searchRole();
				$('#roleForm').form('reset');
			}else{
				$.messager.alert('提示','保存失败','info');
			}
		}
	})
})

//搜索
function searchRole(){
	$('#roleList').datagrid('reload',{
		
	});
}

$('#roleList').datagrid({    
    url:'../user/getRoleList.do', 
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
        {field:'name',title:'角色名称',width:'30%'},
        {field:'node',title:'备注',width:'30%'},
        {field:'abc',title:'绑定权限',width:'18%',formatter:function(value,row,index){
        	return '<a href=\'javascript:openBindPower("'+row.id+'")\'>操作</a>';
        }},
        {field:'def',title:'操作',width:'18%',formatter:function(value,row,index){
        	return '<a href=\'javascript:del("'+row.id+'")\'>删除</a>';
        }}
    ]],
    onDblClickRow:function(index,row){
    	$('#roleForm').form('load',row);
    	$('#saveButton').hide();
    	$('#editButton').show();
    	$('#rolePanel').panel({
    		title:'修改角色'
    	})
    }
});  

function del(id){
	$.ajax({
		url:'../user/delRoleByIds.do',
		type:'post',
		data:{
			"ids":id
		},
		dataType:'json',
		success:function(data){
			searchRole();
		}
	})
}
</script>
</html>