precision mediump float;

varying vec2 selectColor;

void main(void)
{
  //vec2 add = vec2(1.0,0.5);
  gl_FragColor = vec4(selectColor, 0.0, 1.0);
  //gl_FragColor = vec4(add, 0.5, 1.0);

}