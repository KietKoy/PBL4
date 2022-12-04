package PBL4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public final static int daytimePort = 5000;

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
		int data[][];
		ArrayList<Vertex> listVertex = new ArrayList<Vertex>();

		public Xuly(Server server, Socket soc) {
			this.server = server;
			this.soc = soc;
		}

		public ArrayList<Point> ListNode() {
			ArrayList<Point> listNode = new ArrayList<Point>();
			for (int i = 0; i < this.n; i++) {
				int x = (int) (200 - (r * 1.5) * Math.cos(2 * Math.PI * i / this.n));
				int y = (int) (150 - r * Math.sin(2 * Math.PI * i / this.n));
				Point node = new Point(x, y);
				listNode.add(node);
			}
			return listNode;
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

			ArrayList<Point> listNode = new ArrayList<Point>();
			listNode = ListNode();
			for (int i = 0; i < this.n; i++) {
				listVertex.add(new Vertex(nameNode[i], listNode.get(i)));
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
//					this.n = dis.readInt();
//					this.data = new int[this.n][this.n];
//					for(int i = 0;i < this.n;i++) {
//						for(int j = 0;j < this.n;j++) {
//							this.data[i][j] = dis.readInt();
//						}
//					}

				this.n = dis.readInt();
				this.data = new int[this.n][this.n];
				for (int i = 0; i < this.n; i++) {
					for (int j = 0; j < this.n; j++) {
						this.data[i][j] = dis.readInt();
					}
				}
				System.out.println("Connect to server");
				for (int i = 0; i < this.n; i++) {
					for (int j = 0; j < this.n; j++) {
						System.out.print(this.data[i][j] + " ");
					}
					System.out.println();
				}
				int a = dis.readInt();
				int b = dis.readInt();
				System.out.println(a + " " + b);
				String rs = "";
				createVertex();
				Dijkstra dijkstra = new Dijkstra();
				dijkstra.computePath(this.listVertex.get(a));
				List<Vertex> shortPath = dijkstra.getShortestPathTo(this.listVertex.get(b));
				for (int i = 0; i < shortPath.size(); i++) {
					rs = rs + shortPath.get(i).toString();
				}
				System.out.println(rs);
				ous.writeObject(rs);
				
//					shortPath = dijkstra.getShortestPathTo(this.listVertex.get(2));
//					ous.writeObject(shortPath.get(0));

			} catch (Exception e1) {

			}
		}

	}
}
