package calculatorClient;

import calculatorClient.CalculatorStub.Complex;
import calculatorClient.CalculatorStub.Multiply;

public class TestClient {
  public static void main(String[] args) throws Throwable {
    Complex m, n, res;

    CalculatorStub stub = new CalculatorStub();
    Multiply mul = new CalculatorStub.Multiply();

    m = new Complex();
    m.setRealPart(10);
    m.setImaginaryPart(10);

    n = new Complex();
    n.setRealPart(10);
    n.setImaginaryPart(10);

    mul.setX(m);
    mul.setY(n);
    res = stub.multiply(mul).get_return();

    System.out.println(res.getRealPart());
    System.out.println(res.getImaginaryPart());
  }
}
