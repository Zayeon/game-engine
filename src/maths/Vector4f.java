package maths;

public class Vector4f {
    public float x, y, z, w;

    public Vector4f(float x, float y, float z, float w){
        set(x, y, z, w);
    }

    public Vector4f(float value){
        set(value, value, value, value);
    }

    public Vector4f(Vector4f copy){
        set(copy.x, copy.y, copy.z, copy.w);
    }

    public Vector4f(){
        set(0, 0, 0, 0);
    }

    public void set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public boolean equals(Vector4f other){
        return x == other.x && y == other.y && z == other.z && w == other.w;
    }

    public float length(){
        return (float) Math.sqrt(lengthSquared());
    }

    public float lengthSquared(){
        return x * x + y * y + z * z + w * w;
    }

    public void translate(float x, float y, float z, float w){
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
    }

    public void negate(){
        x = -x;
        y = -y;
        z = -z;
        w = -w;
    }

    public void normalise(){
        float l = length();
        x = x / l;
        y = y / l;
        z = z / l;
        w = w / l;
    }

    // Stupid Americans
    public void normalize(){
        normalise();
    }

    public void scale(float factor){
        x = x * factor;
        y = y * factor;
        z = z * factor;
        w = w * factor;
    }

    public static float dot(Vector4f a, Vector4f b){
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    public static float angle(Vector4f a, Vector4f b){
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float)Math.acos(dls);
    }

    public static void add(Vector4f a, Vector4f b, Vector4f dest){
        dest.set(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w);
    }

    public static Vector4f add(Vector4f a, Vector4f b){
        return new Vector4f(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w);
    }

    public static void sub(Vector4f a, Vector4f b, Vector4f dest){
        dest.set(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w);
    }

    public static Vector4f sub(Vector4f a, Vector4f b){
        return new Vector4f(a.x - b.x, a.y - b.y, a.z - b.z,a.w - b.w);
    }
}
