package com.client;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.HashMap;

import com.client.gameinterface.Console;
import com.client.model.ClientController;
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
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.ImageResource;
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
import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.commands.AttackCommand;
import com.shared.model.commands.BuildingProductionCommand;
import com.shared.model.commands.ConstructBuildingCommand;
import com.shared.model.commands.MoveUnitCommand;
import com.shared.model.commands.PlaceUnitCommand;
import com.shared.model.entities.GameObject;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;
import com.shared.utils.ColorFunctions;
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
	private WebGLBuffer selectVertBuffer;
	
	private Float32Array tileVertexData, tileTexCoordData, tileSelectData;
	private Float32Array agentVertData, agentTexData;
	
	public static int WIDTH, HEIGHT;
	
	private Camera camera;
	private boolean in = false, out = false, up = false, down = false,
			right = false, left = false, rotateLeft = false, rotateRight = false, center = false, move = false;
	private Vector3 mouseVector;
	
	private float agentX = 0.0f, agentY = 0.0f, agentZ = -0.1f;

	public static final int GRID_WIDTH = 256;
	private long time;
	private final int NUM_TILES = GRID_WIDTH * GRID_WIDTH;
	
	private final boolean debug = false;
	private final boolean commandDebug = true;
	
	private final ClientController theModel;
	private final Canvas webGLCanvas = Canvas.createIfSupported();
	
	private Selector objectSelector;
	private HashMap<UnitType,Mesh> unitMeshes;
	private HashMap<BuildingType,Mesh> buildingMeshes;
	
	private Coordinate mouseTile = new Coordinate(0,0);
	private Coordinate first, curr;
	
	public ArrayList<Integer> selectedEntities;
	
	private Mesh selectionRing;

	private boolean chatFlag = false; // if chat is selected, do not allow camera movment/input
	
	// Game mode
	private enum Mode {
		BUILDING, NONE
	}

	private static Mode currMode = Mode.NONE; // Set initial mode to NONE

	// Location of mouse X and Y
	private static int mouseX;
	private static int mouseY;

	// Collection of BuildingType enum
	private static BuildingType[] buildingTypes = BuildingType.values();
	private static int buildingCounter = -1;
	
	
	public GameCanvas(ClientController theModel) {
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
	 * sets chatFlag to true, turning off input to game canvas
	 */
	public void turnOnChatFlag()
	{
		chatFlag = true;
	}
	
	/**
	 * sets chatFlag to false, turning input to game canvas back on 
	 */
	public void turnOffChatFlag()
	{
		chatFlag = false;
	}
	
	/**
	 * Creates the unit id -> Mesh map used to render entities,
	 * Populates with a few starter entities
	 */
	private void initEntities() {
		unitMeshes = new HashMap<UnitType,Mesh>();
		buildingMeshes = new HashMap<BuildingType,Mesh>();
		selectionRing = OBJImporter.objToMesh(ClientResources.INSTANCE.ringOBJ().getText(), glContext);

		
		final Mesh castle1 = OBJImporter.objToMesh(ClientResources.INSTANCE.castleOBJ().getText(), glContext);
		castle1.setTexture(glContext, ClientResources.INSTANCE.castleTexture());
		
		final Mesh swordsman1 = OBJImporter.objToMesh(ClientResources.INSTANCE.swordsmanOBJ().getText(), glContext);
		swordsman1.setTexture(glContext, ClientResources.INSTANCE.swordsmanTexture());
		
		final Mesh archer1 = OBJImporter.objToMesh(ClientResources.INSTANCE.archerOBJ().getText(), glContext);
		archer1.setTexture(glContext, ClientResources.INSTANCE.archerTexture());
		
		final Mesh cannon1 = OBJImporter.objToMesh(ClientResources.INSTANCE.cannonOBJ().getText(), glContext);
		cannon1.setTexture(glContext, ClientResources.INSTANCE.cannonTexture());
		
		/*// STRAIN THE SERVER AAAAAAHHHHH 
		int idcount = 300;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				entities.put(idcount, OBJImporter.objToMesh(ClientResources.INSTANCE.castleOBJ().getText(), glContext));
				entities.get(idcount).id = idcount;
				entities.get(idcount).posX = (float)(1.5 * (i+1));
				entities.get(idcount).posY = (float)(1.5 * (j+1));
				Console.log("Castle #" + (idcount - 300) + " complete!");
				idcount++;
			}
		}*/
		

		
		for(UnitType t : UnitType.values()) {
			unitMeshes.put(t, cannon1);
		}
		unitMeshes.put(UnitType.INFANTRY, swordsman1);
		unitMeshes.put(UnitType.ARCHER, archer1);
		
		for(BuildingType t : BuildingType.values()) {
			buildingMeshes.put(t, castle1);
		}
		
	}
	
	/**
	 * Renders each entity in the map with the given shader
	 * @param shader
	 */
	public void renderEntities(Shader shader) {
		HashMap<Integer, GameObject> gameObjects = theModel.getGameModel().getGameObjects();
		if(debug) Console.log(gameObjects.size() + " objects to render");
		
		int timeSinceUpdate = theModel.timeSinceLastUpdate();
		
		for(GameObject o : gameObjects.values()) {
			Mesh m;
			if(o instanceof Unit) {
				m = unitMeshes.get(((Unit) o).getUnitType());
			} else if(o instanceof Building) {
				m = buildingMeshes.get(((Building) o).getBuildingType());
			} else {
				m = unitMeshes.get(((Unit) o).getUnitType());
			}
			//Console.log("Extrapolating position " + timeSinceUpdate + " ms forward");
			double[] pos = o.extrapolatePosition(timeSinceUpdate).toArray();
			//Console.log("Unit " + o.getId() + " at " + pos[0] + " " + pos[1]);
			m.posX = (float) pos[0] + 0.5f;
			m.posY = (float) pos[1] + 0.5f;
			float[] color = ColorFunctions.intToHSV(o.getPlayerID());
			m.setTeamColor(color[0], color[1], color[2]);
			m.id = o.getId();
			m.render(glContext, shader, camera);
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
		//int tileSelectAttrib = 0;
		//int vertexPositionAttribute = 1;
		
		// These keeps returning -1 meaning it could not find the attributes. No clue why.
		int tileSelectAttrib = glContext.getAttribLocation(shader.shaderProgram, "tileSelectColor");
		//Console.log("tileSelectAttrib = " + tileSelectAttrib);
		int vertexPositionAttribute = glContext.getAttribLocation(shader.shaderProgram, "vertexPosition");
		//Console.log("vertexPositionAttribute = " + vertexPositionAttribute);
		
		if(debug) Console.log("Rendering tile selection");
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
	}
	
	/**
	 * Renders selected entities with an outline corresponding to their
	 * remaing health
	 * @param selectedShader
	 */
	public void renderSelectedEntities(Shader selectedShader) {
		HashMap<Integer, GameObject> gameObjects = theModel.getGameModel().getGameObjects();
		if(debug) Console.log(gameObjects.size() + " objects to render");
		
		int timeSinceUpdate = theModel.timeSinceLastUpdate();
		
		for(Integer i : selectedEntities) {
			GameObject o = gameObjects.get(i);
			double[] pos = o.extrapolatePosition(timeSinceUpdate).toArray();
			selectionRing.posX = (float) pos[0] + 0.5f;
			selectionRing.posY = (float) pos[1] + 0.5f;
			selectionRing.healthPercentage = o.getHealthPercentage();
			selectionRing.id = i;
			selectionRing.render(glContext, selectedShader, camera);
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
				if (!chatFlag)
				{
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
					case KeyCodes.KEY_I: 
						theModel.sendCommand(new PlaceUnitCommand( UnitType.CANNON, 1, mouseTile));
						break;
					case KeyCodes.KEY_K:
						theModel.sendCommand(new PlaceUnitCommand( UnitType.INFANTRY, (int) (8*Math.random()), mouseTile));
						break;
					case KeyCodes.KEY_L:
						theModel.sendCommand(new PlaceUnitCommand( UnitType.ARCHER, (int) (8*Math.random()), mouseTile));
						break;
					case KeyCodes.KEY_H:
						theModel.sendCommand(new ConstructBuildingCommand( BuildingType.BARRACKS, (int) (8*Math.random()), mouseTile));
						break;
					case KeyCodes.KEY_N:
						for( int i : selectedEntities )
							theModel.sendCommand(new BuildingProductionCommand( i, UnitType.INFANTRY ));
						break;
					case KeyCodes.KEY_B:
						if (event.getNativeEvent().getShiftKey()) {
							Console.log("pressed shift-b");
							// Cycle through building types
							buildingCounter++;
							String currBuilding = buildingTypes[(buildingTypes.length + buildingCounter) % buildingTypes.length].toString();
							// Display in the menu
							$("#building-toolbar").html(currBuilding);
						} else {
							Console.log("pressed b");
							// Toggle building mode
							currMode = currMode == Mode.NONE ? Mode.BUILDING
									: Mode.NONE;
							// Show/hide building toolbar
							$("#building-toolbar").toggle();
							if (currMode == Mode.BUILDING) {
								// Set div to mouse position
								$("#building-toolbar").css("left", mouseX + "px");
								$("#building-toolbar").css("top", mouseY + "px");
								// Set current building type
								String currBuilding = buildingTypes[(buildingTypes.length + buildingCounter) % buildingTypes.length].toString();
								// Display in the menu
								$("#building-toolbar").html(currBuilding);
							}
						}
						break;
					default: if (debug) Console.log("Unrecognized: " + event.getNativeKeyCode()); break;
					}
				} else // chatFlag is set, make sure all camera flags are false
				{
					up    = false;
					down  = false; 
					right = false;
					left  = false; 
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
					if(currMode == Mode.BUILDING) {
						// Build the type that is specified
						theModel.sendCommand(new ConstructBuildingCommand( BuildingType.BARRACKS, (int) (8*Math.random()), mouseTile));
					} else if(event.isShiftKeyDown()) {
						int targetID = objectSelector.pickEntity(event.getClientX(), event.getClientY());
						if(commandDebug) Console.log("Target ID: " + targetID);
						if(targetID == 0) {
							for(Integer i : selectedEntities) {
								theModel.sendCommand(new MoveUnitCommand(i, mouseTile.x, mouseTile.y));
							}
						} else {
							if(commandDebug) Console.log("Selected units attacking unit " + targetID);
							for(Integer i : selectedEntities) {
								theModel.sendCommand(new AttackCommand(i,targetID));
							}
						}
					} else {
						if(!event.isControlKeyDown()) selectedEntities.clear();
						int selectedID = objectSelector.pickEntity(event.getClientX(), event.getClientY());
						if(commandDebug) Console.log("Selected entity with ID " + selectedID + ".");
						if (theModel.getGameModel().getGameObjects().containsKey(selectedID)) {
							if(commandDebug) Console.log("This entity exists! Adding to selected entities...");
							selectedEntities.add(selectedID);
						} else {
							if(commandDebug) Console.log("This entity DOES NOT exist!");
						}
					}
					first = new Coordinate(event.getClientX(), event.getClientY());
					curr = first;
					break;
				}
				
			}
	
		}, MouseDownEvent.getType());
		
		RootPanel.get().addDomHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				// Functionality for Building Mode
				mouseX = event.getX();
				mouseY = event.getY();
				if (currMode == Mode.BUILDING) {
					// Set building css
					// $("#building-toolbar").css("left: '" + mouseX +
					// "px'; top: '" + mouseY + "px';");
					$("#building-toolbar").css("left", mouseX + "px");
					$("#building-toolbar").css("top", mouseY + "px");
				}
				
				//Console.log("X: " + event.getClientX() + ", Y: " + event.getClientY());
				if (GameCanvas.this.onEdgeOfMap(event)) {
					GameCanvas.this.mouseVector = Vector3.getVectorBetween(GameCanvas.this.getCenterOfMap(), new Vector3(event.getClientX(), event.getClientY(), 0));
					GameCanvas.this.move = true;
				} else {
					GameCanvas.this.move = false;
				}
				mouseTile = objectSelector.pickTile(event.getClientX(), event.getClientY());
				//Console.log("(before) TILE: " + mouseTile.toString());
				// A quick check to make sure that we did not miss the map
				if(mouseTile.x >= 0.0) {
					mouseTile.x = (int)(mouseTile.x / (255.0/GRID_WIDTH) + 0.5);
					mouseTile.y = (int)(mouseTile.y / (255.0/GRID_WIDTH) + 0.5);
				}
				RootPanel.get("tile-info").getElement().setInnerHTML(mouseTile.toString());
				//Console.log("(after) TILE: " + mouseTile.toString());
				if (first != null){
					curr = new Coordinate(event.getClientX(), event.getClientY());
				}
			}
			
		}, MouseMoveEvent.getType());
		
		RootPanel.get().addDomHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				// TODO Auto-generated method stub
				int x1 = (int) Math.min(first.x, curr.x);
				int x2 = (int) (x1 + Math.abs(first.x - curr.x));
				
				int y1 = (int) Math.min(first.y, curr.y);
				int y2 = (int) (y1 + Math.abs(first.y - curr.y));
				
				Integer[] ids = objectSelector.pickEntities(x1, y1, x2, y2);
				for (int selectedID : ids){
					if(commandDebug) Console.log("Selected entity with ID " + selectedID + ".");
					if (theModel.getGameModel().getGameObjects().containsKey(selectedID)) {
						if(commandDebug) Console.log("This entity exists! Adding to selected entities...");
						selectedEntities.add(selectedID);
					} else {
						if(commandDebug) Console.log("This entity DOES NOT exist!");
					}
				}
				first = null;
			}
		
		}, MouseUpEvent.getType());

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

		camera.makeCameraMatrix();
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
		if (in && camZ >= 1.0)
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
		glContext.pixelStorei(WebGLRenderingContext.UNPACK_PREMULTIPLY_ALPHA_WEBGL, 0);
		glContext.blendFunc(WebGLRenderingContext.SRC_ALPHA, WebGLRenderingContext.ONE);

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
				.selectedVS().getText(),ClientResources.INSTANCE
				.selectedFS().getText());
		
		final Shader selectBoxShader = new Shader(glContext,ClientResources.INSTANCE
				.selectboxVS().getText(),ClientResources.INSTANCE
				.selectboxFS().getText());
		
		
		final Mesh barrel = OBJImporter.objToMesh(ClientResources.INSTANCE.barrelOBJ().getText(), glContext);
		
		final long startTime = System.currentTimeMillis();
		
		// repaint timer
		Timer t = new Timer() {
			@Override
			public void run() {
				time = System.currentTimeMillis();
				int deltaT = (int) (time - startTime);

				agentX = (float) Math.sin(deltaT / 1000.0);
				agentY = (float) Math.cos(deltaT / 1000.0);

				updateCamera();
				drawScene();
				
				barrel.posX = agentX;
				barrel.posY = agentY;
				barrel.posZ = (float) (agentZ + Math.sin(time/300));
				barrel.rotX = barrel.rotX + 0.01f;
				
				renderEntities(texturedPhongMeshShader);
				renderSelectedEntities(selectedShader);
				renderSelection(selectBoxShader);
			}
		};
		t.scheduleRepeating(33); // roughly 30 FPS
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
		
		selectVertBuffer = glContext.createBuffer();
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
	
	public void renderSelection(Shader selectShader){
		if (first == null)
			return;
		
		Console.log("Rendering Selection");
		Console.log(first + ", " + curr);
		
		
		
		float x1 = (float) ((2 * first.x - WIDTH ) / WIDTH);
		float y1 = -(float) ((2 * first.y - HEIGHT ) / HEIGHT);
		
		float x2 = (float) ((2 * curr.x - WIDTH ) / WIDTH);
		float y2 = -(float) ((2 * curr.y - HEIGHT ) / HEIGHT);
		
		Float32Array selectData = Float32Array.create(new float [] {
				x1, y1,
				x2, y1,
				x1, y2,
				
				x1, y2,
				x2, y2,
				x2, y1
		});
		
		Console.log(new Coordinate(x1, y1) + ", " + new Coordinate(x2, y2));
		
		glContext.useProgram(selectShader.shaderProgram);
		
		glContext.enable(WebGLRenderingContext.BLEND);
		glContext.blendFunc(WebGLRenderingContext.SRC_ALPHA, WebGLRenderingContext.ONE);
		
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, selectVertBuffer);
		glContext.bufferData(glContext.ARRAY_BUFFER, selectData,
				WebGLRenderingContext.DYNAMIC_DRAW);
		
		int selectVertAttrib = glContext.getAttribLocation(selectShader.shaderProgram, "vertices");
		glContext.enableVertexAttribArray(selectVertAttrib);
		glContext.vertexAttribPointer(selectVertAttrib, 2, WebGLRenderingContext.FLOAT, false, 0, 0);
		
		glContext.disable(WebGLRenderingContext.DEPTH_TEST);
		
		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 6);
		
		glContext.enable(WebGLRenderingContext.DEPTH_TEST);
		glContext.disable(WebGLRenderingContext.BLEND);
		
		glContext.flush();
	}
}
