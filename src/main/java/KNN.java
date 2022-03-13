import java.util.List;

public class KNN implements Algorithm<TableWithLabels,List<Double>,String>{
    private TableWithLabels tabla;
    // private List<Double> listaDistancias = new LinkedList<>();
    public KNN() {}
    public void train(TableWithLabels tablaAEstudiar){
        tabla = tablaAEstudiar;
    }

    public String estimate(List<Double> sample){
        if(sample.size() != tabla.getNumColumnas() - 1) return null;
        int posMinimo = indiceMinimo(sample); // Calcula la posición de la mínima distancia
        return tabla.getRowAt(posMinimo).getLabel();
    }

    private int indiceMinimo(List<Double> sample){
        Double minimo = null;
        int fila = 0;
        for(int i = 0; i < tabla.getNumFilas(); i++){
            List<Double> elementoFichero = tabla.getRowAt(i).getData();
            Double distancia = metricaEuclidea(sample, elementoFichero);
            // listaDistancias.add(distancia);
            if(minimo == null) minimo = distancia;
            else if(distancia < minimo){
                minimo = distancia;
                fila = i;
            }
        }
        return fila;
    }
    private Double metricaEuclidea(List<Double> nuevaMuestra, List<Double> elementoFichero){
        Double sumatorio = 0.0;
        for(int i = 0; i < nuevaMuestra.size(); i++){
            Double resta = (nuevaMuestra.get(i) - elementoFichero.get(i));
            resta = resta * resta;
            sumatorio += resta;
        }
        return (Double) Math.sqrt(sumatorio);
    }
}
