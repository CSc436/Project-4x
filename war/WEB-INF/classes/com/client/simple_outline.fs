precision mediump float;

uniform sampler2D texture;
//varying vec2 texCoord;

varying vec4 vColor;

void main(void)
{
  // Basically if the green part of the color tied to the vertex is less than 0.1
  // then set the color to black. Cheap outline yay!
  float green = vColor[1];
  if (green < 0.1)
  {
  	gl_FragColor = vec4(0.0,0.0,0.0,1.0);
  }
  else
  {
    gl_FragColor = vColor;
  }
  gl_FragColor = vec4(1.0);
}