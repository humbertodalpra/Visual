package model;

public class Link {

    private Integer source;
    private Integer target;

    public Link(Integer source, Integer target) {
        this.source = source;
        this.target = target;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

}
