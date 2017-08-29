import java.util.ArrayList;

public class TestingSuite {
	public ArrayList<TestEntry> instances = new ArrayList<>();
	
	public void addEntry(TestEntry te) {
		instances.add(te);
	}

	public boolean test(TestEntry te, Node node) {
		return predict(te, node);
	}
	
	public boolean predict(TestEntry te, Node node) {
		if (te.result.equals(node.name) && node.left == null && node.right == null) {
			return true;
		} else if (!te.result.equals(node.name) && node.left == null && node.right == null ) {
			return false;
		} else if (node.left != null && (te.conditions.contains(node.left.name) || te.result.equals(node.left.name))) {
			return test(te, node.left);
		} else if (node.right != null && (te.conditions.contains(node.right.name) || te.result.equals(node.right.name))) {
			return test(te, node.right);
		} else {
			return false;
		}
	}
}
