package renderEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;


public class NewDisplayManager {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;
    private static final String WINDOW_TITLE = "Project Islands";

    private static long windowKey;
    private static float lastFrameTime = 0;
    private static float delta;
    private static GLFWErrorCallback errorCallback;

    public static void createDisplay(){

        // Setup GLFW
        GLFW.glfwInit();
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Define properties and create window
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,  GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        windowKey = GLFW.glfwCreateWindow(WIDTH, HEIGHT, WINDOW_TITLE, 0, 0);

        // Check if window was created
        if (windowKey == 0){
            throw new RuntimeException("Failed to create window");
        }

        // Set context to this window
        GLFW.glfwMakeContextCurrent(windowKey);
        GL.createCapabilities();

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

    public static void updateDisplay(){
        GLFW.glfwPollEvents();
        GLFW.glfwSwapBuffers(windowKey);
        float currentFrameTime = (float) GLFW.glfwGetTime();
        delta = currentFrameTime - lastFrameTime;
        lastFrameTime = currentFrameTime;

    }

    public static float getFrameTimeSeconds(){
        return delta;
    }

}
