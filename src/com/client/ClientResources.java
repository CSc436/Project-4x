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
	
    @Source(value={"fragment-shader.txt"})
    TextResource fragmentShader();
    
    @Source(value={"vertex-shader.txt"})
    TextResource vertexShader();
}
