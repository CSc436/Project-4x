precision mediump float;

uniform sampler2D texture;
varying vec3 texCoord;
varying vec3 normal;
varying vec3 position;
varying vec4 color;

void main(void)
{
  //gl_FragColor = (texture2D(texture, texCoord.xy) * color) + vec4(0.0,0.0,0.0,1.0);
  gl_FragColor = color;
  // * vec4(1.0,1.0,1.0,0.75);
  //gl_FragColor = vec4(0.5,1.0,0.5,1.0);
  //gl_FragColor = vec4((position+vec3(0.5,0.5,0.5)),1.0);
}