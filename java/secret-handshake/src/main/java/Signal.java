enum Signal {
    WINK(1),
    DOUBLE_BLINK(2),
    CLOSE_YOUR_EYES(2 << 1),
    JUMP(2 << 2),
    REVERSE(2 << 3);

    private final int code;
    
    Signal(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
