/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import booksys.application.domain.Booking;
import booksys.application.domain.BookingObserver;
import booksys.application.domain.BookingSystem;
import booksys.application.domain.Table;

public class StaffUI extends Canvas implements BookingObserver {
	private static final long serialVersionUID = 4540587316285438139L;
	final static int LEFT_MARGIN = 50;
	final static int TOP_MARGIN = 50;
	final static int BOTTOM_MARGIN = 50;
	final static int ROW_HEIGHT = 70;
	final static int COL_WIDTH = 120;

	final static int PPM = 2; // Pixels per minute
	final static int PPH = 60 * PPM; // Pixels per hours
	final static int TZERO = 8; // Earliest time shown
	final static int SLOTS = 8; // Number of booking slots shown

	// Routines to convert between (x, y) and (time, table)
	private int timeToX(LocalTime time) {
		if (time.getHour() == 8) {
			return LEFT_MARGIN;
		}else if(time.getHour() == 10) {
			return LEFT_MARGIN + COL_WIDTH;
		}else if(time.getHour() == 13) {
			return LEFT_MARGIN + 3*COL_WIDTH;
		}else if(time.getHour() == 15) {
			return LEFT_MARGIN + 4*COL_WIDTH;
		}else if(time.getHour() == 18) {
			return LEFT_MARGIN + 6*COL_WIDTH;
		}else if(time.getHour() == 20) {
			return LEFT_MARGIN + 7*COL_WIDTH;
		}else {
			return 0;
		}
	}

	private LocalTime xToTime(int x) {
		x = x - LEFT_MARGIN;
		int h = 0, m=0;
		if(x <= 120) {
			h = 8;
			m = 0;
		}else if(x>=120 && x<=240) {
			h = 10;
			m = 0;
		}else if(x>=360 && x<=480) {
			h = 13;
			m = 30;
		}else if(x>=480 && x<=600) {
			h = 15;
			m = 30;
		}else if(x>=720 && x<=840) {
			h = 18;
			m = 0;
		}else if(x>=840 && x<=960) {
			h = 20;
			m = 0;
		}
		return LocalTime.of(h, m);
	}

	private int tableToY(int table) {
		return TOP_MARGIN + (ROW_HEIGHT * (table - 1));
	}

	private int yToTable(int y) {
		y = y - TOP_MARGIN;
		if(y <= 70) {
			return 1;
		}else if(y>70 && y<=140) {
			return 2;
		}else if(y>140 && y<=210) {
			return 3;
		}else if(y>210 && y<=280) {
			return 4;
		}else if(y>280 && y<=350) {
			return 5;
		}else {
			return 0;
		}
	}

	// Data members

	private Frame parentFrame;
	private BookingSystem bs;
	private Image offscreen;
	private List<Table> tableNumbers;
	private int firstX, firstY, currentX, currentY;
	private boolean mouseDown;

	public StaffUI(Frame f, String username) {
		parentFrame = f;

		tableNumbers = BookingSystem.getTables();
		setSize(LEFT_MARGIN + (SLOTS * COL_WIDTH), TOP_MARGIN + 5 * ROW_HEIGHT + BOTTOM_MARGIN);
		setBackground(Color.white);
		bs = BookingSystem.getInstance();
		bs.addObserver(this);
		
		String name_second = username.split("-")[1];
		
		char[] names = name_second.toCharArray();
		String class_num_aaa = "";
		for(int i=0; i<6; i++) {
			class_num_aaa = class_num_aaa + names[i];
		}
		bs.setClass(Integer.parseInt(class_num_aaa));

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseDown = true;
					bs.showBooking(yToTable(currentY), xToTime(currentX), username);
				}
			}
		});
	}

	public void update() {
		repaint();
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		Dimension canvasSize = getSize();
		if (offscreen == null) {
			offscreen = this.createImage(canvasSize.width, canvasSize.height);
		}
		Graphics og = offscreen.getGraphics();
		og.setColor(getBackground());
		og.fillRect(0, 0, canvasSize.width, canvasSize.height);
		og.setColor(Color.black);

		// Draw screen outlines
		og.drawLine(LEFT_MARGIN, 0, LEFT_MARGIN, canvasSize.height);
		og.drawLine(0, TOP_MARGIN, canvasSize.width, TOP_MARGIN);

		// Write table numbers and horizontal rules
		og.drawString("Mon", 0, TOP_MARGIN + ROW_HEIGHT);
		og.drawLine(LEFT_MARGIN, TOP_MARGIN + ROW_HEIGHT, canvasSize.width, TOP_MARGIN + ROW_HEIGHT);
		og.drawString("Tue", 0, TOP_MARGIN + 2*ROW_HEIGHT);
		og.drawLine(LEFT_MARGIN, TOP_MARGIN + 2*ROW_HEIGHT, canvasSize.width, TOP_MARGIN + 2*ROW_HEIGHT);
		og.drawString("Thurs", 0, TOP_MARGIN + 3*ROW_HEIGHT);
		og.drawLine(LEFT_MARGIN, TOP_MARGIN + 3*ROW_HEIGHT, canvasSize.width, TOP_MARGIN + 3*ROW_HEIGHT);
		og.drawString("Wed", 0, TOP_MARGIN + 4*ROW_HEIGHT);
		og.drawLine(LEFT_MARGIN, TOP_MARGIN + 4*ROW_HEIGHT, canvasSize.width, TOP_MARGIN + 4*ROW_HEIGHT);
		og.drawString("Fri", 0, TOP_MARGIN + 5*ROW_HEIGHT);
		og.drawLine(LEFT_MARGIN, TOP_MARGIN + 5*ROW_HEIGHT, canvasSize.width, TOP_MARGIN + 5*ROW_HEIGHT);

		// Write time labels and vertical rules
		og.drawString("08:00-09:30", 50, 40);
		og.drawLine(50, TOP_MARGIN, 50, canvasSize.height - BOTTOM_MARGIN);
		og.drawString("10:00-11:30", 170, 40);
		og.drawLine(170, TOP_MARGIN, 170, canvasSize.height - BOTTOM_MARGIN);
		og.drawString("Lunch Time", 290, 40);
		og.drawLine(290, TOP_MARGIN, 290, canvasSize.height - BOTTOM_MARGIN);
		og.drawString("13:30-15:00", 410, 40);
		og.drawLine(410, TOP_MARGIN, 410, canvasSize.height - BOTTOM_MARGIN);
		og.drawString("15:30-17:00", 530, 40);
		og.drawLine(530, TOP_MARGIN, 530, canvasSize.height - BOTTOM_MARGIN);
		og.drawString("Dinner Time", 650, 40);
		og.drawLine(650, TOP_MARGIN, 650, canvasSize.height - BOTTOM_MARGIN);
		og.drawString("18:00-19:30", 770, 40);
		og.drawLine(770, TOP_MARGIN, 770, canvasSize.height - BOTTOM_MARGIN);
		og.drawString("20:00-21:30", 890, 40);
		og.drawLine(890, TOP_MARGIN, 890, canvasSize.height - BOTTOM_MARGIN);

		// Display booking information
		og.drawString(String.valueOf(bs.getClassInput()), LEFT_MARGIN, 20);

		List<Booking> enumV = bs.getBookings();
		for (Booking b : enumV) {
			int x = timeToX(b.getTime());
			int y = tableToY(b.getTable().getNumber());
			og.setColor(Color.gray);
			og.fillRect(x, y, 1 * COL_WIDTH, ROW_HEIGHT);
			og.setColor(Color.white);
			og.drawString(b.getDetails(), x, y + ROW_HEIGHT / 2);
		}
		g.drawImage(offscreen, 0, 0, this);
	}

	public boolean message(String message, boolean confirm) {
		ConfirmDialog d = new ConfirmDialog(parentFrame, message, confirm);
		d.show();
		return d.isConfirmed();
	}

	void displayDate() {
		DateDialog d = new DateDialog(parentFrame, "Enter a class");
		d.show();
		if (d.isConfirmed()) {
			int class_num_input = d.getClassNum();
			bs.setClass(class_num_input);
		}
	}

	void addReservation() {
		ReservationDialog d = new ReservationDialog(parentFrame, "Enter reservation details");
		d.show();
		if (d.isConfirmed()) {
			bs.addReservation(d.getCovers(), bs.getClassInput(), d.getTime(), d.getRoom(), d.getTableNumber(), d.getCustomerName(),
					d.getPhoneNumber());
		}
	}


	void cancel() {
		bs.cancelSelected();
	}
}
