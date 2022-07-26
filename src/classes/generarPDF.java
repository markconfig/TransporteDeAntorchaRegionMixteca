package classes;

import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class generarPDF {

    private Font fuenteBold = new Font(Font.FontFamily.COURIER, 24, Font.BOLD);
    private Font fuenteNormal = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
    private Font fuenteItalic = new Font(Font.FontFamily.COURIER, 12, Font.ITALIC);
    private Font fuente = new Font(Font.FontFamily.COURIER, 11, Font.BOLDITALIC);
    private Font fuenteParaLinea = new Font(Font.FontFamily.COURIER, 11, Font.BOLD);

    public void generarPDF(String Titulo, String RutaImagen, String Informacion, String OperacionesPrecio, String Pie,
            String Empresa, String LineaDivisora, String RutaPDF) {
        try {
            // Document document = new Document(PageSize.CROWN_QUARTO,36,36,10,10);
            Document document = new Document(PageSize.LARGE_CROWN_OCTAVO, 12, 12, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(RutaPDF));
            document.open();
            document.add(getHeader(Titulo));
            Image image = Image.getInstance(RutaImagen);
            image.scaleAbsolute(100, 100);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
            document.add(getInfo(Informacion));
            document.add(getPrecioOperaciones(OperacionesPrecio));
            document.add(getPie(Pie));
            document.add(getEmpresa(Empresa));
            document.add(getLinea(LineaDivisora));
            document.close();
        } catch (Exception e) {
        }

    }

    private Paragraph getHeader(String Texto) {
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(Texto);
        c.setFont(fuenteBold);
        p.add(c);
        return p;
    }

    private Paragraph getInfo(String Texto) {
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        // p.setAlignment(Element.ALIGN_CENTER);
        p.setAlignment(Element.ALIGN_UNDEFINED);
        c.append(Texto);
        c.setFont(fuenteItalic);
        p.add(c);
        return p;
    }

    private Paragraph getPrecioOperaciones(String Texto) {
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_RIGHT);
        c.append(Texto);
        c.setFont(fuenteItalic);
        p.add(c);
        return p;
    }

    private Paragraph getPie(String Texto) {
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_RIGHT);
        c.append(Texto);
        c.setFont(fuenteNormal);
        p.add(c);
        return p;
    }

    private Paragraph getEmpresa(String Texto) {
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(Texto);
        c.setFont(fuente);
        p.add(c);
        return p;
    }

    private Paragraph getLinea(String Texto) {
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(Texto);
        c.setFont(fuenteParaLinea);
        p.add(c);
        return p;

    }

}
