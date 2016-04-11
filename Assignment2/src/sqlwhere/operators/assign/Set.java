package sqlwhere.operators.assign;

import java.util.ArrayList;
import java.util.List;

import sqlwhere.operators.Operator;

public class Set implements Operator{
	private String column;
	private Object value;
	
	public Set(String column, Object value){
		this.column = column;
		this.value = value;
	}
	
	public String getColumn(){
		return column;
	}
	
	public Object getValue(){
		return value;
	}
	
	@Override
	public String getString() {
		return String.format("%s = ?", column);
	}

	@Override
	public int getParamCounts() {
		return 1;
	}

	@Override
	public List<Object> getParamValues() {
		List<Object> result = new ArrayList<>();
		result.add(value);
		return result;
	}
}
