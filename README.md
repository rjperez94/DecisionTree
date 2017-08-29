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
3. Choose `data/{data-type}` directory
4. Program will look for `hepatitis-training.dat`, and `hepatitis-test.dat` inside that chosen folder

## Notes

You can change the files the program reads/looks for by changing:
- `private static final String TRAINSET_FILENAME = "hepatitis-training.dat";`
and
-	`private static final String TESTSET_FILENAME = "hepatitis-test.dat";`

...to the respective file names in `data/golf` or `data/hepa` directory
