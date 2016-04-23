package sqlwhere.operators.compare;

import java.util.List;

import sqlwhere.operators.Operator;
import sqlwhere.operators.logical.Not;

/**
 * shorthand class for {@code new Not(new In(column, values...)}
 * @author slklau
 *
 */
public class NotIn implements Operator {
	private Not operator;
	
	public NotIn(String column, Object value, Object... values){
		operator = new Not(new In(column, value, values));
	}
	
	public NotIn(String column, List<Object> values){
		operator = new Not(new In(column, values));
	}

	@Override
	public String getString() {
		return operator.getString();
	}

	@Override
	public int getParamCounts() {
		return operator.getParamCounts();
	}

	@Override
	public List<Object> getParamValues() {
		return operator.getParamValues();
	}

}
