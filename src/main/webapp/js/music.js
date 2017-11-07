function stopMusic(){
	$('#springmvc_music_0123123').remove();
}
function startMusic(msg,img,mus){
	stopMusic();
	$.messager.show({
		title:'提示',
		msg:''+msg+'<img width=\'90px\' height=\'90px\' src=\''+img+'\'/>',
		timeout:9000,
		showType:'slide',
		height:'200px'
	});
	$('body').append('<div id="springmvc_music_0123123"><audio autoplay="autoplay"><source src="'+mus+'" type="audio/mpeg" /></audio></div>');
	window.setTimeout("stopMusic()",10000);
}
