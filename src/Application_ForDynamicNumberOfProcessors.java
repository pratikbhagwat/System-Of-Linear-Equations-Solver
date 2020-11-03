import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Application_ForDynamicNumberOfProcessors {
    public static void main(String[] args) throws IOException {
        System.out.println( "Time Taken : " + doTheWork(args) + " seconds" );
    }

    /**
     *
     * @param args : program arguments
     * @return time taken to get the answers
     * @throws IOException
     */
    public static long doTheWork(String[] args) throws IOException {


        ArrayList<ArrayList<Double>> matrixArrayList = getMatrixFromInput(args);
        int numberOfAvailableProcessors = Integer.parseInt( args[2] );
        ArrayList<Double> vectorArrayList = getVertorFromInput(args);
        ArrayList<Double> answerArraylist = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int columnIndex = 0;columnIndex<matrixArrayList.size()-1;columnIndex++){
            ArrayList<Thread> listOfSpawnedThreads = new ArrayList<>();
            int rowsToBeHandledByEachThread = 0;
            int remainingRowsToBeTransformed =  matrixArrayList.size() - columnIndex + 1;

            if (remainingRowsToBeTransformed <= numberOfAvailableProcessors){
                rowsToBeHandledByEachThread=1;
            }else {
                rowsToBeHandledByEachThread= remainingRowsToBeTransformed/numberOfAvailableProcessors;
            }

            for (int rowIndexStart = columnIndex+ 1;rowIndexStart<matrixArrayList.size();rowIndexStart+=rowsToBeHandledByEachThread){
                if (rowIndexStart+rowsToBeHandledByEachThread <= matrixArrayList.size()){
                    listOfSpawnedThreads.add(new Thread(new ParallelComponent_NGreaterThanP(matrixArrayList,vectorArrayList,rowIndexStart,rowIndexStart+rowsToBeHandledByEachThread,columnIndex)));
                }else {
                    listOfSpawnedThreads.add(new Thread(new ParallelComponent_NGreaterThanP(matrixArrayList,vectorArrayList,rowIndexStart,matrixArrayList.size(),columnIndex)));
                }
            }

            listOfSpawnedThreads.forEach((Thread::start));
            listOfSpawnedThreads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        for (int i = matrixArrayList.size()-1;i>-1;i--){
            double cumSum = 0;
            int storedAnswersIndex = 0;
            for (int j = matrixArrayList.size()-1;j>i;j--){
                cumSum += matrixArrayList.get(i).get(j)*answerArraylist.get(storedAnswersIndex);
                storedAnswersIndex+=1;
            }
            answerArraylist.add( (vectorArrayList.get(i)-cumSum)/matrixArrayList.get(i).get(i));
        }

        /**
         * Uncomment the below 2 lines to verify the input
         */
//        Collections.reverse(answerArraylist);
//        verifyAnswers(args,answerArraylist);
        long endTime = System.currentTimeMillis();

        return (endTime-startTime)/1000;
    }

    /**
     *
     * @param args : Program arguments
     * @return matrix in Arraylist Format
     * @throws IOException
     */
    public static ArrayList<ArrayList<Double>> getMatrixFromInput(String[] args) throws IOException {
        ArrayList<ArrayList<Double>> matrixArrayList = new ArrayList<>();
        List<String> matrix = Files.readAllLines(Paths.get(args[0]));
        for (String row : matrix){
            String[] rowArray = row.split(",");
            ArrayList<Double> rowArrayDouble = new ArrayList<>();
            for (String numberString : rowArray){

                rowArrayDouble.add(Double.parseDouble(numberString));

            }
            matrixArrayList.add(rowArrayDouble);
        }
        return matrixArrayList;
    }

    /**
     *
     * @param args: Program arguments
     * @return : Vector in arraylist format
     * @throws IOException
     */
    public static ArrayList<Double> getVertorFromInput(String[] args) throws IOException {
        List<String> vector = Files.readAllLines(Paths.get(args[1]));
        ArrayList<Double> vectorArrayList = new ArrayList<>();
        for (String numbers: vector){
            String[] vectorNumbers = numbers.split(";");
            for (String vectorNumber : vectorNumbers){
                vectorArrayList.add(Double.parseDouble(vectorNumber));
            }
        }
        return vectorArrayList;
    }

    /**
     *
     * @param args: Program arguments
     * @param answerArraylist : answers generated from the program.
     * @throws IOException
     * Description: Call this method to test the answers which you get are correct or not.
     * Note: The answers may differ at ending decimal places.
     */
    public static void verifyAnswers(String[] args,ArrayList<Double>answerArraylist) throws IOException {
        ArrayList<ArrayList<Double>> matrixArrayListTest = getMatrixFromInput(args);
        ArrayList<Double> vectorArrayListTest = getVertorFromInput(args);
        ArrayList<Double> generatedVector = new ArrayList<>();
        for (int i = 0;i<matrixArrayListTest.size();i++){
            double cumsum = 0;
            for (int j = 0;j<matrixArrayListTest.get(0).size();j++){
                cumsum+= matrixArrayListTest.get(i).get(j)*answerArraylist.get(j);
            }
            generatedVector.add(cumsum);
        }
        System.out.println("Generated Answer Vector : "+ generatedVector);
        System.out.println("Original Answer Vector : " + vectorArrayListTest);
    }
}
