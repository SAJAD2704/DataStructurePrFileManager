import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Phase1
{
    public void folderExtract(File root) throws IOException
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
    public void folderExtract(File root,File folder) throws IOException
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
