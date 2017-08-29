import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import part2.Instance;

public class DecisionTree {
	private static JFileChooser fileChooser = new JFileChooser();
	private static final String TRAINSET_FILENAME = "hepatitis-training.dat";
	private static final String TESTSET_FILENAME = "hepatitis-test.dat";
	
	private static ArrayList<String> categories = new ArrayList<>();
	private static ArrayList<String> attributes = new ArrayList<>();
	private static HashSet<Instance> instances = new HashSet<>();
	
	private static TestingSuite ts = new TestingSuite();
	
	public static void main (String [] args) {
		chooseDir();

		double prob1 = probability(categories.get(0), instances);
		double prob2 = probability(categories.get(1), instances);
		double bpProb = (prob1 > prob2) ? prob1 : prob2;
		BasePredictor bp = new BasePredictor(chooseName(instances), bpProb);
		Node root = buildTree(instances,attributes, bp);
		root.toString(0);
		
		int correct = 0;
		for(TestEntry te: ts.instances) {
			boolean r = ts.test(te, root); 
			if (r)	correct++;
			System.out.println("Actual "+te.result+" Correct? "+r+" CONDITIONS: "+te.printConds());
		}
		System.out.println("Learned classifier "+ts.instances.size()+" test instances, "+correct+" correct");
		double accuracy = ((double)correct/ts.instances.size())*100;
		System.out.println("Accuracy: "+accuracy+" %");
		
		//baseline classifier
		correct = 0;
		for(TestEntry te: ts.instances) {
			if (te.result.equals(bp.cat)) {
				correct++;
			}
		}
		
		System.out.println("Baseline classifier "+ts.instances.size()+" test instances, "+correct+" correct");
		accuracy = ((double)correct/ts.instances.size())*100;
		System.out.println("Accuracy: "+accuracy+" %");
	}
	
	private static Node buildTree(HashSet<Instance> instances, ArrayList<String> attributes, BasePredictor bp) {
		if (instances.isEmpty()) {
			return new Node(bp.cat, null, null, bp.prob);
		} else if (isPure(instances)) {
			return new Node(instances.iterator().next().category, null, null, 1);
		} else if (attributes.isEmpty()) {
			String name = chooseName(instances);
			return new Node(name, null, null, probability(name,instances));
		} else {
			HashSet<Instance> bestFalseIns =  new HashSet<>();
			HashSet<Instance> bestTrueIns =  new HashSet<>();
			double bestImpurity = Double.MAX_VALUE;
			String bestAtt = "";
			
			for (int i = 0 ; i <attributes.size(); i++) {
				HashSet<Instance> falseIns =  new HashSet<>();
				HashSet<Instance> trueIns =  new HashSet<>();
				for (Instance ins:instances) {
					if (ins.vals.get(i)) {
						trueIns.add(ins);
					} else {
						falseIns.add(ins);
					}
				}
				
				double ave = averageImpurity(trueIns, falseIns, i);
				if (ave < bestImpurity) {
					bestImpurity = ave;
					bestAtt = attributes.get(i);
					bestTrueIns = trueIns;
					bestFalseIns = falseIns;
				}
			}
			if(attributes.remove(bestAtt)) {
				Node left = buildTree(bestTrueIns, attributes, bp);
				Node right = buildTree(bestFalseIns, attributes, bp);
				return new Node(bestAtt, left, right, 1.0 - bestImpurity);
			} else {
				return null;
			}
		}
	}

	private static double averageImpurity(HashSet<Instance> trueIns, HashSet<Instance> falseIns, int it) {
		double totalIns = (double)trueIns.size()+falseIns.size();
		
		double probTrue = (double)trueIns.size()/totalIns;
		double probFalse = (double)falseIns.size()/totalIns;
		
		double impurityTrue = probability(categories.get(0), trueIns) * probability(categories.get(1), trueIns);
		double impurityFalse = probability(categories.get(0), falseIns) * probability(categories.get(1), falseIns);
		
		return (double)(probTrue*impurityTrue)+(double)(probFalse*impurityFalse);
	}

	private static boolean isPure(HashSet<Instance> instances) {
		String cat = "";
		boolean isSet = false;
		
		for (Instance ins: instances) {
			if (isSet) {
				if(!ins.category.equals(cat)) {
					return false;
				}
			} else {
				cat = ins.category;
				isSet = true;
			}
		}
		return true;
	}

	private static double probability(String target, HashSet<Instance> instances) {
		int sums = 0;
		for (Instance ins: instances) {
			if (ins.category.equals(target)) {
				sums++;
			}
		}
		return (double)sums/instances.size();
	}

	private static String chooseName(HashSet<Instance> instances) {
		HashMap <String, Integer> sums = new HashMap<>();
		for(Instance ins: instances) {
			if (sums.containsKey(ins.category)) {
				sums.put(ins.category, sums.get(ins.category)+1);
			} else {
				sums.put(ins.category, 1);
			}
		}
		
		String name = "";
		int highestCount=0;
		
		for (Entry<String, Integer> entry: sums.entrySet()) {
			if (entry.getValue() > highestCount) {
				name = entry.getKey();
				highestCount=entry.getValue();
			}
		}
		return name;
	}

	private static void chooseDir() {
		File train = null, test = null;

		// set up the file chooser
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setDialogTitle("Select input directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// run the file chooser and check the user didn't hit cancel
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// get the files in the selected directory and match them to
			// the files we need.
			File directory = fileChooser.getSelectedFile();
			File[] files = directory.listFiles();

			for (File f : files) {
				if (f.getName().equals(TRAINSET_FILENAME)) {
					train = f;
				} else if (f.getName().equals(TESTSET_FILENAME)) {
					test = f;
				}
			}

			// check none of the files are missing, and call the load
			// method in your code.
			if (train == null || test == null) {
				JOptionPane.showMessageDialog(null, "Directory does not contain correct files", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} else {
				readFile(train, test);
			}
		}
	}

	private static void readFile(File train, File test) {
		try {
			String line = null;
			BufferedReader data = new BufferedReader(new FileReader(train));
			
			String[] cats = data.readLine().split("\\s");
			for (String s:cats) {
				categories.add(s);
			}
			String[] atts = data.readLine().split("\\s+");
			for (String s:atts) {
				attributes.add(s);
			}
			
			System.out.println("Loading training.");
			while ((line = data.readLine()) != null) {
				String[] values = line.split("\\s+");
				instances.add(new Instance(values[0], values));
			}
			data.close();

			System.out.println("Loading test.");			
			data = new BufferedReader(new FileReader(test));
			//skip 2...assume same category and attr list
			data.readLine();
			data.readLine();
			
			while ((line = data.readLine()) != null) {
				String[] values = line.split("\\s+");
				print(values);
				TestEntry te = new TestEntry(values[0], values, attributes);
				ts.addEntry(te);
			}
			data.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void print(String[] values) {
		StringBuilder sb = new StringBuilder();
		for(String s: values) {
			sb.append("  "+s);
		}
	}
	
	
}
