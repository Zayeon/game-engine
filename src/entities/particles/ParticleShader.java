package entities.particles;

import org.joml.Matrix4f;
import renderEngine.shaders.ShaderProgram;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/entities/particles/particleVShader.txt";
	private static final String FRAGMENT_FILE = "src/entities/particles/particleFShader.txt";

	private int location_projectionMatrix;
	private int location_numberOfRows;

	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_numberOfRows = getUniformLocation("numberOfRows");
		location_projectionMatrix = getUniformLocation("projectionMatrix");
	}

	@Override
	protected void bindAttributes() {
	    bindAttribute(0, "position");
		bindAttribute(1, "modelViewMatrix");
        bindAttribute(5, "texOffsets");
        bindAttribute(6, "blendFactor");
	}

	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

	protected void loadNumberOfRows(float numberOfRows){
	    loadFloat(location_numberOfRows, numberOfRows);
    }

}
