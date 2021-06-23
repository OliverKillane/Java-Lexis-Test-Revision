package generalmatrices.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix<T> {

  private final T[][] elements;
  private final int order;

  public Matrix(List<T> data) {
    if (data.size() == 0) {
      throw new IllegalArgumentException();
    } else {

      // get the size
      order = (int) Math.sqrt(data.size());

      // initialise the array
      elements = (T[][]) new Object[order][order];

      // copy rows into 2D array
      for (int row = 0; row < order; row++) {
        data.subList(row * order, (row + 1) * order).toArray(elements[row]);
      }
    }
  }

  // PRE: Valid row and column.
  public T get(int row, int col) {
    return elements[row][col];
  }

  public int getOrder() {
    return order;
  }

  public String toString() {
    String result = "[";

    // iterate through all rows
    for (T[] row : elements) {
      result += "[";

      // iterate through all but the last element of the row, adding spaces
      for (int elementNo = 0; elementNo < getOrder() - 1; elementNo++) {
        result += row[elementNo] + " ";
      }

      // add last element without added space
      result += row[getOrder() - 1] + "]";
    }

    return result + "]";
  }

  public Matrix<T> sum(Matrix<T> other, BinaryOperator<T> elementSum) {
    if (getOrder() != other.getOrder()) {
      throw new IllegalArgumentException("matrix Orders do not match");
    } else {
      return new Matrix<>(
          IntStream
              .range(0, order * order)
              .mapToObj(
                  (index) -> elementSum
                      .apply(
                          get(index / getOrder(), index % getOrder()),
                          other.get(index / getOrder(), index % getOrder())
                      )
              )
              .collect(
                  Collectors.toList()
              )
      );
    }
  }

  public Matrix<T> product(Matrix<T> other, BinaryOperator<T> elementSum,
      BinaryOperator<T> elementProduct) {
    if (getOrder() != other.getOrder()) {
      throw new IllegalArgumentException("matrix Orders do not match");
    } else {

      // create new array for the construtor of Matrix
      List<T> prodList = new ArrayList<>();

      for (int row = 0; row < getOrder(); row++) {
        for (int col = 0; col < getOrder(); col++) {

          // get the first element
          T sum = elementProduct.apply(get(row, 0), other.get(0, col));

          // multiply and add the next elements
          for (int summand = 1; summand < getOrder(); summand++) {
            sum = elementSum
                .apply(sum, elementProduct.apply(get(row, summand), other.get(summand, col)));
          }

          // place inside the array
          prodList.add(sum);
        }
      }

      return new Matrix<>(prodList);
    }
  }

  // TODO: populate as part of Question 1 and Question 3

}
