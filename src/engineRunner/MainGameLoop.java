package engineRunner;
//Example note -(name here)
import org.lwjgl.opengl.Display;

import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		
		//Open GL Expects verticies to be defined counter clockwise
		
		  float[] vertices = {
				    -0.5f, 0.5f, 0f, //V0
				    -0.5f, -0.5f, 0f, //V1
				    0.5f, -0.5f, 0f, //V2
				    0.5f, 0.5f, 0f //V4
				  };
		  
		  int[] indices = {
				  0,1,3, //Top left triangle
				  3,1,2 //Bottom right triangle
		  };
		  
		  float[] textureCoords = {
				  0,0, //v0
				  0,1, //v1
				  1,1, //v2
				  1,0  //v3
		  };
		
		  RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		  ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		  TexturedModel texturedModel = new TexturedModel(model,texture);
		  
		while(!Display.isCloseRequested()){
			
			
			//Main loop here -HungryBoy02
			
			renderer.prepere();
			shader.start();
			//models
			renderer.render(texturedModel);
			
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		
		//When close is requested -HungryBoy02
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
