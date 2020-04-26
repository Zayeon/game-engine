package renderEngine;

import maths.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;


public class NewDisplayManager {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;
    private static final String WINDOW_TITLE = "Project Islands";

    private static long windowKey;
    private static float lastFrameTime = 0;
    private static float delta = 0;
    private static GLFWErrorCallback errorCallback;
    private static GLFWCursorPosCallback cursorCallback;
    private static GLCapabilities capabilities;
    private static float lastX;
    private static float lastY;
    private static float dx;
    private static float dy;

    public static void createDisplay(){

        // Setup GLFW
        GLFW.glfwInit();

        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Define properties and create window
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,  GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        windowKey = GLFW.glfwCreateWindow(WIDTH, HEIGHT, WINDOW_TITLE, 0, 0);

        // Check if window was created
        if (windowKey == 0){
            throw new RuntimeException("Failed to create window");
        }

        // GLFW.glfwSetInputMode(windowKey, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwSetCursorPosCallback(windowKey, cursorCallback = new GLFWCursorPosCallback(){
            @Override
            public void invoke(long window, double xpos, double ypos) {
                NewDisplayManager.cursorPositionCallback(xpos,  ypos);
            }
        });


        // Set context to this window
        GLFW.glfwMakeContextCurrent(windowKey);
        capabilities = GL.createCapabilities();

        // Finally show the window
        GLFW.glfwShowWindow(windowKey);

    }

    public static long getWindowKey() {
        return windowKey;
    }

    public static void closeDisplay(){
        GLFW.glfwDestroyWindow(windowKey);
    }

    public static boolean isCloseRequested(){
        return GLFW.glfwWindowShouldClose(windowKey);
    }

    public static void setupDisplay(){

    }

    public static void updateDisplay(){
        GLFW.glfwSwapBuffers(windowKey);
        GLFW.glfwPollEvents();
        float currentFrameTime = (float) GLFW.glfwGetTime();
        delta = currentFrameTime - lastFrameTime;
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds(){
        return delta;
    }

    public static GLCapabilities getCapabilities() {
        return capabilities;
    }

    // TODO: Add a text input feature
    // it should take in a callback function and add it to a list of functions that is
    // called every time the main input callback is called

    public static boolean isKeyDown(int key){
        int state = GLFW.glfwGetKey(windowKey, key);
        return state == GLFW.GLFW_PRESS;
    }

    public static boolean getMouseButton(int button){
        int state = GLFW.glfwGetMouseButton(windowKey, button);
        return state == GLFW.GLFW_PRESS;
    }

    public static Vector2f getMousePos(){
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        GLFW.glfwGetCursorPos(windowKey, xpos, ypos);
        Vector2f pos = new Vector2f((float)xpos[0], (float)ypos[0]);
        if (pos.x < 0){
            pos.x = 0;
        }else if(pos.x > WIDTH){
            pos.x = WIDTH;
        }
        if (pos.y < 0){
            pos.y = 0;
        }else if(pos.y > HEIGHT){
            pos.y = HEIGHT;
        }
        //System.out.println(String.valueOf(pos.x) + " " + String.valueOf(pos.y));
        return pos;
    }

    public static float getDX() {
        return dx;
    }

    public static float getDY() {
        return dy;
    }

    private static void cursorPositionCallback(double xpos, double ypos){
        dx = (float)xpos - lastX;
        dy = (float)ypos - lastY;
        lastX = (float)xpos;
        lastY = (float)ypos;
    }

}