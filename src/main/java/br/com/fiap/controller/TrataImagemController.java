package br.com.fiap.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.ConverteImagem;
import br.com.fiap.dto.TrataImagemRequest;
import br.com.fiap.dto.TrataImagemResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TrataImagemController {
	
	@PostMapping("/ler-recibo")
	  @ResponseBody
	  public ResponseEntity<TrataImagemResponse> teste(@RequestBody TrataImagemRequest request) {
		String imagemCodificada = "Não foi possível realizar a leitura.";
		try {
			imagemCodificada = ConverteImagem.decode(request);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  TrataImagemResponse response = new TrataImagemResponse();
		  response.setTextoRecibo(imagemCodificada);
		  return ResponseEntity.status(HttpStatus.OK).body(response);
	  }

}
