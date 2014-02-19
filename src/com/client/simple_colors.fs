precision mediump float;

uniform sampler2D texture;
//varying vec2 texCoord;

varying vec4 vColor;

void main(void)
{
  gl_FragColor = vColor;
}