package aeroplane;

public class Economy extends Customer{

  protected Economy(String name, String surname, int age) {
    super(name, surname, age);
  }

  @Override
  public String toString() {
    return "ECONOMY CLASS: " + super.toString();
  }
}
