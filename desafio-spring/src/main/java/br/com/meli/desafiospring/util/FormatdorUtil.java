package br.com.meli.desafiospring.util;

import org.apache.log4j.Logger;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FormatdorUtil {

    private static final Logger logger = Logger.getLogger(FormatdorUtil.class);

    private FormatdorUtil() {}

    public static String formatarCPF(String cpf) {
        String mascara = "###.###.###-##";
        try {
            MaskFormatter maskFormatter = new MaskFormatter(mascara);
            maskFormatter.setValueContainsLiteralCharacters(false);
            return maskFormatter.valueToString(cpf);
        } catch (ParseException e) {
            logger.error(e);
            return "";
        }
    }
}
