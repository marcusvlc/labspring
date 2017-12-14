var app = angular.module('sistema',['ui.router']);


// Factory que guarda as informações globalmente, nao deixando resetar ao mudar entre rotas.
app.factory('todoListService', function() {
    var clienteLogado = {};
    var clientes = [];


  return {
    clienteLogado: clienteLogado,
    clientes: clientes

  };
});





app.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/loginform');
    	
    var loginState = {
            name: 'loginform',
            url: '/loginform',
            templateUrl: 'paginas/loginform.html',
            controller: 'controlePrincipal',
      };
    	
        var homeState = {
          name: 'home',
          url: '/home',
          templateUrl: 'paginas/home.html',
          controller: 'controlePrincipal',
        };
        var playlistsState = {
          name: 'playlists',
          url: '/playlists',
          templateUrl: 'paginas/playlists.html',
          controller: 'controlePrincipal',
        };

       	var favoritosState = {
          name: 'favoritos',
          url: '/favoritos',
          templateUrl: 'paginas/favoritos.html',
          controller: 'controlePrincipal',
        };

          var musicasState = {
          name: 'musicas',
          url: '/musicas',
          templateUrl: 'paginas/musicas.html',
          controller: 'controlePrincipal',
        };

          var albunsState = {
          name: 'albuns',
          url: '/albuns',
          templateUrl: 'paginas/albuns.html',
          controller: 'controlePrincipal',
        };

        $stateProvider.state(homeState);
        $stateProvider.state(playlistsState);
        $stateProvider.state(favoritosState);
        $stateProvider.state(musicasState);
        $stateProvider.state(albunsState);
        $stateProvider.state(loginState);


    });






// app.config(function($routeProvider)
// {

//    $routeProvider
//    .when('/playlists', {
//       templateUrl : 'paginas/playlists.html',
//       controller  : 'playlistsctrl',
//    })

//    .otherwise ({ redirectTo: '/' });

// });
