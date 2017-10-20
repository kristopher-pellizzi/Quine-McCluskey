import java.util.Arrays;
import java.util.Comparator;

public class Implying implements Comparable<Implying>{
    private Binary bin;
    private Integer[] minterms;
    private int marker;

    public Implying(){
        bin=null;
        minterms = null;
        marker=0;
    }

    public Implying(Binary bin) {
        this.bin = bin;
        minterms = new Integer[1];
        minterms[0]=bin.getDecValue();
        marker=0;
    }

    public Binary getBin() {
        return bin;
    }

    public void setBin(Binary bin) {
        this.bin = bin;
    }

    public int getMarker() {
        return marker;
    }

    public void setMarker() {
        this.marker = 1;
    }

    public Integer[] getMinterms() {
        return minterms;
    }

    public void setMinterms(Integer[] minterms) {
        int origLength = this.minterms.length;
        this.minterms = Arrays.copyOf(this.minterms,origLength + minterms.length);
        int index = origLength;
        for(int i=0;i<minterms.length;i++){
            if(search(this.minterms,minterms[i])==-1){
                this.minterms[index]=minterms[i];
                index++;
            }
        }
        this.minterms=Arrays.copyOf(this.minterms,index);

    }

    public void resetMinterms(Integer[] minterms){
        this.minterms=minterms;
    }

    static final Comparator<Implying> comp = new Comparator<Implying>(){
        public int compare(Implying fst, Implying snd){
            int fstSum = fst.bin.getSum();
            int sndSum = snd.bin.getSum();

            if(fstSum > sndSum)
                return 1;
            else
                if(fstSum < sndSum)
                    return -1;
                else
                    return 0;

        }
    };

    @Override
    public int compareTo(Implying other) {
        String fst = bin.getBinValue();
        String snd = other.bin.getBinValue();
        int i = 0;
        int changes = -1;

        if(this.bin.getSum()!=other.bin.getSum() - 1)
            return -1;
        while(i < fst.length()){
            if(fst.charAt(i)!=snd.charAt(i)){
                if(changes == -1)
                    changes = i;
                else
                    return -1;
            }
            i++;
        }
        return changes;
    }

    public static Implying[] add(Implying[] arr, Implying newImplying){
        int i = 0;
        Implying[] ret = new Implying[arr.length + 1];

        ret = Arrays.copyOf(arr,arr.length+1);
        ret[arr.length] = newImplying;
        return ret;
    }

    public static Implying[] delete(Implying[] arr, Implying toDelete){
        int i = 0;
        Implying[] ret = new Implying[arr.length - 1];

        while(!arr[i].equals(toDelete)){
            ret[i] = arr[i];
            i++;
        }
        i++;
        while(i<arr.length){
            ret[i - 1] = arr[i];
            i++;
        }
        return ret;
    }

    public Implying derivative(int index){
        String retBinValue = new String("");

        for(int i = 0; i < bin.getBinValue().length() ; i ++){
            if(i!=index)
                retBinValue = retBinValue + bin.getBinValue().charAt(i);
            else
                retBinValue = retBinValue + '-';

        }
        Binary retBin = new Binary(bin);
        retBin.setBinValue(retBinValue);
        Implying ret = new Implying(retBin);
        ret.setMinterms(minterms);
        if(this.bin.getBinValue().charAt(index)==1)
            ret.bin.setSum(ret.bin.getSum()-1);
        return ret;
    }

    public static int contains(Implying[] arr, Implying newImpl){
        for(int i=0;i<arr.length;i++) {
            if (arr[i].equals(newImpl))
                return i;
        }
        return -1;
    }

    public boolean equals(Implying other) {
        return (this.bin.equals(other.bin));
    }

    public void justifyLength(int length){
        String bin = this.bin.getBinValue();
        int oldLength = this.bin.getBinValue().length();
        String toAdd = new String("");

        for(int i=0;i<length-oldLength;i++){
            toAdd = toAdd + "0";
        }
        this.bin.setBinValue(toAdd + bin);

    }

    public void addMinterms(Integer[] minterms){
        int index = this.minterms.length;
        this.minterms = Arrays.copyOf(this.minterms,this.minterms.length + minterms.length);
        int i=0;

        for(;index<this.minterms.length;index++){
            this.minterms[index]=minterms[i];
            i++;
        }
    }

    public static int search(Integer[] arr, Integer key){
        for(int i=0;i<arr.length;i++){
            if(arr[i]==null)
                return -1;
            if(arr[i].equals(key))
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return bin.getBinValue();
    }

}
