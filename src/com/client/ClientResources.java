package com.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;

public interface ClientResources extends ClientBundle{
	public static final ClientResources INSTANCE = GWT.create(ClientResources.class);
	
	@Source("river_rock.jpg")
	ImageResource riverTexture();
	
	@Source("4terrains.png")
	ImageResource terrainTextures();
	
	@Source("32pxterrains.png")
	ImageResource textureAtlas();
	
    @Source(value={"simple_colors.fs"})
    TextResource fragmentShader();
    
    @Source(value={"texture-shader.txt"})
    TextResource textureShader();
    
    @Source(value={"vertex-shader.txt"})
    TextResource vertexShader();
    
    @Source(value={"fs_red.txt"})
    TextResource redShader();
    
    @Source(value={"vs_simple.txt"})
    TextResource agentVertexShader();
    
    @Source(value={"simple_colors.vs"})
    TextResource simpleColorsVS();
    
    @Source(value={"simple_colors.fs"})
    TextResource simpleColorsFS();
    
    @Source(value={"simple_outline.fs"})
    TextResource simpleOutlineFS();
}
