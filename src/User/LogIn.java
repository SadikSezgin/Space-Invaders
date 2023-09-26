package User;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Main.Panel;

public class LogIn extends Panel {
	
	private static final String REGISTRATION_FILE_PATH = "C:\\Users\\Sadik\\eclipse-workspace\\Term Project\\bin\\registration.txt";
	
	public static Boolean logIn() {
        Boolean found = false;
        LogIn logInDialog = new LogIn();
        JPanel panel = new JPanel();

        JTextField username_field = new JTextField(20);
        JPasswordField password_field = new JPasswordField(20);

        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Username: "));
        panel.add(username_field);
        panel.add(new JLabel("Password: "));
        panel.add(password_field);

        int option = JOptionPane.showOptionDialog(logInDialog, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        
        if (option == JOptionPane.OK_OPTION) {
            String username = username_field.getText();
            String password = new String(password_field.getPassword());
            
            
            String registration_data = readFromFile(REGISTRATION_FILE_PATH);
            String[] lines = registration_data.split("\n");
    

            for(String line : lines) {
            	String[] parts = line.split(",");
            
	            for(int i=0; i < parts.length; i+=2)
	            {
	            	String saved_username = parts[i];
	            	String saved_password = parts[i+1];
	            	
	            	if(username.equals(saved_username) && password.equals(saved_password)) {
	    				found = true;
	    				break;
	            	}
	            }
            }
        }
        return found;
	}
	
}

