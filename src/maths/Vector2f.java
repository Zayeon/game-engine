package maths;

public class Vector2f {
    public float x, y;

    public Vector2f(float x, float y){
        set(x, y);
    }

    public Vector2f(float value){
        set(value, value);
    }

    public Vector2f(Vector2f copy){
        set(copy.x, copy.y);
    }

    public Vector2f(){
        set(0, 0);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2f other){
        return x == other.x && y == other.y;
    }

    public float length(){
        return (float) Math.sqrt(lengthSquared());
    }

    public float lengthSquared(){
        return x * x + y * y;
    }

    public void translate(float x, float y){
        this.x += x;
        this.y += y;
    }

    public void negate(){
        x = -x;
        y = -y;
    }

    public void normalise(){
        float l = length();
        x = x / l;
        y = y / l;
    }

    // Stupid Americans
    public void normalize(){
        normalise();
    }

    public void scale(float factor){
        x = x * factor;
        y = y * factor;
    }

    public static float dot(Vector2f a, Vector2f b){
        return a.x * b.x + a.y * b.y;
    }

    public static float angle(Vector2f a, Vector2f b){
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float)Math.acos(dls);
    }

    public static void add(Vector2f a, Vector2f b, Vector2f dest){
        dest.set(a.x + b.x, a.y + b.y);
    }

    public static Vector2f add(Vector2f a, Vector2f b){
        return new Vector2f(a.x + b.x, a.y + b.y);
    }

    public static void sub(Vector2f a, Vector2f b, Vector2f dest){
        dest.set(a.x - b.x, a.y - b.y);
    }

    public static Vector2f sub(Vector2f a, Vector2f b){
        return new Vector2f(a.x - b.x, a.y - b.y);
    }


}
