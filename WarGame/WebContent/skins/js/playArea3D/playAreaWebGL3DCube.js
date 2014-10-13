var LIBS={
		degToRad: function(angle){
			return(angle*Math.PI/180);
		},

		get_projection: function(angle, a, zMin, zMax) {
			var tan=Math.tan(LIBS.degToRad(0.5*angle)),
			A=-(zMax+zMin)/(zMax-zMin),
			B=(-2*zMax*zMin)/(zMax-zMin);

			return [
			        .5/tan, 0 ,   0, 0,
			        0, .5*a/tan,  0, 0,
			        0, 0,         A, -1,
			        0, 0,         B, 0
			        ];
		},

		get_I4: function() {
			return [1,0,0,0,
			        0,1,0,0,
			        0,0,1,0,
			        0,0,0,1];
		},

	  set_I4: function(m) {
		    m[0]=1, m[1]=0, m[2]=0, m[3]=0,
		      m[4]=0, m[5]=1, m[6]=0, m[7]=0,
		      m[8]=0, m[9]=0, m[10]=1, m[11]=0,
		      m[12]=0, m[13]=0, m[14]=0, m[15]=1;
		  },

		  rotateX: function(m, angle) {
			var c=Math.cos(angle);
			var s=Math.sin(angle);
			var mv1=m[1], mv5=m[5], mv9=m[9];
			m[1]=m[1]*c-m[2]*s;
			m[5]=m[5]*c-m[6]*s;
			m[9]=m[9]*c-m[10]*s;

			m[2]=m[2]*c+mv1*s;
			m[6]=m[6]*c+mv5*s;
			m[10]=m[10]*c+mv9*s;
		},

		rotateY: function(m, angle) {
			var c=Math.cos(angle);
			var s=Math.sin(angle);
			var mv0=m[0], mv4=m[4], mv8=m[8];
			m[0]=c*m[0]+s*m[2];
			m[4]=c*m[4]+s*m[6];
			m[8]=c*m[8]+s*m[10];

			m[2]=c*m[2]-s*mv0;
			m[6]=c*m[6]-s*mv4;
			m[10]=c*m[10]-s*mv8;
		},

		rotateZ: function(m, angle) {
			var c=Math.cos(angle);
			var s=Math.sin(angle);
			var mv0=m[0], mv4=m[4], mv8=m[8];
			m[0]=c*m[0]-s*m[1];
			m[4]=c*m[4]-s*m[5];
			m[8]=c*m[8]-s*m[9];

			m[1]=c*m[1]+s*mv0;
			m[5]=c*m[5]+s*mv4;
			m[9]=c*m[9]+s*mv8;
		},

		translateZ: function(m, t){
			m[14]+=t;
		},

		translateY: function(m, t){
			m[13]+=t;
		},

		translateX: function(m, t){
			m[12]+=t;
		}

};

var gl = null,
	canvas = null,
	glProgram = null,
	fragmentShader = null,
	vertexShader = null,
	_color = null,
	_position = null;

var vertexPositionAttribute = null,
	cubeVerticeBuffer = null,
	cubeFacesBuffers = null,
	_Pmatrix = null,
	_Vmatrix = null,
	_Mmatrix = null,
	PROJMATRIX=null,
	MOVEMATRIX=null,
	VIEWMATRIX=null;



var time_old=0,
	theta=0,
	phi=0;

var amortization = 0.95;
var drag=false;

var dX=0, dY=0, 
	trX=0,trXSignum=1,
	trY=0,trYSignum=1;

var old_x, old_y;

var mouseDown=function(e) {
	drag=true;
	old_x=e.pageX, old_y=e.pageY;
	e.preventDefault();
	return false;
};

var mouseUp=function(e){
	drag=false;
};

var mouseMove=function(e) {
	if (!drag) return false;
    dX=(e.pageX-old_x)*2*Math.PI/canvas.width,
    dY=(e.pageY-old_y)*2*Math.PI/canvas.height;
    theta+=dX;
    phi+=dY;
	old_x=e.pageX, old_y=e.pageY;
	e.preventDefault();
};

$(document).ready(function(){
	canvas = document.getElementById('containerPlayArea');
	
	canvas.addEventListener("mousedown", mouseDown, false);
	canvas.addEventListener("mouseup", mouseUp, false);
	canvas.addEventListener("mouseout", mouseUp, false);
	canvas.addEventListener("mousemove", mouseMove, false);

	PROJMATRIX=LIBS.get_projection(40, canvas.width/canvas.height, 1, 100),
	MOVEMATRIX=LIBS.get_I4(),
	VIEWMATRIX=LIBS.get_I4();
	LIBS.translateZ(VIEWMATRIX, -10);	
	initWebGL(canvas);
	animate(0);

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
	gl.clearColor(0.0, 0.0, 0.0, 0.0);
	gl.clear(gl.COLOR_BUFFER_BIT);
	
	gl.enable(gl.DEPTH_TEST);
	gl.depthFunc(gl.LEQUAL);
	gl.clearDepth(1.0);
}
function initShaders(){
	
	var vs_source = null,//$('#shader-vs').html(),
		fs_source = null; //$('#shader-fs').html();

	$.ajax({
		async: false,
		url: '/WarGame/pages/scripts/playArea3D/shader.vs',
		success: function (data) {
		vs_source = data.firstChild.textContent;
		},
		dataType: 'xml'
		});

	$.ajax({
		async: false,
		url: '/WarGame/pages/scripts/playArea3D/shader.fs',
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
	
	_Pmatrix = gl.getUniformLocation(glProgram, "Pmatrix");
	_Vmatrix = gl.getUniformLocation(glProgram, "Vmatrix");
	_Mmatrix = gl.getUniformLocation(glProgram, "Mmatrix");

	_color = gl.getAttribLocation(glProgram, "color");
	_position = gl.getAttribLocation(glProgram, "position");

	gl.enableVertexAttribArray(_color);
	gl.enableVertexAttribArray(_position);

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
	var cubeVertices = [
	                    -1,-1,-1,     1,1,0,
	                    1,-1,-1,     1,1,0,
	                    1, 1,-1,     1,1,0,
	                    -1, 1,-1,     1,1,0,

	                    -1,-1, 1,     0,0,1,
	                    1,-1, 1,     0,0,1,
	                    1, 1, 1,     0,0,1,
	                    -1, 1, 1,     0,0,1,

	                    -1,-1,-1,     0,1,1,
	                    -1, 1,-1,     0,1,1,
	                    -1, 1, 1,     0,1,1,
	                    -1,-1, 1,     0,1,1,

	                    1,-1,-1,     1,0,0,
	                    1, 1,-1,     1,0,0,
	                    1, 1, 1,     1,0,0,
	                    1,-1, 1,     1,0,0,

	                    -1,-1,-1,     1,0,1,
	                    -1,-1, 1,     1,0,1,
	                    1,-1, 1,     1,0,1,
	                    1,-1,-1,     1,0,1,

	                    -1, 1,-1,     0,1,0,
	                    -1, 1, 1,     0,1,0,
	                    1, 1, 1,     0,1,0,
	                    1, 1,-1,     0,1,0
     ];
	cubeVerticeBuffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, cubeVerticeBuffer);
	gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(cubeVertices), gl.STATIC_DRAW);
	
	//FACES :
	var cube_faces = [
	                  0,1,2,
	                  0,2,3,

	                  4,5,6,
	                  4,6,7,

	                  8,9,10,
	                  8,10,11,

	                  12,13,14,
	                  12,14,15,

	                  16,17,18,
	                  16,18,19,

	                  20,21,22,
	                  20,22,23
	];
	cubeFacesBuffers = gl.createBuffer ();
	gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, cubeFacesBuffers);
	gl.bufferData(gl.ELEMENT_ARRAY_BUFFER,new Uint16Array(cube_faces),gl.STATIC_DRAW);	
}


var animate=function(time) {

    var dt=time-time_old;
    if (!drag) {
        dX*=amortization, dY*=amortization;
        theta+=dX, phi+=dY;
    }
    if(Math.abs(trX)>5)
    {
    	trXSignum=-trX/Math.abs(trX);
    }	
    trX+=0.01*trXSignum;
    
    if(Math.abs(trY)>4)
    {
    	trYSignum=-trY/Math.abs(trY);
    }	
    trY+=0.02*trYSignum;
    
    LIBS.set_I4(MOVEMATRIX);
    LIBS.rotateY(MOVEMATRIX,theta);
    LIBS.rotateX(MOVEMATRIX,phi);
    LIBS.translateX(MOVEMATRIX,trX);
    LIBS.translateY(MOVEMATRIX,trY);
    time_old=time;

	gl.viewport(0.0, 0.0, canvas.width, canvas.height);
	gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
	gl.uniformMatrix4fv(_Pmatrix, false, PROJMATRIX);
	gl.uniformMatrix4fv(_Vmatrix, false, VIEWMATRIX);
	gl.uniformMatrix4fv(_Mmatrix, false, MOVEMATRIX);
	gl.vertexAttribPointer(_position, 3, gl.FLOAT, false,4*(3+3),0) ;
	gl.vertexAttribPointer(_color, 3, gl.FLOAT, false,4*(3+3),3*4) ;
	gl.bindBuffer(gl.ARRAY_BUFFER, cubeVerticeBuffer);
	gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, cubeFacesBuffers);
	gl.drawElements(gl.TRIANGLES, 6*2*3, gl.UNSIGNED_SHORT, 0);

	gl.flush();

    window.requestAnimationFrame(animate);
  };
  
  

