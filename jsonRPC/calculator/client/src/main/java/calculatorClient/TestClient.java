package calculatorClient;

import java.net.URL;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

public class TestClient {
  public static void main(final String[] args) throws Throwable {
    CalculatorInterface calculator;
    JsonRpcHttpClient client;
    Complex m, n, res;

    client = new JsonRpcHttpClient(
        new URL("http://localhost:8080/calculatorJSONServer/Calculator"));

    calculator = ProxyUtil.createClientProxy(
        TestClient.class.getClassLoader(), CalculatorInterface.class,
        client);

    m = new Complex();
    m.setRealPart(3);
    m.setImaginaryPart(11);

    n = new Complex();
    n.setRealPart(5);
    n.setImaginaryPart(7);

    res = calculator.multiply(m, n);

    System.out.println(res.getRealPart());
    System.out.println(res.getImaginaryPart());
  }
}
