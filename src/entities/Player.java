package entities;

import entities.terrains.Terrain;
import maths.Vector3f;
import org.lwjgl.glfw.GLFW;
import renderEngine.NewDisplayManager;
import renderEngine.models.TexturedModel;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    public static final float GRAVITY = 50;
    private static final float JUMP_POWER = 30;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain terrain){
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * NewDisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * NewDisplayManager.getFrameTimeSeconds();
        float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float)(distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed -= GRAVITY * NewDisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * NewDisplayManager.getFrameTimeSeconds(), 0);

        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight){
            upwardsSpeed = 0;
            super.getPosition().y = terrainHeight;
            isInAir = false;
        }

    }

    private void jump(){
        if (!isInAir){
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }

    }

    private void checkInputs(){
        if (NewDisplayManager.isKeyDown(GLFW.GLFW_KEY_W)){
            this.currentSpeed = RUN_SPEED;

        }else if(NewDisplayManager.isKeyDown(GLFW.GLFW_KEY_S)){
            this.currentSpeed = -RUN_SPEED;

        }else {
            this.currentSpeed = 0;
        }

        if (NewDisplayManager.isKeyDown(GLFW.GLFW_KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;

        }else if (NewDisplayManager.isKeyDown(GLFW.GLFW_KEY_A)){
            this.currentTurnSpeed = TURN_SPEED;

        }else {
            this.currentTurnSpeed = 0;
        }

        if(NewDisplayManager.isKeyDown(GLFW.GLFW_KEY_SPACE)){
            jump();
        }

    }
}
