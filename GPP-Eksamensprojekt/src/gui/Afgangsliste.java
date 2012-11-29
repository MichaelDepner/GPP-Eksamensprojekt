package gui;

import javax.swing.*;

public class Afgangsliste extends JFrame {
	
	    public Afgangsliste() {
	        
	        setTitle("Afgange");
	        JTabbedPane jtp = new JTabbedPane();
	        getContentPane().add(jtp);
	        
	        JPanel jp1 = new JPanel();
	        JPanel jp2 = new JPanel();
	        JPanel jp3 = new JPanel();
	        JPanel jp4 = new JPanel();
	        
	        JLabel label1 = new JLabel();
	        label1.setText("Onsdag d. 28. november 2012");
	        JLabel label2 = new JLabel();
	        label2.setText("Torsdag d. 29. november 2012");
	        JLabel label3 = new JLabel();
	        label3.setText("Fredag d. 30. november 2012");
	        JLabel label4 = new JLabel();
	        label4.setText("Lørdag d. 1. december 2012");
	        
	        jp1.add(label1);
	        jp2.add(label2);
	        jp3.add(label3);
	        jp4.add(label4);
	        
	        jtp.addTab("28/11", jp1);
	        jtp.addTab("29/11", jp2);
	        jtp.addTab("30/11", jp3);
	        jtp.addTab("01/12", jp4); }
	    }