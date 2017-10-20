public class Converter {
    public static Binary binConversion(int dec) {
        String res = new String("");
        Integer resto = 0;
        Binary ret = new Binary();
        int sum = 0;

        ret.setDecValue(dec);
        if(dec==0)
            return new Binary();
        while (dec != 0) {
            resto = dec % 2;
            sum += resto;
            res = res + resto.toString();
            dec = dec / 2;
        }
        ret.setBinValue(reverseString(res));
        ret.setSum(sum);
        return ret;
    }

    private static String reverseString(String str) {
        String res = new String("");
        for (int i = 0; i < str.length(); i++) {
            res = res + str.charAt(str.length() - i - 1);
        }
        return res;
    }
}
