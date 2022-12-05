package PBL4;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

public class ClientImplement extends JFrame implements Serializable {

	private JPanel contentPane;
	private JTextField textField;
	private JFileChooser fileDialog;

	DataInputStream dis;
	DataOutputStream dos;
	ObjectInputStream ois;
	int n;
	int[][] data = new int[n][n];
	Draw panel, panel1;
	int j = 0;
	List<JTextField> list = new ArrayList<JTextField>();
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int[][] data = { { 0, 0 } };
					ClientImplement frame = new ClientImplement(data, 0);
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
	public String[] createNameNode() { //int numberOfNode
		String[] nameNode = new String[this.n];
		char s = 'A';
		nameNode[0] = String.valueOf(s);
		for (int i = 1; i < this.n; i++) {
			s += 1;
			nameNode[i] = String.valueOf(s);
		}
		return nameNode;
	}
	
	public ArrayList<String> listLine()
	{
		String[] nameNode = createNameNode();
		ArrayList<String> rs = new ArrayList<String>();
		
		for (int i = 0; i < this.n; i++) {
			for (int j = i; j < this.n; j++) {
				if (i == j)
					continue;
				if (data[i][j] != 0) {
					//listVertex.get(i).addNeighbour(new Edge(data[i][j], listVertex.get(i), listVertex.get(j)));
					rs.add(nameNode[i] + nameNode[j]);
				}
			}
		}
		return rs;
	}
	public ClientImplement(int[][] data, int n) {

		this.data = data;
		this.n = n;
//		panel = new Draw(data, n);
//		panel.setBounds(10, 43, 445, 311);
//		contentPane.add(panel);
		
		ArrayList<String> test = listLine();
//		for(int i = 0; i < test.size(); i++)
//		{
//			System.out.print(test.get(i) + " ");
//		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ArrayList<Vertex> shortPath = new ArrayList<Vertex>();
		panel = new Draw(this.data, this.n, shortPath);
		panel.setBounds(10, 35, 445, 311);
		contentPane.add(panel);

		JLabel lblNewLabel = new JLabel("D\u1EEF li\u1EC7u");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(453, 10, 101, 37);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("LU\u1ED2NG L\u01AFU TH\u00D4NG");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(503, 43, 201, 37);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("g\u00F3i/s");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(573, 75, 72, 18);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u0110i\u1EC1u khi\u1EC3n ch\u01B0\u01A1ng tr\u00ECnh");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(123, 345, 162, 19);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("B\u1EA3ng s\u1ED1 li\u1EC7u");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> s1 = new ArrayList<String>();
				for(int i = 0;i < list.size();i++) {
					s1.add(list.get(i).getText());
				}
				DataTable dt = new DataTable(data, n, s1);
				dt.setVisible(true);
				//frame.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(25, 383, 134, 37);
		contentPane.add(btnNewButton);
		
		int count = 0;
		for(int i = 0;i < n;i++) {
			for(int j = i;j < n;j++) {
				if(data[i][j] != 0) {
					count++;
				}
			}
		}

		int b = 114;
		for(int i = 0;i < Math.round((double)count/2);i++) {
				textField = new JTextField(test.get(i)+":");
				textField.setBounds(450, b, 96, 19);
				textField.setColumns(10);
				list.add(textField);
				b += 36;
		}
		b = 114;
		for(int i = 0;i < count/2;i++) {
				textField = new JTextField(test.get((int) (i + Math.round((double)count/2)))+":");
				textField.setBounds(580, b, 96, 19);
				textField.setColumns(10);
				list.add(textField);
				b += 36;
		}

		//b += 36;
		JButton btnNewButton_3 = new JButton("D\u1EEF li\u1EC7u t\u1EEB file");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_3.setBounds(450, b, 134, 37);
		contentPane.add(btnNewButton_3);
		JLabel statusLabel = new JLabel("File name");
        statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        statusLabel.setBounds(600, b, 350, 30);
        contentPane.add(statusLabel);
		
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileDialog = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "txt");
                fileDialog.setFileFilter(filter);
                int returnVal1 = fileDialog.showOpenDialog(null);

		        FileInputStream fileInputStream = null;
		        BufferedReader bufferedReader = null;
		        
		        if(returnVal1 == JFileChooser.APPROVE_OPTION) {
		        	java.io.File f = fileDialog.getSelectedFile();
                	statusLabel.setText("File Selected :" + f.getPath());
                	
                	String url = f.getPath();
		        	try {
			            fileInputStream = new FileInputStream(url);
			            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			            String line = bufferedReader.readLine();
			            List<String> list1 = new ArrayList<String>();
			            while (line != null) {
			                System.out.println(line);
			                list1.add(line);
			                line = bufferedReader.readLine();
			            }
			            for(int i = 0;i < list.size();i++) {
							list.get(i).setText(list.get(i).getText() + list1.get(i));
						}
			        } catch(Exception e1) {
			        }
		        }

		        
			}
		});
		
		for(int i = 0;i < list.size();i++) {
			contentPane.add(list.get(i));
		}
		
		int a = 65;
		String[] value = new String[n] ;
		for(int i = 0;i < n; i++) {
			char ch = (char)a;
			value[i] = Character.toString(ch);
			a++;
		}
		
		String[] value1 = new String[n] ;
		for(int i = 0;i < n - 1;i++) {
			value1[i] = value[i+1];
		}
		
		b = (int) (114 + 36* Math.round((double)count/2) + 36);
		JLabel lblNewLabel_4 = new JLabel("\u0110\u01B0\u1EDDng \u0111i ng\u1EAFn nh\u1EA5t t\u1EEB");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(510, b, 169, 37);
		contentPane.add(lblNewLabel_4);	
		
		b += 36;
		JLabel lblNewLabel_6 = new JLabel("\u0110\u1EBFn");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(580, b, 49, 23);
		contentPane.add(lblNewLabel_6);
		
		JComboBox comboBox_1 = new JComboBox(value1);
		comboBox_1.setBounds(650, b, 101, 31);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox = new JComboBox(value);
		comboBox.setBounds(420, b, 108, 31);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				comboBox_1.removeAllItems();
				int a = 65;
				String[] value1 = new String[n] ;
				int k = 0;
				for(int i = 0;i < n; i++) {
					char ch = (char)a;
					System.out.println(Character.toString(ch) + " " + comboBox.getSelectedItem().toString());
					if(Character.toString(ch).equals(comboBox.getSelectedItem().toString()) == false) {
						value1[k] = Character.toString(ch);
						System.out.println(k + " " + value1[k]);
						comboBox_1.addItem(value1[k]);
						k++;
					}
					a++;
				}
			}
        });	
		
		contentPane.add(comboBox);

		JButton btnNewButton_1 = new JButton("K\u1EBFt qu\u1EA3");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(180, 383, 101, 37);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				if(panel1 != null) {
					panel1.setVisible(false);
				}
				// TODO Auto-generated method stub
				try {
					Socket soc = new Socket("localhost", 5000);
					dis = new DataInputStream(soc.getInputStream());
					dos = new DataOutputStream(soc.getOutputStream());
					ois = new ObjectInputStream(soc.getInputStream());
					dos.writeInt(n);
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < n; j++) {
							dos.writeInt(data[i][j]);
						}
					}
					
					int a = comboBox.getSelectedIndex();
					dos.writeInt(a);
					int b = comboBox_1.getSelectedIndex();
					if(b >= a) {
						dos.writeInt(b+1);
					}
					else {
						dos.writeInt(b);
					}
				ArrayList<Vertex> shortPath = new ArrayList<Vertex>();

				String s = (String) ois.readObject();
				System.out.println(s);
				shortPath = panel.createShortPath(s);
				panel1 = new Draw(data, n, shortPath);
				panel1.setBounds(10, 35, 445, 311);
				contentPane.add(panel1);
				} catch (Exception e1) {
				}
			}
		});

		JButton btnNewButton_2 = new JButton("K\u1EBFt th\u00FAc");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_2.setBounds(300, 383, 101, 37);
		contentPane.add(btnNewButton_2);
		


		

	}
}
