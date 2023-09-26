package User;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Main.Panel;

public class Register extends Panel {
	
	private static final String REGISTRATION_FILE_PATH = "C:\\Users\\Sadik\\eclipse-workspace\\Term Project\\bin\\registration.txt";
	
	private void register() {
		JPanel panel = new JPanel();
		
		JTextField username_field = new JTextField(20);
		JPasswordField password_field = new JPasswordField(20);
		
		panel.setLayout(new GridLayout(2, 2));
		panel.add(new JLabel("Username: "));
		panel.add(username_field);
		panel.add(new JLabel("Password: "));
		panel.add(password_field);
		
		int option = JOptionPane.showOptionDialog(this, panel, "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		
		if(option == JOptionPane.OK_OPTION) {
			String username = username_field.getText();
			String password = new String(password_field.getPassword()); 
			
			String existingData = readFromFile(REGISTRATION_FILE_PATH).trim();
			if(existingData == null || existingData.trim().isEmpty()) {
				System.out.println("Existingdata = null");
				String registrationData = username + "," + password;
				writeToFile(REGISTRATION_FILE_PATH, registrationData, false);
			}else {
				System.out.println("Existingdata != null");
				String registrationData = "\n" + username + "," + password;		
				writeToFile(REGISTRATION_FILE_PATH, registrationData, true);
			}
		}
		
	}
	
}
