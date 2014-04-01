package com.client;

import com.googlecode.gwtgl.array.Uint8Array;
import com.googlecode.gwtgl.binding.WebGLFramebuffer;
import com.googlecode.gwtgl.binding.WebGLRenderbuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLTexture;

public class ClickSelector {

	private WebGLRenderingContext glContext;
	private GameCanvas canvas;
	private Shader idShader;
	private WebGLFramebuffer framebuffer;
	private WebGLRenderbuffer renderbuffer;
	private WebGLTexture frametexture;
	
	// These values are only set because they guarantee that we will not
	// run off the canvas UNLESS someone has massive monitors. Normally we
	// would use the HEIGHT and WIDTH given by canvas, but they lots of things
	// would need to be reinitialized if the window gets resized. Will have
	// to look into that more.
	private final int MAPWIDTH = 2048;
	private final int MAPHEIGHT = 2048;
	
	/**
	 * Constructs a ClickSelector that allows users to click entities
	 * and get their entity ID in return.
	 * @param context
	 * @param c
	 */
	public ClickSelector(WebGLRenderingContext context, GameCanvas c) {
		glContext = context;
		canvas = c;
		initFrameRenderbuffers();
		initShader();
	}
	
	/**
	 * @param x	X-coordinate of the canvas window
	 * @param y	Y-coordinate of the canvas window
	 * @return	The integer ID of the clicked entity
	 */
	public int pick(int x, int y) {
		int entID = 0;
		//System.out.println(""+x+", "+y);
		renderEntitiesAsColors();
		entID = getID(x,y);
		resetFramebuffer();
		return entID;
	}
	
	/**
	 * Initialize the shader that will color entities based on their IDs
	 */
	private void initShader() {
		idShader = new Shader(glContext,ClientResources.INSTANCE
				.simpleMeshVS().getText(),ClientResources.INSTANCE
				.idFS().getText());
	}
	
	private void renderEntitiesAsColors() {
		// Bind our framebuffer so that we can render these entities off screen
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer);
		// Clear the current framebuffer 
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT);
		// Reset the clearColor back to black in case the clear color ever changes
		glContext.clearColor(0, 0, 0, 255);
		// Render the list of entities attached to the canvas
		canvas.renderEntities(idShader);
	}
	
	/** 
	 * @param x	X-coordinate of the canvas window
	 * @param y	Y-coordinate of the canvas window
	 * @return	The integer ID of the clicked entity
	 */
	private int getID(int x, int y) {
		int id = 0;
		// A simple array for storing a single pixel of 4 (rgba) components.
		// This may change to allow for box selection later on.
		Uint8Array pixelData = Uint8Array.create(4);
		glContext.readPixels(x, canvas.HEIGHT - y, 1, 1, WebGLRenderingContext.RGBA,WebGLRenderingContext.UNSIGNED_BYTE, pixelData);
		//System.out.println("R:" + pixelData.get(0) + " G:" + pixelData.get(1) +" B:" + pixelData.get(2) +" A:" + pixelData.get(3));
		// Convert the pixel rgb components to an entity id
		id = pixelData.get(2) + (pixelData.get(1) * 256) + (pixelData.get(0) * 65536);
		return id;
	}
	
	/**
	 * Reset the framebuffer to the default framebuffer
	 */
	private void resetFramebuffer() {
		// Reset to the default framebuffer (which is normally the screen)
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null);
	}
	
	/**
	 * Initializes an empty texture that will store the image when we 
	 * render entities according to their IDs.
	 */
	private void initFrameTexture() {
		// Create a new empty texture
		frametexture = glContext.createTexture();
	
		// Bind the texture so that parameter changes (below) will affect it
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, frametexture);
		
		// Set some parameters of the texture
		glContext.texImage2D(WebGLRenderingContext.TEXTURE_2D, 0,
				WebGLRenderingContext.RGBA, MAPWIDTH, MAPHEIGHT, 0, WebGLRenderingContext.RGBA,
				WebGLRenderingContext.UNSIGNED_BYTE, null);
		
		// Set more parameters that are mostly just visual fluff
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_MAG_FILTER,
				WebGLRenderingContext.NEAREST);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_MIN_FILTER,
				WebGLRenderingContext.NEAREST);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_WRAP_S,
				WebGLRenderingContext.CLAMP_TO_EDGE);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_WRAP_T,
				WebGLRenderingContext.CLAMP_TO_EDGE);
		glContext.generateMipmap(WebGLRenderingContext.TEXTURE_2D);
	
		//	glContext.activeTexture(WebGLRenderingContext.TEXTURE0);
		//	glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, minimapTexture);
	}
	
	/**
	 * Initializes both the frame and render buffers. The framebuffer is linked to the color
	 * component of frametexture. The renderbuffer is linked to the depth component so that
	 * entities do not overlap each other while rendering.
	 */
	private void initFrameRenderbuffers() {
		// Create a new empty framebuffer
		framebuffer = glContext.createFramebuffer();
		
		//Create a new empty renderbuffer
		renderbuffer = glContext.createRenderbuffer();
		
		// Bind the framebuffer so parameter changes will affect it
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer);
				
		initFrameTexture();
		
		// Bind the renderbuffer which keeps track of the depth component
		glContext.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, renderbuffer);
	    glContext.renderbufferStorage(WebGLRenderingContext.RENDERBUFFER, WebGLRenderingContext.DEPTH_COMPONENT16,
	    		MAPWIDTH, MAPHEIGHT);
	    
		// Tell the framebuffer that it will be attached to the texture
	    glContext.framebufferTexture2D(WebGLRenderingContext.FRAMEBUFFER,
	    		WebGLRenderingContext.COLOR_ATTACHMENT0, WebGLRenderingContext.TEXTURE_2D,
	    		frametexture, 0);
	    // Tell the framebuffer to use renderbuffer as its RENDERBUFFER
	    glContext.framebufferRenderbuffer(WebGLRenderingContext.FRAMEBUFFER,
	    		WebGLRenderingContext.DEPTH_ATTACHMENT, WebGLRenderingContext.RENDERBUFFER,
	    		renderbuffer);
	    
	    // Reset the binding positions to their defaults. These will be rebound later
	    // when they're actually needed
	    glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, null);
	    glContext.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, null);
	    glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null);

	}
}
