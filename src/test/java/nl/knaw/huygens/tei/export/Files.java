package nl.knaw.huygens.tei.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Files {

  /** Default encoding for text files. */
  public static final String ENCODING = "UTF-8";

  /**
   * Returns a <code>File</code> with the specfied name in the user's home directory.
   * This may be a file or a directory; it may exist or not.
   */
  public static File fileInHomeDirectory(String filename) {
    return new File(System.getProperty("user.home"), filename);
  }

  public static void writeTextToFile(String text, File file, boolean append) {
    FileOutputStream stream = null;
    try {
      stream = new FileOutputStream(file, append);
      IOUtils.write(text, stream, ENCODING);
    } catch (IOException e) {
      System.err.println(">> " + e.getMessage());
      IOUtils.closeQuietly(stream);
    }
  }

  public static void writeTextToFile(String text, File file) {
    writeTextToFile(text, file, false);
  }

  public static void writeTextToFile(String text, String filename) {
    writeTextToFile(text, new File(filename));
  }

  public static String readTextFromFile(File file) {
    try {
      return FileUtils.readFileToString(file, ENCODING);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read from " + file.getAbsolutePath());
    }
  }

  private Files() {
    throw new AssertionError("Non-instantiable class");
  }

}
