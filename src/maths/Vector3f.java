package maths;

public class Vector3f {
    public float x, y, z;

    public Vector3f(float x, float y, float z){
        set(x, y, z);
    }

    public Vector3f(float value){
        set(value, value, value);
    }

    public Vector3f(Vector3f copy) {
        set(copy.x, copy.y, copy.z);
    }

    public Vector3f(Vector4f copy) {
        set(copy.x, copy.y, copy.z);
    }

    public Vector3f(){
        set(0, 0, 0);
    }

    public void set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Vector3f other){
        return x == other.x && y == other.y && z == other.z;
    }

    public float length(){
        return (float) Math.sqrt(lengthSquared());
    }

    public float lengthSquared(){
        return x * x + y * y + z * z;
    }

    public void translate(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void negate(){
        x = -x;
        y = -y;
        z = -z;
    }

    public void normalise(){
        float l = length();
        x = x / l;
        y = y / l;
        z = z / l;
    }

    // Stupid Americans
    public void normalize(){
        normalise();
    }

    public void scale(float factor){
        x = x * factor;
        y = y * factor;
        z = z * factor;
    }

    public static float dot(Vector3f a, Vector3f b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static float angle(Vector3f a, Vector3f b){
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float)Math.acos(dls);
    }

    public static void cross(Vector3f a, Vector3f b, Vector3f dest){
        dest.x = a.y * b.z - a.z * b.y;
        dest.y = b.x * a.z - b.z * a.x;
        dest.z = a.x * b.y - a.y * b.x;
    }

    public static Vector3f cross(Vector3f a, Vector3f b){
        Vector3f temp = new Vector3f();
        cross(a, b, temp);
        return temp;
    }

    public static void add(Vector3f a, Vector3f b, Vector3f dest){
        dest.set(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vector3f add(Vector3f a, Vector3f b){
        return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static void sub(Vector3f a, Vector3f b, Vector3f dest){
        dest.set(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vector3f sub(Vector3f a, Vector3f b){
        return new Vector3f(a.x - b.x, a.y - b.y, a.z - b.z);
    }
}
