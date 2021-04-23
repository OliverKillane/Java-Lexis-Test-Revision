package filesystems;

import java.util.Arrays;

final public class DocDataFile extends DocFile {

  private final byte[] contents;

  /**
   * Constructor for docdatafile. Takes name of the file
   *
   * @param name The name of the file.
   */
  DocDataFile(String name, byte[] contents) {
    super(name);
    this.contents = contents;
  }

  @Override
  public int getSize() {
    return getName().length() + contents.length;
  }

  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public boolean isDataFile() {
    return true;
  }

  @Override
  public DocDirectory asDirectory() {
    throw new UnsupportedOperationException();
  }

  @Override
  public DocDataFile asDataFile() {
    return this;
  }

  @Override
  public DocFile duplicate() {

    // create duplicate array for use in construction
    byte[] duplicateContents = new byte[contents.length];
    System.arraycopy(contents, 0, duplicateContents, 0, contents.length);

    return new DocDataFile(getName(), duplicateContents);
  }

  public boolean containsByte(byte data) {
    for (byte content : contents) {
      if (content == data) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof DocDataFile) {
      DocDataFile otherfile = (DocDataFile) other;

      return otherfile.getName().equals(getName()) && Arrays.equals(contents, otherfile.contents);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int filehash = 0;

    for (int index = 0; index < contents.length; index++) {
      filehash += (index + 1) * contents[index];
    }

    return getName().hashCode() * filehash;
  }
}
