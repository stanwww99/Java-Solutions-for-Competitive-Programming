package classics;

public class quine {
    public static void main(String[] args) {
        String s = "public class Quine {%1$c    public static void main(String[] args) {%1$c        String s = %2$c%3$s%2$c;%1$c        System.out.printf(s, 10, 34, s);%1$c    }%1$c}%1$c";
        System.out.printf(s, 10, 34, s);
    }
}
