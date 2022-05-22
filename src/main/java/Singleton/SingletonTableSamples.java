package Singleton;

public class SingletonTableSamples {
    private static String tableWithLabels;
    private static String tableNoLabels;
    private static final SingletonTableSamples Singleton = new SingletonTableSamples();
    private SingletonTableSamples(){
        String sep = System.getProperty("file.separator");
        tableWithLabels = "data"+sep+ "iris.csv";
        tableNoLabels = "data"+sep+"miles_dollars.csv";
    }
    public static String getTableWithLabelsFile(){
        return tableWithLabels;
    }
    public static String getTableFile(){
        return tableNoLabels;
    }
}
