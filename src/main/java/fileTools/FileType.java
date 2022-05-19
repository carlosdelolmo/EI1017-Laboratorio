package fileTools;

public class FileType {
    private String filename;
    public FileType(String filename){
        this.filename = filename;
    }
    public FileType(){}
    public void setFileName(String filename){
        this.filename = filename;
    }
    public String getFileName(){
        return filename;
    }
    public String getExtension(){
        String fileType = "";
        int i = filename.lastIndexOf('.');
        fileType = filename.substring(i+1,filename.length());
        return fileType;
    }
}
