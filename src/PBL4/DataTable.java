package PBL4;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.JTable;

public class DataTable extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	JScrollPane sp;
	int n;
	int[][] data = new int[n][n];
	String[] columnNames = {"i", "Line", "Ld(pkts/sec)", "C(kbps)", "mC(pkts/sec)", "T(msec)", "Trong so"};
	List<String> list1 = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int[][] data = {{0,0}};
					List<String> list = new ArrayList<String>();
					DataTable frame = new DataTable(data, 0, list);
					frame.setTitle("Bang phan tich mang con");

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
	public DataTable(int[][] data, int n, List<String> s) {
		this.data = data;
		this.n = n;
		this.list1 = s;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 643, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea("Bang phan tich mang con su dung kich thuoc trung binh cua goi la 800"
				+ "\nbits. Luong luu thong nguoc (BA,CB...) cung giong luong luu thong "
				+ "\nthuan(AB,BC,...) "
				+ "\n\nLd(pkts/sec : so goi tin truyen"
				+ "\nC(kbps) : dung luong trong so tren moi"
				+ "\nmC(pkts/sec) : so trung binh cac goi/s moi duong"
				+ "\nT(msec) : do tre trung binh cua moi duong");
		textArea.setBounds(10, 257, 609, 156);
		contentPane.add(textArea);
		
		table = new JTable();
		table.setBounds(713, 36, -675, 277);
		DefaultTableModel TbModel = new DefaultTableModel();
		TbModel.setColumnIdentifiers(columnNames);	
		table.setModel(TbModel);
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0;i < n;i++) {
			for(int j = i;j < n;j++) {
				if(data[i][j] != 0) {
					list.add(data[i][j]);
				}
			}
		}
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		dtm.setRowCount(0);
		int a1 = 65;
		for(int i = 0;i < list.size(); i++) {
			String[] d = new String[list.size()];
			d[0] = Character.toString(a1);
			a1++;
			dtm.addRow(d);
		}
		for(int i = 0;i < list.size();i++) {
        	dtm.setValueAt(Integer.toString(i+1), i, 0);
         }
		for(int i = 0;i < list.size();i++) {
			dtm.setValueAt(Integer.toString(list.get(i)), i, 2);
		}
		String[] canh = new String[list.size()];
		String[] matran = new String[list.size()];
		int k = 0;
		for(String s1 : s) {
			String[] arrOfStr = s1.split(":", 0);
			for(int i = 0;i < arrOfStr.length - 1; i++) {
				canh[k] = arrOfStr[i];
				matran[k] = arrOfStr[i+1];
				k++;
			}
		}
		for(int i = 0;i < canh.length;i++) {
			dtm.setValueAt(canh[i], i, 1);
		}
		for(int i = 0;i < matran.length;i++) {
			dtm.setValueAt(matran[i], i, 3);
		}
		sp = new JScrollPane(table);
		sp.setViewportView(table);
		sp.setBounds(10, 73, 609, 161);
		contentPane.add(sp);

	}
}
