package sqlwhere.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Select extends Operation{
	private String table;
	private List<String> columns = new ArrayList<>();
	private Where where;
	
	public Select(String col1, String... columns){
		this.columns.add(col1);
		this.columns.addAll(Arrays.asList(columns));
	}
	
	public Select from(String table){
		this.table = table;
		return this;
	}
	
	public Select where(Where where){
		this.where = where;
		return this;
	}

	@Override
	public Map<Integer, Object> getIndexMap() {
		return where==null ? null : where.getIndexMap();
	}

	@Override
	public String getStatement(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ").append(columns.get(0));
		for(int i=1; i<columns.size(); i++){
			sb.append(", ").append(columns.get(i));
		}
		sb.append(" FROM ").append(table);
		if(where!=null)	sb.append(" ").append(where.getStatement());
		return sb.toString();
	}
}
