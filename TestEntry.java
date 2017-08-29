import java.util.ArrayList;

public class TestEntry {
	public String result;
	public ArrayList<String> conditions = new ArrayList<>();
	
	public TestEntry(String result, String[] s, ArrayList<String> attributes) {
		this.result = result;
		//skip first element
	    for(int i = 1; i < s.length; i++) {
	      Boolean b = Boolean.parseBoolean(s[i]);
	      if (b) {
	    	  conditions.add(attributes.get(i-1));
	      }
	    }
	}

	public String printConds() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		for(String s: conditions) {
			sb.append(s+" ");
		}
		sb.append(" }");
		return sb.toString();
	}
	
}
