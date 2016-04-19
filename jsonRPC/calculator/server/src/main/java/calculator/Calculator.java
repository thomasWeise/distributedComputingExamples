package calculator;

public class Calculator implements CalculatorInterface {
  public Calculator() {
    super();
  }

  @Override
  public Complex add(final Complex x, final Complex y) {
    return new Complex(//
        (x.getRealPart() + y.getRealPart()), //
        (x.getImaginaryPart() + y.getImaginaryPart()));
  }

  @Override
  public Complex sub(final Complex x, final Complex y) {
    return new Complex(//
        (x.getRealPart() - y.getRealPart()), //
        (x.getImaginaryPart() - y.getImaginaryPart()));
  }

  @Override
  public Complex multiply(final Complex x, final Complex y) {
    final double a1 = x.getRealPart();
    final double b1 = x.getImaginaryPart();
    final double a2 = y.getRealPart();
    final double b2 = y.getImaginaryPart();

    return new Complex(//
        ((a1 * a2) - (b1 * b2)), //
        ((a1 * b2) + (b1 * a2)));
  }

  @Override
  public Complex divide(final Complex x, final Complex y) {
    final double a1 = x.getRealPart();
    final double b1 = x.getImaginaryPart();
    final double a2 = y.getRealPart();
    final double b2 = y.getImaginaryPart();

    return new Complex(//
        (((a1 * a2) + (b1 * b2)) / ((a2 * a2) + (b2 * b2))), //
        (((a2 * b1) - (b2 * a1)) / ((a2 * a2) + (b2 * b2))));
  }
}
