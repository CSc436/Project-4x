precision mediump float;

uniform sampler2D texture;
uniform vec3 cameraPos;
uniform vec3 teamColor;
varying vec3 texCoord;
varying vec3 normal;
varying vec3 position;
//varying vec4 vColor;

void main(void)
{
  vec3 lightPos = vec3(100.0,100.0,100.0);
  vec3 posToLight = normalize(lightPos - position);
  vec3 posToCamera = normalize(cameraPos - position);
  vec3 mirrorVec = normalize(vec3((2.0 * dot(posToLight,normal)) * normal) - posToLight);
  float spec = pow(float(1.0 * dot(mirrorVec,posToCamera)),32.0);
  
  if (texCoord.x < 0.5)
  {
  	gl_FragColor = spec + ( texture2D(texture, texCoord.xy) * vec4(teamColor, 1.0) ) ;
  }
  else
  {
    gl_FragColor = spec + texture2D(texture, texCoord.xy);
    //gl_FragColor = vec4(0.5,1.0,0.5,1.0);
    //gl_FragColor = vec4((position+vec3(0.5,0.5,0.5)),1.0);
  }
}