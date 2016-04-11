package sqlwhere.operators;

import java.util.List;

public interface Operator {
	public String getString();
	public int getParamCounts();
	public List<Object> getParamValues();
}
