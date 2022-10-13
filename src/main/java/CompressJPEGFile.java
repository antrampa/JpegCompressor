import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class CompressJPEGFile {

    public static void main(String[] args) throws IOException {

        File folder = new File("img");
        int i=0;
        for(File file : folder.listFiles()){
            if(file.isDirectory()){
                continue;
            }
            String fileName = file.getName();
            System.out.println("Compressing the file: " + fileName);
            try{
                String compressedFileName = "comp_" + fileName;
                compressingJPGFile(file, compressedFileName);
                System.out.println(++i + ". File Compressed : " + compressedFileName);
            }catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void compressingJPGFile(File file, String compressedFileName) throws IOException  {
        File imageFile = file; //new File("img/20221011_232704.jpg");
        File compressedImageFile = new File("imgcopr/" + compressedFileName);

        InputStream is = new FileInputStream(imageFile);
        OutputStream os = new FileOutputStream(compressedImageFile);

        float quality = 0.5f;
       
        BufferedImage image = ImageIO.read(is);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

        if (!writers.hasNext()) {
            throw new IllegalStateException("No writers found");
        }

        ImageWriter writer = (ImageWriter) writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        writer.write(null, new IIOImage(image, null, null), param);

        is.close();
        os.close();
        ios.close();
        writer.dispose();
    }

}
