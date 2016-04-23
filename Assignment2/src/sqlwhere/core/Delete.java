package sqlwhere.core;

import java.util.HashMap;
import java.util.Map;

public class Delete extends Operation{
	private String table;
	private Where where;
	
	public Delete(String table){
		this.table = table;
	}
	
	public Delete where(Where where){
		this.where = where;
		return this;
	}
	
	@Override
	public Map<Integer, Object> getIndexMap(){
		return where==null ? new HashMap<Integer, Object>() : where.getIndexMap();
	}
	
	@Override
	public int getParamCounts(){
		return where==null ? 0: where.getParamCounts();
	}
	
	@Override
	public String getStatement(){
		return String.format("DELETE FROM \"%s\" %s", table, where==null ? "" : where.getStatement());
	}

}
