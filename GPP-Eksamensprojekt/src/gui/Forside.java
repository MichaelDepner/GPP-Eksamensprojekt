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
		
		//S�t layout i contentPane til et borderlayout, og tilf�j et gridlayout til centerPanel
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(3, 1));
		
		//Indl�s Logo.png, og gem det i en JLabel
		ImageIcon imageLogo = new ImageIcon(getClass().getResource("png/Logo.png"));
		logoLabel = new JLabel(imageLogo);
		
		//Tilf�j vores 2 panels til contentPanen, p� CENTER og NORTH pladserne
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(logoLabel, BorderLayout.NORTH);
		
		//Initialis�r de 3 knapper
		rejse = new JButton("S�g rejser");
		booking = new JButton("S�g booking");
		afgang = new JButton("S�g afgang");
		
		
		
		//Tilf�jer actionListenere til knapperne
		rejse.addActionListener(new rejseActionListener());
		booking.addActionListener(new bookingActionListener());
		afgang.addActionListener(new afgangActionListener());
		
		//Tilf�jet knapperne til centerPanel
		centerPanel.add(rejse);
		centerPanel.add(afgang);
		centerPanel.add(booking);
		
		
		//S�t vindue-st�rrelsen, g�r s� det ikke kan resizes, og g�r det hele synligt.
		frame.setPreferredSize(new Dimension(640, 460));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private class rejseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("S�ger rejser!");
		}
	}
	
	private class afgangActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("S�ger afgange!");
		}
	}
	
	private class bookingActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("S�ger bookingner!");
		}
	}
	
	
	
}