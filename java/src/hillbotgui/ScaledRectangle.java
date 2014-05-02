package hillbotgui;

public class ScaledRectangle {
	private double min;
	private double max;
	private double height;
	
	private double maxHeight;
	
	public ScaledRectangle(double smin, double smax, double smaxHeight){
		min = smin;
		max = smax;
		maxHeight = smaxHeight;
	}
	
	public double calcScaledHeight(double data){
		height = maxHeight*data/(max - min);
		return height;
	}
	
	public double getScaledHeight(){
		return height;
	}
}

