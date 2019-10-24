function Page(search) {
	
	this.page = 1;
	this.totalPage = 1;
	this.limit = 10;
	
	this.firstPage = function() {
		this.page = 1;
		search();
	}
	
	this.subPage = function() {
		if(this.page > 1) {
			this.page --;
		} else {
			alert('已到达首页');
			return;
		}
		search();
	}
	
	this.addPage = function() {
		if(this.page < this.totalPage) {
			this.page ++;
		} else {
			alert('已到达最后一页');
			return;
		}
		search();
	}
	
	this.lastPage = function() {
		this.page = this.totalPage;
		search();
	}
	
}