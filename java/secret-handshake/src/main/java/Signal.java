enum Signal {

    WINK(0), DOUBLE_BLINK(1), CLOSE_YOUR_EYES(2), JUMP(3), REVERSE(4);

    private final int code;


    Signal(int radix) {
        this.code = (int) Math.pow(2, radix);
    }

    public int getCode() {
        return code;
    }

}
