package com.client.rendering;

/* Renderable
 * Objects that implement Renderable are any objects that are expected to be
 * visible in game.  That is, they will need a vertex shader and fragment
 * shader that have been built into a shader program.  Note that the render
 * function should NOT actually call the renderer but only pushes the object's
 * buffers to the GPU.  The render loop will be calling the render functions.
 */
public interface Renderable {

	void render(Shader shader);
	
}
