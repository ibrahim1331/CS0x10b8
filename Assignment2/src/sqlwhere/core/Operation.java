package sqlwhere.core;

import java.util.Map;

public abstract class Operation {
	public abstract Map<Integer, Object> getIndexMap();
	
	public abstract String getStatement();
}
