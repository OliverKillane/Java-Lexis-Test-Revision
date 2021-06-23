package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;

public interface CompactWordsSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    if (word == null) {
      throw new InvalidWordException("word is null");
    } else if (word.length() == 0) {
      throw new InvalidWordException("word is empty");
    } else {
      if (word.chars().allMatch((letter) -> 'a' <= letter && letter <= 'z')) {
        throw new InvalidWordException("Invalid character in word");
      }
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();

}
