attribute vec3 vertexPosition;
attribute vec2 tileSelectColor;

uniform mat4 perspectiveMatrix;
uniform vec3 camPos;
varying vec2 selectColor;

void main(void)
{
  selectColor = tileSelectColor;
  gl_Position = perspectiveMatrix * vec4(vertexPosition + camPos, 1.0);
}