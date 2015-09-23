$(document)
		.ready(
				function() {

					$('#data1')
							.click(
									function(event) {

										$
												.ajax({
													url : "${pageContext.request.contextPath}/one",
													type : "DELETE",

													success : function(response) {
														var respContent = "";
													

														$("#one")
																.html(response);
													}
												});

										event.preventDefault();
									});

				});