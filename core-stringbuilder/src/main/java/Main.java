public class Main {
    public static void main(String[] args) {
        var sb = new CustomStringBuilder();

        String str1 = sb.append("ab").append("cd").toString();
        System.out.println("str1: " + str1);
        System.out.println("----------------");

        String str2 = sb.undo().toString();
        System.out.println("str2: " + str2);
        System.out.println("----------------");

        String str3 = sb.undo().toString();
        System.out.println("str3: " + str3);
        System.out.println("----------------");

        String str4 = sb.undo().undo().undo().toString();

    }
}