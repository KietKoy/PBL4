package PBL4;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;


import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class ClientData extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel TbModel;
	String ColumnsName[] = {"","",""};
	JScrollPane sp;
	private JTextField textField;
	String ipServer;
	static ClientData frame = new ClientData("");


	DataInputStream dis;
	DataOutputStream dos;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ClientData frame = new ClientData();
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
	public ClientData(String ip) {
		this.ipServer = ip;	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String dinh[] = new String[26];
		for(int i = 0;i < 26; i++) {
			dinh[i] = Integer.toString(i+1);
		}
		JComboBox comboBox = new JComboBox(dinh);

		comboBox.setBounds(131, 41, 101, 21);
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				int v = Integer.parseInt(comboBox.getSelectedItem().toString());
				String[] columnNames = new String[Integer.parseInt(comboBox.getSelectedItem().toString())+1];
				for(int i = 1;i <= Integer.parseInt(comboBox.getSelectedItem().toString()); i++) {
					columnNames[i] = "";
				}
				int a = 65;
				columnNames[0] = "";
				for(int i = 1;i <= Integer.parseInt(comboBox.getSelectedItem().toString()); i++) {
					char ch = (char)a;
					columnNames[i] = Character.toString(ch);
					a++;
				}
				TbModel.setColumnIdentifiers(columnNames);
				
				DefaultTableModel dtm = (DefaultTableModel)table.getModel();
				dtm.setRowCount(0);
				int a1 = 65;
				for(int i = 0;i < v; i++) {
					String[] d = new String[v];
					d[0] = Character.toString(a1);
					a1++;
					dtm.addRow(d);
				}
				for(int i = 1;i < v; i++) {
		        	 dtm.isCellEditable(i,0);
				}
				for(int i = 0;i < v; i++) {
		        	 for(int j = 0;j <= v;j++) {
		        		 if(i == j - 1)
		        		 {
		        			 dtm.setValueAt("0", i, j);
		        		 }
		        	 }
		         }
		    }
		});
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("S\u1ED1 \u0111\u1EC9nh");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(22, 33, 119, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(58, 300, 383, 53);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setText("");
		
		JButton btnNewButton = new JButton("SEND");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 int v = Integer.parseInt(comboBox.getSelectedItem().toString());
				int[][] data = new int[v][v];
				DefaultTableModel dtm = (DefaultTableModel)table.getModel();
				Boolean check = true;
				for(int i = 0;i < v; i++)
				{
					for(int j = 0;j < v;j++) {
							if((String)(dtm.getValueAt(i, j+1)) == null) {
								lblNewLabel_1.setText("Hay nhap gia tri vao table");
								lblNewLabel_1.setForeground(Color.RED);
								check = false;
							}
					}
				}
				if(check == true) {
						for(int i = 0;i < v; i++)
					{
						for(int j = 0;j < v;j++) {
								data[i][j] = Integer.parseInt((String)(dtm.getValueAt(i, j+1)));
						}
					}
	
						for(int i = 0;i < v;i++) {
				        	 for(int j = 0;j < v;j++) {
				        		 dtm.setValueAt((String)(dtm.getValueAt(i, j+1)), j, i+1);
				        		 data[i][j] = data[j][i];
				        	 }
				         }
						try {
							new ClientImplement(data, v, ipServer).setVisible(true);
							frame.setVisible(false);
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				}

		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(292, 350, 85, 30);
		contentPane.add(btnNewButton);
		
		table = new JTable();
		table.setBounds(77, 208, 479, -140);
		TbModel = new DefaultTableModel(){
			@Override
            public boolean isCellEditable(int row, int column) {
               /* Set the 11th column as editable and rest non-
                    editable */
                if(column==0){
                    return false;
                }else{
                	return true;
                }
            }
		};
		TbModel.setColumnIdentifiers(ColumnsName);	

		table.addKeyListener((KeyListener) new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event) {
            	int v = Integer.parseInt(comboBox.getSelectedItem().toString());
		    	  DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		         for(int i = 0;i < v;i++) {
		        	 for(int j = 0;j < v;j++) {
		        		 dtm.setValueAt((String)(dtm.getValueAt(i, j+1)), j, i+1);
		        	 }
		         }
            }
            @Override
            public void keyReleased(KeyEvent event) {

            }
            @Override
            public void keyPressed(KeyEvent event) {
            	
            }
        });
		
		table.setModel(TbModel);
		
		sp = new JScrollPane(table);
		sp.setViewportView(table);
		sp.setBounds(10, 73, 685, 209);
		contentPane.add(sp);
		


		
		new Thread(this).start();
		this.setVisible(true);
		
	}
	
	public void run() {
		while (true) {
			try {
				

			}catch(Exception e) {
				
			}
		}
	}
}
