package filesystems;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DocDirectory extends DocFile {

  private final Set<DocFile> contents = new HashSet<>();

  /**
   * Construct a directory with the given name.
   *
   * @param name The name of the directory.
   */
  DocDirectory(String name) {
    super(name);
  }

  @Override
  public int getSize() {
    return getName().length();
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public boolean isDataFile() {
    return false;
  }

  @Override
  public DocDirectory asDirectory() {
    return this;
  }

  @Override
  public DocDataFile asDataFile() {
    throw new UnsupportedOperationException();
  }

  @Override
  public DocFile duplicate() {

    // create new directory
    DocDirectory duplicateDirectory = new DocDirectory(getName());

    for (DocFile file : contents) {
      duplicateDirectory.addFile(file.duplicate());
    }

    return duplicateDirectory;
  }

  public boolean containsFile(String name) {
    return contents.stream().anyMatch((file) -> file.getName() == name);
  }

  public Set<DocFile> getAllFiles() {
    return contents;
  }

  public Set<DocDirectory> getDirectories() {
    return contents.stream().filter(DocFile::isDirectory).map(DocFile::asDirectory)
        .collect(Collectors.toSet());
  }

  public Set<DocDataFile> getDataFiles() {
    return contents.stream().filter(DocFile::isDataFile).map(DocFile::asDataFile)
        .collect(Collectors.toSet());
  }

  public void addFile(DocFile file) {
    if (contents.stream().anyMatch((otherfile) -> otherfile.getName() == file.getName())) {
      throw new IllegalArgumentException();
    } else {
      contents.add(file);
    }
  }

  public boolean removeFile(String filename) {
    for (DocFile file : contents) {
      if (file.getName().equals(filename)) {
        contents.remove(file);
        return true;
      }
    }
    return false;
  }

  // PRE: file with name (filename) is in the directory
  public DocFile getFile(String filename) {
    return contents.stream().filter((file) -> file.getName().equals(filename)).findFirst().get();
  }
}
