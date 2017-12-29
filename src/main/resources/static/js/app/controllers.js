app.controller("controlePrincipal", 

	function($scope, $http, todoListService) {

//		$scope.artistasCadastrados = todoListService.artistasCadastrados;
//		$scope.playlistsCadastradas = todoListService.playlistsCadastradas;
		$scope.albunsCadastrados = [];
		$scope.artistasFavoritados = [];
		$scope.musicasCadastradas = [];

		$scope.albunsListados = [];
		$scope.musicasListadas = [];
		$scope.artistaExisteNoSistema = false;
		$scope.editando = false;
		$scope.artistaDaVez = {nome: "", imagem: "" , comentario: "", ehFavorito: false, nota:"0", ultimaMusica:""};
		$scope.albumDaVez = {nome: "", imagem: "", ano: "", musicas:[], dono:""};
		$scope.musicaDaVez = {nome: "", albumNome:"", ano: "", duracao:""};
		$scope.playlistDaVez = {nome:"", musicas:[]};
		$scope.usuariosCadastrados = [];
//		var userObject = localStorage.getItem('userData');
		$scope.userDaVez = JSON.parse(localStorage.getItem('userData'));
//		$scope.userDaVez = angular.copy($scope.user);
		carregarArrays();

		
		$scope.atualizarCache = function(Usuario) {
			localStorage.setItem("userData", JSON.stringify(Usuario));
		}
		
		
		$http({method:'GET', url:'http://localhost:8080/usuarios'})
			.then(function(resposta){
				$scope.usuariosCadastrados = resposta.data;
				console.log("Fez corretamente o GET");
				
			}, function(resposta){
				console.log("Fez erroneamente o GET");
			});			

		
		
		$scope.fazerLogin = function(Usuario) {
			
			$http.post("http://localhost:8080/autenticar", Usuario)
			.then(function (resposta){
				console.log("Sucesso " + resposta);
				localStorage.setItem("userData", JSON.stringify(resposta.data));
				
				window.location.href = "http://localhost:8080/index";
				
				
			}, function(resposta){
				console.log("Falha " + resposta);
				
			});
			
		}
		
		$scope.registrar = function(Usuario) {
			
			var existe = false;
			var keys = Object.keys($scope.usuariosCadastrados);
			for (var i = 0, len = keys.length; i < len; i++) {
			  if($scope.usuariosCadastrados[keys[i]].email ==  Usuario.email) {
				  existe = true;
				  break;
			  }
			}
			
			if(existe ==  false) {
				$http.post("http://localhost:8080/usuarios", Usuario)
				.then(function (resposta){
					console.log("Registrou corretamente " + resposta);
					alert("Registrado com sucesso!");
					window.location.href = "http://localhost:8080/login";
					
					
				}, function(resposta){
					console.log("Falha " + resposta);
					
				});
			} else {
				alert("Usuario já cadastrado, tente fazer seu registro com outras informações!");
			}
		}
		
		function carregarArrays(){
			$scope.albunsCadastrados = [];
			$scope.artistasFavoritados = [];
			$scope.musicasCadastradas = [];
			
			// Carrega o array com todos os artistas favoritados
			for(i = 0; i < $scope.userDaVez.artistas.length; i++) {
				if($scope.userDaVez.artistas[i].ehFavorito == true) {
					$scope.artistasFavoritados.push($scope.userDaVez.artistas[i]);
				}
			}
			
			// Carrega o array com todos os albuns do sistema
			for(i = 0; i < $scope.userDaVez.artistas.length; i++) {
				for(j = 0; j < $scope.userDaVez.artistas[i].albuns.length; j++) {
					$scope.albunsCadastrados.push($scope.userDaVez.artistas[i].albuns[j]);
				}
			}
			
//			for(i = 0; i < $scope.userDaVez.artistas.length; i++) {
//				for(j = 0; j < $scope.userDaVez.artistas[i].albuns.length; j++) {
//					for(k = 0; k < $scope.userDaVez.artistas[i].albuns[j].musicas.length; k++) {
//						$scope.musicasCadastradas.push($scope.userDaVez.artistas[i].albuns[j].musicas[k]);
//					}
//				}
//			}
			
		}
		
		

		$scope.removerMusicaPlaylist = function(Playlist, Musica) {
			var click = confirm("Deseja excluir a música " + Musica.nome + " da playlist " + Playlist.nome + " ?");

			if(click) {
				for (var i = Playlist.musicas.length - 1; i >= 0; i--) {
					if(Playlist.musicas[i] == Musica) {
						Playlist.musicas.splice(i, 1);
						Materialize.toast('A música ' + Musica.nome + ' foi removida com sucesso!', 2000)
					}
				}
			}
		}

		$scope.addMusicaNaPlaylist = function() {
			if(musicaExisteNaPlaylist($scope.playlistDaVez, $scope.musicaDaVez)) {
				Materialize.toast('A música ' + $scope.musicaDaVez.nome + ' já existe nessa playlist, tente outra!', 2000)
			} else {
				$scope.playlistDaVez.musicas.push($scope.musicaDaVez);
				Materialize.toast('A música ' + $scope.musicaDaVez.nome + ' foi adicionada com sucesso na sua playlist!', 2000)
				$scope.resetMusicaDaVez();
				$scope.resetPlaylistDaVez();
				$('#modaladdmusicaplaylist').modal('close');
			}
		}

		var musicaExisteNaPlaylist = function(Playlist, Musica) {
			var temMusica = false;
			for (var i = Playlist.musicas.length - 1; i >= 0; i--) {
				if(Playlist.musicas[i] == Musica) {
					temMusica = true;
				}
			}

			return temMusica;
		}

		$scope.cadastrarPlayList = function(Playlist) {
			if(checaPlayListNoSistema(Playlist)) {
				Materialize.toast('A playlist ' + Playlist.nome + ' já existe no sistema, tente cadastrar outra!', 2000)
			} else {
				$scope.playlistsCadastradas.push(Playlist);
				$('#modalplaylists').modal('close');
				Materialize.toast('A playlist ' + Playlist.nome + ' foi cadastrada com sucesso! Agora é só adicionar músicas!', 2000)
			}
		}

		$scope.removerPlayList = function(Playlist) {
			var click = confirm("Deseja excluir a playlist " + Playlist.nome + "?");

			if(click) {
				for (var i = $scope.playlistsCadastradas.length - 1; i >= 0; i--) {
				if($scope.playlistsCadastradas[i] == Playlist) {
					$scope.playlistsCadastradas.splice(i, 1);
					Materialize.toast('A playlist ' + Playlist.nome + ' foi removida com sucesso!', 2000)
					}
				}

			}

		}

		var checaPlayListNoSistema = function(Playlist) {
			var existePlaylist = false;
			for (var i = $scope.playlistsCadastradas.length - 1; i >= 0; i--) {
				if($scope.playlistsCadastradas[i].nome == Playlist.nome) {
					existePlaylist = true;
				}
			}

			return existePlaylist;
		}

		$scope.abrirModalPlaylist = function() {
			$scope.Playlist = {nome: "", musicas:[]};
			$('#modalplaylists').modal('open');

		}

		$scope.abrirModalMusicaPlayList = function(Playlist) {
			$scope.resetMusicaDaVez();
			$('#modaladdmusicaplaylist').modal('open');
			$scope.playlistDaVez = Playlist;
		}

		$scope.setMusicaDaVez = function(Musica) {
			$scope.musicaDaVez = Musica;
		}

		$scope.abrirModalMusica = function(Artista) {
			$scope.musicasListadas = [];

			$scope.resetArtistaDaVez();
			$scope.resetMusicaDaVez();


			for (var i = Artista.albuns.length - 1; i >= 0; i--) {
				for (var j = Artista.albuns[i].musicas.length - 1; j >= 0; j--) {
					$scope.musicasListadas.push(Artista.albuns[i].musicas[j]);
				}
			}

			$scope.artistaDaVez = Artista;
			$('#modalmusica').modal('open');
		}

		$scope.salvarUltimaMusicaOuvida = function() {
			if($scope.musicaDaVez.nome != "") {
				$scope.artistaDaVez.ultimaMusica = $scope.musicaDaVez.nome;
				Materialize.toast('A música: > ' + $scope.musicaDaVez.nome + ' < agora é sua última música ouvida do artista ' + $scope.artistaDaVez.nome, 2000)
				$('#modalmusica').modal('close');
			} else {
				Materialize.toast('Você precisa selecionar uma música antes!', 2000)
			}
			

		}

		$scope.abreListarAlbuns = function(Artista) {
			$scope.albunsListados = Artista.albuns;
			$scope.artistaDaVez = Artista;
			$('#modal3').modal('open');

		}

		$scope.setArtistaDaVez = function(Artista) {
			$scope.artistaDaVez = Artista;
		}

		$scope.setAlbumDaVez = function(Album) {
			$scope.albumDaVez = Album;
		}

		$scope.resetArtistaDaVez = function() {
			$scope.artistaDaVez = {nome: "", imagem: "" , comentario: "", ehFavorito: false, albuns:[],  nota:"0", ultimaMusica:""};

		}

		$scope.resetPlaylistDaVez = function() {
			$scope.playlistDaVez = {nome:"", musicas:[]};
		}

		$scope.resetMusicaDaVez = function() {
			$scope.musicaDaVez = {nome: "", ano: "", duracao:""};

		}

		$scope.resetAlbumDaVez = function() {
			$scope.albumDaVez = {nome: "", imagem: "", ano: "", musicas:[]};

		}

		$scope.addMusica = function(Musica) {
			if(Musica.nome == "" || Musica.ano == "" || Musica.duracao == "") {
				Materialize.toast('Alguma informação está incorreta, tente novamente!', 2000)
			} else {
				if(false) { // IMPLEMENTAR musicaExisteNoSistema
					Materialize.toast('A música > ' + Musica.nome + ' < já existe no sistema, tente cadastrar outra!', 2000)
				} else {
					
					$scope.buscarArtistaPorAlbum($scope.albumDaVez);
					
					console.log($scope.albumDaVez.musicas);
					console.log($scope.artistaDaVez.nome);
					
					$http.post("http://localhost:8080/usuarios/" + $scope.userDaVez.id + "/artistas/" + $scope.artistaDaVez.id + "/albuns/" + $scope.albumDaVez.id + "/musicas", Musica)
					.then(function (resposta){
						console.log("Cadastrou a musica corretamente " + resposta);
						Musica.id = resposta.data.id;
		
						
					}, function(resposta){
						console.log("Falha " + resposta);
						
					});
				$scope.albumDaVez.musicas.push(Musica);
				$scope.atualizarCache($scope.userDaVez);
				$('#modal4').modal('close');
				Materialize.toast('A música > ' + Musica.nome +  ' < foi adicionada ao Álbum: > ' + $scope.albumDaVez.nome + ' < com sucesso!', 2000)
				$scope.resetAlbumDaVez();
				$scope.resetArtistaDaVez();
				}

			}
		}
		
		$scope.buscarArtistaPorAlbum = function (Album) {
			for(i = 0; i < $scope.userDaVez.artistas.length; i++) {
				for(j = 0; j < $scope.userDaVez.artistas[i].albuns; j++) {
					if($scope.userDaVez.artistas[i].albuns[j].nome == Album.nome) {
						$scope.artistaDaVez = $scope.userDaVez.artistas[i];
						break;
					}
				}
			}
			
		}

		$scope.addAlbum = function(Album) {
				if(Album.nome == "") {
					Materialize.toast('Alguma informação está incorreta, tente novamente!', 2000)
				} else {
					if($scope.albumExisteNoSistema(Album)) { // COLOCAR AQUI A VERIFICAÇAO SE O ALBUM EXISTE NO SISTEMA
						Materialize.toast('O álbum > ' + Album.nome +  ' < já existe no sistema, tente outro!', 2000)
					} else {
						
						
						$http.post("http://localhost:8080/usuarios/" + $scope.userDaVez.id + "/artistas/" + $scope.artistaDaVez.id + "/albuns", Album)
						.then(function (resposta){
							console.log("Cadastrou o album corretamente " + resposta);
							Album.id = resposta.data.id;
			
							
						}, function(resposta){
							console.log("Falha " + resposta);
							
						});
						
						$scope.artistaDaVez.albuns.push(Album);
						$scope.atualizarCache($scope.userDaVez);
						carregarArrays();
						$('#modal2').modal('close');
						Materialize.toast('O álbum > ' + Album.nome +  ' < foi cadastrado com sucesso!', 2000)
						$scope.resetArtistaDaVez();

					}
			}
		}


		$scope.albumExisteNoSistema = function(Album) {
			var existe = false;
			for(i = 0; i < $scope.userDaVez.artistas.length; i++) {
				for(j = 0; j < $scope.userDaVez.artistas[i].albuns.length; j++) {
					if(Album.nome == $scope.userDaVez.artistas[i].albuns[j].nome) {
						existe = true;
						break;
					}
				}
			}
			
			return existe;
			
		}

		$scope.musicaExisteNoSistema = function(Musica) {
			var existeMusica  = false;
			for (var i = $scope.musicasCadastradas.length - 1; i >= 0; i--) {
				if($scope.musicasCadastradas[i].nome == Musica.nome) {
					existeMusica  = true;
				}
			}

			return existeMusica
		}
		
		

		$scope.addArtista = function(Artista) {
			if(Artista.nome == "") {
				Materialize.toast('Alguma informação está incorreta, verifique e tente novamente!', 2000)
				limparFormulario();
			} else {
					if(artistaJaEstaCadastrado(Artista) == false) {
					
					$http.post("http://localhost:8080/usuarios/" + $scope.userDaVez.id + "/artistas", Artista)
					.then(function (resposta){
						console.log("Cadastrou o artista com sucesso " + resposta);
						Artista.id = resposta.data.id;
						
					}, function(resposta){
						console.log("Falha " + resposta);
					});
					
					$scope.userDaVez.artistas.push(Artista);
					$scope.atualizarCache($scope.userDaVez);
					$('#modal1').modal('close');
  					Materialize.toast('Dados Salvos com sucesso!', 1000) // Tempo esta em ms
					limparFormulario();
				} else {
					Materialize.toast('O artista já existe no sistema, tente novamente!', 2000) // Tempo esta em ms
					limparFormulario();
				};
			};
			
		};

		$scope.setNotaArtista = function() {
			var e = document.getElementById("notaselecao");
			var notaDoArtista = e.options[e.selectedIndex].value;
			$scope.artistaDaVez.nota = notaDoArtista;
			
			$http.post("http://localhost:8080/usuarios/"+ $scope.userDaVez.id + "/artistas", $scope.artistaDaVez)
			.then(function (resposta){
				console.log("Alterou com sucesso " + resposta);

											
				
			}, function(resposta){
				console.log("Falha " + resposta);
				
			});
			
			
			$scope.atualizarCache($scope.userDaVez);
			
			
			Materialize.toast('O artista ' + $scope.artistaDaVez.nome + ' foi avaliado com nota ' + notaDoArtista, 2000)
		}

		var init = function() {
			limparFormulario();
		}

		var artistaEditado;

		$scope.abreAdicionarArtista = function() {
			$scope.editando = false;
			limparFormulario();
			$('#modal1').modal('open');

		};

		$scope.abreAdicionarAlbum = function() {
			$scope.Album = {nome: "", imagem: "", ano: "", artista:null, musicas:[]};
			$('#modal2').modal('open');
		}

		$scope.abreAdicionarMusica = function() {
			$scope.Musica = {nome: "", albumNome:"", ano: "", duracao:""};
			$('#modal4').modal('open');

		}

		$scope.favoritarArtista = function(Artista) {
				Artista.ehFavorito = true;
				$http.post("http://localhost:8080/usuarios/"+ $scope.userDaVez.id + "/artistas", Artista)
				.then(function (resposta){
					console.log("Alterou com sucesso " + resposta);			
					
				}, function(resposta){
					console.log("Falha " + resposta);
					
				});
				
				$scope.atualizarCache($scope.userDaVez);
//				$scope.artistasFavoritados.push(Artista);
		}

		$scope.desfavoritarArtista = function(Artista) {
				var click = confirm("Deseja excluir " + Artista.nome + " da sua lista de favoritos?");

				if(click == true) {
					Artista.ehFavorito = false;
					Materialize.toast('O artista foi removido da sua lista de favoritos!', 2000)
					$http.post("http://localhost:8080/usuarios/"+ $scope.userDaVez.id + "/artistas", Artista)
					.then(function (resposta){
						console.log("Alterou com sucesso " + resposta);
						
						
					}, function(resposta){
						console.log("Falha " + resposta);
						
					});
				}
				
				$scope.atualizarCache($scope.userDaVez);

		}

//		$scope.testar = function() {
//			 for(i=0; i< $scope.artistasCadastrados.length; i++) {  
//	        document.write('<option value="' + $scope.artistasCadastrados[i].nome +'">' + $scope.artistasCadastrados[i].nome + '</option>');
//	   		 };
//		};
 
		$scope.editarArtista = function(Artista) {
			$scope.editando = true;
			angular.copy(Artista, $scope.Artista);
			$('#modal1').modal('open');
			artistaEditado = Artista;
		};

		$scope.removerArtista = function(Artista) {
			
			
			$http.delete("http://localhost:8080/usuarios/" + $scope.userDaVez.id + "/artistas/" + Artista.id)
			.then(function (resposta){
				console.log("Removeu o artista com sucesso " + resposta);
				
			}, function(resposta){
				console.log("Falha " + resposta);
			});
			
			var keys = Object.keys($scope.userDaVez.artistas);
			for (var i = 0, len = keys.length; i < len; i++) {
			  if($scope.userDaVez.artistas[keys[i]].nome ==  Artista.nome) {
				  $scope.userDaVez.artistas.splice(i, 1);
			  }
			}
			
			
			$scope.atualizarCache($scope.userDaVez);
			
			

		};

		$scope.removeAlbunsArtista = function(Artista) {
			for (var i = $scope.albunsCadastrados.length - 1; i >= 0; i--) {
				if($scope.albunsCadastrados[i].dono == Artista.nome) {
					$scope.albunsCadastrados.splice(i, 1);
				}
			}
		}

		$scope.salvarArtista = function(Artista) {
			if(Artista.nome == "") {
				Materialize.toast('Alguma informação está incorreta, verifique e tente novamente!', 2000)
			} else {
					if(artistaJaEstaCadastrado(Artista) == false || Artista.nome == artistaEditado.nome) {
						artistaEditado.nome = Artista.nome;
						artistaEditado.imagem = Artista.imagem;
						artistaEditado.comentario = Artista.comentario;
						
						
						$http.post("http://localhost:8080/usuarios/"+ $scope.userDaVez.id + "/artistas", Artista)
						.then(function (resposta){
							console.log("Alterou com sucesso " + resposta);
		
														
							
						}, function(resposta){
							console.log("Falha " + resposta);
							
						});
						
						$scope.atualizarCache($scope.userDaVez);
						
						$('#modal1').modal('close');
  						Materialize.toast('Dados Salvos com sucesso!', 1000) // Tempo esta em ms
						limparFormulario();
				} else {
					$('#modal1').modal('close');
					Materialize.toast('O artista já existe no sistema, sua edição falhou, tente novamente!', 3000) // Tempo esta em ms
					limparFormulario();
				};
			};

		};

		var limparFormulario = function() {
			$scope.Artista = {id: 0 ,nome:"",imagem:"",nota:"0",comentario:"",ehFavorito:false,ultimaMusicaOuvida:"", albuns:[]};
			$scope.artistaExisteNoSistema = false;

		};

		var artistaJaEstaCadastrado = function(Artista) {
			var existe = false;
			
			var keys = Object.keys($scope.userDaVez.artistas);
			for (var i = 0, len = keys.length; i < len; i++) {
			  if($scope.userDaVez.artistas[keys[i]].nome ==  Artista.nome) {
				  existe = true;
				  break;
			  }
			}
			

			return existe;
		};

		init();

	});



