function ajax(method, url, data, truefun, falsefun, endfun) {
	var succfun;
	var xhr = new XMLHttpRequest();
	method = method.toLowerCase();
	if(method == 'get') {
		if(data) {
			xhr.open('get', '/api/'+ url +'?'+ urlencode(data));
			data = null;
		} else {
			xhr.open('get', '/api/'+ url);
		}
		succfun = truefun;
	}
	else {
		succfun = function(res) {
			if(res.status) {
				_alert('操作成功', 'success');
				truefun ? truefun(res) : null;
			} else {
				_alert(res.msg);
				falsefun && falsefun();
			}
		}
		xhr.open('post', '/api/'+ url);
		if(method == 'file') {
			if(typeof FormData == 'undefined') {
				alert('因您浏览器版本太旧，请在弹窗中重新上传一次');
				var iframe = document.createElement('iframe');
				iframe.name = 'iframe_upload';
				//form
				var form = document.createElement('form');
				form.action = '/index.php/'+ url;
				form.target = iframe.name;
				form.method = 'post';
				form.enctype = 'multipart/form-data';
				//input
				var input_file = append_input();
				input_file.type = 'file';
				input_file.onchange = function() {
					form.submit();
				}
				for(key in data) {
					if(data[key]==undefined || typeof data[key]=='object') {
						input_file.name = key;
					} else {
						//非文件参数
						append_input(key, data[key]);
					}
				}
				append_input('csrf_random', Math.random());
				append_input('csrf_time', (new Date()).getTime());
				append_input().type = 'submit';
				function append_input(key, value) {
					var input = document.createElement('input');
					if(key) input.name = key;
					if(value) input.value = value;
					form.appendChild(input);
					return input;
				}
				//box
				var divbox = document.createElement('div');
				divbox.style.display = 'block';
				divbox.appendChild(iframe);
				divbox.appendChild(form);
				document.body.appendChild(divbox);
				//提交处理
				iframe.onload = function() {
					var json = iframe.contentWindow.document.body.innerText;
					if(!json) {
						return;
					}
					var res = JSON.parse(json);
					succfun(res);
					document.body.removeChild(divbox);
				}
				input_file.click();
				return;
			}
			var formData = new FormData();
			for(key in data) {
				if(data[key].length == 1) {
					formData.append(key, data[key][0]);
				} else {
					if(typeof data[key] == 'object') {
						var files = data[key];
						for(index=0; index<files.length; index++) {
							formData.append(key+'[]', files[index]);
						}
					} else {
						//非文件参数
						formData.append(key, data[key]);
					}
				}
			}
			data = formData;
		}
		else if(method == 'post') {
			xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
			data = JSON.stringify(data);
		}
		else {
			throw new Error('method只支持get、post、file三种传值');
			return;
		}
		xhr.setRequestHeader('time', (new Date()).getTime());
		xhr.setRequestHeader('random', Math.random());
	}
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4) {
			endfun && endfun();
			if((xhr.status>=200 && xhr.status<=299) || xhr.status==304) {
				var res = JSON.parse(xhr.responseText);
				succfun(res);
			}
			else if(xhr.status == 401) {
				_alert('尚未登录或登陆失效');
				noLogin();
			}
			else if(xhr.status == 403) {
				_alert(xhr.responseText);
			}
			else if(xhr.status == 404) {
				_alert('链接不存在');
			}
			else if(xhr.status>=500 && xhr.status<=599) {
				_alert('服务器错误，请稍后再试');
			}
			else {
				_alert('未知错误'+xhr.status);
			}
		}
	}
	xhr.send(data);
	//用户提示
	function _alert(msg, type) {
		if(!type) {
			type = 'error';
		}
		typeof(toastr)!='undefined' ? toastr[type](msg) : alert(msg);
	}
	//未登录的处理
	function noLogin() {
		//清除本地登录标识
		switch(url.substr(0, url.indexOf('/'))) {
			case 'manager':
				sessionStorage.removeItem('manager_name');
				sessionStorage.removeItem('manager_level');
				break;
			case 'user':
				sessionStorage.removeItem('user_name');
				break;
		}
		window.top.location.href = 'login.html';
	}
}

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

function loadJs(url) {
	var script = document.createElement('script');
	script.src = url;
	document.body.appendChild(script);
}

//对象转url
function urlencode(data, key) {
	var res = '';
	if(typeof data == 'object') {
		for(i in data) {
			var k = key==null ? i : key+'['+i+']';
			var encodeValue = urlencode(data[i], k);
			if(encodeValue) {
				res += '&'+ encodeValue;
			}
		}
	}
	else {
		res += '&'+ encodeURIComponent(key) +'='+ encodeURIComponent(data);
	}
	return res.substr(1);
}

// 表单自动加载数据函数，
function formload(obj, data) {
	var inputObj = obj.getElementsByTagName('input');
	for(i=0; i<inputObj.length; i++) {
		var name = inputObj[i].name;
		if(data[name] === undefined) {
			continue;
		}
		switch(inputObj[i].type) {
			case 'radio':
				if(inputObj[i].value == data[name]) {
					inputObj[i].checked = 'checked';
				}
				break;
			case 'checkbox':
				var arr = [];
				if(typeof(data[name]) == 'string') {
					arr = data[name].split(',');
				} else {
					arr = data[name];
				}
				for(index in arr) {
					if(inputObj[i].value == arr[index]) {
						inputObj[i].checked = 'checked';
						break;
					}
				}
				break;
			case 'file':
				break;
			case 'date':
				inputObj[i].value = typeof data[name]=='number' ? timestampToStr(data[name], onlyDay=true) : data[name];
				break;
			case 'datetime':
				inputObj[i].value = typeof data[name]=='number' ? timestampToStr(data[name]) : data[name];
				break;
			case 'text':
			case 'hidden':
			default :
				inputObj[i].value = data[name];
		}
	}
	var textareaObj = obj.getElementsByTagName('textarea');
	for(i=0; i<textareaObj.length; i++) {
		var name = textareaObj[i].name;
		if(data[name] === undefined || data[name] === null) {
			continue;
		}
		textareaObj[i].value = data[name];
	}
	var selectObj = obj.getElementsByTagName('select');
	for(i=0; i<selectObj.length; i++) {
		var name = selectObj[i].name;
		if(data[name] === undefined) {
			continue;
		}
		selectObj[i].value = data[name];
	}
	var imgObj = obj.getElementsByTagName('img');
	for(i=0; i<imgObj.length; i++) {
		var name = imgObj[i].name;
		if(data[name] === undefined) {
			continue;
		}
		imgObj[i].src = data[name];
	}
	var spanObj = obj.getElementsByTagName('span');
	for(i=0; i<spanObj.length; i++) {
		var name = spanObj[i].getAttribute('name');
		if(data[name] === undefined) {
			continue;
		}
		spanObj[i].innerText = data[name];
	}
}


//获取form中所有表单数据并转换为对象
//传入obj为 document.getElectmentById()得到的对象
function getFormObj(form, chb_split) {
	chb_split = chb_split || ',';
	var data = {};
	var inputs = form.querySelectorAll('input');
	for(i=0; i<inputs.length; i++) {
		var input = inputs[i];
		if(!input.name || input.disabled) {
			continue;
		}
		if(input.type == 'checkbox') {
			if(typeof data[input.name] == 'undefined') {
				data[input.name] = '';
			}
			if(input.checked) {
				if(data[input.name] == '') {
					data[input.name] = input.value;
				} else {
					data[input.name] += chb_split + input.value;
				}
			}
		}
		else if(input.type == 'radio') {
			if(typeof data[input.name] == 'undefined') {
				data[input.name] = '';
			}
			if(input.checked) {
				data[input.name] = input.value;
			}
		}
		else if(input.type == 'file') {
			continue;
		}
		else {
			data[input.name] = input.value;
		}
	}
	var selects = form.querySelectorAll('select');
	for(i=0; i<selects.length; i++) {
		var select = selects[i];
		if(select.disabled) {
			continue;
		}
		if(selects[i].multiple) {
			data[select.name] = [];
			var options = select.querySelectorAll('option:checked');
			for(j=0; j<options.length; j++) {
				data[select.name].push(options[j].value);
			}
			data[select.name] = data[select.name].join(chb_split);
		} else {
			data[select.name] = select.value;
		}
	}
	var textareas = form.querySelectorAll('textarea');
	for(i=0; i<textareas.length; i++) {
		var textarea = textareas[i];
		if(textarea.disabled) {
			continue;
		}
		data[textarea.name] = textarea.value;
	}
	return data;
}

//时间戳转换为正常日期
function timestampToStr(timestamp, onlyDay) {
	if(!timestamp) {
		return '-';
	}
	var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
	var Y = date.getFullYear();
	var M = date.getMonth() + 1;
	if(M < 10) {
		M = '0' + M;
	}
	var D = date.getDate();
	if(D < 10) {
		D = '0' + D;
	}
	if(onlyDay) {
		return Y+'-'+M+'-'+D;
	}
	var h = date.getHours();
	if(h < 10) {
		h = '0' + h;
	}
	var m = date.getMinutes();
	if(m < 10) {
		m = '0' + m;
	}
	var s = date.getSeconds();
	if(s < 10) {
		s = '0' + s;
	}
	return Y+'-'+M+'-'+D+' '+h+':'+m+':'+s;
}

function input_upload(accept, multiple, callback) {
	var input = document.createElement('input');
	input.style.display = 'none';
	input.type = 'file';
	switch(accept) {
		case 'img':
			input.accept = 'image/*';
			break;
		case 'excel':
			input.accept = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel';
			break;
		case 'pdf':
			input.accept = 'application/pdf';
			break;
	}
	if(multiple) {
		input.multiple = 'multiple';
	}
	input.onchange = function() {
		callback && callback(input.files);
		document.body.removeChild(input);
	}
	document.body.appendChild(input);
	input.click();
}

function form_download(url, data) {
	if(!data) {
		data = {};
	}
	data.csrf_random = Math.random();
	data.csrf_time = (new Date()).getTime();
	var form = document.createElement('form');
	form.action = url;
	form.method = 'POST';
	form.target = '_blank';
	form.style.display = 'none';
	var inputs = {};
	for(key in data) {
		var input = document.createElement('input');
		input.name = key;
		input.value = data[key];
		form.appendChild(input);
	}
	document.body.appendChild(form);
	form.submit();
	document.body.removeChild(form);
}

function a_download(url, name) {
	var a = document.createElement('a');
	if(name) {
		a.download = name;
	}
	a.href = url;
	a.target = '_blank';
	document.body.appendChild(a);
	a.click();
	document.body.removeChild(a);
}

//下载base64流 文件
function download_base64_file(base_64, name) {
	var bstr = atob(base_64);
	var n = bstr.length;
	var u8arr = new Uint8Array(n);
	while (n--) {
		u8arr[n] = bstr.charCodeAt(n) // 转换编码后才可以使用charCodeAt 找到Unicode编码
	}
	var blob = new Blob([u8arr]);
	var url = window.URL.createObjectURL(blob);
	a_download(url, name);
}

//初始化
if(typeof toastr != 'undefined') {
	toastr.options = {
		"closeButton": true, //是否显示关闭按钮
		"progressBar": true,
		"timeOut": "2000",
	}
}
if(typeof layer != 'undefined') {
	layer.config({extend: 'extend/layer.ext.js'});
}

function int2ip(num) {
	var str;
	var tt = [];
	tt[0] = (num >>> 24) >>> 0;
	tt[1] = ((num << 8) >>> 24) >>> 0;
	tt[2] = (num << 16) >>> 24;
	tt[3] = (num << 24) >>> 24;
	str = String(tt[0]) + "." + String(tt[1]) + "." + String(tt[2]) + "." + String(tt[3]);
	return str;
}