/*PImage milit;
void setup(){
	size(600,400);
//	in Processing, you must load the images inside of the setup function
//	milit = loadImage("/WarGame/skins/img/militar.jpg");
}*/


function Vec2(x, y){
	this.x = x;
	this.y = y;
	
}

Vec2.prototype = {
		muls: function(n) { return new Vec2(this.x*n, this.y*n); },
		imuls: function(n) { this.x *= n; this.y *= n; return this; },
		mul: function(v) { return new Vec2(this.x*v.x, this.y*v.y); },
		imul: function(v) { this.x *= v.x; this.y *= v.y; return this; },
		divs: function(n) { return new Vec2(this.x/n, this.y/n); },
		div: function(v) { return new Vec2(this.x/v.x, this.y/v.y); },
		adds: function(n) { return new Vec2(this.x+n, this.y+n); },
		iadds: function(s) { this.x+=s; this.y+=s; return this; },
		add: function(v) { return new Vec2(this.x+v.x, this.y+v.y); },
		iadd: function(v) { this.x+=v.x; this.y+=v.y; return this;},
		subs: function(n) { return new Vec2(this.x-n, this.y-n); },
		isubs: function(s) { this.x-=s; this.y-=s; return this;},
		sub: function(v) { return new Vec2(this.x-v.x, this.y-v.y); },
		isub: function(v) { this.x-=v.x; this.y-=v.y; return this;},
		set: function(x, y) {this.x = x; this.y = y;}
};

function Particle(position){
	this.position = position;
	this.velocity = new Vec2(0,0);
	this.age = 0;
}

Particle.prototype = {
	maxAge : 2000,	
	update: function(td) {
		this.age += td;
		this.position.iadd(this.velocity.muls(td));
		return this.age < this.maxAge;
	}
};			
		

//The particle distance is 120 pixels from the left and 70 pixels
//from the top
/*var position = new Vec2(120, 70),
//and in one second the particle moves
//10 pixels to the right and -5 pixels down (5 pixels up).
velocity = new Vec2(10, -5),
particle = new Particle(position, velocity);

//60 times per second
window.setInterval(function(){
	var td = 1.0/60;
	particle.position.iadd(particle.velocity.muls(td));
}, 1000/60);*/


function notNaN(obj, name){
	var key = '__' + name;
	obj.__defineGetter__(name, function(){
	return this[key];
	});
	obj.__defineSetter__(name, function(v) {
	// you can also check for isFinite() in here if you'd like to
	if(typeof v !== 'number' || isNaN(v)){
	throw new TypeError(name + ' isNaN');
	}
	this[key] = v;
	});
}

function fuzzy(range, base){
	return (base||0) + (Math.random()-0.5)*range*2;
}

function choice(array) {
	return array[Math.floor(Math.random()*array.length)];
}

function force(particle, td){
}

function gravity(particle, td){
	particle.velocity.y += 50*td;
}

function renderCanvasImage(ctx, particles, fade){
	for(var i = 0; i < particles.length; i++) {
	var particle = particles[i];
	ctx.save();
	ctx.translate(particle.position.x, particle.position.y);
	ctx.drawImage(particle.image, -particle.image.width/2, -particle.image.height/2);
	ctx.restore();
	}
}

function ParticleSystem(){
	this.particles = [];
	this.forces = [];
}

ParticleSystem.prototype = {
		update: function(td) {
			var alive = [];
			for(var i = 0; i < this.particles.length; i++) {
				var particle = this.particles[i];
				for(var j = 0; j < this.forces.length; j++) {
					var force = this.forces[j];
					force(particle, td);
				}
				if(particle.update(td)){
					alive.push(particle);
				}
			}
			this.particles = alive;
		}
};


var spark = new Image();
spark.onload = main;
spark.src = '/WarGame/skins/img/spark.jpg';

function emit(system, width, height){
	var position = new Vec2(Math.random()*width, Math.random()*height);
	for(var i = 0; i < 100; i++) {
	var particle = new Particle(position);
	particle.velocity.x = fuzzy(100);
	particle.velocity.y = fuzzy(100);
	
	/*particle.velocity.x = Math.cos(alpha)*radius;
	particle.velocity.y = Math.sin(alpha)*radius;*/
	particle.image = spark;
	particle.maxAge = fuzzy(0.5, 2);
	
	particle.image = spark;
	system.particles.push(particle);
	}
}

function gravity(particle, td){
	particle.velocity.y += td*10;
}

function accelerationf(force){
	return function(particle, td){
	particle.velocity.iadd(force.muls(td));
	};
}

var gravity = accelerationf(new Vec2(0, 50));

function dampingf(damping){
	return function(particle, td){
		particle.velocity.imuls(damping);
	};
}

function main(){
	var canvas = document.getElementById('containerPlayArea'),
	ctx = canvas.getContext('2d'),
	system = new ParticleSystem();
	system.forces.push(gravity);
	system.forces.push(wind);
	system.forces.push(drag);
	
	canvas.width = 500;
	canvas.height = 400;

	emit(system, canvas.width, canvas.height);
	window.setInterval(function() {
		if(Math.random() < 0.1){
			emit(system, canvas.width, canvas.height);
		}
		system.update(1/30);
		ctx.fillStyle = "#000000";//'rgba(255, 255, 255, 0.4)';
		ctx.fillRect(0, 0, canvas.width, canvas.height);
		ctx.globalCompositeOperation = 'lighter';
		renderCanvasImage(ctx, system.particles);
		ctx.globalCompositeOperation = 'source-over';
	}, 1000/30);
}

var drag = dampingf(0.97);

function wind(particle, td){
	particle.velocity.x += td*Math.random()*50;
}

//system = new ParticleSystem();
main();

/*void draw(){
	background(0);
	main();
//displaying the images simply pass the function the variable and a location
//image(milit,mouseX,mouseY);
}*/




