package br.com.meli.desafiospring.util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FormatdorUtil {

    public static String formatarCPF(String cpf) {
        String mascara = "###.###.###-##";
        try {
            MaskFormatter maskFormatter = new MaskFormatter(mascara);
            maskFormatter.setValueContainsLiteralCharacters(false);
            return maskFormatter.valueToString(cpf);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
