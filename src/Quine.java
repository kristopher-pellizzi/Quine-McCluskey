import java.util.*;

public class Quine {

    public static void main(String[] args) {
        Set<Integer> onSet = new HashSet<Integer>();
        Set<Integer> dcSet = new HashSet<Integer>();

        System.out.println("Quanti mintermini ha la funzione?");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        System.out.println("Inserisci tutti i mintermini");
        for (int i = 0; i < n; i++) {
            onSet.add(scan.nextInt());
        }
        System.out.println("Quanti Don't Care ha la funzione?");
        n = scan.nextInt();
        if(n!=0)
            System.out.println("Inserisci tutti i DC");
        for(int i=0;i<n;i++){
            dcSet.add(scan.nextInt());
        }

        ArrayList<Integer> ondcSet = new ArrayList<>();
        ondcSet.addAll(onSet);
        ondcSet.addAll(dcSet);
        Integer[] onSetArr = ondcSet.toArray(new Integer[ondcSet.size()]);
        for(int i=0;i<onSetArr.length;i++){
            System.out.println(onSetArr[i]);
        }
        Arrays.sort(onSetArr);
        for(int i=0;i<onSetArr.length;i++){
            System.out.println(onSetArr[i]);
        }
        System.out.println();
        Implying[] implyings = new Implying[ondcSet.size()];
        int maxLength = 0;
        for (int i = 0; i < ondcSet.size(); i++) {
            implyings[i] = new Implying(Converter.binConversion(onSetArr[i]));
            if(implyings[i].getBin().getBinValue().length()>maxLength)
                maxLength=implyings[i].getBin().getBinValue().length();
        }
        Arrays.sort(implyings, Implying.comp);

        for(int i=0;i<implyings.length;i++){
            implyings[i].justifyLength(maxLength);
        }


        int fstIndex = 0;
        int sndIndex;
        int hammingDist = 0;

        /*for(int i=0;i<implyings.length;i++){
            System.out.println(implyings[i]);
        }*/

        int endTable = implyings.length;
        int currentSum=0;
        while (fstIndex < endTable) {
            sndIndex = fstIndex + 1;
            while (sndIndex < endTable) {
                //System.out.println(fstIndex + ": " + implyings[fstIndex] + " --> " + sndIndex + ": " + implyings[sndIndex]);
                hammingDist = implyings[fstIndex].compareTo(implyings[sndIndex]);
                if (hammingDist != -1) {
                    implyings[fstIndex].setMarker();
                    implyings[sndIndex].setMarker();
                    Implying newImpl = implyings[fstIndex].derivative(hammingDist);
                    newImpl.addMinterms(implyings[sndIndex].getMinterms());
                    int contained = Implying.contains(implyings, newImpl);

                    if (contained==-1) {
                        implyings = Implying.add(implyings, newImpl);
                        implyings[implyings.length - 1].justifyLength(maxLength);
                    }
                    else{
                        implyings[contained].setMinterms(newImpl.getMinterms());
                    }
                }
                sndIndex++;
            }
            fstIndex++;
            if(fstIndex==endTable && endTable<implyings.length)
                endTable=implyings.length;
        }

        System.out.println("Stampo gli implicanti");
        for(int i=0;i<implyings.length;i++){
            System.out.print(implyings[i] + " --> ");
            for(int j=0;j<implyings[i].getMinterms().length;j++) {
                System.out.printf("%d\t",implyings[i].getMinterms()[j]);
            }
            System.out.println();
        }

        for(int i = 0; i < implyings.length;i++) {
            int delete=1;
            for(int j=0;j<implyings[i].getMinterms().length;j++) {
                if (!dcSet.contains(implyings[i].getMinterms()[j])){
                    delete=0;
                }
            }
            if (implyings[i].getMarker() == 1 || delete==1) {
                implyings = Implying.delete(implyings, implyings[i]);
                i--;
            }
        }
        System.out.println();

        System.out.println();
        onSetArr = onSet.toArray(new Integer[onSet.size()]);
        CoveringTable tab = new CoveringTable(onSetArr,implyings);
        tab.print();
        int end=0;
        CoveringTable old = CoveringTable.copy(tab);
        while(end==0) {
            tab.essentiality();
            System.out.println();
            System.out.println("Essenzialita: ");
            tab.print();
            tab.rowDominance();
            System.out.println();
            System.out.println("Dominanze di riga: ");
            tab.print();
            tab.columnDominance();
            System.out.println();
            System.out.println("Dominanze di colonna:");
            tab.print();
            if(tab.equals(old) || tab.getRowsNumb()==0){
                end=1;
            }
            else{
                old=CoveringTable.copy(tab);
            }

        }
        System.out.println();
        System.out.println("Result: ");
        tab.printResult();

    }
}
