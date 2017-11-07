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
<script src="../js/music.js"></script>
<title>权限</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">   
	    <div data-options="region:'west',title:'权限树'" style="width:25%;">
	    	<ul id="powerTreeId"></ul>
	    </div>   
	    <div data-options="region:'east',title:'详细权限'" style="width: 75%">
	    	<div class="easyui-panel" data-options="fit:true,border:false">
		    	<div class="easyui-panel" data-options="border:false" style="height: 20%">
		    		<div style="margin: 2% 10px">
			    		<form id="menuForm" method="post">
			    			<table>
			    				<tr>
			    					<td>绑定权限：</td>
			    					<td>
			    						<input type="hidden" id="powerID" name="powerId"/>
			    						<input type="text" class="easyui-textbox" id="powerName" readonly="true"/>
			    					</td>
			    					<td rowspan="3">备注：</td>
			    					<td rowspan="3"> 
			    					<input type="text" name="node" id="menuNode" class="easyui-textbox" data-options="multiline:true" 
			    						style="width:250px;height:65px"/> 
			    					<a href="javascript:saveMenu();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
			    					</td>
			    				</tr>
			    				<tr>
			    					<td>菜单名称：
			    					</td>
			    					<td> <input type="text" id="menuName" name="name" class="easyui-textbox"/> </td>
			    				</tr>
			    				<tr>
			    					<td>url：</td>
			    					<td> <input type="text" id="menuUrl"  name="url" class="easyui-textbox" /> </td>
			    				</tr>
			    				
			    			</table>
			    		</form>
		    		</div>
		    	</div>
		    	<div class="easyui-panel"  data-options="title:'菜单列表'" style="height: 80%">
		    		<table id="powerMenuId"></table>
		    		
		    	</div>
	    	</div>
	    </div>   
	</div>  
	<div id="rightMenu" class="easyui-menu" style="width: 120px">
		<div onclick="append()" data-options="iconCls:'icon-add'">追加</div>
		<div onclick="remove()" data-options="iconCls:'icon-remove'">移除</div>
		<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
	<div id="powerDialog" class="easyui-dialog" data-options="title:'追加权限节点',resizable:true,modal:true,closed:true,
		buttons:[
			{
				text:'保存',
				iconCls:'icon-ok',
				plain:true,
				handler:function(){
					savePower();
				}
			}]" style="width: 400px;height: 300px">
		<div style="padding:75px 60px 20px 60px">
			<form id="powerForm" method='post'>
				<table>
					<tr>
						<td>权限名称：</td>
						<td> 
						<input type="hidden" name="id" id="powerId"/>
						<input type="text" name="text" class="easyui-textbox"/> 
						<input type="hidden" id="pid" name="pid"/>
						</td>
					</tr>
					<tr>
						<td>url：</td>
						<td>
							<input type="text" name="url" class="easyui-textbox"  data-options="multiline:true" style="width:180px;height:50px"/> 
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">


	function saveMenu(){
		$('#menuForm').form('submit',{
			url:'../user/saveMenu.do',
			onSubmit:function(){
				var powerID = $('#powerID').val();
				if(powerID == null || powerID == ''){
					$.messager.alert('提示','请选择需要绑定的权限','info');
					return false;
				}
			},
			success:function(data){
				$('#menuNode').textbox('setValue','');
				$('#menuName').textbox('setValue','');
				$('#menuUrl').textbox('setValue','');
				$('#powerMenuId').datagrid('reload');
			}
		})
	}
	
	//初始化详细权限
	function initPowerMenuList(powerId){
		$('#powerMenuId').datagrid({    
		    url:'../user/getPowerMenu.do?powerId='+powerId, 
		   // toolbar:'#toolbar',
		    pageNumber:1,
		    pageSize:10,
		    fit:true,
		    pageList:[10,20,50,100],
		    pagination:true,
		    loadMsg:'数据加载中...',
		    rownumbers:true,
		    columns:[[    
		        {field:'id',title:'ID',checkbox:true},    
		        {field:'name',title:'菜单名称',width:'30%'},
		        {field:'url',title:'url',width:'30%'},
		        {field:'node',title:'备注',width:'30%'},
		        {field:'abc',title:'操作',width:'5%',formatter:function(value,row,index){
		        	return '<a href="javascript:del(\''+row.id+'\');">删除</a>'
		        }}
		    ]]
		  
		});  
	}
	
	//删除详细权限
	function del(menuId){
		$.ajax({
			url:'../user/delMenuById.do',
			type:'post',
			data:{
				id:menuId
			},
			dataType:'json',
			success:function(data){
				$('#powerMenuId').datagrid('reload');
			}
		})
	}
	
	function remove(){
		var tree = $('#powerTreeId').tree('getSelected');
		if(tree.pid == 0){
			$.messager.alert('提示','根节点不能被删除','info');
			return;
		}
		if(tree.children.length <= 0){
			$.messager.confirm('提示','你确定要删除吗',function(r){
				if (r) {
					deletePower(tree.id);
				}
			})
		}else{
			$.messager.confirm('提示','你确定要删除吗?删除父节点会把下面的子节点全部删除',function(r){
			if (r) {
					deletePower(tree.id);
				}
			})
		}
	}
	
	function deletePower(powerId){
		$.ajax({
			url:'../user/deletePowerById.do',
			type:'post',
			data:{
				id:powerId
			},
			dataType:'json',
			success:function(data){
				if (data) {
					$.messager.alert('提示','删除成功','info');
					initTree();
				}else{
					$.messager.alert('提示','删除失败','warning');
				}
			}
		})
	}
	
	function edit(){
		var tree = $('#powerTreeId').tree('getSelected');
		$('#powerId').val(tree.id);
		$('#powerForm').form('load',tree)
		$('#powerDialog').dialog({
			title:'编辑权限节点',
			iconCls:'icon-edit',
			closed:false,
			onClose:function(){
				$('#powerForm').form('reset');
			}
		})
	}
	function savePower(){
		$('#powerForm').form('submit',{
			url:'../user/savePower.do',
			success:function(data){
				$('#powerDialog').dialog('close');
				$('#powerForm').form('reset');
				initTree();
				//startMusic("保存成功了", "../images/dese.gif", "../music/dabeizhou.mp3");
			}
		})
	}
	function append(){
		var tree = $('#powerTreeId').tree('getSelected');
		$('#pid').val(tree.id);
		$('#powerDialog').dialog({
			title:'追加权限节点',
			iconCls:'icon-add',
			closed:false,
			onClose:function(){
				$('#powerForm').form('reset');
			}
			
		})
	}
	$(function(){
		initTree();
		initPowerMenuList();
	})
	function initTree(){
		$('#powerTreeId').tree({
			url:'../user/getPowerListAll.do',
			lines:true,
			onClick:function(node){
				$('#powerName').textbox('setValue',node.text);
				$('#powerID').val(node.id);
				initPowerMenuList(node.id);
			},
			onContextMenu:function(e,node){
				e.preventDefault();
				$('#powerTreeId').tree('select', node.target);
				$('#rightMenu').menu('show', {
					left: e.pageX,
					top: e.pageY
				});

			}
		});
	}
</script>
</html>