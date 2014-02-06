package com.client;

import com.shared.FieldVerifier;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class _x implements EntryPoint {

	private WebGLRenderingContext glContext;
	private WebGLProgram shaderProgram;
	private int vertexPositionAttribute;
	private WebGLBuffer vertexBuffer;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Canvas webGLCanvas = Canvas.createIfSupported();

		RootPanel.get("gwtGL").add(webGLCanvas);
		//System.out.println(webGLCanvas.getParent().getOffsetHeight());
		//System.out.println(webGLCanvas.getParent().getOffsetWidth());
		webGLCanvas.setCoordinateSpaceHeight(webGLCanvas.getParent().getOffsetHeight());
		webGLCanvas.setCoordinateSpaceWidth(webGLCanvas.getParent().getOffsetWidth());
		glContext = (WebGLRenderingContext) webGLCanvas
				.getContext("experimental-webgl");
		if (glContext == null) {
			Window.alert("Sorry, your browser doesn't support WebGL!");
		}
		glContext.viewport(0, 0, webGLCanvas.getParent().getOffsetHeight(), webGLCanvas.getParent().getOffsetWidth());
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent e) {
				webGLCanvas.setCoordinateSpaceHeight(webGLCanvas.getParent().getOffsetHeight());
				webGLCanvas.setCoordinateSpaceWidth(webGLCanvas.getParent().getOffsetWidth());
				glContext.viewport(0, 0, webGLCanvas.getParent().getOffsetHeight(), webGLCanvas.getParent().getOffsetWidth());
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

		drawScene();
	}

	public void initShaders() {
		WebGLShader fragmentShader = getShader(
				WebGLRenderingContext.FRAGMENT_SHADER, Shaders.INSTANCE
						.fragmentShader().getText());
		WebGLShader vertexShader = getShader(
				WebGLRenderingContext.VERTEX_SHADER, Shaders.INSTANCE
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
		glContext.enableVertexAttribArray(vertexPositionAttribute);
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
		float[] vertices = new float[] { 0.0f, 1.0f, -5.0f, // first vertex
				-1.0f, -1.0f, -5.0f, // second vertex
				1.0f, -1.0f, -5.0f // third vertex
		};
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(vertices),
				WebGLRenderingContext.STATIC_DRAW);
	}

	private void drawScene() {
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT
				| WebGLRenderingContext.DEPTH_BUFFER_BIT);
		float[] perspectiveMatrix = createPerspectiveMatrix(45, 1, 0.1f, 1000);
		WebGLUniformLocation uniformLocation = glContext.getUniformLocation(
				shaderProgram, "perspectiveMatrix");
		glContext.uniformMatrix4fv(uniformLocation, false, perspectiveMatrix);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);
		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 3);
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

		return new float[] { X, 0.0f, A, 0.0f, 0.0f, Y, B, 0.0f, 0.0f, 0.0f, C,
				-1.0f, 0.0f, 0.0f, D, 0.0f };
	};
}
