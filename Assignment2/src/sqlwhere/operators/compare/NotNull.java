package sqlwhere.operators.compare;

import java.util.List;

import sqlwhere.operators.Operator;
import sqlwhere.operators.logical.Not;

/**
 * shorthand class for {@code new Not(new Null(column)}
 * @author slklau
 *
 */
public class NotNull implements Operator {
	private Not operator;
	
	public NotNull(String column){
		operator = new Not(new Null(column));
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
