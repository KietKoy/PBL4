package PBL4;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Client_Start extends JFrame {
	private JPanel contentPane;
	private JTextField textField_1;
	static Client_Start frame = new Client_Start();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client_Start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setBounds(146, 56, 129, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("CONNECT");
		btnNewButton.setBounds(146, 85, 129, 21);
		btnNewButton.addActionListener(new ActionListener() {
			private boolean isFormatIpv4(String host) {
		    	String pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
		        return host.matches(pattern);
		    }
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String ip = textField_1.getText().toString();
				if(ip.equals("") || isFormatIpv4(ip) == false) {
					JOptionPane.showMessageDialog(contentPane,
			                "Vui Long Nhap IP",
			                "ERROR",
			                JOptionPane.INFORMATION_MESSAGE);
					
				}
				else {
					new ClientData(ip).setVisible(true);
					frame.setVisible(false);
				}
			}
		});
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("NHAP IP SERVER:");
		lblNewLabel.setBounds(146, 35, 129, 13);
		contentPane.add(lblNewLabel);
	}
}