import java.util.HashMap;

// ColorCombiner.java
// Takes two colors and combines them to either an adding or subtracting property
public class ColorCombiner {
	private HashMap<String,HashMap<String,String>> colorAdder;
	private HashMap<String,HashMap<String,String>> colorSubtractor;
	
	public ColorCombiner(){
		// colorAdder Setup
		colorAdder.put("red", new HashMap<String,String>());
			colorAdder.get("red").put("red", "red");
			colorAdder.get("red").put("orange", "orange");
			colorAdder.get("red").put("yellow", "orange");
			colorAdder.get("red").put("green", "black");
			colorAdder.get("red").put("blue", "purple");
			colorAdder.get("red").put("purple", "black");
		colorAdder.put("orange", new HashMap<String,String>());
			colorAdder.get("orange").put("red", "red");
			colorAdder.get("orange").put("orange", "orange");
			colorAdder.get("orange").put("yellow", "yellow");
			colorAdder.get("orange").put("green", "black");
			colorAdder.get("orange").put("blue", "black");
			colorAdder.get("orange").put("purple", "black");
		colorAdder.put("yellow", new HashMap<String,String>());
			colorAdder.get("yellow").put("red", "orange");
			colorAdder.get("yellow").put("orange", "orange");
			colorAdder.get("yellow").put("yellow", "yellow");
			colorAdder.get("yellow").put("green", "green");
			colorAdder.get("yellow").put("blue", "green");
			colorAdder.get("yellow").put("purple", "black");
		colorAdder.put("green", new HashMap<String,String>());
			colorAdder.get("green").put("red", "black");
			colorAdder.get("green").put("orange", "black");
			colorAdder.get("green").put("yellow", "yellow");
			colorAdder.get("green").put("green", "green");
			colorAdder.get("green").put("blue", "blue");
			colorAdder.get("green").put("purple", "black");
		colorAdder.put("blue", new HashMap<String,String>());
			colorAdder.get("blue").put("red", "purple");
			colorAdder.get("blue").put("orange", "black");
			colorAdder.get("blue").put("yellow", "green");
			colorAdder.get("blue").put("green", "green");
			colorAdder.get("blue").put("blue", "blue");
			colorAdder.get("blue").put("purple", "purple");
		colorAdder.put("purple", new HashMap<String,String>());
			colorAdder.get("purple").put("red", "black");
			colorAdder.get("purple").put("orange", "black");
			colorAdder.get("purple").put("yellow", "black");
			colorAdder.get("purple").put("green", "black");
			colorAdder.get("purple").put("blue", "blue");
			colorAdder.get("purple").put("purple", "purple");
		
		// colorSubtractor Setup
		colorSubtractor.put("red", new HashMap<String,String>());
			colorSubtractor.get("red").put("red", "white");
			colorSubtractor.get("red").put("yellow", "red");
			colorSubtractor.get("red").put("blue", "red");
		colorSubtractor.put("orange", new HashMap<String,String>());
			colorSubtractor.get("orange").put("red", "yellow");
			colorSubtractor.get("orange").put("yellow", "red");
			colorSubtractor.get("orange").put("blue", "orange");
		colorSubtractor.put("yellow", new HashMap<String,String>());
			colorSubtractor.get("yellow").put("red", "yellow");
			colorSubtractor.get("yellow").put("yellow", "white");
			colorSubtractor.get("yellow").put("blue", "yellow");
		colorSubtractor.put("green", new HashMap<String,String>());
			colorSubtractor.get("green").put("red", "green");
			colorSubtractor.get("green").put("yellow", "blue");
			colorSubtractor.get("green").put("blue", "yellow");
		colorSubtractor.put("blue", new HashMap<String,String>());
			colorSubtractor.get("blue").put("red", "blue");
			colorSubtractor.get("blue").put("yellow", "blue");
			colorSubtractor.get("blue").put("blue", "white");
		colorSubtractor.put("purple", new HashMap<String,String>());
			colorSubtractor.get("purple").put("red", "blue");
			colorSubtractor.get("purple").put("yellow", "purple");
			colorSubtractor.get("purple").put("blue", "red");
	}
	
	// Combine a color with an adding color
	public String addColor(String in, String fall){
		return colorAdder.get(in).get(fall);
	}
	
	// Combine a color with a subtracting color
	public String subtractColor(String in, String fall) throws Exception{
		if(fall.equals("orange") || fall.equals("green") || fall.equals("purple")){throw new Exception();}
		return colorSubtractor.get(in).get(fall);
	}
	
	// Combines both add and subtract functions with an additional variable
	public String combineColor(String in, String fall, boolean subtract) throws Exception{
		if(subtract != true){
			return colorAdder.get(in).get(fall);
		}
		else{
			if(fall.equals("orange") || fall.equals("green") || fall.equals("purple")){throw new Exception();}
			return colorSubtractor.get(in).get(fall);
		}
	}
}
