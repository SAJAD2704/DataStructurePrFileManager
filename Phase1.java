import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Phase1
{
    public static void extractHereZip(String zipFile)
    {
        File file = new File(zipFile);
        String outputFolder = file.getPath().replaceAll(file.getName(),"");

        byte[] buffer = new byte[1024];

        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            file.delete();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void folderExtract(File root) throws IOException
    {
        for(String i : root.list())
        {
            File file = new File(root + i);
            if(file.isDirectory())
            {
                folderExtract(root,file);
            }
        }
    }
    public static void folderExtract(File root,File folder) throws IOException
    {
        for(String i : folder.list())
        {
            File file = new File(folder + i);
            if(file.isDirectory())
            {
                folderExtract(root,file);
            }
            else
            {
                cut(file.getPath(),root.getPath(), file.getName());
            }
        }
    }

    private static void cut(String filePath,String to,String fileName) throws IOException
    {

        Files.copy(Path.of(filePath), Path.of(to + fileName));
        File file = new File(filePath);
        if (file.delete())
        {
            throw new IOException("Error in Deleting Copied File!");
        }
    }
}
