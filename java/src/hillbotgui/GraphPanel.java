package hillbotgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	private double rectHeight;
	
	private int panelWidth;
	private int panelHeight;
	
	private ArrayList<ScaledRectangle> rects;
	
	public GraphPanel(int panelWidth, int panelHeight){
		super();
		
		rects = new ArrayList<ScaledRectangle>();
		
		this.panelHeight = panelHeight;
		
		this.panelWidth = panelWidth;
		
		rectHeight = 0;
		
		setPreferredSize(new Dimension(panelWidth,panelHeight));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		for(ScaledRectangle r : rects){
			g2d.fill(new Rectangle2D.Double(panelWidth / rects.size() * rects.indexOf(r),panelHeight - r.getScaledHeight(), 
					panelWidth / rects.size(), r.getScaledHeight()));
		}
		
		g2d.dispose();
	}
	
	public ScaledRectangle getRect(int index){
		return rects.get(index);
	}
	
	public void addRect(ScaledRectangle r){
		rects.add(r);
	}
}
