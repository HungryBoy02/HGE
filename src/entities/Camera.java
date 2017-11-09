package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,100,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera(){}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z-=1.00f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z+=1.00f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+=1.00f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-=1.00f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y-=1.00f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y+=1.00f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			pitch-=1.0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			pitch+=1.0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw-=1.0f;
			roll-=1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw+=1.0f;
			roll+=1f;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			roll=0f;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			roll=0f;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	

}
