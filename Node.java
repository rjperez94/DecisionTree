public class Node {
	public String name;
	public Node left;
	public Node right;
	public double prob;

	public Node(String name, Node left, Node right, double prob) {
		this.name = name;
		this.left = left;
		this.right = right;
		this.prob = prob;
	}

	public void toString(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append("     ");
		}
		if (left != null || right != null) {
			if (left != null) {
				System.out.println(sb + name + " = True");
				left.toString(depth + 1);
			}
			if (right != null) {
				System.out.println(sb + name + " = False");
				right.toString(depth + 1);
			}
		} else {
			System.out.println(sb + "Class " + name + ", prob = " + prob);
		}
	}
}
