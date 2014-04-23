attribute vec3 vertexPosition;
attribute vec4 vertexColor;
//attribute vec2 vertexTexCoord;

uniform mat4 perspectiveMatrix;
//varying vec2 texCoord;

varying vec4 vColor;

void main(void)
{
  //texCoord = vertexTexCoord;
  vColor = vertexColor;
  gl_Position = perspectiveMatrix * vec4(vertexPosition,1.0);
}