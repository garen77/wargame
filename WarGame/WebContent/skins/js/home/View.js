var UserView = Backbone.View.extend({
	el : $('#container'),
	initialize: function() {
		_.bindAll(this, 'render'); 
		this.render();
	},

	render: function(){
		var features = this.model.attributes.idFeatures;
		var featuresImg = [];
		if(features && features.length >0)
		{
			for(var i = 0; i < features.length; i++)
			{
				featuresImg[i] = baseUrlImg+'/feature/'+feature+features[i]+pngExt;
			}	
		}	
		this.model.attributes.featuresImg = featuresImg;
		
		
		var charact = this.model.attributes.idCharacter;
		var characterImg= '';
		if(charact)
		{
			characterImg = baseUrlImg+'/character/'+character+charact+pngExt;
		}	

		this.model.attributes.featuresImg = featuresImg;
		this.model.attributes.characterImg = characterImg;
		
		var templ = _.template($('#userTemplate').html());		
		
        this.$el.html(templ(this.model.toJSON()));
        
        
	}
});



