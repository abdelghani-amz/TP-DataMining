package TP1;

public class Element {
    public float[] attr;
    public boolean[] missing;
    public String type,rawData;


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

    }
}
