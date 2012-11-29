package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Betaling {
	//Navn på kort, udløbsmåned, udløbsår, kortnr, kontrolcifre, korttype
	
	//tilbage- og til-gennemse
	
	private Container contentPane;
	private JFrame frame;
	private JLabel labelName, labelMonth, labelYear, labelNumber, labelCVR, labelType;
	private JTextField name, month, year, number, cvr, type;
	private JButton next, back;
	
	private Betaling() {
		frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(640, 460);
        frame.setResizable(false);
        
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        //Laver et panel i contentPane, der får et Gridlayout
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(6,2));
        
        //Opretter vores labels
        labelName = new JLabel("Navn på kort");
        labelMonth = new JLabel("Udløbsmåned");
        labelYear = new JLabel("Udløbsår");
        labelNumber = new JLabel("Kortnummer");
        labelCVR = new JLabel("Kontrolcifre");
        labelType = new JLabel("Korttype");
        
        //Opretter vores textFields
        name = new JTextField();
        month = new JTextField();
        year = new JTextField();
        number = new JTextField();
        cvr = new JTextField();
        type = new JTextField();
        
        //Adds labels and textFields to the GridLayout
		panel.add(labelName);
		panel.add(name);
		panel.add(labelMonth);
		panel.add(month);
		panel.add(labelYear);
		panel.add(year);
		panel.add(labelNumber);
		panel.add(number);
		panel.add(labelCVR);
		panel.add(cvr);
		panel.add(labelType);
		panel.add(type);
		
		//
		JPanel panelSouth = new JPanel();
        contentPane.add(panelSouth, BorderLayout.SOUTH);
        panelSouth.setLayout(new FlowLayout());
        
        next = new JButton("Næste");
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
