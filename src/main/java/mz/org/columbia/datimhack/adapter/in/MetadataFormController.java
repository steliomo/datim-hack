/**
 *
 */
package mz.org.columbia.datimhack.adapter.in;

import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.openqa.selenium.WebDriver;

import mz.org.columbia.datimhack.domain.SubmissionType;

/**
 * @author Stélio Moiane
 *
 */
public class MetadataFormController extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel username;
	private JLabel password;
	private JLabel dataFile;
	private JButton browseFile;
	private JLabel submissionType;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField dataFileField;
	private JButton submit;

	private ActionProcess actionProcess;

	private ButtonGroup buttonGroup;

	private JRadioButton input;

	private JRadioButton clear;

	private JButton close;

	public MetadataFormController(final WebDriver browser) {

		this.setTitle("DATIM Hack - ICAP v1.0");
		this.setSize(300, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		final JPanel panel = new JPanel();
		this.add(panel);
		panel.setLayout(null);

		this.username = new JLabel("Username:");
		this.username.setBounds(10, 20, 100, 25);
		panel.add(this.username);

		this.password = new JLabel("Password:");
		this.password.setBounds(10, 50, 100, 25);
		panel.add(this.password);

		this.dataFile = new JLabel("Data File:");
		this.dataFile.setBounds(10, 80, 100, 25);
		panel.add(this.dataFile);

		this.submissionType = new JLabel("Submi.Type:");
		this.submissionType.setBounds(10, 110, 100, 25);
		panel.add(this.submissionType);

		this.dataFileField = new JTextField(20);
		this.dataFileField.setEnabled(false);
		this.dataFileField.setBounds(8, 140, 250, 25);
		panel.add(this.dataFileField);

		this.usernameField = new JTextField(20);
		this.usernameField.setBounds(100, 20, 165, 25);
		panel.add(this.usernameField);

		this.passwordField = new JPasswordField(20);
		this.passwordField.setBounds(100, 50, 165, 25);
		panel.add(this.passwordField);

		this.buttonGroup = new ButtonGroup();
		this.input = new JRadioButton("Input");
		this.input.setBounds(100, 110, 80, 25);

		this.clear = new JRadioButton("Clear");
		this.clear.setBounds(170, 110, 80, 25);

		this.buttonGroup.add(this.input);
		this.buttonGroup.add(this.clear);
		panel.add(this.input);
		panel.add(this.clear);

		this.browseFile = new JButton("Browse");
		this.browseFile.setBounds(100, 80, 80, 25);
		panel.add(this.browseFile);

		this.browseFile.addActionListener(event -> {
			final JFileChooser fileChooser = new JFileChooser();
			final int resuslt = fileChooser.showOpenDialog(this);

			if (resuslt == JFileChooser.APPROVE_OPTION) {
				final File selectedFile = fileChooser.getSelectedFile();
				this.dataFileField.setText(selectedFile.getAbsolutePath());
			}
		});

		this.submit = new JButton("Submit");
		this.submit.setBounds(10, 170, 80, 25);
		panel.add(this.submit);

		this.close = new JButton("Close");
		this.close.setBounds(100, 170, 80, 25);
		panel.add(this.close);

		this.submit.addActionListener(event -> {
			if (this.usernameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Input your DATIM username");
				return;
			}

			final String password = new String(this.passwordField.getPassword());

			if (password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Input your DATIM password");
				return;
			}

			if (this.dataFileField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Select the file data.");
				return;
			}

			if (this.buttonGroup.getSelection() == null) {
				JOptionPane.showMessageDialog(null, "Select the submissionType");
				return;
			}

			this.actionProcess.process();

		});

		this.close.addActionListener(event -> {
			browser.close();
			System.exit(0);
		});

		this.setVisible(true);
	}

	public String getUsername() {
		return this.usernameField.getText();
	}

	public String getPassword() {
		return String.valueOf(this.passwordField.getPassword());
	}

	public String getFileName() {
		return this.dataFileField.getText();
	}

	public JButton getSubmit() {
		return this.submit;
	}

	public void setActionProcess(final ActionProcess actionProcess) {
		this.actionProcess = actionProcess;
	}

	public SubmissionType getSubmissionType() {

		if (this.input.isSelected()) {
			return SubmissionType.INPUT;
		}

		return SubmissionType.CLEAN;
	}
}
