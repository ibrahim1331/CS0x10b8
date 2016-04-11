package sqlwhere.operators.compare;

import java.util.ArrayList;
import java.util.List;

import sqlwhere.operators.Operator;

public class LessThan implements Operator {
	private String column;
	private Object value;
	
	public LessThan(String column, Object value){
		this.column = column;
		this.value = value;
	}

	@Override
	public String getString() {
		return String.format("%s < ?", column);
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
