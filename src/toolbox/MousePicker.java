package toolbox;

import entities.Camera;
import entities.terrains.Terrain;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderEngine.NewDisplayManager;

public class MousePicker {

    private static final int MAX_TERRAIN_COLLISION_ATTEMPTS = 200;
    private static final float TERRAIN_COLLISION_PRECISION = 0.1f;

    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

    public MousePicker(Matrix4f projectionMatrix, Camera camera) {
        this.projectionMatrix = projectionMatrix;
        this.camera = camera;

        this.viewMatrix = Maths.createViewMatrix(camera);
    }

    public void update(){
        viewMatrix = Maths.createViewMatrix(camera);
        currentRay = calculateMouseRay();
    }

    // TODO: Possible optimisation?
    // This algorithm keeps checking coordinates in front of the current ray to see if it has gone past the terrain
    // then it goes back and keeps checking in a spiral to find the position of intersection
    public Vector3f getTerrainCollision(Terrain terrain){
        Vector3f cameraPos = camera.getPosition();
        boolean isCameraAboveTerrain =  cameraPos.y > terrain.getHeightOfTerrain(cameraPos.x, cameraPos.z);

        for (int attempt=0; attempt<MAX_TERRAIN_COLLISION_ATTEMPTS; attempt++){


            Vector3f scaledDirection = new Vector3f(currentRay.x * attempt, currentRay.y * attempt, currentRay.z * attempt);
            Vector3f nextCheck = cameraPos.add(scaledDirection); // ;

            // Checking if nextCheck is close enough to the real value to satisfy TERRAIN_COLLISION_PRECISION
            if (Math.abs(terrain.getHeightOfTerrain(nextCheck.x, nextCheck.z) - nextCheck.y) < 1){
                return nextCheck;
            }

            //nextCheck = Vector3f.add(cameraPos, scaledDirection, null); //
            // checking if the nextCheck has gone past the point of intersection
            if (nextCheck.y < terrain.getHeightOfTerrain(nextCheck.x, nextCheck.z) == isCameraAboveTerrain){
                // we need to now perform a binary search with previous_nextCheck and nextCheck as bounds and we are looking for a point with the correct precision
                Vector3f previousCheck = cameraPos.add(new Vector3f(currentRay.x * attempt -1, currentRay.y * attempt - 1, currentRay.z * attempt -1)); // ;
                float factor = 0.5f; // how much to linearly interpolate by
                Vector3f midpoint = nextCheck;
                while(Math.abs(terrain.getHeightOfTerrain(midpoint.x, midpoint.z) - nextCheck.y) < 1){
                    midpoint = Maths.linearlyInterpolate(previousCheck, nextCheck, factor);
                    factor /= 2;
                }
                return midpoint;
            }
        }
        return null;
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    private Vector3f calculateMouseRay(){
        Vector2f mousePos = NewDisplayManager.getMousePos();
        //http://antongerdelan.net/opengl/raycasting.html
        Vector2f normalisedCoords = getNormalisedDeviceCoords(mousePos.x, mousePos.y);
        Vector4f clipCoords = new Vector4f(normalisedCoords.x, normalisedCoords.y, -1f, 1f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector3f worldRay = toWorldsCoords(eyeCoords);
        return worldRay;
    }

    private Vector2f getNormalisedDeviceCoords(float mouse_x, float mouse_y){ // converts 1920x1080 to -1x1
        float x = (2f * mouse_x) / NewDisplayManager.WIDTH - 1f;
        float y = (2f * mouse_y) / NewDisplayManager.HEIGHT - 1f;

        return new Vector2f(x, y);
    }

    private Vector4f toEyeCoords(Vector4f clipCoords){
        Matrix4f invertedProjection = projectionMatrix.invert();
        Vector4f eyeCoords = invertedProjection.transform(clipCoords);
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private Vector3f toWorldsCoords(Vector4f eyeCoords){
        Matrix4f invertedView = viewMatrix.invert();
        Vector4f rayWorld = invertedView.transform(eyeCoords);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalize();
        return mouseRay;
    }


}
