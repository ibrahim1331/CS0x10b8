package sqlwhere.operators.logical;

import java.util.ArrayList;
import java.util.List;

import sqlwhere.operators.Operator;

public class And implements Operator{
private List<Operator> carry = new ArrayList<>();
	
	public And(Operator op1, Operator op2, Operator... ops){
		carry.add(op1);
		carry.add(op2);
		for(Operator op: ops){
			carry.add(op);
		}
	}

	@Override
	public String getString() {
		StringBuffer result = new StringBuffer();
		result.append("(").append(carry.get(0).getString());
		for(int i=1; i<carry.size(); i++){
			result.append(" AND ").append(carry.get(i).getString());
		}
		result.append(")");
		return result.toString();
	}

	@Override
	public int getParamCounts() {
		int sum = 0;
		for(Operator op: carry){
			sum += op.getParamCounts();
		}
		return sum;
	}

	@Override
	public List<Object> getParamValues() {
		List<Object> result = new ArrayList<>();
		for(Operator op: carry){
			result.addAll(op.getParamValues());
		}
		return result;
	}
}
