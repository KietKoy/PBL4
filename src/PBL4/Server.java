package PBL4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public final static int daytimePort = 8000;

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		ServerSocket server;
		Socket theConnection;
		try {
			server = new ServerSocket(daytimePort);
			while (true) {
				Socket s = server.accept();
				Xuly x = new Xuly(this, s);
				x.start();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	class Xuly extends Thread {
		Server server;
		Socket soc;
		int r = 100;
		String[] nameNode;
		int n;
		int size_arrLd;
		int arrC[];
		int data[][];
		int arrLd[];
		ArrayList<Vertex> listVertex = new ArrayList<Vertex>();

		public Xuly(Server server, Socket soc) {
			this.server = server;
			this.soc = soc;
		}

		public void createNameNode() {
			nameNode = new String[this.n];
			char s = 'A';
			nameNode[0] = String.valueOf(s);
			for (int i = 1; i < this.n; i++) {
				s += 1;
				nameNode[i] = String.valueOf(s);
			}
		}

		public void createVertex() {
			createNameNode();
			for (int i = 0; i < this.n; i++) {
				listVertex.add(new Vertex(nameNode[i], null));
			}

			for (int i = 0; i < this.n; i++) {
				for (int j = 0; j < this.n; j++) {
					if (i == j)
						continue;
					if (this.data[i][j] != 0) {
						listVertex.get(i).addNeighbour(new Edge(this.data[i][j], listVertex.get(i), listVertex.get(j)));
					}
				}
			}
		}

		public void run() {
			try {
				System.out.println("hehe");
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
				ObjectOutputStream ous = new ObjectOutputStream(soc.getOutputStream());
				String chucnang = dis.readUTF();
				System.out.println(chucnang);
				if(chucnang.equals("Phantichmang")) {
					this.n = dis.readInt();
					System.out.println(n);
					this.size_arrLd = dis.readInt();
					System.out.println(this.size_arrLd);
					this.data = new int[this.n][this.n];
					this.arrC = new int[this.size_arrLd];
					this.arrLd = new int[this.size_arrLd];
					int totalLd  = 0;
					for(int i = 0; i < this.size_arrLd; i++) {
						this.arrLd[i] = dis.readInt();
						totalLd += this.arrLd[i];
//						System.out.print(this.arrLd[i]);
					}
					int  k = 0;
					for (int i = 0; i < this.n; i++) {
						for (int j = 0; j < this.n; j++) {
							this.data[i][j] = dis.readInt();
							if(i < j && data[i][j] > 0) {
								this.arrC[k] = data[i][j];
//								System.out.print(this.arrC[k] + " ");
								k++;
							}
						}
					}
					double arrmC[] = new double[this.size_arrLd];
					double arrT[] = new double[this.size_arrLd];
					double arrG[] = new double[this.size_arrLd];
					for(int i = 0; i < this.size_arrLd; i++) {
						DecimalFormat df = new DecimalFormat("#.###");
						arrmC[i] = (double)(arrC[i]*1000)/800;
						arrT[i] = Math.round(1000/(arrmC[i] - arrLd[i]));
						arrG[i] = (double)arrLd[i]/totalLd;
						//Làm tròn
//						arrmC[i] = Double.parseDouble(df.format(arrmC[i]));
//						arrT[i] = Double.parseDouble(df.format(arrT[i]));
						arrG[i] = Double.parseDouble(df.format(arrG[i]));
						dos.writeDouble(arrmC[i]);
						dos.writeDouble(arrT[i]);
						dos.writeDouble(arrG[i]);
					}
				}
				
//				System.out.println("Connect to server");
//				for (int i = 0; i < this.n; i++) {
//					for (int j = 0; j < this.n; j++) {
//						System.out.print(this.data[i][j] + " ");
//					}
//					System.out.println();
//				}
//				int a = dis.readInt();
//				int b = dis.readInt();
//				System.out.println(a + " " + b);
//				String rs = "";
//				createVertex();
//				Dijkstra dijkstra = new Dijkstra();
//				dijkstra.computePath(this.listVertex.get(a));
//				List<Vertex> shortPath = dijkstra.getShortestPathTo(this.listVertex.get(b));
//				for (int i = 0; i < shortPath.size(); i++) {
//					rs = rs + shortPath.get(i).toString();
//				}
//				System.out.println(rs);
//				ous.writeObject(rs);

			} 
				catch (Exception e1) {

			}
		}

	}
}
