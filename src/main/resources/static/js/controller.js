appLogin.controller("controllerLogin", function($scope, $http) {
	$scope.clientes = [];
	$scope.clienteLogado = {};
		
		$http({method:'GET', url:'http://localhost:8080/clientes'})
		.then(function(resposta){
			$scope.clientes = resposta.data;
			
		}, function(resposta){

		});
		
	$scope.fazerLogin = function(usuario, senha) {
		for (i = 0; i < $scope.clientes.length; i++) { 
		    if(usuario == $scope.clientes[i].login) {
		    	if(senha == $scope.clientes[i].senha) {
		    		$scope.clienteLogado = $scope.clientes[i];
		    		break;
		    	}
		    }
		}
	}
	
});