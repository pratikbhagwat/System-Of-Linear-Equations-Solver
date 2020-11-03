import java.util.ArrayList;

public class ParallelComponent implements Runnable {
    private ArrayList<ArrayList<Double>> matrixArrayList;
    private ArrayList<Double> vectorArrayList;
    private int rowNumberToBeChanged;
    private int columnToTurnIntoZeros;
    public ParallelComponent(ArrayList<ArrayList<Double>> matrixArrayList,ArrayList<Double> vectorArrayList,int rowNumberToBeChanged,int columnToTurnIntoZeros){
        this.matrixArrayList = matrixArrayList;
        this.vectorArrayList = vectorArrayList;
        this.rowNumberToBeChanged = rowNumberToBeChanged;
        this.columnToTurnIntoZeros = columnToTurnIntoZeros;
    }
    @Override
    public void run() {
        ArrayList<Double> rowToBeChanged = this.matrixArrayList.get(this.rowNumberToBeChanged);
        double multiplyingFactor = (this.matrixArrayList.get(this.rowNumberToBeChanged).get(this.columnToTurnIntoZeros) / this.matrixArrayList.get(this.columnToTurnIntoZeros).get(this.columnToTurnIntoZeros));
        for (int index = this.columnToTurnIntoZeros;index<rowToBeChanged.size();index++){
            rowToBeChanged.set(index,(double)Math.round( (this.matrixArrayList.get(this.rowNumberToBeChanged).get(index) -  this.matrixArrayList.get(columnToTurnIntoZeros).get(index) * multiplyingFactor)*1000d)/1000d) ;
        }
        this.vectorArrayList.set(this.rowNumberToBeChanged, Math.round((this.vectorArrayList.get(rowNumberToBeChanged) - this.vectorArrayList.get(columnToTurnIntoZeros)*multiplyingFactor)*1000d)/1000d );
    }
}
