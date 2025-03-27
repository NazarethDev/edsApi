package br.com.eds.api.eds.service.arquivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class ArquivoCleanupService {

    @Autowired
    ArquivoService arquivoService;

    private String [] DIRETORIOS = {
            arquivoService.getIMPRESSAO_DIR(),
            arquivoService.getDESIGN_DIR(),
            arquivoService.getCONSERTO_DIR()
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

