function imgCompress(file, max_length, quality, callback) {
	if(max_length == undefined) {
		max_length = 1600;
	}
	if(quality == undefined) {
		quality = 0.8;
	}
	
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onload = function() {
		var img = new Image();
		img.src = this.result;
		img.onload = function() {
			var _img = this;
			var originWidth = _img.width;
			var originHeight = _img.height;
			var width;
			var height;
			
			//最大边长判断
			var maxOriginLength = originWidth > originHeight ? originWidth : originHeight;
			if(maxOriginLength > max_length) {
				//宽高比
				var rate = originWidth / originHeight;
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
			
			var anw = document.createAttribute('width');
			anw.nodeValue = width;
			var anh = document.createAttribute('height');
			anh.nodeValue = height;
			
			var canvas = document.createElement('canvas');
			canvas.setAttributeNode(anw);
			canvas.setAttributeNode(anh);
			canvas.getContext('2d').drawImage(_img, 0, 0, width, height);
			var base64 = canvas.toDataURL('image/jpeg', quality);
			
			//base64转file对象
			var blob = dataURLtoBlob(base64);
			var newFile = new File([blob], file.name +'.jpg');
			
			callback(newFile);
		}
	}
	//将base64转换为blob
	function dataURLtoBlob(dataurl) { 
		var arr = dataurl.split(','),
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