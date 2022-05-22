package Singleton;

public class SingletonFileSamples {
    private static String tableWithLabels;
    private static String tableNoLabels;
    private static final SingletonFileSamples Singleton = new SingletonFileSamples();
    private SingletonFileSamples(){
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
