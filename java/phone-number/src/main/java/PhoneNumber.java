public class PhoneNumber {
    public static final String INVALID_NUMBER = "0000000000";

    private final String areaCode;
    private final String privat1;
    private final String privat2;

    public PhoneNumber(String number) {
        final String digits =
                number.chars()
                        .filter(Character::isDigit)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();

        final String digits10 = toDigits10(digits);

        this.areaCode = digits10.substring(0, 3);
        this.privat1 = digits10.substring(3, 6);
        this.privat2 = digits10.substring(6);
    }

    private String toDigits10(String number) {
        final int len = number.length();
        return len == 10? number:
                (len == 11 && number.charAt(0) == '1')? number.substring(1):
                        INVALID_NUMBER;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getNumber() {
        return areaCode + privat1 + privat2;
    }

    public String pretty() {
        return String.format("(%s) %s-%s", areaCode, privat1, privat2);
    }
}
