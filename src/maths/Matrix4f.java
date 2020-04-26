package maths;

import java.nio.FloatBuffer;

public class Matrix4f {
    public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;

    public Matrix4f(){
        setIdentity();
    }

    public void setIdentity(){
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m03 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m13 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
    }

    public void scale(Vector3f vec){
        m00 *= vec.x;
        m01 *= vec.x;
        m02 *= vec.x;
        m03 *= vec.x;
        m10 *= vec.y;
        m11 *= vec.y;
        m12 *= vec.y;
        m13 *= vec.y;
        m20 *= vec.z;
        m21 *= vec.z;
        m22 *= vec.z;
        m23 *= vec.z;
    }

    public void store(FloatBuffer buf){
        buf.put(m00);
        buf.put(m01);
        buf.put(m02);
        buf.put(m03);
        buf.put(m10);
        buf.put(m11);
        buf.put(m12);
        buf.put(m13);
        buf.put(m20);
        buf.put(m21);
        buf.put(m22);
        buf.put(m23);
        buf.put(m30);
        buf.put(m31);
        buf.put(m32);
        buf.put(m33);
    }

    public void rotate(float angle, Vector3f axis){
//        float cos = (float)Math.cos(angle);
//        float sin = (float)Math.sin(angle);
//        float oneMinusCos = 1.0f - cos;
//
//        axis.normalise();
//
//        Matrix4f temp = new Matrix4f();
//
//        temp.m00 = axis.x * axis.x * oneMinusCos + cos;
//        temp.m10 = axis.x * axis.y * oneMinusCos - axis.z * sin;
//        temp.m20 = axis.x * axis.z * oneMinusCos + axis.y * sin;
//
//        temp.m01 = axis.y * axis.x * oneMinusCos +  axis.z * sin;
//        temp.m11 = axis.y * axis.y * oneMinusCos + cos;
//        temp.m21 = axis.y * axis.z * oneMinusCos - axis.x * sin;
//
//        temp.m02 = axis.z * axis.x * oneMinusCos - axis.y * sin;
//        temp.m12 = axis.z * axis.y * oneMinusCos + axis.x * sin;
//        temp.m22 = axis.z * axis.z * oneMinusCos + cos;
//
//        temp.m33 = 1;

        Matrix4f temp = new Matrix4f();
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.x*axis.y;
        float yz = axis.y*axis.z;
        float xz = axis.x*axis.z;
        float xs = axis.x*s;
        float ys = axis.y*s;
        float zs = axis.z*s;

        float f00 = axis.x*axis.x*oneminusc+c;
        float f01 = xy*oneminusc+zs;
        float f02 = xz*oneminusc-ys;
        // n[3] not used
        float f10 = xy*oneminusc-zs;
        float f11 = axis.y*axis.y*oneminusc+c;
        float f12 = yz*oneminusc+xs;
        // n[7] not used
        float f20 = xz*oneminusc+ys;
        float f21 = yz*oneminusc-xs;
        float f22 = axis.z*axis.z*oneminusc+c;

        float t00 = m00 * f00 + m10 * f01 + m20 * f02;
        float t01 = m01 * f00 + m11 * f01 + m21 * f02;
        float t02 = m02 * f00 + m12 * f01 + m22 * f02;
        float t03 = m03 * f00 + m13 * f01 + m23 * f02;
        float t10 = m00 * f10 + m10 * f11 + m20 * f12;
        float t11 = m01 * f10 + m11 * f11 + m21 * f12;
        float t12 = m02 * f10 + m12 * f11 + m22 * f12;
        float t13 = m03 * f10 + m13 * f11 + m23 * f12;
        m20 = m00 * f20 + m10 * f21 + m20 * f22;
        m21 = m01 * f20 + m11 * f21 + m21 * f22;
        m22 = m02 * f20 + m12 * f21 + m22 * f22;
        m23 = m03 * f20 + m13 * f21 + m23 * f22;
        m00 = t00;
        m01 = t01;
        m02 = t02;
        m03 = t03;
        m10 = t10;
        m11 = t11;
        m12 = t12;
        m13 = t13;

    }

    public void translate(Vector3f vec){
        m30 += m00 * vec.x + m10 * vec.y + m20 * vec.z;
        m31 += m01 * vec.x + m11 * vec.y + m21 * vec.z;
        m32 += m02 * vec.x + m12 * vec.y + m22 * vec.z;
        m33 += m03 * vec.x + m13 * vec.y + m23 * vec.z;
    }

    public void translate(Vector2f vec){
        m30 += m00 * vec.x + m10 * vec.y;
        m31 += m01 * vec.x + m11 * vec.y;
        m32 += m02 * vec.x + m12 * vec.y;
        m33 += m03 * vec.x + m13 * vec.y;
    }

    public float determinant(){
        float f =
                m00
                        * ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32)
                        - m13 * m22 * m31
                        - m11 * m23 * m32
                        - m12 * m21 * m33);
        f -= m01
                * ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32)
                - m13 * m22 * m30
                - m10 * m23 * m32
                - m12 * m20 * m33);
        f += m02
                * ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31)
                - m13 * m21 * m30
                - m10 * m23 * m31
                - m11 * m20 * m33);
        f -= m03
                * ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31)
                - m12 * m21 * m30
                - m10 * m22 * m31
                - m11 * m20 * m32);
        return f;
    }

    public void inverse(Matrix4f dest){
        float det = determinant();
        if (det == 0){
            dest = null;
            return;
        }
        float reciprocalDet = 1f / det;

        // first row
        float t00 =  determinant3x3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
        float t01 = -determinant3x3(m10, m12, m13, m20, m22, m23, m30, m32, m33);
        float t02 =  determinant3x3(m10, m11, m13, m20, m21, m23, m30, m31, m33);
        float t03 = -determinant3x3(m10, m11, m12, m20, m21, m22, m30, m31, m32);
        // second row
        float t10 = -determinant3x3(m01, m02, m03, m21, m22, m23, m31, m32, m33);
        float t11 =  determinant3x3(m00, m02, m03, m20, m22, m23, m30, m32, m33);
        float t12 = -determinant3x3(m00, m01, m03, m20, m21, m23, m30, m31, m33);
        float t13 =  determinant3x3(m00, m01, m02, m20, m21, m22, m30, m31, m32);
        // third row
        float t20 =  determinant3x3(m01, m02, m03, m11, m12, m13, m31, m32, m33);
        float t21 = -determinant3x3(m00, m02, m03, m10, m12, m13, m30, m32, m33);
        float t22 =  determinant3x3(m00, m01, m03, m10, m11, m13, m30, m31, m33);
        float t23 = -determinant3x3(m00, m01, m02, m10, m11, m12, m30, m31, m32);
        // fourth row
        float t30 = -determinant3x3(m01, m02, m03, m11, m12, m13, m21, m22, m23);
        float t31 =  determinant3x3(m00, m02, m03, m10, m12, m13, m20, m22, m23);
        float t32 = -determinant3x3(m00, m01, m03, m10, m11, m13, m20, m21, m23);
        float t33 =  determinant3x3(m00, m01, m02, m10, m11, m12, m20, m21, m22);


        // transpose and divide by the determinant
        dest.m00 = t00*reciprocalDet;
        dest.m11 = t11*reciprocalDet;
        dest.m22 = t22*reciprocalDet;
        dest.m33 = t33*reciprocalDet;
        dest.m01 = t10*reciprocalDet;
        dest.m10 = t01*reciprocalDet;
        dest.m20 = t02*reciprocalDet;
        dest.m02 = t20*reciprocalDet;
        dest.m12 = t21*reciprocalDet;
        dest.m21 = t12*reciprocalDet;
        dest.m03 = t30*reciprocalDet;
        dest.m30 = t03*reciprocalDet;
        dest.m13 = t31*reciprocalDet;
        dest.m31 = t13*reciprocalDet;
        dest.m32 = t23*reciprocalDet;
        dest.m23 = t32*reciprocalDet;
    }

    public Matrix4f inverse(){
        Matrix4f temp = new Matrix4f();
        inverse(temp);
        return temp;
    }

    public void negate(Matrix4f dest){
        mul(this, -1, dest);
    }

    public Matrix4f negate(){
        Matrix4f temp = new Matrix4f();
        negate(temp);
        return temp;
    }

    public void transpose(Matrix4f dest){
        dest.m00 = m00;
        dest.m01 = m10;
        dest.m02 = m20;
        dest.m03 = m30;
        dest.m10 = m01;
        dest.m11 = m11;
        dest.m12 = m21;
        dest.m13 = m31;
        dest.m20 = m02;
        dest.m21 = m12;
        dest.m22 = m22;
        dest.m23 = m32;
        dest.m30 = m03;
        dest.m31 = m13;
        dest.m32 = m23;
        dest.m33 = m33;
    }

    public Matrix4f transpose(){
        Matrix4f temp = new Matrix4f();
        transpose(temp);
        return temp;
    }

    public void transform(Vector4f right, Vector4f dest){
        float x = m00 * right.x + m10 * right.y + m20 * right.z + m30 * right.w;
        float y = m01 * right.x + m11 * right.y + m21 * right.z + m31 * right.w;
        float z = m02 * right.x + m12 * right.y + m22 * right.z + m32 * right.w;
        float w = m03 * right.x + m13 * right.y + m23 * right.z + m33 * right.w;

        dest.x = x;
        dest.y = y;
        dest.z = z;
        dest.w = w;

    }

    public Vector4f transform(Vector4f right){
        Vector4f dest = new Vector4f();
        transform(right, dest);
        return dest;
    }

    public static void add(Matrix4f left, Matrix4f right, Matrix4f dest){
        dest.m00 = left.m00 + right.m00;
        dest.m01 = left.m01 + right.m01;
        dest.m02 = left.m02 + right.m02;
        dest.m03 = left.m03 + right.m03;
        dest.m10 = left.m10 + right.m10;
        dest.m11 = left.m11 + right.m11;
        dest.m12 = left.m12 + right.m12;
        dest.m13 = left.m13 + right.m13;
        dest.m20 = left.m20 + right.m20;
        dest.m21 = left.m21 + right.m21;
        dest.m22 = left.m22 + right.m22;
        dest.m23 = left.m23 + right.m23;
        dest.m30 = left.m30 + right.m30;
        dest.m31 = left.m31 + right.m31;
        dest.m32 = left.m32 + right.m32;
        dest.m33 = left.m33 + right.m33;
    }

    public static Matrix4f add(Matrix4f left, Matrix4f right) {
        Matrix4f dest = new Matrix4f();
        add(left, right, dest);
        return dest;
    }

    public static void sub(Matrix4f left, Matrix4f right, Matrix4f dest) {
        dest.m00 = left.m00 - right.m00;
        dest.m01 = left.m01 - right.m01;
        dest.m02 = left.m02 - right.m02;
        dest.m03 = left.m03 - right.m03;
        dest.m10 = left.m10 - right.m10;
        dest.m11 = left.m11 - right.m11;
        dest.m12 = left.m12 - right.m12;
        dest.m13 = left.m13 - right.m13;
        dest.m20 = left.m20 - right.m20;
        dest.m21 = left.m21 - right.m21;
        dest.m22 = left.m22 - right.m22;
        dest.m23 = left.m23 - right.m23;
        dest.m30 = left.m30 - right.m30;
        dest.m31 = left.m31 - right.m31;
        dest.m32 = left.m32 - right.m32;
        dest.m33 = left.m33 - right.m33;
    }

    public static Matrix4f sub(Matrix4f left, Matrix4f right) {
        Matrix4f dest = new Matrix4f();
        sub(left, right, dest);
        return dest;
    }

    public static void mul(Matrix4f left, Matrix4f right, Matrix4f dest) {

        dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
        dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
        dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
        dest.m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
        dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
        dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
        dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
        dest.m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
        dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
        dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
        dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
        dest.m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
        dest.m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
        dest.m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
        dest.m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
        dest.m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;

    }

    public static Matrix4f mul(Matrix4f left, Matrix4f right) {
        Matrix4f dest = new Matrix4f();
        mul(left, right, dest);
        return dest;
    }

    public static void mul(Matrix4f mat, float scalar, Matrix4f dest) {

        dest.m00 = scalar * mat.m00;
        dest.m01 = scalar * mat.m01;
        dest.m02 = scalar * mat.m02;
        dest.m03 = scalar * mat.m03;
        dest.m10 = scalar * mat.m10;
        dest.m11 = scalar * mat.m11;
        dest.m12 = scalar * mat.m12;
        dest.m13 = scalar * mat.m13;
        dest.m20 = scalar * mat.m20;
        dest.m21 = scalar * mat.m21;
        dest.m22 = scalar * mat.m22;
        dest.m23 = scalar * mat.m23;
        dest.m30 = scalar * mat.m30;
        dest.m31 = scalar * mat.m31;
        dest.m32 = scalar * mat.m32;
        dest.m33 = scalar * mat.m33;

    }

    public static Matrix4f mul(Matrix4f mat, float scalar) {
        Matrix4f dest = new Matrix4f();
        mul(mat, scalar, dest);
        return dest;
    }

    private static float determinant3x3(float t00, float t01, float t02,
                                        float t10, float t11, float t12,
                                        float t20, float t21, float t22)
    {
        return   t00 * (t11 * t22 - t12 * t21)
                + t01 * (t12 * t20 - t10 * t22)
                + t02 * (t10 * t21 - t11 * t20);
    }
}
