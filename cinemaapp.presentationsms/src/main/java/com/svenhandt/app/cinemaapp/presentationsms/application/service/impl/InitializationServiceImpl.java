package com.svenhandt.app.cinemaapp.presentationsms.application.service.impl;

import com.svenhandt.app.cinemaapp.presentationsms.application.service.InitializationService;
import com.svenhandt.app.cinemaapp.presentationsms.domain.command.interceptor.exception.FilmAlreadyCreatedException;
import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.CreateFilmCommand;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class InitializationServiceImpl implements InitializationService {

    private static final String SEMICOLON = ";";
    private static final String DATA_LINE_INCORRECT_FORMAT = "data line has not the correct format: ";

    private static final Logger LOG = LoggerFactory.getLogger(InitializationServiceImpl.class);

    @Value("${initfiles.presentations.file.path}")
    private String initPresentationsFilePath;

    private final CommandGateway commandGateway;

    public InitializationServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    @Override
    public void initialize() {
        BufferedReader reader = null;
        try
        {
            URL fileUrl = getClass().getClassLoader().getResource(initPresentationsFilePath);
            URI fileUri = fileUrl.toURI();
            Path path = Paths.get(fileUri);
            reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                createFilm(currentLine);
            }
        }
        catch (IOException | URISyntaxException ex)
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            IOUtils.closeQuietly(reader);
        }
    }

    private void createFilm(String dataLine) {
        Validate.isTrue(StringUtils.contains(dataLine, SEMICOLON), DATA_LINE_INCORRECT_FORMAT);
        int semicolonIndex = StringUtils.indexOf(dataLine, SEMICOLON);
        Validate.isTrue(semicolonIndex < (StringUtils.length(dataLine) - 1));
        String filmName = StringUtils.substring(dataLine, 0, semicolonIndex);
        String filmId = StringUtils.lowerCase(filmName.replaceAll(" ", "_"));
        String presentationsDataLine = StringUtils.substring(dataLine, semicolonIndex + 1, StringUtils.length(dataLine));
        try {
            CreateFilmCommand command = CreateFilmCommand
                    .builder()
                    .filmId(filmId)
                    .filmName(filmName)
                    .presentationsDataLine(presentationsDataLine)
                    .build();
            commandGateway.sendAndWait(command);
        }
        catch(FilmAlreadyCreatedException ex) {
            LOG.warn("Film already created: " + filmName);
        }
    }

}
