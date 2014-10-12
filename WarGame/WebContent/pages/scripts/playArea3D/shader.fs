<script id="shader-fs" type="x-shader/x-fragment">
	precision mediump float;
	varying vec3 vColor;
	void main(void) {
		gl_FragColor = vec4(vColor, 1.);
	}
</script>
