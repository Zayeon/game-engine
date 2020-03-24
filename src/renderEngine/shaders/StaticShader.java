package renderEngine.shaders;

import entities.Camera;
import entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import toolbox.Maths;

import java.util.List;

public class StaticShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/renderEngine/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/renderEngine/shaders/fragmentShader.txt";

    private static final int MAX_LIGHTS = 4;

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int[] location_lightPosition;
    private int[] location_lightColour;
    private int[] location_attenuation;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColour;
    private int location_numberOfRows;
    private int location_offset;
    private int location_plane;
    private int location_toShadowMapSpace;
    private int location_shadowMap;
    private int location_textureSampler;

    private Light lastLights[] = new Light[MAX_LIGHTS];

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_skyColour = super.getUniformLocation("skyColour");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");
        location_plane = getUniformLocation("plane");
        location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
        location_shadowMap = super.getUniformLocation("shadowMap");
        location_textureSampler = super.getUniformLocation("textureSampler");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];
        for(int i=0; i<MAX_LIGHTS; i++){
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureSampler");
        super.bindAttribute(2, "normal");

    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void connectTextureUnits(){
        super.loadInt(location_textureSampler, 0);
        super.loadInt(location_shadowMap, 5);
    }

    public void loadLights(List<Light> lights){
        for(int i=0;i<MAX_LIGHTS;i++){
            if(i<lights.size()){
                boolean uniformModified = false;
                if (lastLights[i] == null){
                    super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
                    super.loadVector(location_lightColour[i], lights.get(i).getColour());
                    super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
                    uniformModified = true;
                } else {
                    if (lastLights[i].getPosition() != lights.get(i).getPosition()){
                        super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
                        uniformModified = true;
                    }
                    if (lastLights[i].getColour() != lights.get(i).getColour()){
                        super.loadVector(location_lightColour[i], lights.get(i).getColour());
                        uniformModified = true;
                    }
                    if (lastLights[i].getAttenuation() != lights.get(i).getAttenuation()){
                        super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
                        uniformModified = true;
                    }
                }
                if (uniformModified){
                    lastLights[i] = lights.get(i);
                }
            }else{
                super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
                super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
                super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
            }
        }

    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadFakeLightingVariable(boolean useFake){
        super.loadBoolean(location_useFakeLighting, useFake);
    }

    public void loadSkyColour(float r, float g, float b){
        super.loadVector(location_skyColour, new Vector3f(r,g,b));
    }

    public void loadNumberOfRows(int numberOfRows){
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    public void loadOffset(float x, float y){
        super.loadVector(location_offset, new Vector2f(x, y));
    }

    public void loadClipPlane(Vector4f plane){
        super.loadVector(location_plane, plane);
    }

    public void loadToShadowSpaceMatrix(Matrix4f matrix){
        super.loadMatrix(location_toShadowMapSpace, matrix);
    }

}
