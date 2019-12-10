function ImageCutter(file, quality) {
	
	if(!quality) {
		quality = 0.8;
	}
	var callbackType = 'file';
	var img;
	
	this.setCallbackType = function(type) {
		if(type != 'file' && type != 'base64' && type != 'blob') {
			return;
		}
		callbackType = type;
	}
	
	function loadImg(callback) {
		if(img) {
			getInfo();
		} else {
			var reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = function() {
				img = new Image();
				img.src = reader.result;
				img.onload = function() {
					getInfo();
				}
			}
		}
		function getInfo() {
			var originWidth = img.width;
			var originHeight = img.height;
			var rate = originWidth / originHeight;
			
			callback(originWidth, originHeight, rate);
		}
	}
	
	//按最长边压缩
	this.compress = function(max_length) {
		if(!max_length) {
			max_length = 1600;
		}
		return new Promise(function(resolve, reject) {
			loadImg(function(originWidth, originHeight, rate) {
				var width,height;
				//最大边长判断
				var maxOriginLength = originWidth > originHeight ? originWidth : originHeight;
				if(maxOriginLength > max_length) {
					//根据最长边压缩
					if(originWidth > originHeight) {
						width = max_length;
						height = width / rate;
					} else {
						height = max_length;
						width = height * rate;
					}
					
				} else {
					//不用压缩
					width = originWidth;
					height = originHeight;
				}
				imgDraw(function() {
					resolve();
				}, 0, 0, width, height);
			});
		});
	}
	
	//缩放
	this.zoom = function(width, height) {
		if(!width || width <= 0) {
			throw new DOMException('error, params 2 "width" can not be null');
			return;
		}
		return new Promise(function(resolve, reject) {
			loadImg(function(originWidth, originHeight, rate) {
				if(height == null || height <= 0) {
					height = width / rate;
				}
				imgDraw(function() {
					resolve();
				}, 0, 0, width, height);
			});
		});
	}
	
	//裁剪中心区域
	this.cut = function(width, height) {
		return new Promise(function(resolve, reject) {
			loadImg(function(originWidth, originHeight, rate) {
				let x = - (originWidth - width) / 2;
				let y = - (originHeight - height) / 2;
				
				imgDraw(function() {
					resolve();
				}, x, y, originWidth, originHeight, width, height);
			});
		});
	}
	
	//根据输入的最大高度裁剪，保留裁剪后区域
	this.cutMaxHeight = function(max_height) {
		return new Promise(function(resolve, reject) {
			loadImg(function(originWidth, originHeight, rate) {
				let width = originWidth;
				let height = originHeight > max_height ? max_height : originHeight;
				let x = 0;
				let y = - (originHeight - height) / 2;
				imgDraw(function() {
					resolve();
				}, x, y, originWidth, originHeight, width, height);
			});
		});
	}
	
	function imgDraw(callback, x, y, width, height, outputWidth, outputHeight) {
		if(outputWidth == undefined) {
			outputWidth = width;
		}
		if(outputHeight == undefined) {
			outputHeight = height;
		}
		var anw = document.createAttribute('width');
		anw.nodeValue = outputWidth;
		var anh = document.createAttribute('height');
		anh.nodeValue = outputHeight;
		
		var canvas = document.createElement('canvas');
		canvas.setAttributeNode(anw);
		canvas.setAttributeNode(anh);
		
		var context = canvas.getContext('2d');
		context.drawImage(img, x, y, width, height);
		
		//白色背景色
		writeBackground(context, canvas);
		
		var base64 = canvas.toDataURL('image/jpeg', quality);
		img = new Image();
		img.src = base64;
		img.onload = function() {
			callback();
		}
	}
	
	//将canvas的透明背景设置成白色   
	function writeBackground(context, canvas) {
		var imageData = context.getImageData(0, 0, canvas.width, canvas.height);
		for(var i = 0; i < imageData.data.length; i += 4) {
		    // 当该像素是透明的，则设置成白色
		    if(imageData.data[i + 3] == 0) {
		        imageData.data[i] = 255;
		        imageData.data[i + 1] = 255;
		        imageData.data[i + 2] = 255;
		        imageData.data[i + 3] = 255;
		    }
		}
		context.putImageData(imageData, 0, 0);
	}
	
	this.display = function() {
		var base64 = img.src;
		var res;
		switch(callbackType) {
			case 'file':
				var blob = base64toBlob(base64);
				var fileName = file.name;
				fileName = fileName.substr(0, fileName.lastIndexOf('.'));
				res = new File([blob], fileName +'.jpg');
				break;
			case 'base64':
				res = base64;
				break;
			case 'blob':
				res = base64toBlob(base64);
				break;
		}
		return res;
	}
	
	//将base64转换为blob
	function base64toBlob(base64) { 
		var arr = base64.split(','),
			mime = arr[0].match(/:(.*?);/)[1],
			bstr = atob(arr[1]),
			n = bstr.length,
			u8arr = new Uint8Array(n);
		while (n--) {
			u8arr[n] = bstr.charCodeAt(n);
		}
		return new Blob([u8arr], { type: mime });
	}
	
}