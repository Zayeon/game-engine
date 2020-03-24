package entities;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import renderEngine.NewDisplayManager;

public class Camera {

    private float distanceFromPlayer = 30;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 15, 0);
    private float pitch = 30;
    private float yaw;
    private float roll;

    private Player player;

    public Camera(Player player){
        this.player = player;
    }

    public void move(){
        //calculateZoom();
        calculateCameraMovement();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }

    public void invertPitch(){
        this.pitch = -pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateCameraPosition(float horizDistance, float verticDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;

        position.y = player.getPosition().y + verticDistance + 5;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }


    private void calculateZoom(){
//        float zoomLevel = Mouse.getDWheel() * 0.1f;
//        distanceFromPlayer -= zoomLevel;
//        distanceFromPlayer = Math.max(distanceFromPlayer, 5f); // minimum zoom
//        distanceFromPlayer = Math.min(distanceFromPlayer, 50f); // maximum zoom
    }

    private void calculateCameraMovement(){
        if(NewDisplayManager.getMouseButton(1)){
            float angleChange = NewDisplayManager.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
            float pitchChange = NewDisplayManager.getDY() * 0.1f;
            pitch -= pitchChange;
            //pitch = Math.max(pitch, 0.0f);
        }

        if(NewDisplayManager.isKeyDown(GLFW.GLFW_KEY_R)){
            distanceFromPlayer = 30;
            pitch = 30;
            angleAroundPlayer = 0;
        }
    }
}
