package com.salesianostriana.dam.campusswap.ficheros.logica.local;

import com.salesianostriana.dam.campusswap.ficheros.general.excepciones.StorageException;
import com.salesianostriana.dam.campusswap.ficheros.general.model.FileMetadata;
import com.salesianostriana.dam.campusswap.ficheros.logica.StorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    private Path rootLocation;


    @PostConstruct
    @Override
    public void init() {
        rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }

    }

    @Override
    public FileMetadata store(MultipartFile file)  {
        try {
            String filename =  store(file.getBytes(), file.getOriginalFilename(), file.getContentType());
            return LocalFileMetadataImpl.of(filename);
        } catch (Exception ex) {
            throw new StorageException("Error storing file: " + file.getOriginalFilename(), ex);
        }
    }
    @Override
    public Resource loadAsResource(String id) {
        try {
            Path file = load(id);
            UrlResource resource =
                    new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + id);
            }

        } catch (MalformedURLException ex) {
            throw new StorageException("Could not read file: " + id);
        }
    }

    private void redimensionarImagen(BufferedImage originalImage, int targetWidth, int targetHeight, Path targetPath, String format) throws IOException {
        int finalWidth = targetWidth;
        int finalHeight = targetHeight;

        if(targetHeight == -1){
            double ratio = (double) targetWidth / originalImage.getWidth();
            finalHeight = (int) (originalImage.getHeight() * ratio);
        }

        int type = (originalImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        BufferedImage resizedImage = new BufferedImage(finalWidth, finalHeight, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, finalWidth, finalHeight, null);
        g.dispose();

        ImageIO.write(resizedImage, format != null ? format : "jpg", targetPath.toFile());
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Files.delete(load(filename));
        } catch (IOException e) {
            throw new StorageException("Could not delete file:" + filename);
        }
    }

    private String store(byte[] file, String filename, String contentType) throws Exception {

        // Limpiamos el nombre del fichero
        String newFilename = StringUtils.cleanPath(filename);

        if (file.length == 0)
            throw new StorageException("The file is empty");

        newFilename = calculateNewFilename(newFilename);

        try (InputStream inputStream = new ByteArrayInputStream(file)) {
            Files.copy(inputStream, rootLocation.resolve(newFilename),
                    StandardCopyOption.REPLACE_EXISTING
                    );
        } catch(IOException ex) {
            throw new StorageException("Error storing file: " + newFilename, ex);
        }

        return newFilename;
    }

    private String calculateNewFilename(String filename) {
        String newFilename = filename;

        while(Files.exists(rootLocation.resolve(newFilename))) {
            // Tratamos de generar un nuevo
            String extension = StringUtils.getFilenameExtension(newFilename);
            String name = newFilename.replace("." + extension, "");

            String suffix = Long.toString(System.currentTimeMillis());
            suffix = suffix.substring(suffix.length()-6);

            newFilename = name + "_" + suffix + "." + extension;

        }
        return newFilename;
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not delete all");
        }
    }
}
