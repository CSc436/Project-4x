package com.client;

import com.shared.FieldVerifier;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLTexture;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class _x implements EntryPoint {
	private WebGLRenderingContext glContext;
	private WebGLProgram shaderProgram;
	private WebGLTexture texture;
	private int vertexPositionAttribute, vertexTexCoordAttrib;
	private WebGLBuffer vertexBuffer, texCoordBuffer;
	private static int WIDTH, HEIGHT;
	private static long startTime;

	public void onModuleLoad() {
		final Canvas webGLCanvas = Canvas.createIfSupported();

		RootPanel.get("gwtGL").add(webGLCanvas);
		
		glContext = (WebGLRenderingContext) webGLCanvas
				.getContext("experimental-webgl");
		if (glContext == null) {
			Window.alert("Sorry, your browser doesn't support WebGL!");
		}
		
		//These lines make the viewport fullscreen
		webGLCanvas.setCoordinateSpaceHeight(webGLCanvas.getParent()
				.getOffsetHeight());
		webGLCanvas.setCoordinateSpaceWidth(webGLCanvas.getParent()
				.getOffsetWidth());
		HEIGHT = webGLCanvas.getParent().getOffsetHeight();
		WIDTH = webGLCanvas.getParent().getOffsetWidth();
		
		glContext.viewport(0, 0, WIDTH, HEIGHT);
		
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

			}
		});
		start();
	}

	private void start() {
		initShaders();
		glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glContext.clearDepth(1.0f);
		glContext.enable(WebGLRenderingContext.DEPTH_TEST);
		glContext.depthFunc(WebGLRenderingContext.LEQUAL);
		initBuffers();
		//initTexture();
		startTime = System.currentTimeMillis();
	    Timer t = new Timer() {
	        @Override
	        public void run() {
	          drawScene();
	        }
	      };
	    t.scheduleRepeating(16);
	}

	private void initTexture() {
		texture = glContext.createTexture();
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
		glContext.texImage2D(
				WebGLRenderingContext.TEXTURE_2D,
				0,
				WebGLRenderingContext.RGB,
				WebGLRenderingContext.RGB,
				WebGLRenderingContext.UNSIGNED_BYTE,
				ImageElement.as(getImage(
						ClientResources.INSTANCE.riverTexture()).getElement()));
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_MAG_FILTER,
				WebGLRenderingContext.LINEAR);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D,
				WebGLRenderingContext.TEXTURE_MIN_FILTER,
				WebGLRenderingContext.LINEAR);
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, null);
		// System.out.println(texture);
	}

	public Image getImage(final ImageResource imageResource) {
		// System.out.println("(" + imageResource.getWidth() + ", " +
		// imageResource.getHeight() +")");
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
		// System.out.println(img);
		return img;
	}

	public void initShaders() {
		WebGLShader fragmentShader = getShader(
				WebGLRenderingContext.FRAGMENT_SHADER, ClientResources.INSTANCE
						.fragmentShader().getText());
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

		glContext.useProgram(shaderProgram);

		vertexPositionAttribute = glContext.getAttribLocation(shaderProgram,
				"vertexPosition");
		vertexTexCoordAttrib = glContext.getAttribLocation(shaderProgram,
				"vertexTexCoord");

		glContext.enableVertexAttribArray(vertexPositionAttribute);
		glContext.enableVertexAttribArray(vertexTexCoordAttrib);
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
		float[] vertices = new float[] { 1.0f, 1.0f, -2.5f, // first vertex
				-1.0f, 1.0f, -2.5f, // second vertex
				1.0f, -1.0f, -2.5f, // third vertex
				-1.0f, -1.0f, -2.5f };
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(vertices),
				WebGLRenderingContext.STATIC_DRAW);

		texCoordBuffer = glContext.createBuffer();
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texCoordBuffer);
		float[] colors = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				0.0f, 1.0f };
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(colors), WebGLRenderingContext.STATIC_DRAW);
	}

	private void drawScene() {
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT
				| WebGLRenderingContext.DEPTH_BUFFER_BIT);

		// create perspective matrix
		float[] perspectiveMatrix = createPerspectiveMatrix(45, 1, 0.1f, 1000);
		WebGLUniformLocation uniformLocation = glContext.getUniformLocation(
				shaderProgram, "perspectiveMatrix");

		// vertices
		glContext.uniformMatrix4fv(uniformLocation, false, perspectiveMatrix);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// texture coordinates
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texCoordBuffer);
		glContext.vertexAttribPointer(vertexTexCoordAttrib, 2,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// texture data
		//glContext.activeTexture(glContext.TEXTURE0);
		//glContext.bindTexture(glContext.TEXTURE_2D, texture);
/*		glContext.uniform1i(
				glContext.getUniformLocation(shaderProgram, "texture"), 0);*/
		glContext.uniform2f(
				glContext.getUniformLocation(shaderProgram, "resolution"), (float)WIDTH, (float)HEIGHT);
		glContext.uniform1f(
				glContext.getUniformLocation(shaderProgram, "time"), (System.currentTimeMillis() - startTime) / 1000.0f);

		// draw geometry
		glContext.drawArrays(WebGLRenderingContext.TRIANGLE_STRIP, 0, 4);
		glContext.flush();
	}

	private float[] createPerspectiveMatrix(int fieldOfViewVertical,
			float aspectRatio, float minimumClearance, float maximumClearance) {
		float top = minimumClearance
				* (float) Math.tan(fieldOfViewVertical * Math.PI / 360.0);
		float bottom = -top;
		float left = bottom * aspectRatio;
		float right = top * aspectRatio;

		float X = 2 * minimumClearance / (right - left);
		float Y = 2 * minimumClearance / (top - bottom);
		float A = (right + left) / (right - left);
		float B = (top + bottom) / (top - bottom);
		float C = -(maximumClearance + minimumClearance)
				/ (maximumClearance - minimumClearance);
		float D = -2 * maximumClearance * minimumClearance
				/ (maximumClearance - minimumClearance);

		return new float[] {	X, 		0.0f, 	A, 	0.0f, 
								0.0f, 	Y, 		B, 	0.0f, 
								0.0f, 	0.0f, 	C, 	-1.0f, 
								0.0f, 	0.0f, 	D, 	0.0f };
	}
}
