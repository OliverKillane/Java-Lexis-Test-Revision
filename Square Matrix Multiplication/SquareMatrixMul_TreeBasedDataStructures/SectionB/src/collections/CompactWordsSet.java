package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;

public interface CompactWordsSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    if (word == null){
      throw new InvalidWordException("Word is null");
    } else if (word.length() == 0){
      throw new InvalidWordException("Word is empty");
    } else if (word.chars().anyMatch(letter -> letter > 'z' || letter < 'a')){
      throw new InvalidWordException("Word contains invalid characters");
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();

}
