import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CoveringTable {
    private Integer[] minterms;
    private Implying[] implyings;
    private Integer[][] rows;
    private ArrayList<Implying> result = new ArrayList<>();

    public CoveringTable(Integer[] minterms,Implying[] implyings){
        this.minterms = minterms;
        this.implyings = implyings;
        rows = new Integer[implyings.length][minterms.length];
        for(int i=0;i<implyings.length;i++){
            for(int j=0;j<minterms.length;j++){
                if(Implying.search(implyings[i].getMinterms(),minterms[j])!=-1){
                    rows[i][j]=1;
                }
                else
                    rows[i][j]=0;
            }
        }
    }

    public void print(){
        if(implyings.length>0) {
            for (int i = 0; i < implyings[0].getBin().getBinValue().length(); i++) {
                System.out.print(" ");
            }
            for (int i = 0; i < minterms.length; i++) {
                System.out.printf("\t%d", minterms[i]);
            }
            System.out.println("\n");
            for (int i = 0; i < rows.length; i++) {
                System.out.print(implyings[i]);
                for (int j = 0; j < rows[0].length; j++) {
                    System.out.printf("\t%d", rows[i][j]);
                }
                System.out.println();
            }
        }
    }


    public void essentiality(){
        int i,j;
        int fstIndex = 0;
        int sndIndex = 0;
        for(j = 0;j<minterms.length;j++){
            int counter = 0;
            for(i = 0;i<implyings.length;i++){
                if(rows[i][j]==1){
                    counter++;
                    fstIndex=i;
                }
            }
            if(counter==1){
                deleteRow(implyings[fstIndex],counter,1);
                j=-1;
            }
        }
    }

    private void deleteRow(Implying toDelete, Integer addToResult, Integer deleteMinterms){
        Integer[][] newRows;
        ArrayList<Integer> toDeleteMinterms = new ArrayList<>();
        Integer[] newMinterms;
        int counter = 0;
        int newI=0,newJ=0;

        for(int i=0;i<implyings.length;i++){
            if(implyings[i].equals(toDelete)){
               for(int j=0;j<minterms.length;j++){
                   if(deleteMinterms!=0 && rows[i][j]==1){
                       toDeleteMinterms.add(minterms[j]);
                       counter++;
                   }
               }
            }
        }

        newRows = new Integer[implyings.length - 1][minterms.length - counter];
        newI=0;
        for(int i=0;i<implyings.length;i++){
            newJ=0;
            if(!implyings[i].equals(toDelete)){
                for(int j=0;j<minterms.length;j++){
                    if(!toDeleteMinterms.contains(minterms[j])){
                        newRows[newI][newJ] = rows[i][j];
                        newJ++;
                    }
                }
                newI++;
            }
        }

        newJ=0;
        newMinterms = new Integer[minterms.length - counter];
        for(int j=0;j<minterms.length;j++){
            if(!toDeleteMinterms.contains(minterms[j])){
                newMinterms[newJ] = minterms[j];
                newJ++;
            }
        }


        implyings=Implying.delete(implyings,toDelete);
        if(addToResult!=0)
            result.add(toDelete);
        rows=newRows;
        minterms=newMinterms;


        for(int i=0;i<implyings.length;i++){
            counter=0;
            for(int j=0;j<minterms.length;j++){
                if(rows[i][j]==1){
                    counter++;
                }
            }
            if(counter==0){
                deleteRow(implyings[i],counter,1);
            }
        }


    }


    private void deleteColumn(Integer toDelete){
        Integer[][] newRows = new Integer[implyings.length][minterms.length - 1];
        int newJ = 0;

        for(int i=0;i<implyings.length;i++){
            newJ=0;
            for(int j=0;j<minterms.length;j++){
                if(!minterms[j].equals(toDelete)){
                    newRows[i][newJ] = rows[i][j];
                    newJ++;
                }
            }
        }

        newJ=0;
        Integer[] newMinterms = new Integer[minterms.length - 1];
        for(int j=0;j<minterms.length;j++){
            if(!minterms[j].equals(toDelete)){
                newMinterms[newJ] = minterms[j];
                newJ++;
            }
        }
        rows=newRows;
        minterms=newMinterms;
    }

    public void printResult(){
        for(Implying i : result){
            System.out.printf("%s\t",i.getBin().getBinValue());
        }
        System.out.println();
    }

    public void rowDominance(){
        ArrayList<Integer> coveredMinterms1 = new ArrayList<>();
        ArrayList<Integer> coveredMinterms2 = new ArrayList<>();
        ArrayList<Implying> toDeleteImpl = new ArrayList<>();
        for(int i=0;i<implyings.length;i++){
            coveredMinterms1.removeAll(coveredMinterms1);
            for(int k=0;k<rows[i].length;k++){
                if(rows[i][k]==1)
                    coveredMinterms1.add(minterms[k]);
            }
            for(int j=0;j<implyings.length;j++){
                coveredMinterms2.removeAll(coveredMinterms2);
                for(int k=0;k<rows[j].length;k++){
                    if(rows[j][k]==1)
                        coveredMinterms2.add(minterms[k]);
                }
                if(implyings[i]!=implyings[j] && coveredMinterms1.containsAll(coveredMinterms2) && toDeleteImpl.size()!=implyings.length - 1) {
                    toDeleteImpl.add(implyings[j]);
                }
            }
        }
        for(Implying i : toDeleteImpl) {
            deleteRow(i, 0, 0);
        }
    }


    public void columnDominance(){
        ArrayList<Implying> coveringImplyings1 = new ArrayList<>();
        ArrayList<Implying> coveringImplyings2 = new ArrayList<>();
        ArrayList<Integer> toDeleteMinterms = new ArrayList<>();
        for(int i=0;i<minterms.length;i++){
            coveringImplyings1.removeAll(coveringImplyings1);
            for(int k=0;k<implyings.length;k++){
                if(rows[k][i]==1)
                    coveringImplyings1.add(implyings[k]);
            }

            for(int j=0;j<minterms.length;j++){
                coveringImplyings2.removeAll(coveringImplyings2);
                for(int k=0;k<implyings.length;k++){
                    if(rows[k][j]==1)
                        coveringImplyings2.add(implyings[k]);
                }

                if(minterms[i]!=minterms[j] && coveringImplyings2.containsAll(coveringImplyings1) && toDeleteMinterms.size()!=minterms.length - 1){
                    toDeleteMinterms.add(minterms[j]);
                }
            }
        }

        for(Integer i : toDeleteMinterms){
            deleteColumn(i);
        }
    }

    public boolean equals(CoveringTable other){
        if(this.minterms.length==other.minterms.length && this.implyings.length==other.implyings.length)
            return true;
        return false;
    }

    public static CoveringTable copy(CoveringTable src){
        CoveringTable cpy = new CoveringTable(src.minterms,src.implyings);
        return cpy;
    }

    public int getRowsNumb(){
        return rows.length;
    }
}
