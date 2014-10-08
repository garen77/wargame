var gl = null,
canvas = null,
glProgram = null,
fragmentShader = null,
vertexShader = null;
var vertexPositionAttribute = null,
trianglesVerticeBuffer = null;

$(document).ready(function(){
	canvas = document.getElementById('containerPlayArea');
	initWebGL(canvas);
	

});

function initWebGL(canvas) {
  gl = null;
  
  try {
    // Try to grab the standard context. If it fails, fallback to experimental.
    gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
    
    if(gl)
    {
    	setupWebGL();
    	initShaders();
    	setupBuffers();
    	drawScene();
    }else{
    	alert( "Error: Your browser does not appear to" +
    	"support WebGL.");
    }
  }
  catch(e) {}
  
  // If we don't have a GL context, give up now
  if (!gl) {
    alert("Unable to initialize WebGL. Your browser may not support it.");
    gl = null;
  }
  
  return ;
}

function setupWebGL()
{
//	set the clear color to a shade of green
	gl.clearColor(0.1, 0.5, 0.1, 1.0);
	gl.clear(gl.COLOR_BUFFER_BIT);
}
function initShaders(){
	
	var vs_source = null,//$('#shader-vs').html(),
		fs_source = null; //$('#shader-fs').html();

	$.ajax({
		async: false,
		url: '/WarGame/pages/scripts/playArea/shader.vs',
		success: function (data) {
		vs_source = data.firstChild.textContent;
		},
		dataType: 'xml'
		});

	$.ajax({
		async: false,
		url: '/WarGame/pages/scripts/playArea/shader.fs',
		success: function (data) {
			fs_source = data.firstChild.textContent;
		},
		dataType: 'xml'
		});

	var vertexShader = makeShader(vs_source,gl.VERTEX_SHADER); //gl.createShader(gl.VERTEX_SHADER);
	var fragmentShader = makeShader(fs_source,gl.FRAGMENT_SHADER); //gl.createShader(gl.FRAGMENT_SHADER);
	
	glProgram = gl.createProgram();
	gl.attachShader(glProgram, vertexShader);
	gl.attachShader(glProgram, fragmentShader);
	
	gl.linkProgram(glProgram);
	gl.useProgram(glProgram);
}

function makeShader(src, type)
{
//	compile the vertex shader
	var shader = gl.createShader(type);
	gl.shaderSource(shader, src);
	gl.compileShader(shader);
	if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
		alert("Error compiling shader: " + gl.getShaderInfoLog(shader));
	}
	return shader;
}

function setupBuffers(){
	var triangleVertices = [
		//left triangle
		-0.9, 0.9, 0.0,
		0.0, 0.0, 0.0,
		-0.5, -0.5, 0.0,
		//right triangle
		0.5, 0.5, 0.0,
		0.0, 0.0, 0.0,
		0.3, -0.3, 0.0
	];
	trianglesVerticeBuffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, trianglesVerticeBuffer);
	gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(triangleVertices), gl.STATIC_DRAW);
}

function drawScene(){
	vertexPositionAttribute = gl.getAttribLocation(glProgram, "aVertexPosition");
	gl.enableVertexAttribArray(vertexPositionAttribute);
	gl.bindBuffer(gl.ARRAY_BUFFER, trianglesVerticeBuffer);
	gl.vertexAttribPointer(vertexPositionAttribute, 3, gl.FLOAT, false, 0, 0);
	gl.drawArrays(gl.TRIANGLES, 0, 6);
}

