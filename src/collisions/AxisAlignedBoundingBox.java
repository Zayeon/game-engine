package collisions;

public class AxisAlignedBoundingBox {
    private EndPoint[] min = new EndPoint[3];
    private EndPoint[] max = new EndPoint[3];

    public AxisAlignedBoundingBox(float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {
        min[0] = new EndPoint(this, minX, true);
        min[1] = new EndPoint(this, minY, true);
        min[2] = new EndPoint(this, minZ, true);

        max[0] = new EndPoint(this, maxX, false);
        max[1] = new EndPoint(this, maxY, false);
        max[2] = new EndPoint(this, maxZ, false);
    }

    public EndPoint getMinX(){
        return min[0];
    }

    public EndPoint getMaxX(){
        return max[0];
    }

    public EndPoint getMinY(){
        return min[1];
    }

    public EndPoint getMaxY(){
        return max[1];
    }

    public EndPoint getMinZ(){
        return min[2];
    }

    public EndPoint getMaxZ(){
        return max[2];
    }
}
