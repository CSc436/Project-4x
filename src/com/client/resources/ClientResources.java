package com.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.javac.testing.impl.StaticJavaResource;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface ClientResources extends ClientBundle{
	public static final ClientResources INSTANCE = GWT.create(ClientResources.class);
	
	@Source("river_rock.jpg")
	ImageResource riverTexture();
	
	@Source("6terrains.png")
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
    
    @Source(value={"simple_mesh.vs"})
    TextResource simpleMeshVS();
    
    @Source(value={"normals_mesh.fs"})
    TextResource normalsMeshFS();
    
    @Source(value={"id.fs"})
    TextResource idFS();
    
    @Source(value={"selected.vs"})
    TextResource selectedVS();
    
    @Source(value={"selected.fs"})
    TextResource selectedFS();
    
    @Source(value={"meshTextured.fs"})
    TextResource texturedMeshFS();
    
    @Source(value={"meshTexturedPhong.fs"})
    TextResource texturedMeshPhongFS();
    
    @Source(value={"tileId.fs"})
    TextResource tileIdFS();
    
    @Source(value={"tileId.vs"})
    TextResource tileIdVS();
    
    @Source(value={"selectbox.fs"})
    TextResource selectboxFS();
    
    @Source(value={"selectbox.vs"})
    TextResource selectboxVS();
    
    // OBJ definitions
    @Source(value={"barrel.obj"})
    TextResource barrelOBJ();
    
    @Source(value={"cube.obj"})
    TextResource cubeOBJ();
    
    @Source(value={"castle.obj"})
    TextResource castleOBJ();
    
    @Source(value={"cannon.obj"})
    TextResource cannonOBJ();
    
    @Source(value={"swordsman.obj"})
    TextResource swordsmanOBJ();
    
    @Source(value={"archer.obj"})
    TextResource archerOBJ();
    
    @Source(value={"barracks.obj"})
    TextResource barracksOBJ();
    
    @Source(value={"floorSprite.obj"})
    TextResource floorSpriteOBJ();
    
    @Source(value={"ring.obj"})
    TextResource ringOBJ();
    
    @Source("castle_base_colors_medium.jpg")
    ImageResource castleTexture();
	
    @Source("cannon_base_colors_small.png")
    ImageResource cannonTexture();
    
    @Source("swordsman_base_colors_medium.png")
    ImageResource swordsmanTexture();
    
    @Source("archer_base_colors_medium.jpg")
    ImageResource archerTexture();
    
    @Source("barracks_base_colors_medium.jpg")
    ImageResource barracksTexture();
    
    @Source("ring_medium.png")
    ImageResource ringTexture();
    
    @Source(value={"tile.obj"})
    TextResource tileOBJ();
    
    @Source("genericTexture.png")
    ImageResource genericTexture();
	
    @Source("terrains.png")
    ImageResource terrainTexture();

}

