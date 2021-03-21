package generalmatrices.examples;

import generalmatrices.matrix.Matrix;
import generalmatrices.pair.PairWithOperators;
import java.util.List;

public class Example {

  public static Matrix<PairWithOperators> multiplyPairMatrices(
        List<Matrix<PairWithOperators>> matrices) {
    // TODO: implement as part of Question 4

    Matrix<PairWithOperators> result = matrices.get(0);
    for (int matrixNo = 1; matrixNo < matrices.size(); matrixNo++){
      result = result.product(matrices.get(matrixNo), PairWithOperators::sum, PairWithOperators::product);
    }

    return result;
  }

}
