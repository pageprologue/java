package study.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Spring Zipping and Unzipping in Java - https://www.baeldung.com/java-compress-and-uncompress
 * JAVA 11 API - https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/zip/ZipFile.html
 * Example codota - https://www.codota.com/code/java/methods/java.util.zip.ZipFile/getInputStream
 */
public class ZipFile {

    public static final int BUFFER_SIZE = 2048;

    public void getFiles(String in, String out) throws IOException {
        File rootDir = new File(out);
        if(!rootDir.exists()) {
            rootDir.mkdir();
        }

        File destDir = new File(in);
        List<String> subDir = new ArrayList<>();
        if(destDir.isDirectory()) {
            File[] fileList = destDir.listFiles();
            for(File file : fileList) {
                String fileName = out + File.separator + file.getName().replace(".zip", "");
                File newDir = new File(fileName);
                if(!newDir.exists()) {
                    newDir.mkdir();
                }
                unzip(file, fileName);
                subDir.add(fileName);
            }
        }
        
        // 하위 zip 파일, 이미지 복사
        for(int i = 0; i < subDir.size(); i++) {
            File subFile = new File(subDir.get(i));
            File[] subFileList = subFile.listFiles();
            for(File file : subFileList) {
                if(!file.isDirectory()) {
                    String fileName = subFile.getPath() + File.separator + file.getName().replace(".zip", "");
                    File newDir = new File(fileName);
                    if(!newDir.exists()) {
                        newDir.mkdir();
                    }
                    unzip(file, fileName);
                }
            }
        }

        // zip 파일 삭제
        for(int i = 0; i < subDir.size(); i++) {
            File subFile = new File(subDir.get(i));
            File[] subFileList = subFile.listFiles();
            for(File file : subFileList) {
                if(file.getName().contains(".zip")) {
                    file.delete();
                }
            }
        }
    }

    private void unzip(File file, String fileName) throws IOException {
        if(file.getName().contains("zip")) {
            File unzipDir = new File(fileName.replace(".zip", ""));
            if(!unzipDir.exists()) {
                unzipDir.mkdir();
            }
            
            byte[] buffer = new byte[BUFFER_SIZE];
            try( ZipInputStream zis = new ZipInputStream(new FileInputStream(file.getPath())) ) {
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    File newFile = newFile(unzipDir, zipEntry); 
                    if(zipEntry.getName().contains(".zip")) {
                        // unzip
                        try(FileOutputStream fos = new FileOutputStream(newFile)) {
                            int len;
                            while ((len = zis.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                            zipEntry = zis.getNextEntry();
                        }
                    } else {
                        String dir = newFile.getPath().replace(newFile.getName(), "");
                        File subDir = new File(dir);
                        if(!subDir.exists()) {
                            subDir.mkdirs();
                        }
                        try(FileOutputStream fos = new FileOutputStream(newFile)) {
                            zipEntry = zis.getNextEntry();
                        }
                    }
                }
            }
        }
    }  

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        String target = zipEntry.getName();
        File destFile = new File(destinationDir, target);
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("@@@@@@@@@@ Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }
    
}
