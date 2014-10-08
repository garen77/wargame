

	var NFIELDS = 5,MAX_PARTICLES = 500,
		PARTICLES_LENGTH = MAX_PARTICLES * NFIELDS;
	var Float32Array = window.Float32Array || Array,
		particles_i = (particles_i+NFIELDS) % PARTICLES_LENGTH;

	var MAX_AGE = 40;
	
	var requestAnimationFrame = window.requestAnimationFrame ||
	window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame ||
	window.msRequestAnimationFrame || function(f){window.setTimeout(f, 5); },
	canvas = document.getElementById('containerPlayArea'),
	ctx = canvas.getContext('2d'),
	particles = new Float32Array(PARTICLES_LENGTH),
	// position to insert the next particle
	particles_i = 0,
	// time in ms
	t0 = new Date()*1,
	// some shortcuts, they don't seem to make to code faster
	PI = Math.PI,
	random = Math.random,
	cos = Math.cos,
	sin = Math.sin,
	mouseX = 0,
	mouseY = 0;

	canvas.width = 500;
	canvas.height = 400;

	canvas.addEventListener('click', function(e) {

	    // use pageX and pageY to get the mouse position
	    // relative to the browser window
		
		var mousePos = getMouseCanvasPos(canvas, e);
		mouseX = mousePos.x;
		mouseY = mousePos.y;
		
	    // now you have local coordinates,
	    // which consider a (0,0) origin at the
	    // top-left of canvas element
	});
	
	function getMouseCanvasPos(canv,evt)
	{
		var rect = canv.getBoundingClientRect();
		return {
	          x: evt.clientX - rect.left,
	          y: evt.clientY - rect.top
	        };
	}
	
	function emit(x, y) {
		for(var i = 0; i < 250; i++) {
			particles_i = (particles_i+NFIELDS) % PARTICLES_LENGTH;
			particles[particles_i] = x;
			particles[particles_i+1] = y;
			var alpha = fuzzy(PI),
			radius = random()*1000,
			vx = cos(alpha)*radius,
			vy = sin(alpha)*radius,
			age = random();
			particles[particles_i+2] = vx;
			particles[particles_i+3] = vy;
			particles[particles_i+4] = age;
		}
	}	

	function draw(){
		var t1 = new Date()*1,
		// time delta in seconds
		td = (t1-t0)/1000,
		width = canvas.width,
		height = canvas.height,
		gravity = 50,
		drag = 0.999,
		// color
		r = 120,
		g = 55,
		b = 10;
		t0 = t1;

		emit(mouseX,mouseY);
		
		ctx.fillStyle = 'rgba(0, 0, 0, 0.4)';
		ctx.fillRect(0, 0, width, height);
		var imgdata = ctx.getImageData(0, 0, width, height),
		data = imgdata.data;
		for(var i = 0; i < PARTICLES_LENGTH; i+= NFIELDS) {
			// check age
			if((particles[i+4] += td) > MAX_AGE) continue;
			// ~~ = double bitwise inversion = Math.ceil
			var x = ~~(particles[i] = (particles[i] +
					(particles[i+2] *= drag)*td)),
					y = ~~(particles[i+1] = (particles[i+1] +
							(particles[i+3] = (particles[i+3] + gravity*td)*drag)*td));
			// check bounds
			if(x < 0 || x >= width || y < 0 || y >= height)
				continue;
			// calculate offset
			var offset = (x+y*width)*4;
			// set pixel
			data[offset] += r;
			data[offset+1] += g;
			data[offset+2] += b;
			// dont touch alpha
		}
		ctx.putImageData(imgdata, 0, 0);
		requestAnimationFrame(draw, canvas);
	}
//	requestAnimationFrame(draw, canvas);


	/*var gl = null,
	shaderProgram = null,
	fragmentShader = null,
	vertexShader = null,
	vertexPositionAttribute = null,
	vertexColorAttribute = null,
	squareVerticesBuffer = null,
	mvMatrix = mat4.create(),
	pMatrix = mat4.create();
	$(document).ready(function(){
		initWebGL();
		initShaders();
		executeProgram();
	});
	function initWebGL() {
		try {
			var canvas = $("#containerPlayArea").get(0);
			gl = WebGLUtils.setupWebGL(canvas);
			gl.viewportWidth = canvas.width;
			gl.viewportHeight = canvas.height;
		} catch(e) {
			alert(e);
		}
		if (!gl) {
			alert("Error trying to initialise WebGL");
		} else {
			gl.clearColor(0.0, 0.4, 0.0, 1.0);
		}
	}*/