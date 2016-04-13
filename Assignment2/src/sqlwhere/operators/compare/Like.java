package sqlwhere.operators.compare;

import java.util.ArrayList;
import java.util.List;

import sqlwhere.operators.Negable;

public class Like implements Negable {
	private String column, value;
	
	public Like(String column, String value){
		this.column = column;
		this.value = value;
	}

	@Override
	public String getNegatedString() {
		return String.format("%s NOT LIKE ?", column);
	}
	
	@Override
	public String getString() {
		return String.format("%s LIKE ?", column);
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
