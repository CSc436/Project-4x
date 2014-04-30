package com.client;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.client.gameinterface.Console;
import com.client.model.ClientModel;
import com.client.rendering.Camera;
import com.client.rendering.Mesh;
import com.client.rendering.OBJImporter;
import com.client.rendering.RenderTile;
import com.client.rendering.Selector;
import com.client.rendering.Shader;
import com.client.resources.ClientResources;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLTexture;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;
import com.shared.model.commands.Command;
import com.shared.model.commands.MoveUnitCommand;
import com.shared.model.entities.GameObject;
import com.shared.utils.Coordinate;
import com.shared.utils.Vector3;

public class GameCanvas {
	private WebGLRenderingContext glContext;
	private WebGLProgram shaderProgram, agentShader;
	private WebGLTexture texture;
	
	private int vertexPositionAttribute, vertexTexCoordAttrib;
	private int agentVertAttrib, agentTexAttrib;
	private WebGLUniformLocation texUniform, matrixUniform, camPosUniform;
	private WebGLBuffer tileVertexBuffer, tileTexCoordBuffer, tileSelectBuffer;
	private WebGLBuffer entityVertBuffer, entityTexBuffer;
	
	private Float32Array tileVertexData, tileTexCoordData, tileSelectData;
	private Float32Array agentVertData, agentTexData;
	
	public static int WIDTH, HEIGHT;
	
	private Camera camera;
	private boolean in = false, out = false, up = false, down = false,
			right = false, left = false, rotateLeft = false, rotateRight = false, center = false, move = false;
	private Vector3 mouseVector;
	
	private float agentX = 0.0f, agentY = 0.0f, agentZ = -0.1f;

	public static final int GRID_WIDTH = 12;
	private long time;
	private final int NUM_TILES = GRID_WIDTH * GRID_WIDTH;
	
	private final boolean debug = true;
	
	private final ClientModel theModel;
	private final Canvas webGLCanvas = Canvas.createIfSupported();
	
	private Selector objectSelector;
	private HashMap<Integer,Mesh> entities;
	public ArrayList<Integer> selectedEntities;

	public GameCanvas(ClientModel theModel) {
		// CODE FOR MINIMAP DEV/CLICK SELECTING
		selectedEntities = new ArrayList<Integer>();
		this.mouseVector = new Vector3(0,0,0);
		// END OF CODE
		
		RootPanel.get("gwtGL").add(webGLCanvas);
		glContext = (WebGLRenderingContext) webGLCanvas
				.getContext("experimental-webgl");

		if (glContext == null) {
			Window.alert("Sorry, your browser doesn't support WebGL!");
		}
		
		// These lines make the viewport fullscreen
		webGLCanvas.setCoordinateSpaceHeight(webGLCanvas.getParent()
				.getOffsetHeight());
		webGLCanvas.setCoordinateSpaceWidth(webGLCanvas.getParent()
				.getOffsetWidth());
		HEIGHT = webGLCanvas.getParent().getOffsetHeight();
		WIDTH = webGLCanvas.getParent().getOffsetWidth();
		camera = new Camera();

		glContext.viewport(0, 0, WIDTH, HEIGHT);
		
		// MORE CLICK CODE
		objectSelector = new Selector(glContext, this);
		initEntities();
		
		this.theModel = theModel;
		
		registerMapMovements();
		registerResizeHandler();
		camera.makeCameraMatrix();
		start();
		Console.log("done with Game Canvas");
	}
	
	/**
	 * Creates the unit id -> Mesh map used to render entities,
	 * Populates with a few starter entities
	 */
	private void initEntities() {
		entities = new HashMap<Integer,Mesh>();
		
		final Mesh ent2 = OBJImporter.objToMesh(ClientResources.INSTANCE.cubeOBJ().getText(), glContext);
		ent2.posX = 20.0f;
		ent2.id = 65432;
		entities.put(ent2.id, ent2);
		
		final Mesh castle1 = OBJImporter.objToMesh(ClientResources.INSTANCE.castleOBJ().getText(), glContext);
		castle1.setTexture(glContext, ClientResources.INSTANCE.castleTexture());
		castle1.posX = 2.5f;
		castle1.posY = 2.5f;
		castle1.posZ = 0.0f;
		castle1.id = 123321;
		entities.put(castle1.id, castle1);
		
		final Mesh cannon1 = OBJImporter.objToMesh(ClientResources.INSTANCE.cannonOBJ().getText(), glContext);
		cannon1.setTexture(glContext, ClientResources.INSTANCE.cannonTexture());
		cannon1.posX = 4.5f;
		cannon1.posY = 4.5f;
		cannon1.posZ = 0.0f;
		cannon1.id = 777777;
		entities.put(cannon1.id, cannon1);

		final Mesh unit1 = OBJImporter.objToMesh(ClientResources.INSTANCE.tileOBJ().getText(), glContext);
		final Mesh unit2 = OBJImporter.objToMesh(ClientResources.INSTANCE.tileOBJ().getText(), glContext);
		final Mesh unit3 = OBJImporter.objToMesh(ClientResources.INSTANCE.tileOBJ().getText(), glContext);
		final Mesh unit4 = OBJImporter.objToMesh(ClientResources.INSTANCE.tileOBJ().getText(), glContext);
		final Mesh unit5 = OBJImporter.objToMesh(ClientResources.INSTANCE.tileOBJ().getText(), glContext);
		unit1.id = 1;
		unit1.posZ = -0.1f;
		unit2.id = 2;
		unit2.posZ = -0.1f;
		unit3.id = 3;
		unit3.posZ = -0.1f;
		unit4.id = 4;
		unit4.posZ = -0.1f;
		unit5.id = 5;
		unit5.posZ = -0.1f;

		entities.put(1, unit1);
		entities.put(2, unit2);
		entities.put(3, unit3);
		entities.put(4, unit4);
		entities.put(5, unit5);
	}
	
	/**
	 * Renders each entity in the map with the given shader
	 * @param shader
	 */
	public void renderEntities(Shader shader) {
		Set<Integer> keys = entities.keySet();
		Integer[] keysArr = new Integer[entities.size()];
		keysArr = keys.toArray(keysArr);
		for(int i = 0; i< keysArr.length; i++) {
			entities.get(keysArr[i]).render(glContext, shader, camera);
		}
	}
	
	/**
	 * 
	 */
	public void renderTiles(Shader shader){
//		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT
//				| WebGLRenderingContext.DEPTH_BUFFER_BIT);

		glContext.useProgram(shader.shaderProgram);
		
		// These attribute locations are hard coded. I don't know if they change on
		//different hardware though, so be wary of that.
		int tileSelectAttrib = 0;
		int vertexPositionAttribute = 1;
		
		// These keeps returning -1 meaning it could not find the attributes. No clue why.
		//int tileSelectAttrib = glContext.getAttribLocation(shader.shaderProgram, "tileSelectColor");
		//Console.log("tileSelectAttrib = " + tileSelectAttrib);
		//int vertexPositionAttribute = glContext.getAttribLocation(shader.shaderProgram, "vertexPositoin");
		//Console.log("vertexPositionAttribute = " + vertexPositionAttribute);
		
		Console.log("Rendering tile selection");
		glContext.enableVertexAttribArray(tileSelectAttrib);
		glContext.enableVertexAttribArray(vertexPositionAttribute);
		
		// vertices
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileVertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// select color
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileSelectBuffer);
		glContext.vertexAttribPointer(tileSelectAttrib , 2,
				WebGLRenderingContext.FLOAT, false, 0, 0);
		
		// uniforms
		WebGLUniformLocation camPosUniform = glContext.getUniformLocation(shader.shaderProgram, "camPos");
		glContext.uniform3f(camPosUniform, camera.getX(), camera.getY(), camera.getZ());
		
		WebGLUniformLocation matrixUniform = glContext.getUniformLocation(shader.shaderProgram, "perspectiveMatrix");
		glContext.uniformMatrix4fv(matrixUniform, false, camera.getCameraMatrix());

		// draw geometry
		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, NUM_TILES * 6);
		
		glContext.disableVertexAttribArray(vertexPositionAttribute);
		glContext.disableVertexAttribArray(tileSelectAttrib);
		
		//glContext.flush();

		//-------------------------------------------
//		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT
//				| WebGLRenderingContext.DEPTH_BUFFER_BIT);
//
//		glContext.useProgram(shaderProgram);
//
//		glContext.enableVertexAttribArray(vertexPositionAttribute);
//		glContext.enableVertexAttribArray(vertexTexCoordAttrib);
//
//		// vertices
//		glContext.uniformMatrix4fv(matrixUniform, false, camera.getCameraMatrix());
//		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileVertexBuffer);
//		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
//				WebGLRenderingContext.FLOAT, false, 0, 0);
//
//		// texture coordinates
//		glContext
//				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileTexCoordBuffer);
//		glContext.vertexAttribPointer(vertexTexCoordAttrib, 2,
//				WebGLRenderingContext.FLOAT, false, 0, 0);
//
//		// uniforms
//		glContext.uniform3f(camPosUniform, camera.getX(), camera.getY(), camera.getZ());
//
//		// texture
//		glContext.activeTexture(WebGLRenderingContext.TEXTURE0);
//		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
//		glContext.uniform1i(texUniform, 0);
//
//		// draw geometry
//		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, NUM_TILES * 6);
//		
//		glContext.disableVertexAttribArray(vertexPositionAttribute);
//		glContext.disableVertexAttribArray(vertexTexCoordAttrib);
//
//		renderAgent();
//		
//		glContext.flush();
	}
	
	/**
	 * Renders selected entities with the selection shader
	 * @param selectedShader
	 */
	public void renderSelectedEntities(Shader selectedShader) {
		int size = selectedEntities.size();
		for(int i = 0; i < size; i++) {
			entities.get(selectedEntities.get(i)).render(glContext, selectedShader, camera);
		}
	}
	
	/**
	 * Binds keys to browser window to move map around and zoom in/out
	 */
	private void registerMapMovements() {
		RootPanel.get().addDomHandler(new KeyDownHandler() {
			private long lastHit = System.currentTimeMillis();

			@Override
			public void onKeyDown(KeyDownEvent event) {
				objectSelector.invalidMap = true;
				if (debug) Console.log("Pressed: " + event.getNativeKeyCode());
				switch (event.getNativeKeyCode()) {
				case KeyCodes.KEY_UP:
				case KeyCodes.KEY_W:
					up = true;
					break;
				case KeyCodes.KEY_DOWN:
				case KeyCodes.KEY_S:
					down = true;
					break;
				case KeyCodes.KEY_LEFT:
				case KeyCodes.KEY_A:
					left = true;
					break;
				case KeyCodes.KEY_RIGHT:
				case KeyCodes.KEY_D:
					right = true;
					break;
				case KeyCodes.KEY_P: out = true; break;
				case KeyCodes.KEY_O: in = true; break;
				case KeyCodes.KEY_Q: rotateLeft = true; break;
				case KeyCodes.KEY_E: rotateRight = true; break;
				case KeyCodes.KEY_X: center = true; break;
				default: if (debug) Console.log("Unrecognized: " + event.getNativeKeyCode()); break;
				}
			}
		}, KeyDownEvent.getType());

		RootPanel.get().addDomHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				switch (event.getNativeKeyCode()) {
				case KeyCodes.KEY_UP:
				case KeyCodes.KEY_W:
					up = false; break;
				case KeyCodes.KEY_DOWN:
				case KeyCodes.KEY_S:
					down = false; break;
				case KeyCodes.KEY_LEFT:
				case KeyCodes.KEY_A:
					left = false; break;
				case KeyCodes.KEY_RIGHT:
				case KeyCodes.KEY_D:
					right = false; break;
				case KeyCodes.KEY_O: in = false; break;
				case KeyCodes.KEY_P: out = false; break;
				case KeyCodes.KEY_Q: rotateLeft = false; break;
				case KeyCodes.KEY_E: rotateRight = false; break;
				case KeyCodes.KEY_X: center = false; break;
				default: break;
				}
			}
		}, KeyUpEvent.getType());

		// Handle mousedown events (for any button on the moues)
		RootPanel.get().addDomHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				switch(event.getNativeButton()) {
				case NativeEvent.BUTTON_LEFT:
					if(event.isShiftKeyDown()) {
						int targetID = objectSelector.pickEntity(event.getClientX(), event.getClientY());
						System.out.println("Selected entity with ID " + targetID + ".");
						if (entities.containsKey(targetID)) {
							System.out.println("This entity exists! Adding to selected entities...");
							double x = entities.get(targetID).posX;
							double y = entities.get(targetID).posY;
							for(Integer i : selectedEntities) {
								theModel.sendCommand(new MoveUnitCommand(i,x,y));
							}
						}
						else {
							System.out.println("This entity DOES NOT exist!");
						}
					} else {
						if(!event.isControlKeyDown()) selectedEntities.clear();
						int selectedID = objectSelector.pickEntity(event.getClientX(), event.getClientY());
						if(selectedID < 50 || true) {
							System.out.println("Selected entity with ID " + selectedID + ".");
							if (entities.containsKey(selectedID)) {
								System.out.println("This entity exists! Adding to selected entities...");
								selectedEntities.add(selectedID);
							}
							else {
								System.out.println("This entity DOES NOT exist!");
							}
						}
					}
					break;
				}
				
			}
	
		}, MouseDownEvent.getType());
		
		RootPanel.get().addDomHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				//Console.log("X: " + event.getClientX() + ", Y: " + event.getClientY());
				if (GameCanvas.this.onEdgeOfMap(event)) {
					GameCanvas.this.mouseVector = Vector3.getVectorBetween(GameCanvas.this.getCenterOfMap(), new Vector3(event.getClientX(), event.getClientY(), 0));
					GameCanvas.this.move = true;
				} else {
					GameCanvas.this.move = false;
				}
				Coordinate c = objectSelector.pickTile(event.getClientX(), event.getClientY());
				// A quick check to make sure that we did not miss the map
				if(c.x >= 0.0) {
					c.x = (int)(c.x / (255/GRID_WIDTH));
					c.y = (int)(c.y / (255/GRID_WIDTH));
				}
				RootPanel.get("tile-info").getElement().setInnerHTML(c.toString());
				Console.log("TILE: " + c.toString());
			}
			
		}, MouseMoveEvent.getType());
	}
	
	private Vector3 getCenterOfMap() {
		int centerX = webGLCanvas.getAbsoluteLeft() + webGLCanvas.getCoordinateSpaceWidth()/2;
		int centerY = webGLCanvas.getAbsoluteTop() + webGLCanvas.getCoordinateSpaceHeight()/2;
		return new Vector3(centerX, centerY, 0);
	}

	private boolean onEdgeOfMap(MouseMoveEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Dynamically resizes the viewport to keep it full screen
	 */
	private void registerResizeHandler() {
		// Resize callback
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent e) {
				webGLCanvas.setCoordinateSpaceHeight(webGLCanvas.getParent()
						.getOffsetHeight());
				webGLCanvas.setCoordinateSpaceWidth(webGLCanvas.getParent()
						.getOffsetWidth());

				HEIGHT = webGLCanvas.getParent().getOffsetHeight();
				WIDTH = webGLCanvas.getParent().getOffsetWidth();

				glContext.viewport(0, 0, WIDTH, HEIGHT);
				camera.makeCameraMatrix();
			}
		});

		initClickHandlers();
		camera.makeCameraMatrix();
	}

	/**
	 * Registers handlers for Interface buttons
	 */
	private void initClickHandlers() {
		// City Menu Button
		$("#city-button").click(new Function() {
			public boolean f(Event e) {
				// Show city menu
				toggleSidebar(false);
				$("#agent-menu").hide();
				$("#city-menu").show();
				// Change content to city menu
				return true; // Default return true
			}
		});

		// Agent Menu Button
		$("#agent-button").click(new Function() {
			public boolean f(Event e) {
				// Show agent menu
				toggleSidebar(false);
				$("#city-menu").hide();
				$("#agent-menu").show();
				// Change content to agent menu
				return true; // Default return true
			}
		});

		// Sidebar close/open
		$("#sidebar-hide").click(new Function() {
			public boolean f(Event e) {
				// Hide sidebar
				toggleSidebar(true);
				return true; // Default return true
			}
		});
	}
	
	/**
	 * Registers handlers for showing/hiding sidebar
	 */
	private void toggleSidebar(boolean hideIfShowing) {
		String left = $("#sidebar").css("left");
		if (left.equals("0px")) {
			//Hide only if param is true
			if (hideIfShowing) {
				int width = $("#sidebar").outerWidth(true);
				int closeWidth = $("#sidebar-hide").outerWidth(true);
				$("#sidebar").animate("left:-" + (width + closeWidth));
			}
		} else {
			//Show sidebar
			$("#sidebar").animate("left:0");
		}
	}	
	
	/**
	 * Updates the position of the camera
	 */
	private void updateCamera() {
		float camZ = camera.getZ();
		float delta = camZ / 10.0f;
		
		if (up)
			camera.up(delta);
		if (down)
			camera.down(delta);
		if (left)
			camera.left(delta);
		if (right)
			camera.right(delta);
		if (in && camZ >= 2.0)
			camera.zoomIn();
		if (out && camZ <= 25.0f)
			camera.zoomOut();
		if (rotateLeft)
			camera.rotateLeft();
		if (rotateRight)
			camera.rotateRight();
		if (center)
			camera.defaultPosition();
		if (move)
			camera.move(mouseVector);
	}

	/**
	 * 
	 */
	private void start() {
		glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glContext.clearDepth(1.0f);
		glContext.enable(WebGLRenderingContext.DEPTH_TEST);
		glContext.depthFunc(WebGLRenderingContext.LEQUAL);

		initTexture();
		initShaders();
		agentShader();
		
		makeTiles();
		makeAgent();
		initBuffers();
		
		final Shader texturedMeshShader = new Shader(glContext,ClientResources.INSTANCE
				.simpleMeshVS().getText(),ClientResources.INSTANCE
				.texturedMeshFS().getText());
		
		final Shader texturedPhongMeshShader = new Shader(glContext,ClientResources.INSTANCE
				.simpleMeshVS().getText(),ClientResources.INSTANCE
				.texturedMeshPhongFS().getText());
		
		final Shader normalShader = new Shader(glContext,ClientResources.INSTANCE
				.simpleMeshVS().getText(),ClientResources.INSTANCE
				.normalsMeshFS().getText());
		
		final Shader idShader = new Shader(glContext,ClientResources.INSTANCE
				.simpleMeshVS().getText(),ClientResources.INSTANCE
				.idFS().getText());
		
		final Shader selectedShader = new Shader(glContext,ClientResources.INSTANCE
				.simpleMeshVS().getText(),ClientResources.INSTANCE
				.selectedFS().getText());
		
		final Mesh barrel = OBJImporter.objToMesh(ClientResources.INSTANCE.barrelOBJ().getText(), glContext);

		// repaint timer
		Timer t = new Timer() {
			@Override
			public void run() {
				time = System.currentTimeMillis();
				
				float[] pos;
				agentX = 0;
				agentY = 0;
				
				Map<Integer, GameObject> modelEntities = theModel.getGameModel().getGameObjects();
				Set<Integer> keySet = modelEntities.keySet();
				for(Integer i : keySet) {
					Mesh currMesh = entities.get(i);
					pos = theModel.getPosition(i, System.currentTimeMillis());
					currMesh.posX = pos[0];
					currMesh.posY = pos[1];
				}
				
				updateCamera();
				drawScene();
				
				barrel.posX = agentX;
				barrel.posY = agentY;
				barrel.posZ = (float) (agentZ + Math.sin(time/300));
				barrel.rotX = barrel.rotX + 0.01f;
				
				renderEntities(texturedPhongMeshShader);
				renderSelectedEntities(selectedShader);
			}
		};
		t.scheduleRepeating(16); // roughly 30 FPS
	}
	
	/**
	 * Loads terrain texture 
	 */
	private void initTexture() {
		texture = glContext.createTexture();

		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
		glContext.texImage2D(WebGLRenderingContext.TEXTURE_2D, 0,
				WebGLRenderingContext.RGB, WebGLRenderingContext.RGB,
				WebGLRenderingContext.UNSIGNED_BYTE, ImageElement.as(getImage(
						ClientResources.INSTANCE.terrainTexture())
						.getElement()));
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_MAG_FILTER,
				WebGLRenderingContext.LINEAR);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_MIN_FILTER,
				WebGLRenderingContext.LINEAR);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_WRAP_S,
				WebGLRenderingContext.CLAMP_TO_EDGE);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_WRAP_T,
				WebGLRenderingContext.CLAMP_TO_EDGE);
		glContext.generateMipmap(WebGLRenderingContext.TEXTURE_2D);

		glContext.activeTexture(WebGLRenderingContext.TEXTURE0);
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
	}

	/**
	 * Loads an image resource 
	 * @param imageResource
	 * @return	the loaded image
	 */
	public Image getImage(final ImageResource imageResource) {
		final Image img = new Image();
		img.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				RootPanel.get().remove(img);
			}
		});
		img.setVisible(false);
		RootPanel.get().add(img);

		img.setUrl(imageResource.getSafeUri());
		return img;
	}

	/**
	 * Sets up the shaders for rendering the terrain
	 */
	public void initShaders() {
		WebGLShader fragmentShader = getShader(
				WebGLRenderingContext.FRAGMENT_SHADER, ClientResources.INSTANCE
						.textureShader().getText());
		WebGLShader vertexShader = getShader(
				WebGLRenderingContext.VERTEX_SHADER, ClientResources.INSTANCE
						.vertexShader().getText());

		shaderProgram = glContext.createProgram();
		glContext.attachShader(shaderProgram, vertexShader);
		glContext.attachShader(shaderProgram, fragmentShader);
		glContext.linkProgram(shaderProgram);

		if (!glContext.getProgramParameterb(shaderProgram,
				WebGLRenderingContext.LINK_STATUS)) {
			throw new RuntimeException("Could not initialise shaders");
		}

		vertexPositionAttribute = glContext.getAttribLocation(shaderProgram,
				"vertexPosition");
		vertexTexCoordAttrib = glContext.getAttribLocation(shaderProgram,
				"vertexTexCoord");

		texUniform = glContext.getUniformLocation(shaderProgram, "texture");
		matrixUniform = glContext.getUniformLocation(shaderProgram,
				"perspectiveMatrix");
		camPosUniform = glContext.getUniformLocation(shaderProgram, "camPos");
	}
	
	/**
	 * Loads shaders for rendering agents
	 */
	public void agentShader(){
		WebGLShader fragmentShader = getShader(
				WebGLRenderingContext.FRAGMENT_SHADER, ClientResources.INSTANCE
						.fragmentShader().getText());
		WebGLShader vertexShader = getShader(
				WebGLRenderingContext.VERTEX_SHADER, ClientResources.INSTANCE
						.agentVertexShader().getText());

		agentShader = glContext.createProgram();
		glContext.attachShader(agentShader, vertexShader);
		glContext.attachShader(agentShader, fragmentShader);
		glContext.linkProgram(agentShader);

		if (!glContext.getProgramParameterb(agentShader,
				WebGLRenderingContext.LINK_STATUS)) {
			throw new RuntimeException("Could not initialise shaders");
		}

		agentVertAttrib = glContext.getAttribLocation(agentShader,
				"vertexPosition");
		agentTexAttrib = glContext.getAttribLocation(agentShader,
				"vertexTexCoord");
	}

	private WebGLShader getShader(int type, String source) {
		WebGLShader shader = glContext.createShader(type);

		glContext.shaderSource(shader, source);
		glContext.compileShader(shader);

		if (!glContext.getShaderParameterb(shader,
				WebGLRenderingContext.COMPILE_STATUS)) {
			throw new RuntimeException(glContext.getShaderInfoLog(shader));
		}

		return shader;
	}

	/**
	 * Creates the vertex and texture coordinate buffer for  rendering
	 */
	private void initBuffers() {
		tileVertexBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileVertexBuffer);

		glContext.bufferData(glContext.ARRAY_BUFFER, tileVertexData,
				WebGLRenderingContext.DYNAMIC_DRAW);

		tileTexCoordBuffer = glContext.createBuffer();
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileTexCoordBuffer);

		glContext.bufferData(glContext.ARRAY_BUFFER, tileTexCoordData,
				WebGLRenderingContext.DYNAMIC_DRAW);
		
		tileSelectBuffer = glContext.createBuffer();
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileSelectBuffer);

		glContext.bufferData(glContext.ARRAY_BUFFER, tileSelectData,
				WebGLRenderingContext.DYNAMIC_DRAW);
	}
	
	private void makeAgent(){
		float[] verts = { 
				0.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				
				0.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 0.0f
		};
		
		float[] texs = {
				0.0f, 0.0f,
				1.0f, 0.0f,
				0.0f, 1.0f,
				
				0.0f, 1.0f,
				1.0f, 1.0f,
				1.0f, 0.0f
		};
		
		agentVertData = Float32Array.create(verts);
		agentTexData = Float32Array.create(texs);
		
		entityVertBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, entityVertBuffer);

		glContext.bufferData(glContext.ARRAY_BUFFER, agentVertData,
				WebGLRenderingContext.DYNAMIC_DRAW);

		entityTexBuffer = glContext.createBuffer();
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, entityTexBuffer);


		glContext.bufferData(glContext.ARRAY_BUFFER, agentTexData,
				WebGLRenderingContext.DYNAMIC_DRAW);
	}
	
	private void renderAgent(){
		glContext.useProgram(agentShader);

		glContext.enableVertexAttribArray(agentVertAttrib);
		glContext.enableVertexAttribArray(agentTexAttrib);

		// vertices
		glContext.uniformMatrix4fv(glContext.getUniformLocation(agentShader, "perspectiveMatrix"), false, camera.getCameraMatrix());
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, entityVertBuffer);
		glContext.vertexAttribPointer(agentVertAttrib, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// texture coordinates
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, entityTexBuffer);
		glContext.vertexAttribPointer(agentTexAttrib, 2,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// uniforms
		glContext.uniform3f(glContext.getUniformLocation(agentShader,  "camPos"), camera.getX(), camera.getY(), camera.getZ());
		
		glContext.uniform3f(glContext.getUniformLocation(agentShader,  "agentPos"), agentX, agentY, agentZ);

		// draw geometry
		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 6);
		
	}

	/**
	 * Creates the terrain tiles
	 */
	private void makeTiles() {
		System.out.println("Generating Tiles:");

		tileVertexData = Float32Array.create(NUM_TILES * 6 * 3);
		tileTexCoordData = Float32Array.create(NUM_TILES * 6 * 2);
		tileSelectData = Float32Array.create(NUM_TILES * 6 * 2);
		
		RenderTile[][] map = RenderTile.makeMap(System.currentTimeMillis(), GRID_WIDTH);
		
		int index = 0;
		for (int x = 0; x < GRID_WIDTH; x++)
			for (int y = 0; y < GRID_WIDTH; y++) {
				map[x][y].addToBuffer(index++, tileVertexData, tileTexCoordData, tileSelectData);
			}
	}

	public void drawScene() {
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT
				| WebGLRenderingContext.DEPTH_BUFFER_BIT);

		glContext.useProgram(shaderProgram);

		glContext.enableVertexAttribArray(vertexPositionAttribute);
		glContext.enableVertexAttribArray(vertexTexCoordAttrib);

		// vertices
		glContext.uniformMatrix4fv(matrixUniform, false, camera.getCameraMatrix());
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileVertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// texture coordinates
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, tileTexCoordBuffer);
		glContext.vertexAttribPointer(vertexTexCoordAttrib, 2,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// uniforms
		glContext.uniform3f(camPosUniform, camera.getX(), camera.getY(), camera.getZ());

		// texture
		glContext.activeTexture(WebGLRenderingContext.TEXTURE0);
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
		glContext.uniform1i(texUniform, 0);

		// draw geometry
		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, NUM_TILES * 6);
		
		glContext.disableVertexAttribArray(vertexPositionAttribute);
		glContext.disableVertexAttribArray(vertexTexCoordAttrib);

		renderAgent();
		
		glContext.flush();
	}
	
}
