package TP3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import TP1.Element;


public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<Element> dataset = loadDataset("Dataset-Exos.txt");
        int attrIndex = 3;
        ArrayList<Float> attributsList = new ArrayList<>();
        for (int i = 0; i < dataset.size(); i++) {
            attributsList.add(i, dataset.get(i).attr[attrIndex]);
        }
        
        int n = attributsList.size(); // Nombre d'instances
        int k = (int) (1 + 3 * (Math.log(n))); // Nb d'intervals
        System.out.println("N = " + n + "\nK = " + k);
        float valeurMin = Collections.min(attributsList);
        float valeurMax = Collections.max(attributsList);
        System.out.println("Valeur min = " + valeurMin + "\nValeur max = " + valeurMax);
        
        double largeur = (valeurMax - valeurMin) / k;
        System.out.println("Largeur: " + largeur);

        ArrayList<Intervalle> intervalles = new ArrayList<>();
        float tmpBorneInf = 0;
        float tmpBorneSup = (float) largeur;
        for (int i = 0; i < k; i++) {
            intervalles.add(new Intervalle(tmpBorneInf, tmpBorneSup));
            tmpBorneInf += largeur;
            tmpBorneSup += largeur;
        }
        System.out.println("\nLes intervalles:");
        for (Intervalle intervalle : intervalles) {
            System.out.print(intervalle.toString() + " ; ");
        }


        System.out.println("\n\n L'intervalle de chaque instance:");
        for (float attrInstance : attributsList) {
            Intervalle attrIntervalle = null;
            for (Intervalle tmpInt : intervalles) {
                if (attrInstance >= tmpInt.borneInf && attrInstance <= tmpInt.borneSup) {
                    attrIntervalle = tmpInt;
                }
            }
            if (attrIntervalle != null) {
                System.out.println("Attribut: " + attrInstance + " - Intervalle: " + attrIntervalle.toString());
            }
        }


        System.out.println("\n\n 2--Remplacer les valeurs discrétisées par la moyenne de l'intervalle correspondant:");
        for (int i = 0; i < attributsList.size(); i++) {
            float attrInstance = attributsList.get(i);
            Intervalle attrIntervalle = null;
            for (Intervalle tmpInt : intervalles) {
                if (attrInstance >= tmpInt.borneInf && attrInstance <= tmpInt.borneSup) {
                    attrIntervalle = tmpInt;
                }
            }
            if (attrIntervalle != null) {
                attributsList.set(i, attrIntervalle.moyenne());
            }
        }
        afficherLesAttributs(attributsList);


        float valeurMinNew = 0;
        float valeurMaxNew = 1;
        float valeurMinOld = Collections.min(attributsList);
        float valeurMaxOld = Collections.max(attributsList);
        System.out.println("\n\n 3--Normaliser les valeurs du dataset:");
        for (int i = 0; i < attributsList.size(); i++) {
            float valeurIOld = attributsList.get(i);
            float valeurINew = ((valeurIOld - valeurMinOld) / (valeurMaxOld - valeurMinOld)) * (valeurMaxNew - valeurMinNew) + valeurMinNew;
            attributsList.set(i, valeurINew);
        }
        afficherLesAttributs(attributsList);
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

    static void afficherLesAttributs(ArrayList<Float> attributsList) {
        System.out.println("La liste d'attributs:");
        for (float attrInstance : attributsList) {
            System.out.print(attrInstance + " ; ");
        }
    }
}
