package sqlwhere.operators.compare;

import java.util.List;

import sqlwhere.operators.Operator;
import sqlwhere.operators.logical.Not;

/**
 * shorthand class for {@code new Not(new Like(column, value)}
 * @author slklau
 *
 */
public class NotLike implements Operator {
	private Not operator;
	
	public NotLike(String column, String value){
		operator = new Not(new Like(column, value));
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
