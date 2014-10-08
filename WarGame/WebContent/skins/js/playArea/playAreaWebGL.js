var gl = null,
shaderProgram = null,
fragmentShaderSource = "\n\
	precision mediump float;\n\
	\n\
	\n\
	\n\
	varying vec3 vColor;\n\
	void main(void) {\n\
	gl_FragColor = vec4(vColor, 1.);\n\
	}",
vertexShaderSource = "\n\
	attribute vec2 position; //the position of the point\n\
	attribute vec3 color;  //the color of the point\n\
	\n\
	varying vec3 vColor;\n\
	void main(void) { //pre-built function\n\
	gl_Position = vec4(position, 0., 1.); //0. is the z, and 1 is w\n\
	vColor=color;\n\
	}",
vertexPositionAttribute = null,
vertexColorAttribute = null,
squareVerticesBuffer = null,
mvMatrix = mat4.create(),
pMatrix = mat4.create(),
canvas = null,
_color = null,
_position = null,
TRIANGLE_VERTEX = null,
triangle_faces = null,
TRIANGLE_FACES = null,
SHADER_PROGRAM = null,
shaderFragment = null,
shaderVertex = null;

var triangle_vertex=[
                     -1,-1, //first summit -> bottom left of the viewport
                     0,0,1,
                     1,-1, //bottom right of the viewport
                     1,1,0,
                     1,1,  //top right of the viewport
                     1,0,0
                   ];

$(document).ready(function(){
	canvas = document.getElementById('containerPlayArea');
	gl = initWebGL(canvas);
	
	  shaderVertex=get_shader(vertexShaderSource, gl.VERTEX_SHADER, "VERTEX");

	  shaderFragment=get_shader(fragmentShaderSource, gl.FRAGMENT_SHADER, "FRAGMENT");
	  
	  SHADER_PROGRAM=gl.createProgram();
	  gl.attachShader(SHADER_PROGRAM, shaderVertex);
	  gl.attachShader(SHADER_PROGRAM, shaderFragment);
	  
	  gl.linkProgram(SHADER_PROGRAM);

	  _color = gl.getAttribLocation(SHADER_PROGRAM, "color");
	  _position = gl.getAttribLocation(SHADER_PROGRAM, "position");

	  gl.enableVertexAttribArray(_color);
	  gl.enableVertexAttribArray(_position);

	  gl.useProgram(SHADER_PROGRAM);
	  

	  /*========================= THE TRIANGLE ========================= */
	  //POINTS :

	TRIANGLE_VERTEX= gl.createBuffer ();
	gl.bindBuffer(gl.ARRAY_BUFFER, TRIANGLE_VERTEX);
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(triangle_vertex),gl.STATIC_DRAW);
	    
	//FACES :
	triangle_faces = [0,1,2];
	TRIANGLE_FACES= gl.createBuffer ();
	gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, TRIANGLE_FACES);
	gl.bufferData(gl.ELEMENT_ARRAY_BUFFER,new Uint16Array(triangle_faces), gl.STATIC_DRAW);

	/*========================= DRAWING ========================= */
	gl.clearColor(0.0, 0.0, 0.0, 0.0);
	
	animate();
});

function initWebGL(canvas) {
  gl = null;
  
  try {
    // Try to grab the standard context. If it fails, fallback to experimental.
    gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
  }
  catch(e) {}
  
  // If we don't have a GL context, give up now
  if (!gl) {
    alert("Unable to initialize WebGL. Your browser may not support it.");
    gl = null;
  }
  
  return gl;
}

function get_shader(source, type, typeString) {
    var shader = gl.createShader(type);
    gl.shaderSource(shader, source);
    gl.compileShader(shader);
    if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
      alert("ERROR IN "+typeString+ " SHADER : " + gl.getShaderInfoLog(shader));
      return false;
    }
    return shader;
  };




function animate() {

	gl.viewport(0.0, 0.0, canvas.width, canvas.height);
	gl.clear(gl.COLOR_BUFFER_BIT);
	
	// animation core
	gl.vertexAttribPointer(_position, 2, gl.FLOAT, false,4*(2+3),0) ;
	gl.vertexAttribPointer(_color, 3, gl.FLOAT, false,4*(2+3),2*4) ;
	gl.bindBuffer(gl.ARRAY_BUFFER, TRIANGLE_VERTEX);

	gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, TRIANGLE_FACES);
	gl.drawElements(gl.TRIANGLES, 3, gl.UNSIGNED_SHORT, 0);
    // animation core end
    
	gl.flush();

  window.requestAnimationFrame(animate);
}

