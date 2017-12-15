app.controller("controlePrincipal", 

	function($scope, $http, todoListService) {

//		$scope.artistasCadastrados = todoListService.artistasCadastrados;
//		$scope.playlistsCadastradas = todoListService.playlistsCadastradas;
//		$scope.albunsCadastrados = todoListService.albunsCadastrados;
//		$scope.artistasFavoritados = todoListService.artistasFavoritados;
//	$scope.musicasCadastradas = todoListService.musicasCadastradas;

		$scope.albunsListados = [];
		$scope.musicasListadas = [];
		$scope.artistaExisteNoSistema = false;
		$scope.editando = false;
		$scope.artistaDaVez = {nome: "", imagem: "" , comentario: "", ehFavorito: false, albuns:[],  nota:"0", ultimaMusica:""};
		$scope.albumDaVez = {nome: "", imagem: "", ano: "", musicas:[], dono:""};
		$scope.musicaDaVez = {nome: "", albumNome:"", ano: "", duracao:""};
		$scope.playlistDaVez = {nome:"", musicas:[]};
		$scope.usuariosCadastrados = [];
		$scope.user = localStorage.getItem("userData");
		
		$http({method:'GET', url:'http://localhost:8080/usuarios'})
			.then(function(resposta){
				$scope.usuariosCadastrados = resposta.data;
				console.log("Fez corretamente o GET");
				
			}, function(resposta){
				console.log("Fez erroneamente o GET");
			});			

		
		
		$scope.fazerLogin = function(Cliente) {
			
			$http.post("http://localhost:8080/autenticar", Cliente)
			.then(function(resposta){
				console.log("Sucesso " + resposta);
				localStorage.setItem("userData", JSON.stringify(resposta.data));
				window.location.href = "http://localhost:8080/index";
				
				
			}, function(resposta){
				console.log("Falha " + resposta);
				
			});
			
		}
		
		$scope.atualizarUsuario = function(Usuario) {
			$http.put('http://localhost:8080/usuarios', JSON.stringify(Cliente)).then(
				
				function (response) {
					console.log("Deu bom o PUT");
				}, 
				
				function (response) {
					console.log("Deu ruim o PUT");
				});
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
			$scope.musicaDaVez = {nome: "", albumNome:"", ano: "", duracao:""};

		}

		$scope.resetAlbumDaVez = function() {
			$scope.albumDaVez = {nome: "", imagem: "", ano: "", musicas:[], dono:""};

		}

		$scope.addMusica = function(Musica) {
			if(Musica.nome == "" || Musica.ano == "" || Musica.duracao == "" || $scope.albumDaVez.nome == "" ) {
				Materialize.toast('Alguma informação está incorreta, tente novamente!', 2000)
			} else {
				if($scope.musicaExisteNoSistema(Musica)) {
					Materialize.toast('A música > ' + Musica.nome + ' < já existe no sistema, tente cadastrar outra!', 2000)
				} else {

				$scope.albumDaVez.musicas.push(Musica);
				Musica.albumNome = $scope.albumDaVez.nome;
				$scope.musicasCadastradas.push(Musica);
				$('#modal4').modal('close');
				Materialize.toast('A música > ' + Musica.nome +  ' < foi adicionada ao Álbum: > ' + $scope.albumDaVez.nome + ' < com sucesso!', 2000)
				$scope.resetAlbumDaVez();
				}

			}
		}

		$scope.addAlbum = function(Album) {
				if(Album.nome == "") {
					Materialize.toast('Alguma informação está incorreta, tente novamente!', 2000)
				} else {
					if($scope.albumExisteNoSistema(Album)) {
						Materialize.toast('O álbum > ' + Album.nome +  ' < já existe no sistema, tente outro!', 2000)
					} else {
						if(Album.ano == "" || !Number.isInteger(Album.ano)) {
							Album.ano = "Data não especificada ou incorreta"
						}
						
						Album.dono = $scope.artistaDaVez.nome;
						$scope.artistaDaVez.albuns.push(Album);
						$scope.albunsCadastrados.push(Album);
						$('#modal2').modal('close');
						Materialize.toast('O álbum > ' + Album.nome +  ' < foi cadastrado com sucesso!', 2000)
						$scope.resetArtistaDaVez();

					}
			}
		}


		$scope.albumExisteNoSistema = function(Album) {
			var existeAlbum = false;
			for (var i = $scope.albunsCadastrados.length - 1; i >= 0; i--) {
				if($scope.albunsCadastrados[i].nome == Album.nome) {
					existeAlbum = true;
				}
			}

			return existeAlbum;
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
					$scope.user.artistas.push(Artista);
					$('#modal1').modal('close');
  					Materialize.toast('Dados Salvos com sucesso!', 1000) // Tempo esta em ms
  					$scope.atualizarUsuario($scope.user);
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
			$scope.Album = {nome: "", imagem: "", ano: "", musicas:[], dono:""};
			$('#modal2').modal('open');
		}

		$scope.abreAdicionarMusica = function() {
			$scope.Musica = {nome: "", albumNome:"", ano: "", duracao:""};
			$('#modal4').modal('open');

		}

		$scope.favoritarArtista = function(Artista) {
				Artista.ehFavorito = true;
				Materialize.toast('O artista foi adicionado a sua lista de favoritos!', 2000) 
				$scope.artistasFavoritados.push(Artista);
		}

		$scope.desfavoritarArtista = function(Artista) {
				var click = confirm("Deseja excluir " + Artista.nome + " da sua lista de favoritos?");

				if(click == true) {
					Artista.ehFavorito = false;
					Materialize.toast('O artista foi removido da sua lista de favoritos!', 2000)
					for (var i = $scope.artistasFavoritados.length - 1; i >= 0; i--) {
				 	if(Artista.nome == $scope.artistasFavoritados[i].nome) {
				 		$scope.artistasFavoritados.splice(i, 1);
				 	}
				 } 
				}


		}

		$scope.testar = function() {
			 for(i=0; i< $scope.artistasCadastrados.length; i++) {  
	        document.write('<option value="' + $scope.artistasCadastrados[i].nome +'">' + $scope.artistasCadastrados[i].nome + '</option>');
	   		 };
		};
 
		$scope.editarArtista = function(Artista) {
			$scope.editando = true;
			angular.copy(Artista, $scope.Artista);
			$('#modal1').modal('open');
			artistaEditado = Artista;
		};

		$scope.removerArtista = function(Artista) {
			for (var i = $scope.artistasCadastrados.length - 1; i >= 0; i--) {
				if(Artista == $scope.artistasCadastrados[i]) {
					$scope.artistasCadastrados.splice(i, 1);
				};
			};

			for (var i = $scope.artistasFavoritados.length - 1; i >= 0; i--) {
				if(Artista == $scope.artistasFavoritados[i]) {
					$scope.artistasFavoritados.splice(i, 1);
				};
			}

			$scope.removeAlbunsArtista(Artista);

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
			$scope.Artista = {nome: "", imagem: "", comentario: "", albuns:[],  nota:"0", ultimaMusica:""};
			$scope.artistaExisteNoSistema = false;

		};

		var artistaJaEstaCadastrado = function(Artista) {
			var existe = false;
			
			for(i in $scope.user.artistas) {
				if($scope.user.artistas[i] == Artista) {
					existe = true;
					break;
				}
			}
			

			return existe;
		};

		init();

	});



