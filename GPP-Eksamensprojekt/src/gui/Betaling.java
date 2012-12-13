package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class Betaling {
	//Navn på kort, udløbsmåned, udløbsår, kortnr, kontrolcifre, korttype
	
	//tilbage- og til-gennemse
	
	private Container contentPane;
	private JFrame frame;
	private JLabel labelName, labelMonth, labelYear, labelNumber, labelCVR, labelType;
	private JTextField name, month, year, number, cvr, type;
	private JButton next, back;
	
	public Betaling() {
		frame.setTitle("Betaling");
		
		frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(700, 460);
        frame.setResizable(false);
        
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        //Laver panel til overskrift
        JPanel panelNorth = new JPanel();
        contentPane.add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        
        //Overskrift
        JLabel overskrift = new JLabel("Indtast betalingsoplysninger her");
        overskrift.setFont(new Font("String", Font.BOLD, 14));
        panelNorth.add(overskrift);
        
        //Laver et panel i contentPane, der får et Gridlayout
        JPanel panelCenter = new JPanel();
        contentPane.add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new GridLayout(6,1,10,10));
	    panelCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
	    JPanel panelEast = new JPanel();
        contentPane.add(panelEast, BorderLayout.EAST);
        panelEast.setLayout(new GridLayout(6,1,10,10));
	    panelEast.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
        //Opretter vores labels
        labelName = new JLabel("Navn på kort");
        labelMonth = new JLabel("Udløbsmåned");
        labelYear = new JLabel("Udløbsår");
        labelNumber = new JLabel("Kortnummer");
        labelCVR = new JLabel("Kontrolcifre");
        labelType = new JLabel("Korttype");
        
        //Opretter vores textFields
        name = new JTextField(20);
        month = new JTextField();
        year = new JTextField();
        number = new JTextField();
        cvr = new JTextField();
        type = new JTextField();
        
        //Adds labels and textFields to the GridLayout
		panelCenter.add(labelName);
		panelCenter.add(labelMonth);
		panelCenter.add(labelYear);
		panelCenter.add(labelNumber);
		panelCenter.add(labelCVR);
		panelCenter.add(labelType);

		panelEast.add(name);
		panelEast.add(month);
		panelEast.add(year);
		panelEast.add(number);
		panelEast.add(cvr);
		panelEast.add(type);
		
		//
		JPanel panelSouth = new JPanel();
        contentPane.add(panelSouth, BorderLayout.SOUTH);
        panelSouth.setLayout(new FlowLayout());
        
        next = new JButton("Bestil");
        back = new JButton("Tilbage");
        panelSouth.add(back);
        panelSouth.add(next);
        
        //ActionListeners til knapperne
        next.addActionListener(new Listener());
        back.addActionListener(new Listener());
		
		frame.pack();
		frame.setVisible(true);
	}
	
	//Lytter til knapperne
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == back) {
                System.out.println("Going back");
            } else if(event.getSource() == next) {
            	System.out.println("Going to the next step");
            }
        }
    }
}
