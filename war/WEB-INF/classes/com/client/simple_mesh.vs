attribute vec3 vertexPosition;
attribute vec3 vertexTexCoord;
attribute vec3 vertexNormal;

uniform mat4 perspectiveMatrix;
uniform mat4 modelMatrix;
uniform vec3 camPos;
varying vec3 position;
varying vec3 texCoord;
varying vec3 normal;
//varying vec4 vColor;

void main(void)
{
  texCoord = vertexTexCoord;
  normal = vertexNormal;
  position = vertexPosition;
  //vColor = vertexColor;
  //mat4 model = mat4(modelMatrix[0],modelMatrix[1],modelMatrix[2],(modelMatrix[3]+vec4(camPos,1.0)));
  gl_Position = perspectiveMatrix * modelMatrix * vec4(vertexPosition+camPos,1.0);
}