package PBL4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Draw extends JPanel {
	int r = 100;
	int n;
	int[][] data = new int[n][n];
	String[] nameNode;
	public ArrayList<Vertex> listVertex = new ArrayList<Vertex>();
	ArrayList<Vertex> shortPath = new ArrayList<Vertex>();
	Draw(int[][] data, int n, ArrayList<Vertex> shortPath) {
		setPreferredSize(new Dimension(420, 420));
		this.data = data;
		this.n = n;
		this.shortPath = shortPath;
		createVertex();
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
	public void createNameNode() { //int numberOfNode
		nameNode = new String[this.n];
		char s = 'A';
		nameNode[0] = String.valueOf(s);
		for (int i = 1; i < this.n; i++) {
			s += 1;
			nameNode[i] = String.valueOf(s);
		}
	}

	public void createVertex() {//int numberOfNode
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
				if (data[i][j] != 0) {
					listVertex.get(i).addNeighbour(new Edge(data[i][j], listVertex.get(i), listVertex.get(j)));
				}
			}
		}
	}

	public void drawLineSegment(Point from, Point to, Color c, int size, String label, Graphics2D g) {
		g.setColor(c);
		g.setStroke(new BasicStroke(size));
		int x1 = from.getX();
		int y1 = from.getY();
		int x2 = to.getX();
		int y2 = to.getY();
		g.drawLine(x1, y1, x2, y2);
		int sx = (int) ((x1 + x2) / 2.1);
		int sy = (int) ((y1 + y2) / 2.1);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.setColor(Color.red);
		g.drawString(label, sx, sy);
	}
	public ArrayList<Vertex> createShortPath(String s)
	{
		String[] nameVertex = s.split("");
		ArrayList<Vertex> shortPath = new ArrayList<Vertex>();
		for(int i = 0; i < s.length(); i++)
		{
			for(int j = 0; j < this.listVertex.size(); j++)
			{
				if(nameVertex[i].equals(this.listVertex.get(j).toString()))
				{
					shortPath.add(listVertex.get(j));
				}	
			}
		}
		return shortPath;
	}

	@Override
	public void paint(Graphics g) {
//		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));

		for (int i = 0; i < listVertex.size(); i++) {
			for (int j = 0; j < listVertex.get(i).getEdges().size(); j++) {
				List<Edge> listEdge = new ArrayList<Edge>();
				listEdge = listVertex.get(i).getEdges();
				drawLineSegment(listEdge.get(j).getStartVertex().getPoint(),
						listEdge.get(j).getTargetVertex().getPoint(), Color.black, 3,
						String.valueOf(listEdge.get(j).getWeight()), g2);
			}
		}

//		Dijkstra dijkstra = new Dijkstra();
//		dijkstra.computePath(listVertex.get(0));
//		List<Vertex> shortPath = new ArrayList<Vertex>();
//		shortPath = dijkstra.getShortestPathTo(listVertex.get(2));
//		for (int i = 0; i < shortPath.size() - 1; i++) {
//			drawLineSegment(shortPath.get(i).getPoint(), shortPath.get(i + 1).getPoint(), Color.red, 3, "", g2);
//		}
		if(this.shortPath.size() > 0)
		{
			for (int  i = 0; i < shortPath.size() - 1; i++)
			{
				drawLineSegment(this.shortPath.get(i).getPoint(), this.shortPath.get(i + 1).getPoint(), Color.red, 3, "", g2);
			}
		}

		for (int i = 0; i < n; ++i) {
			int x = listVertex.get(i).getPoint().getX();
			int y = listVertex.get(i).getPoint().getY();

			g2.setColor(Color.yellow);
			g2.setStroke(new BasicStroke(10));
			g2.draw(new Line2D.Float(x, y, x, y));

			String s1 = nameNode[i];
			g.setColor(Color.red);
			int xlb = (int) (200 - ((r * 1.5) + 15) * Math.cos(2 * Math.PI * i / n));
			int ylb = (int) (150 - (r + 30) * Math.sin(2 * Math.PI * i / n));
			g.drawString(s1, xlb, ylb);
		}
	}
}
