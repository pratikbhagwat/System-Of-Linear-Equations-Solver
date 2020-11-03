import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;


public class CallerProgram {
    /**
     *
     * @param args : numberOfTrials, matrixFile, vectorFile,
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        ArrayList<String> matrixFiles = new ArrayList<>(Arrays.asList("64.txt","256.txt","1024.txt","4096.txt"));// add "16384.txt" to test for 16384 size matrix
        ArrayList<String> vectorFiles = new ArrayList<>(Arrays.asList("64Vector.txt","256Vector.txt","1024Vector.txt","4096Vector.txt"));//add "16384Vector.txt" to test for 16384 size matrix
        int numberOfTrials = 1;// number of trials to run.
        ArrayList<Long> serialResults = new ArrayList<>();
        ArrayList<Long> nonOptimizedParallelResults = new ArrayList<>();
        ArrayList<Long> optimizedParallelResults = new ArrayList<>();

        for (int i = 0;i<matrixFiles.size();i++){

            long cumSum = 0;
            for (int j=0;j<numberOfTrials;j++){
                cumSum += SerialApplication.doTheWork(new String[]{matrixFiles.get(i),vectorFiles.get(i)});
            }
            serialResults.add( cumSum/numberOfTrials );

            cumSum = 0;
            for (int j=0;j<numberOfTrials;j++){
                cumSum += Application.doTheWork(new String[]{matrixFiles.get(i),vectorFiles.get(i)});
            }
            nonOptimizedParallelResults.add( cumSum/numberOfTrials );

            cumSum = 0;
            for (int j=0;j<numberOfTrials;j++){
                cumSum += Application_ForDynamicNumberOfProcessors.doTheWork(new String[]{matrixFiles.get(i),vectorFiles.get(i),"10"});
            }
            optimizedParallelResults.add( cumSum/numberOfTrials );
        }

        System.out.println(serialResults);
        System.out.println(nonOptimizedParallelResults);
        System.out.println(optimizedParallelResults);
    }
}
