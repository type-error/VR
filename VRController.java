import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VRController {
	private List<Customer> customers = new ArrayList<Customer>();
	private List<Video> videos = new ArrayList<Video>();

	public void registerCustomer(String name) {
		Customer customer = new Customer(name);
		customers.add(customer);
	}

	public void registerVideo(String title, int videoType, int priceCode, Date registeredDate) {
		Video video = new Video(title, videoType, priceCode, registeredDate);
		videos.add(video);
	}

	public Customer findCustomer(String customerName) {
		for (Customer customer : customers) {
			if (customer.getName().equals(customerName)) {
				return customer;
			}
		}

		return null;
	}

	public Video findVideo(String videoTitle) {
		for (Video video : videos) {
			if (video.getTitle().equals(videoTitle) && video.isRented() == false) {
				return video;
			}
		}

		return null;
	}

	public void rentVideo(String customerName, String videoTitle) {
		Customer foundCustomer = findCustomer(customerName);
		if (foundCustomer == null) {
			return;
		}

		Video foundVideo = findVideo(videoTitle);
		if (foundVideo == null) {
			return;
		}

		Rental rental = new Rental(foundVideo);
		foundVideo.setRented(true);

		List<Rental> customerRentals = foundCustomer.getRentals();
		customerRentals.add(rental);
		foundCustomer.setRentals(customerRentals);
	}

	public String getCustomerReport(String customerName) {
		Customer foundCustomer = findCustomer(customerName);
		if (foundCustomer == null) {
			return null;
		}

		return foundCustomer.getReport();
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void addCustomer(String customerName) {
		customers.add(new Customer(customerName));
	}

	public void addVideo(String videoTitle, int videoType, int priceCode) {
		videos.add(new Video(videoTitle, videoType, priceCode, new Date()));
	}

	public boolean addRental(String customerName, String videoTitle) {
		Customer customer = findCustomer(customerName);
		if (customer == null) {
			return false;
		}

		Video video = findVideo(videoTitle);
		if (video == null) {
			return false;
		}

		customer.addRental(new Rental(video));
		return true;
	}

	public boolean returnVideo(String customerName, String videoTitle) {
		Customer customer = findCustomer(customerName);
		if (customer == null) {
			return false;
		}

		Video video = findVideo(videoTitle);
		if (video == null) {
			return false;
		}

		List<Rental> customerRentals = customer.getRentals();
		for (Rental rental : customerRentals) {
			if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
				rental.returnVideo();
				rental.getVideo().setRented(false);
				return true;
			}
		}

		return false;
	}
	
	public String getRentalList(Customer customer) {
		String msg = "";
		for (Rental rental : customer.getRentals()) {
			msg += ("\tTitle: " + rental.getVideo().getTitle() + " ");
			msg += ("\tPrice Code: " + rental.getVideo().getPriceCode());
		}
		return msg;
	}

	public boolean clearRental(String customerName) {
		Customer customer = findCustomer(customerName);
		if (customer == null) {
			return false;
		}
		
		List<Rental> rentals = new ArrayList<Rental>();
		customer.setRentals(rentals);
		return true;
	}
}
