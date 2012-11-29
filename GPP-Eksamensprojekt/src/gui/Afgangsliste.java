package gui;

import javax.swing.*;
import javax.swing.table.TableColumn;

import java.awt.*;

public class Afgangsliste extends JFrame {
	
	private JPanel jp1Udrejse, jp2Udrejse, jp3Udrejse, jp4Udrejse;
	private JPanel jp1Hjemrejse, jp2Hjemrejse, jp3Hjemrejse, jp4Hjemrejse;
	private JLabel label1, label2, label3, label4;
	private JLabel weekNumber;
	private JLabel labelHjemrejse, labelUdrejse;
	private JButton lastWeek, nextWeek;
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
		
        //Opretter panel
        jp1Udrejse = new JPanel();
        //Indhold af panel
        jp1Udrejse.add(labelUdrejse);
		jp1Udrejse.add(table.getTableHeader());
        jp1Udrejse.add(table);
        
        //Tilføjer panel jp1Udrejse til jtp
        jtp.addTab("28/11", jp1Udrejse);
        
        //Opretter panel
        jp1Hjemrejse = new JPanel();
        //Indhold af panel
        jp1Hjemrejse.add(labelHjemrejse);
		jp1Hjemrejse.add(table.getTableHeader());
        jp1Hjemrejse.add(table);
        
        //Tilføjer panel jp1Hjemrejse til jtp
        jtp2.addTab("28/11", jp1Hjemrejse);
        
        //Laver en next-knap
        JButton next = new JButton("Næste");
        panelSouth.add(next);
        
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
    	//Her er overskriften, som vil blive vist under de forskellige faner
        labelUdrejse = new JLabel();
        labelUdrejse.setText("Onsdag d. 28. november 2012" + "Udrejse - Lufthavn");
        labelHjemrejse = new JLabel();
        labelHjemrejse.setText("Onsdag d. 28. november 2012" + "Hjemrejse - Lufthavn");
        
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
        nextWeek = new JButton("Næste uge");
        weekNumber = new JLabel("uge");
        
        panel.add(lastWeek);
        panelNorthJtp.add(weekNumber);
        panelCenterJtp2.add(weekNumber);
        panelNorthJtp.add(nextWeek);
        panelCenterJtp2.add(nextWeek);
    }
}