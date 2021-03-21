package generalmatrices.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Matrix<T> {
  private final T[][] elems;
  private final int order;


  public Matrix(List<T> elems){
    if (elems.size() == 0){
      throw new IllegalArgumentException();
    } else {
      order = (int) Math.sqrt(elems.size());

      this.elems = (T[][]) new Object[order][order];

      for (int row = 0; row < order; row++){
        for (int col = 0; col < order; col++){
          this.elems[row][col] = elems.get(row * order + col);
        }
      }
    }
  }

  public T get(int row, int col){
    return elems[row][col];
  }

  public int getOrder(){
    return order;
  }

  public String toString(){
    StringBuilder result = new StringBuilder("[");

    for (int row = 0; row < order; row++){
      result.append("[");
      for (int col = 0; col < order - 1; col++){
        result.append(elems[row][col].toString()).append(" ");
      }
      result.append(elems[row][order - 1].toString()).append("]");
    }

    result.append("]");
    return result.toString();
  }

  public Matrix<T> sum(Matrix<T> other, BinaryOperator<T> elementSum){
    ArrayList<T> sumElems = new ArrayList<>();

    for (int row = 0; row < order; row++){
      for (int col = 0; col < order; col++){
        sumElems.add(elementSum.apply(get(row, col), other.get(row, col)));
      }
    }


    return new Matrix<>(sumElems);
  }

  public Matrix<T> product(Matrix<T> other, BinaryOperator<T> elementSum,BinaryOperator<T> elementProduct){
    ArrayList<T> prodElems = new ArrayList<>();

    for (int row = 0; row < order; row++){
      for (int col = 0; col < order; col++){
        T prod = elementProduct.apply(get(row, 0), other.get(0, col));

        for (int at = 1; at < order; at++){
          prod = elementSum.apply(prod, elementProduct.apply(get(row, at), other.get(at, col)));
        }

        prodElems.add(prod);
      }
    }

    return new Matrix<>(prodElems);
  }


  // TODO: populate as part of Question 1 and Question 3

}
