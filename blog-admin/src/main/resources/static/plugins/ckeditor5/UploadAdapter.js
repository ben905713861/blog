class UploadAdapter {
	constructor(loader, type) {
		this.loader = loader;
		this.type = type;
	}
	upload() {
		var _this = this;
		let imageCutter;
		return this.loader.file
			.then(function(file) {
				imageCutter = new ImageCutter(file);
				return imageCutter.compress(1200);
			}).then(function() {
				let newFile = imageCutter.display();
				return new Promise(function(resolve, reject) {
					ajax('file', '/upload/uploadOne?type='+ _this.type, {
						upfile: newFile,
					}, function success(res) {
						resolve({
							default: res.url,
						});
					}, function fail(res) {
						reject('上传失败，' + res.msg);
					}, null, function error() {
						reject('上传失败，服务器响应错误');
					});
				});
			});
	}
	abort() {

	}
}