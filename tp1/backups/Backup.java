import java.io.IOException;
import java.nio.file.*;

public class Backup {

	public static void main(String[] args) {

		String src = System.getProperty("user.dir") + System.getProperty("file.separator") + "Backup" + ".java";
		String dest = System.getProperty("user.dir") + System.getProperty("file.separator") + "backups" + System.getProperty("file.separator") + "Backup" + ".java";

		FileSystem fileSystem = FileSystems.getDefault();
		Path src_path = fileSystem.getPath(src);
		Path dest_path = fileSystem.getPath(dest);

		try {
			Files.copy(src_path, dest_path);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
