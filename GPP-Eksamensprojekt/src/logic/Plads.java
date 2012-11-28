package logic;

import javax.swing.*;

public class Plads {
	
	private int seatNo;
	private boolean isReserved;
	private boolean isAisle;
	
	
	/**
	 * @param seatNo Placeringen af sædet i afgang-arrayet
	 * @param isReserved Pladsens status
	 * @param isAisle Er pladsen en mellemgang?
	 */
	public Plads(int seatNo, boolean isReserved, boolean isAisle) {
		this.seatNo = seatNo;
		this.isReserved = isReserved;
		this.isAisle = isAisle;
	}
}
