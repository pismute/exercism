enum Signal {
    WINK(1),
    DOUBLE_BLINK(1 << 1),
    CLOSE_YOUR_EYES(1 << 2),
    JUMP(1 << 3),
    REVERSE(1 << 4);

    private final int code;
    
    Signal(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
