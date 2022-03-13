import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Kmeans implements Algorithm<Table,Double,Double>{
    public Kmeans(int numberClusters, int iterations, long seed){
        this.numberClusters=numberClusters;
        this.iterations=iterations;
        this.seed=seed;
    }
    private int numberClusters;
    private int iterations;
    private long seed;

    public void train(Table t){
        List<Row> representantes = new LinkedList<Row>();
        Random random=new Random(seed);
        for(int i=0;i<numberClusters;i++){
            int indice=random.nextInt();
            Row nuevafila=t.getRowAt(indice);

            representantes.add(nuevafila);
        }
    }

    public Double estimate(Double d){
        return 0.0;
    };


}
