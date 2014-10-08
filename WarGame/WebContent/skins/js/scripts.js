var server = 'localhost:9090';
var http = 'http://';
var baseUrlPath = http+server+'/WarGame/rest/WGRest';
var baseUrlImg = http+server+'/WarGame/skins/img';

var character = 'character_';
var feature = 'feature_';

var pngExt = '.png';

function fuzzy(range, base){
	return (base||0) + (Math.random()-0.5)*range*2;
}
