//手机导航栏按钮
function mnavhClick() {
	var oUl = document.getElementById("starlist");  
	var style = oUl.style;
	style.display = style.display == "block" ? "none" : "block";
	document.getElementById("mnavh").className = style.display == "block" ? "open" : ""
}

//加载html小模块
(function() {
	let boxes = document.querySelectorAll('loadhtml');
	for(let i = 0; i < boxes.length; i++) {
		let url = boxes[i].getAttribute('url');
		loadHtml(url, function(html) {
			boxes[i].outerHTML = html;
		});
	}
})();
