package TP1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String test = "a,,c";
        System.out.println(test.split(",")[1]);

        ArrayList<Element> data = loadDataset("Dataset-Exos.txt");
        description(data);
        calculeTendanceCentrale(0,data);
        missingValues(0,data);

    }

    static ArrayList<Element> loadDataset(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        ArrayList<Element> data = new ArrayList<>();
        while (line != null) {
            if (!line.isEmpty())
                data.add(new Element(line));
            line = reader.readLine();
        }
        reader.close();
        return data;
    }
    static void description(ArrayList<Element> data){

        System.out.println("Nb instances : " + data.size());
        System.out.println("Nb attributes : " + (data.get(0).attr.length+1));
        Random rand = new Random();
        System.out.println("Instances example : ");
        for(int i = 0 ; i < 3;i++){
            int randIndex = rand.nextInt()%data.size();
            if(randIndex<0)
                randIndex*=-1;
            System.out.println(data.get(randIndex).rawData);
        }
    }

    static void calculeTendanceCentrale(int attributeIndex, ArrayList<Element> data) {
        if (attributeIndex < 0 || attributeIndex >= 4) {//todo
            System.out.println("Error: invalid index");
            return;
        }
        float sum = 0;
        List<Float> attrs = new Vector<>();

        for (Element item : data) {
            if(!item.missing[attributeIndex]){
                sum+=item.attr[attributeIndex];
                attrs.add(item.attr[attributeIndex]);
            }


        }
        System.out.println("Moy de l'attribut "+attributeIndex+" : "+sum/ data.size());
        Collections.sort(attrs);
        float med = attrs.get(attrs.size()/2) ;
        if(attrs.size()%2==0){
            med += attrs.get(attrs.size()/2+1);
            med/=2;
        }
        System.out.println("Mediane de l'attribut "+attributeIndex+" : "+med);
        List<Float> modItems = new ArrayList<>();
        float currentItem = attrs.get(0);
        int currentFreq = 1 , currentMaxFreq = 0;

        for (int i = 1; i < attrs.size() ; i++){
            if(attrs.get(i)==currentItem){
                currentFreq++;
            }else{
                currentItem = attrs.get(i);
                if(currentMaxFreq<currentFreq){
                    currentMaxFreq=currentFreq;
                }
                currentFreq=1;
            }

        }
        currentFreq = 0;
        currentItem = attrs.get(0);
        System.out.println("Modes (freq : "+currentMaxFreq+"): ");
        for (int i = 1; i < attrs.size() ; i++){
            if(attrs.get(i)==currentItem){
                currentFreq++;
            }else{
                if(currentFreq==currentMaxFreq){
                    modItems.add(currentItem);
                    System.out.print(currentItem+" , ");
                }
                currentItem = attrs.get(i);
            }
        }
       float q0=0,q1=attrs.get(attrs.size()/4) ,q2=med,q3=attrs.get(attrs.size()*3/4),q4=0;
        q4 = Collections.max(attrs);
        q0= Collections.min(attrs);

        if((attrs.size()/4)%2==0){
            q1 += attrs.get(attrs.size()/4+1);
            q1/=2;
        }
        if((attrs.get(attrs.size()*3/4))%2==0){
            q3 += attrs.get(attrs.size()*3/4+1);
            q3/=2;
        }
        System.out.println();
        System.out.println("Q0: " + q0);
        System.out.println("Q1: " + q1);
        System.out.println("Q2: " + q2);
        System.out.println("Q3: " + q3);
        System.out.println("Q4: " + q4);
    }
    static void  missingValues(int attrIndex , ArrayList<Element> data){
        int missingCount=0;
        for (Element item : data) {
            missingCount += item.missing[attrIndex] ? 1 : 0;
        }
        System.out.println("Nb missing of attr ("+attrIndex+") : "+missingCount+"("+(float)missingCount*100/(float)data.size() +"%)");
    }
}
