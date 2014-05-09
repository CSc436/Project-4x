attribute vec2 vertices;

void main()
{
  gl_Position = vec4(vertices, -0.1, 1.0);
}