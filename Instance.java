import java.util.ArrayList;

public class Instance {
    public String category;
    public ArrayList<Boolean> vals = new ArrayList<Boolean>();

    public Instance(String cat, String [] s){
      this.category = cat;
      
      //skip first element
      for(int i = 1; i < s.length; i++) {
    	  vals.add(Boolean.parseBoolean(s[i]));
      }
    }

    public boolean getAtt(int index){
      return vals.get(index);
    }
    
    public String toString(){
      StringBuilder ans = new StringBuilder(category);
      ans.append(" ");
      for (Boolean val : vals)
	ans.append(val?"true  ":"false ");
      return ans.toString();
    }
    
  }
