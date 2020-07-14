
import java.awt.FlowLayout;

import javax.swing.*;

public class MainLogScreen {



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    JFrame window = new JFrame("PayCalc Version 2.0");
	    window.getContentPane().setLayout(new FlowLayout());
	    JTextField textfield1 = new JTextField("User",35);
	    
	    window.getContentPane().add(textfield1);
	    JButton runa = new JButton("Run");
	    window.getContentPane().add(runa);
	  
	   
	 
	    window.setVisible(true);
	  }
		
	}



