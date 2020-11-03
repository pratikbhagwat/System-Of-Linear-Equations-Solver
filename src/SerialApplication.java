import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerialApplication {
    public static void main(String[] args) throws IOException{
        System.out.println( "Time Taken : " + doTheWork(args) + " seconds" );
    }

    public static long  doTheWork(String[] args) throws IOException {
        ArrayList<ArrayList<Double>> matrixArrayList = getMatrixFromInput(args);
        ArrayList<Double> vectorArrayList = getVertorFromInput(args);
        ArrayList<Double> answerArraylist = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int columnIndex = 0;columnIndex<matrixArrayList.size()-1;columnIndex++){
            ArrayList<Thread> listOfSpawnedThreads = new ArrayList<>();
            for (int rowIndex =columnIndex+ 1;rowIndex<matrixArrayList.size();rowIndex++){
                listOfSpawnedThreads.add(new Thread(new ParallelComponent(matrixArrayList,vectorArrayList,rowIndex,columnIndex)));
            }
            listOfSpawnedThreads.forEach((Thread::run));
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
}

