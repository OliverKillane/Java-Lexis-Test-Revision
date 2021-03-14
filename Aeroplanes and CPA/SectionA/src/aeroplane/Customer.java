package aeroplane;

public abstract class Customer extends Passenger{
  protected final int age;

  protected Customer(String name, String surname, int age) {
    super(name, surname);

    // age must be non-negative
    assert age >= 0;

    this.age = age;
  }

  @Override
  public boolean isAdult() {
    return age >= 18;
  }
}
