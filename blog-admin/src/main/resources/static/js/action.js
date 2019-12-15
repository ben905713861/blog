//公共动作

//是否为手机
function is_mobileBrowser() {
	var sUserAgent = navigator.userAgent.toLowerCase();
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
	var bIsMidp = sUserAgent.match(/midp/i) == "midp";
	var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
	var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
	var bIsAndroid = sUserAgent.match(/android/i) == "android";
	var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
	var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
	return (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM);
}

//加载html
function loadHtml(url, f2) {
	var xhr = new XMLHttpRequest;
	xhr.open('GET', url);
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			if(typeof f2 == 'function') {
				f2(xhr.responseText);
			}
			else if(typeof f2 == 'object') {
				f2.innerHTML = xhr.responseText;
			}
		}
	}
	xhr.send();
}


if(is_mobileBrowser()) {
	//下方快捷导航
	loadHtml('/static/tpl/nav.html', function(html) {
		let box = document.createElement('div');
		box.innerHTML = html;
		document.body.onload = function() {
			document.body.appendChild(box);
		}
	});
	
	//手机返回键
	(function() {
		function plusReady(){
			// 隐藏滚动条
			plus.webview.currentWebview().setStyle({scrollIndicator:'none'});
			// Android处理返回键
			plus.key.addEventListener('backbutton', function() {
				if(location.pathname == '/static/m_index.html') {
					confirm('确认退出？') && plus.runtime.quit();
				} else {
					history.back();
				}
			}, false);
		}
		if(window.plus){
			plusReady();
		} else {
			document.addEventListener('plusready', plusReady, false);
		}
	})();
}
