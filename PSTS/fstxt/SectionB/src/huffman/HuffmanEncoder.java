package huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class HuffmanEncoder {

  final HuffmanNode root;
  final Map<String, String> word2bitsequence;

  private HuffmanEncoder(HuffmanNode root,
      Map<String, String> word2bitSequence) {
    this.root = root;
    this.word2bitsequence = word2bitSequence;
  }

  public static HuffmanEncoder buildEncoder(Map<String, Integer> wordCounts) {
    //TODO: complete the implementation of this method (Q1)

    if (wordCounts == null) {
      throw new HuffmanEncoderException("wordCounts cannot be null");
    }
    if (wordCounts.size() < 2) {
      throw new HuffmanEncoderException("This encoder requires at least two different words");
    }

    // fixing the order in which words will be processed: this determinize the execution and makes
    // tests reproducible.
    TreeMap<String, Integer> sortedWords = new TreeMap<String,Integer>(wordCounts);
    PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(sortedWords.size());

    // create HuffmanLeaf objects for every word, add then to the queue
    for (String word : sortedWords.keySet()) {
      queue.offer(new HuffmanLeaf(wordCounts.get(word), word));
    }

    HuffmanNode lowest;
    HuffmanNode secondLowest;

    // constuct the huffman tree
    while (queue.size() > 1) {
      lowest = queue.poll();
      secondLowest = queue.poll();
      queue.offer(new HuffmanInternalNode(lowest, secondLowest));
    }

    HuffmanNode root = queue.poll();


    var a  = getCodes(root);

    return new HuffmanEncoder(root, getCodes(root));
  }

  private static Map<String, String> getCodes(HuffmanNode node) {
    if (node.isLeaf()) {
      HuffmanLeaf leaf = node.asLeaf();

      return new HashMap<>(){{put(leaf.getWord(), "");}};
    } else {
     HuffmanInternalNode internal = node.asInternal();

     Map<String, String> leftMap = getCodes(internal.getLeft());
     Map<String, String> rightMap = getCodes(internal.getRight());

     // for left we append a 1 and for right a 0
     leftMap.replaceAll((k,v) -> "0" + v);
     rightMap.replaceAll((k,v) -> "1" + v);

     // merge the maps together
     leftMap.putAll(rightMap);

     return leftMap;
    }
  }


  public String compress(List<String> text) {
    assert text != null && text.size() > 0;

    String result = "";


    // not done with stream as it gets messy with exceptions.
    for (String word : text) {
      if (word2bitsequence.containsKey(word)) {
        result += word2bitsequence.get(word);
      } else {
        throw new HuffmanEncoderException();
      }
    }

    return result;
  }

  // PRE: compressedText consists only of '0' and '1'.
  public List<String> decompress(String compressedText) {
    assert compressedText != null && compressedText.length() > 0;

    List<String> result = new ArrayList<>();
    HuffmanNode current = root;

    // iterate through each character in the string
    for (char digit : compressedText.toCharArray()) {

      // regardless go to successor node based on current digit
      current = digit == '0' ? current.asInternal().getLeft() : current.asInternal().getRight();

      // if a leaf has been reached, add the word to the list, reset current to root.
      if (current.isLeaf()) {
        result.add(current.asLeaf().getWord());
        current = root;
      }
    }

    if (current != root) {
      throw new HuffmanEncoderException();
    }

    return result;
  }


  // Below the classes representing the tree's nodes. There should be no need to modify them, but
  // feel free to do it if you see it fit

  private static abstract class HuffmanNode implements Comparable<HuffmanNode> {

    private final int count;

    public HuffmanNode(int count) {
      this.count = count;
    }

    @Override
    public int compareTo(HuffmanNode otherNode) {
      return count - otherNode.count;
    }

    public int getCount() {
      return count;
    }

    public abstract boolean isLeaf();

    public abstract HuffmanLeaf asLeaf();

    public abstract HuffmanInternalNode asInternal();
  }


  private static class HuffmanLeaf extends HuffmanNode {

    private final String word;

    public HuffmanLeaf(int frequency, String word) {
      super(frequency);
      this.word = word;
    }

    public String getWord() {
      return word;
    }

    @Override
    public boolean isLeaf() {
      return true;
    }

    @Override
    public HuffmanLeaf asLeaf() {
      return this;
    }

    @Override
    public HuffmanInternalNode asInternal() {
      throw new UnsupportedOperationException();
    }
  }


  private static class HuffmanInternalNode extends HuffmanNode {

    private final HuffmanNode left;
    private final HuffmanNode right;

    public HuffmanInternalNode(HuffmanNode left, HuffmanNode right) {
      super(left.count + right.count);
      this.left = left;
      this.right = right;
    }

    public HuffmanNode getLeft() {
      return left;
    }

    public HuffmanNode getRight() {
      return right;
    }

    @Override
    public boolean isLeaf() {
      return false;
    }

    @Override
    public HuffmanLeaf asLeaf() {
      throw new UnsupportedOperationException();
    }

    @Override
    public HuffmanInternalNode asInternal() {
      return this;
    }
  }
}
