package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.particles.ParticleMaster;
import entities.particles.ParticleSystem;
import entities.particles.ParticleTexture;
import font.fontMeshCreator.FontType;
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import entities.terrains.Terrain;
import renderEngine.models.ModelTexture;
import entities.terrains.TerrainTexture;
import entities.terrains.TerrainTexturePack;
import toolbox.DayTracker;
import toolbox.MousePicker;
import entities.water.WaterFrameBuffers;
import entities.water.WaterRenderer;
import entities.water.WaterShader;
import entities.water.WaterTile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
        Random random = new Random(12345);

        //*****TEXT*****

        TextMaster.init(loader);
        FontType font = new FontType(loader.loadFontTexture("candara"), new File("res/font/candara.fnt"));
        GUIText text = new GUIText("Sample Text", 3, font, new Vector2f(0.5f, 0.5f), 0.5f, true);
        text.setColour(1, 0, 1);

        //**************

        //*****TEXTURES*****

        ModelTexture treetexture = new ModelTexture(loader.loadTexture("tree"));

        ModelTexture tree2texture = new ModelTexture(loader.loadTexture("lowPolyTree"));

        ModelTexture grasstexture = new ModelTexture(loader.loadTexture("grassTexture"));
        grasstexture.setHasTransparency(true);
        grasstexture.setUseFakeLighting(true);

        ModelTexture ferntexture = new ModelTexture(loader.loadTexture("fern"));
        ferntexture.setHasTransparency(true);
        ferntexture.setUseFakeLighting(true);
        ferntexture.setNumberOfRows(2);

        ModelTexture flowertexture = new ModelTexture(loader.loadTexture("flower"));
        flowertexture.setHasTransparency(true);
        flowertexture.setUseFakeLighting(true);

        ModelTexture playertexture = new ModelTexture(loader.loadTexture("playerTexture"));

        ModelTexture lamptexture = new ModelTexture(loader.loadTexture("lamp"));
        lamptexture.setUseFakeLighting(true);

        //******************

        //*****MODELs*****

        TexturedModel treeModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader), treetexture);

        TexturedModel tree2Model = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), tree2texture);

        TexturedModel grassModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), grasstexture);

        TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), ferntexture);

        TexturedModel flowerModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), flowertexture);

        TexturedModel playerModel = new TexturedModel(OBJLoader.loadObjModel("person", loader), playertexture);

        TexturedModel lampModel = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), lamptexture);

        //****************

        //*****TERRAIN STUFF*****

        List<Terrain> terrains = new ArrayList<>();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadUtilTexture("blendMap"));

        terrains.add(new Terrain(0, -1, loader, texturePack, blendMap, "heightmap"));
        //***********************

        //*****ENTITY STUFF*****

        List<Entity> entities = new ArrayList<>();

        entities.add(new Entity(tree2Model, new Vector3f(183, -2.7f, -114f), 0, 0 ,0, 1));
        entities.add(new Entity(tree2Model, new Vector3f(186, -2.7f, -15f), 0, 0 ,0, 1));
        entities.add(new Entity(tree2Model, new Vector3f(191, -2.7f, -58f), 0, 0 ,0, 1));
        entities.add(new Entity(tree2Model, new Vector3f(121, -2.7f, -34), 0, 0 ,0,1));

        //**********************

        //*****PLAYER STUFF*****

        Player player = new Player(playerModel, new Vector3f(0, 0, -25), 0, 0, 0, 0.4f);
        Camera camera = new Camera(player);

        player.setPosition(new Vector3f(100, 0, -100));
        entities.add(player);

        //**********************


        //*****LIGHTING STUFF*****

        List<Light> lights = new ArrayList<>();
        Light sun = new Light(new Vector3f(0,1000,-1000), new Vector3f(0.6f, 0.6f, 0.6f));
        lights.add(sun);
        lights.add(new Light(new Vector3f(185,15,-293), new Vector3f(2,0,0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370,20,-300), new Vector3f(2,2,0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293,10,-305), new Vector3f(0,0,2), new Vector3f(1, 0.01f, 0.002f)));



        //************************

        //*****MISC*****

        DayTracker dayTracker = new DayTracker(300);
        dayTracker.setTime(dayTracker.getSecondsPerDay()/2f);

        MasterRenderer renderer = new MasterRenderer(loader, camera, dayTracker);

        MousePicker picker = new MousePicker(renderer.getProjectionMatrix(), camera);

        //**************



        //*****WATER STUFF*****

        WaterFrameBuffers fbos = new WaterFrameBuffers();
        WaterShader waterShader = new WaterShader();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos);
        List<WaterTile> waters = new ArrayList<>();
        WaterTile water = new WaterTile(100, -100, -4f);
        waters.add(water);




        //*********************


        //*****GUI STUFF*****

        List<GuiTexture> guis = new ArrayList<>();
        //GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
        //guis.add(shadowMap);
        GuiRenderer guiRenderer = new GuiRenderer(loader);

        //*******************

        //*****PARTICLES*****

        ParticleMaster.init(loader, renderer.getProjectionMatrix());

        ParticleTexture particleTexture = new ParticleTexture(loader.loadParticleTexture("particleAtlas"), 4, false);
        ParticleSystem system = new ParticleSystem(particleTexture, 80, 20, 0.1f,  4, 1.6f);
        system.setLifeError(0.1f);
        system.setSpeedError(0.25f);
        system.setScaleError(0.5f);
        system.setDirection(new Vector3f(0, 1,0 ), 0.2f);
        system.randomizeRotation();


        //*******************




        //*****Game Loop*****

        while(!Display.isCloseRequested()){
            camera.move();
            picker.update();
            player.move(terrains.get(0));
            ParticleMaster.update(camera);

            GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

            // shadows
            renderer.renderShadowMap(entities, sun);

            // particles
            system.generateParticles(new Vector3f(100, 10, -100));
            system.generateParticles(new Vector3f(0, 10, 0));


            // Rendering the scene to the reflection frame buffer
            fbos.bindReflectionFrameBuffer();
            float distance = 2 * (camera.getPosition().y - water.getHeight());
            camera.getPosition().y -= distance;
            camera.invertPitch();
            renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1f));
            camera.getPosition().y += distance;
            camera.invertPitch();

            // Rendering the scene to the refraction frame buffer
            fbos.bindRefractionFrameBuffer();
            renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
            fbos.unbindCurrentFrameBuffer();

            // General Rendering
            renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, -1, 0, 1000000));
            waterRenderer.render(waters, camera, lights.get(0));
            ParticleMaster.renderParticles(camera);

            // Gui Rendering
            guiRenderer.render(guis);
            TextMaster.render();

            DisplayManager.updateDisplay();
            dayTracker.tick();
        }

        //*******************


        //*****CLEAN UP******

        TextMaster.cleanUp();
        ParticleMaster.cleanUp();
        fbos.cleanUp();
        waterShader.cleanUp();
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

        //*******************

	}

}


// Things to do in the future

// Create a scene class
// Optimise collision detection
