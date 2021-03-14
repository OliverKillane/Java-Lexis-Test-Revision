package aeroplane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SeatAllocator {

	private Map<Seat, Passenger> allocation;

	private static final String CREW = "crew";
	private static final String BUSINESS = "business";
	private static final String ECONOMY = "economy";


	private static final Seat
			CREW_FIRST = new Seat(1,'A'), CREW_LAST = new Seat(1,'F'),
			BUSINESS_FIRST = new Seat(2,'A'), BUSINESS_LAST = new Seat(15,'F'),
			ECONOMY_FIRST = new Seat(16,'A'), ECONOMY_LAST = new Seat(50,'F');
	
	public SeatAllocator() {
		allocation = new HashMap<Seat, Passenger>();
	}

	@Override
	public String toString() {
		return allocation.toString();
	}
	
	private void allocateInRange(Passenger passenger,
			Seat first, Seat last) throws AeroplaneFullException {
		// TODO: Section A, Task 4
		for(Seat checkseat = first;;checkseat = checkseat.next()){
			if (!allocation.containsKey(checkseat) && (passenger.isAdult() || !checkseat.isEmergencyExit())){
				allocation.put(checkseat, passenger);
				return;
			} else if (checkseat.equals(last)){
				break;
			}
		}
		throw new AeroplaneFullException();
	}

	private static String readStringValue(BufferedReader br) throws MalformedDataException, IOException {

		String result = br.readLine();
		
		if(result == null) {
			throw new MalformedDataException();
		}
		
		return result;
		
	}

	private static int readIntValue(BufferedReader br)
			throws MalformedDataException, IOException {
		try {
			return Integer.parseInt(readStringValue(br));
		} catch(NumberFormatException e) {
			throw new MalformedDataException();
		}
	}

	private static Luxury readLuxuryValue(BufferedReader br)
			throws MalformedDataException, IOException {
		try {
			return Luxury.valueOf(readStringValue(br));
		} catch(IllegalArgumentException e) {
			throw new MalformedDataException();
		}
	}

	
	public void allocate(String filename) throws IOException, AeroplaneFullException {
		
		BufferedReader br = new BufferedReader(new FileReader(filename));

		String line;
		while((line = br.readLine()) != null) {
			try {
				if(line.equals(CREW)) {
					allocateCrew(br);
				} else if(line.equals(BUSINESS)) {
					allocateBusiness(br);
				} else if(line.equals(ECONOMY)) {
					allocateEconomy(br);
				} else {
					throw new MalformedDataException();
				}
			} catch(MalformedDataException e) {
				System.out.println("Skipping malformed line of input");
			}
		}
		
	}
	
	private void allocateCrew(BufferedReader br) throws IOException, MalformedDataException, AeroplaneFullException {
		String firstName = readStringValue(br);
		String lastName = readStringValue(br);
		// TODO: Section A, Task 4
		//       create a crew member using firstName and lastName
		//       call allocateInRange with appropriate arguments

		allocateInRange(new Crew(firstName,lastName), CREW_FIRST,CREW_LAST);
	}

	private void allocateBusiness(BufferedReader br) throws IOException, MalformedDataException, AeroplaneFullException {
		String firstName = readStringValue(br);
		String lastName = readStringValue(br);
		int age = readIntValue(br);
		Luxury luxury = readLuxuryValue(br);
		// TODO: Section A, Task 4
		//       create a business class passenger using firstName, lastName, age and luxury
		//       call allocateInRange with appropriate arguments

		allocateInRange(new Business(firstName, lastName,age, luxury), BUSINESS_FIRST,BUSINESS_LAST);
	}

	private void allocateEconomy(BufferedReader br) throws IOException, MalformedDataException, AeroplaneFullException {
		String firstName = readStringValue(br);
		String lastName = readStringValue(br);
		int age = readIntValue(br);
		// TODO: Section A, Task 4
		//       create an economy class passenger using firstName, lastName and age
		//       call allocateInRange with appropriate arguments

		allocateInRange(new Economy(firstName, lastName,age), ECONOMY_FIRST,ECONOMY_LAST);


	}

	public void upgrade(){
		for (Seat businessSeat = BUSINESS_FIRST;;businessSeat = businessSeat.next()){
			if (!allocation.containsKey(businessSeat)){
				for (Seat economySeat = ECONOMY_FIRST;;economySeat = economySeat.next()){
					if(allocation.containsKey(economySeat)){
						allocation.put(businessSeat,allocation.get(economySeat));
						allocation.remove(economySeat);
						break;
					} else if (economySeat.equals(ECONOMY_LAST)){
						break;
					}
				}
			} else if (businessSeat.equals(BUSINESS_LAST)){
				return;
			}
		}
	}

	// TODO: Section A, Task 5 - add upgrade method

}
