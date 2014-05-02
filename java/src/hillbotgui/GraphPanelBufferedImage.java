package hillbotgui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GraphPanelBufferedImage extends BufferedImage {
	private GraphPanel panel;
	private ArrayList<ScaledRectangle> rects;

	public GraphPanelBufferedImage(int width, int height, int imageType, GraphPanel panel) {
		super(width, height, imageType);
		
		this.panel = panel;
		rects = panel.getRects();
	}
	
	public void paintTheGoodStuff(){
		Graphics2D g2d = (Graphics2D) getGraphics().create();
		
		g2d.setBackground(new Color(0,0,0,0));
		
		g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
		
		g2d.setColor(Color.black);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setStroke(new BasicStroke(panel.getStrokeWidth()));
		
		for(ScaledRectangle r : rects){
			g2d.fill(new Rectangle2D.Double(panel.getWidth() / (rects.size()) * rects.indexOf(r) + 
						panel.getWidthBuffer() - panel.getStrokeWidth(),
					
						panel.getHeight() - r.getScaledHeight() - panel.getBottomBuffer(), 
					panel.getWidth() / (rects.size()) - panel.getWidthBuffer() - panel.getStrokeWidth(), 
					r.getScaledHeight() - panel.getStrokeWidth()));
		}
		
		g2d.dispose();
	}

}
