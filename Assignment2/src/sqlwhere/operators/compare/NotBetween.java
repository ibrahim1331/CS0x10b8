package sqlwhere.operators.compare;

import java.util.List;

import sqlwhere.operators.Operator;
import sqlwhere.operators.logical.Not;

/**
 * shorthand class for {@code new Not(new Between(column, start, end)}
 * @author slklau
 *
 */
public class NotBetween implements Operator {
	private Not operator;
	
	public NotBetween(String column, Object start, Object end){
		operator = new Not(new Between(column, start, end));
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
