package br.com.eds.api.eds.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/download")
public class DowloadFiles {

    private final String BASE_DIR = "src/main/resources/static";

    @GetMapping("/{tipo}/{nomeArquivo}")
    public ResponseEntity<Resource> downloadArquivo(@PathVariable String tipo, @PathVariable String nomeArquivo) {
        try {
            String diretorio = switch (tipo) {
                case "impressao_design" -> "impressao_design/";
                case "criacao_design" -> "criacao_design/";
                case "conserto" -> "conserto/";
                default -> throw new IllegalArgumentException("Tipo inválido");
            };

            // 🔍 Garante que tenha uma barra correta
            Path caminhoArquivo = Paths.get(BASE_DIR, diretorio).resolve(nomeArquivo).normalize();
            System.out.println("Tentando acessar o arquivo em: " + caminhoArquivo.toAbsolutePath());

            Resource resource = new UrlResource(caminhoArquivo.toUri());

            if (!resource.exists()) {
                System.out.println("Arquivo não encontrado.");
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
