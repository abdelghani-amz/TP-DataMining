package TP6;

public class Element implements Comparable<Element>{
    public float[] attr;
    public boolean[] missing;
    public String type,rawData;
    public double distance ;
    public int cluster ;


    public Element(String data) {
        this.rawData = data;
        String[] splitResults = data.split(",");
        attr = new float[4];
        missing = new boolean[4];
        for(int i = 0 ; i < 4 ; i++){
            try{
                attr[i] = Float.parseFloat(splitResults[i]);
                missing[i] = false;
            }catch (Exception e){
                missing[i] = true;
            }

        }
        type = splitResults[4];
        cluster = -1 ;

    }
    
    public Element() {
    	
    }
    
    public int compareTo(Element p)  {
    	
    	if(distance - p.distance > 0) return 1; 
    	else if (distance - p.distance < 0) return -1 ;
    	else return 0 ;
    }
    
    public int compare(Element p1, Element p2) {
    	
    	if(p1.distance - p2.distance > 0) return 1; 
    	else if (p1.distance - p2.distance < 0) return -1 ;
    	else return 0 ;
    	
    }
    
    public String toString() {
    	
    	return "" + attr[0] + " " + attr[1] + " " + attr[2] + " " + attr[3] + " " + type + " "+ cluster +"\n";
    }
    
}
