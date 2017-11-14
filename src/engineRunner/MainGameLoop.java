package engineRunner;

import models.RawModel;
import models.TexturedModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//*********TERRAIN TEXTURE STUFF**********
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grass_flowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		//****************************************
		
		
		TexturedModel treeModel = new TexturedModel(OBJLoader.loadObjModel("Tree", loader),
				new ModelTexture(loader.loadTexture("Tree")));
		ModelTexture treeTexture = treeModel.getTexture();
		treeTexture.setShineDamper(10);
		treeTexture.setReflectivity(1);

		//Tall Grass Model -HungryBoy02
		TexturedModel grassModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("grassTexture")));
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setUseFakeLighting(true);
		
		
		//Fern Model -HungryBoy02
		TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
				new ModelTexture(loader.loadTexture("fern")));
		fernModel.getTexture().setHasTransparency(true);
		
		//Alternate Tree Model -HungryBoy02
		TexturedModel altTreeModel = new TexturedModel(OBJLoader.loadObjModel("tree_alt_2", loader),
				new ModelTexture(loader.loadTexture("tree_alt_2")));
		ModelTexture altTreeTexture = altTreeModel.getTexture();
		altTreeTexture.setShineDamper(10);
		altTreeTexture.setReflectivity(1);
		
		//WineGlass Model
		TexturedModel WineGlassModel = new TexturedModel(OBJLoader.loadObjModel("WineGlass", loader),
				new ModelTexture(loader.loadTexture("WineGlass")));
		ModelTexture WineGlassTexture = WineGlassModel.getTexture();
		WineGlassTexture.setShineDamper(10);
		WineGlassTexture.setReflectivity(1);
		
		RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);
		TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i = 0; i < 400; i++){
			if (i % 7 == 0) {
				entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() * 400 - 200, 0, random.nextFloat() * -400), 0, 0, 0, 2));
			}
			if (i % 3 == 0) {
				entities.add(new Entity(treeModel, new Vector3f(random.nextFloat() * 400 - 200, 0, random.nextFloat() * -400), 0, random.nextFloat() * 360, 0, 5f));
				entities.add(new Entity(fernModel, new Vector3f(random.nextFloat() * 400 - 200, 0, random.nextFloat() * -400), 0, random.nextFloat() * 360, 0, 1.5f));
				entities.add(new Entity(altTreeModel, new Vector3f(random.nextFloat() * 400 - 200, 0, random.nextFloat() * -400), 0, random.nextFloat() * 360, 0, 1.5f));
			}

		}
		

		Entity entity = new Entity(WineGlassModel, new Vector3f(0, 0, -25), 0, 0, 0, 20);
		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));

		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);

		Camera camera = new Camera();

		MasterRenderer renderer = new MasterRenderer();
		
		Player player = new Player(stanfordBunny, new Vector3f(100,0,-100), 0, 0, 0, 1);
		
		while (!Display.isCloseRequested()) {
			camera.move();
			player.move();
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processEntity(entity);
			for(Entity ent:entities){
				renderer.processEntity(ent);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
