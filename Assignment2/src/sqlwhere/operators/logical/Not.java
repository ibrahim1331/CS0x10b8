package sqlwhere.operators.logical;

import java.util.List;

import sqlwhere.operators.Negable;
import sqlwhere.operators.Operator;

public class Not implements Operator {
	private Negable operator;
	
	public Not(Negable operator){
		this.operator = operator;
	}
	
	@Override
	public String getString() {
		return operator.getNegatedString();
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
