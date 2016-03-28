$(document).ready(function(){
	//init dropdown
	$(".ui.dropdown").dropdown();
	
	//init tab in login/register modal
	$("#modalLoginReg .menu .item").tab();
	
	//init form validation
	$("form[name='login']")
	.form({
		fields:{
			email: 'email',
			password: ['empty', 'minLength[8]']	
		},
		inline: true
	})
	.on("submit", function(e){
		e.preventDefault();
		if($(this).form("is valid")){
			console.log("login submit");
		}
	});

	$("form[name='register']")
	.form({
		fields:{
			email: 'email',
			password: ['empty','minLength[8]'],
			title: 'empty',
			firstname: 'empty',
			lastname: 'empty',
			terms: 'checked'
		},
		inline: true,
	})
	.on("submit", function(e){
		e.preventDefault();
		if($(this).form("is valid")){
			console.log("register submit");
			$(this).form("add errors",[
			   "This email is already registered."
			])
		}
	});
	
	//click login button to open modal
	$("#btnLogin").on("click", function(){
		$("#modalLoginReg")
		.modal({
			observeChanges: true,
			onHidden: function(){
				$("form[name='login']").form("reset");
				$("form[name='register']").form("reset");
			}
		})
		.modal("show");
	});
})