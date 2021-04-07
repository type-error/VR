import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);
	}

	public void showMessage(int totalPoint) {
		if ( totalPoint >= 10 ) {
			System.out.println("Congrat! You earned one free coupon");
		}

		if ( totalPoint >= 30 ) {
			System.out.println("Congrat! You earned two free coupon");
		}
	}

	private double discountCharge(int daysRented) {
		return daysRented > 2 ? (daysRented - 2) * 1.5 : 0;
	}

	public String getReport() {
		String name = getName(); // Extract Variable

		String result = "Customer Report for " + name + "\n";
//		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			Video video = each.getVideo(); // Extract Variable

			double eachCharge = 0;
			int eachPoint = 0 ;
			int daysRented = 0;
			long diff = 0;

			if (each.getStatus() == 1) { // returned Video
				Date returnDate = each.getReturnDate(); // Extract Variable
				Date rentDate = each.getRentDate(); // Extract Variable

				diff = returnDate.getTime() - rentDate.getTime();

//				daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;

			} else { // not yet returned
				diff = new Date().getTime() - each.getRentDate().getTime();

//				daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
			}

			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1; // Duplicated Code

			// switch (each.getVideo().getPriceCode()) {
			switch (video.getPriceCode()) {
			case Video.REGULAR:
				eachCharge += 2;
				eachCharge += discountCharge(daysRented); // Decompose Conditional

//				if (daysRented > 2)
//					eachCharge += (daysRented - 2) * 1.5;
				break;
			case Video.NEW_RELEASE:
				eachCharge = daysRented * 3;
				break;
			}

			eachPoint++;

			if ((video.getPriceCode() == Video.NEW_RELEASE) ) // 재선언한 변수 참조
				eachPoint++;

			if ( daysRented > each.getDaysRentedLimit() )
				eachPoint -= Math.min(eachPoint, video.getLateReturnPointPenalty()) ;

			result += "\t" + video.getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n"; // 재선언한 변수 참조

			totalCharge += eachCharge;

			totalPoint += eachPoint ;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

		showMessage(totalPoint); // Extract Method

//		if ( totalPoint >= 10 ) {
//			System.out.println("Congrat! You earned one free coupon");
//		}
//		if ( totalPoint >= 30 ) {
//			System.out.println("Congrat! You earned two free coupon");
//		}

		return result ;
	}
}
