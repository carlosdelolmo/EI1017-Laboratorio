package fileTools;

import fileTools.FileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTypeTest {

    @Test
    void setFileName() {
        FileType fileType = new FileType();
        fileType.setFileName("prueba.txt");
        assertEquals("txt", fileType.getExtension());
    }

    @Test
    void getFileName() {
        FileType fileType = new FileType("prueba2.h");
        assertEquals("prueba2.h", fileType.getFileName());
    }

    @Test
    void getExtension() {
        FileType fileType = new FileType("home/dir/otherDir/h.f.bat");
        assertEquals("bat", fileType.getExtension());
    }
}