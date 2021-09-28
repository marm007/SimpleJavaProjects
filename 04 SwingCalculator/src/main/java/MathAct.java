public class MathAct {

    private String name;
    private String code;

    public MathAct(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
