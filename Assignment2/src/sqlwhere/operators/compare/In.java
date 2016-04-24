package sqlwhere.operators.compare;

import java.util.ArrayList;
import java.util.List;

import sqlwhere.operators.Negable;

public class In implements Negable {
	private String column;
	private List<Object> values = new ArrayList<Object>();
	
	public In(String column, Object value, Object... values){
		this.column = column;
		this.values.add(value);
		for(Object o: values){
			this.values.add(o);
		}
	}
	
	public In(String column, List<? extends Object> values){
		this.column = column;
		this.values.addAll(values);
	}

	@Override
	public String getString() {
		return String.format("%s IN (%s)", column, this.getParamString());
	}

	@Override
	public int getParamCounts() {
		return values.size();
	}

	@Override
	public List<Object> getParamValues() {
		return values;
	}

	@Override
	public String getNegatedString() {
		return String.format("%s NOT IN (%s)", column, this.getParamString());
	}
	
	private String getParamString(){
		StringBuffer sb = new StringBuffer("?");
		for(int i=1; i<values.size(); i++){
			sb.append(",?");
		}
		return sb.toString();
	}

}
