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
			password: 'empty'	
		},
		inline: true
	})
	.on("submit", function(e){
		e.preventDefault();
		var form = $(this);
		if(form.form("is valid")){
			$.ajax({
				url: "auth/login",
				method: "post",
				data: {
					email: form.form("get value","email"),
					password: form.form("get value","password")
				}
			}).then(function(data){
				console.log(data);
				window.location.href=data;
			}, function(jqXHR){
				console.log(jqXHR);
				form.form("add errors", [jqXHR.responseText]);
			})
			console.log("login submit");
		}
	});

	$("form[name='register']")
	.form({
		fields:{
			email: 'email',
			password: 'empty',
			title: 'empty',
			firstname: 'empty',
			lastname: 'empty',
			terms: 'checked'
		},
		inline: true,
	})
	.on("submit", function(e){
		e.preventDefault();
		
		var form = $(this);
		if(form.form("is valid")){
			console.log("register submit");
			$.ajax({
				url: "auth/register",
				method: "post",
				data: {
					email: form.form("get value","email"),
					password: form.form("get value","password"),
					gender: form.form("get value","gender"),
					title: form.form("get value","title"),
					firstname: form.form("get value","firstname"),
					lastname: form.form("get value","lastname"),
				}
			}).then(function(data){
				window.location.href=data;
			},function(jqXHR){
				form.form("add errors",[jqXHR.responseText]);
			})
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