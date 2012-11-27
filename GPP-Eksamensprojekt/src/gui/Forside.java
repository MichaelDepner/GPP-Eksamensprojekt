package gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class Forside {
	private JFrame frame;
	private JPanel southPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel northPanel = new JPanel();
	private JButton rejse, booking, afgang;
	
	private JLabel logoLabel;
	
	
	
	public Forside() {
		makeWindow();
	}
	
	private void makeWindow() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(3, 1));
		
		ImageIcon imageLogo = new ImageIcon(getClass().getResource("png/Logo.png"));
		logoLabel = new JLabel(imageLogo);
		
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(logoLabel, BorderLayout.NORTH);
		
		rejse = new JButton("Søg rejser");
		booking = new JButton("Søg booking");
		afgang = new JButton("Søg afgang");
		
		rejse.addActionListener(new rejseActionListener());
		booking.addActionListener(new bookingActionListener());
		afgang.addActionListener(new afgangActionListener());
		
		centerPanel.add(rejse);
		centerPanel.add(afgang);
		centerPanel.add(booking);
		
		frame.setPreferredSize(new Dimension(640, 460));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private class rejseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Søger rejser!");
		}
	}
	
	private class afgangActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Søger afgange!");
		}
	}
	
	private class bookingActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Søger bookingner!");
		}
	}
	
	
	
}
