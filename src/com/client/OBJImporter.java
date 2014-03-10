package com.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.googlecode.gwtgl.binding.WebGLRenderingContext;

public class OBJImporter {

	public static Mesh objToMesh(String objSource, WebGLRenderingContext glContext) {
		Scanner scanner = new Scanner(objSource);
		
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
		Pattern delimDefault = scanner.delimiter();
		
		String token;
		float f1, f2, f3;
		int i1, i2, i3;
		while(scanner.hasNext()) {
			token = scanner.next();
			switch(token) {
			// Found vertex: read the next three floats
			case "v":
				System.out.println("Found a vert!");
				f1 = scanner.nextFloat();
				f2 = scanner.nextFloat();
				f3 = scanner.nextFloat();
				System.out.print("VERTEX: " + f1 + ", " + f2 + ", " + f3 + "\n");
				vertList.add(f1);
				vertList.add(f2);
				vertList.add(f3);
				break;
			// Found vertex normal: read the next three floats
			case "vn":
				System.out.println("Found a vert normal!");
				f1 = scanner.nextFloat();
				f2 = scanner.nextFloat();
				f3 = scanner.nextFloat();
				System.out.print("NORMAL: " + f1 + ", " + f2 + ", " + f3 + "\n");
				normalList.add(f1);
				normalList.add(f2);
				normalList.add(f3);
				break;
			// Found vertex texture coordinate: read the next three floats
			case "vt":
				System.out.println("Found a texture coordinate!");
				f1 = scanner.nextFloat();
				f2 = scanner.nextFloat();
				f3 = scanner.nextFloat();
				System.out.print("TEXCOORD: " + f1 + ", " + f2 + ", " + f3 + "\n");
				texcoordList.add(f1);
				texcoordList.add(f2);
				texcoordList.add(f3);
				break;
			// Found a face definition: read the next three ints
			case "f":
				// Get components of first vertex
				System.out.println("Found a face!");
				i1 = scanner.useDelimiter("[/ ]").nextInt();
				i2 = scanner.useDelimiter("[/ ]").nextInt();
				i3 = scanner.useDelimiter("[/ ]").nextInt();
				System.out.print("FACE: " + i1 + ", " + i2 + ", " + i3 + "\n");
				// Get vert
				vs.add(vertList.get((i1-1)*3));
				vs.add(vertList.get(((i1-1)*3)+1));
				vs.add(vertList.get(((i1-1)*3)+2));
				System.out.println("V1: " + vertList.get((i1-1)*3) + ", " + vertList.get(((i1-1)*3)+1) + ", " + vertList.get(((i1-1)*3)+2));
				
				// Get texcoord
				ts.add(texcoordList.get((i2-1)*3));
				ts.add(texcoordList.get(((i2-1)*3)+1));
				ts.add(texcoordList.get(((i2-1)*3)+2));
				
				// Get normal
				ns.add(normalList.get((i3-1)*3));
				ns.add(normalList.get(((i3-1)*3)+1));
				ns.add(normalList.get(((i3-1)*3)+2));
				
				// Get components of second vertex
				System.out.println("Found a face!");
				i1 = scanner.useDelimiter("[/ ]").nextInt();
				i2 = scanner.useDelimiter("[/ ]").nextInt();
				i3 = scanner.useDelimiter("[/ ]").nextInt();
				System.out.print("FACE: " + i1 + ", " + i2 + ", " + i3 + "\n");
				// Get vert
				vs.add(vertList.get((i1-1)*3));
				vs.add(vertList.get(((i1-1)*3)+1));
				vs.add(vertList.get(((i1-1)*3)+2));
				System.out.println("V2: " + vertList.get((i1-1)*3) + ", " + vertList.get(((i1-1)*3)+1) + ", " + vertList.get(((i1-1)*3)+2));
				
				// Get texcoord
				ts.add(texcoordList.get((i2-1)*3));
				ts.add(texcoordList.get(((i2-1)*3)+1));
				ts.add(texcoordList.get(((i2-1)*3)+2));
				
				// Get normal
				ns.add(normalList.get((i3-1)*3));
				ns.add(normalList.get(((i3-1)*3)+1));
				ns.add(normalList.get(((i3-1)*3)+2));
				
				// Get components of third vertex
				//System.out.println("Found a face!");
				System.out.println("Found a face!");
				i1 = scanner.useDelimiter("[/ ]").nextInt();
				i2 = scanner.useDelimiter("[/ ]").nextInt();
				i3 = scanner.useDelimiter("[/ ]").nextInt();
				System.out.print("FACE: " + i1 + ", " + i2 + ", " + i3 + "\n");
				// Get vert
				vs.add(vertList.get((i1-1)*3));
				vs.add(vertList.get(((i1-1)*3)+1));
				vs.add(vertList.get(((i1-1)*3)+2));
				System.out.println("V3: " + vertList.get((i1-1)*3) + ", " + vertList.get(((i1-1)*3)+1) + ", " + vertList.get(((i1-1)*3)+2));
				
				// Get texcoord
				ts.add(texcoordList.get((i2-1)*3));
				ts.add(texcoordList.get(((i2-1)*3)+1));
				ts.add(texcoordList.get(((i2-1)*3)+2));
				
				// Get normal
				ns.add(normalList.get((i3-1)*3));
				ns.add(normalList.get(((i3-1)*3)+1));
				ns.add(normalList.get(((i3-1)*3)+2));
				
				// Reset delimiter
				scanner.useDelimiter(delimDefault);
				
				triCount++;

				break;
			default:
				System.out.println("Found something else!");
				break;
			}
		}
		
		System.out.println(triCount+" triangles found");
		float[] vArr = new float[triCount*9];
		float[] tArr = new float[triCount*9];
		float[] nArr = new float[triCount*9];
		
		for(int i = 0; i < triCount*9; i++) {
			vArr[i] = vs.get(i);
			tArr[i] = ts.get(i);
			nArr[i] = ns.get(i);
		}
		scanner.close();
		return new Mesh(vArr,tArr,nArr,triCount,glContext);
	}
}
