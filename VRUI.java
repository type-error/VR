import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in);

	private static VRController vrController = new VRController();

	public static void main(String[] args) {
		VRUI ui = new VRUI();

		boolean quit = false;
		while (!quit) {
			int command = ui.showCommand();
			switch (command) {
			case 0:
				quit = true;
				break;
			case 1:
				ui.listCustomers();
				break;
			case 2:
				ui.listVideos();
				break;
			case 3:
				ui.register("customer");
				break;
			case 4:
				ui.register("video");
				break;
			case 5:
				ui.rentVideo();
				break;
			case 6:
				ui.returnVideo();
				break;
			case 7:
				ui.getCustomerReport();
				break;
			case 8:
				ui.clearRentals();
				break;
			case -1:
				ui.init();
				break;
			default:
				break;
			}
		}
		System.out.println("Bye");
	}

	public void clearRentals() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		Customer foundCustomer = vrController.findCustomer(customerName);
		if (foundCustomer == null) {
			System.out.println("No customer found");
		} else {
			String rentalList = vrController.getRentalList(foundCustomer);
			System.out.println(rentalList);
			vrController.clearRental(customerName);
		}
	}

	public void returnVideo() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		System.out.println("Enter video title to return: ");
		String videoTitle = scanner.next();

		vrController.returnVideo(customerName, videoTitle);
	}

	private void init() {
		vrController.addCustomer("James");
		vrController.addCustomer("Brown");
		
		vrController.addVideo("v1", Video.CD, Video.REGULAR);
		vrController.addVideo("v2", Video.DVD, Video.NEW_RELEASE);

		vrController.addRental("James", "v1");
		vrController.addRental("James", "v2");
	}

	public void listVideos() {
		System.out.println("List of videos");

		for (Video video : vrController.getVideos()) {
			System.out.println("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
		}
		System.out.println("End of list");
	}

	public void listCustomers() {
		System.out.println("List of customers");
		for (Customer customer : vrController.getCustomers()) {
			System.out.println("Name: " + customer.getName() + "\tRentals: " + customer.getRentals().size());
			for (Rental rental : customer.getRentals()) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
			}
		}
		System.out.println("End of list");
	}

	public void getCustomerReport() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();
		String result = vrController.getCustomerReport(customerName);
		if (result == null) {
			System.out.println("No customer found");
		} else {
			System.out.println(result);
		}
	}

	public void rentVideo() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		System.out.println("Enter video title to rent: ");
		String videoTitle = scanner.next();

		vrController.rentVideo(customerName, videoTitle);
	}

	public void register(String object) {
		if (object.equals("customer")) {
			System.out.println("Enter customer name: ");
			vrController.registerCustomer(scanner.next());
		} else {
			System.out.println("Enter video title to register: ");
			String title = scanner.next();

			System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
			int videoType = scanner.nextInt();

			System.out.println("Enter price code( 1 for Regular, 2 for New Release ):");
			int priceCode = scanner.nextInt();

			Date registeredDate = new Date();
			vrController.registerVideo(title, videoType, priceCode, registeredDate);
		}
	}

	public int showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt();

		return command;

	}
}
