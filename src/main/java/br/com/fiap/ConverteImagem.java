package br.com.fiap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import br.com.fiap.dto.TrataImagemRequest;

public class ConverteImagem {
	
	public static byte[] decode(TrataImagemRequest base64String) throws FileNotFoundException, IOException {
		try(FileOutputStream stream = new FileOutputStream(new File("output.png")))
		{
			stream.write(Base64.getDecoder().decode(base64String.getImagemBase64()));
			TrataImagem.trataImagem();
		}
		return null;		
	}
	
}
