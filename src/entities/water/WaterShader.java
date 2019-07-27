package entities.water;

import entities.Camera;
import entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.shaders.ShaderProgram;
import toolbox.Maths;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = "/entities/water/waterVertex.txt";
	private final static String FRAGMENT_FILE = "/entities/water/waterFragment.txt";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_dudvMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_normalMap;
	private int location_lightColour;
	private int location_lightPosition;
	private int location_depthMap;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_reflectionTexture = getUniformLocation("reflectionTexture");
		location_refractionTexture = getUniformLocation("refractionTexture");
		location_dudvMap = getUniformLocation("dudvMap");
		location_moveFactor = getUniformLocation("moveFactor");
		location_cameraPosition = getUniformLocation("cameraPosition");
		location_normalMap = getUniformLocation("normalMap");
		location_lightColour = getUniformLocation("lightColour");
		location_lightPosition = getUniformLocation("lightPosition");
		location_depthMap = getUniformLocation("depthMap");

	}

	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		loadVector(location_cameraPosition, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

	public void loadMoveFactor(float factor){
		loadFloat(location_moveFactor, factor);
	}

	public void loadLights(Light sun){
		loadVector(location_lightColour, sun.getColour());
		loadVector(location_lightPosition, sun.getPosition());

	}

	public void connextTextureUnits(){
		loadInt(location_reflectionTexture, 0);
		loadInt(location_refractionTexture, 1);
		loadInt(location_dudvMap, 2);
		loadInt(location_normalMap, 3);
		loadInt(location_depthMap, 4);
	}

}
