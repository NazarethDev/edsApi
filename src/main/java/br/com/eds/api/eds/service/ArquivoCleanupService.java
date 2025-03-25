package br.com.eds.api.eds.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class ArquivoCleanupService {
    private String [] DIRETORIOS = {
            "src/main/resources/static/impressao_design/",
            "src/main/resources/static/criacao_design/",
            "src/main/resources/static/conserto/"
    };

    private static final int DIAS_EXPIRACAO = 15;

    @Scheduled(cron = "0 0 2 * * ?")
    public void excluirArquivosAntigos(){
        var dataLimite = Instant.now().minus(DIAS_EXPIRACAO, ChronoUnit.DAYS);

        for (String diretorio : DIRETORIOS){
           var pasta = new File(diretorio);
           if(pasta.exists() && pasta.isDirectory()){
               for (File arquivo : pasta.listFiles()){
                   if (arquivo.isFile()){
                       var dataArquivo = Instant.ofEpochMilli(arquivo.lastModified());
                       if (dataArquivo.isBefore(dataLimite)){
                           arquivo.delete();
                       }
                   }
               }
           }
        }
    }

}

