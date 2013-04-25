package util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Camera {

	private Matrix4f viewMatrix; // Transforms from World Space to View Space
	private Matrix4f projectionMatrix; // Transforms from View Space to Clip Space
	private Matrix4f modelViewProjectionMatrix; // Transforms from World to Clip Space
	private Matrix4f rotation;
	private final Vector3f eyePoint; // Position of the Camera in World Space
	private final Vector3f viewDirectionVector; // The point the Camara points at
	private final Vector3f viewUpVector; // should be manually normalized and made perpendicular
	private final Vector3f viewRightVector;
	private Vector4f tmp;
	private float dMin; // Distance from eyePoint to the near Plane
	private float dMax; // Distance from eyePoint to the far Plane
	private float fov; // The field of view in U Direction
	private float aspectRatio; //The aspectRatio of the Monitor
	private float rotationX;
	private float rotationY;
	private float distance;
	private static float pi_over_2 = (float) (Math.PI / 2);
	
	public Camera(int width, int height) {
		eyePoint = new Vector3f();
		viewDirectionVector = new Vector3f();
		viewRightVector = new Vector3f();
		aspectRatio = (float)width/height;
		fov = (float) Math.toRadians(90f);
		dMin = 0.1f;
		dMax = 1000.0f;
		
		viewUpVector = new Vector3f();
	
		viewMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
		modelViewProjectionMatrix = new Matrix4f();
		rotation = new Matrix4f();
		
		rotationX = 0.0f;
		rotationY = 0.0f;
		distance = -2.0f;
		
		updateProjection();
		updateView();
	}

	private void updateMVP() {
		Matrix4f.mul(projectionMatrix, viewMatrix, modelViewProjectionMatrix);
	}

	private void updateProjection() {
		float tana = (float) (1.0 / Math.tan(fov * 0.5));
	
		projectionMatrix.m00 = tana;
		projectionMatrix.m10 = 0;
		projectionMatrix.m20 = 0;
		projectionMatrix.m30 = 0;
		
		projectionMatrix.m01 = 0;
		projectionMatrix.m11 = aspectRatio * tana;
		projectionMatrix.m21 = 0;
		projectionMatrix.m31 = 0;
		
		projectionMatrix.m02 = 0;
		projectionMatrix.m12 = 0;
		projectionMatrix.m22 = dMax / (dMax - dMin);
		projectionMatrix.m32 = - (dMax * dMin) / (dMax - dMin);
		
		projectionMatrix.m03 = 0;
		projectionMatrix.m13 = 0;
		projectionMatrix.m23 = 1;
		projectionMatrix.m33 = 0;
		
		updateMVP();
	}

	private void updateView() {
		
		float sin_x = (float) Math.sin(rotationX);
		float cos_x = (float) Math.cos(rotationX);
		float sin_y = (float) Math.sin(rotationY);
		float cos_y = (float) Math.cos(rotationY);
		
		viewDirectionVector.set(sin_y * cos_x, -sin_x, cos_x * cos_y);
		viewUpVector.set(sin_y * sin_x, cos_x, sin_x * cos_y);
		viewRightVector.set(cos_y, 0, -sin_y);
		
		eyePoint.set(viewDirectionVector);
//		eyePoint.negate();
		eyePoint.scale(distance);
		
		viewMatrix.m00 = viewRightVector.x;
		viewMatrix.m10 = viewRightVector.y;
		viewMatrix.m20 = viewRightVector.z;
		viewMatrix.m30 = -(eyePoint.x * viewRightVector.x + eyePoint.y * viewRightVector.y + eyePoint.z * viewRightVector.z);
		
		viewMatrix.m01 = viewUpVector.x;
		viewMatrix.m11 = viewUpVector.y;
		viewMatrix.m21 = viewUpVector.z;
		viewMatrix.m31 = -(eyePoint.x * viewUpVector.x + eyePoint.y * viewUpVector.y + eyePoint.z * viewUpVector.z);
		
		viewMatrix.m02 = viewDirectionVector.x;
		viewMatrix.m12 = viewDirectionVector.y;
		viewMatrix.m22 = viewDirectionVector.z;
		viewMatrix.m32 = -(eyePoint.x * viewDirectionVector.x + eyePoint.y * viewDirectionVector.y + eyePoint.z * viewDirectionVector.z);
		
		viewMatrix.m03 = 0;
		viewMatrix.m13 = 0;
		viewMatrix.m23 = 0;
		viewMatrix.m33 = 1;
		updateMVP();
	}

	public Matrix4f getMVP() {
		return  modelViewProjectionMatrix;
	}
	
	public void rollCamera(float angle) {
		rotation.setIdentity();
		tmp = new Vector4f(viewUpVector.x, viewUpVector.y, viewUpVector.z, 0);
		rotation.rotate(angle, viewDirectionVector);
		Matrix4f.transform(rotation, tmp, tmp);
		viewUpVector.x = tmp.getX();
		viewUpVector.y = tmp.getY();
		viewUpVector.z = tmp.getZ();
		
		tmp = new Vector4f(viewRightVector.x, viewRightVector.y, viewRightVector.z, 0);
		Matrix4f.transform(rotation, tmp, tmp);
		viewRightVector.x = tmp.getX();
		viewRightVector.y = tmp.getY();
		viewRightVector.z = tmp.getZ();
		updateView();
	}
	
	public void pitchCamera(float angle) {
		rotation.setIdentity();
		tmp = new Vector4f(viewUpVector.x, viewUpVector.y, viewUpVector.z, 0);
		rotation.rotate(angle, viewRightVector);
		Matrix4f.transform(rotation, tmp, tmp);
		viewUpVector.x = tmp.getX();
		viewUpVector.y = tmp.getY();
		viewUpVector.z = tmp.getZ();
		
		tmp = new Vector4f(viewDirectionVector.x, viewDirectionVector.y, viewDirectionVector.z, 0);
		Matrix4f.transform(rotation, tmp, tmp);
		viewDirectionVector.x = tmp.getX();
		viewDirectionVector.y = tmp.getY();
		viewDirectionVector.z = tmp.getZ();
		updateView();
	}
	
	public void yawCamera(float angle) {
		rotation.setIdentity();
		tmp = new Vector4f(viewRightVector.x, viewRightVector.y, viewRightVector.z, 0);
		rotation.rotate(angle, viewUpVector);
		Matrix4f.transform(rotation, tmp, tmp);
		viewRightVector.x = tmp.getX();
		viewRightVector.y = tmp.getY();
		viewRightVector.z = tmp.getZ();
		
		tmp = new Vector4f(viewDirectionVector.x, viewDirectionVector.y, viewDirectionVector.z, 0);
		Matrix4f.transform(rotation, tmp, tmp);
		viewDirectionVector.x = tmp.getX();
		viewDirectionVector.y = tmp.getY();
		viewDirectionVector.z = tmp.getZ();
		updateView();
	}
	
	public void moveForward(float distance) {
		this.distance += distance;
		updateView();
	}
	
	public void moveSideward(float distance) {
		eyePoint.x += distance * viewRightVector.x;
		eyePoint.y += distance * viewRightVector.y;
		eyePoint.z += distance * viewRightVector.z;
		updateView();
	}
	
	public void moveUpward(float distance) {
		eyePoint.x += distance * viewUpVector.x;
		eyePoint.y += distance * viewUpVector.y;
		eyePoint.z += distance * viewUpVector.z;
		updateView();
	}
	
	public void rotateX(float angle) {
		
		rotationX += angle;
		
		if(rotationX > pi_over_2) {
			rotationX = pi_over_2;
		} else if(rotationX < -pi_over_2) {
			rotationX = -pi_over_2;
		}
		updateView();
	}
	
	public void rotateY(float angle) {
		
		rotationY += angle;	
		updateView();
		
	}
}
