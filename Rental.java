import java.util.Date;

public class Rental {
	public static final int RENTED = 0;
	public static final int RETURNED = 1;

	private Video video ;
	private int status;
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = RENTED;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		if (status == RENTED) {
			this.status = RETURNED;
			returnDate = new Date() ;
		}
	}

	public Date getRentDate() {
		return rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented ;
		long diff;
		if (getStatus() == RETURNED) {
			diff = returnDate.getTime() - rentDate.getTime();
		} else {
			diff = new Date().getTime() - rentDate.getTime();
		}

		daysRented = (int) (diff / DateUtil.getTimeMillisOfDay()) + 1;
		if (daysRented <= 2) { 
			return limit ;
		}

		switch (video.getVideoType()) {
			case Video.VHS: 
				limit = 5;
				break ;
			case Video.CD:
				limit = 3;
				break ;
			case Video.DVD:
				limit = 2;
				break ;
		}
		return limit ;
	}
}
