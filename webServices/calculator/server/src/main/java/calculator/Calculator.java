package calculator;

public class Calculator {
  public Calculator() {
    super();
  }

  public Complex add(final Complex x, final Complex y) {
    return new Complex(//
        (x.getRealPart() + y.getRealPart()),//
        (x.getImaginaryPart() + y.getImaginaryPart()));
  }

  public Complex sub(final Complex x, final Complex y) {
    return new Complex(//
        (x.getRealPart() - y.getRealPart()),//
        (x.getImaginaryPart() - y.getImaginaryPart()));
  }

  public Complex multiply(final Complex x, final Complex y) {
    double a1 = x.getRealPart();
    double b1 = x.getImaginaryPart();
    double a2 = y.getRealPart();
    double b2 = y.getImaginaryPart();

    return new Complex(//
        ((a1 * a2) - (b1 * b2)),//
        ((a1 * b2) + (b1 * a2)));
  }

  public Complex divide(final Complex x, final Complex y) {
    double a1 = x.getRealPart();
    double b1 = x.getImaginaryPart();
    double a2 = y.getRealPart();
    double b2 = y.getImaginaryPart();

    return new Complex(//
        (((a1 * a2) + (b1 * b2)) / ((a2 * a2) + (b2 * b2))),//
        (((a2 * b1) - (b2 * a1)) / ((a2 * a2) + (b2 * b2))));
  }
}
