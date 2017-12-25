package engineRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
//hello
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();

		// *********TERRAIN TEXTURE STUFF**********

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grass_flowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// ****************************************

		// *******************LOAD MODELS*********************

		// Tall Grass Model -HungryBoy02
		TexturedModel grassModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("grassTexture")));
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setUseFakeLighting(true);

		// Fern Model -HungryBoy02

		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);

		TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);

		fernModel.getTexture().setHasTransparency(true);
		fernModel.getTexture().setReflectivity(1);
		fernModel.getTexture().setShineDamper(10);

		// Alternate Tree Model -HungryBoy02
		TexturedModel altTreeModel = new TexturedModel(OBJLoader.loadObjModel("tree_alt_2", loader),
				new ModelTexture(loader.loadTexture("tree_alt_2")));
		ModelTexture altTreeTexture = altTreeModel.getTexture();

		// Bunny Model

		RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);
		TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));

		// Redone Wine Glass Model - HungryBoy02

		ModelData reWineGlassModelData = OBJFileLoader.loadOBJ("RedoneWineGlass");
		RawModel reWineGlassModel = loader.loadToVAO(reWineGlassModelData.getVertices(),
				reWineGlassModelData.getTextureCoords(), reWineGlassModelData.getNormals(),
				reWineGlassModelData.getIndices());
		TexturedModel ReWineGlass = new TexturedModel(reWineGlassModel,
				new ModelTexture(loader.loadTexture("RedoneWineGlass")));
		ReWineGlass.getTexture().setShineDamper(10);
		ReWineGlass.getTexture().setReflectivity(1);

		// Street Lamp Model

		ModelData StreetLampModelData = OBJFileLoader.loadOBJ("StreetLamp");
		RawModel StreetLampModel = loader.loadToVAO(StreetLampModelData.getVertices(),
				StreetLampModelData.getTextureCoords(), StreetLampModelData.getNormals(),
				StreetLampModelData.getIndices());
		TexturedModel StreetLamp = new TexturedModel(StreetLampModel,
				new ModelTexture(loader.loadTexture("StreetLamp")));
		StreetLamp.getTexture().setShineDamper(10);
		StreetLamp.getTexture().setReflectivity(2);

		// new tree

		RawModel NewTree = OBJLoader.loadObjModel("NewTree", loader);
		TexturedModel nTree = new TexturedModel(NewTree, new ModelTexture(loader.loadTexture("NewTree")));
		nTree.getTexture().setHasTransparency(true);

		// **********************************************

		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();

		// **************Load Normal Mapped Models******************

		// [Barrel**
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		// **Barrel]

		// *********************************************************

		// [***************Load Entities******************************

		// Barrel
		normalMapEntities.add(new Entity(barrelModel, new Vector3f(75, 10, -75), 0, 0, 0, 1f));

		// **********************************************************]

		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrainMini = new Terrain(0, -1, loader, texturePack,
				new TerrainTexture(loader.loadTexture("floatingIslandBlendMap")), "floatingIslandHeightMap", 150, 5);

		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrainMini);

		// Lights
		Light sun = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);

		// Lamp
		Entity MovableLamp = new Entity(StreetLamp,
				new Vector3f(50, terrainMini.getHeightOfTerrain(50, -60) - 0.5f, -60), 0, 0, 0, 5);
		entities.add(MovableLamp);

		// **********Random generation***********
		Random random1 = new Random(676452);
		for (int i = 0; i < 10; i++) {
			if (i % 20 == 0) {
				float x = random1.nextFloat() * 150;
				float z = random1.nextFloat() * -150;
				float y = terrainMini.getHeightOfTerrain(x, z);
				entities.add(new Entity(grassModel, new Vector3f(x, y, z), 0, 0, 0, 2));
			}
			if (i % 5 == 0) {
				float x = random1.nextFloat() * 800;
				float z = random1.nextFloat() * -600;
				float y = terrainMini.getHeightOfTerrain(x, z);
				entities.add(new Entity(nTree, new Vector3f(x, y - 5, z), 0, random1.nextFloat() * 360, 0, 10f));
				x = random1.nextFloat() * 150;
				z = random1.nextFloat() * -150;
				y = terrainMini.getHeightOfTerrain(x, z);
				entities.add(new Entity(fernModel, random1.nextInt(4), new Vector3f(x, y, z), 0,
						random1.nextFloat() * 360, 0, 0.9f));
				x = random1.nextFloat() * 150;
				z = random1.nextFloat() * -150;
				y = terrainMini.getHeightOfTerrain(x, z);
				entities.add(new Entity(nTree, new Vector3f(x, y - 5, z), 0, random1.nextFloat() * 360, 0, 10f));
			}

		}

		// *************

		MasterRenderer renderer = new MasterRenderer(loader);

		Player player = new Player(stanfordBunny, new Vector3f(50, 0, -50), 0, 0, 0, 0.3f);

		Camera camera = new Camera(player);

		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		entities.add(player);

		// Gui Stuff

		GuiRenderer guiRenderer = new GuiRenderer(loader);
		List<GuiTexture> guis = new ArrayList<GuiTexture>();

		// Water Stuff

		WaterFrameBuffers buffers = new WaterFrameBuffers();

		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(75, -75, -2.5f);
		waters.add(water);

		// *****************Game Loop*****************************

		while (!Display.isCloseRequested()) {
			player.move(terrainMini);
			camera.move();
			picker.update();
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

			// Water Reflection -Hungry
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
					new Vector4f(0, 1, 0, -water.getHeight() + 0.1f));
			camera.getPosition().y += distance;
			camera.invertPitch();

			// Water Refraction
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
					new Vector4f(0, -1, 0, water.getHeight()));

			// rScene rendering -Hungry
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, 100000));
			waterRenderer.render(waters, camera, sun);
			guiRenderer.render(guis);

			DisplayManager.updateDisplay();
		}
		// *****************After Exit Code*****************
		buffers.cleanUp();
		waterShader.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
