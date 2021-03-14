package aeroplane;

public abstract class Passenger {
  protected final String name, surname;

  protected Passenger(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }

  public abstract boolean isAdult();

  public String toString(){
    return "NAME: " + name + ", SURNAME: " + surname;
  }

  // TODO: Section A, Task 2
}


