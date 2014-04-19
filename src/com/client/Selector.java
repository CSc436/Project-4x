package com.client;

import com.googlecode.gwtgl.array.Uint8Array;
import com.googlecode.gwtgl.binding.WebGLFramebuffer;
import com.googlecode.gwtgl.binding.WebGLRenderbuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLTexture;

public class Selector {

	private WebGLRenderingContext glContext;
	private GameCanvas canvas;
	private Shader entityIdShader, tileIdShader;
	private WebGLFramebuffer entityFramebuffer, tileFrameBuffer;
	private WebGLRenderbuffer entityRenderbuffer, tileRenderBuffer;
	private WebGLTexture entityTexture, tileTexture;
	
	// These values are only set because they guarantee that we will not
	// run off the canvas UNLESS someone has massive monitors. Normally we
	// would use the HEIGHT and WIDTH given by canvas, but they lots of things
	// would need to be reinitialized if the window gets resized. Will have
	// to look into that more.
	private final int MAPWIDTH = 2048;
	private final int MAPHEIGHT = 2048;
	public boolean invalidMap = true;
	
	/**
	 * Constructs a ClickSelector that allows users to click entities
	 * and get their entity ID in return.
	 * @param context
	 * @param c
	 */
	public Selector(WebGLRenderingContext context, GameCanvas c) {
		glContext = context;
		canvas = c;
		initFrameRenderbuffers();
		initShaders();
	}
	
	/**
	 * @param x	X-coordinate of the canvas window
	 * @param y	Y-coordinate of the canvas window
	 * @return	The integer ID of the clicked entity
	 */
	public int pickEntity(int x, int y) {
		int entID = 0;
		//System.out.println(""+x+", "+y);
		renderEntitiesAsColors();
		entID = getID(x,y);
		resetFramebuffer();
		return entID;
	}
	
	public Coordinate pickTile(int x, int y){
		
		if (invalidMap)
			renderTileFrameBuffer();
		
		Console.log("Picking at (" + x + ", " + y + ")");
		
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, tileFrameBuffer);
		
		Uint8Array pixelData = Uint8Array.create(4);
		glContext.readPixels(x, canvas.HEIGHT - y, 1, 1, WebGLRenderingContext.RGBA,WebGLRenderingContext.UNSIGNED_BYTE, pixelData);
		String s = "[";
		for (int i = 0; i < 4; i ++)
			s = s + pixelData.get(i) + (i < 3 ? ", " : "");
		Console.log(s + "]");
		
		if (invalidMap){
			//resetFramebuffer();
			invalidMap = false;
		}
		
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null);
		
		return new Coordinate(pixelData.get(0), pixelData.get(1));
	}
	
	private void renderTileFrameBuffer() {
		Console.log("Rendering tile frame buffe");
		// Bind our framebuffer so that we can render these entities off screen
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, tileFrameBuffer);
		// Clear the current framebuffer 
		glContext.clearColor(0.0f, 0.0f, 1.0f, 1.0f);
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);
		
		// Render the list of entities attached to the canvas
		canvas.renderTiles(tileIdShader);
		
		// UNCOMMENT TO RENDER TO SCREEN AS WELL
		// Note that this step ONLY occurs when a map is considered invalid, so frequently
		// it will only render for a single frame.
		//glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null);
		//glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);
		//canvas.renderTiles(tileIdShader);
		
		// Reset the clearColor back to black in case the clear color ever changes
		glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}

	/**
	 * Initialize the shader that will color entities based on their IDs
	 */
	private void initShaders() {
		entityIdShader = new Shader(glContext,ClientResources.INSTANCE
				.simpleMeshVS().getText(),ClientResources.INSTANCE
				.idFS().getText());
		tileIdShader = new Shader(glContext, ClientResources.INSTANCE.tileIdVS().getText(), 
				ClientResources.INSTANCE.tileIdFS().getText());
	}
	
	private void renderEntitiesAsColors() {
		// Bind our framebuffer so that we can render these entities off screen
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, entityFramebuffer);
		// Clear the current framebuffer 
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);
		// Reset the clearColor back to black in case the clear color ever changes
		glContext.clearColor(0, 0, 0, 255);
		// Render the list of entities attached to the canvas
		canvas.renderEntities(entityIdShader);
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
		entityTexture = glContext.createTexture();
	
		// Bind the texture so that parameter changes (below) will affect it
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, entityTexture);
		
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
		
		tileTexture = glContext.createTexture();
		
		// Bind the texture so that parameter changes (below) will affect it
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, tileTexture);
		
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
		entityFramebuffer = glContext.createFramebuffer();
		
		//Create a new empty renderbuffer
		entityRenderbuffer = glContext.createRenderbuffer();
		
		// Bind the framebuffer so parameter changes will affect it
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, entityFramebuffer);
				
		initFrameTexture();
		
		// Bind the renderbuffer which keeps track of the depth component
		glContext.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, entityRenderbuffer);
	    glContext.renderbufferStorage(WebGLRenderingContext.RENDERBUFFER, WebGLRenderingContext.DEPTH_COMPONENT16,
	    		MAPWIDTH, MAPHEIGHT);
	    
		// Tell the framebuffer that it will be attached to the texture
	    glContext.framebufferTexture2D(WebGLRenderingContext.FRAMEBUFFER,
	    		WebGLRenderingContext.COLOR_ATTACHMENT0, WebGLRenderingContext.TEXTURE_2D,
	    		entityTexture, 0);
	    // Tell the framebuffer to use renderbuffer as its RENDERBUFFER
	    glContext.framebufferRenderbuffer(WebGLRenderingContext.FRAMEBUFFER,
	    		WebGLRenderingContext.DEPTH_ATTACHMENT, WebGLRenderingContext.RENDERBUFFER,
	    		entityRenderbuffer);
	    
	    
	    // Create a new empty framebuffer
	    tileFrameBuffer = glContext.createFramebuffer();
		
		//Create a new empty renderbuffer
	    tileRenderBuffer = glContext.createRenderbuffer();
		
		// Bind the framebuffer so parameter changes will affect it
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, tileFrameBuffer);
		
		// Bind the renderbuffer which keeps track of the depth component
		glContext.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, tileRenderBuffer);
	    glContext.renderbufferStorage(WebGLRenderingContext.RENDERBUFFER, WebGLRenderingContext.DEPTH_COMPONENT16,
	    		MAPWIDTH, MAPHEIGHT);
	    
		// Tell the framebuffer that it will be attached to the texture
	    glContext.framebufferTexture2D(WebGLRenderingContext.FRAMEBUFFER,
	    		WebGLRenderingContext.COLOR_ATTACHMENT0, WebGLRenderingContext.TEXTURE_2D,
	    		tileTexture, 0);
	    // Tell the framebuffer to use renderbuffer as its RENDERBUFFER
	    glContext.framebufferRenderbuffer(WebGLRenderingContext.FRAMEBUFFER,
	    		WebGLRenderingContext.DEPTH_ATTACHMENT, WebGLRenderingContext.RENDERBUFFER,
	    		tileRenderBuffer);
	    
	    // Reset the binding positions to their defaults. These will be rebound later
	    // when they're actually needed
	    glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, null);
	    glContext.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, null);
	    glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null);

	}
}
