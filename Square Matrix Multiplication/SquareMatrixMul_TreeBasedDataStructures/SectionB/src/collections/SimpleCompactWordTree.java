package collections;

import collections.exceptions.InvalidWordException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.Renderer;

public class SimpleCompactWordTree implements CompactWordsSet {

  // size stored as atomic integer
  private final AtomicInteger size = new AtomicInteger(0);

  // set root to contain empty
  private final WordTreeNode root = new WordTreeNode((char) 0);


  @Override
  public boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);

    WordTreeNode prev;
    WordTreeNode current = root;
    current.lock();

    char currentChar;

    for (int index = 0; index < word.length(); index++){
      prev = current;
      currentChar = word.charAt(index);
      current = current.getChild(currentChar);

      if (current == null){
        current = new WordTreeNode(currentChar);
        prev.setChild(currentChar, current);
      }

      current.lock();
      prev.unlock();
    }

    if (current.isWord()){
      current.unlock();
      return false;
    } else {
      current.setIsWord();
      size.incrementAndGet();
      current.unlock();

      return true;
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);

    WordTreeNode node = findNode(word);
    if (node == null || !node.isWord()){
      return false;
    } else {
      node.setNotWord();
      size.decrementAndGet();
      return true;
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);

    // no need for synchronisation, as not changing structure, only going to nodes.

    WordTreeNode node = findNode(word);

    return node != null && node.isWord();
  }

  private WordTreeNode findNode(String word){
    char currentChar;
    WordTreeNode current = root;

    for (int index = 0; index < word.length(); index++){
      currentChar = word.charAt(index);
      current = current.getChild(currentChar);
      if (current == null){
        return null;
      }
    }

    return current;
  }


  @Override
  public int size() {
    return size.get();
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    List<String> Bucket = new ArrayList<>();
    uniqueWordsInAlphabeticOrderHelper(root, "", Bucket);

    return Bucket;
  }

  private void uniqueWordsInAlphabeticOrderHelper(WordTreeNode node, String path, List<String> Bucket){
    if (node.isWord()){
      Bucket.add(path);
    }

    node.getChildrenStream().forEach(child -> {
      uniqueWordsInAlphabeticOrderHelper(child, path + child.getLetter(), Bucket);
    });
  }
}

class WordTreeNode{
  private static final int ASCII_BASE_VALUE = 97;

  private final Lock lock = new ReentrantLock();
  private WordTreeNode[] children = new WordTreeNode[26];
  private AtomicBoolean isWord = new AtomicBoolean(false);
  private final char letter;

  public WordTreeNode(char letter){
    this.letter = letter;
  }

  public char getLetter() {
    return letter;
  }

  // for synchronization
  public void lock(){
    lock.lock();
  }

  public void unlock(){
    lock.unlock();
  }

  public WordTreeNode getChild(char child) {

    // assumes it a valid char
    return children[child - ASCII_BASE_VALUE];
  }

  public void setChild(char child, WordTreeNode childNode){
    children[child - ASCII_BASE_VALUE] = childNode;
  }

  public void setIsWord(){
    isWord.compareAndSet(isWord.get(), true);
  }

  public void setNotWord(){
    isWord.compareAndSet(isWord.get(), false);
  }

  public boolean isWord(){
    return isWord.get();
  }

  public Stream<WordTreeNode> getChildrenStream(){
    return Arrays.stream(children).filter(Objects::nonNull);
  }
}

// DONE: 58 min left, passes all tests, little unsure about having locks on contains, unique order
// and remove