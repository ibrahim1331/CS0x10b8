package sqlwhere.operators.compare;

import java.util.ArrayList;
import java.util.List;

import sqlwhere.operators.Negable;

public class Null implements Negable {
	private String column;
	
	public Null(String column){
		this.column = column;
	}

	@Override
	public String getNegatedString() {
		return String.format("%s IS NOT NULL", column);
	}

	@Override
	public String getString() {
		return String.format("%s IS NULL", column);
	}

	@Override
	public int getParamCounts() {
		return 0;
	}

	@Override
	public List<Object> getParamValues() {
		return new ArrayList<Object>();
	}

}
