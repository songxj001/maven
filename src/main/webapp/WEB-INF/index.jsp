<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<!-- <script type="text/javascript" src="../js/ztree/js/jquery-1.4.4.min.js"></script> -->
	<script src="../js/easyui/jquery.min.js"></script>
	<script src="../js/easyui/jquery.easyui.min.js"></script>
	<script src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet" href="../js/easyui/themes/icon.css"/>
	<link rel="stylesheet" href="../js/easyui/themes/default/easyui.css"/>
	
	
	<link rel="stylesheet" href="../js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="../js/ztree/js/jquery.ztree.core.js"></script>
	<!-- <script src="../js/ztree/jquery.ztree.all.js"></script> -->
	
</head>
<style type="text/css">
	.ztree * {font-size: 10pt;font-family:"Microsoft Yahei",Verdana,Simsun,"Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue",Arial}
	.ztree li ul{ margin:0; padding:0}
	.ztree li {line-height:30px;}
	.ztree li a {width:200px;height:30px;padding-top: 0px;}
	.ztree li a:hover {text-decoration:none; background-color: #e6f0ff;border:0;height:30px;width: 98%;}
	/* .ztree li a span.button.switch {visibility:hidden} */
	.ztree.showIcon li a span.button.switch {visibility:visible}
	.ztree li a.curSelectedNode {background-color:#c9e1f9;border:0;height:30px;width: 98%;}
	.ztree li span {line-height:30px;}
	.ztree li span.button {margin-top: -7px;}
	.ztree li span.button.switch {width: 16px;height: 16px;}
	
	.ztree li a.level0 span {font-size: 150%;font-weight: bold;}
	.ztree li span.button {background-image:url("../js/ztree/left_menuForOutLook.png"); *background-image:url("../js/ztree/left_menuForOutLook.gif")}
	.ztree li span.button.switch.level0 {width: 20px; height:20px}
	.ztree li span.button.switch.level1 {width: 20px; height:20px}
	.ztree li span.button.noline_open {background-position: 0 0;}
	.ztree li span.button.noline_close {background-position: -18px 0;}
	.ztree li span.button.noline_open.level0 {background-position: 0 -18px;}
	.ztree li span.button.noline_close.level0 {background-position: -18px -18px;}
	
</style>
<body>
	<div class="easyui-layout" data-options="fit:true">   
	    <div data-options="region:'north',title:'金科教育',split:true" style="height:100px;"></div>   
	    <div data-options="region:'west',title:'功能导航',split:true" style="width:250px;">
	   		<div class="zTreeDemoBackground left">
	    		<ul id="navigationTree" class="ztree showIcon"></ul>
	    	</div>
	    </div>   
	    <div data-options="region:'center',title:'详细功能'">
	    	<div id="detailTabs" class="easyui-tabs" data-options="fit:true">   
			    <div title="欢迎" style="padding:20px;display:none;">   
			        欢迎    使用
			    </div>   
			</div>  
	      </div>   
	</div>  
</body>
<script type="text/javascript">
var curMenu = null, zTree_Menu = null;
var setting = {
	view: {
		showLine: false,
		showIcon: false,
		selectedMulti: false,
		dblClickExpand: false,
		addDiyDom: addDiyDom
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onClick: onClick
	}
};

function addDiyDom(treeId, treeNode) {
	var spaceWidth = 5;
	var switchObj = $("#" + treeNode.tId + "_switch"),
	icoObj = $("#" + treeNode.tId + "_ico");
	switchObj.remove();
	icoObj.before(switchObj);

	if (treeNode.level > 1) {
		var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
		switchObj.before(spaceStr);
	}
}
function onClick(event, treeId, treeNode, clickFlag){
	console.log(treeNode);
	if(!treeNode.isParent){
		var exists = $('#detailTabs').tabs('exists',treeNode.name);
		if(exists){
			$('#detailTabs').tabs('select',treeNode.name);
		}else{
			$('#detailTabs').tabs('add',{    
			    title:treeNode.name,    
			    content:createFrame(treeNode.pathUrl),    
			    closable:true,    
			    tools:[{    
			        iconCls:'icon-mini-refresh',    
			        handler:function(){    
			        	var tab = $('#detailTabs').tabs('getSelected');  // 获取选择的面板
			        	$('#detailTabs').tabs('update', {
			        		tab: tab,
			        		options: {
			        			content:createFrame(treeNode.pathUrl)
			        		}
			        	});
			        }    
			    }]    
			}); 
		}
	}
}
function createFrame(url){  
    return '<iframe scrolling="auto" frameborder="0"  src="'+ url + '" style="width:100%;height:100%;"></iframe>';  
}
function beforeClick(treeId, treeNode) {
	//console.log(treeNode);
	if (treeNode.level == 0 ) {
		var zTree = $.fn.zTree.getZTreeObj("navigationTree");
		zTree.expandNode(treeNode);
		return false;
	}
	return true;
}

$(document).ready(function(){
	$.ajax({
		url:'../tree/getNavigationTree.do',
		type:'post',
		data:{},
		dataType:'json',
		success:function(data){
			var treeObj = $("#navigationTree");
			var zTree = $.fn.zTree.init(treeObj, setting, data);

			treeObj.hover(function () {
				if (!treeObj.hasClass("showIcon")) {
					treeObj.addClass("showIcon");
				}
			}, function() {
				treeObj.removeClass("showIcon");
			});
		}
	})
});

</script>
</html>