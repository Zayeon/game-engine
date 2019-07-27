package font.fontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.shaders.ShaderProgram;

public class FontShader extends ShaderProgram{

	private static final String VERTEX_FILE = "/font/fontRendering/fontVertex.txt";
	private static final String FRAGMENT_FILE = "/font/fontRendering/fontFragment.txt";
	
    private int location_colour;
    private int location_translation;

	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {

	    location_colour = getUniformLocation("colour");
	    location_translation = getUniformLocation("translation");
	}

	@Override
	protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoords");
	}

	protected void loadColour(Vector3f colour){
	    loadVector(location_colour, colour);
    }

    protected void loadTranslation(Vector2f translation){
	    loadVector(location_translation, translation);
    }
}
