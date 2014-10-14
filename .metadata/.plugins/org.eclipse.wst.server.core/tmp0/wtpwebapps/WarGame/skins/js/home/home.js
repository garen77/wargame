$(document).ready(function(){


	$('#homeTitle').on('mouseout',function(){
        $('#homeTitle').css({
        	"font-weight" : "normal",
            "color": "black"
        });
	});

	$('#homeTitle').on('mouseover',function(){
        $('#homeTitle').css({
        	"font-weight" : "bold",
            "color": "red"
        });
	});

	var user = new UserModel();
	var idSessionUser = $('#pageForm\\:idCurrentUser').val();
	if(idSessionUser)
	{
		user.set({idUser: idSessionUser});
		user.fetch({
				success : function(userResp)
				{
					loadUserView(userResp);
				}
		});					
	}	

});

function loadUserView(userMod)
{
	var userView = new UserView({model: userMod});
	userView.render();
}
