package calculatorClient;

import java.io.Serializable;

public class Complex implements Serializable {
  private static final long serialVersionUID = 1L;

  private double m_a;
  private double m_b;

  public Complex() {
    super();
  }

  public Complex(final double a, final double b) {
    this();
    this.setRealPart(a);
    this.setImaginaryPart(b);
  }

  public double getRealPart() {
    return this.m_a;
  }

  public void setRealPart(final double a) {
    this.m_a = a;
  }

  public double getImaginaryPart() {
    return this.m_b;
  }

  public void setImaginaryPart(final double b) {
    this.m_b = b;
  }
}
