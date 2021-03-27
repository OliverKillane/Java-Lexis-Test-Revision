package videogame;

public class GameReport {

	public static void main(String[] args) {

		// create objects
		Magician
				m1 = new Magician("Ged", 300),
				m2 = new Magician("Dumbeldore", 200),
				m3 = new Magician("Saruman", 600),
				m4 = new Magician("Harry", 18),
				m5 = new Magician("Gandalf", 950),
				m6 = new Magician("Gargamel", 200);


		TransportUnit
				t2 = new TransportUnit("Shadowfax", 350){{
					add(m4);
					add(m5);
				}},
				t1 = new TransportUnit("Falkour", 2500){{
					add(m1);
					add(m2);
					add(m3);
					add(t2);
				}};


		// placing in containers
		System.out.println(t1.toString());

		t1.applySpell(m6);

		System.out.println(t1.toString());

		Magician m7 = new Magician("m7", t1.minimumStrikeToDestroy());

		assert !(t1.isAlive() || t2.isAlive() || m1.isAlive() || m2.isAlive() || m3.isAlive() || m4.isAlive() || m5.isAlive());

		assert m6.isAlive() && m7.isAlive();

		t1.applySpell(m7);

		System.out.println(t1.toString());

		// TODO: complete this method as part of Section A Question 5
		
	}

}
