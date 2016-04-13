package sqlwhere.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sqlwhere.operators.assign.Set;

public class Update extends Operation {
	private String table;
	private List<Set> setValues = new ArrayList<>();
	private Where where;
	
	public Update(String table){
		this.table = table;
	}
	
	public Update set(String column, Object value){
		setValues.add(new Set(column, value));
		return this;
	}
	
	public Update set(List<Set> values){
		setValues.addAll(values);
		return this;
	}
	
	public Update where(Where where){
		this.where = where;
		return this;
	}

	@Override
	public Map<Integer, Object> getIndexMap() {
		Map<Integer, Object> result = new HashMap<>();
		int index = 1;
		for(Set set: setValues){
			result.put(index++, set.getValue());
		}
		for(Object o: where.getParamValues()){
			result.put(index++, o);
		}
		return result;
	}

	@Override
	public String getStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ").append("\"").append(table).append("\"");
		sb.append(" SET ").append(setValues.get(0).getString());
		for(int i=1; i<setValues.size(); i++){
			sb.append(", ").append(setValues.get(i).getString());
		}
		if(where!=null){
			sb.append(" ").append(where.getStatement());
		}
		return sb.toString();
	}

}
