import java.util.Arrays;
import java.util.Comparator;

public class Minterm {
    private Binary bin;

    public Minterm(){
        bin = null;
    }

    public Minterm(Binary bin){
        this.bin=bin;
    }

    public Binary getBin() {
        return bin;
    }

    public void setBin(Binary bin) {
        this.bin = bin;
    }



    public static Minterm[] delete(Minterm[] minterms, Minterm toDelete){
        Minterm[] ret = new Minterm[minterms.length - 1];
        int index=0;

        for(int i=0;i<minterms.length;i++){
            if(!minterms[i].bin.equals(toDelete)){
                ret[index]=minterms[i];
                index++;
            }
        }
        return ret;
    }

    public static Integer[] delete(Integer[] minterms, Integer toDelete){
        Integer[] ret = new Integer[minterms.length - 1];
        int index=0;

        for(int i=0;i<minterms.length;i++){
            if(!minterms[i].equals(toDelete)){
                ret[index]=minterms[i];
                index++;
            }
        }
        return ret;
    }

    static final Comparator<Minterm> comp = new Comparator<Minterm>(){
        public int compare(Minterm fst, Minterm snd){
            int fstSum = fst.getBin().getSum();
            int sndSum = snd.getBin().getSum();

            if(fstSum<sndSum)
                return -1;
            else
            if(fstSum==sndSum)
                return 0;
            else
                return 1;
        }
    };
}

