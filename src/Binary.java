public class Binary {
    private int decValue;
    private String binValue;
    private int sum;

    public int getDecValue() {
        return decValue;
    }

    public String getBinValue() {
        return binValue;
    }

    public int getSum() {
        return sum;
    }

    public void setDecValue(int decValue) {
        this.decValue = decValue;
    }

    public void setBinValue(String binValue) {
        this.binValue = binValue;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }



    public Binary(){
        decValue=0;
        binValue="";
        sum=0;
    }

    public Binary(Binary other){
        this.decValue=other.decValue;
        this.binValue=other.binValue;
        this.sum=other.sum;
    }

    public boolean equals(Binary other){
        return(this.binValue.equals(other.binValue));
    }
}
