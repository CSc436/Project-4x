precision mediump float;

uniform sampler2D texture;
uniform vec4 id;
varying vec2 texCoord;
varying vec3 normal;
varying vec3 position;
//varying vec4 vColor;

void main(void)
{
  gl_FragColor = id;
  //gl_FragColor = vec4(0.5,1.0,0.5,1.0);
  //gl_FragColor = vec4((position+vec3(0.5,0.5,0.5)),1.0);
}