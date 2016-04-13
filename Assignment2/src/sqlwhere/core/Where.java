package sqlwhere.core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sqlwhere.operators.Operator;

public class Where{	
	private static final String OR = "OR";
	private static final String AND = "AND";
	
	private List<Object> opList = new ArrayList<>();
	
	public Where(Operator op){
		opList.add(op);
	}
	
	public Where or(Operator op){
		opList.add(OR);
		opList.add(op);
		return this;
	}
	
	public Where and(Operator op){
		opList.add(AND);
		opList.add(op);
		return this;
	}
	
	public Map<Integer, Object> getIndexMap(){
		Map<Integer, Object> idxMap = new HashMap<>();
		int index = 1;
		for(Object op: opList){
			if(op instanceof Operator){
				for(Object obj: ((Operator) op).getParamValues()){
					idxMap.put(index++, obj);
				}
			}
		}
		return idxMap;
	}
	
	public List<Object> getParamValues(){
		List<Object> result = new ArrayList<>();
		for(Object op: opList){
			if(op instanceof Operator){
				result.addAll(((Operator) op).getParamValues());
			}
		}
		return result;
	}
	
	public String getStatement(){
		StringBuffer result = new StringBuffer("WHERE");
		for(Object op: opList){
			if(op instanceof Operator){
				result.append(" ").append(((Operator) op).getString());
			} else {
				result.append(" ").append(op.toString());
			}
		}
		return result.toString();
	}
}
