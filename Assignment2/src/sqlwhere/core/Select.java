package sqlwhere.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Select extends Operation{
	private String table;
	private List<String> columns = new ArrayList<>();
	private Where where;
	private List<OrderBy> orderBy = new ArrayList<>();
	private List<String> groupBy = new ArrayList<>();
	
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
	
	public Select orderBy(String column, boolean asc){
		orderBy.add(new OrderBy(column, asc));
		return this;
	}
	
	public Select orderBy(List<OrderBy> orderBys){
		orderBy.addAll(orderBys);
		return this;
	}
	
	public Select groupBy(String... column){
		groupBy.addAll(Arrays.asList(column));
		return this;
	}
	
	public Select groupBy(List<String> columns){
		groupBy.addAll(columns);
		return this;
	}
	
	@Override
	public int getParamCounts(){
		return where.getParamCounts();
	}

	@Override
	public Map<Integer, Object> getIndexMap() {
		return where==null ? new HashMap<Integer, Object>() : where.getIndexMap();
	}

	@Override
	public String getStatement(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ").append(columns.get(0));
		for(int i=1; i<columns.size(); i++){
			sb.append(", ").append(columns.get(i));
		}
		sb.append(" FROM ").append("\"").append(table).append("\"");
		if(where!=null){	
			sb.append(" ").append(where.getStatement());
		}
		if(groupBy.size()!=0){
			sb.append(" GROUP BY ").append(groupBy.get(0));
			for(int i=1; i<groupBy.size(); i++){
				sb.append(groupBy.get(i));
			}
		}
		if(orderBy.size()!=0){
			sb.append(" ORDER BY ").append(orderBy.get(0).column).append(orderBy.get(0).asc?"":" DESC");
			for(int i=1; i<orderBy.size(); i++){
				sb.append(orderBy.get(i).column).append(orderBy.get(i).asc?"":" DESC");
			}
		}
		return sb.toString();
	}
	
	public class OrderBy{
		String column;
		boolean asc;
		
		public OrderBy(String column, boolean asc){
			this.column = column;
			this.asc = asc;
		}
		
		public OrderBy(String column){
			this(column, true);
		}
	}
}
