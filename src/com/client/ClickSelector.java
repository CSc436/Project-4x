package com.client;

import com.googlecode.gwtgl.array.Int32Array;
import com.googlecode.gwtgl.array.Uint8Array;
import com.googlecode.gwtgl.binding.WebGLFramebuffer;
import com.googlecode.gwtgl.binding.WebGLRenderbuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLTexture;

public class ClickSelector {

	private WebGLRenderingContext glContext;
	private GameCanvas canvas;
	private WebGLFramebuffer framebuffer;
	private WebGLRenderbuffer renderbuffer;
	private WebGLTexture frametexture;
	private final int MAPWIDTH = 2048;
	private final int MAPHEIGHT = 2048;
	
	public ClickSelector(WebGLRenderingContext context, GameCanvas c) {
		glContext = context;
		canvas = c;
		initFramebuffer();
	}
	
	/**
	 * 
	 * @param x	X-coordinate of the canvas window
	 * @param y	Y-coordinate of the canvas window
	 * @return	The integer ID of the clicked entity
	 */
	public int pick(int x, int y) {
		int entID = 0;
		System.out.println(""+x+", "+y);
		renderEntitiesAsColors();
		getID(x,y);
		resetFramebuffer();
		return entID;
	}
	
	private void renderEntitiesAsColors() {
		//WebGLRenderbuffer colorsRenderBuffer = glContext.createRenderbuffer();
		//glContext.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, colorsRenderBuffer);
		// Set the current framebuffer to the minimap buffer
		//glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, colorsFrameBuffer);
		//glContext.renderbufferStorage(WebGLRenderingContext.RENDERBUFFER, WebGLRenderingContext.RGBA, 1024, 1024);
		//canvas.drawScene();
		//resetRenderbuffer();
		
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer);
		// Clear the current framebuffer to green
		//glContext.clearColor(0, 255, 0, 255);
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT);
		// And reset the clearColor back to black
		glContext.clearColor(0, 0, 0, 255);
		canvas.drawScene();
	}
	
	/**
	 * 
	 * @param x	X-coordinate of the canvas window
	 * @param y	Y-coordinate of the canvas window
	 * @return	The integer ID of the clicked entity
	 */
	private int getID(int x, int y) {
		int id = 0;
		Uint8Array pixelData = Uint8Array.create(4);
		glContext.readPixels(x, y, 1, 1, WebGLRenderingContext.RGBA,WebGLRenderingContext.UNSIGNED_BYTE, pixelData);
		System.out.println("R:" + pixelData.get(0) + " G:" + pixelData.get(1) +" B:" + pixelData.get(2) +" A:" + pixelData.get(3));
		return id;
	}
	
	private void resetFramebuffer() {
		// Reset to the default framebuffer
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null);
	}
	
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
	
	private void initFramebuffer() {
		// Create a new empty framebuffer
		framebuffer = glContext.createFramebuffer();
		
		// Bind the framebuffer to parameter changes will affect it
		glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer);
		//renderbuffer = glContext.createRenderbuffer();
				
		initFrameTexture();
		
//		glContext.bindRenderbuffer(glContext.RENDERBUFFER, renderbuffer);
//	    glContext.renderbufferStorage(glContext.RENDERBUFFER, WebGLRenderingContext.DEPTH_COMPONENT16,
//	    		MAPWIDTH, MAPHEIGHT);
	    
		// Tell the framebuffer that it will be attached to the texture
	    glContext.framebufferTexture2D(WebGLRenderingContext.FRAMEBUFFER,
	    		WebGLRenderingContext.COLOR_ATTACHMENT0, WebGLRenderingContext.TEXTURE_2D,
	    		frametexture, 0);
//	    glContext.framebufferRenderbuffer(WebGLRenderingContext.FRAMEBUFFER,
//	    		WebGLRenderingContext.DEPTH_ATTACHMENT, WebGLRenderingContext.RENDERBUFFER,
//	    		renderbuffer);
	    
	    // Reset to defaults
	    glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, null);
	    glContext.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, null);
	    glContext.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null);

	}
}
