precision mediump float;

varying vec2 selectColor;

void main(void)
{
  gl_FragColor = vec4(selectColor, 1.0, 1.0);

}