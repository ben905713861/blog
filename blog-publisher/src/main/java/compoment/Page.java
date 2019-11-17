package compoment;

import java.util.HashMap;
import java.util.Map;

public class Page {

	private int firstPage = 1;
	private int endPage;
	public int thisPage;
	public int limit;
	private int nextPage;
	private int lastPage;
	
	public Page(int limit) {
		this.limit = limit;
	}
	
	public void setTotalCount(int totalCount) {
		endPage = (int) Math.ceil((double)totalCount / limit);
		if(endPage == 0) {
			endPage = 1;
		}
	}
	
	public void setThisPage(int thisPage) {
		this.thisPage = thisPage;
	}
	
	public boolean isEndPage() {
		return thisPage == endPage;
	}
	
	public Map<String, String> toMap() {
		nextPage = thisPage + 1;
		nextPage = nextPage > endPage ? endPage : nextPage;
		
		lastPage = thisPage - 1;
		lastPage = lastPage < 1 ? 1 : lastPage;
		
		Map<String, String> page = new HashMap<String, String>();
		page.put("firstPage", String.valueOf(firstPage));
		page.put("endPage", String.valueOf(endPage));
		page.put("thisPage", String.valueOf(thisPage));
		page.put("nextPage", String.valueOf(nextPage));
		page.put("lastPage", String.valueOf(lastPage));
		return page;
	}
	
}
