attribute vec3 vertexPosition;
attribute vec3 vertexTexCoord;
attribute vec3 vertexNormal;

uniform mat4 perspectiveMatrix;
uniform mat4 modelMatrix;
uniform vec3 camPos;
uniform float healthPercentage;

varying vec3 position;
varying vec3 texCoord;
varying vec3 normal;
varying vec4 color;
//varying vec4 vColor;

vec4 healthToColor(float);

void main(void)
{
  float scale = 1.1;
  vec3 newVertexPosition = vertexPosition * scale;

  texCoord = vertexTexCoord;
  normal = vertexNormal;
  position = newVertexPosition;
  
  color = healthToColor(healthPercentage);
  
  //mat4 model = mat4(modelMatrix[0],modelMatrix[1],modelMatrix[2],(modelMatrix[3]+vec4(camPos,1.0)));
  gl_Position = perspectiveMatrix * modelMatrix * vec4(newVertexPosition+camPos,1.0);
}

vec4 healthToColor(in float health)
{ 
  if (health >= 1.0){
    return vec4(0.0, 0.0, 1.0, 1.0);
  }
  
  vec4 outColor;
    
  if (health > 0.5)
  {
    return vec4( 1.0 - ( (health - 0.5)/0.5 ), 1.0, 0.0, 1.0);
  }
  else
  {
      return vec4( 1.0,  health/0.5, 0.0, 1.0);
  }
  
  return outColor;
}