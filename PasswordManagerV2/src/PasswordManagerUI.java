import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class PasswordManagerUI {
	public static JFrame frame;

	public static void main(String[] args) throws Exception {

		Map<String, byte[]> database = PasswordManagerLogic.loadDatabaseFromFile();

		frame = new JFrame("Password Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 380);
		frame.setLayout(new GridLayout(3, 1));

		JButton createAccountButton = new JButton("Create Account");
		JButton loginButton = new JButton("Login");
		JButton importButton = new JButton("Import");

		createAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PasswordManagerLogic.createAccount();
			}
		});

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (PasswordManagerLogic.login(frame)) {
					frame.remove(createAccountButton);
					frame.remove(loginButton);
					frame.remove(importButton);
				}
			}
		});

		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Import As");
				int userSelection = fileChooser.showOpenDialog(frame);
				try {
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File fileToLoad = fileChooser.getSelectedFile();
						InputStream stream = new FileInputStream(fileToLoad);
						database.put(fileToLoad.getName(), stream.readAllBytes());
						stream.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Unexpected error exporting passwords.");
				}
			}

		});

		frame.add(createAccountButton);
		frame.add(loginButton);
		frame.add(importButton);
		frame.setVisible(true);
	}

}
