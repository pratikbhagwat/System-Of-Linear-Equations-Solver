import java.util.ArrayList;

public class ParallelComponent_NGreaterThanP implements Runnable {
    private ArrayList<ArrayList<Double>> matrixArrayList;
    private ArrayList<Double> vectorArrayList;
    private int rowToBeChangedStart ;
    private int rowToBeChangedEnd ;
    private int columnToTurnIntoZeros;
    public ParallelComponent_NGreaterThanP(ArrayList<ArrayList<Double>> matrixArrayList,ArrayList<Double> vectorArrayList,int rowToBeChangedStart,int rowToBeChangedEnd,int columnToTurnIntoZeros){
        this.matrixArrayList = matrixArrayList;
        this.vectorArrayList = vectorArrayList;
        this.rowToBeChangedStart = rowToBeChangedStart;
        this.rowToBeChangedEnd = rowToBeChangedEnd;
        this.columnToTurnIntoZeros = columnToTurnIntoZeros;
    }
    @Override
    public void run() {

        for (int rowNumberToBeChanged = this.rowToBeChangedStart ; rowNumberToBeChanged< this.rowToBeChangedEnd;rowNumberToBeChanged++){
            ArrayList<Double> rowToBeChanged = this.matrixArrayList.get(rowNumberToBeChanged);
            double multiplyingFactor = (this.matrixArrayList.get(rowNumberToBeChanged).get(this.columnToTurnIntoZeros) / this.matrixArrayList.get(this.columnToTurnIntoZeros).get(this.columnToTurnIntoZeros));
            for (int index = this.columnToTurnIntoZeros;index<rowToBeChanged.size();index++){
                rowToBeChanged.set(index,(double)Math.round( (this.matrixArrayList.get(rowNumberToBeChanged).get(index) -  this.matrixArrayList.get(columnToTurnIntoZeros).get(index) * multiplyingFactor)*1000d)/1000d) ;
            }
            this.vectorArrayList.set(rowNumberToBeChanged, Math.round((this.vectorArrayList.get(rowNumberToBeChanged) - this.vectorArrayList.get(columnToTurnIntoZeros)*multiplyingFactor)*1000d)/1000d );
        }
    }
}
