package sqlwhere.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sqlwhere.operators.assign.Set;

public class Insert extends Operation {
	private String table;
	private List<Set> insertValues = new ArrayList<>();
	
	public Insert(String table){
		this.table = table;
	}
	
	public Insert values(String column, Object value){
		insertValues.add(new Set(column, value));
		return this;
	}
	
	public Insert values(List<Set> values){
		insertValues.addAll(values);
		return this;
	}

	@Override
	public Map<Integer, Object> getIndexMap() {
		Map<Integer, Object> result = new HashMap<>();
		int index = 1;
		for(Set s: insertValues){
			result.put(index++, s.getValue());
		}
		return result;
	}

	@Override
	public String getStatement() {
		StringBuffer columns = new StringBuffer();
		columns.append(insertValues.get(0).getColumn());
		StringBuffer values = new StringBuffer();
		values.append("?");
		for(int i=1; i<insertValues.size(); i++){
			columns.append(",").append(insertValues.get(i).getColumn());
			values.append(",").append("?");
		}
		return String.format("INSERT INTO \"%s\" (%s) VALUES (%s)", table, columns, values);
	}

}
