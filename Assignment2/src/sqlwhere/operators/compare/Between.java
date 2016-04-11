package sqlwhere.operators.compare;

import java.util.ArrayList;
import java.util.List;

import sqlwhere.operators.Negable;

public class Between implements Negable {
	private String column;
	private Object start, end;
	
	public Between(String column, Object start, Object end){
		this.column = column;
		this.start = start;
		this.end = end;
	}

	@Override
	public String getNegatedString() {
		return String.format("%s NOT BETWEEN ? AND ?", column);
	}
	
	@Override
	public String getString() {
		return String.format("%s BETWEEN ? AND ?", column);
	}

	@Override
	public int getParamCounts() {
		return 2;
	}

	@Override
	public List<Object> getParamValues() {
		List<Object> result = new ArrayList<>();
		result.add(start);
		result.add(end);
		return result;
	}
}
