#version 150

in vec3 vPosition;
in vec4 vNormal;

uniform mat4 mvpMatrix;
uniform mat4 mwMatrix;

void main() {

	gl_Position = mvpMatrix * mwMatrix * vec4( vPosition, 1.0 );

}