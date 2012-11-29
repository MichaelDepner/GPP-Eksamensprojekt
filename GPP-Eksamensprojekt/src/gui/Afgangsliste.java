package gui;

import javax.swing.*;

import java.awt.*;

public class Afgangsliste extends JFrame {
	
	private JPanel jp1, jp2, jp3, jp4;
	private JLabel label1, label2, label3, label4;
	private JLabel weekNumber;
	private JButton lastWeek, nextWeek;
	
    public Afgangsliste() {
        //Laver vores fane-vindue
        JTabbedPane jtp = new JTabbedPane();
        setTitle("Afgange");
        jtp.setPreferredSize(new Dimension(640, 460));
        //jtp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //Sætter BorderLayout i vinduet, og placerer fanerne i CENTER
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jtp, BorderLayout.CENTER);
        
        //Opretter et panel i NORTH, og sætter FlowLayout
        JPanel panelNorth = new JPanel();
        getContentPane().add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new FlowLayout());
        
        //Sætter knapper og uge-label ind i FlowLayoutet
        lastWeek = new JButton("Forrige uge");
        nextWeek = new JButton("Næste uge");
        weekNumber = new JLabel("uge");
        panelNorth.add(lastWeek);
        panelNorth.add(weekNumber);
        panelNorth.add(nextWeek);
             
        //Opretter panels
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        
        //Her er overskriften, som vil blive vist under de forskellige faner
        label1 = new JLabel();
        label1.setText("Onsdag d. 28. november 2012");
        label2 = new JLabel();
        label2.setText("Torsdag d. 29. november 2012");
        label3 = new JLabel();
        label3.setText("Fredag d. 30. november 2012");
        label4 = new JLabel();
        label4.setText("Lørdag d. 1. december 2012");
        
        jp1.add(label1);
        jp2.add(label2);
        jp3.add(label3);
        jp4.add(label4);
        
       // Her tilføjes titlerne til fanerne
        jtp.addTab("28/11", jp1);
        jtp.addTab("29/11", jp2);
        jtp.addTab("30/11", jp3);
        jtp.addTab("01/12", jp4); 
        
        pack();
    }
}