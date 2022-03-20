package pss.trabalhofinal.bancodeimagens.utils;

import java.io.File;
import java.nio.file.Paths;

public abstract class RelativePath {

    public static String toRelativePath(File arquivo) {

        if (!arquivo.exists() || arquivo == null) {
            throw new RuntimeException("Erro ao transtormar caminho!");
        }
        if (arquivo.getPath().contains(System.getProperty("user.dir"))) {
            return Paths.get(System.getProperty("user.dir")).relativize(arquivo.toPath()).toString();
        }
        return arquivo.getPath();
    }
}
