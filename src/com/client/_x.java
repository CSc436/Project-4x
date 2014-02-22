package com.client;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.client.matrixutils.FloatMatrix;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
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
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Selector;
import com.google.gwt.query.client.Selectors;

import static com.google.gwt.query.client.GQuery.*;
import static com.google.gwt.query.client.css.CSS.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */


public class _x implements EntryPoint {
	private WebGLRenderingContext glContext;
	private WebGLProgram shaderProgram;
	private WebGLTexture texture;
	private int vertexPositionAttribute, vertexTexCoordAttrib;
	private WebGLUniformLocation texUniform, resolutionUniform, timeUniform,
			matrixUniform, camPosUniform;
	private WebGLBuffer vertexBuffer, texCoordBuffer;
	private Float32Array vertexData, texCoordData;
	private static int WIDTH, HEIGHT;
	private static long startTime;
	private float[] cameraMatrix;
	private float camX = 0.0f, camY = -20.0f, camZ = 20.0f;
	private boolean in = false, out = false, up = false, down = false,
			right = false, left = false;
	private long time;

	private final int GRID_WIDTH = 32;
	private final int NUM_TILES = GRID_WIDTH * GRID_WIDTH;

	private ArrayList<RenderTile> tiles = new ArrayList<RenderTile>();

	public void onModuleLoad() {
		final Canvas webGLCanvas = Canvas.createIfSupported();

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

		glContext.viewport(0, 0, WIDTH, HEIGHT);
		
		RootPanel.get().addDomHandler(new KeyDownHandler() {
			private long lastHit = System.currentTimeMillis();

			@Override
			public void onKeyDown(KeyDownEvent event) {
				// TODO Auto-generated method stub

				if (time - lastHit < 100)
					return;

				lastHit = time;

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
				case KeyCodes.KEY_E:
					in = true;
					break;
				case KeyCodes.KEY_Q:
					out = true;
					break;
				default:
					break;
				}
			}
		}, KeyDownEvent.getType());
		
		RootPanel.get().addDomHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				switch (event.getNativeKeyCode()) {
				case KeyCodes.KEY_UP:
				case KeyCodes.KEY_W:
					up = false;
					break;
				case KeyCodes.KEY_DOWN:
				case KeyCodes.KEY_S:
					down = false;
					break;
				case KeyCodes.KEY_LEFT:
				case KeyCodes.KEY_A:
					left = false;
					break;
				case KeyCodes.KEY_RIGHT:
				case KeyCodes.KEY_D:
					right = false;
					break;
				case KeyCodes.KEY_E:
					in = false;
					break;
				case KeyCodes.KEY_Q:
					out = false;
					break;
				default:
					break;
				}
			}
		}, KeyUpEvent.getType());

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
				makeCameraMatrix();
			}
		});

		initClickHandlers();
		makeCameraMatrix();
		start();
	}

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

	private void makeCameraMatrix() {
		// 4.71238898
		cameraMatrix = FloatMatrix.createCameraMatrix(0.0f,
				3.14159f + .785398163f, 0.0f, 45,
				(float) WIDTH / (float) HEIGHT, 0.1f, 1000000f)
				.columnWiseData();
	}

	private void updateCamera() {
		// TODO Auto-generated method stub
		float delta = camZ / 10.0f;
		if (up)
			camY += delta;
		if (down)
			camY -= delta;
		if (left)
			camX += delta;
		if (right)
			camX -= delta;
		if (in && camZ >= 2.0) {
			camZ -= 1.0f;
			camY += 1.0f;
		}
		if (out && camZ <= 25.0f) {
			camZ += 1.0f;
			camY -= 1.0f;
		}
	}

	private void start() {
		glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glContext.clearDepth(1.0f);
		glContext.enable(WebGLRenderingContext.DEPTH_TEST);
		glContext.depthFunc(WebGLRenderingContext.LEQUAL);

		initTexture();
		initShaders();

		makeTiles();
		initBuffers();

		startTime = System.currentTimeMillis();
		Timer t = new Timer() {
			@Override
			public void run() {
				time = System.currentTimeMillis();
				updateCamera();
				drawScene();
			}
		};
		t.scheduleRepeating(16);
	}

	private void initTexture() {
		texture = glContext.createTexture();

		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
		glContext.texImage2D(WebGLRenderingContext.TEXTURE_2D, 0,
				WebGLRenderingContext.RGB, WebGLRenderingContext.RGB,
				WebGLRenderingContext.UNSIGNED_BYTE, ImageElement.as(getImage(
						ClientResources.INSTANCE.terrainTextures())
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

	private void initBuffers() {
		vertexBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);

		glContext.bufferData(glContext.ARRAY_BUFFER, vertexData,
				WebGLRenderingContext.DYNAMIC_DRAW);

		texCoordBuffer = glContext.createBuffer();
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texCoordBuffer);

		glContext.bufferData(glContext.ARRAY_BUFFER, texCoordData,
				WebGLRenderingContext.DYNAMIC_DRAW);
	}

	private void makeTiles() {
		System.out.println("Generating Tiles:");

		float[][] heightmap = DiamondSquare.DSGen(GRID_WIDTH,
				System.currentTimeMillis(), 0.2f);
		LandType type;

		vertexData = Float32Array.create(NUM_TILES * 6 * 3);
		texCoordData = Float32Array.create(NUM_TILES * 6 * 2);
		
		int index = 0;
		for (int x = 0; x < GRID_WIDTH; x++)
			for (int y = 0; y < GRID_WIDTH; y++) {
				int val = (int) (255 * heightmap[x][y]);
				type = (val < 100 ? LandType.Water
						: val < 132 ? LandType.Dirt 
								: LandType.Grass);
				
				RenderTile.addTileToBuffer(x, y, 1.0f, index++, type,
						glContext, vertexData, texCoordData);
				
				if (index % 10 == 0) {
					float percent = (100 * index) / (float) NUM_TILES;
					percent = ((int) (100 * percent)) / 100.0f;
					System.out.println(percent + "%");
				}
			}
	}

	private void drawScene() {
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT
				| WebGLRenderingContext.DEPTH_BUFFER_BIT);

		glContext.useProgram(shaderProgram);

		glContext.enableVertexAttribArray(vertexPositionAttribute);
		glContext.enableVertexAttribArray(vertexTexCoordAttrib);

		// vertices
		glContext.uniformMatrix4fv(matrixUniform, false, cameraMatrix);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// texture coordinates
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texCoordBuffer);
		glContext.vertexAttribPointer(vertexTexCoordAttrib, 2,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// uniforms
		glContext.uniform3f(camPosUniform, camX, camY, camZ);

		// texture
		glContext.activeTexture(WebGLRenderingContext.TEXTURE0);
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
		glContext.uniform1i(texUniform, 0);

		// draw geometry
		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, NUM_TILES * 6);

		glContext.flush();
	}
}
