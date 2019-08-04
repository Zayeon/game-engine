package collisions;

public class EndPoint {
    private AxisAlignedBoundingBox owner;
    private boolean minimum; // is minimum endpoint

    public float value;

    public EndPoint(AxisAlignedBoundingBox owner, float value, boolean minimum) {
        this.owner = owner;
        this.value = value;
        this.minimum = minimum;
    }

    public AxisAlignedBoundingBox getOwner() {
        return owner;
    }


    public boolean isMinimum() {
        return minimum;
    }
}
