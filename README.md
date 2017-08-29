# DecisionTree

## Compiling Java files using Eclipse IDE

1. Download this repository as ZIP
2. Create new `Java Project` in `Eclipse`
3. Right click on your `Java Project` --> `Import`
4. Choose `General` --> `Archive File`
5. Put directory where you downloaded ZIP in `From archive file`
6. Put `ProjectName/src` in `Into folder`
7. Click `Finish`

## Running the program

1. Right click on your `Java Project` --> `Run As` --> `Java Application`
2. Program will ask for directory of where the text files are
3. Choose `data/hepa` directory
4. Program will look for `hepatitis-training.dat`, and `hepatitis-test.dat` inside that chosen folder

## Notes

- You can change the files the program reads/looks for by changing:
  - `private static final String TRAINSET_FILENAME = "hepatitis-training.dat";`
  and
  -	`private static final String TESTSET_FILENAME = "hepatitis-test.dat";`

  ...to the respective file names in `data/golf` or `data/hepa` directory

- Click <a href='https://github.com/rjperez94/DecisionTree/blob/master/algorithm.gif'>here</a> for the algorithm used

## About the Data File

The original data in `data/hepa` describes 137 cases of patients with hepatitis, along with the outcome. Each case is specified by 16 Boolean attributes describing the patient and the results of various tests. The original file used in `data/hepa` contained all the 137 cases while the `training` file contains 110 of those cases (chosen at random) and the `testing` file contains the remaining 27 cases

The data files are formatted as tab-separated text files, containing two header lines, followed by a line for each instance

- The first line contains the names of the two classes
- The second line contains the names of the attributes
- Each instance line contains the class name followed by the values of the attributes (“true” or “false”)

This data set is taken from the UCI Machine Learning Repository <a href='http://mlearn.ics.uci.edu/MLRepository.html'>here</a>

It consists of data about the prognosis of patients with hepatitis. This version has been simplified by removing some of the numerical attributes, and converting others to booleans by an arbitrary division of the range

The files in `data/golf` is a smaller data set in the same format that is useful <strong>ONLY</strong> for testing. Each instance describes the weather conditions that made a golf player either: decide to play golf OR to stay at home. This data set is not large enough to do any useful evaluation
