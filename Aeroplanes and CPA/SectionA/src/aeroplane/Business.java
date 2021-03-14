package aeroplane;

public class Business extends Customer{
  private final Luxury luxury;

  protected Business(String name, String surname, int age, Luxury luxury) {
    super(name, surname, age);
    this.luxury = luxury;
  }

  @Override
  public String toString() {
    return "BUSINESS CLASS: " + super.toString();
  }
}
