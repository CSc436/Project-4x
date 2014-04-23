package com.client.rendering;

import java.util.ArrayList;

import com.googlecode.gwtgl.binding.WebGLRenderingContext;

public class OBJImporter {

	public static Mesh objToMesh(String objSource,
			WebGLRenderingContext glContext) {

		String[] tokens = objSource.split("\\s+");

		// Verticies: 3 components
		ArrayList<Float> vertList = new ArrayList<Float>();

		// Normals: 3 components
		ArrayList<Float> normalList = new ArrayList<Float>();

		// UV Coordinates: 3 components
		ArrayList<Float> texcoordList = new ArrayList<Float>();

		// Final lists
		ArrayList<Float> vs = new ArrayList<Float>();
		ArrayList<Float> ts = new ArrayList<Float>();
		ArrayList<Float> ns = new ArrayList<Float>();

		int triCount = 0;
		// Pattern delimDefault = scanner.delimiter();

		String token;
		float f1, f2, f3;
		int i1, i2, i3;
		for (int i = 0; i < tokens.length; i++) {
			token = tokens[i];
			switch (token) {
			// Found vertex: read the next three floats
			case "v":
				// Console.log("Found a vert!");
				f1 = Float.parseFloat(tokens[++i]); // scanner.nextFloat();
				f2 = Float.parseFloat(tokens[++i]);
				f3 = Float.parseFloat(tokens[++i]);
				// Console.log("VERTEX: " + f1 + ", " + f2 + ", " + f3 + "\n");
				vertList.add(f1);
				vertList.add(f2);
				vertList.add(f3);
				break;
			// Found vertex normal: read the next three floats
			case "vn":
				// Console.log("Found a vert normal!");
				f1 = Float.parseFloat(tokens[++i]);
				f2 = Float.parseFloat(tokens[++i]);
				f3 = Float.parseFloat(tokens[++i]);
				// Console.log("NORMAL: " + f1 + ", " + f2 + ", " + f3 + "\n");
				normalList.add(f1);
				normalList.add(f2);
				normalList.add(f3);
				break;
			// Found vertex texture coordinate: read the next three floats
			case "vt":
				// Console.log("Found a texture coordinate!");
				f1 = Float.parseFloat(tokens[++i]);
				f2 = Float.parseFloat(tokens[++i]);
				f3 = Float.parseFloat(tokens[++i]);
				// Console.log("TEXCOORD: " + f1 + ", " + f2 + ", " + f3 +
				// "\n");
				texcoordList.add(f1);
				texcoordList.add(f2);
				texcoordList.add(f3);
				break;
			// Found a face definition: read the next three ints
			case "f":
				// Get components of first vertex
				// Console.log("Found a face!");
				String[] split = tokens[++i].split("/");
				i1 = Integer.parseInt(split[0]);
				i2 = Integer.parseInt(split[1]);
				i3 = Integer.parseInt(split[2]);
				// Console.log("FACE: " + i1 + ", " + i2 + ", " + i3 + "\n");
				// Get vert
				vs.add(vertList.get((i1 - 1) * 3));
				vs.add(vertList.get(((i1 - 1) * 3) + 1));
				vs.add(vertList.get(((i1 - 1) * 3) + 2));
				// Console.log("V1: " + vertList.get((i1-1)*3) + ", " +
				// vertList.get(((i1-1)*3)+1) + ", " +
				// vertList.get(((i1-1)*3)+2));

				// Get texcoord
				ts.add(texcoordList.get((i2 - 1) * 3));
				ts.add(texcoordList.get(((i2 - 1) * 3) + 1));
				ts.add(texcoordList.get(((i2 - 1) * 3) + 2));

				// Get normal
				ns.add(normalList.get((i3 - 1) * 3));
				ns.add(normalList.get(((i3 - 1) * 3) + 1));
				ns.add(normalList.get(((i3 - 1) * 3) + 2));

				// Get components of second vertex
				// Console.log("Found a face!");
				split = tokens[++i].split("/");
				i1 = Integer.parseInt(split[0]);
				i2 = Integer.parseInt(split[1]);
				i3 = Integer.parseInt(split[2]);
				// Console.log("FACE: " + i1 + ", " + i2 + ", " + i3 + "\n");
				// Get vert
				vs.add(vertList.get((i1 - 1) * 3));
				vs.add(vertList.get(((i1 - 1) * 3) + 1));
				vs.add(vertList.get(((i1 - 1) * 3) + 2));
				// Console.log("V2: " + vertList.get((i1-1)*3) + ", " +
				// vertList.get(((i1-1)*3)+1) + ", " +
				// vertList.get(((i1-1)*3)+2));

				// Get texcoord
				ts.add(texcoordList.get((i2 - 1) * 3));
				ts.add(texcoordList.get(((i2 - 1) * 3) + 1));
				ts.add(texcoordList.get(((i2 - 1) * 3) + 2));

				// Get normal
				ns.add(normalList.get((i3 - 1) * 3));
				ns.add(normalList.get(((i3 - 1) * 3) + 1));
				ns.add(normalList.get(((i3 - 1) * 3) + 2));

				// Get components of third vertex
				// //Console.log("Found a face!");
				// Console.log("Found a face!");

				// Console.log("Found a face!");

				split = tokens[++i].split("/");
				i1 = Integer.parseInt(split[0]);
				i2 = Integer.parseInt(split[1]);
				i3 = Integer.parseInt(split[2]);
				// Console.log("FACE: " + i1 + ", " + i2 + ", " + i3 + "\n");
				// Get vert
				vs.add(vertList.get((i1 - 1) * 3));
				vs.add(vertList.get(((i1 - 1) * 3) + 1));
				vs.add(vertList.get(((i1 - 1) * 3) + 2));
				// Console.log("V3: " + vertList.get((i1-1)*3) + ", " +
				// vertList.get(((i1-1)*3)+1) + ", " +
				// vertList.get(((i1-1)*3)+2));

				// Get texcoord
				ts.add(texcoordList.get((i2 - 1) * 3));
				ts.add(texcoordList.get(((i2 - 1) * 3) + 1));
				ts.add(texcoordList.get(((i2 - 1) * 3) + 2));

				// Get normal
				ns.add(normalList.get((i3 - 1) * 3));
				ns.add(normalList.get(((i3 - 1) * 3) + 1));
				ns.add(normalList.get(((i3 - 1) * 3) + 2));

				triCount++;

				break;
			default:
				// Console.log("Found something else!");
				break;
			}
		}

		// Console.log(triCount+" triangles found");
		float[] vArr = new float[triCount * 9];
		float[] tArr = new float[triCount * 9];
		float[] nArr = new float[triCount * 9];

		for (int i = 0; i < triCount * 9; i++) {
			vArr[i] = vs.get(i);
			tArr[i] = ts.get(i);
			nArr[i] = ns.get(i);
		}
		return new Mesh(vArr, tArr, nArr, triCount, glContext);
	}
}
