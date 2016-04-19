package calculatorClient;

public interface CalculatorInterface {

  public abstract Complex add(final Complex x, final Complex y);

  public abstract Complex sub(final Complex x, final Complex y);

  public abstract Complex multiply(final Complex x, final Complex y);

  public abstract Complex divide(final Complex x, final Complex y);
}
