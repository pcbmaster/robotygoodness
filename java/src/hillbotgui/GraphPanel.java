package hillbotgui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	
	private int panelWidth;
	private int panelHeight;
	
	private int widthBuffer;
	
	private int bottomBuffer;
	
	private ArrayList<ScaledRectangle> rects;
	
	private int strokeWidth;
	
	private GraphPanelBufferedImage bufferedImage;
	
	public GraphPanel(int panelWidth, int panelHeight){
		super();
		
		rects = new ArrayList<ScaledRectangle>();
		
		this.panelHeight = panelHeight;
		
		this.panelWidth = panelWidth;
		
		setPreferredSize(new Dimension(panelWidth,panelHeight));
		
		widthBuffer = 5;
		bottomBuffer = 10;
		strokeWidth = 1;
		
		bufferedImage = new GraphPanelBufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB, this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		bufferedImage.paintTheGoodStuff();
		
		g2d.drawImage(bufferedImage,0,0,null);
		
		g2d.dispose();
	}
	
	public ScaledRectangle getRect(int index){
		return rects.get(index);
	}
	
	public void addRect(ScaledRectangle r){
		rects.add(r);
	}

	public ArrayList<ScaledRectangle> getRects() {
		return rects;
	}
	
	public int getStrokeWidth(){
		return strokeWidth;
	}
	
	public int getWidthBuffer(){
		return widthBuffer;
	}
	
	public int getBottomBuffer(){
		return bottomBuffer;
	}
}
