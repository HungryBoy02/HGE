package engineRunner;
//hello
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

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
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		
		TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
		
		fernModel.getTexture().setHasTransparency(true);
		fernModel.getTexture().setReflectivity(1);
		fernModel.getTexture().setShineDamper(10);
		
		//Alternate Tree Model -HungryBoy02
		TexturedModel altTreeModel = new TexturedModel(OBJLoader.loadObjModel("tree_alt_2", loader),
				new ModelTexture(loader.loadTexture("tree_alt_2")));
		ModelTexture altTreeTexture = altTreeModel.getTexture();
		
		//WineGlass Model
		TexturedModel WineGlassModel = new TexturedModel(OBJLoader.loadObjModel("WineGlass", loader),
				new ModelTexture(loader.loadTexture("WineGlass")));
		ModelTexture WineGlassTexture = WineGlassModel.getTexture();
		WineGlassTexture.setShineDamper(10);
		WineGlassTexture.setReflectivity(1);
		
		RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);
		TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));
		
		//Redone Wine Glass Model
		
				ModelData reWineGlassModelData = OBJFileLoader.loadOBJ("RedoneWineGlass");
				RawModel reWineGlassModel = loader.loadToVAO(reWineGlassModelData.getVertices(),
						reWineGlassModelData.getTextureCoords(), reWineGlassModelData.getNormals(),
						reWineGlassModelData.getIndices());
				TexturedModel ReWineGlass = new TexturedModel(reWineGlassModel,
						new ModelTexture(loader.loadTexture("RedoneWineGlass")));
				ReWineGlass.getTexture().setShineDamper(10);
				ReWineGlass.getTexture().setReflectivity(1);
				
				//Street Lamp Model
				
				ModelData StreetLampModelData = OBJFileLoader.loadOBJ("StreetLamp");
				RawModel StreetLampModel = loader.loadToVAO(StreetLampModelData.getVertices(),
						StreetLampModelData.getTextureCoords(), StreetLampModelData.getNormals(),
						StreetLampModelData.getIndices());
				TexturedModel StreetLamp = new TexturedModel(StreetLampModel,
						new ModelTexture(loader.loadTexture("StreetLamp")));
				StreetLamp.getTexture().setShineDamper(10);
				StreetLamp.getTexture().setReflectivity(2);
				
				//new tree
				
			RawModel NewTree = OBJLoader.loadObjModel("NewTree", loader);
			TexturedModel nTree = new TexturedModel(NewTree, new ModelTexture(loader.loadTexture("NewTree")));
			nTree.getTexture().setHasTransparency(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		

		Entity entity = new Entity(ReWineGlass, new Vector3f(0, 0, -25), 0, 0, 0, 20);

		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");
		
		//Lights
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f,0.4f,0.4f)));
        lights.add(new Light(new Vector3f(185, 9, -293), new Vector3f(2,0,0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 16, -300), new Vector3f(0,2,2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293, 6, -305), new Vector3f(2,2,0), new Vector3f(1, 0.01f, 0.002f)));
        
        //Lamps
        
        entities.add(new Entity(StreetLamp, new Vector3f(185, terrain.getHeightOfTerrain(185, -293) - 0.5f, -293), 0, 0, 0, 5));
        entities.add(new Entity(StreetLamp, new Vector3f(370, terrain.getHeightOfTerrain(370, -300) - 0.5f, -300), 0, 0, 0, 5));
        entities.add(new Entity(StreetLamp, new Vector3f(293, terrain.getHeightOfTerrain(293, -305) - 0.5f, -305), 0, 0, 0, 5));
        Random random1 = new Random(676452);
		for(int i = 0; i < 800; i++){
			if (i % 20 == 0) {
				float x = random1.nextFloat() * 800;
                float z = random1.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(grassModel, new Vector3f(x,y,z), 0, 0, 0, 2));
			}
			if (i % 5 == 0) {
                float x = random1.nextFloat() * 800;
                float z = random1.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(nTree, new Vector3f(x,y - 5,z), 0, random1.nextFloat() * 360, 0, 10f));
				x = random1.nextFloat() * 800;
                z = random1.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(fernModel, random1.nextInt(4), new Vector3f(x,y,z), 0, random1.nextFloat() * 360, 0, 0.9f));
				x = random1.nextFloat() * 800;
                z = random1.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(altTreeModel, new Vector3f(x,y - 5,z), 0, random1.nextFloat() * 360, 0, 3f));
			}

		}
		
		MasterRenderer renderer = new MasterRenderer();
		
		Player player = new Player(stanfordBunny, new Vector3f(100,0,-100), 0, 0, 0, 0.3f);
		
		Camera camera = new Camera(player);
		
		while (!Display.isCloseRequested()) {
			player.move(terrain);
			camera.move();
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
			for(Entity ent:entities){
				renderer.processEntity(ent);
			}
			renderer.render(lights, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
