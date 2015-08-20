package com.theccode.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Model {
	
	public int vao;
	public int textureID;
	public int size;
	
	public int programID;
	
	private BufferedImage texture;
	private ArrayList<Integer> buffers = new ArrayList<Integer>();
	
	public Model(float[] vertices, int[] indices, float[] textureCoords, BufferedImage texture, String vertexShaderLoc, String fragmentShaderLoc) {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		storeIndices(indices);
		storeFloatInBuffer(vertices, 0, 3);
		storeFloatInBuffer(textureCoords, 1, 2);
		glBindVertexArray(0);
		
		this.texture = texture;
		textureID = TextureLoader.loadTexture(this.texture);
		
		programID = ShaderUtils.load(vertexShaderLoc, fragmentShaderLoc);
		
		size = indices.length;
	}
	
	public void delete() {
		for (int vbo : buffers)
			glDeleteBuffers(vbo);
		
		glDeleteVertexArrays(vao);
	}
	
	private void storeFloatInBuffer(float[] data, int index, int size) {
		int vbo = glGenBuffers();
		buffers.add(vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = Utilities.createFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private void storeIndices(int[] indices) {
		int vbo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer buffer = Utilities.createIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
}