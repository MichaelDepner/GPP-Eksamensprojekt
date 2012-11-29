package gui;

import javax.swing.*;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Afgangsliste extends JFrame {
	
	private JPanel jp1Udrejse, jp2Udrejse, jp3Udrejse, jp4Udrejse;
	private JPanel jp1Hjemrejse, jp2Hjemrejse, jp3Hjemrejse, jp4Hjemrejse;
	private JLabel label1, label2, label3, label4;
	private JLabel weekNumber;
	private JLabel labelHjemrejse, labelUdrejse;
	private JButton lastWeek, nextWeek, next;
	private TableColumn column;
	private JTable table;
	private JTabbedPane jtp, jtp2;
	
    public Afgangsliste() {
        setTitle("Afgange");
        //jtp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //Laver vores fane-vinduer
        jtp = new JTabbedPane();
        jtp2 = new JTabbedPane();
        
        //Sætter BorderLayout i contentPane, og laver panels indeni
        getContentPane().setLayout(new BorderLayout());
        //NORTH
        JPanel panelNorth = new JPanel();
        getContentPane().add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new BorderLayout());
        //CENTER
        JPanel panelCenter = new JPanel();
        getContentPane().add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new BorderLayout());
        //SOUTH
        JPanel panelSouth = new JPanel();
        getContentPane().add(panelSouth, BorderLayout.SOUTH);
        panelSouth.setLayout(new FlowLayout());
        
        //Sætter fane-vinduerne ind i layouts'ene
        panelNorth.add(jtp, BorderLayout.CENTER);
        panelCenter.add(jtp2, BorderLayout.CENTER);
        
        //Laver layout til panelNorth NORTH
        JPanel panelNorthJtp = new JPanel();
        panelNorth.add(panelNorthJtp, BorderLayout.NORTH);
        panelNorthJtp.setLayout(new FlowLayout());
        
        //Laver layout til panelCenter NORTH
        JPanel panelCenterJtp2 = new JPanel();
        panelCenter.add(panelCenterJtp2, BorderLayout.NORTH);
        panelCenterJtp2.setLayout(new FlowLayout());
        
        //Sætter knapper og uge-label ind i FlowLayouts
        buttonsAndLabel(panelNorthJtp);
        
        buttonsAndLabel(panelCenterJtp2);
        
        table();
		
		//Sætter bredden af columns
		setWidth(0, 120);
		setWidth(1, 120);
		setWidth(2, 120);
		setWidth(3, 120);
		setWidth(4, 120);
		
		//Opretter panels
        jp1Udrejse = new JPanel();
        jp1Hjemrejse = new JPanel();
        
        labelUdrejse = new JLabel();
        labelUdrejse.setText("Onsdag d. 28. november 2012" + "Udrejse - Lufthavn");
        jp1Udrejse.add(labelUdrejse);
        
		table(jp1Udrejse);
		//Tilføjer panel jp1Udrejse til jtp
        jtp.addTab("28/11", jp1Udrejse);
		
        labelHjemrejse = new JLabel();
        labelHjemrejse.setText("Onsdag d. 28. november 2012" + "Hjemrejse - Lufthavn");
		jp1Hjemrejse.add(labelHjemrejse);
		
		table(jp1Hjemrejse);
		//Tilføjer panel jp1Hjemrejse til jtp
        jtp2.addTab("28/11", jp1Hjemrejse);
        
        //Laver en next-knap
        next = new JButton("Næste");
        panelSouth.add(next);
        next.addActionListener(new Listener());
        
        /**
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        
		/**
        label2 = new JLabel();
        label2.setText("Torsdag d. 29. november 2012");
        label3 = new JLabel();
        label3.setText("Fredag d. 30. november 2012");
        label4 = new JLabel();
        label4.setText("Lørdag d. 1. december 2012");
        
        jp2.add(label2);
        jp3.add(label3);
        jp4.add(label4);
        
        // Her tilføjes titlerne til fanerne
        jtp.addTab("28/11", jp1);
        jtp.addTab("29/11", jp2);
        jtp.addTab("30/11", jp3);
        jtp.addTab("01/12", jp4); */

        setPreferredSize(new Dimension(640, 460));
        pack();
    }
    
    private void setWidth(int i, int j) {
    	column = table.getColumnModel().getColumn(i);
    	
    	column.setMinWidth(j);
		column.setMaxWidth(j);
		column.setPreferredWidth(j);
    }
    
    private void table(){
    	//Laver skemaerne
        String columns[] = {"Pris","Afrejse - ankomst","Rejsetid", "Lufthavne", "Ledige pladser"};
		Object data[][] = {
				{"500-800 kr.", "16.00-18.00", "02.00", "CPH-LON", "215"},
				{"600-800 kr.", "21.00-23.00", "02.00", "CPH-LON", "100"}
				//{"Tom",new Integer(20),"Male"},
		};
		table = new JTable(data,columns);
    }
    
    private void buttonsAndLabel(JPanel panel){
    	lastWeek = new JButton("Forrige uge");
        //lastWeek.addActionListener(new Listener());
    	//men skal have en actionlistener for hver, lige nu bliver den første overskrevet
        
        nextWeek = new JButton("Næste uge");
        //nextWeek.addActionListener(new Listener());
    	//men skal have en actionlistener for hver, lige nu bliver den første overskrevet
        
        weekNumber = new JLabel("uge");
        
        panel.add(lastWeek);
        panel.add(weekNumber);
        panel.add(nextWeek);
    }
    
    private void table(JPanel panel){
    	table();
        //Indhold af panel
        panel.add(table.getTableHeader());
        panel.add(table);
    }
    
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == nextWeek) {
            	System.out.println("Næste uge");
            } else if(event.getSource() == lastWeek) {
            	System.out.println("Forrige uge");
            } else if(event.getSource() == next) {
            	System.out.println("Næste");
            }
        }
    }
}