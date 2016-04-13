package sqlwhere.operators.compare;

import java.util.List;

import sqlwhere.operators.Operator;
import sqlwhere.operators.logical.Not;

/**
 * shorthand class for {@code new Not(new Equal(column, value)}
 * @author slklau
 *
 */
public class NotEqual implements Operator {
	private Not operator;
	
	public NotEqual(String column, Object value){
		operator = new Not(new Equal(column, value));
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
